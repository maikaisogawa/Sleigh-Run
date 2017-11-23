import java.awt.Color;

import acm.graphics.GCompound;
import acm.graphics.GRect;

public class Kareldolph extends GCompound{
	
	public Kareldolph() {
		
		body = new GRect(BODY_WIDTH, BODY_HEIGHT);
		body.setColor(Color.WHITE);
		body.setFilled(true);
		body.setFillColor(Color.WHITE);
		add(body, 0, 0);
		
	}

	private static final int BODY_WIDTH = 20;
	private static final int BODY_HEIGHT = 30;
	
	private GRect body;
	
}
