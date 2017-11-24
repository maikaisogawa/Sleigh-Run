import java.awt.Color;

import acm.graphics.GArc;
import acm.graphics.GCompound;

public class Rope extends GCompound {

	public Rope() {
		ropeStart = new GArc(2 * RADIUS, 2 * RADIUS, 60, 40);
		ropeStart.setColor(gold);
		add(ropeStart, 0, 0);
	}
	
	private static final int RADIUS = 120;
	
	private GArc ropeStart;
	
	Color gold = Color.decode("#E4CB85");
}
