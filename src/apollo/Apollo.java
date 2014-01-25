/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.sound.midi.*;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 *
 * @author Martin
 */
public class Apollo {

    private static Synthesizer synth;
    private static MidiChannel[] mc;
    private static int x;
    private static ApolloUI ui;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hello Worlds!");
        
        ui = new ApolloUI();
        ui.setVisible(true);
        
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            mc = synth.getChannels();
            Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
            synth.loadInstrument(instr[90]);
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(Apollo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Timer uploadCheckerTimer = new Timer(true);
        uploadCheckerTimer.scheduleAtFixedRate(
        new TimerTask() {
          public void run() {
              mc[5].noteOn(60+ui.musicValue,800);
          }}, 0, 800);
    }
}
