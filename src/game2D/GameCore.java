package game2D;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;


/**
 * @author David Cairns
 * 
 * Core Game class that implements default game loop. Subclasses should
 * implement the draw() method and override the update method.
*/
public abstract class GameCore extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	protected static final int FONT_SIZE = 12;
    
    private boolean stop;			// true if the game loop should continue
    private	long startTime;				// The time the game started
    private long currTime;				// The current time
    private long elapsedTime;			// Elapsed time since previous check
    
    private long frames;				// Used to calculate frames per second (FPS)
    
    private BufferedImage buffer=null;	// buffer is used as a buffered image for drawing offscreen
    private Graphics2D 	  bg=null;    		// The virtual Graphics2D device associated with the above image
    
    
    boolean pause = false;
    boolean cheat = false;
    boolean restart = false;
    
    
    /**
     * Default constructor for GameCore
     *
     */
    public GameCore()
    {
    	stop = false;
    	
        frames = 1;
        startTime = 1;
        currTime = 1;
    }

    public void endGameScreen() {
    	JFrame frame = new JFrame("Game Over");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	JLabel textLabel = new JLabel("You have died. Game Over.", SwingConstants.CENTER);
    	textLabel.setPreferredSize(new Dimension(300,100));
    	frame.getContentPane().add(textLabel, BorderLayout.CENTER);
    	
    	frame.setLocationRelativeTo(null);
    	frame.pack();
    	frame.setVisible(true);
    	
    	JPanel panel = new JPanel();
    	frame.add(panel);
    	
    	JButton restartButton = new JButton("Restart");
    	restartButton.setVisible(true);
    	restartButton.setSize(100,100);
    	panel.add(restartButton);
    	
    	restartButton.addActionListener(e -> {
    		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    		restart = true;
    		pause = false;
    		
    	});
    	
    	JButton exitButton = new JButton("Exit");
    	exitButton.setVisible(true);
    	exitButton.setSize(100,100);
    	panel.add(exitButton);
    	
    	exitButton.addActionListener(e -> {
    		System.exit(0);
    	});
    }
    
    public void winningScreen() {
    	JFrame frame = new JFrame("Winner!");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	JLabel textLabel = new JLabel("Congratulations on beating the game! You can restart if you wish to play again or exit.", SwingConstants.CENTER);
    	textLabel.setPreferredSize(new Dimension(500,100));
    	frame.getContentPane().add(textLabel, BorderLayout.CENTER);
    	
    	frame.setLocationRelativeTo(null);
    	frame.pack();
    	frame.setVisible(true);
    	
    	JPanel panel = new JPanel();
    	frame.add(panel);
    	
    	JButton restartButton = new JButton("Restart");
    	restartButton.setVisible(true);
    	restartButton.setSize(100,100);
    	panel.add(restartButton);
    	
    	restartButton.addActionListener(e -> {
    		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    		restart = true;
    		pause = false;
    		
    	});
    	
    	JButton exitButton = new JButton("Exit");
    	exitButton.setVisible(true);
    	exitButton.setSize(100,100);
    	panel.add(exitButton);
    	
    	exitButton.addActionListener(e -> {
    		System.exit(0);
    	});
    }

    public void pauseScreen() {
    	JFrame pauseFrame = new JFrame("Pause Menu");
    	pauseFrame.setSize(300,100);
    	pauseFrame.setLocationRelativeTo(null);
    	pauseFrame.setVisible(true);
    	
    	
    	JPanel panel = new JPanel();
    	pauseFrame.add(panel);
    	
    	JButton resumeButton = new JButton("Resume");
    	resumeButton.setVisible(true);
    	resumeButton.setSize(100,100);
    	panel.add(resumeButton);
    	
    	//resumeButton.addActionListener(this);
    	
    	resumeButton.addActionListener(e -> {
    		
    		pauseFrame.dispatchEvent(new WindowEvent(pauseFrame, WindowEvent.WINDOW_CLOSING));
    		pause = false;
    	});
    	
    	JButton restartButton = new JButton("Restart");
    	restartButton.setVisible(true);
    	restartButton.setSize(100,100);
    	panel.add(restartButton);
    	
    	restartButton.addActionListener(e -> {
    		pauseFrame.dispatchEvent(new WindowEvent(pauseFrame, WindowEvent.WINDOW_CLOSING));
    		restart = true;
    		pause = false;
    		
    	});
    	
    	JButton cheatButton = new JButton("Activate Cheat Mode");
    	cheatButton.setVisible(true);
    	cheatButton.setSize(100,100);
    	panel.add(cheatButton);
    	
    	cheatButton.addActionListener(e -> {
    		cheat = true;
    	});
    	
    	JButton exitButton = new JButton("Exit");
    	exitButton.setVisible(true);
    	exitButton.setSize(100,100);
    	panel.add(exitButton);
    	
    	exitButton.addActionListener(e -> {
    		System.exit(0);
    	});
    }
    
    
    public boolean getPause() {
    	return pause;
    }
    
    public void setPause() {
    	pause = true;
    }
    
    public boolean isRestarting() {
    	return restart;
    }
    
    public boolean cheatOn()
    {
    	return cheat;
    }
    
    /** 
     * Signals the game loop that it's time to quit 
     * 
     */
    public void stop() { stop = true; }


    /** 
     * Starts the game by first initialising the game via init()
     * and then calling the gameLoop()
     *
     * @param full True to set to fullscreen mode, false otherwise
     * @param x Width of screen in pixels
     * @param y Height of screen in pixels
     */
    public void run(boolean full, int x, int y) {
        try 
        {
            init(full,x,y);
            gameLoop();
        }
        finally 
		{ 
        	
        }
    }


    /**
     * Internal initialisation method.
     * 
     * @param full	True to start the game in full screen mode
     * @param xres	Width in pixels of game screen
     * @param yres	Height in pixels of game screen
     */
    private void init(boolean full, int xres, int yres) {
    	

        setVisible(true);
        
        addKeyListener(this);
        setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
    }
    

    /**
     * Loads an image with the given 'fileName'
     * 
     * @param fileName The file path to the image file that should be loaded 
     * @return A reference to the Image object that was loaded
     */
    public Image loadImage(String fileName) 
    { 
    	return new ImageIcon(fileName).getImage(); 
    }

    /**
     *  Runs through the game loop until stop() is called. 
     *  
     *  This method will call your update() method followed by your draw()
     *  method to display the updated game state. It implements double buffering
     *  for both full screen and windowed mode.
     */
    public void gameLoop() {
        startTime = System.currentTimeMillis();
        currTime = startTime;
        frames = 1;		// Keep a note of frames for performance measure

        Graphics2D g;
        stop = false;
        
        // Create our own buffer
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        bg = (Graphics2D)buffer.createGraphics();
        bg.setClip(0, 0, getWidth(), getHeight());
        
        while (!stop) {
            elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;

            // Call the overridden update method
            update(elapsedTime);

	        // Get the current graphics device 	            
            g = (Graphics2D)getGraphics();
            
            
	        if (g != null)
	        {
	            	draw(bg);
	            	g.drawImage(buffer,null,0,0);
            }
            
            frames++;

            // take a nap
            try { Thread.sleep(10); } catch (InterruptedException ex) { }
        }
        System.exit(0);
    }
    
    /**
     * @return The current frames per second (FPS)
     */
    public float getFPS()
    {
    	if (currTime - startTime <= 0) return 0.0f;
    	return (float)frames/((currTime - startTime)/1000.0f);
    }

    /**
     * Handles the keyReleased event to check for the 'Escape' key being
     * pressed. If you override this method, make sure you allow the user 
     * to stop the game.
     */
	public void keyReleased(KeyEvent e) 
	{ 
	}

	/**
	 * Handler for the keyPressed event (empty)
	 */
	public void keyPressed(KeyEvent e) { }
	
	/**
	 * Handler for the keyTyped event (empty)
	 */
	public void keyTyped(KeyEvent e) {	}
		
    /** 
     * Updates the state of the game/animation based on the
     * amount of elapsed time that has passed. You should
     * override this in your game class to do something useful.
     */
    public void update(long elapsedTime) { /* do nothing  */ }


    /** 
     * Subclasses must override this method to draw output to
     * the screen via the Graphics2D object 'g'.
     * 
     * @param g The Graphics2D object to draw with.
     */
    public abstract void draw(Graphics2D g);
}
