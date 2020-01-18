package com.kroy.game.entities;

import com.badlogic.gdx.graphics.Texture;

public abstract class Entity
{
	/**
	 * Entity is an abstract class that represents something on that map that is capable of taking actions
	 * It has a texture that is rendered to represent the entity in the map
	 */
	protected Texture texture;

	public Entity() {}
	
	public Texture getTexture()
	{
		/**
		 * Getter for entity textures
		 * @return the texture of the entity
		 */
		return this.texture;
	}
}
