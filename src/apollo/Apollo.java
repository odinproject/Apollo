/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo;

import java.io.IOException;
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
    
    public static void main(String[] args) {
        ApolloPlayer player = new ApolloPlayer();
    }
}
