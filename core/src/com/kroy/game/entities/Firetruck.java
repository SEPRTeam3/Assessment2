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
	private int health = 5;
	
	public Firetruck()
	{
		this.texture = new Texture(Gdx.files.internal("firetruck3.png"));
		this.id = entityID.FIRETRUCK;
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

	public boolean isMovementPossible(int x1, int y1, int x2, int y2)
	{
		/*
		Returns true if the movement is possible within the firetruck's movement pattern
		Returns false if not
		Does not consider map dimensions or obstructions!
		 */
		double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
		return this.getMovementDistance() > distance;
	}

//	public void attack(int targetX, int targetY, Map map)
//	{
//		Entity e = map.getEntity(targetX, targetY);
//		if (e != null && e instanceof WarlikeEntity)
//		{
//			System.out.println("Dealing damage to the enemy");
//			((WarlikeEntity) e).takeDamage(attackStrength);
//		}
//		else
//		{
//			System.out.println("There's nothing for me to attack");
//		}
//	}

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
