package com.kroy.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Firestation extends Entity implements DamageableEntity
{
    public static final int restockingRadius = 2;

    public Firestation()
    {
        this.texture = new Texture(Gdx.files.internal("core/assets/firestation.png"));
    }

    @Override
    public boolean takeDamage(int amount) {
        return false;
    }
}
