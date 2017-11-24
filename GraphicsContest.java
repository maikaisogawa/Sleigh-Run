/*
 * File: GraphicsContest.java
 * --------------------------
 */

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import acm.program.*;
import acm.util.*;
import acm.graphics.*;

/*
 * Issue: score count only working after initial houses pass
 * TASKS: make everything look better, top boundary
 */

public class GraphicsContest extends GraphicsProgram {

/*
 * Program Constants
 */
	private static final long serialVersionUID = 1L;
	/* Width and height of application window in pixels. */
	public static final int APPLICATION_WIDTH = 700;
	public static final int APPLICATION_HEIGHT = 700;
	public static final int WIDTH = APPLICATION_WIDTH;
	public static final int HEIGHT = APPLICATION_HEIGHT;
	
	private static final int HOUSE_SPACE = 15; // space between houses to be reduced to visual appeal
	private static final int BOTTOM_SPACE = 40; // house space from bottom of screen
	private static final int NUM_HOUSE_COLORS = 6;  // number of house colors
	private static final int PARTY_SPACE = 20; // space between sleigh and left side of screen
	private static final int MAX_SPEED = 8;  // maximum speed of falling *gravity* so not too fast
/*	
 * Instance Variables
 */
	public static GraphicsContest sleighRun;
	
	private AudioClip sarajevo;

	private RandomGenerator rgen = RandomGenerator.getInstance();
	private House house; 
	private Sleighran sleighran;
	private Kareldolph kareldolph;
	private Rope rope;
	private Drone drone;
	
	private boolean gameOver = false; // keeps track if game is over
	private boolean started = false;  // keeps track if game has started
	private boolean musicStarted = false;  // keeps track if music has started
	private boolean hardcore = false;   // keeps track if initial speed has increased
	
	private int delay = 20;   // speed of movement, speeds up every half-minute
	private int score = 0;    // score of player updates when house is passed
	
	private double hx = -2;   // houses moving left
	private double hy = 0;    // houses do not move up or down
	private double dx = -2;   // drones move left
	private double dy = 0;     // drones do not move up or down
	private double finalX;  // x location of final score label
	private double finalY;   // y location of final score label
	private double xVel = 0;  // x movement of kareldolph
	private double yVel = 2;  // y movement of kareldolph
	private double kareldolphX = PARTY_SPACE * 8;  // x location of kareldolph 
	private double kareldolphY = HEIGHT / 2;   // y location of kareldolph 
	private double scoreX;   // x location of score label
	private double scoreY;   // y location of score label
	
	public GCompound[] houses = new GCompound[5];  // Array of GCompounds for houses
	public GCompound[] drones = new GCompound [3];  // Array of GCompounds for drones
	private String hexcolor = "#F29352";  // initial hexcolor of house
	
