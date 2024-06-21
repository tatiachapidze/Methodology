
/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		int a = Check_a();
		int answer = numberOfMoves(a);
		println("The process took " + answer + " to reach 1");
	}

	/*
	 * this method reaches to one and counts how many moves does it took to reach
	 * one
	 */
	private int numberOfMoves(int a) {
		int numberOfMoves = 0;
		while (a != 1) {
			a = nextNumber(a);
			numberOfMoves++;
		}
		return numberOfMoves;
	}

	/*
	 * this method checks if the number is even or odd, makes special calculations
	 * and returns the calculated number
	 */
	private int nextNumber(int a) {
		int recentValue = a;
		if (a % 2 == 0) {
			a = a / 2;
			println(recentValue + " is even so i take half: " + a);
		} else {
			a = 3 * a + 1;
			println(recentValue + " is odd, so i make 3n+1: " + a);
		}
		return a;
	}

	/* this method checks if the entered number is positive */
	private int Check_a() {
		int value;
		while (true) {
			value = readInt("Enter a number: ");
			if (value > 0) {
				break;
			} else {
				println("please enter positive number");
			}
		}
		return value;
	}
}