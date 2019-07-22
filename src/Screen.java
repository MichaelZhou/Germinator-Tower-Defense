/**
 * Creates the screen that displays the game in the fame
 * @author Semi Park, Michael Zhou, David Sun
 */
import java.awt.*;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;
import java.io.*;

public class Screen extends JPanel implements Runnable, MouseListener, MouseMotionListener
{
	public Thread thread = new Thread(this);

	// Creating the arrays of images
	public static Image[] tileset_ground1 = new Image[100];
	public static Image[] tileset_ground2 = new Image[100];
	public static Image[] tileset_ground3 = new Image[100];
	public static Image[] tileset_ground4 = new Image[100];
	public static Image[] tileset_ground5 = new Image[100];
	public static Image[] tileset_air = new Image[100];
	public static Image[] tileset_res = new Image[100];
	public static Image[] tileset_enemy = new Image[100];
	

	// Setting up the key variable values that will appear on the screen
	public static int myWidth, myHeight;
	public static int money;
	public static int life;
	public static int killed = 0; // David Sun
	public static int killsToWin;
	public static int level = 1;
	public static int maxLevel = 5;
	public static int winTime = 200;
	public static int winFrame = 0;
	// Frequency of the enemies
	public static double spawnTime;
	public int spawnFrame = 0;
	public static boolean isFirst = true;
	public static boolean isDebug = false;
	public static boolean isWin = false;

	public static Point currentPoint = new Point(999, 557);

	public static Room room;
	public static Save save;
	public static Store store;
	
	// Creating 100 enemies
	public static Enemy[] germs = new Enemy[100];
	
	public int showScreen = 0;
	public Image menuScreen;
	public Image instructionsScreen;
	public Image aboutScreen;
	
	public Rectangle play;
	public Rectangle instructions;
	public Rectangle about;
	public Rectangle back1;
	public Rectangle back2;

	// Allows the mouse to operate on the screen
	public Screen(Frame frame)
	{
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);

