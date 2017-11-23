import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import acm.graphics.GArc;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GPolygon;
import acm.graphics.GRect;

public class Sleighran extends GCompound{

	public Sleighran() {
		
	// This section creates the Mehran portion of SLeighran
		
		Image mehranImage = null;	
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("mehron.png");
		try {
			mehranImage = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GImage mehran = new GImage(mehranImage);
		mehran.scale(0.15);
		
		body = new GRect(BODY_WIDTH, BODY_HEIGHT);
		body.setColor(sweater);
		body.setFilled(true);
		body.setFillColor(sweater);
		add(body, mehran.getWidth() + BODY_WIDTH / 2, 0 - 2);
		
		arms = new GRect(ARMS_WIDTH, ARMS_HEIGHT);
		arms.setColor(sweater);
		arms.setFilled(true);
		arms.setFillColor(sweater);
		add(arms, mehran.getWidth() + BODY_WIDTH * 2 - ARMS_WIDTH + 10, BODY_HEIGHT - OTHER_LEGS_HEIGHT * 3);
		
		otherLegs = new GRect(OTHER_LEGS_WIDTH, OTHER_LEGS_HEIGHT);
		otherLegs.setColor(sweater);
		otherLegs.setFilled(true);
		otherLegs.setFillColor(sweater);
		add(otherLegs, mehran.getWidth() + BODY_WIDTH * 2, BODY_HEIGHT - OTHER_LEGS_HEIGHT);
		
		legs = makeTriangle(LEG_SIZE, LEG_SIZE);
		legs.setColor(sweater);
		legs.setFilled(true);
		legs.setFillColor(sweater);
		add(legs, mehran.getWidth() + BODY_WIDTH * 2 - 4, BODY_HEIGHT);
	
		add(mehran, mehran.getWidth(), 0 - mehran.getHeight());
		
		hat = makeTriangle(HAT_SIZE, HAT_SIZE);
		hat.setColor(red);
		hat.setFilled(true);
		hat.setFillColor(red);
		add(hat, mehran.getWidth() + 10, 0 - mehran.getHeight() - 3);
		
		hatDecal = new GOval(2, 2);
		hatDecal.setColor(cream);
		hatDecal.setFilled(true);
		hatDecal.setFillColor(cream);
		add(hatDecal, mehran.getWidth() + 8, 0 - mehran.getHeight() - hat.getHeight());
		
		hatLine = new GLine(0, 0, HAT_SIZE, 0);
		hatLine.setColor(cream);
		add(hatLine, mehran.getWidth() + 4, 0 - mehran.getHeight() + 3);
		
		
	// This section creates the sleigh portion of Sleighran
		backTop = new GArc(2 * ARC_RADIUS, 2 * ARC_RADIUS, 20, 90);
		backTop.setColor(red);
		backTop.setFilled(true);
		backTop.setFillColor(red);
		add(backTop, 0, 0);
		
		back = new GRect(BACK_WIDTH, BACK_HEIGHT);
		back.setColor(red);
		back.setFilled(true);
		back.setFillColor(red);
		add(back, ARC_RADIUS, ARC_RADIUS - 5);
		
		base = new GRect(BASE_WIDTH, BASE_HEIGHT);
		base.setColor(red);
		base.setFilled(true);
		base.setFillColor(red);
		add(base, ARC_RADIUS + BACK_WIDTH, BACK_HEIGHT - BASE_HEIGHT / 2 + 2);
		
		fill = makeTriangle(SMALL_TRIANGLE, SMALL_TRIANGLE);
		fill.setColor(red);
		fill.setFilled(true);
		fill.setFillColor(red);
		add(fill, ARC_RADIUS + BACK_WIDTH, BACK_HEIGHT - BASE_HEIGHT + SMALL_TRIANGLE / 2);
		
		bottomRight = new GOval(BOTTOM_RIGHT_RADIUS * 2, BOTTOM_RIGHT_RADIUS * 2);
		bottomRight.setColor(red);
		bottomRight.setFilled(true);
		bottomRight.setFillColor(red);
		add(bottomRight, ARC_RADIUS + BASE_WIDTH + BOTTOM_RIGHT_RADIUS, BACK_HEIGHT - BOTTOM_RIGHT_RADIUS + 2);
		
		fillFront = makeTriangle(SMALL_TRIANGLE, SMALL_TRIANGLE);
		fillFront.setColor(red);
		fillFront.setFilled(true);
		fillFront.setFillColor(red);
		add(fillFront, ARC_RADIUS + BASE_WIDTH + BOTTOM_RIGHT_RADIUS + SMALL_TRIANGLE - 1, BACK_HEIGHT - BOTTOM_RIGHT_RADIUS);
		
		ski = new GLine(0, 0, SKI_LENGTH, 0);
		ski.setColor(gold);
		add(ski, ARC_RADIUS, BACK_HEIGHT + ARC_RADIUS);
		
		skiFront = new GArc(2 * SKI_FRONT_RADIUS, 2 * SKI_FRONT_RADIUS, -90, 270);
		skiFront.setColor(gold);
		add(skiFront, ARC_RADIUS + SKI_LENGTH - SKI_FRONT_RADIUS + 1, BACK_HEIGHT - 1);
		
		skiBack = new GArc(2 * SKI_BACK_RADIUS, 2 * SKI_BACK_RADIUS, -90, -270);
		skiBack.setColor(gold);
		add(skiBack, SKI_BACK_RADIUS + 5, BACK_HEIGHT + SKI_BACK_RADIUS);
		
		skiLine1 = new GLine(0, 0, 0, SKI_LINE_LENGTH);
		skiLine1.setColor(gold);
		add(skiLine1, ARC_RADIUS + SKI_LINE_LENGTH * 3, BACK_HEIGHT + SKI_LINE_LENGTH * 2);
		
		skiLine2 = new GLine(0, 0, 0, SKI_LINE_LENGTH);
		skiLine2.setColor(gold);
		add(skiLine2, ARC_RADIUS + SKI_LINE_LENGTH * 7, BACK_HEIGHT + SKI_LINE_LENGTH * 2);
	}
/*
 * This method makes a triangle shape to fill the sleigh
 */
	private GPolygon makeTriangle(double width, double height){ 
		GPolygon poly = new GPolygon();
		poly.addVertex(0, -height / 2);
		poly.addVertex(width / 2, height / 2);
		poly.addVertex(-width / 2, height / 2);
		return poly;
	}
/*	
 * Instance variables for the sleigh portion
 */
	private static final int ARC_RADIUS = 15;
	private static final int BACK_WIDTH = 15;
	private static final int BACK_HEIGHT = 25;
	private static final int BASE_WIDTH = 30;
	private static final int BASE_HEIGHT = 15;
	private static final int SMALL_TRIANGLE = 10;
	private static final int BOTTOM_RIGHT_RADIUS = 7;
	private static final int SKI_LENGTH = 50;
	private static final int SKI_FRONT_RADIUS = 8;
	private static final int SKI_BACK_RADIUS = 5;
	private static final int SKI_LINE_LENGTH = 5;
	
	private GRect base;
	private GRect back;
	private GPolygon fill;
	private GPolygon fillFront;
	private GLine ski;
	private GLine skiLine1;
	private GLine skiLine2;
	private GOval bottomRight;
	private GArc backTop;
	private GArc skiFront;
	private GArc skiBack;

/*	
 * Instance variables for Mehran portion
 */
	private static final int MEHRAN_RADIUS = 5;
	private static final int HAT_SIZE = 12;
	private static final int BODY_WIDTH = 10;
	private static final int BODY_HEIGHT = 20;
	private static final int LEG_SIZE = 30;
	private static final int OTHER_LEGS_WIDTH = 10;
	private static final int OTHER_LEGS_HEIGHT = 5;
	private static final int ARMS_WIDTH = 10;
	private static final int ARMS_HEIGHT = 3;
	
	private GPolygon hat;
	private GOval hatDecal;
	private GLine hatLine;
	private GRect body;
	private GPolygon legs;
	private GRect otherLegs;
	private GRect arms;
	
/*
 * colors added for Sleighran compound
 */
	Color cream = Color.decode("#F0ECEB");
	Color gold = Color.decode("#E4CB85");
	Color red = Color.decode("#971835");
	Color sweater = Color.decode("#6C8C85");
	
}
