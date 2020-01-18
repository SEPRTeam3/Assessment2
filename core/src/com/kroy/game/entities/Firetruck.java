package com.kroy.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.map.Map;

import static com.kroy.game.map.Map.HEIGHT;
import static com.kroy.game.map.Map.WIDTH;

public class Firetruck extends Entity implements DamageableEntity
{
	/**
	 * Firetruck entity
	 */
	private boolean movedThisTurn;
	private boolean attackedThisTurn;
	private int movementDistance = 5;
	private int attackDistance = 5;
	private int attackStrength = 1;
	private int maxHealth = 5;
	private int health = maxHealth;
	private int maxWater = 2;
	private int water = maxWater;


	public Firetruck()
	{
		/**
		 * Firetruck default constructor.
		 * @return firetruck with default stats and texture
		 */
		this.texture = new Texture(Gdx.files.internal("firetruck3.png"));
		this.movedThisTurn = false;
		this.attackedThisTurn = false;
	}

	public int getHealth(){return this.health; }

	public void setHealth(int health){this.health=health; }


	public Firetruck(Texture texture, int maxMove, int maxHealth, int maxWater)
	{
		/**
		 * Alternate Firetruck constructor
		 * @param texture texture for the Firetruck
		 * @param maxMove maximum movement distance for the Firetruck
		 * @param maxHealth maximum health for the Firetruck
		 * @param maxWater maximum water capacity for the Firetruck
		 * @param attackDistance maxmimum attacking distance for the Firetruck
		 * @param attackStrength the number of points of damage dealt by the firetruck
		 * @return firetruck with specified stats
		 */
		System.out.println("Alt init is used");
		this.texture = texture;
		this.movementDistance = maxMove;
		this.maxHealth = maxHealth;
		this.maxWater = maxWater;
		this.movedThisTurn = false;
		this.attackedThisTurn = false;
		this.attackDistance = attackDistance;
		this.attackStrength = attackStrength;
	}

	public int getHealth(){return this.health; }

	public void setHealth(int health){this.health=health; }

	public boolean hasMovedThisTurn() { return this.movedThisTurn; }
	
	public void setMovedThisTurn() { this.movedThisTurn = true; }

	public boolean hasAttackedThisTurn() { return this.attackedThisTurn; }

	public void setAttackedThisTurn() { this.attackedThisTurn = true; }
	
	public void resetTurn()
	{
		/**
		 * Allows the Firetruck to move and attack again
		 */
		this.movedThisTurn = false;
		this.attackedThisTurn = false;
	}

	public int getMovementDistance() { return this.movementDistance; }

	public int getAttackDistance() { return this.attackDistance; }

	public boolean isAttackPossible(int x1, int y1, int x2, int y2)
	{
		/**
		 * Returns true if a firetruck at (x1, y1) can attack (x2, y2), false if not.
		 * Does not consider map dimensions or obstructions!
		 * @param x1 x location of firetruck
		 * @param y1 y location of firetruck
		 * @param x2 x location of attack
		 * @param y2 y location of attack
		 * @return true if the firetruck can attack this location
		 */
		// attack pattern is in a straight line an within radius of attack distance
		boolean inLine = (x1 == x2) || (y1 == y2);
		boolean inRadius = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)) <= this.attackDistance;
		return inLine && inRadius;
	}

	public boolean useWater()
	{
		/**
		 * Called when the firetruck uses water, decreases the Firetruck's stored water by one.
		 * @return true if the Firetruck has water left, false if it is empty
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

	public int getWater() { return(this.water); }

	public void setWater(int water){this.water=water;}


	public void restock()
	{
		/**
		 * Returns the firetruck's health and water to the maximum values
		 */
		this.health = maxHealth;
		this.water = maxWater;
	}

	public boolean takeDamage(int i)
	{
		/**
		 * Deals i points of damage to the Firetruck
		 * @return true if the firetruck is destroyed, else return false
		 */
		this.health -= i;
		return this.health <= 0;
	}

	public int getAttackStrength() { return this.attackStrength; }
}
