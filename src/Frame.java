/**
 * Sets up the overall frame of the game
 * @author Semi Park, Michael Zhou, David Sun
 */
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;

import javax.swing.*;


import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;

//Setting up the frame and its dimensions. Also adding the title 
public class Frame extends JFrame {
	public static String title = "GerminatorTD";
	public static Dimension size = new Dimension(999, 557);
	private AudioClip backGroundSound;

	// Allowing the opening and closing of the frame
	public Frame() {
		setTitle(title);
		setSize(size);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Added stuff for
		// music////////////////////////////////////////////////////////////
		backGroundSound = Applet.newAudioClip(getCompleteURL("loop.au"));
		backGroundSound.play();

		// Image from clipartpanda.com
		setIconImage(Toolkit.getDefaultToolkit().getImage("res/germIcon.png"));

		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);

		JMenu game = new JMenu("Game");
		menubar.add(game);
		JMenuItem exit = new JMenuItem("Exit");
		game.add(exit);
		exit.addActionListener(new ActionListener() {
			/**
			 * Responds to the exit game option being selected
			 * 
			 * @param event
			 *            The event that selected this menu option
			 */
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		JMenu help = new JMenu("Help");
		menubar.add(help);
		JMenuItem rules = new JMenuItem("Rules");
		help.add(rules);
		rules.addActionListener(new ActionListener() {
			/**
			 * Responds to the help option being selected
			 * 
			 * @param event
			 *            The event that selected this menu option
			 */
			public void actionPerformed(ActionEvent event) {
				JOptionPane
						.showMessageDialog(
								Frame.this,
								"The objective of this game is to stop the germs from reaching the exit.\n"
										+ "Place the towers on the grid by clicking the tower and then clicking on the desired tile location.\n"
										+ "For each germ that you kill or that exits the level, you gain 5 coins.\n"
										+ "You have a total of 20 health, and for each germ that reaches the end, one health will be lost.\n"
										+ "Good luck!\n", "Rules",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});
		JMenuItem towers = new JMenuItem("Towers");
		help.add(towers);
		towers.addActionListener(new ActionListener() {
			/**
			 * Responds to the help option being selected
			 * 
			 * @param event
			 *            The event that selected this menu option
			 */
			public void actionPerformed(ActionEvent event) {
				JOptionPane
						.showMessageDialog(
								Frame.this,
								"Ice Cube: Medium Range, Low Damage, Slows the enemy. \n"
										+ "Bubbles: Medium Range, Medium Damage.\n"
										+ "Dish Soap: Long Range, High Damage, Shots also affect adjacent enemies. \n",
								"Rules", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JMenuItem about = new JMenuItem("About...");
		help.add(about);
		about.addActionListener(new ActionListener() {
			/**
			 * Responds to the about option being selected
			 * 
			 * @param event
			 *            The event that selected this menu option
			 */
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(Frame.this,
						"by Semi Park, David Sun, Michael Zhou \u00a9 2015",
						"About", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		init();
	}

	// Sets up a grid inside the frame and makes it visible
	public void init() {
		setLayout(new GridLayout(1, 1, 0, 0));

		Screen screen = new Screen(this);
		add(screen);

		setVisible(true);
	}

	// Gets the URL needed for newAudioClip Added stuff for
	// music/////////////////////////////////////////////////////////////////////////////////
	public URL getCompleteURL(String fileName) {
		try {
			return new URL("file:" + System.getProperty("user.dir") + "/"
					+ fileName);
		} catch (MalformedURLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	// Added
	// Stuff////////////////////////////////////////////////////////////////////////
	private class GoodbyeWindowListener extends WindowAdapter {
		// Deals with window closing
		public void windowClosing(WindowEvent event) {
			backGroundSound.play();
			try {
				Thread.sleep(1200);
			} catch (InterruptedException exception) {
			}
			setVisible(false);
			System.exit(0);
		}

	}

	public static void main(String args[]) {
		Frame frame = new Frame();

	}

}
