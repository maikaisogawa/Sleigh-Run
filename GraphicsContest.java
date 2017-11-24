/*
 * File: GraphicsContest.java
 * --------------------------
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import acm.program.*;
import acm.util.*;

import acm.graphics.*;

public class GraphicsContest extends GraphicsProgram {
	
	private static final long serialVersionUID = 1L;
	
	/* Width and height of application window in pixels. */
	public static final int APPLICATION_WIDTH = 700;
	public static final int APPLICATION_HEIGHT = 700;
	
	public static final int WIDTH = APPLICATION_WIDTH;
	public static final int HEIGHT = APPLICATION_HEIGHT;
	
	public static GraphicsContest sleighRun;
	private static final int HOUSE_SPACE = 15;
	private static final int BOTTOM_SPACE = 30;
	
	private static final int NUM_HOUSE_COLORS = 6;
	private static final int DELAY = 20;
	
	private static final int PARTY_SPACE = 20;

	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	private House house; 
	private Sleighran sleighran;
	private Kareldolph kareldolph;
	private Rope rope;
	
	
	private boolean gameOver = false; 
	private boolean started = false;
	
	public GCompound[] houses = new GCompound[5];
	private String hexcolor = "#F29352";
	private double hx = -2;   // houses moving left
	private double hy = 0;    // houses do not move up or down

	public void run() {
		while(true) {
		setup();
		waitForPlayer();
		while(!gameOver) {
			housesMove();
			moveParty();
			checkHouses();
			checkForCollisions();
			pause(DELAY);
		}
		playGameOver();
		waitForRestart();
		}
	}
	
	private void setup() {
		addBackground();
		createHouses();	
		addParty();
		addMouseListeners();
		addKeyListeners();
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
	}
	
	private void playGameOver() {
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
		GLabel playAgain = new GLabel("CLICK TO PLAY AGAIN?");  // creates starting prompt
		double dx = getWidth() / 2 - playAgain.getWidth() + 20;
		double dy = getHeight() / 2;
		playAgain.setColor(Color.WHITE);
		playAgain.setFont(new Font("Arial", Font.BOLD, 20));
		playAgain.setLocation(dx,dy);
		add(playAgain);
	}

	private double xVel = 0;
	private double yVel = 2;
	
	private static final int MAX_SPEED = 8;
	
	private double kareldolphX = PARTY_SPACE * 8;
	private double kareldolphY = HEIGHT / 2;

	
	private void moveParty() {
		if(started) {
			if(yVel < MAX_SPEED) {
				yVel++;
			}
			kareldolph.move(xVel, yVel);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		jump();
	}
	
	public void jump() {
		yVel = -15;
	}
// can also use space bar to make party jump
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
		started = true;
	}
	
	private void waitForRestart() {
		waitForClick();  // waits for player's click
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
			System.out.println("HEIGHT: " + getHeight() + "; HOUSE: " + house.getHeight() + "; BOTTOM:" + BOTTOM_SPACE);
			double y = getHeight() - house.getHeight() + BOTTOM_SPACE + (house.smokePuff.getHeight() * house.numPuffs);  // y location of house
			System.out.println("Y: " + y);
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
		double y = getHeight() - house.getHeight() + BOTTOM_SPACE;
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
