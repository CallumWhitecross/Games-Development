//2718715
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import java.awt.*;


import game2D.*;

// Game demonstrates how we can override the GameCore class
// to create our own 'game'. We usually need to implement at
// least 'draw' and 'update' (not including any local event handling)
// to begin the process. You should also add code to the 'init'
// method that will initialise event handlers etc. By default GameCore
// will handle the 'Escape' key to quit the game but you should
// override this with your own event handler.

/**
 * @author David Cairns
 *
 */
@SuppressWarnings("serial")

public class Game extends GameCore 
{
	// Useful game constants
	static int screenWidth = 512;
	static int screenHeight = 384;
	
	int lives = 3;

    float 	lift = 0.0001f;
    float	gravity = 0.0003f;
    boolean gravityB = true;
    boolean falling = false;
    
    // Game state flags
    boolean jump = false;
    boolean rightWall = false;
    boolean leftWall = false;
    boolean roof = false;
    boolean hitDetection = false;
    boolean stun = false;
    long start = 0;
    

    // Game resources
    Animation idle;
    Animation run;
    Animation runL;
    Animation jumpA;
    Animation jumpL;
    
    Animation walkR;
    Animation walkL;
    Animation deadL;
    Animation deadR;
    
    Sprite	player = null;
    Sprite e = null;
    ArrayList<Sprite> enemy = new ArrayList<Sprite>();
    ArrayList<Sprite> bg = new ArrayList<Sprite>();

    TileMap tmap = new TileMap();	// Our tile map, note that we load it in init()
    
    long total;    
    
    // The score will be the total time elapsed since a crash
    

    /**
	 * The obligatory main method that creates
     * an instance of our class and starts it running
     * 
     * @param args	The list of parameters this program might use (ignored)
     */
    public static void main(String[] args) {

        Game gct = new Game();
        gct.init();
        // Start in windowed mode with the given screen height and width
        gct.run(false,screenWidth,screenHeight);
    }

    /**
     * Initialise the class, e.g. set up variables, load images,
     * create animations, register event handlers
     */
    public void init()
    {         
        Sprite s;	// Temporary reference to a sprite
        
        
        Sound bgSound = new Sound("sounds/bg.wav");
        bgSound.setBG();
        bgSound.start();
        // Load the tile map and print it out so we can check it is valid
        tmap.loadMap("maps", "map.txt");
        
        setSize(tmap.getPixelWidth() / 4, tmap.getPixelHeight());
        setVisible(true);

        // Create a set of background sprites that we can 
        // rearrange to give the illusion of motion
        
        idle = idleAnimation();
        run = runRAnimation();
        runL = runLAnimation();
        jumpA = jumpAnimation();
        jumpL = jumpLAnimation();
        
        walkR = walkRAnimation();
        walkL = walkLAnimation();
        deadL = deadLAnimation();
        deadR = deadRAnimation();
        
        // Initialise the player with an animation
        player = new Sprite(idle);
        
        e = new Sprite(walkR);
        
        // Load a single cloud animation
        Animation ca = new Animation();
        ca.addFrame(loadImage("images/tiles/BG.png"), 1000);
        
        // Create 3 clouds at random positions off the screen
        // to the right
        
        for (int i=-1; i<7;i++) {
        	s = new Sprite(ca);
        	s.setX(screenWidth * i * 2);
        	s.setY(0);
        	s.show();
        	bg.add(s);
        }
        	
        	//s = new Sprite(ca);
        	

        initialiseGame();
      		
        System.out.println(tmap);
    }

    
    public Animation idleAnimation()
    {
    	Image idle1 = loadImage("images/Animation/Idle/Idle__001.png");
        Image idle2 = loadImage("images/Animation/Idle/Idle__002.png");
        Image idle3 = loadImage("images/Animation/Idle/Idle__003.png");
        Image idle4 = loadImage("images/Animation/Idle/Idle__004.png");
        Image idle5 = loadImage("images/Animation/Idle/Idle__005.png");
        Image idle6 = loadImage("images/Animation/Idle/Idle__006.png");
        Image idle7 = loadImage("images/Animation/Idle/Idle__007.png");
        Image idle8 = loadImage("images/Animation/Idle/Idle__008.png");
        Image idle9 = loadImage("images/Animation/Idle/Idle__009.png");
        
        idle = new Animation();
        idle.addFrame(idle1, 100);
        idle.addFrame(idle2, 100);
        idle.addFrame(idle3, 100);
        idle.addFrame(idle4, 100);
        idle.addFrame(idle5, 100);
        idle.addFrame(idle6, 100);
        idle.addFrame(idle7, 100);
        idle.addFrame(idle8, 100);
        idle.addFrame(idle9, 100);
        
        return idle;
    }
    
