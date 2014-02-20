package game;

import game.entity.Player;
import game.gamestate.GameState;
import game.gamestate.Level1State;

/**
 *
 * @author Martin
 */
public class GameProperties
{
    
    private Level1State state;
    private double playerSpeed;
    
    public GameProperties(Level1State state)
    {
        this.state = state;
        playerSpeed = 0.0;
    }
    
    public double getPlayerSpeed()
    {
        return playerSpeed;
    }
    
    // updates all the properties of the game we care about
    public void update()
    {
        // update the player speed property. For now, this is the only property we care about
        Player p = state.getPlayer();
        if (p != null)
        {
            playerSpeed = p.getDX();
        }
    }
    
}
