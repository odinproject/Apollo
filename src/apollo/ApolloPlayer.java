/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo;

import apollo.chordbase.CheatChordDatabase;
import apollo.chordbase.Chord;
import apollo.chordbase.ChordDatabase;
import apollo.composer.Composer;
import apollo.orchestra.Orchestra;
import com.sun.media.sound.SF2Soundbank;
import com.sun.media.sound.*;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import game.GamePanel;
import javax.swing.JFrame;

/**
 * I get the feeling this class is doomed to be messy, at least for now. So be it.
 * @author Martin
 */
public class ApolloPlayer 
{
    //Constants:
    public final static Integer MIDDLE_C_OFFSET = 60;
    
    // midi player information
    private Synthesizer synth;
    private Soundbank soundbank;
    private MidiChannel[] midiChannel;
    private GamePanel game;
    private Instrument[] instruments;
    private int currentNote;
    private Chord currentChord;
    private ChordDatabase chordDatabase = new CheatChordDatabase();
    int tick = 0;
    
    private Composer composer;
    private Orchestra orchestra;
    
    public ApolloPlayer()
    {
        init();
    }
    
    public void init()
    {
        game = new GamePanel(this);
        // Create the interface
        JFrame window = new JFrame("Adventure Quest");
        window.setContentPane(game);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
        
        orchestra = new Orchestra();
        composer = new Composer(orchestra);
        
        
//        try 
//        {
//            // initialize the audio player
//            synth = MidiSystem.getSynthesizer();
//            synth.open();
//            
//            File file = new File("OrchestraRhythm.sf2");
//            //soundbank = MidiSystem.getSoundbank(file); //Uncomment this line to get the "orchestra hits" back.
//            soundbank = synth.getDefaultSoundbank();
//            midiChannel = synth.getChannels();
//            
//            instruments = soundbank.getInstruments();
//            printInstruments(soundbank, instruments);
//            
//            System.out.println(soundbank.getName());
//            
//            if (soundbank != null)
//            {
//                boolean bInstrumentsLoaded = synth.loadAllInstruments(soundbank);
//            }
//            
//            int chordInstrument = 51;
//            int melodyInstrument = 0;
//            
//            midiChannel[0].programChange(chordInstrument);
//            midiChannel[1].programChange(melodyInstrument);
//        } 
//        catch (/*IOException | InvalidMidiDataException | */MidiUnavailableException ex) 
//        {
//            Logger.getLogger(Apollo.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    public void play() 
    {   
        orchestra.play();
        Timer uploadCheckerTimer = new Timer(true);
//        currentChord = chordDatabase.getNextChord(0);
//        
        uploadCheckerTimer.scheduleAtFixedRate(
            new TimerTask() 
            {
                @Override
                public void run() 
                {
                    composer.update();
                }
            }, 0, 100);
////                    MusicFuncs.turnOffMelodyNote(midiChannel[1], currentChord, tick);
//                    tick++;
//                    if (tick%4==0)
//                    {
//                        MusicFuncs.turnOffGaussianChord(midiChannel[0], currentChord);
//                        selectNewChord();
//                        MusicFuncs.playGaussianChord(midiChannel[0], currentChord, .5);
//                        if (tick == 8) 
//                        {
//                            tick = 0;
//                        }
//                    }
////                    MusicFuncs.playMelodyNote(midiChannel[1], currentChord, tick, 1);
//                }
//
//            private void selectNewChord() 
//            {
//                //There are two lines below. They represent different ways of selecting chords.
//                
//                //Select based on the current chord number in the GUI.
//                //currentChord = chordDatabase.getChordByNumber(ui.musicValue);
//                
//                //Have the computer select a chord. See the method for further details.
//                currentChord = chordDatabase.getNextChord(game.musicValue);
//            }
//            
//            }, 0, 200);
    }
    
    public void printInstruments(Soundbank soundbank,Instrument[] instruments){
        System.out.println("");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("Soundbank name: " + soundbank.getName());
        System.out.println("Soundbank version: " + soundbank.getVersion());
        System.out.println("Description: " + soundbank.getDescription());
        System.out.println("Author:  " + soundbank.getVendor() + ".");
        System.out.println("Number of instruments: " + soundbank.getInstruments().length);

        for (Instrument i : instruments)
        {
            System.out.println(  "Bank="    + i.getPatch().getBank() + 
                   " Patch="   + i.getPatch().getProgram() +
                   " Instr.="  + i);
        }
    }
}