    public Animation runRAnimation()
    {
    	Image run0 = loadImage("images/Animation/RunR/Run__000.png");
    	Image run1 = loadImage("images/Animation/RunR/Run__001.png");
        Image run2 = loadImage("images/Animation/RunR/Run__002.png");
        Image run3 = loadImage("images/Animation/RunR/Run__003.png");
        Image run4 = loadImage("images/Animation/RunR/Run__004.png");
        Image run5 = loadImage("images/Animation/RunR/Run__005.png");
        Image run6 = loadImage("images/Animation/RunR/Run__006.png");
        Image run7 = loadImage("images/Animation/RunR/Run__007.png");
        Image run8 = loadImage("images/Animation/RunR/Run__008.png");
        Image run9 = loadImage("images/Animation/RunR/Run__009.png");
        
        run = new Animation();
        run.addFrame(run0, 100);
        run.addFrame(run1, 100);
        run.addFrame(run2, 100);
        run.addFrame(run3, 100);
        run.addFrame(run4, 100);
        run.addFrame(run5, 100);
        run.addFrame(run6, 100);
        run.addFrame(run7, 100);
        run.addFrame(run8, 100);
        run.addFrame(run9, 100);
        
        return run;
    }
    
    public Animation runLAnimation()
    {
    	Image runL0 = loadImage("images/Animation/RunL/Run__000.png");
    	Image runL1 = loadImage("images/Animation/RunL/Run__001.png");
        Image runL2 = loadImage("images/Animation/RunL/Run__002.png");
        Image runL3 = loadImage("images/Animation/RunL/Run__003.png");
        Image runL4 = loadImage("images/Animation/RunL/Run__004.png");
        Image runL5 = loadImage("images/Animation/RunL/Run__005.png");
        Image runL6 = loadImage("images/Animation/RunL/Run__006.png");
        Image runL7 = loadImage("images/Animation/RunL/Run__007.png");
        Image runL8 = loadImage("images/Animation/RunL/Run__008.png");
        Image runL9 = loadImage("images/Animation/RunL/Run__009.png");
        
        runL = new Animation();
        runL.addFrame(runL0, 100);
        runL.addFrame(runL1, 100);
        runL.addFrame(runL2, 100);
        runL.addFrame(runL3, 100);
        runL.addFrame(runL4, 100);
        runL.addFrame(runL5, 100);
        runL.addFrame(runL6, 100);
        runL.addFrame(runL7, 100);
        runL.addFrame(runL8, 100);
        runL.addFrame(runL9, 100);
        
        return runL;
    }
    
    public Animation jumpAnimation()
    {
    	Image jump0 = loadImage("images/Animation/Jump/Jump__000.png");
    	Image jump1 = loadImage("images/Animation/Jump/Jump__001.png");
        Image jump2 = loadImage("images/Animation/Jump/Jump__002.png");
        Image jump3 = loadImage("images/Animation/Jump/Jump__003.png");
        Image jump4 = loadImage("images/Animation/Jump/Jump__004.png");
        Image jump5 = loadImage("images/Animation/Jump/Jump__005.png");
        Image jump6 = loadImage("images/Animation/Jump/Jump__006.png");
        Image jump7 = loadImage("images/Animation/Jump/Jump__007.png");
        Image jump8 = loadImage("images/Animation/Jump/Jump__008.png");
        Image jump9 = loadImage("images/Animation/Jump/Jump__009.png");
        
        jumpA = new Animation();
        jumpA.addFrame(jump0, 100);
        jumpA.addFrame(jump1, 100);
        jumpA.addFrame(jump2, 100);
        jumpA.addFrame(jump3, 100);
        jumpA.addFrame(jump4, 100);
        jumpA.addFrame(jump5, 100);
        jumpA.addFrame(jump6, 100);
        jumpA.addFrame(jump7, 100);
        jumpA.addFrame(jump8, 100);
        jumpA.addFrame(jump9, 100);
        
        return jumpA;
    }
    
    public Animation jumpLAnimation()
    {
    	Image jump0 = loadImage("images/Animation/JumpL/Jump__000.png");
    	Image jump1 = loadImage("images/Animation/JumpL/Jump__001.png");
        Image jump2 = loadImage("images/Animation/JumpL/Jump__002.png");
        Image jump3 = loadImage("images/Animation/JumpL/Jump__003.png");
        Image jump4 = loadImage("images/Animation/JumpL/Jump__004.png");
        Image jump5 = loadImage("images/Animation/JumpL/Jump__005.png");
        Image jump6 = loadImage("images/Animation/JumpL/Jump__006.png");
        Image jump7 = loadImage("images/Animation/JumpL/Jump__007.png");
        Image jump8 = loadImage("images/Animation/JumpL/Jump__008.png");
        Image jump9 = loadImage("images/Animation/JumpL/Jump__009.png");
        
        jumpL = new Animation();
        jumpL.addFrame(jump0, 100);
        jumpL.addFrame(jump1, 100);
        jumpL.addFrame(jump2, 100);
        jumpL.addFrame(jump3, 100);
        jumpL.addFrame(jump4, 100);
        jumpL.addFrame(jump5, 100);
        jumpL.addFrame(jump6, 100);
        jumpL.addFrame(jump7, 100);
        jumpL.addFrame(jump8, 100);
        jumpL.addFrame(jump9, 100);
        
        return jumpL;
    }
    
