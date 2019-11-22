package kroy.game.objs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import kroy.game.map.Obj;

public class Building extends Obj
{
	public Building()
	{
		this.texture = new Texture(Gdx.files.internal("buildings.png"));
	}
}
