/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo;

import apollo.chordbase.Chord;
import apollo.chordbase.ChordDatabase;
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
    private ApolloUI ui;
    private Instrument[] instruments;
    private int currentNote;
    private Chord currentChord;
    private ChordDatabase chordDatabase = new ChordDatabase();
    int tick = 0;
    
    public ApolloPlayer()
    {
        
    }
    
    public void play() 
    {
        
        // Create the interface
        ui = new ApolloUI();
        ui.setVisible(true);
        
        try 
        {
            
            // initialize the audio player
            synth = MidiSystem.getSynthesizer();
            synth.open();
            
            File file = new File("OrchestraRhythm.sf2");
            //soundbank = MidiSystem.getSoundbank(file); //Uncomment this line to get the "orchestra hits" back.
            soundbank = synth.getDefaultSoundbank();
            midiChannel = synth.getChannels();
            
            instruments = soundbank.getInstruments();
            printInstruments(soundbank, instruments);
            
            System.out.println(soundbank.getName());
            
            if (soundbank != null)
            {
                boolean bInstrumentsLoaded = synth.loadAllInstruments(soundbank);
            }
            
            int chordInstrument = 51;
            int melodyInstrument = 0;
            
            midiChannel[0].programChange(chordInstrument);
            midiChannel[1].programChange(melodyInstrument);
        } 
        catch (/*IOException | InvalidMidiDataException | */MidiUnavailableException ex) 
        {
            Logger.getLogger(Apollo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Timer uploadCheckerTimer = new Timer(true);
        currentChord = chordDatabase.getChordByNumber(0);
        
        
        uploadCheckerTimer.scheduleAtFixedRate(
            new TimerTask() 
            {
                @Override
                public void run() 
                {
                    MusicFuncs.turnOffMelodyNote(midiChannel[1], currentChord, tick);
                    tick++;
                    if (tick%8==0)
                    {
                        MusicFuncs.turnOffGaussianChord(midiChannel[0], currentChord);
                        selectNewChord();
                        MusicFuncs.playGaussianChord(midiChannel[0], currentChord, .5);
                        tick = 0;
                    }
                    MusicFuncs.playMelodyNote(midiChannel[1], currentChord, tick, 1);
                }

            private void selectNewChord() 
            {
                //There are two lines below. They represent different ways of selecting chords.
                
                //Select based on the current chord number in the GUI.
                //currentChord = chordDatabase.getChordByNumber(ui.musicValue);
                
                //Have the computer select a chord. See the method for further details.
                currentChord = chordDatabase.getNextChord(ui.musicValue);
            }
            
            }, 0, 200);
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
