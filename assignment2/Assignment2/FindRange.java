
/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	private static final int SENTINEL = 0;

	public void run() {
		headLine();
		findRange();
	}

	/* this method prints out the headline */
	private void headLine() {
		println("This program finds the largest and smallest numbers.");
	}

	/*
	 * this method compares new numbers with existing numbers and sorts which one is
	 * max and which one is min, when the sentinel is entered program stops
	 */
	private void findRange() {
		int value = readInt("? ");
		int max = value;
		int min = value;
		while (value != SENTINEL) {
			max = Math.max(max, value);
			min = Math.min(min, value);
			value = readInt("? ");
		}
		printAnswer(max, min);
	}

	/*
	 * this method prints out answer: from entered numbers which one is smallest and
	 * which one is largest, but if the first number is sentinel, prints out special
	 * message, and if only one number is entered, prints out that it's both
	 * smallest and largest
	 */
	private void printAnswer(int max, int min) {
		if (max == SENTINEL) {
			println("It's a SENTINEL");
		} else {
			println("smallest: " + min);
			println("largest: " + max);
		}
	}
}
