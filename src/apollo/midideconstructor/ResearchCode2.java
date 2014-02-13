package apollo.midideconstructor;

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

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

public class ResearchCode2 
{

    public static void main(String[] args) throws Exception 
    {
        //Have a bunch of weights for different points in the song.
        Map<Integer, double[]> songWeights = new TreeMap<>();
        Map<Integer, Integer> currentlyHeldNotes = new TreeMap<>();
        
        Map<Integer, Integer> tempoMap = new TreeMap<>();
        
        NoteVisualizer visuals = new NoteVisualizer();
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
        InputStream is = new BufferedInputStream(new FileInputStream(new File(".\\Midis\\Kakariko.mid")));

        // Sets the current sequence on which the sequencer operates.
        // The stream must point to MIDI file data.
        sequencer.setSequence(is);
        
        //Get the Pulses Per Quarter.
        MidiFileReader reader = new MidiFileReader();
        MidiFileFormat format = reader.getMidiFileFormat(new BufferedInputStream(new FileInputStream(new File(".\\Midis\\Kakariko.mid"))));
        System.out.println("PPQ: "+format.getResolution()+"  <------------!!!");
        double ppq = format.getResolution();
        if ((int) ppq % 16 != 0)
        {
            throw new IllegalArgumentException("Bad ppq detected: "+ppq);
        }
        
        //Actually, I didn't want the sequencer to play it just yet. I actually
        //wanted the sequence. So let's grab that.
        Sequence sequence = sequencer.getSequence();
        
        Track[] tracks = sequence.getTracks();
        
        //System.out.println(sequence.deleteTrack(tracks[1]));
        
        tracks = sequence.getTracks();
        
        sequencer.setSequence(sequence);
        
        //Prepare the buffers.
        int endOfSong = 400000;
        for (int x = 0; x < endOfSong+ppq; x+=((int)ppq/16))
        {
            songWeights.put(x, new double[12]);
        }
        
        
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
                if (c instanceof NoteOn)
                {
                    NoteOn noteOn = (NoteOn)c;
                    if (noteOn.getVelocity() == 0)
                    {
                        handleNoteRelease(currentlyHeldNotes, songWeights, noteOn.getNoteNumber(), (int)e.getTick(), (int)ppq/16, (int)ppq, endOfSong);
                    }
                    currentlyHeldNotes.put(noteOn.getNoteNumber(), (int)e.getTick());
                }
                if (c instanceof NoteOff)
                {
                    NoteOff noteOff = (NoteOff)c;
                    handleNoteRelease(currentlyHeldNotes, songWeights, noteOff.getNoteNumber(), (int)e.getTick(), (int)ppq/16, (int)ppq, endOfSong);
                }
                if (c instanceof SetTempo)
                {
                    SetTempo setTempo = (SetTempo)c;
                    System.out.println("At "+e.getTick()+": Tempo: "+setTempo);
                    tempoMap.put((int)e.getTick(), setTempo.getMicrosecondsPerQuarterNote());
                }
            }
        }        

        // Starts playback of the MIDI data in the currently loaded sequence.
        //sequencer.setTrackMute(1, true);
        sequencer.setTempoFactor(1f);
        sequencer.start();
        
        double tempo = 50000;
        double timeScalar = (tempo/1000)/ppq; //(Milliseconds per pulse)
        
        double microsecondsThrough = 0;
        int microsecondsWaited = 0;
        double oldPulses = 0;
        
        for (int pulses = 0; pulses <= endOfSong; pulses+= ppq/16)
        {
            if (pulses%(ppq*2) == 0)
            {
                System.out.println("======Half Measure======");
            }
            else if (pulses%ppq == 0)
            {
                System.out.println("------------------------");
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
            
            double pulsesWaited = (pulses - oldPulses);
            microsecondsThrough += (pulsesWaited * timeScalar);
            int microSecondToWait = (int)(microsecondsThrough - microsecondsWaited);
            Thread.sleep(microSecondToWait);
            microsecondsWaited += microSecondToWait;
            
            
            oldPulses = pulses;
            visuals.updateBars(songWeights.get(pulses));
            visuals.printChord(songWeights.get(pulses));
        }

    }

    private static void handleNoteRelease(Map<Integer, Integer> currentlyHeldNotes, Map<Integer, double[]> songWeights, int noteNumber, int endTime, int timeStep, int ppq, int endOfSong) 
    {
        //Make sure we're actually holding the key down.
        if (!currentlyHeldNotes.keySet().contains(noteNumber))
        {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).severe("A note at "+endTime+" was released before ever being held down!");
            return;
        }
        
        //Get the sstart time, and remove the held note. (It's been released now.)
        int startTime = currentlyHeldNotes.get(noteNumber);
        currentlyHeldNotes.remove(noteNumber);
        
        //Figure out the pitch
        int pitch = noteNumber%12;
        
        //Truncate both to the nearest time step.
        startTime = (startTime/timeStep)*timeStep;
        endTime = (endTime/timeStep)*timeStep;
        
        //Figure out how much this note should "weigh." number of pulses / pulsesPerQuarter = number of quarter notes.
        double numberOfQuarterNotes = (double)(endTime-startTime)/(double)ppq;
        
        double noteWeightMultiplier = numberOfQuarterNotes/(1.0+numberOfQuarterNotes);
        noteWeightMultiplier *= 100.0;
        
        //As long as the note is being held down, contribute the full note weight.
        for (int songPosition = (int)startTime; songPosition <= endTime; songPosition+=timeStep)
        {
            songWeights.get(songPosition)[pitch] += noteWeightMultiplier;
        }
        
        //Go back in time, contributing a little of your chord to the past.
        double adjustedWeight = 1;
        for (int songPosition = ((int)startTime-timeStep); songPosition >=0; songPosition-=timeStep)
        {
            adjustedWeight *= 0.5;
            songWeights.get(songPosition)[pitch] += (noteWeightMultiplier*adjustedWeight);
        }
        
        //Go forward in time, contributing a little more of your chord to the future.
        adjustedWeight = 1;
        for (int songPosition = ((int)endTime+timeStep); songPosition <=endOfSong+timeStep; songPosition+=timeStep)
        {
            adjustedWeight *= 0.82;
            songWeights.get(songPosition)[pitch] += (noteWeightMultiplier*adjustedWeight);
        }
        
    }

    private static void printTheWholeMess(Map<Integer, double[]> songWeights) 
    {
        for (Integer i : songWeights.keySet())
        {
            System.out.print(i+": ");
            Funcs.printArray(songWeights.get(i));
        }
    }
}