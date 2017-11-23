import java.awt.Color;
import acm.graphics.GCompound;
import acm.graphics.GRect;

public class Sleighran extends GCompound{

	public Sleighran() {
		
		base = new GRect(BASE_WIDTH, BASE_HEIGHT);
		base.setColor(cream);
		base.setFilled(true);
		base.setFillColor(Color.red);
		
		add(base, 0, 0);
		
	}
	
	
	private static final int BASE_WIDTH = 50;
	private static final int BASE_HEIGHT = 20;
	
	private GRect base;
/*
 * colors added for Sleighran compound
 */
	Color cream = Color.decode("#F0ECEB");
	Color gold = Color.decode("#E4CB85");
	Color red = Color.decode("#A6201A");
	
}
