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
	private static final int TIMER_LENGTH = 33;  // seconds for timer
/*	
 * Instance Variables
 */
	public static GraphicsContest sleighRun;
	
	private AudioClip sarajevo;
	
	private Timer timer;

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
	private int delayChange = 10; // gradual speed-up of game as player progresses
	
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
	
	private GLabel verse1;
	private GLabel verse2;
	private GLabel verse3;
	private GLabel verse4;
	private GLabel prompt;
	private GLabel finalScore;   // label declaring final score of player
	private GLabel scoreCount;  // label to display score of player
	
/*
 * Run method for Sleigh Run Game
 */
	public void run() {
		displayStartScreen();  // screen runs before game starts, giving background and explaining game
		
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
			waitForRestart();  // waits for player to click to restart game
		}
	}
/*	
 * This method provides the start screen to the game, providing background/into and information.
 * It prompts the user before starting the game.
 */
	private void displayStartScreen() {
		addMouseListeners();
		addKeyListeners();
		
		GLabel title = new GLabel("SLEIGH RUN");
		title.setColor(Color.BLACK);
		title.setFont(new Font("Arial", Font.BOLD, 60));
		double x = getWidth() / 2 - title.getWidth() / 2;
		double y = getHeight() / 2 - title.getHeight() * 3;
		title.setLocation(x,y);
		add(title);
		
		verse1 = new GLabel ("Twas the night before a CS106A assignment was due, when all through the campus,");
		verse1.setColor(Color.BLACK);
		double x1 = getWidth() / 2 - verse1.getWidth() / 2;
		double y1 = getHeight() / 2 - verse1.getHeight() * 5;
		verse1.setLocation(x1, y1);
		add(verse1);
		
		verse2 = new GLabel ("Not a student had finished, not even those who got a 120 on the midterm.");
		verse2.setColor(Color.BLACK);
		double x2 = getWidth() / 2 - verse2.getWidth() / 2;
		double y2 = getHeight() / 2 - 40;
		verse2.setLocation(x2, y2);
		add(verse2);
		
		verse3 = new GLabel ("A legend flew through the air with care,");
		verse3.setColor(Color.BLACK);
		double x3 = getWidth() / 2 - verse3.getWidth() / 2;
		double y3 = getHeight() / 2;
		verse3.setLocation(x3, y3);
		add(verse3);
		
		verse4 = new GLabel ("In hopes that the students would go find help at the L.A.I.R.");
		verse4.setColor(Color.BLACK);
		double x4 = getWidth() / 2 - verse4.getWidth() / 2;
		double y4 = getHeight() / 2 + 40;
		verse4.setLocation(x4, y4);
		add(verse4);
		
		prompt = new GLabel ("CLICK TO CONTINUE");
		prompt.setColor(Color.BLACK);
		prompt.setFont(new Font("Arial", Font.BOLD, 20));
		double promptX = getWidth() / 2 - prompt.getWidth() / 2;
		double promptY = getHeight() / 2 + 100;
		prompt.setLocation(promptX,promptY);
		add(prompt);
		
		waitForClick();
		
		remove(verse1);
		remove(verse2);
		remove(verse3);
		remove(verse4);
		remove(prompt);
		
		verse1 = new GLabel ("The deadline to submit was fast approaching,");
		verse1.setColor(Color.BLACK);
		x1 = getWidth() / 2 - verse1.getWidth() / 2;
		y1 = getHeight() / 2 - verse1.getHeight() * 5;
		verse1.setLocation(x1, y1);
		add(verse1);
		
		verse2 = new GLabel ("You need to give every student some coaching.");
		verse2.setColor(Color.BLACK);
		x2 = getWidth() / 2 - verse2.getWidth() / 2;
		y2 = getHeight() / 2 - 40;
		verse2.setLocation(x2, y2);
		add(verse2);
		
		verse3 = new GLabel ("Don't hit the houses or altitude limit as you soar,");
		verse3.setColor(Color.BLACK);
		x3 = getWidth() / 2 - verse3.getWidth() / 2;
		y3 = getHeight() / 2;
		verse3.setLocation(x3, y3);
		add(verse3);
		
		verse4 = new GLabel ("and when the music drops it might get HARDCORE...");
		verse4.setColor(Color.BLACK);
		x4 = getWidth() / 2 - verse4.getWidth() / 2;
		y4 = getHeight() / 2 + 40;
		verse4.setLocation(x4, y4);
		add(verse4);
		
		prompt = new GLabel ("CLICK TO START");
		prompt.setColor(Color.BLACK);
		prompt.setFont(new Font("Arial", Font.BOLD, 20));
		promptX = getWidth() / 2 - prompt.getWidth() / 2;
		promptY = getHeight() / 2 + 100;
		prompt.setLocation(promptX,promptY);
		add(prompt);
		
		waitForClick();
		
		remove(title);
		remove(verse1);
		remove(verse2);
		remove(verse3);
		remove(verse4);
		remove(prompt);
	}