	private GLabel finalScore;   // label declaring final score of player
	private GLabel scoreCount;  // label to display score of player
	
/*
 * Run method for Sleigh Run Game
 */
	public void run() {
		while(true) {  // runs in continuous loop so player can enjoy the game and BE HARDCORE
			setup();  // sets up game
			waitForPlayer();  // waits for player to click to start game
			while(!gameOver) {   // while player has not yet lost
				keepScore();     // tracks score of player
				gamePlay();      // provides motion and housekeeping of objects
				checkForCollisions();   // checks for collisions of kareldolph and anything
				pause(delay);     // delay in visuals for game play *speed* of game
			}
			playGameOver();  // provides end of game play and resets functions
			finalScore();    // gives final score to player
			waitForRestart();  // waits for player to click to restart game
		}
	}
/*
 * This method provides the motion of the objects in the game as well as checks if objects need
 * to be removed from screen and new ones added
 */
	private void gamePlay() {
		housesMove();   // motion of houses
		moveParty();    // motion of party
		checkHouses();  // if houses have gone off screen, removes house and adds another (array housekeeping)
		if(hardcore) {  // if music has dropped/double speed has started
			dronesMove();   // drones begin to move 
			checkDrones();  // drone housekeeping add/remove to array and screen
		}
	}
/*
 * This method sets up the graphics and creates the objects in the game. It also sets the timer
 * and adds Mouse/Key Listeners
 */
	private void setup() {
		createWorld();  // adds graphics and objects to screen
		setTitle("Sleigh Run");  // sets title of game
		setTimer();   // sets timer
		addMouseListeners();
		addKeyListeners();
	}
/*
 * This method creates the visuals and objects of the game
 */
	private void createWorld() {
		addBackground();   // adds starry night background
		createHouses();	    // creates and adds houses
		addParty();      // creates and adds sleighran, rope, and kareldolph
		createDrones();    // creates drones and adds to screen
	}
/*
 * This method provides the player with the final score after the game has ended
 */
	private void finalScore() {
		finalScore = new GLabel("Your score was: " + score);  // creates label
		finalX = getWidth() / 2 - finalScore.getWidth() / 2 * 3;  // x location
		finalY = finalScore.getHeight() * 3;   // y location
		finalScore.setColor(Color.WHITE);    // sets color of label
		finalScore.setFont(new Font("Arial", Font.BOLD, 38));  // sets font of label
		add(finalScore, finalX, finalY);  // adds label to screen
	}
/*
 * This method keeps track of the player's score and adds the score label to the screen
 */
	private void keepScore() {
		remove(scoreCount);  // removes previously placed label
		if(houses[0].getX() + houses[0].getWidth() == kareldolph.getX()) { // if house has passed
			score++;  // increases score count
		} 
		scoreCount = new GLabel(String.valueOf(score));  // creates new score label
		scoreX = getWidth() / 2 - scoreCount.getWidth();  // x location of label
		scoreY = getHeight() / 2 - 80;                     // y location of label
		scoreCount.setColor(Color.WHITE);                // sets color of label
		scoreCount.setFont(new Font("Arial", Font.BOLD, 38));  // sets font of label
		scoreCount.setLocation(scoreX,scoreY);             // sets location of label
		add(scoreCount);                                 // add label
	}
/*	
 * This method adds the drones to the screen and to the drones array
 */
	private void createDrones() {
		double droneY;
		double droneX = rgen.nextDouble(getWidth(), getWidth() * 2 - PARTY_SPACE);  // x location of drone random
		double droneSpace;   // space between drones so visually appealing
		for(int i = 0; i < drones.length; i++) {   // adds drones for as many drones exist in array
			drone = new Drone();  // creates new drone
			droneSpace = rgen.nextDouble(drone.getWidth() * 2, getWidth() / 2);  // random spacing
			droneY = rgen.nextDouble(0, getHeight() / 2 - drone.getHeight() * 2);  // random height
			add(drone, droneX, droneY);  // adds drone
			drones[i] = drone;        // adds drone to array
			droneX += droneSpace;       // increases drone space
		}
	}
/*
 * This method makes the drones move
 */
	private void dronesMove() {
		for(int i = 0; i < drones.length; i++) {  // makes all drones move
			drones[i].move(dx,dy);
		} 
	}
/*	
 * This method checks if the drone has gone off screen. if it has, it removes the drone,
 * updates the array, and adds a new drone
 */
	private void checkDrones() {
		for(int i = 0; i < drones.length; i++) {
			if(drones[i].getX() < 0 - drone.getWidth()){  // drone has gone out of bounds
				remove(drones[i]);  // removes the drone that has gone off screen
				adjustDroneArray();  // updates the drones array 
				addDrone();        // adds a new drone to the array and screen
			}
		}	
	}
/*
 * This method updates the array so that the old drone is removed, the drones are shifted,
 * and new drone is added to the end of the array
 */
	private void adjustDroneArray() {
		for(int i = 0; i < drones.length - 1; i++) {
			drones[i] = drones [i + 1];  // next drone shifted to position of prior
		}
	}
/*
 * This method adds a new drone to the array and screen
 */
	private void addDrone() {
		drone = new Drone();  // creates new drone
		double droneX = getWidth() + getWidth() / 2;  // new x location
		double droneY = rgen.nextDouble(0, getHeight() / 2 - drone.getHeight() * 2);  // new y location
		add(drone, droneX, droneY);  // adds drone to screen
		drones[drones.length - 1] = drone;  // adds drone to end of drones array
	}

