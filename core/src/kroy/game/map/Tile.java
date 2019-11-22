package kroy.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class Tile
{
	protected Texture texture;
	
	public Tile(){}
	
	public Texture getTexture()
	{
		return this.texture;
	}
}
