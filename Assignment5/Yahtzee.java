
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.Arrays;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	/* process of whole game */
	private void playGame() {
		fullScores = new int[nPlayers][TOTAL];
		finalScores = new int[nPlayers];
		initializingScores();
		gameProcess();
		insertScores();
	}

	/*
	 * this method inserts Upper, lower and bonus score. Prints the final result-
	 * who is the winner
	 */
	private void insertScores() {
		int winnerIndex = 0;
		int highestScore = 0;
		for (int i = 0; i < nPlayers; i++) {
			display.updateScorecard(UPPER_SCORE, i + 1, fullScores[i][UPPER_SCORE]);
			display.updateScorecard(LOWER_SCORE, i + 1, fullScores[i][LOWER_SCORE]);
			if (fullScores[i][UPPER_SCORE] > 63) {
				fullScores[i][UPPER_BONUS] = 35;
			} else {
				fullScores[i][UPPER_BONUS] = 0;
			}
			display.updateScorecard(UPPER_BONUS, i + 1, fullScores[i][UPPER_BONUS]);
			display.updateScorecard(TOTAL, i + 1, finalScores[i] + fullScores[i][UPPER_BONUS]);
			if (highestScore < finalScores[i]) {
				highestScore = finalScores[i];
				winnerIndex = i;
			}
		}
		display.printMessage("Congratulations, " +playerNames[winnerIndex] + ", You're the winner with a total score of " + highestScore+ "!");
	}

	/* process of game */
	private void gameProcess() {
		int playerIndex = 0;
		for (int i = 0; i < N_SCORING_CATEGORIES * nPlayers; i++) {
			int[] dice = generateDice();
			dice = oneFullMove(playerIndex, dice);
			display.printMessage("select category");
			int category = display.waitForPlayerToSelectCategory();
			int score = getScore(category, dice);
			updateScores(category, playerIndex, score, dice);
			if (playerIndex == nPlayers - 1) {
				playerIndex = 0;
			} else {
				playerIndex = playerIndex + 1;
			}
		}
	}

	/* this method initializes the starting scores of every category */
	private void initializingScores() {
		for (int i = 0; i < nPlayers; i++) {
			for (int j = 0; j < 17; j++) {
				fullScores[i][j] = -1;
			}
			fullScores[i][UPPER_SCORE] = fullScores[i][LOWER_SCORE] = 0;
		}

	}

	/* this method returns final dice after re-rolling */
	private int[] oneFullMove(int playerIndex, int[] dice) {
		display.printMessage(playerNames[playerIndex] + "'s turn. Click \"Roll Dice\" button to roll the dice. ");
		display.waitForPlayerToClickRoll(playerIndex + 1);
		display.displayDice(dice);
		display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\"");
		display.waitForPlayerToSelectDice();
		if (CheckNextMove(dice)) {
			dice = changeDice(dice);
			display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\"");
			display.waitForPlayerToSelectDice();
			dice = changeDice(dice);
		}
		return dice;
	}

	/* this method checks if the dice is selected again */
	private boolean CheckNextMove(int[] dice) {
		for (int i = 0; i < dice.length; i++) {
			if (display.isDieSelected(i)) {
				return true;
			}
		}
		return false;
	}

	/* this method updates scores after one whole move */
	private void updateScores(int category, int playerIndex, int score, int[] dice) {
		while (true) {
			if (fullScores[playerIndex][category] == -1) {
				finalScores[playerIndex] = finalScores[playerIndex] + score;
				fullScores[playerIndex][category] = score;
				display.updateScorecard(category, playerIndex + 1, score);
				display.updateScorecard(TOTAL, playerIndex + 1, finalScores[playerIndex]);
				if (category <= 6) {
					fullScores[playerIndex][UPPER_SCORE] = fullScores[playerIndex][UPPER_SCORE] + score;
				} else {
					fullScores[playerIndex][LOWER_SCORE] = fullScores[playerIndex][LOWER_SCORE] + score;
				}
				break;
			} else {
				display.printMessage(playerNames[playerIndex] + " choose another category !!!");
				category = display.waitForPlayerToSelectCategory();
				score = getScore(category, dice);
			}
		}
	}

	/* this method returns the scores according to the category */
	private int getScore(int category, int[] dice) {
		Arrays.sort(dice);
		int sum = Arrays.stream(dice).sum();
		int result = 0;
		switch (category) {
		case ONES:
		case TWOS:
		case THREES:
		case FOURS:
		case FIVES:
		case SIXES:
			result = checkOnestoSixes(dice, category);
			break;
		case THREE_OF_A_KIND:
			result = checkThreeOfAKind(dice, category, sum);
			break;
		case FOUR_OF_A_KIND:
		case YAHTZEE:
			result = checkYahtzeeAndFourKind(dice, category, sum);
			break;
		case FULL_HOUSE:
			if (dice[0] == dice[1] && dice[2] == dice[4] || dice[0] == dice[2] && dice[3] == dice[4]) {
				result = 25;
			}
			break;
		case SMALL_STRAIGHT:
		case LARGE_STRAIGHT:
			result = checkStraights(dice, category);
			break;
		case CHANCE:
			result = sum;
			break;
		}
		return result;
	}

	/*
	 * this method checks if the dice configuration is valid for Small Straight and
	 * Large Straight
	 */
	private int checkStraights(int[] dice, int category) {
		int count = 0;
		for (int i = 0; i < dice.length - 1; i++) {
			if (dice[i] + 1 == dice[i + 1]) {
				count++;
			}
		}
		if (count >= 3 && category == SMALL_STRAIGHT) {
			return 30;
		} else if (count == 4 && category == LARGE_STRAIGHT) {
			return 40;
		}

		return 0;

	}

	/*
	 * this method checks if the dice configuration is valid for Yahtzee and Four of
	 * a Kind
	 */
	private int checkYahtzeeAndFourKind(int[] dice, int category, int sum) {
		int count = 0;
		for (int i = 0; i < dice.length - 1; i++) {
			if (dice[i] == dice[i + 1]) {
				count++;
			}
		}
		if (count >=3 && category == FOUR_OF_A_KIND && dice[1] == dice[3]) {
			return sum;
		} else if (count == 4 && category == YAHTZEE) {
			return 50;
		}
		return 0;
	}

	/*
	 * this method checks if the dice configuration is valid for the three of a Kind
	 */
	private int checkThreeOfAKind(int[] dice, int category, int sum) {
		for (int i = 0; i < dice.length - 2; i++) {
			if (dice[i] == dice[i + 1] && dice[i + 1] == dice[i + 2]) {
				return sum;
			}
		}
		return 0;
	}

	/*
	 * this method checks is the dice configuration is valid for twos, threes,
	 * fours, fives, sixes
	 */
	private int checkOnestoSixes(int[] dice, int category) {
		int sum = 0;
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == category) {
				sum += category;
			}
		}
		return sum;
	}

	/* this method changes the dice after choosing it again */
	private int[] changeDice(int[] dice) {
		for (int i = 0; i < dice.length; i++) {
			if (display.isDieSelected(i)) {
				dice[i] = rgen.nextInt(1, 6);
				display.displayDice(dice);
			}
		}
		return dice;
	}

	/* this method returns the first configuration if dice */
	private int[] generateDice() {
		int[] dice = new int[N_DICE];
		for (int i = 0; i < dice.length; i++) {
			dice[i] = rgen.nextInt(1, 6);
		}
		return dice;
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[][] fullScores;// [index of player][category]
	private int[] finalScores;

}