		thread.start();
	}

	public static void hasWon() // changed with original file by DS of if
								// statement
	{
		if (killed == killsToWin)
		{
			isWin = true;
			killed = 0; // Davis Sun the relationship of killed vs. number in
						// the mission file
			// level=level +1;
		}

	}

	// Calling all the methods needed for the screen
	public void define()
	{
		room = new Room();
		save = new Save();
		store = new Store();

		money = 10000; // Amount of money that the user has to spend, minus 20
		life = 20;

		// putting the outside tiles in the desired areas
		for (int i = 0; i < tileset_ground1.length; i++) {
			tileset_ground1[i] = new ImageIcon("res/tileset_ground1.png")
					.getImage();
			tileset_ground1[i] = createImage(new FilteredImageSource(
					tileset_ground1[i].getSource(), new CropImageFilter(0,
							32 * i, 32, 32)));
		}
		for (int i = 0; i < tileset_ground2.length; i++) {
			tileset_ground2[i] = new ImageIcon("res/tileset_ground2.png")
					.getImage();
			tileset_ground2[i] = createImage(new FilteredImageSource(
					tileset_ground2[i].getSource(), new CropImageFilter(0,
							32 * i, 32, 32)));
		}
		for (int i = 0; i < tileset_ground3.length; i++) {
			tileset_ground3[i] = new ImageIcon("res/tileset_ground3.png")
					.getImage();
			tileset_ground3[i] = createImage(new FilteredImageSource(
					tileset_ground3[i].getSource(), new CropImageFilter(0,
							32 * i, 32, 32)));
		}
		for (int i = 0; i < tileset_ground4.length; i++) {
			tileset_ground4[i] = new ImageIcon("res/tileset_ground4.png")
					.getImage();
			tileset_ground4[i] = createImage(new FilteredImageSource(
					tileset_ground4[i].getSource(), new CropImageFilter(0,
							32 * i, 32, 32)));
		}
		for (int i = 0; i < tileset_ground5.length; i++) {
			tileset_ground5[i] = new ImageIcon("res/tileset_ground5.png")
					.getImage();
			tileset_ground5[i] = createImage(new FilteredImageSource(
					tileset_ground5[i].getSource(), new CropImageFilter(0,
							32 * i, 32, 32)));
		}

		// putting transparent tiles in the desired areas
		for (int i = 0; i < tileset_air.length; i++)
		{
			tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
			tileset_air[i] = createImage(new FilteredImageSource(
					tileset_air[i].getSource(), new CropImageFilter(0, 32 * i,
							32, 32)));
		}
		
		tileset_res[0] = new ImageIcon("res/cell1.png").getImage();
		tileset_res[1] = new ImageIcon("res/heart.png").getImage();
		tileset_res[2] = new ImageIcon("res/coin.png").getImage();

		tileset_enemy[0] = new ImageIcon("res/germ.png").getImage();
		
		menuScreen = new ImageIcon("res/mainMenu.png").getImage();
		instructionsScreen = new ImageIcon("res/instructions.png").getImage();
		aboutScreen = new ImageIcon("res/about.png").getImage();
		
		save.loadSave(new File("Save/mission" + level + ".txt"));

		for (int i = 0; i < germs.length; i++)
		{
			germs[i] = new Enemy();
		}

	}

	public void paintComponent(Graphics g)
	{
		if (isFirst)
		{
			myWidth = getWidth();
			myHeight = getHeight();
			define();

			isFirst = false;
		}

		if (showScreen == 0) // main menu
		{
			g.drawImage(menuScreen, 0,0, this);
			play = new Rectangle (483,128,466,100);
			g.drawRect(483, 128, 466, 100);
			instructions = new Rectangle (482,260,466,100);
			g.drawRect(482, 260, 466, 100);
			about = new Rectangle (482,400,446,100);
			g.drawRect(482, 400, 466, 100);
	
			//Draw all buttons
				// buttons are going to be Rectangle objects
		}
		else if (showScreen == 1) // game screen
		{
		// Background color of the game
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, getWidth() / 2 + 273, getHeight());

		// Color of the store
		g.setColor(new Color(204, 204, 204));
		g.fillRect(768, 0, getWidth(), getHeight());

		// The color of the blocks under the image
		g.setColor(new Color(50, 50, 50));
		
		// Drawing a border around the game not the store
		g.drawLine(room.block[0][0].x - 1, 0, room.block[0][0].x - 1,
				room.block[room.worldHeight - 1][0].y + room.blockSize);// Drawing
																		// the
																		// left
																		// line
		g.drawLine(room.block[0][room.worldWidth - 1].x + room.blockSize, 0,
				room.block[0][room.worldWidth - 1].x + room.blockSize,
				room.block[room.worldHeight - 1][0].y + room.blockSize);// Drawing
																		// the
																		// right
																		// line
		g.drawLine(room.block[0][0].x, room.block[room.worldHeight - 1][0].y
				+ room.blockSize, room.block[0][room.worldWidth - 1].x
				+ room.blockSize, room.block[room.worldHeight - 1][0].y
				+ room.blockSize);// Drawing the bottom line

		// Drawing the room
		room.draw(g);

		
		// Placing all the enemies in the game
		for (int i = 0; i < germs.length; i++)
		{
			if (germs[i].inGame)
				germs[i].draw(g);
		}

		// Drawing the store
		store.draw(g);

		if (life < 1)
		{
			g.setColor(new Color(240, 20, 20));
			g.fillRect(0, 0, myWidth, myHeight);
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("Courier New", Font.BOLD, 20));
			g.drawString("Game Over", 100, 100);
		}

		if (isWin) // Outstanding
		{
			g.setColor(new Color(240, 20, 20));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("Arial", Font.BOLD, 20));
			if (level == maxLevel)
			{
				g.drawString(
						"CONGRATULATIONS!!! YOU HAVE COMPLETED THE GAME!",
						200, 200);
			}

			else
			{
				g.drawString(
						"YOU HAVE COMPLETED THIS LEVEL!!! Loading next level...",
						200, 200);
			}
		}
		}
		else if(showScreen == 2) // instructions
		{
			g.drawImage(instructionsScreen, 0,0, this);
			back1 = new Rectangle (715,385,266,86);
			g.drawRect(715, 385, 266, 86);
			
		} // about
		else if(showScreen==3)
		{
			g.drawImage(aboutScreen, 0,0, this);
			back2 = new Rectangle (717,406,265,85);
			g.drawRect(717, 406, 265, 85);
		}
	}


	public void mousePressed(MouseEvent e)
	{
		currentPoint = new Point (e.getX(), e.getY());
		
		//options for clicking on main menu
		if (showScreen==0)
		{
			if (play.contains(Screen.currentPoint))
			{
				showScreen = 1;
				repaint();
			}
			else if (instructions.contains(Screen.currentPoint))
			{
				showScreen = 2;
				repaint();
			}
			else if (about.contains(Screen.currentPoint))
			{
				showScreen =3;
				repaint();
			}
		}
		
		//options for clicking on instructions screen
		else if (showScreen == 2)
		{
			if (back1.contains(Screen.currentPoint))
			{
				showScreen = 0;
				repaint();
			}
		}
			//options for clicking on about screen
		else if (showScreen == 3)
		{
			if(back2.contains(Screen.currentPoint))
			{
				showScreen=0;
				repaint();
			}
		}
		else if(showScreen==1)
		{
			store.click(e.getButton());
			
		}
		
	}

		
			
	// Creates and spawns the enemies form the beginning of the path
	public void enemySpawner()
	{
		if (spawnFrame >= spawnTime)
		{
			for (int i = 0; i < germs.length; i++)
			{
				if (!germs[i].inGame)
				{
					germs[i].spawnMob(Value.enemyGerm);
					break;
				}
			}
			spawnFrame = 0;
		}

		else
			spawnFrame += 1;
	}

	/**
	 * Repeats the physics of the level and the germs every 30 milliseconds.
	 */
	public void run()
	{
		while (true)
		{

			long start = System.nanoTime();

			if (!isFirst && life > 0 && !isWin)
			{
				room.physic();
				enemySpawner();
				for (int i = 0; i < germs.length; i++)
				{
					if (germs[i].inGame)
					{
						germs[i].physic();
					}
				}
			}
			else
			{
				if (isWin)
				{
					if (winFrame >= winTime)
					{ // when it reaches the max level
						if (level == maxLevel)
						{
							// System.out.println (level);
							System.exit(0);
						}
						// if you have levels left
						else
						{
							level += 1;
							define();
							isWin = false;
						}
						winFrame = 0;
					}
					else
					{
						winFrame += 1;
					}
				}
			}

			int runTime = (int) ((System.nanoTime() - start) / 1000000000);

			repaint();

			try
			{
				Thread.sleep(30 - runTime);
			}
			catch (Exception e)
			{
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e)
	{
		//mse = new Point ((e.getX())-((Frame.size.width-Screen.myWidth)/2), (e.getY())-((Frame.size.height-(Screen.myHeight))-(Frame.size.width - Screen.myWidth/2)));
	}


	//mouse cursor
	public void mouseMoved(MouseEvent e)
	{
		currentPoint = new Point (e.getX(), e.getY());
		
		//currentPoint = new Point ((e.getX()) - ((Frame.size.width - Screen.myWidth)/2-34), (e.getY()) - ((Frame.size.height - (Screen.myHeight)) - (Frame.size.width - Screen.myWidth)/2-40));
	}
}
