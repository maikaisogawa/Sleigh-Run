import java.awt.Color;

import acm.graphics.GCompound;
import acm.graphics.GRect;

public class Kareldolph extends GCompound{
	
	public Kareldolph() {
		
		body = new GRect(BODY_WIDTH, BODY_HEIGHT);
		body.setColor(cream);
		body.setFilled(true);
		body.setFillColor(cream);
		add(body, 0, 0);
		
	}

	private static final int BODY_WIDTH = 20;
	private static final int BODY_HEIGHT = 30;
	
	private GRect body;
	
	Color cream = Color.decode("#F0ECEB");
	
}
