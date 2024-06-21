
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		DrawScaffold();
		counter = 0;
		WrongLabel = null;
		wrong_letters = " ";
	}

	/* this method draws the Scaffold and creates empty label */
	private void DrawScaffold() {
		GLine Scaffold = new GLine(getWidth() / 2 - BEAM_LENGTH, getHeight() / 2 + SCAFFOLD_HEIGHT / 2,
				getWidth() / 2 - BEAM_LENGTH, getHeight() / 2 - SCAFFOLD_HEIGHT / 2);
		add(Scaffold);
		GLine Beam = new GLine(getWidth() / 2 - BEAM_LENGTH, getHeight() / 2 - SCAFFOLD_HEIGHT / 2, getWidth() / 2,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2);
		add(Beam);
		GLine Rope = new GLine(getWidth() / 2, getHeight() / 2 - SCAFFOLD_HEIGHT / 2, getWidth() / 2,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH);
		add(Rope);
		Label = new GLabel("", getWidth() / 2 - BEAM_LENGTH, getHeight() / 2 + SCAFFOLD_HEIGHT / 2 + 20);
		Label.setFont("-20");
		add(Label);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		Label.setLabel(word);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	public void noteIncorrectGuess(char letter) {
		wrong_letters += letter;
		counter++;
		DrawBody();
		WrongLabel = new GLabel(wrong_letters, getWidth() / 2 - BEAM_LENGTH,
				getHeight() / 2 + SCAFFOLD_HEIGHT / 2 + 40);
		add(WrongLabel);
	}

	/* this method draws the body parts */
	private void DrawBody() {
		if (counter == 1) {
			drawHead();
		} else if (counter == 2) {
			drawBody();
		} else if (counter == 3) {
			drawLeftArm();
		} else if (counter == 4) {
			drawRightArm();
		} else if (counter == 5) {
			drawLeftLeg();
		} else if (counter == 6) {
			drawRightLeg();
		} else if (counter == 7) {
			drawLeftFoot();
		} else if (counter == 8) {
			drawRightFoot();
		}

	}

	/* this method draws the right foot */
	private void drawRightFoot() {
		GLine RightFoot = new GLine(getWidth() / 2 + HIP_WIDTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				getWidth() / 2 + HIP_WIDTH + FOOT_LENGTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(RightFoot);
	}

	/* this method draws the left foot */
	private void drawLeftFoot() {
		GLine LeftFoot = new GLine(getWidth() / 2 - HIP_WIDTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				getWidth() / 2 - HIP_WIDTH - FOOT_LENGTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(LeftFoot);

	}

	/* this method draws right leg */
	private void drawRightLeg() {
		GLine RightLeg1 = new GLine(getWidth() / 2,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				getWidth() / 2 + HIP_WIDTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		add(RightLeg1);
		GLine RightLeg2 = new GLine(getWidth() / 2 + HIP_WIDTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				getWidth() / 2 + HIP_WIDTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(RightLeg2);

	}

	/* this method draws left leg */
	private void drawLeftLeg() {
		GLine LeftLeg1 = new GLine(getWidth() / 2,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				getWidth() / 2 - HIP_WIDTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		add(LeftLeg1);
		GLine LeftLeg2 = new GLine(getWidth() / 2 - HIP_WIDTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				getWidth() / 2 - HIP_WIDTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(LeftLeg2);

	}

	/* this method draws right arm */
	private void drawRightArm() {
		GLine RightArm1 = new GLine(getWidth() / 2,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				getWidth() / 2 + UPPER_ARM_LENGTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		add(RightArm1);
		GLine RightArm2 = new GLine(getWidth() / 2 + UPPER_ARM_LENGTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				getWidth() / 2 + UPPER_ARM_LENGTH, getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS
						+ ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(RightArm2);

	}

	/* this method draws left arm */
	private void drawLeftArm() {
		GLine LeftArm1 = new GLine(getWidth() / 2 - UPPER_ARM_LENGTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				getWidth() / 2,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		add(LeftArm1);
		GLine LeftArm2 = new GLine(getWidth() / 2 - UPPER_ARM_LENGTH,
				getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				getWidth() / 2 - UPPER_ARM_LENGTH, getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS
						+ ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(LeftArm2);
	}

	/* this method draws the body */
	private void drawBody() {
		GLine body = new GLine(getWidth() / 2, getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS,
				getWidth() / 2, getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		add(body);

	}

	/* this method draws the head */
	private void drawHead() {
		GOval head = new GOval(getWidth() / 2 - HEAD_RADIUS, getHeight() / 2 - SCAFFOLD_HEIGHT / 2 + ROPE_LENGTH,
				2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(head);
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private String wrong_letters = "";
	private GLabel WrongLabel;
	private int counter = 0;
	private GLabel Label;
}
