/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apollo;

import apollo.composer.Composer;
import apollo.orchestra.Orchestra;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.midi.Instrument;
import javax.sound.midi.Soundbank;
import game.GamePanel;
import javax.swing.JFrame;

/**
 * I get the feeling this class is doomed to be messy, at least for now. So be it.
 * @author Martin
 */
public class ApolloPlayer 
{   
    private GamePanel game;
    
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
        composer.setGameProperties(game.getProperties());
    }
    
    public void play() 
    {   
        orchestra.play();
        Timer uploadCheckerTimer = new Timer(true);

        uploadCheckerTimer.scheduleAtFixedRate(
            new TimerTask() 
            {
                @Override
                public void run() 
                {
                    composer.update();
                }
            }, 0, 100);
    }
}