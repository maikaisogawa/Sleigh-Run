import java.awt.Color;

import acm.graphics.GArc;
import acm.graphics.GCompound;
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
		add(fill, ARC_RADIUS + BACK_WIDTH, BACK_HEIGHT - BASE_HEIGHT);
		
//		bottomLeft = new GOval(BOTTOM_LEFT_RADIUS * 2, BOTTOM_LEFT_RADIUS * 2);
//		bottomLeft.setColor(red);
//		bottomLeft.setFilled(true);
//		bottomLeft.setFillColor(red);
//		
//		add(base, 0, 0);
//		add(bottomLeft, 0 - BOTTOM_LEFT_RADIUS, 0 - (BOTTOM_LEFT_RADIUS * 2 - BASE_HEIGHT));
		
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
//	private static final int BOTTOM_LEFT_RADIUS = 9;
	
	private GRect base;
	private GPolygon fill;
//	
//	private GOval bottomLeft;
	
	private GArc backTop;
	private GRect back;
/*
 * colors added for Sleighran compound
 */
	Color cream = Color.decode("#F0ECEB");
	Color gold = Color.decode("#E4CB85");
	Color red = Color.decode("#971835");
	
}
