/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo;

import apollo.chordbase.Chord;
import java.util.Set;
import javax.sound.midi.MidiChannel;

/**
 *
 * @author Talonos
 */
public class MusicFuncs 
{
    private static Integer MIDDLE_C = 60;

    /**
     * When given a midi channel, plays the chord across the keyboard in a roughly gaussian
     * distribution.
     * @param midiChannel the channel to play the chord on.
     * @param chord the chord to play.
     * @param velocityAdjuster A number from 0 to 1 to adjust the velocity by.
     */
    public static void playGaussianChord(MidiChannel midiChannel, Chord chord, double velocityAdjuster) 
    {
        Set<Integer> notes = chord.getNotes();
        for (int pitch = 0; pitch < 128; pitch++)
        {
            if (notes.contains(pitch%12))
            {
                //This isn't a gaussian. I don't know what it is, actually. We should switch it to
                //to a guassian later.
                double velocity = 128.0-Math.abs(((double)pitch*2.0)-128.0);
                velocity = velocity*velocity/128.0*velocityAdjuster;
                midiChannel.noteOn(pitch, (int)velocity);
            }
        }
    }

    public static void turnOffGaussianChord(MidiChannel midiChannel, Chord currentChord) 
    {
        Set<Integer> notes = currentChord.getNotes();
        for (int pitch = 0; pitch < 128; pitch++)
        {
            if (notes.contains(pitch%12))
            {
                midiChannel.noteOff(pitch);
            }
        }
    }

    static void turnOffMelodyNote(MidiChannel midiChannel, Chord chord, int tick) 
    {
        playMelodyNote(midiChannel, chord, tick, 0);
    }

    static void playMelodyNote(MidiChannel midiChannel, Chord chord, int tick, double veloMulter) 
    {
        //The tick comes in zero indexed. Fix that.
        tick++;
        int velocity = (int)(veloMulter * 128.0);
        
        //See how we must play this chord.
        boolean chordHasFourNotes = chord.getNotes().size() == 4;
        
        //Four note version: 43212343
        if (chordHasFourNotes)
        {
            int noteNum = 0;
            for (Integer pitch : chord.getNotes())
            {
                noteNum++;
                if (noteNum == 1 && tick == 4)
                {
                    midiChannel.noteOn(pitch+MIDDLE_C, (int)velocity);
                }
                if (noteNum == 2 && (tick == 3 || tick == 5))
                {
                    midiChannel.noteOn(pitch+MIDDLE_C, (int)velocity);
                }
                if (noteNum == 3 && (tick == 2 || tick == 6 || tick == 8))
                {
                    midiChannel.noteOn(pitch+MIDDLE_C, (int)velocity);
                }
                if (noteNum == 4 && (tick == 1 || tick == 7))
                {
                    midiChannel.noteOn(pitch+MIDDLE_C, (int)velocity);
                }
            }
        }
        else
        {
            //Three note version: 32132132, like clocks.
            int noteNum = 0;
            for (Integer pitch : chord.getNotes())
            {
                noteNum++;
//                System.out.println("noteNum: "+noteNum+", pitch: "+pitch+", tick: "+tick);
                if (noteNum == 1 && (tick == 3 || tick == 6))
                {
                    midiChannel.noteOn(pitch+MIDDLE_C, (int)velocity);
//                    System.out.println(" - Turning on 1.");
                }
                if (noteNum == 2 && (tick == 2 || tick == 5 || tick == 8))
                {
                    midiChannel.noteOn(pitch+MIDDLE_C, (int)velocity);
//                    System.out.println(" - Turning on 2.");
                }
                if (noteNum == 3 && (tick == 1 || tick == 4 || tick == 7))
                {
                    midiChannel.noteOn(pitch+MIDDLE_C, (int)velocity);
//                    System.out.println(" - Turning on 3.");
                }
            }
        }
    }
    
}
