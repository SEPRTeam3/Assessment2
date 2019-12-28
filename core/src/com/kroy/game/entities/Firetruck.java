package com.kroy.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.map.Map;

import static com.kroy.game.map.Map.HEIGHT;
import static com.kroy.game.map.Map.WIDTH;

public class Firetruck extends Entity implements WarlikeEntity
{
	private boolean movedThisTurn;
	private int movementDistance = 5;
	private int attackStrength = 1;
	private int maxHealth = 5;
	private int health = 5;
	
	public Firetruck()
	{
		this.texture = new Texture(Gdx.files.internal("firetruck3.png"));
		this.id = entityID.FIRETRUCK;
		this.movedThisTurn = false;
	}
	
	public boolean hasMovedThisTurn()
	{
		return this.movedThisTurn;
	}
	
	public void setMovedThisTurn()
	{
		this.movedThisTurn = true;
	}
	
	public void resetTurn()
	{
		this.movedThisTurn = false;
	}

	public int getMovementDistance() { return this.movementDistance; }

	public boolean[][] getAttackPattern(int x, int y, Map map)
	{
		boolean[][] reachable = new boolean[map.HEIGHT][map.WIDTH];

		for (int i = 0; i < map.HEIGHT; i++)
		{
			for (int j = 0; j < map.WIDTH; j++)
			{
				if (i == y || j == x)
				{
					reachable[i][j] = true;
				}
				else
				{
					reachable[i][j] = false;
				}
			}
		}
		return reachable;
	}

	public void attack(int targetX, int targetY, Map map)
	{
		Entity e = map.getEntity(targetX, targetY);
		if (e != null && e instanceof WarlikeEntity)
		{
			System.out.println("Dealing damage to the enemy");
			((WarlikeEntity) e).takeDamage(attackStrength);
		}
		else
		{
			System.out.println("There's nothing for me to attack");
		}
	}

	public void takeDamage(int i)
	{
		this.health -= i;
	}
}
