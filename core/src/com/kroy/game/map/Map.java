package com.kroy.game.map;

import com.badlogic.gdx.math.Vector2;
import com.kroy.game.blocks.Building;
import com.kroy.game.blocks.Obstacle;
import com.kroy.game.entities.*;
import com.kroy.game.entities.Entity.entityID;
import com.kroy.game.map.tiles.Grass;

import java.util.*;

public class Map
{
	public final static int HEIGHT = 24;
	public final static int WIDTH = 24;
	
	private Tile backgroundLayer[][] = new Tile[WIDTH][HEIGHT];
	private Entity entityLayer[][] = new Entity[WIDTH][HEIGHT];
	private Block blockLayer[][] = new Block[WIDTH][HEIGHT];

	public ShortestPathfinder pathfinder = new ShortestPathfinder(this);
	
	public Map()
	{
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				backgroundLayer[j][i] = new Grass();
				entityLayer[j][i] = null;
				blockLayer[j][i] = null;
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
		/* Moves entity from (x1, y1) to (x2, y2) if the move is legal
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
				ArrayList<Vector2> path = pathfinder.shortestPath(x1, y1, x2, y2);

				if (path != null && path.size() <= f.getMovementDistance())
				{
					f.setMovedThisTurn();
					entityLayer[x2][y2] = f;
					entityLayer[x1][y1] = null;
				}
				else
				{
					System.out.println("Pathfinder can't get us there");
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
		// Add pre-check that locations are within map
		if (entityLayer[x1][y1] != null)
		{
			if (entityLayer[x1][y1] instanceof Firetruck)
			{
				Firetruck f = (Firetruck) entityLayer[x1][y1];
				if (!f.hasAttackedThisTurn())
				{
					damageLocation(f.getAttackStrength(), x2, y2);
					f.setAttackedThisTurn();
				}
				else
				{
					System.out.println("The firetruck has already attacked this turn");
				}
			}
			else if (entityLayer[x1][y1] instanceof Fortress)
			{
				Fortress f = (Fortress) entityLayer[x1][y1];
				damageLocation(f.getAttackStrength(), x2, y2);
				System.out.println("A fortress got angry at " + x2 + ", " + y2);
			}
			else
			{
				System.out.println("The entity there wasn't capable of attacking");
			}
		}
		else
		{
			System.out.println("There was no entity there to do the attacking");
		}

	}

	public void damageLocation(int amount, int x, int y)
	{
		if ((x >= 0 && x < WIDTH) && (y >= 0 && y < HEIGHT))
		{
			if (entityLayer[x][y] != null && entityLayer[x][y] instanceof DamageableEntity)
			{
				DamageableEntity d = (DamageableEntity) entityLayer[x][y];
				boolean destroyed = d.takeDamage(amount);
				if (destroyed)
				{
					entityLayer[x][y] = null;
					System.out.println("You killed a thing.");
				}
			}
			else
			{
				System.out.println("There's nothing here to damage");
			}
		}
		else
		{
			System.out.println("Damage location was out of the map");
		}
	}

	boolean isSpaceEmpty(int x, int y)
	{
		/*
		If there is a block or entity in that location, return false, else return true
		 */
		return entityLayer[x][y] == null && blockLayer[x][y] == null;
	}
	
	public void spawnFiretruck(int x, int y) { entityLayer[x][y] = new Firetruck(); }
	
	public void spawnBuilding(int x, int y) { blockLayer[x][y] = new Building(); }

	public void spawnFortress(int x, int y)
	{
		entityLayer[x][y] = new Fortress();
	}

	public void spawnFirestation(int x, int y) { entityLayer[x][y] = new Firestation(); }

	public void spawnObstacle(int x, int y) { blockLayer[x][y] = new Obstacle(); }
	
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
}
