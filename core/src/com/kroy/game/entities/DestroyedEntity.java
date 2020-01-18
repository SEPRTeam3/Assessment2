package com.kroy.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class DestroyedEntity extends Entity
{
    /**
     * Destroyed entity is a entity that created in place of any entity that is destroyed.
     * It only contains texture data.
     */
    public DestroyedEntity() {this.texture = new Texture(Gdx.files.internal("Destroyed.png"));}
}
