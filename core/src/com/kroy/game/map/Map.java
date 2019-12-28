package com.kroy.game.map;

import com.kroy.game.blocks.Building;
import com.kroy.game.entities.Entity;
import com.kroy.game.entities.Entity.entityID;
import com.kroy.game.entities.Firetruck;
import com.kroy.game.entities.WarlikeEntity;
import com.kroy.game.map.tiles.Grass;

public class Map
{
	public final static int HEIGHT = 24;
	public final static int WIDTH = 24;
	
	private Tile backgroundLayer[][] = new Tile[WIDTH][HEIGHT];
	private Entity entityLayer[][] = new Entity[WIDTH][HEIGHT];
	private Block blockLayer[][] = new Block[WIDTH][HEIGHT];
	
	public Map()
	{
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				backgroundLayer[j][i] = new Grass();
				entityLayer[i][j] = null;
				blockLayer[i][j] = null;
			}
		}
	}
	
	public Tile getBackground(int x, int y)
	{
		return backgroundLayer[x][y];
	}
	
	public Entity getEntity(int x, int y)
	{
		return entityLayer[x][y];
	}
	
	public Block getBlock(int x, int y)
	{
		return blockLayer[x][y];
	}
	
	public void moveEntity(int x1, int y1, int x2, int y2)
	{
		/* Moves entity from (x1, y1) to (x2, y2) if the move is legal to do so
		 * 
		 */
		
		if (entityLayer[x1][y1] == null)
		{
			return;
		}
		
		Entity e = entityLayer[x1][y1];
		
		switch (e.id)
		{
		case FIRETRUCK:
			Firetruck f = (Firetruck) e;
			// Check if the firetruck has moved this turn
			if (!f.hasMovedThisTurn())
			{
				// Check if the destination is within the truck's movement radius
				System.out.println(getShortestPaths(x1, y1, f.getMovementDistance())[y2][x2]);
				if (getShortestPaths(x1, y1, f.getMovementDistance())[y2][x2])
				{
					// Check the space is free
					if (entityLayer[x2][y2] == null && blockLayer[x2][y2] == null)
					{
						f.setMovedThisTurn();
						entityLayer[x2][y2] = f;
						entityLayer[x1][y1] = null;
					}
					else
					{
						System.out.println("There's already something here");
					}
				}
				else
				{
					System.out.println("This firetruck can't move this far");
				}
			}
			else
			{
				System.out.println("This firetruck has already been moved");
			}
		}
	}

	public void attackEntity(int x1, int y1, int x2, int y2)
	{
		/*
		The entity at (x1, y1) attacks at (x2, y2) if it is possible to do so
		 */
		if (entityLayer[x1][y1] != null && entityLayer[x1][y1] instanceof WarlikeEntity)
		{
			((WarlikeEntity) entityLayer[x1][y1]).attack(x2, y2, this);
		}
		else
		{
			System.out.println("There was no entity there to do the attacking");
		}

	}
	
	public void debugMakeFiretruck(int x, int y)
	{
		entityLayer[x][y] = new Firetruck();
	}
	
	public void debugMakeBuilding(int x, int y)
	{
		blockLayer[x][y] = new Building();
	}
	
	public void resetTurn()
	{
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				if (entityLayer[i][j] != null && entityLayer[i][j].id == entityID.FIRETRUCK)
				{
					Firetruck f = (Firetruck) entityLayer[i][j];
					f.resetTurn();
				}
			}
		}
	}

	public boolean[][] getShortestPaths(int x, int y, int distance)
	{
		/*
		Given a location on the map and a movement radius, returns a map of all reachable locations in the form of a 2d
		array of booleans where 'true' indicates that the position is reachable, 'false' indicates that it is not.
		 */

		//System.out.println("Getting shortest path from " + x + ", " + y);

		boolean[][] reachable = new boolean[HEIGHT][WIDTH];

		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				if (distance >= Math.sqrt(Math.pow(j - x, 2) + Math.pow(i - y, 2)))
				{
					reachable[i][j] = true;
				}
				else
				{
					reachable[i][j] = false;
				}
			}
		}
		//radius >= Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2))
		return reachable;
	}
	
}
