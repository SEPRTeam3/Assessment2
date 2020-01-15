package com.kroy.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.map.Map;

import static com.kroy.game.map.Map.HEIGHT;
import static com.kroy.game.map.Map.WIDTH;

public class Firetruck extends Entity implements DamageableEntity
{
	private boolean movedThisTurn;
	private boolean attackedThisTurn;
	private int movementDistance = 5;
	private int attackStrength = 1;
	private int maxHealth = 5;
	private int health = maxHealth;
	private int maxWater = 2;
	private int water = maxWater;


	public Firetruck()
	{
		this.texture = new Texture(Gdx.files.internal("firetruck3.png"));
		this.movedThisTurn = false;
		this.attackedThisTurn = false;
	}
	
	public boolean hasMovedThisTurn()
	{
		return this.movedThisTurn;
	}
	
	public void setMovedThisTurn()
	{
		this.movedThisTurn = true;
	}

	public boolean hasAttackedThisTurn() { return this.attackedThisTurn; }

	public void setAttackedThisTurn() { this.attackedThisTurn = true; }
	
	public void resetTurn()
	{
		this.movedThisTurn = false;
		this.attackedThisTurn = false;
	}

	public int getMovementDistance() { return this.movementDistance; }

	public boolean isAttackPossible(int x1, int y1, int x2, int y2)
	{
		/*
		Returns true if the attack is possible within the firetruck's attack pattern
		Returns false if not
		Does not consider map dimensions or obstructions!
		 */
		return (x1 == x2) || (y1 == y2);
	}

	public boolean useWater()
	{
		/*
		Returns true if the firetruck has water left, and depletes the available water
		Returns false if there is no available water
		 */
		if (this.water <= 0)
		{
			System.out.println("The firetruck had no water left!");
			return false;
		}
		else
		{
			water--;
			return true;
		}
	}

	public void restock()
	{
		/*
		Returns the firetruck to max health and max water
		 */
		this.health = maxHealth;
		this.water = maxWater;
	}

	public boolean takeDamage(int i)
	{
		this.health -= i;
		return this.health <= 0;
	}

	public int getAttackStrength()
	{
		return this.attackStrength;
	}
}
