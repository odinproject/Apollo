/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo.composer;

import apollo.chordbase.CheatChordDatabase;
import apollo.chordbase.Chord;
import apollo.orchestra.Bar;
import apollo.orchestra.Note;
import apollo.orchestra.Orchestra;
import apollo.orchestra.Tick;
import game.GameProperties;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Martin
 */
public class Composer {
    
    private Orchestra orchestra;
    private GameProperties properties;
    
    // sometimes we need to place stops in the 'yet-to-be-generated' next bar
    // this meta-bar carries information about stops that need to be accounted
    // for when composing the next bar
    private Bar nextBarStops;
    
    private CheatChordDatabase cheatSheet;
    public final static Integer MIDDLE_C_OFFSET = 60;
    
    public Composer (Orchestra o)
    {
        orchestra = o;
        cheatSheet = new CheatChordDatabase();
        cheatSheet.setSongNumber(2);
        nextBarStops = new Bar();
        for (int i=0; i<16; i++)
        {
            Tick emptyTick = new Tick();
            nextBarStops.addTick(i, emptyTick);
        }
        
        Chord c = nextChordFromCheatData();
        orchestra.addBarToTrack(1, barForChord(c));
    }
    
    public void setGameProperties(GameProperties prop)
    {
        properties = prop;
    }
    
    public void update()
    {
        if (orchestra.unplayedBars() < 1)
        {
            // get cheat chords
            Chord c = nextChordFromCheatData();
            orchestra.addBarToTrack(1, barForChord(c));
            
            // get rhythms
//            orchestra.addBarToTrack(2, randomRhythm());
        }
    }
    
    public Bar barForChord(Chord c)
    {
        Bar newBar = new Bar();
        Set<Integer> notes = c.getNotes();
        
        // fill newBar with empty Ticks
        for (int i=0; i<16; i++)
        {
            Tick emptyTick = new Tick();
            newBar.addTick(i, emptyTick);
        }
        
        // run through the carried-over stops from the previous Bar. Add any
        // stops that may still exist.
        for (int i=0; i<nextBarStops.getTicks().length; i++)
        {
            Tick tick = nextBarStops.getTicks()[i];
            for (Note stop : tick.getStops())
            {
                newBar.getTick(i).addStop(stop);
            }
        }
        
        // this is a list of new stops that might carry over into the next chord
        Tick nextChordStops = new Tick();
        
        // we limit the chord to a melodic range of MIDDLE_C +- 12
        // it ends up sounding identical to the gaussian anyways
        int melodicRange = 12;
        
        for (int pitch = 0; pitch < 128; pitch++)
        {
            if (notes.contains(pitch%12) && pitch > MIDDLE_C_OFFSET - melodicRange 
                                         && pitch < MIDDLE_C_OFFSET + melodicRange)
            {
                Note n = new Note((short)pitch, (short)75);
                newBar.getTick(0).addNote(n);
                
                nextChordStops.addStop(n);
            }
        }
        
        nextBarStops = new Bar();
        for (int i=1; i<16; i++)
        {
            Tick emptyTick = new Tick();
            nextBarStops.addTick(i, emptyTick);
        }
        nextBarStops.addTick(0, nextChordStops);
        
        return newBar;
    }
    
    /**
     * @return Chord: the next Chord in the cheat database if it exists,
     * otherwise an empty Chord with no notes
     */
    public Chord nextChordFromCheatData()
    {
        if (cheatSheet != null)
        {
            return cheatSheet.getNextChord(0);
        }
        else
        {
            return new Chord();
        }
    }
    
    public Bar randomRhythm()
    {
        Random random = new Random();
        Bar randomRhythm = new Bar();

        for (int i=0; i<16; i++)
        {
            int beat = random.nextInt(2);
            Tick t = new Tick();
            if (beat == 1)
            {
                Note n = new Note((short)60, (short)200);
                t.addNote(n);
            }
            randomRhythm.addTick(i, t);
        }
        return randomRhythm;
    }
}
