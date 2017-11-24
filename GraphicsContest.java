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
	
	public GCompound[] houses = new GCompound[5];  // Array of GCompounds for houses
	public GCompound[] drones = new GCompound [3];  // Array of GCompounds for drones
	private String hexcolor = "#F29352";  // initial hexcolor of house
	
	private GLabel finalScore;   // label declaring final score of player
	
/*
 * Run method for Sleigh Run Game
 */
	public void run() {
		while(true) {
			setup();
			waitForPlayer();
			while(!gameOver) {
				keepScore();
				gamePlay();
				checkForCollisions();
				pause(delay);
			}
			playGameOver();
			finalScore();
			waitForRestart();
		}
	}
/*
 * This method provides the motion of the objects in the game as well as checks if objects need
 * to be removed from screen and new ones added
 */
	private void gamePlay() {
		housesMove();
		moveParty();
		checkHouses();
		if(hardcore) {
			dronesMove();
			checkDrones();
		}
	}
/*
 * This method sets up the graphics and creates the objects in the game. It also sets the timer
 * and adds Mouse/Key Listeners
 */
	private void setup() {
		createWorld();
		setTitle("Sleigh Run");
//		addBackground();
//		createHouses();	
//		addParty();
//		createDrones();
		setTimer();
		addMouseListeners();
		addKeyListeners();
	}
	
	private void createWorld() {
		addBackground();
		createHouses();	
		addParty();
		createDrones();
	}
	
	private void finalScore() {
		finalScore = new GLabel("Your score was: " + score);
		finalX = getWidth() / 2 - finalScore.getWidth() / 2 * 3;
		finalY = finalScore.getHeight() * 3;
		finalScore.setColor(Color.WHITE);
		finalScore.setFont(new Font("Arial", Font.BOLD, 38));
		add(finalScore, finalX, finalY);
	}
	
	private int score = 0;
	private GLabel scoreCount;
	private double scoreX;
	private double scoreY;
	
	private void keepScore() {
		remove(scoreCount);
		if(houses[0].getX() + houses[0].getWidth() == kareldolph.getX()) {
			score++;
		} 
		scoreCount = new GLabel(String.valueOf(score));
		scoreX = getWidth() / 2 - scoreCount.getWidth();
		scoreY = getHeight() / 2 - 80;
		scoreCount.setColor(Color.WHITE);
		scoreCount.setFont(new Font("Arial", Font.BOLD, 38));
		scoreCount.setLocation(scoreX,scoreY);
		add(scoreCount); // add label
	}
	
	private void createDrones() {
		double droneY;
		double droneX = rgen.nextDouble(getWidth(), getWidth() * 2 - PARTY_SPACE);
		double droneSpace;
		for(int i = 0; i < 3; i++) {
			drone = new Drone();
			droneSpace = rgen.nextDouble(drone.getWidth() * 2, getWidth() / 2);
			droneY = rgen.nextDouble(0, getHeight() / 2 - drone.getHeight() * 2);
			add(drone, droneX, droneY);
			drones[i] = drone;
			droneX += droneSpace;
		}
	}
	
	private void dronesMove() {
		for(int i = 0; i < drones.length; i++) {
			drones[i].move(dx,dy);
		} 
	}
	
	private void checkDrones() {
		for(int i = 0; i < drones.length; i++) {
			if(drones[i].getX() < 0 - drone.getWidth()){
				remove(drones[i]);
				adjustDroneArray();
				addDrone();
			}
		}	
	}
	
	private void adjustDroneArray() {
		for(int i = 0; i < drones.length - 1; i++) {
			drones[i] = drones [i + 1];
		}
	}
	
	private void addDrone() {
		drone = new Drone();
		double droneX = getWidth() + getWidth() / 2;
		double droneY = rgen.nextDouble(0, getHeight() / 2 - drone.getHeight() * 2);
		add(drone, droneX, droneY);
		drones[2] = drone;
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
