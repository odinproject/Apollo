package apollo.midideconstructor;

import apollo.MusicFuncs;
import apollo.chordbase.Chord;
import apollo.chordbase.ChordDatabase;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.MidiCommand;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.MidiCommandWithChannel;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.NoteOff;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.NoteOn;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.RawMidiMessageParser;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiMessage;

import apollo.chordbase.Funcs;
import edu.columbia.ee.csmit.MidiKaraoke.MidiMessage.SetTempo;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

public class ResearchCode3 
{

    public static void main(String[] args) throws Exception 
    {
        
        Map<Integer, Integer> tempoMap = new TreeMap<>();
        int endOfSong = CONSTS.END_OF_SONG;
        
        int samplesPerBeat = CONSTS.SAMPLES_PER_BEAT;
        int beatsPerSegment = CONSTS.BEATS_PER_SEGMENT;
        
        ChordVisualizer visuals = new ChordVisualizer(new ChordDatabase());
        visuals.setVisible(true);
        // Obtains the default Sequencer connected to a default device.
        Sequencer sequencer = MidiSystem.getSequencer();

        // Opens the device, indicating that it should now acquire any
        // system resources it requires and become operational.
        sequencer.open();
        
        File directory = new File(".\\");
        System.out.println(directory.isDirectory());
        for (File tFile : directory.listFiles())
        {
            System.out.println(tFile.getName());
        }

        // create a stream from a file
        InputStream is = new BufferedInputStream(new FileInputStream(new File(".\\Midis\\TakeBackNight.mid")));

        // Sets the current sequence on which the sequencer operates.
        // The stream must point to MIDI file data.
        sequencer.setSequence(is);
        
        //Get the Pulses Per Quarter.
        MidiFileReader reader = new MidiFileReader();
        MidiFileFormat format = reader.getMidiFileFormat(new BufferedInputStream(new FileInputStream(new File(".\\Midis\\TakeBackNight.mid"))));
        System.out.println("PPQ: "+format.getResolution()+"  <------------!!!");
        double ppq = format.getResolution();
        if ((int) ppq % samplesPerBeat != 0)
        {
            throw new IllegalArgumentException("Bad ppq detected: "+ppq);
        }
        
        //Actually, I didn't want the sequencer to play it just yet. I actually
        //wanted the sequence. So let's grab that.
        Sequence sequence = sequencer.getSequence();
        
        Track[] tracks = sequence.getTracks();
        
        //System.out.println(sequence.deleteTrack(tracks[9]));
      
        sequencer.setSequence(sequence);
        
        tracks = sequence.getTracks();
        
        //Use the library I found to make something that changes midi bytecode into
        //java classes.
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
        
        Deconstructor d = new Deconstructor();
        DeconstructedPossibility dp = d.deconstruct(tracks, ppq);
        
        
        
        
        
        // initialize the audio player
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        
        File file = new File("OrchestraRhythm.sf2");
        //soundbank = MidiSystem.getSoundbank(file); //Uncomment this line to get the "orchestra hits" back.
        Soundbank soundbank = synth.getDefaultSoundbank();
        MidiChannel[] midiChannel = synth.getChannels();
        
        Instrument[] instruments = soundbank.getInstruments();
        
        if (soundbank != null)
        {
            boolean bInstrumentsLoaded = synth.loadAllInstruments(soundbank);
        }
        
        int chordInstrument = 51;
        int melodyInstrument = 0;
        
        midiChannel[11].programChange(chordInstrument);
        
        
      
        // Starts playback of the MIDI data in the currently loaded sequence.
        sequencer.setTempoFactor(1f);
        sequencer.start();
        
        double tempo = 50000;
        double timeScalar = (tempo/1000)/ppq; //(Milliseconds per pulse)
        
        double microsecondsThrough = 0;
        int microsecondsWaited = 0;
        double oldPulses = 0;
        int segmentsSoFar = 0;
        Chord bestChord = new Chord();
        
        for (int pulses = 0; pulses <= endOfSong; pulses+= ppq/samplesPerBeat)
        {
            if (pulses%(ppq*beatsPerSegment) == 0)
            {
                //System.out.println("======Half Measure======");
                int oldType = bestChord.getChordType();
                int oldTone = bestChord.getTone();
                MusicFuncs.turnOffGaussianChord(midiChannel[11], bestChord);
                //System.out.println(dp.getVoteStringOnMeasure(segmentsSoFar++));
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
                //System.out.println("Selected "+bestChord);
                visuals.setChord(bestChord);
                MusicFuncs.playGaussianChord(midiChannel[11], bestChord, .55);
                int newType = bestChord.getChordType();
                int newTone = bestChord.getTone();
                System.out.println(oldType+","+(newTone-oldTone)+","+newType+" #Selected "+bestChord);
            }
            else if (pulses%ppq == 0)
            {
                //System.out.println("------------------------");
            }
            //Did the tempo change last measure?
            for (Integer i : tempoMap.keySet())
            {
                if (i >= oldPulses && i < pulses)
                {
                    tempo = tempoMap.get(i);
                    timeScalar = (tempo/1000)/ppq; //(Milliseconds per pulse)
                }
            }
            
            //Wait the correct amount of time.
            double pulsesWaited = (pulses - oldPulses);
            microsecondsThrough += (pulsesWaited * timeScalar);
            int microSecondToWait = (int)(microsecondsThrough - microsecondsWaited);
            Thread.sleep(microSecondToWait);
            microsecondsWaited += microSecondToWait;
            
            oldPulses = pulses;
            //visuals.updateBars(songWeights.get(pulses));
            //visuals.printChord(songWeights.get(pulses));
        }

    }



}