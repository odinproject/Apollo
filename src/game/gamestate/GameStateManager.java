package game.gamestate;

import game.GamePanel;
import game.GameProperties;
import java.util.ArrayList;

public class GameStateManager
{
    private ArrayList<GameState> gameStates;
    public int currentState;

    private GamePanel panel;

    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;

    public GameStateManager(GamePanel panel)
    {
        this.panel = panel;
        gameStates = new ArrayList<GameState>();

        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new Level1State(this));
    }

    public void setState(int state)
    {
        currentState = state;
        gameStates.get(currentState).init();
        if (currentState == 1)
        {
            // entered the game, play music
            panel.startMusic();
        }
    }

    public void update()
    {
        gameStates.get(currentState).update();
    }

    public void draw(java.awt.Graphics2D g)
    {
        gameStates.get(currentState).draw(g);
    }

    public void keyPressed(int k)
    {
        gameStates.get(currentState).keyPressed(k);
    }

    public void keyReleased(int k)
    {
        gameStates.get(currentState).keyReleased(k);
    }
    
    public double getPlayerSpeed()
    {
        if (currentState == 1)
        {
            return ((Level1State)gameStates.get(currentState)).getPlayer().getDX();
        }
        else
        {
            return 0.0;
        }
    }
    
    public double getPlayerXPosition()
    {
        if (currentState == 1)
        {
            return ((Level1State)gameStates.get(currentState)).getPlayer().getXTemp();
        }
        else
        {
            return 100.0;
        }
    }
    
    public GameProperties getProperties()
    {
        return panel.getProperties();
    }
}