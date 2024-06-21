
import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout_extention extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** Number of Bricks at the beginnig of the game */
	private int BRICK_AMOUNT = NBRICK_ROWS * NBRICKS_PER_ROW;

	/** Private instance variable */
	private RandomGenerator rgen = RandomGenerator.getInstance();

	/** velocity if the ball */
	private double vx;
	private double vy;

	/** Private GOval */
	private GOval Ball;

	/** Private GRect */
	private GRect Paddle;

	/* creates the heart images */
	private GImage heart1 = new GImage("fullHeart.png");
	private GImage heart2 = new GImage("fullHeart.png");
	private GImage heart3 = new GImage("fullHeart.png");
	private GImage brokenheart1 = new GImage("brokenheart.png");
	private GImage brokenheart2 = new GImage("brokenheart.png");
	private GImage brokenheart3 = new GImage("brokenheart.png");

	/** private Glabels and GImage */
	private GLabel ScoreValue;
	private GLabel finalresult;
	private GImage background = new GImage("Background.jpg");
	private GLabel text;

	/* the number of score */
	private int score = 0;

	/** Number of turns */
	private int a = NTURNS;

	/** Time of pause */
	private int pause_time = 20;
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		inicializing();
		addMouseListeners(); /* this method enbables us to get mouse events */
		GameProcess();
	}

	/* Process of game */
	private void GameProcess() {
		movingBall();
		gameResult(result());
	}

	/* this method moves ball until player losses or wins */
	private void movingBall() {
		while (a != 0) {
			waitForClick();
			vy = 3.0;
			vx = rgen.nextDouble(1.0, 3.0);
			if (rgen.nextBoolean(0.5))
				vx = -vx;
			moving();
			if (BRICK_AMOUNT != 0) {
				hittingBottom();
			} else {
				break;
			}
		}
	}

	/*
	 * this method changes heart image with broken heart image, decreases number of
	 * turns and player's score after the ball hits the bottom wall
	 */
	private void hittingBottom() {
		remove(getElementAt(getWidth() - a * heart1.getWidth(), 0));
		addBrokenheart();
		pause(200);
		a--;
		changeScore(-20);
		drawBall();
	}

	/* this method adds broken heart image after ball hits the bottom wall */
	private void addBrokenheart() {
		if (a == 3) {
			brokenheart1.setSize(heart1.getWidth(), heart1.getHeight());
			add(brokenheart1, getWidth() - a * heart1.getWidth(), 0);
		}
		if (a == 2) {
			brokenheart2.setSize(heart1.getWidth(), heart1.getHeight());
			add(brokenheart2, getWidth() - a * heart1.getWidth(), 0);
		}
		if (a == 1) {
			brokenheart3.setSize(heart1.getWidth(), heart1.getHeight());
			add(brokenheart3, getWidth() - a * heart1.getWidth(), 0);
		}
	}

	/* this method prints final resilts after the game is finished */
	private void gameResult(String result) {
		removeAll();
		finalresult = createLabel(result);
		add(finalresult);
		GLabel FinalScore = createFinalScore();
		add(FinalScore);
	}

	/* This method creates text about final score */
	private GLabel createFinalScore() {
		GLabel FinalScore = new GLabel("Final score : " + score);
		FinalScore.setFont("SansSerif-29");
		FinalScore.setLocation(getWidth() / 2 - FinalScore.getWidth() / 2,
				getHeight() / 2 + finalresult.getAscent() + FinalScore.getAscent() / 2);
		return FinalScore;
	}

	/* this method prinths whether the player won or lost */
	private String result() {
		String result;
		if (BRICK_AMOUNT == 0) {
			result = "YOU WON";
		} else {
			result = "GAME OVER";
		}
		return result;
	}

	/* this method creates the label */
	private GLabel createLabel(String text) {
		GLabel Text = new GLabel(text);
		Text.setFont("SansSerif-36");
		Text.setLocation(getWidth() / 2 - Text.getWidth() / 2, getHeight() / 2 + Text.getAscent() / 2);
		return Text;
	}

	/* This method moves the ball until ball hits the lower wall */
	private void moving() {
		while (true) {
			Ball.move(vx, vy);
			pause(pause_time);
			checkSides();
			ObjectRemover();
			if (Ball.getY() + 2 * BALL_RADIUS >= getHeight() || BRICK_AMOUNT == 0) {
				remove(Ball);
				break;
			}
		}
	}

	/* this method checks the objects that the ball has hit and removes them */
	private void ObjectRemover() {
		GObject collider = getCollidingObject();
		CheckPaddle(collider);

		if (collider != null && collider != background && collider != text && collider != ScoreValue
				&& collider != heart1 && collider != heart2 && collider != heart3 && collider != brokenheart1
				&& collider != brokenheart2 && collider != brokenheart3 && collider != Paddle) { 
			bounceClip.play();
			remove(collider);
			vy = -vy;
			BRICK_AMOUNT--;
			changeScore(5);
		}
	}

	/*
	 * this method checks whether the object that the ball has hit is paddle or not,
	 * and changes the ball direction according to the hitting point
	 */
	private void CheckPaddle(GObject collider) {
		if (collider == Paddle && Ball.getX() + BALL_RADIUS / 2 <= Paddle.getX() + PADDLE_WIDTH / 2) {
			changeDirection();
			if (vx > 0) {
				vx = -vx;
			}
		} else if (collider == Paddle && Ball.getX() + BALL_RADIUS / 2 > Paddle.getX() + PADDLE_WIDTH / 2) {
			changeDirection();
			if (vx < 0) {
				vx = -vx;
			}
		}
	}

	/* this method changes the ball direction and velocity */
	private void changeDirection() {
		double diff = Ball.getY() - (Paddle.getY() - 2 * BALL_RADIUS);
		Ball.move(0, - diff);
		vy = -vy;

	}

	/* this method changes player's score */
	private void changeScore(int changeValue) {
		score = score + changeValue;
		ScoreValue.setLabel("" + score);
	}

	/*
	 * This method checks the coordinates of the ball as it should move inside the
	 * window and not go beyond the walls
	 */
	private void checkSides() {
		if (Ball.getX() <= 0 || Ball.getX() + 2 * BALL_RADIUS >= getWidth()) {
			vx = -vx;
		}
		if (Ball.getY() <= 0) {
			vy = -vy;
		}
	}

	/* This method set ups the game */
	private void inicializing() {
		addBackground();
		drawBricks();
		drawPaddle();
		drawBall();
		lives();
		scoring();
	}

	/* This method adds background */
	private void addBackground() {
		setBackground(Color.MAGENTA);
		background = new GImage("Background.jpg");
		background.setSize(getWidth(), getHeight() * 3 / 4);
		add(background);
	}

	/* This value adds text about score */
	private void scoring() {
		text = new GLabel("Score:");
		text.setFont("SansSerif-30");
		text.setLocation(0, 30);
		text.setColor(Color.GREEN);
		add(text);
		ScoreValue = new GLabel("" + score);
		ScoreValue.setFont("SansSerif-30");
		ScoreValue.setColor(Color.GREEN);
		ScoreValue.setLocation(text.getWidth(), 30);
		add(ScoreValue);
	}

	/* this method adds three heart images */
	private void lives() {
		add(heart1, getWidth() - heart1.getWidth(), 0);
		add(heart2, getWidth() - 2 * heart2.getWidth(), 0);
		add(heart3, getWidth() - 3 * heart3.getWidth(), 0);
	}

	/* This method draws the ball */
	private void drawBall() {
		Ball = new GOval(getWidth() / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
		Ball.setFilled(true);
		Ball.setColor(Color.GREEN);
		add(Ball);
	}

	/* This method draws the paddle */
	private void drawPaddle() {
		Paddle = new GRect(getWidth() / 2 - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT,
				PADDLE_WIDTH, PADDLE_HEIGHT);
		Paddle.setFilled(true);
		add(Paddle);

	}

	/* This method draws Bricks */
	private void drawBricks() {
		double x;
		double y = BRICK_Y_OFFSET;
		if (NBRICKS_PER_ROW % 2 == 0) {
			x = (double) getWidth() / 2 - (double) BRICK_SEP / 2 - NBRICKS_PER_ROW / 2 * BRICK_WIDTH
					- (double) (NBRICKS_PER_ROW / 2 - 1) * BRICK_SEP;
		} else {
			x = (double) getWidth() / 2 - (double) BRICK_WIDTH / 2 - (double) (NBRICKS_PER_ROW - 1) / 2 * BRICK_WIDTH
					- (double) (NBRICKS_PER_ROW - 1) / 2 * BRICK_SEP;
		}
		for (int i = 0; i < NBRICK_ROWS; i++) {
			drawLine(y, i, x);
			y += BRICK_HEIGHT + BRICK_SEP;
		}
	}

	/* this method fills one line with bricks */
	private void drawLine(double y, int i, double x) {
		for (int j = 0; j < NBRICKS_PER_ROW; j++) {
			drawBrick(x, y, i);
			x += BRICK_WIDTH + BRICK_SEP;
		}

	}

	/* this method draws one filled brick, the color depends one the line */
	private void drawBrick(double x, double y, int i) {
		GRect Brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
		if (i < 2) {
			Brick.setColor(Color.RED);
		} else if (i < 4) {
			Brick.setColor(Color.ORANGE);
		} else if (i < 6) {
			Brick.setColor(Color.YELLOW);
		} else if (i < 8) {
			Brick.setColor(Color.GREEN);
		} else if (i < 10) {
			Brick.setColor(Color.CYAN);
		}
		Brick.setFilled(true);
		add(Brick);
	}

	/*
	 * This method is called every time user moves mouse and the paddle follows the
	 * mouse
	 */
	public void mouseMoved(MouseEvent e) {
		if (e.getX() >= 0 && e.getX() <= getWidth() - PADDLE_WIDTH) {
			Paddle.setLocation(e.getX(), getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET);
		} else {
			Paddle.setLocation(getWidth() - PADDLE_WIDTH, getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET);
		}
	}

	/* this method check whether ball hits the object or not */
	private GObject getCollidingObject() {
		if (getElementAt(Ball.getX(), Ball.getY()) != null) {
			return getElementAt(Ball.getX(), Ball.getY());
		} else if (getElementAt(Ball.getX() + 2 * BALL_RADIUS, Ball.getY()) != null) {
			return getElementAt(Ball.getX() + 2 * BALL_RADIUS, Ball.getY());
		} else if (getElementAt(Ball.getX(), Ball.getY() + 2 * BALL_RADIUS) != null) {
			return getElementAt(Ball.getX(), Ball.getY() + 2 * BALL_RADIUS);
		} else if (getElementAt(Ball.getX() + 2 * BALL_RADIUS, Ball.getY() + 2 * BALL_RADIUS) != null) {
			return getElementAt(Ball.getX() + 2 * BALL_RADIUS, Ball.getY() + 2 * BALL_RADIUS);
		} else if(getElementAt(Ball.getX()+BALL_RADIUS+1, Ball.getY())!=null) {
			return getElementAt(Ball.getX()+BALL_RADIUS+1, Ball.getY());
		} else if(getElementAt(Ball.getX()+BALL_RADIUS+1, Ball.getY()+Ball.getHeight())!=null) {
			return getElementAt(Ball.getX()+BALL_RADIUS+1, Ball.getY()+Ball.getHeight());
		} 
		return null;
	}
}
