package com.kroy.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class Block
{
	protected Texture texture;
	
	public Block(){}
	
	public Texture getTexture()
	{
		return this.texture;
	}
}