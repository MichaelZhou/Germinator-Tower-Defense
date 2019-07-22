/**
 * Controls and displays a menu that players could puchase towers, see how much money they have, and how much health as well
 * Michael Zhou, David Sun, Semi Park
 */

import java.awt.*; 

public class Store
{
	//Variables needed to create the buttons and panel 
	public static int shopWidth =4;
	public static int buttonSize = 90;
	public static int cellSpace =5;
	public static int iconSize =60;
	public static int iconSpace =12;
	public static int iconTextYLife =30;
	public static int iconTextYMoney =38;
	public static int itemIn=4;
	public static int heldID =-1;
	public static int realID =-1;
	public static int [] buttonID = {Value.airTowerIceCube, Value.airTowerBubbles, Value.airTowerDishSoap,Value.airTrashCan};
	public static int[] buttonPrice = {10, 20, 30, 0};
	public Rectangle[] button = new Rectangle [shopWidth];
	public Rectangle buttonHealth;
	public Rectangle buttonCoins;

	
	public boolean holdsItem =false;
	
	public Store () {
		define();
	}
	
	//When you click on the towers hold the item 
	public void click(int mouseButton)
	{
		if(mouseButton == 1)
		{
			for(int i=0; i<button.length; i++)
			{
				if (button[i].contains(Screen.currentPoint))
				{
					if(buttonID[i]!=Value.airAir)
					{
						if(buttonID[i] == Value.airTrashCan) //Delete item
						{
							holdsItem=false;
						}
						else
						{
						heldID =buttonID[i];
						realID=i;
						holdsItem = true;
						}
					}
				}
			}
			
		
			if(holdsItem)
			{
				if(Screen.money >= buttonPrice[realID])
				{
					for(int row =0; row<Screen.room.block.length; row++)
					{
						for(int col =0; col<Screen.room.block[0].length; col++)
						{
							if(Screen.room.block[row][col].contains(Screen.currentPoint))
							{
								if(Screen.room.block[row][col].groundID != Value.groundRoad && Screen.room.block[row][col].airID == Value.airAir)
								{
									Screen.room.block[row][col].airID = heldID;
									Screen.money-= buttonPrice[realID];
								}
							}
						}
					}
				}
			}
		}
	}
	
	//Placing the defining the buttons with their descriptions
	public void define() {
		for (int i = 0; i<button.length; i++)
		{
			button[i] = new Rectangle((Screen.myWidth/2) + 305 ,130 +((buttonSize+cellSpace)*i) , buttonSize, buttonSize);
		}
		
		//Create boxes to put the heart and coin images 
		buttonHealth = new Rectangle(Screen.room.block[0][0].x+800, button[0].y-130,iconSize, iconSize);
		buttonCoins = new Rectangle(Screen.room.block[0][0].x+800, button[0].y-130 + button[0].height-iconSize,iconSize, iconSize);
	}

	public void draw (Graphics g)
	{
		g.drawImage(Screen.tileset_res[0],(Screen.myWidth/2) + 290 ,122, 190,390, null);
		//Draws the rectangular buttons in the desired position
		for (int i = 0; i<button.length; i++)
		{
			if (button[i].contains(Screen.currentPoint))
			{
				g.setColor(new Color(255,255,100,100));
				g.fillRect(button[i].x, button[i].y, button[i].width+70, button[i].height);
			}
			
			if(buttonID[i]!=Value.airAir)
			{
				g.drawImage(Screen.tileset_air[buttonID[i]], button[i].x+itemIn, button[i].y+itemIn, button[i].width-(itemIn*2), button[i].height-(itemIn*2), null);
			}
				if (buttonPrice[i]>0)
			{
				g.setFont(new Font("Consolas", Font.BOLD, 15));
				g.drawString("Cost: "+buttonPrice[i],button[i].x+itemIn*2+78, button[i].y+itemIn+70);
			}
		}
		//Put the images for the coin and the heart where the 2 boxes are 
		g.drawImage(Screen.tileset_res[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height,null);
		g.drawImage(Screen.tileset_res[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null);
		
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		g.drawString(""+Screen.life, buttonHealth.x + buttonHealth.width- iconSpace, buttonHealth.y +iconTextYLife);
		g.drawString(""+(Screen.money), buttonCoins.x + buttonCoins.width- iconSpace, buttonCoins.y +iconTextYMoney);
		
		if (holdsItem)
		{
			g.drawImage(Screen.tileset_air[heldID], Screen.currentPoint.x- 32, Screen.currentPoint.y-64, 64, 64, null);
		}
		
		//Drawing some headings that would be useful for the user 
		g.setFont(new Font("Consolas", Font.BOLD, 17));
		g.setColor(Color.BLACK);
		g.drawString("Germs: "+(Screen.killed) + "/" + (Screen.killsToWin), 800, 100);
		g.drawString("Towers", 845, 122);

		//Description of the towers
		g.setFont(new Font("Consolas", Font.BOLD, 15));
		g.drawString("Ice Cube" , 889, 190);
		g.drawString("Bubbles", 890, 285);
		g.drawString("Dish Soap" , 878, 380);
		
		
		
	}
}
