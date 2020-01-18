package com.kroy.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class Block
{
	/**
	 * A block is a map object that is static and cannot perform actions
	 */
	protected Texture texture;
	
	public Block(){}
	
	public Texture getTexture()
	{
		return this.texture;
	}
}