	Timer timer;
	private static final int TIMER_LENGTH = 34;
	
	private void setTimer() {
		timer = new Timer();
		timer.schedule(new ThisTask(), (long)TIMER_LENGTH * 1000);   // cue on musical drop
	}
	
	private int delayChange = 10;
	
	class ThisTask extends TimerTask {
		public void run() {
			delay = delayChange;   // makes everything move faster
			hardcore = true;
			setTimer();
			delayChange--;    // makes movement faster every time 34 seconds pass
		}
	}
	
	private void checkForCollisions() {
		for(int i = 0; i < houses.length; i++) {
			if(getElementAt(kareldolph.getX(), kareldolph.getY()) == houses[i]) {   // upper left corner of kareldolph
				gameOver = true;
			} else if(getElementAt(kareldolph.getX() + kareldolph.getWidth(), kareldolph.getY()) == houses[i]) { // upper right corner
				gameOver = true;
			} else if(getElementAt(kareldolph.getX(), kareldolph.getY() + kareldolph.getHeight()) == houses[i]) { // bottom left corner
				gameOver = true;
			} else if(getElementAt(kareldolph.getX() + kareldolph.getWidth(), kareldolph.getY() + kareldolph.getHeight()) == houses[i]) {  // bottom right corner
				gameOver = true;
			}
		}
		for(int j = 0; j < drones.length; j++) {
			if(getElementAt(kareldolph.getX(), kareldolph.getY()) == drones[j]) {   // upper left corner of kareldolph
				gameOver = true;
			} else if(getElementAt(kareldolph.getX() + kareldolph.getWidth(), kareldolph.getY()) == drones[j]) { // upper right corner
				gameOver = true;
			} else if(getElementAt(kareldolph.getX(), kareldolph.getY() + kareldolph.getHeight()) == drones[j]) { // bottom left corner
				gameOver = true;
			} else if(getElementAt(kareldolph.getX() + kareldolph.getWidth(), kareldolph.getY() + kareldolph.getHeight()) == drones[j]) {  // bottom right corner
				gameOver = true;
			}
		}
	}
	
	private void playGameOver() {
		remove(scoreCount);
		musicStarted = false;
		playMusic();
		remove(sleighran);
		remove(rope);
		remove(kareldolph);
		GLabel endGame = new GLabel("THAT'S THE END!");  // creates starting prompt
		double x = getWidth() / 2 - endGame.getWidth() - endGame.getWidth() / 2;
		double y = getHeight() / 2 - 80;
		endGame.setColor(Color.WHITE);
		endGame.setFont(new Font("Arial", Font.BOLD, 38));
		endGame.setLocation(x,y);
		add(endGame);
		started = false;
		gameOver = false;
		hardcore = false;
		delay = 20;
		timer.cancel();
		GLabel playAgain = new GLabel("CLICK TO PLAY AGAIN?");  // creates starting prompt
		double dx = getWidth() / 2 - playAgain.getWidth() + 20;
		double dy = getHeight() / 2;
		playAgain.setColor(Color.WHITE);
		playAgain.setFont(new Font("Arial", Font.BOLD, 20));
		playAgain.setLocation(dx,dy);
		add(playAgain);
	}

	private void moveParty() {
		double sX = 0;
		double sY = kareldolph.getY() - (sleighran.getY() + 2);
		if(started) {
			if(yVel < MAX_SPEED) {
				yVel++;
			}
			kareldolph.move(xVel, yVel);
			if(sY < MAX_SPEED) {
				sY++;
			}
			sleighran.move(sX, sY);
			rope.move(sX, sY);
		}
	}
/*	
 * In the event that the mouse is clicked, party jumps
 */
	public void mouseClicked(MouseEvent e) {
		jump();
	}
/*
 * This method creates the 'jump' height of the party when space bar/ mouse clicked
 */
	public void jump() {  
		yVel = -15;
	}
/*
 * This method allows use space bar to make party jump also
 */
	public void keyReleased(KeyEvent e) { 
		if(e.getKeyCode() == KeyEvent.VK_SPACE ) {
			jump();
		}
	}
	
