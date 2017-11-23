/*
 * File: GraphicsContest.java
 * --------------------------
 */

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import acm.graphics.GImage;
import acm.graphics.GRect;
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
	
	private boolean gameOver;   // initialized false
	private boolean started;    // initialized false
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	ArrayList<House> houses;

	public void run() {
		setup();
	}
	
	
	private void setup() {
		addBackground();
		createHouses();	
		addMouseListeners();
		addActionListeners();
	}
	
	private void addBackground() {	
		
		Image logo = null;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("img.jpg");
		try {
			logo = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GImage background = new GImage(logo);

		
		// ranganath krishnamani

//		Color darkSky = Color.decode("#161D6A");
//		setBackground(darkSky);
		
		add(background, 0, 0);

	}
	
	private void createHouses() {	
		double x = 0;   // x location of house
		String hexcolor = "#F29352";
		for(int i = 0; i < 9; i++) {
			House house = new House(hexcolor);
			String nextColor = getRandomNewColor(house.getColor());
			double y = getHeight() - house.getHeight() + BOTTOM_SPACE;  // y location of house
			add(house, x, y);
			x += house.getWidth() - HOUSE_SPACE;
			hexcolor = nextColor;
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
