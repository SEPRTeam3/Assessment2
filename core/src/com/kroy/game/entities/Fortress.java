package com.kroy.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.map.Block;

public class Fortress extends Entity implements DamageableEntity
{
    static int MAX_HEALTH = 4;
    private int targetsPerTurn = 1;
    private int health;
    private int attackStrength = 1;
    private int attackRadius = 2;

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

    public int getHealth(){return this.health; }

    public void setHealth(int health){this.health=health; }


    public int getAttackStrength()
    {
        return this.attackStrength;
    }

    public int getAttackRadius()
    {
        return this.attackRadius;
    }

    public int getTargetsPerTurn()
    {
        return this.targetsPerTurn;
    }

    public void levelUp()
    {
        this.attackRadius++;
        this.attackStrength++;
    }
}
