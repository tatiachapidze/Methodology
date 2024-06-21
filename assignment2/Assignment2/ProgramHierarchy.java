
/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {
	/** height of each rectangle */
	private static final double RectHeight = 30;
	/** width of each rectangle */
	private static final double RectWidth = 160;
	/** distance between rectangles */
	private static final double HorizontalGap = 20;
	/** distance between top and lower rectangle */
	private static final double VerticalGap = 80;

	public void run() {
		double width = getWidth() / 2;
		double height = getHeight() / 2;
		double a = RectWidth + HorizontalGap;
		drawTopRect(width, height);
		drawLowerRects(width, a);
		drawLines(width, a);
		addTexts(width, height, a);
	}

	/* this method adds 4 different texts in 4 different rectangles' centre */
	private void addTexts(double width, double height, double a) {
		GLabel text2 = createLabel("GraphicsProgram", width, height);
		add(text2);
		width += a;
		GLabel text3 = createLabel("ConsoleProgram", width, height);
		add(text3);
		width += a;
		GLabel text4 = createLabel("DialogProgram", width, height);
		add(text4);
	}

	/* this method creates text in the rectangle's centre */
	private GLabel createLabel(String text, double x, double y) {
		GLabel text1 = new GLabel(text);
		GLabel text2 = new GLabel(text, x - HorizontalGap - RectWidth - text1.getWidth() / 2,
				y + VerticalGap / 2 + RectHeight / 2 + text1.getAscent() / 2);
		return text2;
	}

	/*
	 * this method draws three lines, which are connector of the top rectangle and
	 * lower rectangles
	 */
	private void drawLines(double width, double a) {
		for (int i = 0; i < 3; i++) {
			GLine Line = createLines(width, getHeight() / 2);
			add(Line);
			width += a;
		}
	}

	/* this method draws three lower rectangles */
	private void drawLowerRects(double width, double a) {
		for (int i = 0; i < 3; i++) {
			GRect Rect = createGrect(width, getHeight() / 2);
			add(Rect);
			width += a;
		}
	}

	/* this method draws top rectangle with text in its centre */
	private void drawTopRect(double width, double height) {
		GRect topRect = new GRect(width - RectWidth / 2, height - VerticalGap / 2 - RectHeight, RectWidth, RectHeight);
		add(topRect);
		GLabel text = new GLabel("Program");
		GLabel text1 = new GLabel("Program", width - text.getWidth() / 2,
				height - VerticalGap / 2 - RectHeight / 2 + text.getAscent() / 2);
		add(text1);
		
	}

	/* this method creates rectangles with x and y coordinates */
	private GRect createGrect(double x, double y) {
		GRect rect = new GRect(x - RectWidth*3 / 2 - HorizontalGap, y + VerticalGap / 2, RectWidth,
				RectHeight);
		return rect;
	}

	/* this method draws lines with x and y coordinates */
	private GLine createLines(double x, double y) {
		GLine line = new GLine(getWidth() / 2, y - VerticalGap / 2, x - HorizontalGap - RectWidth, y + VerticalGap / 2);
		return line;
	}
}
