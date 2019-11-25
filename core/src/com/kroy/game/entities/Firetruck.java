package com.kroy.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Firetruck extends Entity
{
	private boolean movedThisTurn;
	
	public Firetruck()
	{
		this.texture = new Texture(Gdx.files.internal("Firetruck2.png"));
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
}