    public Animation walkRAnimation()
    {
    	Image walk1 = loadImage("images/Animation/Dinosaur/WalkR/Walk (1).png");
        Image walk2 = loadImage("images/Animation/Dinosaur/WalkR/Walk (2).png");
        Image walk3 = loadImage("images/Animation/Dinosaur/WalkR/Walk (3).png");
        Image walk4 = loadImage("images/Animation/Dinosaur/WalkR/Walk (4).png");
        Image walk5 = loadImage("images/Animation/Dinosaur/WalkR/Walk (5).png");
        Image walk6 = loadImage("images/Animation/Dinosaur/WalkR/Walk (6).png");
        Image walk7 = loadImage("images/Animation/Dinosaur/WalkR/Walk (7).png");
        Image walk8 = loadImage("images/Animation/Dinosaur/WalkR/Walk (8).png");
        Image walk9 = loadImage("images/Animation/Dinosaur/WalkR/Walk (9).png");
        Image walk10 = loadImage("images/Animation/Dinosaur/WalkR/Walk (10).png");
        
        walkR = new Animation();
        walkR.addFrame(walk1, 100);
        walkR.addFrame(walk2, 100);
        walkR.addFrame(walk3, 100);
        walkR.addFrame(walk4, 100);
        walkR.addFrame(walk5, 100);
        walkR.addFrame(walk6, 100);
        walkR.addFrame(walk7, 100);
        walkR.addFrame(walk8, 100);
        walkR.addFrame(walk9, 100);
        walkR.addFrame(walk10, 100);
        
        return walkR;
    }
    
    public Animation walkLAnimation()
    {
    	
    	Image walk1 = loadImage("images/Animation/Dinosaur/WalkL/Walk (1).png");
        Image walk2 = loadImage("images/Animation/Dinosaur/WalkL/Walk (2).png");
        Image walk3 = loadImage("images/Animation/Dinosaur/WalkL/Walk (3).png");
        Image walk4 = loadImage("images/Animation/Dinosaur/WalkL/Walk (4).png");
        Image walk5 = loadImage("images/Animation/Dinosaur/WalkL/Walk (5).png");
        Image walk6 = loadImage("images/Animation/Dinosaur/WalkL/Walk (6).png");
        Image walk7 = loadImage("images/Animation/Dinosaur/WalkL/Walk (7).png");
        Image walk8 = loadImage("images/Animation/Dinosaur/WalkL/Walk (8).png");
        Image walk9 = loadImage("images/Animation/Dinosaur/WalkL/Walk (9).png");
        Image walk10 = loadImage("images/Animation/Dinosaur/WalkL/Walk (10).png");
        
        walkL = new Animation();
        
        walkL.addFrame(walk1, 100);
        walkL.addFrame(walk2, 100);
        walkL.addFrame(walk3, 100);
        walkL.addFrame(walk4, 100);
        walkL.addFrame(walk5, 100);
        walkL.addFrame(walk6, 100);
        walkL.addFrame(walk7, 100);
        walkL.addFrame(walk8, 100);
        walkL.addFrame(walk9, 100);
        walkL.addFrame(walk10, 100);
        
        return walkL;
    }
    
    public Animation deadRAnimation()
    {
    	
    	Image dead1 = loadImage("images/Animation/Dinosaur/DeadR/Dead (1).png");
        Image dead2 = loadImage("images/Animation/Dinosaur/DeadR/Dead (2).png");
        Image dead3 = loadImage("images/Animation/Dinosaur/DeadR/Dead (3).png");
        Image dead4 = loadImage("images/Animation/Dinosaur/DeadR/Dead (4).png");
        Image dead5 = loadImage("images/Animation/Dinosaur/DeadR/Dead (5).png");
        Image dead6 = loadImage("images/Animation/Dinosaur/DeadR/Dead (6).png");
        Image dead7 = loadImage("images/Animation/Dinosaur/DeadR/Dead (7).png");
        Image dead8 = loadImage("images/Animation/Dinosaur/DeadR/Dead (8).png");
        
        deadR = new Animation();
        
        deadR.addFrame(dead1, 100);
        deadR.addFrame(dead2, 100);
        deadR.addFrame(dead3, 100);
        deadR.addFrame(dead4, 100);
        deadR.addFrame(dead5, 100);
        deadR.addFrame(dead6, 100);
        deadR.addFrame(dead7, 100);
        deadR.addFrame(dead8, 1000000000);
        
        return deadR;
    }
    
