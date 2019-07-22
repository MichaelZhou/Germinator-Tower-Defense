import java.awt.*;

public class Block extends Rectangle
{
	public Rectangle towerSquare;

	public int groundID;
	public int airID;
	public int towerSquareSize = 130;
	public double bubbleFireTime = 600 / 30.0; // Amount of frames it takes for
												// the tower to shoot once
	public int bubbleFireFrame = 0;
	public int bubbleDamage = 1;
	public int bubbleFiringRadius = 130; // Range of the tower

	public double iceCubeFireTime = 600 / 30.0;
	public int iceCubeFireFrame = 0;
	public int iceCubeDamage = 10;
	public int iceCubeFiringRadius = 195;

	public double dishSoapFireTime = 900 / 30.0;
	public int dishSoapFireFrame = 0;
	public int dishSoapDamage = 10;
	public int dishSoapFiringRadius = 390;

	public int targetEnemy = -1;
	public boolean shooting = false;

	public Block(int x, int y, int width, int height, int groundID, int airID)
	{
		setBounds(x, y, width, height);

		
		// Adjusts the range of the tower depending on which type it is
		if (airID == Value.airTowerBubbles && groundID == Value.groundTile)
		{
			towerSquareSize = bubbleFiringRadius;
		}
		if (airID == Value.airTowerIceCube && groundID == Value.groundTile)
		{
			towerSquareSize = iceCubeFiringRadius;
		}
		if (airID == Value.airTowerDishSoap && groundID == Value.groundTile)
		{
			towerSquareSize = dishSoapFiringRadius;
		}
		
	
			towerSquare = new Rectangle(x -(towerSquareSize/2), y-(towerSquareSize/2), width+ (towerSquareSize), height+(towerSquareSize));


	}

	public void draw(Graphics g)
	{
		// Draws different ground tiles depending on what level it is
		if (Screen.level == 1)
		g.drawImage(Screen.tileset_ground1[groundID], x, y, width, height, null);
		if (Screen.level == 2)
			g.drawImage(Screen.tileset_ground2[groundID], x, y, width, height, null);
		if (Screen.level == 3)
			g.drawImage(Screen.tileset_ground3[groundID], x, y, width, height, null);
		if (Screen.level == 4)
			g.drawImage(Screen.tileset_ground4[groundID], x, y, width, height, null);
		if (Screen.level == 5)
			g.drawImage(Screen.tileset_ground5[groundID], x, y, width, height, null);
		
		// If there a tower is placed on a ground tile, draws the tower on top of the ground tile
		if (airID != Value.airAir)
		{
			g.drawImage(Screen.tileset_air[airID], x, y, width, height, null);
		}
	}

	public void physic()
	{

		if (targetEnemy != -1
				&& towerSquare.intersects(Screen.germs[targetEnemy]))
		{
			shooting = true;
		}
		else
		{
			shooting = false;
		}

		// If the tower has not yet found a target, it will begin shooting when
		// a germ has entered its firing radius
		if (!shooting)
		{
			if (airID == Value.airTowerIceCube
					|| airID == Value.airTowerBubbles
					|| airID == Value.airTowerDishSoap)
			{
				for (int i = 0; i < Screen.germs.length; i++)
				{
					if (Screen.germs[i].inGame)
					{
						if (towerSquare.intersects(Screen.germs[i]))
						{
							shooting = true;
							targetEnemy = i;
						}
					}
				}
			}
		}

		if (shooting)
		{
			// If the tower is a bubble tower, fires laser at bubble rate of
			// fire and bubble damage
			if (bubbleFireFrame >= bubbleFireTime
					&& airID == Value.airTowerBubbles)
			{
				Screen.germs[targetEnemy].loseHealth(bubbleDamage);
				bubbleFireFrame = 0;
			}
			else
			{
				bubbleFireFrame++;
			}
			// If the tower is an ice cube tower, fires laser at ice cube rate
			// of fire and ice cube damage
			if (iceCubeFireFrame >= iceCubeFireTime
					&& airID == Value.airTowerIceCube)
			{
				Screen.germs[targetEnemy].loseHealth(iceCubeDamage);
				iceCubeFireFrame = 0;
			}
			else
			{
				iceCubeFireFrame++;
			}
			// If the tower is a dish soap tower, fires laser at dish soap rate
			// of fire and dish soap damage
			if (dishSoapFireFrame >= dishSoapFireTime
					&& airID == Value.airTowerDishSoap)
			{
				Screen.germs[targetEnemy].loseHealth(dishSoapDamage);
				dishSoapFireFrame = 0;
			}
			else
			{
				dishSoapFireFrame++;
			}

			if (Screen.germs[targetEnemy].isDead())
			{
				shooting = false;
				targetEnemy = -1;
				
				Screen.hasWon();
			}
		}
	}

	public void getMoney(int enemyID)
	{
		Screen.money += Value.deathReward[enemyID];

	}

	public void fight(Graphics g)
	{
		if (Screen.isDebug)
		{
			if (airID == Value.airTowerIceCube
					|| airID == Value.airTowerBubbles
					|| airID == Value.airTowerDishSoap)
			{
				g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width,
						towerSquare.height);
			}
		}

		if (shooting)
		{
			g.setColor(new Color(255, 255, 0));
			g.drawLine(x + (width / 2), y + (height / 2),
					Screen.germs[targetEnemy].x
							+ (Screen.germs[targetEnemy].width / 2),
					Screen.germs[targetEnemy].y
							+ (Screen.germs[targetEnemy].height / 2));
		}
	}
}
