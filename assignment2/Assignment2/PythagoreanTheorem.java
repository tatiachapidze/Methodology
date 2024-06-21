
/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("Enter values to compute Pythagorean theorem.");
		double a = Check(1);
		double b = Check(2);
		double ans = count_c(a, b);
		println("c = " + ans);
	}

	/*
	 * the length of the sides are entered and this method counts the length of
	 * hypotenuse with the Pythagorean Theorem
	 */
	private double count_c(double a, double b) {
		double c = Math.sqrt(a * a + b * b);
		return c;
	}

	/*
	 * this method checks whether the numbers that are entered are positive or not
	 * and they will be entered until aren't positive
	 */
	private double Check(int x) {
		double side = 0;
		while (true) {
			if (x == 1) {
				side = readInt("a: ");
			}
			if (x == 2) {
				side = readInt("b: ");
			}
			if (side > 0) {
				break;
			} else {
				println("length of the side must be positive");
			}
		}
		return side;
	}
}
