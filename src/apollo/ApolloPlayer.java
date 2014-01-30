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

//        PITCH C       C#      D       D#      E       F       F#      G       G#      A       A#      B
// OCTAVE       
// 0            0	1	2	3	4	5	6	7	8	9	10	11
// 1            12	13	14	15	16	17	18	19	20	21	22	23
// 2            24	25	26	27	28	29	30	31	32	33	34	35
// 3            36	37	38	39	40	41	42	43	44	45	46	47
// 4            48	49	50	51	52	53	54	55	56	57	58	59
// 5            60	61	62	63	64	65	66	67	68	69	70	71
// 6            72	73	74	75	76	77	78	79	80	81	82	83
// 7            84	85	86	87	88	89	90	91	92	93	94	95
// 8            96	97	98	99	100	101	102	103	104	105	106	107
// 9            108	109	110	111	112	113	114	115	116	117	118	119
// 10           120	121	122	123	124	125	126	127
//        PITCH C       C#      D       D#      E       F       F#      G       G#      A       A#      B

/**
 *
 * @author Martin
 */
public class ApolloPlayer {
    
    
    // midi player information
    private Synthesizer synth;
    private Soundbank soundbank;
    private MidiChannel[] mc;
    private int x;
    private ApolloUI ui;
    private Instrument[] instruments;
    private int currentNote;
    
    // A score is composed of up to 16 tracks
    private Track[] score;
    
    public ApolloPlayer()
    {
        
    }
    
    public void play() {
        
        // Create the interface
        ui = new ApolloUI();
        ui.setVisible(true);
        
        try {
            
            // initialize the audio player
            synth = MidiSystem.getSynthesizer();
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
