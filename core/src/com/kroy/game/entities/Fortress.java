package com.kroy.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.map.Block;

public class Fortress extends Entity implements DamageableEntity
{
    static int MAX_HEALTH = 4;
    int health;

    public Fortress()
    {
        health = MAX_HEALTH;
        this.texture = new Texture(Gdx.files.internal("EtFortress.png"));
    }

    @Override
    public boolean takeDamage(int damageAmount)
    {
        health -= damageAmount;
        System.out.println("Fortress: \" Ow! This really hurts. Stop it! \"");
        return health <= 0;
    }
}
