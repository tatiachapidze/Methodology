
/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
	/** big Circle's radius */
	private static final double rad1 = 72;
	/**middle circle's radius */
	private static final double rad2 = (1.65 * rad1) / 2.54;
	/** small circle's radius */
	private static final double rad3 = (0.76 * rad1) / 2.54;

	public void run() {
		drawTheTarget();
	}

	/*
	 * this method draws three circles, big circle which is filled with red color,
	 * middle circle, which is filled with white color and small circle, which is
	 * filled with red color
	 */
	private void drawTheTarget() {
		GOval bigCircle = createFilledCircle(getWidth() / 2, getHeight() / 2, rad1, Color.RED);
		add(bigCircle);
		GOval midCircle = createFilledCircle(getWidth() / 2, getHeight() / 2, rad2, Color.WHITE);
		add(midCircle);
		GOval smallCircle = createFilledCircle(getWidth() / 2, getHeight() / 2, rad3, Color.RED);
		add(smallCircle);
	}

	/* this method draws the circle which is filled with color */
	private GOval createFilledCircle(double x, double y, double r, Color color) {
		GOval circle = new GOval(x - r, y - r, 2 * r, 2 * r);
		circle.setFilled(true);
		circle.setColor(color);
		return circle;
	}
}
