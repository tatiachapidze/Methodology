
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {
	private double Gap;
	private ArrayList<NameSurferEntry> allEntry;

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		allEntry = new ArrayList<NameSurferEntry>();

	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		allEntry.clear();
		update();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note that
	 * this method does not actually draw the graph, but simply stores the entry;
	 * the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		allEntry.add(entry);
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of entries.
	 * Your application must call update after calling either clear or addEntry;
	 * update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		drawGrahp();
		double VertGap = (double) (getHeight() - 2 * GRAPH_MARGIN_SIZE) / (MAX_RANK - 1);
		for (int i = 0; i < allEntry.size(); i++) {
			switch (i % 4) {
			case 0:
				drawEntry(allEntry.get(i), VertGap, Color.black);
				break;
			case 1:
				drawEntry(allEntry.get(i), VertGap, Color.red);
				break;
			case 2:
				drawEntry(allEntry.get(i), VertGap, Color.blue);
				break;
			case 3:
				drawEntry(allEntry.get(i), VertGap, Color.yellow);
				break;
			}

		}

	}

	/* this method draws graph according to the entered name's ranks */
	private void drawEntry(NameSurferEntry nameSurferEntry, double VertGap, Color color) {
		double y0 = 0;
		double y1 = 0;
		for (int i = 0; i < NDECADES - 1; i++) {
			y0 = VertGap * (nameSurferEntry.getRank(i) - 1) + GRAPH_MARGIN_SIZE;
			y1 = VertGap * (nameSurferEntry.getRank(i + 1) - 1) + GRAPH_MARGIN_SIZE;
			checkCoordinates(nameSurferEntry, i, y0, y1, color);
		}
		GLabel Name = new GLabel(nameSurferEntry.getName() + nameSurferEntry.getRank(NDECADES - 1), getWidth() - Gap,
				GRAPH_MARGIN_SIZE + VertGap * nameSurferEntry.getRank(NDECADES - 1));
		if (nameSurferEntry.getRank(NDECADES - 1) == 0) {
			Name = new GLabel(nameSurferEntry.getName() + "*", getWidth() - Gap, getHeight() - GRAPH_MARGIN_SIZE);
		}
		Name.setColor(color);
		add(Name);
	}

	/* this method checks if the rank is 0, and changes coordinates */
	private void checkCoordinates(NameSurferEntry nameSurferEntry, int i, double y0, double y1, Color color) {
		GLabel Name = new GLabel(nameSurferEntry.getName() + " " + nameSurferEntry.getRank(i), i * Gap, y0);
		if (nameSurferEntry.getRank(i) == 0) {
			y0 = getHeight() - GRAPH_MARGIN_SIZE;
			Name = new GLabel(nameSurferEntry.getName() + "*", i * Gap, y0);
		}
		if (nameSurferEntry.getRank(i + 1) == 0) {
			y1 = getHeight() - GRAPH_MARGIN_SIZE;
		}
		GLine Line = new GLine(i * Gap, y0, Gap * (i + 1), y1);
		Line.setColor(color);
		add(Line);
		Name.setColor(color);
		add(Name);
	}

	/* this method draws background grid */
	private void drawGrahp() {
		drawVerticalLines();
		drawHorizontalLines();
		addDateLabels();

	}

	/* this method adds date labels at the bottom of the graph */
	private void addDateLabels() {
		double x = 5;
		for (int i = 0; i < NDECADES; i++) {
			String str = Integer.toString(START_DECADE + i * 10);
			GLabel Date = new GLabel(str, x, getHeight() - 2);
			add(Date);
			x += Gap;
		}
	}

	/* this method draws background horizontal lines */
	private void drawHorizontalLines() {
		double y = GRAPH_MARGIN_SIZE;
		for (int i = 0; i < 2; i++) {
			GLine HorizontLine = new GLine(0, y, getWidth(), y);
			add(HorizontLine);
			y = getHeight() - GRAPH_MARGIN_SIZE;
		}
	}

	/* this method draws background vertical lines */
	private void drawVerticalLines() {
		Gap = getWidth() / NDECADES;
		double x = Gap;
		for (int i = 0; i < NDECADES; i++) {
			GLine Line = new GLine(x, 0, x, getHeight());
			add(Line);
			x += Gap;
		}

	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}

}
