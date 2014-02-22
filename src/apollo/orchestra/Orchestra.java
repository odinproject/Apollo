package apollo.orchestra;

import apollo.Apollo;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

/**
 *
 * @author Martin
 */
public class Orchestra {
    
    private ArrayList<Track> tracks;
    
    private static final int MAX_TICKS = 16;
    private static final int MAX_BEATS = 4;
    
    // Timing
    // beats per minute. 60bpm = 1 beat per second. 180bpm = 3 beats per second.
    private int bpm;
    
    private int bar;
    // represents the beat in the current bar
    private int beat;
    // represents the tick in the current bar
    private int tick;
    Timer conductor;
    
    private Synthesizer synth;
    private MidiChannel[] midiChannels;
    private Instrument[] instruments;
    
    public Orchestra()
    {
        tracks = new ArrayList<Track>();
        
        setupOrchestra();
        
        // a standard 180 beats per minute
        bpm = 180;
        
        // reset the playhead
        bar = 0;
        beat = 0;
        tick = 0;
    }
    
    private void setupOrchestra()
    {
        try 
        {
            // initialize the audio player
            synth = MidiSystem.getSynthesizer();
            synth.open();
            
            Soundbank soundbank = synth.getDefaultSoundbank();
            instruments = soundbank.getInstruments();
            midiChannels = synth.getChannels();
            synth.loadAllInstruments(soundbank);
            
            Track melody = new Track(0);
            tracks.add(melody);
            setTrackInstrument(0, 0);

            Track backgroundChords = new Track(1);
            tracks.add(backgroundChords);
            setTrackInstrument(1, 51);

            Track percussion = new Track(10);
            tracks.add(percussion);
            setTrackInstrument(2, 116);
        }
        catch (MidiUnavailableException ex) 
        {
            Logger.getLogger(Apollo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void play()
    {
        conductor = new Timer(true);
        conductor.scheduleAtFixedRate(
            new TimerTask()
            {
                @Override
                public void run() 
                {
                    playNextTick();
                }
            }, 0, Math.round(1000.0/((bpm*MAX_BEATS)/60.0)));
    }
    
    public void pause()
    {
        // silence all tracks
        for (Track track : tracks)
        {
            midiChannels[track.getChannel()].allNotesOff();
        }
        // stop the conductor
        conductor.cancel();
    }
    
    public void addBarToTrack(int trackIndex, Bar b)
    {
        tracks.get(trackIndex).addBar(b);
    }
    
    public void playNextTick()
    {
        for (Track track : tracks)
        {
            // get the next tick for the track
            Tick t = track.getTick(bar, tick);
            
            for (Note stop : t.getStops())
            {
                midiChannels[track.getChannel()].noteOff(stop.getPitch());
            }
            for (Note note : t.getNotes())
            {
                midiChannels[track.getChannel()].noteOn(note.getPitch(), note.getVelocity());
            }
        }
        
        tick++;

        // update the current beat
        if (tick % MAX_BEATS == 0)
        {
            beat++;
        }

        // update the current tick and bar
        if (tick == MAX_TICKS)
        {
            tick = 0;
            bar++;
        }
    }
    
    public void setTrackInstrument(int track, int instrument)
    {
        if (track < tracks.size())
        {
            midiChannels[tracks.get(track).getChannel()].programChange(instrument);
        }
    }
    
    public void setTrackChannel(int track, int channel)
    {
        if (track < tracks.size())
        {
            tracks.get(track).setChannel(channel);
        }
    }
    
    public int unplayedBars()
    {
        return tracks.get(0).bars.size() - bar;
    }
}