import java.awt.Color;

import acm.graphics.GArc;
import acm.graphics.GCompound;
import acm.graphics.GOval;
import acm.graphics.GRect;

public class Sleighran extends GCompound{

	public Sleighran() {
		
		back = new GArc(2 * ARC_RADIUS, 2 * ARC_RADIUS, 20, 90);
		back.setColor(red);
		back.setFilled(true);
		back.setFillColor(red);
		add(back, 0, 0);
		
//		base = new GRect(BASE_WIDTH, BASE_HEIGHT);
//		base.setColor(red);
//		base.setFilled(true);
//		base.setFillColor(red);
//		
//		bottomLeft = new GOval(BOTTOM_LEFT_RADIUS * 2, BOTTOM_LEFT_RADIUS * 2);
//		bottomLeft.setColor(red);
//		bottomLeft.setFilled(true);
//		bottomLeft.setFillColor(red);
//		
//		add(base, 0, 0);
//		add(bottomLeft, 0 - BOTTOM_LEFT_RADIUS, 0 - (BOTTOM_LEFT_RADIUS * 2 - BASE_HEIGHT));
		
	}
	
//	GArc arc = new GArc(2 * r, 2 * r, -60, -60);   // creates an upward-facing arc against house
//	add(arc, cx - r, cy - r - SIDE_HEIGHT / 2);   // adds arc to correct place on screen
	private static final int ARC_RADIUS = 20;
	
//	private static final int BASE_WIDTH = 50;
//	private static final int BASE_HEIGHT = 15;
//	private static final int BOTTOM_LEFT_RADIUS = 9;
	
//	private GRect base;
//	
//	private GOval bottomLeft;
	
	private GArc back;
/*
 * colors added for Sleighran compound
 */
	Color cream = Color.decode("#F0ECEB");
	Color gold = Color.decode("#E4CB85");
	Color red = Color.decode("#971835");
	
}
