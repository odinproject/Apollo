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
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiMessage;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

public class ResearchCode 
{

    public static void main(String[] args) throws Exception 
    {
        //Initialize the data structures to hold note events.
        Map<Integer, List<MidiCommandWithChannel>> timeSet = new TreeMap<>();
        
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
        InputStream is = new BufferedInputStream(new FileInputStream(new File(".\\Midis\\LostWoods.mid")));

        MidiFileReader reader = new MidiFileReader();
        
        MidiFileFormat format = reader.getMidiFileFormat(new BufferedInputStream(new FileInputStream(new File(".\\Midis\\LostWoods.mid"))));
        
        System.out.println("PPQ: "+format.getResolution()+"  <------------!!!");
        // Sets the current sequence on which the sequencer operates.
        // The stream must point to MIDI file data.
        sequencer.setSequence(is);
        
        Sequence sequence = sequencer.getSequence();
        
        Track[] tracks = sequence.getTracks();
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
                    if (!timeSet.containsKey((int)e.getTick()))
                    {
                        timeSet.put((int)e.getTick(), new ArrayList<MidiCommandWithChannel>());
                    }
                    timeSet.get((int)e.getTick()).add(noteOn);
                }
                if (c instanceof NoteOff)
                {
                    NoteOff noteOff = (NoteOff)c;
                    if (!timeSet.containsKey((int)e.getTick()))
                    {
                        timeSet.put((int)e.getTick(), new ArrayList<MidiCommandWithChannel>());
                    }
                    timeSet.get((int)e.getTick()).add(noteOff);
                }
                System.out.println("At "+e.getTick()+": "+c);
            }
        }
        
        for (Integer time : timeSet.keySet())
        {
            System.out.println("Event at: "+time);
        }
        

        // Starts playback of the MIDI data in the currently loaded sequence.
        //sequencer.setTrackMute(1, true);
        sequencer.start();
        
        double oldTime = 0;
        double timeScalar = 0.348771484
;
        
        double[] noteWeights = new double[12];
        
        for (Integer time : timeSet.keySet())
        {
            double timeWaited = (time - oldTime);
            Thread.sleep((int)(timeWaited * timeScalar));
            oldTime = time;
            for (MidiCommandWithChannel command : timeSet.get(time))
            {
                if (command instanceof NoteOn)
                {
                    if (((NoteOn)command).getVelocity() != 0)
                    {
                        System.out.println("Playing "+((NoteOn)command).getNoteNumber());
                        noteWeights[((NoteOn)command).getNoteNumber()%12] += 100;
                    }
                    else
                    {
                        noteWeights[((NoteOn)command).getNoteNumber()%12] -= 100;
                    }
                }
                if (command instanceof NoteOff)
                {
                    noteWeights[((NoteOff)command).getNoteNumber()%12] -= 100;
                }
            }
            visuals.updateBars(noteWeights);
        }

    }
}