package game.entity;

import game.tilemap.*;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject {
	
	// player stuff
	private int health;
	private int maxHealth;
	private int fire;
	private int maxFire;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	
	// fireball
	private boolean firing;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;
	
	// scratch
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;
        
        private boolean running;
        private boolean shouldRun;
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		4, 6, 2, 2, 6
	};
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
        private static final int RUNNING = 4;
        
        private double maxWalkSpeed;
        private double maxRunSpeed;
        
        private double flyingSpeed;
	
	public Player(TileMap tm) {
		
            super(tm);

            width = 35;
            height = 69;
            cwidth = 35;
            cheight = 55;

            moveSpeed = 0.3;
            // 1.6
            maxWalkSpeed = 2.0;
            maxRunSpeed = 4.0;
            // 0.4
            stopSpeed = 0.4;
            fallSpeed = 0.15;
            maxFallSpeed = 4.0;
            jumpStart = -5.0;
            stopJumpSpeed = 0.3;

            facingRight = true;

            health = maxHealth = 5;
            fire = maxFire = 2500;

//            fireCost = 200;
//            fireBallDamage = 5;
//            fireBalls = new ArrayList<FireBall>();

//            scratchDamage = 8;
//            scratchRange = 40;

            // load sprites
            try {
                
                BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/resources/Sprites/Player/playerspritesalternate.png"));

                sprites = new ArrayList<BufferedImage[]>();
                for(int i = 0; i < numFrames.length; i++) {

                    BufferedImage[] bi = new BufferedImage[numFrames[i]];

                    for(int j = 0; j < numFrames[i]; j++) {

                        if(i == 0) {
                            bi[j] = spritesheet.getSubimage(j * 28, 70, 28, 69);
                        }
                        else if(i == 1) {
                            bi[j] = spritesheet.getSubimage(j * 35, 0, 35, 69);
                        }
                        else if(i == 2) {
                            bi[j] = spritesheet.getSubimage(j * 30, 139, 30, 69);
                        }
                        else if(i == 3) {
                            bi[j] = spritesheet.getSubimage(j * 38, 208, 37, 69);
                        }
                        else if(i == 4) {
                            bi[j] = spritesheet.getSubimage(j * 58, 276, 58, 69);
                        }
                        
                    }
                    
                    sprites.add(bi);
                }

            }
            catch(Exception e) {
                e.printStackTrace();
            }

            animation = new Animation();
            currentAction = FALLING;
            animation.setFrames(sprites.get(FALLING));
            animation.setDelay(200);
            
	}
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public int getFire() { return fire; }
	public int getMaxFire() { return maxFire; }
	
	public void setFiring() { 
            firing = true;
	}
	public void setScratching() {
            scratching = true;
	}
        public void setShouldRun(boolean b)
        {
            shouldRun = b;
        }
	
	private void getNextPosition() {
		
            // movement
            if(left) {
                if (shouldRun)
                {
                    dx -= moveSpeed;
                    if(dx < -maxRunSpeed) {
                        dx = -maxRunSpeed;
                    }
                }
                else
                {
                    dx -= moveSpeed;
                    if(dx < -maxWalkSpeed) {
                        dx = -maxWalkSpeed;
                    }
                }
                
            }
            else if(right) {
                if (shouldRun)
                {
                    dx += moveSpeed;
                    if(dx > maxRunSpeed) {
                        dx = maxRunSpeed;
                    }
                }
                else
                {
                    dx += moveSpeed;
                    if(dx > maxWalkSpeed) {
                        dx = maxWalkSpeed;
                    }
                }
            }
            else {
                if(dx > 0 && dy == 0) {
                    dx -= stopSpeed;
                    if(dx < 0) {
                        dx = 0;
                    }
                }
                else if(dx < 0 && dy == 0) {
                    dx += stopSpeed;
                    if(dx > 0) {
                        dx = 0;
                    }
                }
            }

            // jumping
            if(jumping && !falling) {
                dy = jumpStart;
                falling = true;	
            }

            // falling
            if(falling) {
                dy += fallSpeed;

                if(dy > 0) jumping = false;
                if(dy < 0 && !jumping) dy += stopJumpSpeed;

                if(dy > maxFallSpeed) dy = maxFallSpeed;
            }
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
                
//                if (currentAction == SCRATCHING)
//                {
//                    if (animation.hasPlayedOnce()) scratching = false;
//                }
//                else if (currentAction == FIREBALL)
//                {
//                    if (animation.hasPlayedOnce()) firing = false;
//                
//		
//                // fire attack
//                fire += 1;
//                if (fire > maxFire) fire = maxFire;
//                if (firing && currentAction != FIREBALL)
//                {
//                    if (fire > fireCost)
//                    {
//                        fire -= fireCost;
//                        FireBall fb = new FireBall(tileMap, facingRight);
//                        fb.setPosition(x, y);
//                        fireBalls.add(fb);
//                    }
//                }
//                
//                for (int i=0; i<fireBalls.size(); i++)
//                {
//                    fireBalls.get(i).update();
//                    if (fireBalls.get(i).shouldRemove())
//                    {
//                        fireBalls.remove(i);
//                        i--;
//                    }
//                }
                
		// set animation
//		if(scratching) {
//			if(currentAction != SCRATCHING) {
//				currentAction = SCRATCHING;
//				animation.setFrames(sprites.get(IDLE));
//				animation.setDelay(50);
//				width = 60;
//			}
//		}
//		else if(firing) {
//			if(currentAction != FIREBALL) {
//				currentAction = FIREBALL;
//				animation.setFrames(sprites.get(IDLE));
//				animation.setDelay(100);
//				width = 30;
//			}
//		}
		if (dy > 0) {
                    if(currentAction != FALLING) {
                            currentAction = FALLING;
                            animation.setFrames(sprites.get(FALLING));
                            animation.setDelay(200);
                            width = 38;
                            height = 69;
                    }
		}
		else if (dy < 0) {
                    if(currentAction != JUMPING) {
                            currentAction = JUMPING;
                            animation.setFrames(sprites.get(JUMPING));
                            animation.setDelay(300);
                            width = 28;
                            height = 69;
                    }
		}
		else if(left || right) {
                    if (shouldRun)
                    {
                        if (currentAction != RUNNING)
                        {
                            currentAction = RUNNING;
                            animation.setFrames(sprites.get(RUNNING));
                            animation.setDelay(100);
                            width = 58;
                            height = 69;
                        }
                    }
                    else if (currentAction != WALKING) {
                        currentAction = WALKING;
                        animation.setFrames(sprites.get(WALKING));
                        animation.setDelay(150);
                        width = 35;
                        height = 69;
                    }
		}
		else {
                    if(currentAction != IDLE) {
                            currentAction = IDLE;
                            animation.setFrames(sprites.get(IDLE));
                            animation.setDelay(200);
                            width = 28;
                            height = 69;
                    }
		}
		
		animation.update();
		
		// set direction
//		if(currentAction != SCRATCHING && currentAction != FIREBALL) {
                if(right) facingRight = true;
                if(left) facingRight = false;
//		}
		
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
                
                // draw fireballs
                
//                for (int i=0; i<fireBalls.size(); i++)
//                {
//                    fireBalls.get(i).draw(g);
//                }
		
		// draw player
		if(flinching) {
                    long elapsed =
                            (System.nanoTime() - flinchTimer) / 1000000;
                    if(elapsed / 100 % 2 == 0) {
                            return;
                    }
		}
		
		if(facingRight) {
			g.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				null
			);
		}
		else {
			g.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2 + width),
				(int)(y + ymap - height / 2),
				-width,
				height,
				null
			);
			
		}
		
	}
	
}

















