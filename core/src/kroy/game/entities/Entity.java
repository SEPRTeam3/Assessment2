package kroy.game.entities;

import com.badlogic.gdx.graphics.Texture;

public abstract class Entity
{
	public enum entityID
	{
		FIRETRUCK
	}
	
	protected Texture texture;
	public entityID id;
	
	public Entity() {}
	
	public Texture getTexture()
	{
		return this.texture;
	}
}
