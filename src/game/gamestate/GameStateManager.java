package game.gamestate;

import game.GamePanel;
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
}