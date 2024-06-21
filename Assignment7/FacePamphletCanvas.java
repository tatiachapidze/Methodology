
/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {
	private double y;

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		GLabel message = new GLabel(msg);
		message.setLocation(getWidth() / 2 - message.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
		message.setFont(MESSAGE_FONT);
		add(message);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		y = TOP_MARGIN;
		addName(profile.getName());
		addImage(profile.getImage());
		addStatus(profile.getStatus(), profile.getName());
		addFriends(profile.getFriends());
	}

	/* this method adds friend names on the canvas */
	private void addFriends(Iterator<String> friends) {
		y -= IMAGE_HEIGHT;
		GLabel title = new GLabel("Friends:", getWidth() / 2, y);
		title.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(title);
		y += title.getHeight();
		while (friends.hasNext()) {
			GLabel name = new GLabel(friends.next(), getWidth() / 2, y);
			name.setFont(PROFILE_FRIEND_FONT);
			add(name);
			y += name.getHeight();
		}
	}

	/* this method prints the status on the canvas */
	private void addStatus(String status, String name) {
		GLabel label = new GLabel("");
		if (status != null) {
			label = new GLabel(name + " is " + status);

		} else {
			label = new GLabel("No current status");
		}
		label.setFont(PROFILE_STATUS_FONT);
		label.setLocation(LEFT_MARGIN, y + STATUS_MARGIN + label.getAscent());
		add(label);
	}

	/* this method add the image on the canvas */
	private void addImage(GImage image) {
		y += IMAGE_MARGIN;
		if (image != null) {
			image.scale(IMAGE_WIDTH / image.getWidth(), IMAGE_HEIGHT / image.getHeight());
			image.setLocation(LEFT_MARGIN, y);
			add(image);
		} else {
			GRect rect = new GRect(LEFT_MARGIN, y, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(rect);
			GLabel text = new GLabel("No Image");
			text.setFont(PROFILE_IMAGE_FONT);
			text.setLocation(LEFT_MARGIN + (IMAGE_WIDTH / 2 - text.getWidth() / 2),
					y + (IMAGE_HEIGHT + text.getHeight()) / 2);
			add(text);
		}
		y += IMAGE_HEIGHT;
	}

	/* this method add user's name on the canvas */
	private void addName(String name) {
		GLabel Name = new GLabel(name);
		y += Name.getHeight();
		Name.setFont(PROFILE_NAME_FONT);
		Name.setLocation(LEFT_MARGIN, y);
		Name.setColor(Color.BLUE);
		add(Name);
	}
}
