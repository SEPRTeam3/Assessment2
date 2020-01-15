package com.kroy.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class DestroyedEntity extends Entity
{
    public DestroyedEntity() {this.texture = new Texture(Gdx.files.internal("Destroyed.png"));}
}
