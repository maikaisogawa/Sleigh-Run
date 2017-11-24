/*
 * File: GraphicsContest.java
 * --------------------------
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
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
	public GCompound[] houses = new GCompound[5];
	private String hexcolor = "#F29352";
	private double hx = -2;   // houses moving left
	private double hy = 0;    // houses do not move up or down

	public void run() {
		setup();
		waitForPlayer();
		while(!gameOver) {
			housesMove();
			checkHouses();
			pause(DELAY);
		}
	}
	
	private void setup() {
		addBackground();
		createHouses();	
		addParty();
		addMouseListeners();
		addActionListeners();
	}
	
	private void addParty() {
		sleighran = new Sleighran();
		add(sleighran, PARTY_SPACE, getHeight() / 2);
		kareldolph = new Kareldolph();
		add(kareldolph, PARTY_SPACE * 4 + sleighran.getWidth(), getHeight() / 2);
		rope = new Rope();
		add(rope, PARTY_SPACE - 30, getHeight() / 2);
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
			double y = getHeight() - house.getHeight() + BOTTOM_SPACE;  // y location of house
			add(house, x, y);
			houses[i] = house;
			x += house.getWidth() - HOUSE_SPACE;
			hexcolor = nextColor;
		} 
	}
	
	private void addHouse() {
		double x = house.getWidth() * 4 - HOUSE_SPACE - 60;
		double y = getHeight() - house.getHeight() + BOTTOM_SPACE;
		String nextColor = getRandomNewColor(house.getColor());
		house = new House(hexcolor);
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
