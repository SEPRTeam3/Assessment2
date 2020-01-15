package com.kroy.game.entities;

import com.badlogic.gdx.graphics.Texture;

public abstract class Entity
{
	protected Texture texture;

	public Entity() {}
	
	public Texture getTexture()
	{
		return this.texture;
	}
}
