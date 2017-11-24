import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import acm.graphics.GCompound;
import acm.graphics.GImage;

public class Drone extends GCompound {

	public Drone() {
		
		Image droneImage = null;	
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("drone.png");
		try {
			droneImage = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GImage drone = new GImage(droneImage);
		drone.scale(0.25);
		add(drone, 0, 0);
	}
}
