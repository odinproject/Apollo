package game.entity;

import game.tilemap.*;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject {
    
    // 
    private boolean movingRight;
    private boolean movingLeft;
	
    // player stuff
    private int health;
    private int maxHealth;
    private int fire;
    private int maxFire;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;
    
    private int playerDirection;
    private int spriteDirection;

    private boolean running;
    private boolean shouldRun;

    // animations
    private ArrayList<BufferedImage[]> sprites;

    // animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int RUNNING = 2;
    private static final int JUMPING = 3;
    private static final int FALLING = 4;
    
    
    private static final int FACING_LEFT = 10;
    private static final int FACING_RIGHT = 11;

    private double maxWalkSpeed;
    private double maxRunSpeed;

    private double flyingSpeed;

    public Player(TileMap tm)
    {
        super(tm);

        width = 35;
        height = 62;
        cwidth = 35;
        cheight = 62;

        moveSpeed = 0.2;
        // 1.6
        maxWalkSpeed = 1.5;
        maxRunSpeed = 4.0;
        // 0.4
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -5.0;
        stopJumpSpeed = 0.3;

        playerDirection = FACING_RIGHT;

        // load sprites
        try
        {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/resources/Sprites/Player/test.png"));

            sprites = new ArrayList<BufferedImage[]>();
            // IDLE (right) 0
            sprites.add(getSpriteStreamForAnimation(spritesheet, 6, 0, 0, 27, 62));
            // IDLE (left) 1
            sprites.add(getSpriteStreamForAnimation(spritesheet, 6, 162, 0, 27, 62));
            // WALKING (right) 2
            sprites.add(getSpriteStreamForAnimation(spritesheet, 6, 0, 62, 33, 62));
            // WALKING (left) 3
            sprites.add(getSpriteStreamForAnimation(spritesheet, 6, 197, 62, 33, 62));
            // RUNNING (right) 4
            sprites.add(getSpriteStreamForAnimation(spritesheet, 6, 0, 125, 53, 52));
            // RUNNING (left) 5
            sprites.add(getSpriteStreamForAnimation(spritesheet, 6, 0, 178, 54, 53));
            // JUMPING (right) 6
            sprites.add(getSpriteStreamForAnimation(spritesheet, 2, 324, 0, 30, 62));
            // JUMPING (left) 7
            sprites.add(getSpriteStreamForAnimation(spritesheet, 2, 384, 0, 30, 62));
            // FALLING (right) 8
            sprites.add(getSpriteStreamForAnimation(spritesheet, 2, 318, 126, 35, 56));
            // FALLING (left) 9
            sprites.add(getSpriteStreamForAnimation(spritesheet, 2, 395, 62, 36, 62));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        animation = new Animation();
        changeSpriteToAction(FALLING);
    }
    
    private void changeSpriteToAction(int action)
    {
        int spriteIndex = 0;
        
        boolean changeDir = (playerDirection != spriteDirection);
        
        switch (action)
        {
            case (IDLE):
                if (currentAction != IDLE || changeDir)
                {
                    spriteIndex = (playerDirection == FACING_RIGHT) ? 0 : 1;
                    spriteDirection = playerDirection;
                    currentAction = IDLE;
                    animation.setFrames(sprites.get(spriteIndex));
                    animation.setDelay(200);
                    cwidth = width = 27;
                    height = 62;
                }
                break;
            case (WALKING):
                if (currentAction != WALKING || changeDir)
                {
                    spriteIndex = (playerDirection == FACING_RIGHT) ? 2 : 3;
                    spriteDirection = playerDirection;
                    currentAction = WALKING;
                    animation.setFrames(sprites.get(spriteIndex));
                    animation.setDelay(150);
                    cwidth = width = 33;
                    height = 62;
                }
                break;
            case (RUNNING):
                if (currentAction != RUNNING || changeDir)
                {
                    spriteIndex = (playerDirection == FACING_RIGHT) ? 4 : 5;
                    spriteDirection = playerDirection;
                    currentAction = RUNNING;
                    animation.setFrames(sprites.get(spriteIndex));
                    animation.setDelay(100);
                    cwidth = width = 53;
                    height = 53;
                }
                break;
            case (JUMPING):
                if (currentAction != JUMPING || changeDir)
                {
                    spriteIndex = (playerDirection == FACING_RIGHT) ? 6 : 7;
                    spriteDirection = playerDirection;
                    currentAction = JUMPING;
                    animation.setFrames(sprites.get(spriteIndex));
                    animation.setDelay(200);
                    cwidth = width = 30;
                    height = 62;
                }
                break;
            case (FALLING):
                if (currentAction != FALLING || changeDir)
                {
                    spriteIndex = (playerDirection == FACING_RIGHT) ? 8 : 9;
                    spriteDirection = playerDirection;
                    currentAction = FALLING;
                    animation.setFrames(sprites.get(spriteIndex));
                    animation.setDelay(200);
                    cwidth = width = 35;
                    height = 60;
                }
                break;
            default:
                spriteIndex = (playerDirection == FACING_RIGHT) ? 0 : 1;
                spriteDirection = playerDirection;
                currentAction = IDLE;
                animation.setFrames(sprites.get(spriteIndex));
                animation.setDelay(200);
                cwidth = width = 27;
                height = 62;
                break;
        }
    }
    
    /**
     * @param spriteSheet the entire spritesheet
     * @param frames how many frames are in this animation
     * @param startX the starting pixel x position of this animation within the original spritesheet
     * @param startY the starting pixel y position of this animation within the original spritesheet
     * @param spriteWidth the width of each frame in this animation
     * @param spriteHeight the height of each frame in this animation
     * @return
     */
    private BufferedImage[] getSpriteStreamForAnimation(BufferedImage spriteSheet, int frames, int startX, int startY, int spriteWidth, int spriteHeight)
    {
        BufferedImage[] spriteStream = new BufferedImage[frames];
        for(int i=0; i < frames; i++)
        {
            spriteStream[i] = spriteSheet.getSubimage((i * spriteWidth)+startX, startY, spriteWidth, spriteHeight);
        }
        
        return spriteStream;
    }

    public void setShouldRun(boolean b) { shouldRun = b; }
    // the left key has been pressed
    public void setLeft(boolean b)
    {
        movingLeft = b;
        if (b) playerDirection = FACING_LEFT;
        if (b) movingRight = false;
    }
    
    public void setRight(boolean b)
    {
        movingRight = b;
        if (b) playerDirection = FACING_RIGHT;
        if (b) movingLeft = false;
    }

    private void getNextPosition()
    {
        // movement
        if (movingLeft && movingRight)
        {
            dx = 0;
        }
        else if(movingLeft)
        {   
            if (shouldRun)
            {
                dx -= moveSpeed;
                if(dx < -maxRunSpeed)
                {
                    dx = -maxRunSpeed;
                }
            }
            else
            {
                dx -= moveSpeed;
                if(dx < -maxWalkSpeed)
                {
                    dx = -maxWalkSpeed;
                }
            }

        }
        else if(movingRight)
        {
            if (shouldRun)
            {
                dx += moveSpeed;
                if(dx > maxRunSpeed)
                {
                    dx = maxRunSpeed;
                }
            }
            else
            {
                dx += moveSpeed;
                if(dx > maxWalkSpeed)
                {
                    dx = maxWalkSpeed;
                }
            }
        }
        else
        {
            if(dx > 0 && dy == 0)
            {
                dx -= stopSpeed;
                if(dx < 0)
                {
                    dx = 0;
                }
            }
            else if(dx < 0 && dy == 0)
            {
                dx += stopSpeed;
                if(dx > 0)
                {
                    dx = 0;
                }
            }
        }

        // jumping
        if(jumping && !falling)
        {
            dy = jumpStart;
            falling = true;	
        }

        // falling
        if(falling)
        {
            dy += fallSpeed;

            if(dy > 0) jumping = false;
            if(dy < 0 && !jumping) dy += stopJumpSpeed;
            if(dy > maxFallSpeed) dy = maxFallSpeed;
        }
    }
    
    public double getDX()
    {
        return dx;
    }

    public void update()
    {
        
        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if (dy > 0)
        {
            changeSpriteToAction(FALLING);
        }
        else if (dy < 0)
        {
            changeSpriteToAction(JUMPING);
        }
        else if(movingLeft || movingRight)
        {
            if (shouldRun)
            {
                changeSpriteToAction(RUNNING);
            }
            else
            {
                changeSpriteToAction(WALKING);
            }
        }
        else
        {
            changeSpriteToAction(IDLE);
        }

        animation.update();
    }

    public void draw(Graphics2D g)
    {
        setMapPosition();
        
        // draw player
        if(flinching)
        {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed / 100 % 2 == 0) return;
        }
        
        g.drawImage(animation.getImage(), 
                (int)(x + xmap - width / 2), 
                (int)(y + ymap - height / 2), 
                null);
    }	
}