import java.awt.Color;

import acm.graphics.GArc;
import acm.graphics.GCompound;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GPolygon;
import acm.graphics.GRect;

public class Sleighran extends GCompound{

	public Sleighran() {
		
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
		add(skiBack, ARC_RADIUS, BACK_HEIGHT - 1);
		
	}
	
	private GPolygon makeTriangle(double width, double height){ 
		GPolygon poly = new GPolygon();
		poly.addVertex(0, -height / 2);
		poly.addVertex(width / 2, height / 2);
		poly.addVertex(-width / 2, height / 2);
		return poly;
	}
	
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
	
	private GRect base;
	private GRect back;
	
	private GPolygon fill;
	private GPolygon fillFront;
	
	private GLine ski;
	
	private GOval bottomRight;
	
	private GArc backTop;
	private GArc skiFront;
	private GArc skiBack;
	
/*
 * colors added for Sleighran compound
 */
	Color cream = Color.decode("#F0ECEB");
	Color gold = Color.decode("#E4CB85");
	Color red = Color.decode("#971835");
	
}
