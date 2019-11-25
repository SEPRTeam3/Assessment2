package com.kroy.game.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.map.Block;

public class Building extends Block
{
	public Building()
	{
		this.texture = new Texture(Gdx.files.internal("buildings.png"));
	}
}