	private void addParty() {
		sleighran = new Sleighran();
		add(sleighran, PARTY_SPACE, getHeight() / 2);
		kareldolph = new Kareldolph();
		add(kareldolph, kareldolphX, kareldolphY);
		rope = new Rope();
		add(rope, PARTY_SPACE - 45, getHeight() / 2);
	}
	
	private void waitForPlayer() {
		GLabel start = new GLabel("CLICK TO START");  // creates starting prompt
		double x = getWidth() / 2 - start.getWidth() - start.getWidth() / 2;
		double y = getHeight() / 2 - 80;
		start.setColor(Color.WHITE);
		start.setFont(new Font("Arial", Font.BOLD, 38));
		start.setLocation(x,y);
		add(start); // add label
		waitForClick();  // waits for player's click
		remove(start);  // removes label from screen
		scoreCount = new GLabel(String.valueOf(score));
		scoreX = getWidth() / 2 - scoreCount.getWidth();
		scoreY = getHeight() / 2 - 80;
		add(scoreCount, scoreX, scoreY);
		started = true;
		musicStarted = true;
		playMusic();
	}
	
	private void playMusic() {
		
		if(musicStarted) {
			sarajevo = MediaTools.loadAudioClip("sarajevo.wav");
			sarajevo.play();
		} else if(!musicStarted) {
			sarajevo.stop();
		}
	}
	
	private void waitForRestart() {
		waitForClick();  // waits for player's click
		score = 0;
	}
	
	
	private void housesMove() {
		for(int i = 0; i < houses.length; i++) {
			houses[i].move(hx,hy);
		}
	} 
	
	private void checkHouses() {
		for(int i = 0; i < houses.length; i++) {
			if(houses[i].getX() < 0 - house.getWidth()) {
				remove(houses[i]);
				adjustArray();
				addHouse();
			}
		}
	}
	
	private void adjustArray() {
		for(int i = 0; i < houses.length - 1; i++) {
			houses[i] = houses[i + 1];
		}
	}
	
	private void addBackground() {		
		Image logo = null;	
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("img.jpg");
		try {
			logo = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GImage background = new GImage(logo);
		add(background, 0, 0);
	}
	
	private void createHouses() {	
		double x = 0;   // x location of house
		for(int i = 0; i < 5; i++) {
			house = new House(hexcolor);
			String nextColor = getRandomNewColor(house.getColor());
			double y = getHeight() - house.base.getHeight() - BOTTOM_SPACE;  // y location of house
			add(house, x, y);
			houses[i] = house;
			x += house.getWidth() - HOUSE_SPACE;
			hexcolor = nextColor;
		} 
	}
	
	private void addHouse() {
		String nextColor = getRandomNewColor(house.getColor());
		house = new House(hexcolor);
		double x = house.getWidth() * 4 - HOUSE_SPACE - 60;
		double y = getHeight() - house.base.getHeight() - BOTTOM_SPACE;
		add(house, x, y);
		houses[4] = house;
		hexcolor = nextColor;
	}
/*
 * This method generates the colors for the houses along the bottom of the screen
 */
	private String getRandomNewColor(Color prevColor) {   
		while (true) { 
			String newColor;
			int color = rgen.nextInt(0,  NUM_HOUSE_COLORS - 1);
			switch(color) { 
			case 0:
				// orange
				newColor = "#F29352";
				break;
			case 1:
				// slate
				newColor = "#82A0DD";
				break;
			case 2:
				// yellow
				newColor = "#F6C677";
				break;
			case 3:
				// another gray
				newColor = "#7A8C96";
				break;
			case 4:
				// green
				newColor = "#72BE9A";
				break;
			case 5:
				// salmon
				newColor = "#F68E81";
				break;
			default:
				// red
				newColor = "#E64D3E";
				break;
			}
			// If the new color is the same as the previous color, 
			// we’re done.  Otherwise, choose again. 
			if (!newColor.equals(prevColor)) return newColor;
		}
	}

}
