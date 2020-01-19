package com.kroy.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kroy.game.blocks.Obstacle;
import com.kroy.game.entities.*;

import java.util.*;

public class Map
{
	public final static int HEIGHT = 24;
	public final static int WIDTH = 24;

	private Entity entityLayer[][] = new Entity[WIDTH][HEIGHT];
	private Block blockLayer[][] = new Block[WIDTH][HEIGHT];

	public ShortestPathfinder pathfinder = new ShortestPathfinder(this);
	
	public Map()
	{
		/**
		 * Map constructor, initialises all tiles to null
		 */
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				entityLayer[j][i] = null;
				blockLayer[j][i] = null;
			}
		}
	}

	
	public Entity getEntity(int x, int y) { return entityLayer[x][y]; }
	
	public Block getBlock(int x, int y) { return blockLayer[x][y]; }

	public void moveEntity(int x1, int y1, int x2, int y2)
	{
		/**
		 * Moves entity from (x1, y1) to (x2, y2) if the move is legal
		 * @param x1 x location of the entity to be moved
		 * @param y1 y location of the entity to be moved
		 * @param x2 x location to move to
		 * @param y2 y location to move to
		 */

		// If there is no entity at (x1, y1) do nothing
		if (entityLayer[x1][y1] == null)
		{
			return;
		}
		
		Entity e = entityLayer[x1][y1];
		
		if (e != null && e instanceof Firetruck)
		{
			// If entity is firetruck

			Firetruck f = (Firetruck) e;
			// Check if the firetruck has moved this turn
			if (!f.hasMovedThisTurn())
			{
				ArrayList<Vector2> path = pathfinder.shortestPath(x1, y1, x2, y2);

				if (path != null && path.size() -1 <= f.getMovementDistance())
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
		/**
		 * The entity at (x1, y1) attacks at (x2, y2) if it is possible to do so
		 * @param x1 the x location of the attacking entity
		 * @param y1 the y location of the attacking entity
		 * @param x2 the x location that is attacked
		 * @param y2 the y location that is attacked
		 */
		// Add pre-check that locations are within map
		if (entityLayer[x1][y1] != null)
		{
			if (entityLayer[x1][y1] instanceof Firetruck)
			{
				Firetruck f = (Firetruck) entityLayer[x1][y1];

				if
				(
					!f.hasAttackedThisTurn()
				)
				{
					boolean hasEnoughWater = f.useWater();
					if (hasEnoughWater)
					{
						if (pathfinder.straightPath(x1, y1, x2, y2))
						{
							damageLocation(f.getAttackStrength(), x2, y2);
							f.setAttackedThisTurn();
						}
						else
						{
							System.out.println("There was something in the way");
						}
					}
					else
					{
						System.out.println("The fire truck didn't have any water");
					}
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
		/**
		 * Deals a given amount of damage to any entity at (x, y)
		 * @param amount The amount of damage points to deal
		 * @param x The x location to deal damage to
		 * @param y The y location to deal damage to
		 */

		if ((x >= 0 && x < WIDTH) && (y >= 0 && y < HEIGHT))
		{
			if (entityLayer[x][y] != null && entityLayer[x][y] instanceof DamageableEntity)
			{
				DamageableEntity d = (DamageableEntity) entityLayer[x][y];
				boolean destroyed = d.takeDamage(amount);
				if (destroyed)
				{
					entityLayer[x][y] = new DestroyedEntity();
					System.out.println("Entity was destroyed");
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

	public boolean isSpaceEmpty(int x, int y)
	{
		/**
		 * Returns whether location (x, y) is empty
		 * @param x The x location to test
		 * @param y The y location to test
		 * @return true if the location contains no blocks or entities, else false
		 */
		return entityLayer[x][y] == null && blockLayer[x][y] == null;
	}
	
	public Firetruck spawnFiretruck(int x, int y)
	{
		/**
		 * Create firetruck at (x, y) with the default stats
		 * @param x The x location to spawn
		 * @param y The y location to spawn
		 * @return A reference to the firetruck that was spawned
		 */
		entityLayer[x][y] = new Firetruck();
		return (Firetruck) entityLayer[x][y];
	}

	public Firetruck spawnFiretruck(int x, int y, Texture texture, int maxMove, int maxHealth, int maxWater, int attackDistance, int attackStrength)
	{
		/**
		 * Create firetruck at (x, y) specifying the stats
		 * @param texture texture for the Firetruck
		 * @param maxMove maximum movement distance for the Firetruck
		 * @param maxHealth maximum health for the Firetruck
		 * @param maxWater maximum water capacity for the Firetruck
		 * @param attackDistance maxmimum attacking distance for the Firetruck
		 * @param attackStrength the number of points of damage dealt by the firetruck
		 * @return A reference to the firetruck that was spawned
		 */
		entityLayer[x][y] = new Firetruck(texture, maxMove, maxHealth, maxWater, attackDistance, attackStrength);
		return (Firetruck) entityLayer[x][y];
	}

	public Fortress spawnFortress(int x, int y)
	{
		/**
		 * Create fortress at (x, y)
		 * @param x The x location to spawn
		 * @param y The y location to spawn
		 * @return A reference to the fortress that was spawned
		 */
		entityLayer[x][y] = new Fortress();
		return (Fortress) entityLayer[x][y];
	}

	public Firestation spawnFirestation(int x, int y)
	{
		/**
		 * Create firestation at (x, y)
		 * @param x The x location to spawn
		 * @param y The y location to spawn
		 * @return A reference to the firestation that was spawned
		 */
		entityLayer[x][y] = new Firestation();
		return (Firestation) entityLayer[x][y];
	}

	public Obstacle spawnObstacle(int x, int y)
	{
		/**
		 * Create an obstacle at (x, y)
		 * @param x The x location to spawn
		 * @param y The y location to spawn
		 * @return A reference to the obstacle that was spawned
		 */
		blockLayer[x][y] = new Obstacle();
		return (Obstacle) blockLayer[x][y];
	}
	
	public void resetTurn()
	{
		/**
		 * Calls reset turn on every entity in the map
		 */
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				if (entityLayer[i][j] != null && entityLayer[i][j] instanceof Firetruck)
				{
					Firetruck f = (Firetruck) entityLayer[i][j];
					f.resetTurn();
				}
			}
		}
	}

	public Vector2 getFirestationLocation()
	{
		/**
		 * Gets the location of the firestation as a 2-vector
		 * @return firestation location
		 */
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				if (entityLayer[i][j] != null && entityLayer[i][j] instanceof Firestation)
				{
					return new Vector2(i, j);
				}
			}
		}
		return null;
	}
}
