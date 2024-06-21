
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {
	private JTextField Name;
	private JButton Add;
	private JButton Delete;
	private JButton Lookup;
	private JTextField Status;
	private JButton ChangeStatus;
	private JTextField Picture;
	private JButton ChangePicture;
	private JTextField Friend;
	private JButton AddFriend;
    private FacePamphletDatabase base;
	private FacePamphletProfile logInProfile;
	private FacePamphletCanvas canvas;

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		base = new FacePamphletDatabase();
		logInProfile = null;
        canvas = new FacePamphletCanvas();
		add(canvas);
        add(new JLabel("Name"), NORTH);
		Name = new JTextField(TEXT_FIELD_SIZE);
		add(Name, NORTH);
		Add = new JButton("Add");
		add(Add, NORTH);
		Delete = new JButton("Delete");
		add(Delete, NORTH);
		Lookup = new JButton("Lookup");
		add(Lookup, NORTH);
		Status = new JTextField(TEXT_FIELD_SIZE);
		add(Status, WEST);
		ChangeStatus = new JButton("Change Status");
		add(ChangeStatus, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		Picture = new JTextField(TEXT_FIELD_SIZE);
		add(Picture, WEST);
		ChangePicture = new JButton("Change Picture");
		add(ChangePicture, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		Friend = new JTextField(TEXT_FIELD_SIZE);
		add(Friend, WEST);
		AddFriend = new JButton("Add Friend");
		add(AddFriend, WEST);

		//Status.addActionListener(this);
		Picture.addActionListener(this);
		Friend.addActionListener(this);
		addActionListeners();

	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Add && !Name.getText().equals("")) {
			addUser();
			}
		if (e.getSource() == Delete && !Name.getText().equals("")) {
			deleteUser();
			}
		if (e.getSource() == Lookup && !Name.getText().equals("")) {
			lookUpUser();
			}
		if (e.getSource() == ChangeStatus || e.getSource() == Status && !Status.getText().equals("")) {
			updateStatus();
			}
		if (e.getSource() == ChangePicture || e.getSource() == Picture && !Picture.getText().equals("")) {
			updatePicture();
		}
		if (e.getSource() == AddFriend || e.getSource() == Friend && !Friend.getText().equals("")) {
			if (logInProfile != null) {
				if (base.containsProfile(Friend.getText())) {
					checkFriend();
				} else {
					canvas.displayProfile(base.getProfile(logInProfile.getName()));
					canvas.showMessage(Friend.getText() + " does not exist");
				}
			} else {
				canvas.removeAll();
				canvas.showMessage("Please select a profile to add friend");
			}
			Friend.setText("");
		}
		
	}
	/* this method checks if the logged in user has already entered person and add new person as the friend*/
	private void checkFriend() {
		if (base.getProfile(logInProfile.getName()).addFriend(Friend.getText())) {
			base.getProfile(Friend.getText()).addFriend(logInProfile.getName());
			canvas.displayProfile(base.getProfile(logInProfile.getName()));
			canvas.showMessage(Friend.getText() + " added as a friend");
		} else {
			canvas.displayProfile(base.getProfile(logInProfile.getName()));
			if (Friend.getText().equals(logInProfile.getName())) {
				canvas.showMessage("you can't add yourself");
			} else {
				canvas.showMessage(
						logInProfile.getName() + " already has " + Friend.getText() + " as a friend");
			}
		}
	}
	/* this method checks and updates profile picture */
	private void updatePicture() {
		if (logInProfile != null) {
			GImage image = null;
			try {
				image = new GImage(Picture.getText());
				base.getProfile(logInProfile.getName()).setImage(image);
				canvas.displayProfile(base.getProfile(logInProfile.getName()));
				canvas.showMessage("Picture updated");
			} catch (ErrorException ex) {
				canvas.displayProfile(base.getProfile(logInProfile.getName()));
				canvas.showMessage("Unable to open image file: " + Picture.getText());
			}

		} else {
			canvas.removeAll();
			canvas.showMessage("Please select a profile to change picture");
			println("choose profile");
		}
		Picture.setText("");
	}
	/* this method updates profile status */
	private void updateStatus() {
		if (logInProfile != null) {

			base.getProfile(logInProfile.getName()).setStatus(Status.getText());
			canvas.displayProfile(base.getProfile(logInProfile.getName()));
			canvas.showMessage("Status updated to " + Status.getText());
		} else {
			canvas.removeAll();
			canvas.showMessage("Please select a profile to change status");

		}
		Status.setText("");
	}
	/* this method looks up entered name */
	private void lookUpUser() {
		if (base.containsProfile(Name.getText())) {

			canvas.displayProfile(base.getProfile(Name.getText()));
			canvas.showMessage("Displaying " + Name.getText());
			logInProfile = base.getProfile(Name.getText());
		} else {
			logInProfile = null;
			canvas.removeAll();
			canvas.showMessage("A profile with the name " + Name.getText() + " doesn't exists");
		}
		Name.setText("");
		
	}
    /* this method deletes entered name's profile*/
	private void deleteUser() {
		canvas.removeAll();
		if (base.containsProfile(Name.getText())) {
			base.deleteProfile(Name.getText());

			canvas.showMessage("Profile of " + Name.getText() + " deleted");
		} else {

			canvas.showMessage("A profile with the name " + Name.getText() + " doesn't exists");

		}
		logInProfile = null;
		Name.setText("");
		
	}
	/* this method adds new user */
	private void addUser() {
		if (base.containsProfile(Name.getText())) {
			canvas.displayProfile(base.getProfile(Name.getText()));
			canvas.showMessage("A profile with the name " + Name.getText() + " already exists");

		} else {
			FacePamphletProfile profile = new FacePamphletProfile(Name.getText());
			base.addProfile(profile);
			canvas.displayProfile(base.getProfile(Name.getText()));
			canvas.showMessage("New profile created");
		}
		logInProfile = base.getProfile(Name.getText());
		Name.setText("");
	}

}
