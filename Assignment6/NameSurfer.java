
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	private JTextField textField = new JTextField(20);
	private JButton GraphButton = new JButton("Graph");
	private JButton ClearButton = new JButton("Clear");
	private NameSurferGraph graph;

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		graph = new NameSurferGraph();
		add(graph);

		add(new JLabel("Name"), SOUTH);
		add(textField, SOUTH);
		add(GraphButton, SOUTH);
		add(ClearButton, SOUTH);
		textField.addActionListener(this);
		addActionListeners();
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String name = "";
		NameSurferEntry entry = null;
		if (e.getSource() == GraphButton || e.getSource() == textField && !textField.getText().equals("")) {
			name = textField.getText().toUpperCase();
			NameSurferDataBase base = new NameSurferDataBase(NAMES_DATA_FILE);
			entry = base.findEntry(name);
			if (entry != null) {
				graph.addEntry(entry);
				graph.update();
			}
			textField.setText("");
		}
		if (e.getSource() == ClearButton) {
			graph.clear();
		}
	}
}
