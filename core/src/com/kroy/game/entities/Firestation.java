package com.kroy.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Firestation extends Entity implements DamageableEntity
{
    public Firestation()
    {
        this.texture = new Texture(Gdx.files.internal("firestation.png"));
    }

    @Override
    public boolean takeDamage(int amount) {
        return false;
    }
}
