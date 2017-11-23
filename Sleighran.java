import java.awt.Color;
import acm.graphics.GCompound;
import acm.graphics.GOval;
import acm.graphics.GRect;

public class Sleighran extends GCompound{

	public Sleighran() {
		
		base = new GRect(BASE_WIDTH, BASE_HEIGHT);
		base.setColor(red);
		base.setFilled(true);
		base.setFillColor(red);
		
		bottomLeft = new GOval(BOTTOM_LEFT_RADIUS * 2, BOTTOM_LEFT_RADIUS * 2);
		bottomLeft.setColor(red);
		bottomLeft.setFilled(true);
		bottomLeft.setFillColor(red);
		
		add(base, 0, 0);
		add(bottomLeft, 0 - BOTTOM_LEFT_RADIUS, 0);
		
	}
	
	
	private static final int BASE_WIDTH = 50;
	private static final int BASE_HEIGHT = 15;
	private static final int BOTTOM_LEFT_RADIUS = 9;
	
	private GRect base;
	
	private GOval bottomLeft;
/*
 * colors added for Sleighran compound
 */
	Color cream = Color.decode("#F0ECEB");
	Color gold = Color.decode("#E4CB85");
	Color red = Color.decode("#971835");
	
}
