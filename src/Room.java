/**
 * Loads up all the items and images created into the game 
 * @author Semi Park, Michael Zhou, David Sun
 */
import java.awt.*;

public class Room
{

	// The number of boxes divided into rows and columns
	public int worldWidth = 12;
	public int worldHeight = 8;

	public int blockSize = 64;
	

	public static Block[][] block;

	public Room()
	{
		define();
	}

	// Defines the parameters of the grid in the world
	public void define()
	{
		block = new Block[worldHeight][worldWidth];

		for (int y = 0; y < block.length; y++)
		{
			for (int x = 0; x < block[0].length; x++)
			{
				block[y][x] = new Block(x * blockSize, y * blockSize,
						blockSize, blockSize, Value.groundTile, Value.airAir);
			}
		}
	}

	public void physic()
	{
		for (int y = 0; y < block.length; y++)
		{
			for (int x = 0; x < block[0].length; x++)
			{
				block[y][x].physic();
			}
		}
	}

	// Draws each block according to the y and the x value
	public void draw(Graphics g)
	{
		for (int y = 0; y < block.length; y++)
		{
			for (int x = 0; x < block[0].length; x++)
			{
				block[y][x].draw(g);
			}
		}

		for (int y = 0; y < block.length; y++)
		{
			for (int x = 0; x < block[0].length; x++)
			{
				block[y][x].fight(g);
			}
		}

	}

}
