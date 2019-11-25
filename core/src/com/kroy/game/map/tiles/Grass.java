package com.kroy.game.map.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.map.Tile;

public class Grass extends Tile
{
	public Grass()
	{
		this.texture = new Texture(Gdx.files.internal("Grass.png"));
	}
}
