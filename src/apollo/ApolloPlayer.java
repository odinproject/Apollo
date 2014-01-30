/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo;

import com.sun.media.sound.SF2Soundbank;
import com.sun.media.sound.*;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

/**
 *
 * @author Martin
 */
public class ApolloPlayer {
    
    private Synthesizer synth;
    private Soundbank soundbank;
    private MidiChannel[] mc;
    private int x;
    private ApolloUI ui;
    private Instrument[] instruments;
    private int currentNote;
    
    public ApolloPlayer()
    {
        
    }
    
    public void play() {
        
        // TODO code application logic here
        System.out.println("Hello Worlds!");
        
        ui = new ApolloUI();
        ui.setVisible(true);
        
        try {
            
            
            /*
             *	We need a synthesizer to play the note on.
             *	Here, we simply request the default
             *	synthesizer.
             */
            synth = MidiSystem.getSynthesizer();

            /*
             *	Of course, we have to open the synthesizer to
             *	produce any sound for us.
             */
            synth.open();
//            File file = new File("soundbank-deluxe.gm");
            File file = new File("OrchestraRhythm.sf2");
//            File file = new File("FlamencoDrums.sf2");
            soundbank = MidiSystem.getSoundbank(file);
//            soundbank = synth.getDefaultSoundbank();
            mc = synth.getChannels();
            instruments = soundbank.getInstruments();
            
            printInstruments(soundbank, instruments);
            
            System.out.println(soundbank.getName());
            
            if (soundbank != null)
            {
                boolean bInstrumentsLoaded = synth.loadAllInstruments(soundbank);
            }
            
            int inst = 18;
            
            mc[0].programChange(inst);
//            IOException | InvalidMidiDataException | 
        } catch (IOException | InvalidMidiDataException | MidiUnavailableException ex) {
            Logger.getLogger(Apollo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Timer uploadCheckerTimer = new Timer(true);
        uploadCheckerTimer.scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {
              mc[0].noteOff(currentNote);
              currentNote = 60+ui.musicValue;
              mc[0].noteOn(60+ui.musicValue,800);
              
          }}, 0, 800);
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