    public Animation deadLAnimation()
    {
    	
    	Image dead1 = loadImage("images/Animation/Dinosaur/DeadL/Dead (1).png");
        Image dead2 = loadImage("images/Animation/Dinosaur/DeadL/Dead (2).png");
        Image dead3 = loadImage("images/Animation/Dinosaur/DeadL/Dead (3).png");
        Image dead4 = loadImage("images/Animation/Dinosaur/DeadL/Dead (4).png");
        Image dead5 = loadImage("images/Animation/Dinosaur/DeadL/Dead (5).png");
        Image dead6 = loadImage("images/Animation/Dinosaur/DeadL/Dead (6).png");
        Image dead7 = loadImage("images/Animation/Dinosaur/DeadL/Dead (7).png");
        Image dead8 = loadImage("images/Animation/Dinosaur/DeadL/Dead (8).png");
        
        deadL = new Animation();
        
        deadL.addFrame(dead1, 100);
        deadL.addFrame(dead2, 100);
        deadL.addFrame(dead3, 100);
        deadL.addFrame(dead4, 100);
        deadL.addFrame(dead5, 100);
        deadL.addFrame(dead6, 100);
        deadL.addFrame(dead7, 100);
        deadL.addFrame(dead8, 1000000000);
        
        return deadL;
    }
    
    /**
     * You will probably want to put code to restart a game in
     * a separate method so that you can call it to restart
     * the game.
     */
    public void initialiseGame()
    {
    	total = 0;
    	
    	lives = 3;
    	      
        player.setX(64);
        player.setY(450);
        player.setVelocityX(0);
        player.setVelocityY(0);
        player.setAlive();
        player.show();
        
        e.setX(1040);
        e.setY(520);
        e.setVelocityX(0.06f);
        e.setVelocityY(0f);
        e.show();
        e.setAlive();
        enemy.add(e);
    }
    
    /**
     * Draw the current state of the game
     */
    public void draw(Graphics2D g)
    {    	
    	// Be careful about the order in which you draw objects - you
    	// should draw the background first, then work your way 'forward'

    	// First work out how much we need to shift the view 
    	// in order to see where the player is.
        int xo = (int) -player.getX() +500;
        int yo = 0;

        // If relative, adjust the offset so that
        // it is relative to the player

        // ...?
        
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // Apply offsets to sprites then draw them
        for (Sprite s: bg)
        {
        	s.setOffsets(xo,yo);
        	s.draw(g);
        }

       
        // Apply offsets to tile map and draw  it
        if (player.getX() < 550) xo = -50; 
        tmap.draw(g,xo,yo);  
        
        for (Sprite e: enemy)
        {
        	e.setOffsets(xo,yo);
        	e.draw(g);
        }
        
        // Apply offsets to player and draw 
        AffineTransform transform = new AffineTransform();
        transform.translate(Math.round(player.getX()),Math.round(player.getY()));
        transform.scale(1f, 1f);
        int width = player.getImage().getWidth(null);
        int height = player.getImage().getHeight(null);
        player.setOffsets(xo, yo);
        player.draw(g);
                
          
        
        // Show score and status information
        String msg = String.format("Lives: %d", lives);
        g.setColor(Color.darkGray);
        g.drawString(msg, getWidth() - 80, 50);
    }

