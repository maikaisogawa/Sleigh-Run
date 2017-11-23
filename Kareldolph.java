import java.awt.Color;

import acm.graphics.GCompound;
import acm.graphics.GPolygon;
import acm.graphics.GRect;

public class Kareldolph extends GCompound{
	
	public Kareldolph() {
		
		body = new GRect(BODY_WIDTH, BODY_HEIGHT);
		body.setColor(cream);
		body.setFilled(true);
		body.setFillColor(cream);
		add(body, 0, 0);
		
		square = new GRect(SQUARE_WIDTH, SQUARE_HEIGHT);
		square.setColor(Color.BLACK);
		add(square, 3, 4);
		
		rightTriangle = makeRightTriangle(TRIANGLE, TRIANGLE);
		rightTriangle.setColor(sky);
		rightTriangle.setFilled(true);
		rightTriangle.setFillColor(sky);
		add(rightTriangle, BODY_WIDTH - TRIANGLE, 0);
		
		leftTriangle = makeLeftTriangle(TRIANGLE, TRIANGLE);
		leftTriangle.setColor(sky);
		leftTriangle.setFilled(true);
		leftTriangle.setFillColor(sky);
		add(leftTriangle, 0, BODY_HEIGHT - TRIANGLE);
		
	}
	
	private GPolygon makeRightTriangle(double width, double height){ 
		GPolygon poly = new GPolygon();
		poly.addVertex(0, 0);
		poly.addVertex(width, 0);
		poly.addVertex(width, height);
		return poly;
	}
	
	private GPolygon makeLeftTriangle(double width, double height){ 
		GPolygon poly = new GPolygon();
		poly.addVertex(0, 0);
		poly.addVertex(width, height);
		poly.addVertex(0, height);
		return poly;
	}

	private static final int BODY_WIDTH = 20;
	private static final int BODY_HEIGHT = 30;
	private static final int TRIANGLE = 5;
	private static final int SQUARE_WIDTH = 8;
	private static final int SQUARE_HEIGHT = 15;
	
	private GRect body;
	private GPolygon rightTriangle;
	private GPolygon leftTriangle;
	private GRect square;
	
	Color cream = Color.decode("#F0ECEB");
	Color sky = Color.decode("#1E2B55");
	
}
