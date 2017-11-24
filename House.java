import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import acm.graphics.GArc;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GOval;
import acm.graphics.GPolygon;
import acm.graphics.GRect;
import acm.util.RandomGenerator;

public class House extends GCompound {
	
	public House(String color) {

		// decodes the hexcolor passed through 
		Color theColor = Color.decode(color);
		
		// creates side portion of hosue
		side = new GRect(SIDE_WIDTH, SIDE_HEIGHT);
		side.setColor(walnut);
		side.setFilled(true);
		side.setFillColor(theColor);
		
		// creates chimney
		chimney = new GRect(CHIMNEY_WIDTH, CHIMNEY_HEIGHT);
		chimney.setColor(Color.LIGHT_GRAY);
		chimney.setFilled(true);
		chimney.setFillColor(Color.LIGHT_GRAY);
		
		// base square of house
		base = new GRect(HOUSE_WIDTH, HOUSE_HEIGHT);
		base.setColor(walnut);
		base.setFilled(true);
		base.setFillColor(theColor);
		
		// fence of house
		fence = new GRect(FENCE_LENGTH, FENCE_HEIGHT);
		fence.setColor(Color.BLACK);
		fence.setFilled(true);
		fence.setFillColor(Color.LIGHT_GRAY);
		
		// big roof of main house
		roof = createRoof(ROOF_WIDTH, ROOF_HEIGHT);
		roof.setColor(walnut);
		roof.setFilled(true);
		roof.setFillColor(walnut);
		
		// small roof of side
		sideRoof = createRoof(SIDE_ROOF_WIDTH, SIDE_ROOF_HEIGHT);
		sideRoof.setColor(walnut);
		sideRoof.setFilled(true);
		sideRoof.setFillColor(cream);
		
		// front door square
		doorFront = new GRect(DOOR_FRONT_WIDTH, DOOR_FRONT_HEIGHT);
		doorFront.setColor(walnut);
		doorFront.setFilled(true);
		doorFront.setFillColor(theColor);
		
		// roof of door
		doorRoof = createRoof(DOOR_ROOF_WIDTH, DOOR_ROOF_HEIGHT);
		doorRoof.setColor(walnut);
		doorRoof.setFilled(true);
		doorRoof.setFillColor(walnut);
		
		// square on front of house
		patio = new GRect(PATIO_WIDTH, PATIO_HEIGHT);
		patio.setColor(walnut);
		patio.setFilled(true);
		patio.setFillColor(theColor);
		
		// roof for square on front of house
		patioRoof = createRoof(PATIO_ROOF_WIDTH, PATIO_ROOF_HEIGHT);
		patioRoof.setColor(walnut);
		patioRoof.setFilled(true);
		patioRoof.setFillColor(cream);
		
		// small line on square on front of house
		patioDecor = new GRect(PATIO_DECOR_WIDTH, PATIO_DECOR_HEIGHT);
		patioDecor.setColor(walnut);
		patioDecor.setFilled(true);
		patioDecor.setFillColor(cream);
		
		// decoration on side square
		sideDeco = new GRect(SIDE_DECO_WIDTH, SIDE_DECO_HEIGHT);
		sideDeco.setColor(walnut);
		sideDeco.setFilled(true);
		sideDeco.setFillColor(cream);
		
		// window on side
		window1 = new GRect(WINDOW_WIDTH, WINDOW_HEIGHT);
		window1.setColor(walnut);
		window1.setFilled(true);
		window1.setFillColor(lightBlue);
		
		// left window on main house
		window2 = new GRect(WINDOW_WIDTH, WINDOW_HEIGHT);
		window2.setColor(walnut);
		window2.setFilled(true);
		window2.setFillColor(lightBlue);
		
		// right window on main
		window3 = new GRect(WINDOW_WIDTH, WINDOW_HEIGHT);
		window3.setColor(walnut);
		window3.setFilled(true);
		window3.setFillColor(lightBlue);
		
		// door
		door = new GRect(DOOR_WIDTH, DOOR_HEIGHT);
		door.setColor(cream);
		door.setFilled(true);
		door.setFillColor(walnut);
		
		// little smoke puffs from chimney - emoji from internet
		Image puff = null;	
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("puff.png");
		try {
			puff = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GImage smokePuff = new GImage(puff);
		smokePuff.scale(0.2);
		
		
		// adds all components
		
		add(side, 0, 0);
		add(sideRoof, SIDE_WIDTH / 2, 0 - SIDE_ROOF_HEIGHT / 2);
		add(sideDeco, 0 - 5, SIDE_HEIGHT / 2 - SIDE_DECO_HEIGHT);
		add(chimney, SIDE_WIDTH + HOUSE_WIDTH / 2 + 10, 0);
		
		int x = rgen.nextInt(1, MAX_PUFFS);
		double puffHeight = 0;
		for(int i = 0; i < x; i++) {
			smokePuff = new GImage(puff);
			smokePuff.scale(0.2);
			add(smokePuff, SIDE_WIDTH + HOUSE_WIDTH / 2 + 17 - smokePuff.getWidth() / 2, 0 - smokePuff.getHeight() - 5 - puffHeight);
			puffHeight += smokePuff.getHeight() + 20;
		}
		
		add(base, SIDE_WIDTH / 2, SIDE_HEIGHT - HOUSE_HEIGHT);
		add(doorFront, 0, SIDE_HEIGHT - DOOR_FRONT_HEIGHT);
		add(doorRoof, SIDE_WIDTH / 2, SIDE_HEIGHT / 2 + DOOR_ROOF_HEIGHT / 2);
		add(patio, SIDE_WIDTH / 2 + HOUSE_WIDTH / 2 - PATIO_WIDTH / 2, SIDE_HEIGHT - PATIO_HEIGHT);		
		double m = 1.0;  // adds three windows on patio
		for(int i = 0; i < 3; i++) {
			GRect patioWindow = new GRect(WINDOW_WIDTH, WINDOW_HEIGHT);
			patioWindow.setColor(walnut);
			patioWindow.setFilled(true);
			patioWindow.setFillColor(walnut);
			add(patioWindow, SIDE_WIDTH + WINDOW_WIDTH * m, SIDE_HEIGHT - PATIO_HEIGHT - PATIO_ROOF_HEIGHT / 2 + WINDOW_HEIGHT / 2);
			m += 1.5;
		}
		add(patioRoof, SIDE_WIDTH / 2 + HOUSE_WIDTH / 2, SIDE_HEIGHT - PATIO_HEIGHT - PATIO_ROOF_HEIGHT / 2);
		add(patioDecor, SIDE_WIDTH / 2 + HOUSE_WIDTH / 2 - PATIO_DECOR_WIDTH / 2, SIDE_HEIGHT - PATIO_HEIGHT / 2 + 5);
		add(roof, SIDE_WIDTH / 2 + HOUSE_WIDTH / 2, SIDE_HEIGHT - HOUSE_HEIGHT - ROOF_HEIGHT / 2);
		add(fence, HOUSE_WIDTH + SIDE_WIDTH / 2, SIDE_HEIGHT - FENCE_HEIGHT);
		add(window1, SIDE_WIDTH / 2 - WINDOW_WIDTH / 2, 0 + 2);
		add(window2, HOUSE_WIDTH / 2 + WINDOW_WIDTH, SIDE_HEIGHT - HOUSE_HEIGHT);
		add(window3, HOUSE_WIDTH / 2 + WINDOW_WIDTH * 2, SIDE_HEIGHT - HOUSE_HEIGHT);
		add(door, SIDE_WIDTH / 2 - DOOR_WIDTH / 2, SIDE_HEIGHT - DOOR_HEIGHT - 13);	
		int k = 0;   // adds three steps leading up to door
		for(int i = 0; i < 3; i++) {
			GRect step = new GRect(STEP_WIDTH, STEP_HEIGHT);
			step.setColor(cream);
			step.setFilled(true);
			step.setFillColor(Color.LIGHT_GRAY);
			add(step, SIDE_WIDTH / 2 - STEP_WIDTH / 2, SIDE_HEIGHT - 1 - (STEP_HEIGHT * 3) + k);
			k += STEP_HEIGHT;
		}	
		int n = 1;   // adds fenceposts
		for(int i = 0; i < 3; i++) {
			GRect fencepost = new GRect(POST_WIDTH, POST_HEIGHT);
			fencepost.setColor(Color.BLACK);
			fencepost.setFilled(true);
			fencepost.setFillColor(Color.LIGHT_GRAY);
			add(fencepost, HOUSE_WIDTH + SIDE_WIDTH / 2 + POST_WIDTH * n, SIDE_HEIGHT - POST_HEIGHT);
			n += 2;
		}
		// this section adds the string of lights on the house
		double r = HOUSE_WIDTH;     // make radius large enough to look good on house
		double cx = SIDE_WIDTH / 2 + HOUSE_WIDTH / 2;   // x location of center of circle
		double cy = HOUSE_HEIGHT / 2;                 // y location of center of circle 
		GArc arc = new GArc(2 * r, 2 * r, -60, -60);   // creates an upward-facing arc against house
		add(arc, cx - r, cy - r - SIDE_HEIGHT / 2);   // adds arc to correct place on screen
		double dx = -22;     // x location of individual light
		double dy = -9;      // y location of individual light
		for(int i = 0; i < 10; i++) {    // places multiple lights on the string
			GOval light = new GOval(LIGHT_RADIUS * 2, LIGHT_RADIUS * 2);   // creates light object
			Color nextColor = getRandomNewColor(light.getColor());   // each light random color
			light.setColor(nextColor);      // sets color of light
			light.setFilled(true);          // fills light
			add(light, SIDE_WIDTH / 2 + HOUSE_WIDTH / 4 + dx, SIDE_HEIGHT / 2 + dy);  // adds light to house
			dx += 10;  // x location for next light
			dy += 4 - i;   // makes lights strung on arc pattern
		}
	}
/*
 * This method provides random colors for the decorative lights. This method is taken from the 
 * section 7 debugging activity for CS 106A. 
 */
	private Color getRandomNewColor(Color prevColor) {   
		while (true) { 
			Color newColor;
			int color = rgen.nextInt(0,  NUM_COLORS - 1);
			switch(color) { 
			case 0:
				newColor = Color.RED;
				break;
			case 1:
				newColor = Color.ORANGE;
				break;
			case 2:
				newColor = Color.YELLOW;
				break;
			case 3:
				newColor = Color.GREEN;
				break;
			case 4:
				newColor = Color.BLUE;
				break;
			default:
				newColor = Color.BLACK;
				break;
			}
			// If the new color is the same as the previous color, 
			// we’re done.  Otherwise, choose again. 
			if (!newColor.equals(prevColor)) return newColor;
		}
	}
/*	
 * creates a triangle shape for a roof used around the house. Makes any size triangle with
 * dimensions passed
 */
	private GPolygon createRoof(double width, double height){ 
		GPolygon poly = new GPolygon();
		poly.addVertex(0, -height / 2);
		poly.addVertex(width / 2, height / 2);
		poly.addVertex(-width / 2, height / 2);
		return poly;
	}
/*
 * private instance variables	
 */
	private static final int SIDE_WIDTH = 40;
	private static final int SIDE_HEIGHT = 160;
	private static final int HOUSE_WIDTH = 100;
	private static final int HOUSE_HEIGHT = 120;
	private static final int FENCE_LENGTH = 70;
	private static final int FENCE_HEIGHT = 20;
	private static final int POST_WIDTH = 10;
	private static final int POST_HEIGHT = 30;
	private static final int ROOF_WIDTH = 120;
	private static final int ROOF_HEIGHT = 50;
	private static final int SIDE_ROOF_WIDTH = 60;
	private static final int SIDE_ROOF_HEIGHT = 30;
	private static final int DOOR_ROOF_WIDTH = 50;
	private static final int DOOR_ROOF_HEIGHT = 35;
	private static final int DOOR_FRONT_WIDTH = 40;
	private static final int DOOR_FRONT_HEIGHT = 45;
	private static final int SIDE_DECO_WIDTH = 45;
	private static final int SIDE_DECO_HEIGHT = 20;
	private static final int PATIO_WIDTH = 50;
	private static final int PATIO_HEIGHT = 40;
	private static final int PATIO_ROOF_WIDTH = 65;
	private static final int PATIO_ROOF_HEIGHT = 25;
	private static final int PATIO_DECOR_WIDTH = 65;
	private static final int PATIO_DECOR_HEIGHT = 5;
	private static final int WINDOW_WIDTH = 10;
	private static final int WINDOW_HEIGHT = 25;
	private static final int LIGHT_RADIUS = 2;
	private static final int DOOR_WIDTH = 10;
	private static final int DOOR_HEIGHT = 30;
	private static final int STEP_WIDTH = 25;
	private static final int STEP_HEIGHT = 4;
	private static final int CHIMNEY_WIDTH = 15;
	private static final int CHIMNEY_HEIGHT = 40;
	
	private static final int NUM_COLORS = 5;
	private static final int MAX_PUFFS = 5;
	
	private GRect base;
	private GRect fence;
	private GRect side;
	private GRect sideDeco;
	private GRect doorFront;
	private GRect patio;
	private GRect patioDecor;
	private GRect window1;
	private GRect window2;
	private GRect window3;
	private GRect door;
	private GRect chimney;
	
	private GPolygon roof;
	private GPolygon sideRoof;
	private GPolygon doorRoof;
	private GPolygon patioRoof;
	
	// new colors created for house compound
	Color cream = Color.decode("#F0ECEB");
	Color walnut = Color.decode("#584E52");
	Color lightBlue = Color.decode("#8ECBF8");
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
}
