package kroy.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class Obj
{
	protected Texture texture;
	
	public Obj(){}
	
	public Texture getTexture()
	{
		return this.texture;
	}
}