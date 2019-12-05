package com.kroy.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Firetruck extends Entity
{
	private boolean movedThisTurn;
	private int movementDistance = 5;
	
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
}
