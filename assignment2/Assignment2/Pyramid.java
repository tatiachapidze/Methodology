
/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;
	/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;
	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	public void run() {
		drawPyramid();
	}

	/* this method draws pyramid with bricks */
	private void drawPyramid() {
		double y = getHeight() - BRICK_HEIGHT;
		double x = (getWidth() / 2 - (double) BRICKS_IN_BASE / 2 * BRICK_WIDTH);
		for (int i = BRICKS_IN_BASE; i > 0; i--) {
			drawLine(y, i, x);
			y -= BRICK_HEIGHT;
			x += BRICK_WIDTH / 2;
		}
	}

	/*
	 * this method fills one line with Brick, the number of bricks decreases with 1
	 * in each following line
	 */
	private void drawLine(double y, int i, double x) {
		for (int j = i; j > 0; j--) {
			drawBrick(x, y);
			x += BRICK_WIDTH;
		}
	}

	/* this method draws one Brick */
	private void drawBrick(double x, double y) {
		GRect Brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
		add(Brick);
	}
}