    /**
     * Update any sprites and check for collisions
     * 
     * @param elapsed The elapsed time between this call and the previous call of elapsed
     */    
    public void update(long elapsed)
    {
    	//while (getPause()) {};
    	
    	if (isRestarting()) {
    		//stop();
    		main(null);
    	}
    	
    	if (lives == 0 && (!cheatOn())) {endGameScreen(); while (true) {}};
    	
    	total += elapsed;
    	player.update(elapsed);
    	//setBounds((int) player.getX(), 0, tmap.getPixelWidth()/4, tmap.getPixelHeight());
    	if (roof) player.setVelocityY(0f);
    	
    	if (gravityB == false && player.getVelocityX() == 0f) {
    		player.setAnimation(idle);
    	}
    	
        // Make adjustments to the speed of the sprite due to gravity
        if (gravityB == true) {
        	player.setVelocityY(player.getVelocityY()+(gravity*elapsed));
        	falling = true;
        	if (player.getVelocityX() >= 0) {player.setAnimation(jumpA);} else if (player.getVelocityX() < 0) {player.setAnimation(jumpL);}
        }
        else {
        	player.setVelocityY(0.0f);
        	falling = false;
        }
       	player.setAnimationSpeed(1.0f);
       	
       	if (jump == true && gravityB == false) 
       	{
       		player.setVelocityY(-0.3f);
       	} 
       	
       	//while (gravityB == false && player.getVelocityX() != 0) {
       		
      // 	}
       	
       	rightWall = false;
    	leftWall = false;
    	roof = false;
    	//hitDetection = false;
       	//if (jumped == true && gravityB == false) {player.setAnimation(idle); jumped = false;}
       	
       	for (Sprite s: bg) {
       		s.setVelocityX(-player.getVelocityX() / 8);
       		s.update(elapsed);
       	}
       	
       	for (Sprite e: enemy) {
       		boolean gravityC = checkTileCollision(e, tmap, elapsed);
       		//if (gravityC) e.setVelocityY(e.getVelocityY()+(gravity*elapsed));
       		//else {e.setVelocityY(0f);}
       		if (rightWall) {e.setVelocityX(-0.06f); e.setAnimation(walkL);}
       		if (leftWall) {e.setVelocityX(0.06f); e.setAnimation(walkR);}
       		e.update(elapsed);
       	}
       		
       	
        // Now update the sprites animation and position
        
        
        rightWall = false;
    	leftWall = false;
    	roof = false;
    	
    	for (Sprite e: enemy ) {
    		if (boundingBoxCollision(player, e) && e.isAlive()) {
    			if (player.getY() >= (e.getY()) - (e.getHeight() / 2)) {
    				lives = lives - 1;
    				stun = true;
    				start = System.currentTimeMillis();
    				player.setVelocityY(-0.03f);
	    			if(player.getX() > e.getX()) 
	    				{leftWall = true;
	    				player.setVelocityX(0.3f);
	    			} else { 
	    				rightWall = true;
	    				player.setVelocityX(-0.3f);
	    			}
    			} else {
    				e.setDead();
    				player.setVelocityY(-player.getVelocityY());
    				if (e.getVelocityX() >= 0) {
    					e.setAnimation(deadR);
    				} else {e.setAnimation(deadL);}
    				e.setVelocityX(0f);
    			}
    		}
    	}
    	
    	rightWall = false;
    	leftWall = false;
    	roof = false;
    	hitDetection = false;
    	
        // Then check for any collisions that may have occurred
        handleScreenEdge(player, tmap, elapsed);
        gravityB = checkTileCollision(player, tmap, elapsed);
        
        
    	
    }
    
    
    /**
     * Checks and handles collisions with the edge of the screen
     * 
     * @param s			The Sprite to check collisions for
     * @param tmap		The tile map to check 
     * @param elapsed	How much time has gone by since the last call
     */
    public void handleScreenEdge(Sprite s, TileMap tmap, long elapsed)
    {
    	// This method just checks if the sprite has gone off the bottom screen.
    	// Ideally you should use tile collision instead of this approach
    	
        if (s.getY() + s.getHeight() > tmap.getPixelHeight())
        {
        	if (!cheatOn()) {lives = 0;}
        	else {// Put the player back on the map 1 pixel above the bottom
            		s.setY(tmap.getPixelHeight() - s.getHeight() - 1); 
            	
            		// and make them bounce
            		s.setVelocityY(-s.getVelocityY());
            		}
        }
    }
    
    Sound w;
     
    /**
     * Override of the keyPressed event defined in GameCore to catch our
     * own events
     * 
     *  @param e The event that has been generated
     */
    public void keyPressed(KeyEvent e) 
    { 
    	int key = e.getKeyCode();
    	
    	//if (key == KeyEvent.VK_ESCAPE) setPause(); pauseScreen(); 
    	
        if (key == KeyEvent.VK_RIGHT && (!rightWall) && (!hitDetection)) 
        {
        	if (stun) {
        		stun = false;
    			while ((System.currentTimeMillis() - start) < 1500) {hitDetection = true;};
    			hitDetection = false;
        	} else {player.setVelocityX(0.2f); if (!falling) {player.setAnimation(run); 
        	//w = new Sound("sounds/walk - cut.wav");
       		//w.start();
       		/*try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
       		try {
       			Thread.sleep(100);
       		} catch (InterruptedException ex) {
       			return;
       		}
       		
       		try {
       			Thread.sleep(100);
       		} catch (InterruptedException ex) {
       			return;
       		}
       		
       		try {
       			Thread.sleep(100);
       		} catch (InterruptedException ex) {
       			return;
       		}
       		*/}}}
        
        if (key == KeyEvent.VK_LEFT && (!leftWall) && (!hitDetection)) 
        {
        	if (stun) {
        		stun = false;
    			while ((System.currentTimeMillis() - start) < 1500) {hitDetection = true;};
    			hitDetection = false;
        	} else {player.setVelocityX(-0.2f); if (!falling) {player.setAnimation(runL);}}}
        
        if (key == KeyEvent.VK_UP) {jump = true; if(player.getVelocityY() == 0) {Sound j = new Sound("sounds/jump.wav"); j.setFade(); j.start();}} 
    	   	
    	if (key == KeyEvent.VK_S)
    	{
    		// Example of playing a sound as a thread
    		Sound s = new Sound("sounds/caw.wav");
    		s.start();
    	}
    	
    	if (key == KeyEvent.VK_N) 
    	{
    		loadSecondMap();
    	}
    	
    	if (key == KeyEvent.VK_L)
    	{
    		winningScreen();
    	}
    	
    	if (key == KeyEvent.VK_S) {
    		for (Sprite s: enemy) {
    			s.setVelocityX(0f);
    		}
    	}
    }
    
