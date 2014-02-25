package apollo.midideconstructor;

import MidiLibs.MidiFileReader;
import apollo.MusicFuncs;
import apollo.chordbase.Chord;
import apollo.chordbase.ChordDatabase;
import apollo.chordbase.Funcs;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.MidiCommand;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.RawMidiMessageParser;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.SetTempo;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.TrackName;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

/**
 * Represents an analysis and subsequent playback of a midi file.
 * @author Talonos
 */
public class MidiAnalysis 
{
    int samplesPerBeat;
    int beatsPerSegment;
    String fileName;
    double ppq = 0;
    int endOfSong = 0;
    
    boolean hasAnalysed;
    private Integer offset;
    private Sequencer sequencer;
    private Synthesizer synth;
    private double volume;
    private float tempoFact = 1;
    
    public MidiAnalysis (String fileName)
    {
        this.fileName = fileName;
    }
    
    public void analyzeSong(MidiDeconstructorForm parent) throws Exception
    {
        // Obtains the default Sequencer connected to a default device.
        Sequencer sequencer = MidiSystem.getSequencer();

        // Opens the device, indicating that it should now acquire any
        // system resources it requires and become operational.
        sequencer.open();

        // create a stream from a file
        InputStream is = new BufferedInputStream(new FileInputStream(new File(fileName)));
        
        // Sets the current sequence on which the sequencer operates.
        // The stream must point to MIDI file data.
        sequencer.setSequence(is);
        
        //Get the Pulses Per Quarter.
        MidiFileReader reader = new MidiFileReader();
        MidiFileFormat format = reader.getMidiFileFormat(new BufferedInputStream(new FileInputStream(new File(fileName))));
        ppq = format.getResolution();
        
        //...and display it.
        parent.displayPPQ(ppq);

        //Get the sequence.
        Sequence sequence = sequencer.getSequence();
        
        //Get the tracks from the sequence.
        Track[] tracks = sequence.getTracks();
        
        List<String> trackDescs = new ArrayList<>();
        RawMidiMessageParser midiParser = new RawMidiMessageParser();
        int trackNum = 0;
        for (Track track : tracks)
        {
            String trackName = "Unknown";
            for (int j = 0; j < track.size(); j++)
            {
                MidiEvent e = track.get(j);
                MidiMessage m = e.getMessage();
                MidiCommand c = midiParser.parse(m);
                if (c instanceof TrackName)
                {
                    trackName = ((TrackName)c).getName();
                    System.out.println(trackNum+": "+trackName);
                }
                if (endOfSong < e.getTick())
                {
                    endOfSong = (int) e.getTick();
                }
            }
            if (trackName.length()>12)
            {
                trackName = trackName.substring(0, 12);
            }
            trackName = (trackNum++)+trackName+": "+track.size()+" events";
            trackDescs.add(trackName);
        }
        
        endOfSong += 1000;
        parent.setTrackNames(trackDescs);
    }
    