/*
 * This method sets up the graphics and creates the objects in the game. It also sets the timer
 * and adds Mouse/Key Listeners
 */
	private void setup() {
		createWorld();  // adds graphics and objects to screen
		setTitle("Sleigh Run");  // sets title of game
	}
/*	
 * Waits for player's click to play game
 */
	private void waitForPlayer() {
		GLabel start = new GLabel("CLICK TO START");  // creates starting prompt
		double x = getWidth() / 2 - start.getWidth() - start.getWidth() / 2;
		double y = getHeight() / 2 - 80;
		start.setColor(Color.WHITE);
		start.setFont(new Font("Arial", Font.BOLD, 38));
		start.setLocation(x,y);
		add(start); // add label
		waitForClick();            // waits for player's click
		remove(start);             // removes label from screen
		scoreCount = new GLabel(String.valueOf(score));  // creates score count label
		scoreX = getWidth() / 2 - scoreCount.getWidth();
		scoreY = getHeight() / 2 - 80;
		add(scoreCount, scoreX, scoreY);     // adds score count label to screen
		started = true;         // starts game function
		musicStarted = true;      // starts music function
		playMusic();            // plays the music
		setTimer();   // sets timer
	}
/*
 * This method keeps track of the player's score and adds the score label to the screen
 */
	private void keepScore() {
		remove(scoreCount);  // removes previously placed label
		if(houses[0].getX() + houses[0].getWidth() == kareldolph.getX()) { // if house has passed
			score++;  // increases score count
		} 
		if(score > 0) {
		scoreCount = new GLabel(String.valueOf(score));  // creates new score label
		scoreX = getWidth() / 2 - scoreCount.getWidth();  // x location of label
		scoreY = getHeight() / 2 - 80;                     // y location of label
		scoreCount.setColor(Color.WHITE);                // sets color of label
		scoreCount.setFont(new Font("Arial", Font.BOLD, 38));  // sets font of label
		scoreCount.setLocation(scoreX,scoreY);             // sets location of label
		add(scoreCount);  
		}// add label
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
 * This method checks for collisions of kareldolph with houses or drones or boundaries.
 * Any collision ends the game
 */
	private void checkForCollisions() {
		if(kareldolph.getX() > 0) {
			gameOver = true;
		}
		for(int i = 0; i < houses.length; i++) { // checks every hosue for collision
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
		for(int j = 0; j < drones.length; j++) {  // checks all drones for collision
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
/*
 * This sequence runs when the game is over
 */
	private void playGameOver() {
		resetFunctions();  // resets functions so game can be played again
		removeStuff();   // remove labels and objects on the screen
		makeEndLabels();  // creates labels to inform player
		finalScore();  // gives final score to player
	}
/*	
 * This method occurs after game has ended, and waits for user to click again. resets score
 */
	private void waitForRestart() {
		waitForClick();  // waits for player's click
		score = 0;  // resets player's score
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
 * This method adds the starry night background image
 */
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
		add(background, 0, 0);  // adds background to screen
	}
/*
 * This method creates the initial houses on the screen
 */
	private void createHouses() {	
		double x = 0;   // x location of house
		for(int i = 0; i < houses.length; i++) {
			house = new House(hexcolor);  // creates new house with random color
			String nextColor = getRandomNewColor(house.getColor());
			double y = getHeight() - house.base.getHeight() - BOTTOM_SPACE;  // y location of house
			add(house, x, y);  // adds house to screen
			houses[i] = house;  // adds house to array
			x += house.getWidth() - HOUSE_SPACE;   // space between houses
			hexcolor = nextColor; // gets new color for house
		} 
	}
/*
 * This method adds sleighran, kareldolph, and rope to screen
 */
	private void addParty() {
		sleighran = new Sleighran();   // creates new sleighran
		add(sleighran, PARTY_SPACE, getHeight() / 2);  // adds sleighran to screen
		kareldolph = new Kareldolph();   // creates new kareldolph
		add(kareldolph, kareldolphX, kareldolphY);   // adds kareldolph to screen
		rope = new Rope();        // creates new rope
		add(rope, PARTY_SPACE - 45, getHeight() / 2);    // adds rope to screen
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
 * Timer class to keep track of when to speed up game
 */
	class ThisTask extends TimerTask {
		public void run() {
			delay = delayChange;   // makes everything move faster
			hardcore = true;     // makes game hardcore
			setTimer();        // sets the timer
			delayChange--;    // makes movement faster every time 34 seconds pass
		}
	}
/*	
 * Sets the timer
 */
	private void setTimer() {
		timer = new Timer();  // creates timer
		timer.schedule(new ThisTask(), (long)TIMER_LENGTH * 1000);   // cue on musical drop
	}
/*
 * This method plays music if the game has started, stops music if game is over or not started
 */
	private void playMusic() {
		if(musicStarted) {        // if the game has started
			sarajevo = MediaTools.loadAudioClip("sarajevo.wav");  // new audio 
			sarajevo.play();      // plays song
		} else if(!musicStarted) {   // if game over or game not started
			sarajevo.stop();   // stops music
		}
	}
/*	
 * This method moves the houses horizontally across the screen
 */
	private void housesMove() {
		for(int i = 0; i < houses.length; i++) {  // moves all houses in array
			houses[i].move(hx,hy);
		}
	}
/*
 * This method moves kareldolph, sleighran, and rope on the screen
 */
	private void moveParty() { // sleighran moves slightly behind kareldolph, for 'natural' motion
		double sX = 0;    // party does not move in horizontally across screen
		double sY = kareldolph.getY() - (sleighran.getY() + 2); // difference between kareldolph and sleighran
		if(started) {   // moves when game starts
			if(yVel < MAX_SPEED) {   // if not yet max speed of 'falling' kareldolph
				yVel++;              // increases falling speed - simulates 'gravity'
			}
			kareldolph.move(xVel, yVel);   // moves kareldolph
			if(sY < MAX_SPEED) {  // if not yet max speed of 'falling' sleighran
				sY++;               // increases falling speed - simulates 'gravity'
			}
			sleighran.move(sX, sY);  // sleighran and rope move together
			rope.move(sX, sY);       // sleighran and rope move together
		}
	}
/*
 * This method checks if house has gone off screen, removes house if so, and adds new house to array
 */
	private void checkHouses() {
		for(int i = 0; i < houses.length; i++) {  // checks all houses
			if(houses[i].getX() < 0 - house.getWidth()) { // if the house is off screen
				remove(houses[i]);  // removes the house that went off screen
				adjustArray();   // updates the array
				addHouse();       // adds a new house to the game and array
			}
		}
	}
/*
 * This method adjusts the array so that next house is moved to prior
 */
	private void adjustArray() {
		for(int i = 0; i < houses.length - 1; i++) {
			houses[i] = houses[i + 1];  // moves houses in array
		}
	}
/*
 * This method adds a new house to the screen and houses array
 */
	private void addHouse() {
		String nextColor = getRandomNewColor(house.getColor());
		house = new House(hexcolor);  // generates new house
		double x = house.getWidth() * 4 - HOUSE_SPACE - 60;
		double y = getHeight() - house.base.getHeight() - BOTTOM_SPACE;
		add(house, x, y);  // adds house to screen
		houses[houses.length - 1] = house;  // adds new house to last space in array
		hexcolor = nextColor;  // gets new color for array
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
/*
 * This method resets functions so that the game can be played again
 */
	private void resetFunctions() {
		musicStarted = false;  // music has not started 
		started = false;    // game has not started
		gameOver = false;    // game no longer over - for next play
		hardcore = false;   // game resets to original starting speed
		delay = 20;    // reset original speed
		timer.cancel();  // cancels running timer
		playMusic();    // stops music
	}
/*
 * This method removes labels and party from screen	
 */
	private void removeStuff() {
		remove(scoreCount); 
		remove(sleighran);   
		remove(rope);
		remove(kareldolph);
	}
/*
 * This method adds the labels at the end of the game, notifying player of end of game
 * and prompting another play
 */
	private void makeEndLabels() {
		GLabel endGame = new GLabel("THAT'S THE END!");  // notifies player end of game
		double x = getWidth() / 2 - endGame.getWidth() - endGame.getWidth() / 2;
		double y = getHeight() / 2 - 80;
		endGame.setColor(Color.WHITE);
		endGame.setFont(new Font("Arial", Font.BOLD, 38));
		endGame.setLocation(x,y);
		add(endGame);  // adds label to screen
		GLabel playAgain = new GLabel("CLICK TO PLAY AGAIN?");  // creates restart prompt
		double dx = getWidth() / 2 - playAgain.getWidth() + 20;
		double dy = getHeight() / 2;
		playAgain.setColor(Color.WHITE);
		playAgain.setFont(new Font("Arial", Font.BOLD, 20));
		playAgain.setLocation(dx,dy);
		add(playAgain);  // adds label to screen
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
 * In the event that the mouse is clicked, party jumps
 */
	public void mouseClicked(MouseEvent e) {
		jump();
	}
/*
 * This method creates the 'jump' height of the party when space bar/ mouse clicked
 */
	public void jump() {  
		yVel = -15;      // arbitrary height of jump
	}
/*
 * This method allows use space bar to make party jump also
 */
	public void keyReleased(KeyEvent e) { 
		if(e.getKeyCode() == KeyEvent.VK_SPACE ) {
			jump();
		}
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