    public void loadSecondMap()
    {
    	tmap.loadMap("maps", "map1.txt");
    	
    	total = 0;
    	
    	lives = 3;
    	      
        player.setX(64);
        player.setY(120);
        player.setVelocityX(0);
        player.setVelocityY(0);
        player.setAlive();
        player.show();
    	
    	for (Sprite e: enemy) {
    		e.setX(1800);
            e.setY(390);
            e.setVelocityX(+0.06f);
            e.setVelocityY(0f);
            e.show();
            e.setAlive();
    	}
    	
    }
    
    
    
    public boolean boundingBoxCollision(Sprite s1, Sprite s2)
    {
    	return ((s1.getX() + s1.getImage().getWidth(null) > s2.getX()) && (s1.getX() < (s2.getX() + s2.getImage().getWidth(null))) && ((s1.getY() + s1.getImage().getHeight(null) > s2.getY()) && (s1.getY() < s2.getY() + s2.getImage().getHeight(null))));  	
    }
    
    /**
     * Check and handles collisions with a tile map for the
     * given sprite 's'. Initial functionality is limited...
     * 
     * @param s			The Sprite to check collisions for
     * @param tmap		The tile map to check 
     */

    public boolean checkTileCollision(Sprite s, TileMap tmap, long elapsed)
    {
    	boolean br = false,tr = false,bl = false,tl = false, mr1 = false, mr2 = false, mb1 = false, mb2 = false, mt1 = false, mt2 = false, ml1 = false, ml2 = false;
    	boolean secret1 = false;
    	boolean secret2 = false;
    	// Take a note of a sprite's current position
    	float tr_x = s.getX();
    	float tr_y = s.getY();
    	
    	// Find out how wide and how tall a tile is
    	float tileWidth = tmap.getTileWidth();
    	float tileHeight = tmap.getTileHeight();
    	
    	float tilePWidth = tmap.getPixelWidth();
    	
    	// Divide the sprite’s x coordinate by the width of a tile, to get
    	// the number of tiles across the x axis that the sprite is positioned at 
    	int	xtile = (int)(tr_x / tileWidth);
    	// The same applies to the y coordinate
    	int ytile = (int)(tr_y / tileHeight);
    	
    	// What tile character is at the top left of the sprite s?
    	char ch = tmap.getTileChar(xtile, ytile);
    	
    	
    	if (ch != '.') // If it's not a dot (empty space), handle it
    	{
    		// Here we just stop the sprite. 
    		//s.stop();
    		tl = true;
    		// You should move the sprite to a position that is not colliding
    	}
    	
    	tr_x = s.getX();
    	tr_y = s.getY();
    	// We need to consider the other corners of the sprite
    	// The above looked at the top left position, let's look at the bottom left.
    	xtile = (int)(tr_x / tileWidth);
    	ytile = (int)((tr_y + s.getHeight()) / tileHeight);
    	ch = tmap.getTileChar(xtile, ytile);
    	
    	// If it's not empty space
     	if (ch != '.') 
    	{
     		//s.stop();
     		bl = true;
    		// Let's make the sprite bounce
    		//s.setVelocityY(-s.getVelocityY()); // Reverse velocity 
    	} if (ch == 's') {secret2 = true;}
     	tr_x = s.getX();
    	tr_y = s.getY();
     	//Top right
     	xtile = (int)((tr_x + s.getWidth()) / tileWidth);
    	ytile = (int)((tr_y )/ tileHeight);
    	ch = tmap.getTileChar(xtile, ytile);
    	
    	// If it's not empty space
     	if (ch != '.') 
    	{
     		tr = true;
    	}
     	tr_x = s.getX();
    	tr_y = s.getY();
     	//Bottom right
     	xtile = (int)((tr_x + s.getWidth()) / tileWidth);
    	ytile = (int)((tr_y + s.getHeight())/ tileHeight);
    	ch = tmap.getTileChar(xtile, ytile);
    	
    	// If it's not empty space
     	if (ch != '.') 
    	{
     		//s.stop();
     		br = true;
    	} if (ch == 's') {secret1 = true;}
     	tr_x = s.getX();
    	tr_y = s.getY();
     	xtile = (int)((tr_x + s.getWidth()) / tileWidth);
     	ytile = (int)(((tr_y + s.getHeight()) * (1/3)) / tileWidth);
     	ch = tmap.getTileChar(xtile,  ytile);
     	
     	if (ch != '.')
     	{
     		//s.stop();
     		mr1 = true;
     		
     	}
     	tr_x = s.getX();
    	tr_y = s.getY();
     	xtile = (int)((tr_x + s.getWidth()) / tileWidth);
     	ytile = (int)(((tr_y + s.getHeight()) * (2/3)) / tileWidth);
     	ch = tmap.getTileChar(xtile,  ytile);
     	
     	if (ch != '.')
     	{
     		//s.stop();
     		mr2 = true;
     		
     	}
     	tr_x = s.getX();
    	tr_y = s.getY();
     	xtile = (int)(((tr_x + s.getWidth()) * (1/3)) / tileWidth);
     	ytile = (int)((tr_y + s.getHeight()) / tileWidth);
     	ch = tmap.getTileChar(xtile,  ytile);
     	
     	if (ch != '.')
     	{
     		//s.stop();
     		mb1 = true;
     		
     	}
     	tr_x = s.getX();
    	tr_y = s.getY();
     	xtile = (int)(((tr_x + s.getWidth()) * (2/3)) / tileWidth);
     	ytile = (int)((tr_y + s.getHeight()) / tileWidth);
     	ch = tmap.getTileChar(xtile,  ytile);
     	
     	if (ch != '.')
     	{
     		//s.stop();
     		mb2 = true;
     		
     	}
     	tr_x = s.getX();
    	tr_y = s.getY();
     	xtile = (int)(((tr_x + s.getWidth()) * 1/3) / tileWidth);
     	ytile = (int)(tr_y / tileWidth);
     	ch = tmap.getTileChar(xtile,  ytile);
     	
     	if (ch != '.')
     	{
     		//s.stop();
     		mt1 = true;
     		
     	}
     	tr_x = s.getX();
    	tr_y = s.getY();
     	xtile = (int)(((tr_x + s.getWidth()) * 2/3) / tileWidth);
     	ytile = (int)(tr_y / tileWidth);
     	ch = tmap.getTileChar(xtile,  ytile);
     	
     	if (ch != '.')
     	{
     		//s.stop();
     		mt2 = true;
     		
     	}
     	tr_x = s.getX();
    	tr_y = s.getY();
     	xtile = (int)(tr_x / tileWidth);
     	ytile = (int)(((tr_y + s.getHeight()) * (1/3)) / tileWidth);
     	ch = tmap.getTileChar(xtile,  ytile);
     	
     	if (ch != '.')
     	{
     		//s.stop();
     		ml1 = true;
     		
     	}
     	tr_x = s.getX();
    	tr_y = s.getY();
     	xtile = (int)(tr_x / tileWidth);
     	ytile = (int)(((tr_y + s.getHeight()) * (2/3)) / tileWidth);
     	ch = tmap.getTileChar(xtile,  ytile);
     	
     	if (ch != '.')
     	{
     		//s.stop();
     		ml2 = true;
     		
     	}
     	//1
     	if (tl == false && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == false && br == true && mb2 == true && mb1 == true && bl == true && ml2 == false && ml1 == false) 
     	{
     		return false;
     	}
     	//2
     	else if (tl == false && mt1 == false && mt2 == false && tr == true && mr1 == true && mr2 == true && br == true && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		rightWall = true;
     		s.setVelocityX(0f);
     		return true;
     	}
     	//3
     	else if (tl == true && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == false && bl == true && ml2 == true && ml1 == true)
     	{
     		leftWall = true;
     		s.setVelocityX(0f);
     		return true;
     	}
     	//4
     	else if (tl == false && mt1 == false && mt2 == false && tr == true && mr1 == true && mr2 == true && br == true && mb2 == true && mb1 == true && bl == true && ml2 == false && ml1 == false)
     	{
     		rightWall = true;
     		s.setVelocityX(0f);
     		return false;
     	}
     	//5
     	else if (tl == true && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == false && br == true && mb2 == true && mb1 == true && bl == true && ml2 == true && ml1 == true)
     	{
     		leftWall = true;
     		s.setVelocityX(0f);
     		return false;
     	}
     	//6
     	else if (tl == true && mt1 == true && mt2 == true && tr == true && mr1 == true && mr2 == true && br == true && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		roof = true;
     		rightWall = true;
     		s.setVelocityX(0f);
     		s.setVelocityY(0f);
     		return true;
     	}
     	//7
     	else if (tl == true && mt1 == true && mt2 == true && tr == true && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == false && bl == true && ml2 == true && ml1 == true)
     	{
     		leftWall = true;
     		roof = true;
     		s.setVelocityX(0f);
     		s.setVelocityY(0f);
     		return true;
     	}
     	//8
     	else if (tl == false && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == false && br == true && mb2 == true && mb1 == true && bl == false && ml2 == false && ml1 == false)
     	{
     		return false;
     	}
     	//9
     	else if (tl == false && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == false && br == true && mb2 == true && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		return false;
     	}
     	//10
     	else if (tl == false && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == false && br == true && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		rightWall = true;
     		s.setVelocityX(0f);
     		return true;
     	}
     	//11
     	else if (tl == false && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == false && br == false && mb2 == true && mb1 == true && bl == true && ml2 == false && ml1 == false)
     	{
     		return false;
     	}
     	//12
     	else if (tl == false && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == true && bl == true && ml2 == false && ml1 == false)
     	{
     		return false;
     	}
     	//13
     	else if (tl == false && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == false && bl == true && ml2 == false && ml1 == false)
     	{
     		leftWall = true;
     		s.setVelocityX(0f);
     		return true;
     	}
     	//14
     	else if (tl == false && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == true && br == true && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		rightWall = true;
     		s.setVelocityX(0f);
     		return true;
     	}
     	//15
     	else if (tl == false && mt1 == false && mt2 == false && tr == false && mr1 == true && mr2 == true && br == true && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		rightWall = true;
     		s.setVelocityX(0f);
     		return true; 
     	}
     	//16
     	else if (tl == false && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == false && bl == true && ml2 == true && ml1 == false)
     	{
     		leftWall = true;
     		s.setVelocityX(0f);
     		return true;
     	}
     	//17
     	else if (tl == false && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == false && bl == true && ml2 == true && ml1 == true)
     	{
     		leftWall = true;
     		s.setVelocityX(0f);
     		return true;
     	}
     	//18
     	else if (tl == false && mt1 == false && mt2 == false && tr == true && mr1 == true && mr2 == true && br == false && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		rightWall = true;
     		s.setVelocityX(0f);
     		return true;
     	}
     	//19
     	else if (tl == false && mt1 == false && mt2 == false && tr == true && mr1 == true && mr2 == false && br == false && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		rightWall = true;
     		s.setVelocityX(0f);
     		return true;
     	}
     	//20
     	else if (tl == false && mt1 == false && mt2 == false && tr == true && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		rightWall = true;
     		s.setVelocityX(0f);
     		return true;
     	}
     	//21
     	else if (tl == true && mt1 == false && mt2 == false && tr == false && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		leftWall = true;
     		s.setVelocityX(0f);
     		return true;
     	}
     	//22
     	else if (tl == true && mt1 == true && mt2 == false && tr == false && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		roof = true;
     		s.setVelocityY(0f);
     		return true;
     	}
     	//23
     	else if (tl == true && mt1 == true && mt2 == true && tr == false && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		roof = true;
     		s.setVelocityY(0f);
     		return true;
     	}
     	//24
     	else if (tl == false && mt1 == false && mt2 == true && tr == true && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		roof = true;
     		s.setVelocityY(0f);
     		return true;
     	}
     	//25
     	else if (tl == false && mt1 == true && mt2 == true && tr == true && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		roof = true;
     		s.setVelocityY(0f);
     		return true;
     	}
     	//26
     	else if (tl == true && mt1 == true && mt2 == true && tr == true && mr1 == false && mr2 == false && br == false && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		roof = true;
     		s.setVelocityY(0f);
     		return true;
     	}
     	//27
     	else if (tl == false && mt1 == false && mt2 == false && tr == false && mr1 == true && mr2 == true && br == false && mb2 == false && mb1 == false && bl == false && ml2 == false && ml1 == false)
     	{
     		rightWall = true;
     		s.setVelocityX(0f);
     		return true;
     	}
     	else if (secret1 == true && secret2 == true)
     	{
     		loadSecondMap();
     	}
     	return true;
    }


	public void keyReleased(KeyEvent e) { 

		int key = e.getKeyCode();

		// Switch statement instead of lots of ifs...
		// Need to use break to prevent fall through.
		switch (key)
		{
			case KeyEvent.VK_ESCAPE : setPause(); pauseScreen(); break;
			case KeyEvent.VK_UP     : jump = false; break;
			case KeyEvent.VK_RIGHT	: if(!rightWall) {player.setVelocityX(player.getVelocityX()-0.2f); } else {player.setAnimation(idle);} if (!falling) {player.setAnimation(idle); /*w.interrupt(); */} break;
			case KeyEvent.VK_LEFT	: if(!leftWall) {player.setVelocityX(player.getVelocityX()+0.2f); } else {player.setAnimation(idle);} if (!falling) {player.setAnimation(idle);} break;
			default :  break;
		}
	}
}
