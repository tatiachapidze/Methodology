
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.util.ArrayList;

public class Hangman extends ConsoleProgram {
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private String Hang_Word;
	private String[] userWord;
	private ArrayList<String> usedLetters = new ArrayList<String>();
	private int turn_N = 8;
	private ArrayList<Integer> correctLetterIndex;
	private HangmanCanvas canvas;

	/* initializes canvas and adds it to the window while run is working */
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		gameProcess();
	}

	private void gameProcess() {
		while (true) {
			setUp();
			while (CheckAvailability()) {
				action();
			}
			printResult();
			usedLetters.clear();
			correctLetterIndex.clear();
			turn_N = 8;
			if (!PlayAgain()) {
				break;
			}
		}

	}

	/* this method checks is the user wants to play again */
	private boolean PlayAgain() {
		String answer = readLine("Do you want to play again? (Please enter yes or no): ");
		answer = answer.toUpperCase();
		if (answer.equals("YES")) {
			return true;

		}
		return false;
	}

	/* This method prints result texts whether you won or lost after one game */
	private void printResult() {
		if (turn_N == 0) {
			println("The word was: " + Hang_Word);
			println("you lose.");
		} else {
			println("you guessed the word " + Hang_Word);
			println("you win.");
		}
	}

	/* This method checks the word is guessed or not */
	private boolean CheckAvailability() {
		for (int i = 0; i < Hang_Word.length(); i++) {
			if (userWord[i] == "-" && turn_N > 0) {
				return true;
			}
		}
		return false;
	}

	/* this method set ups the game */
	private void setUp() {
		canvas.reset();
		HangmanLexicon words = new HangmanLexicon();
		int wordcount = words.getWordCount();
		int word_Index = rgen.nextInt(0, wordcount - 1);
		Hang_Word = words.getWord(word_Index);
		userWord = new String[Hang_Word.length()];
		notification();
	}

	/* this method prints starting texts */
	private void notification() {
		println("Welcome to Hangman! ");
		println(Hang_Word);
		for (int i = 0; i < Hang_Word.length(); i++) {
			userWord[i] = "-";
		}
		printingWord(userWord);
		canvas.displayWord(wordJoiner());
		println("you have " + turn_N + " guesses left.");
	}

	/* this method joins letters from string array */
	private String wordJoiner() {
		String canvaString = " ";
		for (int i = 0; i < userWord.length; i++) {
			canvaString = canvaString + userWord[i];
		}
		return canvaString;
	}

	/* process of game */
	private void action() {
		print("your guess: ");
		String input = readLine();
		input = input.toUpperCase();
		while (!isValidate(input)) {
			input = again();
		}
		checkEquality(input);
		printTexts();
	}

	/* this method checks if the entered letter is validate */
	private boolean isValidate(String input) {
		if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
			println("Invalid Symbol, Please enter again Letter");
			return false;
		}
		if (checkUsedLetters(input)) {
			return false;
		}
		return true;
	}

	/*
	 * this method checks if the entered letter is correct or not and prints
	 * appropriate text
	 */
	private void checkEquality(String input) {
		if (check(input)) {
			for (int i = 0; i < correctLetterIndex.size(); i++) {
				userWord[(int) correctLetterIndex.get(i)] = "" + input.charAt(0);
			}
			println("That guess is correct");
			canvas.displayWord(wordJoiner());
			usedLetters.add(input);
		} else {
			println("There are no " + input + "'s in the word");
			canvas.noteIncorrectGuess(input.charAt(0));
			turn_N--;
		}
	}

	/* this method prints the texts after every action */
	private void printTexts() {
		printingWord(userWord);
		println("you have " + turn_N + " guesses left.");
	}

	/* this method checks if the user has entered already used letter */
	private boolean checkUsedLetters(String input) {
		for (int i = 0; i < usedLetters.size(); i++) {
			if (usedLetters.get(i).equals(input)) {
				println("you've already entered this letter, try again: ");
				return true;
			}
		}
		return false;
	}

	/*
	 * this method enables user to enter letter again if the previous letter is used
	 * or isn't letter
	 */
	private String again() {
		print("your guess: ");
		String result = readLine();
		result = result.toUpperCase();
		return result;
	}

	/* this method prints word with dashes */
	private void printingWord(String[] userWord2) {
		print("The word now looks like this: ");
		for (int i = 0; i < userWord2.length - 1; i++) {
			print(userWord2[i]);
		}
		println(userWord2[userWord2.length - 1]);
	}

	/* this method checks if the entered letter is correct or not */
	private boolean check(String input) {
		boolean result = false;
		correctLetterIndex = new ArrayList<Integer>();
		for (int i = 0; i < Hang_Word.length(); i++) {
			if (input.charAt(0) == Hang_Word.charAt(i)) {
				correctLetterIndex.add(i);
				result = true;
			}
		}
		return result;
	}

}
