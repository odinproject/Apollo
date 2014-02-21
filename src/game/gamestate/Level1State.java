package game.gamestate;

import game.GamePanel;
import game.GameProperties;
import game.tilemap.*;
import game.entity.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Level1State extends GameState {
	
    private TileMap tileMap;
    private Background bg;

    private Player player;
    private GameProperties properties;

    public Level1State(GameStateManager gsm)
    {
        this.gsm = gsm;
        init();
    }

    public void init()
    {
        tileMap = new TileMap(16);
        tileMap.loadTiles("/resources/Tilesets/terrain.png");
        tileMap.loadMap("/resources/Maps/test.map");
        tileMap.setPosition(0, 0);

        bg = new Background("/resources/Backgrounds/forestbg.jpg", 0.1);

        player = new Player(tileMap);
        player.setPosition(100, 100);
        
        properties = new GameProperties(this);
    }
    
    public Player getPlayer()
    {
        return this.player;
    }


    public void update()
    {
        // update player
        player.update();
        tileMap.setPosition(
            GamePanel.WIDTH / 2 - player.getx(),
            GamePanel.HEIGHT / 2 - player.gety()
        );
        properties.update();
    }

    public void draw(Graphics2D g)
    {
        g.clearRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        // draw bg
        bg.draw(g);

        // draw tilemap
        tileMap.draw(g);

        // draw player
        player.draw(g);
    }

    // WARNING! KeyPressed ONLY recognizes the most recent press (if there are multiple)
    public void keyPressed(int k)
    {
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_SHIFT) player.setShouldRun(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_W) player.setJumping(true);
    }

    public void keyReleased(int k)
    {
        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_SHIFT) player.setShouldRun(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_W) player.setJumping(false);
    }	
}