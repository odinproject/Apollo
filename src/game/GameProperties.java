package game;

import game.entity.Player;
import game.gamestate.GameState;
import game.gamestate.GameStateManager;
import game.gamestate.Level1State;

/**
 *
 * @author Martin
 */
public class GameProperties
{
    
    private GameStateManager gsm;
    private double playerSpeed;
    private double playerXPosition;
    
    public GameProperties(GameStateManager gsm)
    {
        this.gsm = gsm;
        playerSpeed = 0.0;
    }
    
    public double getPlayerSpeed()
    {
        return playerSpeed;
    }
    
    public double getPlayerXPosition()
    {
        return playerXPosition;
    }
    
    // updates all the properties of the game we care about
    public void update()
    {
        // update the player speed property. For now, this is the only property we care about
        playerSpeed = gsm.getPlayerSpeed();
        playerXPosition = gsm.getPlayerXPosition();
    }
    
}