    public void testSong(MidiDeconstructorForm parent, Set<Integer> tracksToDelete) throws Exception 
    {
        // Obtains the default Sequencer connected to a default device.
        sequencer = MidiSystem.getSequencer();

        // Opens the device, indicating that it should now acquire any
        // system resources it requires and become operational.
        sequencer.open();

        // create a stream from a file
        InputStream is = new BufferedInputStream(new FileInputStream(new File(fileName)));

        // Sets the current sequence on which the sequencer operates.
        // The stream must point to MIDI file data.
        sequencer.setSequence(is);
        
        //Check to make sure the PPQ and SamplesPerBeat work correctly.
        if ((int) ppq % samplesPerBeat != 0)
        {
            throw new IllegalArgumentException("Bad ppq detected: "+ppq);
        }
        
        //We don't want the sequencer to play it just yet. We actually
        //wanted the sequence, So let's grab that.
        Sequence sequence = sequencer.getSequence();
        
        //Get the tracks from the sequence.
        Track[] tracks = sequence.getTracks();
        
        //Delete unwanted Tracks
        for (Integer toDelete : tracksToDelete)
        {
            sequence.deleteTrack(tracks[toDelete]);
        }
      
        //Now we have a new sequence. Put it back in the sequencer.
        sequencer.setSequence(sequence);
        
        //Get the new set of tracks from the new sequence. (Same as old, but missing the onces we deleted.)
        tracks = sequence.getTracks();
        
        //Create a map to hold tempo change events.
        Map<Integer, Integer> tempoMap = new TreeMap<>();
        
        //Use the library I found to make something that changes midi bytecode into
        //java classes. Use it to make the tempo map.
        RawMidiMessageParser midiParser = new RawMidiMessageParser();
        for (int i = 0; i < tracks.length; i++)
        {
            System.out.println("Track "+i+": "+tracks[i].size()+" events.");
            for (int j = 0; j < tracks[i].size(); j++)
            {
                MidiEvent e = tracks[i].get(j);
                MidiMessage m = e.getMessage();
                MidiCommand c = midiParser.parse(m);
                if (c instanceof SetTempo)
                {
                    SetTempo setTempo = (SetTempo)c;
                    System.out.println("At "+e.getTick()+": Tempo: "+setTempo);
                    tempoMap.put((int)e.getTick(), setTempo.getMicrosecondsPerQuarterNote());
                }
            }
        }
        
        
        Deconstructor d = new Deconstructor(samplesPerBeat, beatsPerSegment, endOfSong, offset);
        DeconstructedPossibility dp = d.deconstruct(tracks, ppq);
        List<double[]> weights = dp.getSampleWeights();
        
        // initialize the audio player
        synth = MidiSystem.getSynthesizer();
        synth.open();
        
        Soundbank soundbank = synth.getDefaultSoundbank();
        MidiChannel[] midiChannel = synth.getChannels();
        
        if (soundbank != null)
        {
            synth.loadAllInstruments(soundbank);
        }
        int chordInstrument = 105;
        
        midiChannel[15].programChange(chordInstrument);

        // Starts playback of the MIDI data in the currently loaded sequence.
        sequencer.setTempoFactor(tempoFact);
        sequencer.start();
        
        double tempo = 50000;
        double timeScalar = ((tempo/1000)/ppq)/tempoFact; //(Milliseconds per pulse)
        
        double millisecondsThrough = 0;
        int millisecondsWaited = 0;
        double oldPulses = 0;
        int segmentsSoFar = 0;
        Chord bestChord = new Chord();
        int pulsesPerSample = (int)(ppq/samplesPerBeat);
        
        int sampleNum = 0;
        boolean chordsStarted = false;
        
        long timeStarted = System.currentTimeMillis();
        for (int pulses = 0; pulses <= endOfSong; pulses+= pulsesPerSample)
        {
            if ((pulses-(offset*pulsesPerSample))%(ppq*beatsPerSegment) == 0)
            {
                int oldType = bestChord.getChordType();
                int oldTone = bestChord.getTone();
                
                MusicFuncs.turnOffGaussianChord(midiChannel[11], bestChord);
                Map <Chord, Double> votes = dp.getVoteOnMeasure(segmentsSoFar++);
                double bestVote = -1;
                for (Chord c : votes.keySet())
                {
                    if (votes.get(c)> bestVote)
                    {
                        bestVote = votes.get(c);
                        bestChord = c;
                    }
                }

                MusicFuncs.playGaussianChord(midiChannel[15], bestChord, volume);
                chordsStarted = true;
                int newType = bestChord.getChordType();
                int newTone = bestChord.getTone();
                System.out.println(oldType+","+(newTone-oldTone)+","+newType+" #Selected "+bestChord);
            }

            //Did the tempo change last measure?
            for (Integer i : tempoMap.keySet())
            {
                if (i >= oldPulses && i < pulses)
                {
                    tempo = tempoMap.get(i);
                    timeScalar = ((tempo/1000)/ppq)/tempoFact; //(Milliseconds per pulse)
                }
            }
            
            //Wait the correct amount of time.
            double pulsesWaited = (pulses - oldPulses);
            millisecondsThrough += (pulsesWaited * timeScalar);
            millisecondsWaited = (int)(System.currentTimeMillis()-timeStarted);
            int microSecondToWait = (int)(millisecondsThrough - millisecondsWaited);
            Thread.sleep(Math.max(microSecondToWait,0));
            
            oldPulses = pulses;
            
            if (chordsStarted)
            {
                parent.updateBars(weights.get(sampleNum++), bestChord, 0);
            }
        }

    }

    void setConsts(Integer spb, Integer bps, Integer off) {
        this.samplesPerBeat=spb;
        this.beatsPerSegment=bps;
        this.offset = off;
    }

    void stopEverything() 
    {
        
        MidiChannel[] midiChannel = synth.getChannels();
        for (MidiChannel c : midiChannel)
        {
            MusicFuncs.turnOffAllNotes(c);
        }
        synth.close();
        sequencer.stop();
    }

    void setVolume(int value) 
    {
        this.volume = (double)value/100;
    }

    void setTempo(double d) 
    {
        this.tempoFact = (float) d;
    }


}
