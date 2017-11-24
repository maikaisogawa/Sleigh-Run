import java.awt.Color;

import acm.graphics.GCompound;
import acm.graphics.GLine;
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
		add(square, 4, 3);
		
		line = new GLine(0, 0, LINE_LENGTH, 0);
		line.setColor(Color.BLACK);
		add(line, 4 + SQUARE_WIDTH / 2, 8 + SQUARE_HEIGHT);
		
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
		
		leg = makeLeg(LEG_SIZE);
		leg.setColor(cream);
		leg.setFilled(true);
		leg.setFillColor(Color.BLACK);
		add(leg, BODY_WIDTH / 2, BODY_HEIGHT);
		
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
	
	private GPolygon makeLeg(double size){
		GPolygon poly = new GPolygon();
		poly.addVertex(0, 0);
		poly.addVertex(LEG_SIZE / 2, 0);
		poly.addVertex(LEG_SIZE / 2, LEG_SIZE / 2);
		poly.addVertex(LEG_SIZE, LEG_SIZE / 2);
		poly.addVertex(LEG_SIZE, LEG_SIZE);
		poly.addVertex(0, LEG_SIZE);
		return poly;
	}

	private static final int BODY_WIDTH = 20;
	private static final int BODY_HEIGHT = 30;
	private static final int TRIANGLE = 5;
	private static final int SQUARE_WIDTH = 10;
	private static final int SQUARE_HEIGHT = 15;
	private static final int LINE_LENGTH = 8;
	private static final int LEG_SIZE = 8;
	
	private GRect body;
	private GPolygon rightTriangle;
	private GPolygon leftTriangle;
	private GRect square;
	private GLine line;
	private GPolygon leg;
	
	Color cream = Color.decode("#F0ECEB");
	Color sky = Color.decode("#1E2B55");
	
}
