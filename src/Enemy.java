/**
 * Controls all behaviour of the enemies 
 * @author Michael Zhou, David Sun, Semi Park
 */

import java.awt.*;

//Database for the enemy 
public class Enemy extends Rectangle 
{
	public int xC,yC;
	public int enemySize = 64;
	public int health;
	public int enemyWalk = 0;
	public int upward = 0;
	public int downward = 1;
	public int right =2;
	public int left =3;
	public int direction = right;
	public int enemyID = Value.enemyAir;
	public boolean inGame = false;
	public boolean hasUpward=false;
	public boolean hasDownward= false;
	public boolean hasLeft =false;
	public boolean hasRight =false;
	public Enemy()
	{
		health=100;
	}

	//Spawns the enemy from the beginning of the path 
		public void spawnMob(int enemyID)
		{
			for (int y =0; y<Screen.room.block.length; y++)
			{
				if(Screen.room.block[y][0].groundID == Value.groundRoad)
				{
				setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y, enemySize, enemySize);
				xC =0;
				yC =y;
				}
			}
			
			this.enemyID =enemyID;
			
			inGame =true;
			
		}
		
		public void deleteEnemy()
		{
			inGame =false;
			direction =right;
			enemyWalk =0;
			health =100;
			Screen.room.block[0][0].getMoney(enemyID);
		}
		
		public void healthReduce()
		{
			Screen.life -=1;
		} 
		
		//Controls the speed and where the enemy will walk 
		public int walkFrame =0;
		public double walkSpeed =20/30;
		
		//Physical movement of the enemy on the path by keeping track of the position of the enemy on the Grid
	
		public void physic()
		{
			if(walkFrame >= walkSpeed)
			{
				if(direction == right)
				{
					x+=1;
				}
				else if(direction ==upward)
				{
					y-=1;
				}
				else if (direction == downward)
				{
					y+=1;
				}
				else if (direction == left)
				{
					x-=1;
				}
				
				enemyWalk+=1;
				
				if(enemyWalk==Screen.room.blockSize)
				{
					if(direction == right)
					{
						xC+=1;
						hasRight =true;
					}
					else if(direction ==upward)
					{
						yC-=1;
						hasUpward =true;
					}
					else if (direction == downward)
					{
						yC+=1;
						hasDownward =true;
					}
					else if(direction == left)
					{
						xC -=1;
						hasLeft = true;
					}
					
					if(!hasUpward)
					{
						try{
							if(Screen.room.block[yC+1][xC].groundID == Value.groundRoad)
							{
								direction =downward;
							}
						} catch(Exception e) {}
					}
					
					if(!hasDownward)
					{
						try{
							if(Screen.room.block[yC-1][xC].groundID == Value.groundRoad)
							{
								direction =upward;
							}
						} catch(Exception e) {}
					}
					
					if(!hasLeft)
					{
						try{
							if(Screen.room.block[yC][xC+1].groundID == Value.groundRoad)
							{
								direction =right;
							}
						} catch(Exception e) {}
					}
					
					if(!hasRight)
					{
						try{
							if(Screen.room.block[yC][xC+1].groundID == Value.groundRoad)
							{
								direction = right;
							}
						} catch(Exception e) {}
					}
					
					if(Screen.room.block[yC][xC].airID == Value.airExit)
					{
						deleteEnemy();
						healthReduce();
					}
					
					
					hasUpward = false;
					hasDownward =false;
					hasLeft =false;
					hasRight =false;
					enemyWalk =0;
				}
				
				walkFrame=0;
			}
			else
			{
				walkFrame+=1;
			}
		}
		
		public void loseHealth(int amount)
		{
			health-=amount;
					
			checkDeath();
		}
		
		public void checkDeath()
		{
			if (health ==0)
			{
				deleteEnemy();
				Screen.killed++;
			}
		}
		
		public boolean isDead()
		{
			if (inGame)
			{
				return false;
			}
			else
			{
				return true;
			}
			
		}
		
	
		public void draw(Graphics g)
		{
				// Drawing the enemy moving along the path
				g.drawImage(Screen.tileset_enemy[enemyID], x, y, width, height, null);
				
				// Drawing the health bar
				g.setColor(new Color(255, 0, 0));
				g.fillRect(x+7, y-10, width-14,5);
				
				g.setColor(new Color(51, 255, 0));
				g.fillRect(x+7, y-10, health/2,5);
				
				g.setColor(new Color (0,0,0));
				g.drawRect(x+7, y-10, health/2-1,5-1);
				
		}
}
