package com.kroy.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Fortress extends Entity implements DamageableEntity
{
    /**
     * The class that represents an ET fortress in the map
     */

    static int MAX_HEALTH = 4;
    private int targetsPerTurn = 1;
    private int health;
    private int attackStrength = 1;
    private int attackRadius = 1;

    public Fortress()
    {
        /**
         * Constructor for fortresses
         */
        health = MAX_HEALTH;
        this.texture = new Texture(Gdx.files.internal("EtFortress.png"));
    }

    @Override
    public boolean takeDamage(int damageAmount)
    {
        /**
         * Called when damage is dealt to the Fortress
         * @param damageAmount  an integer that give the number of points of damage dealt to the fortress
         * @return              A boolean value where true indicates that the fortress was destroyed, false indicates
         *                      that it was not
         */
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
        /**
         * Increases basic stats of the fortress by one
         */
        this.attackRadius++;
        this.attackStrength++;
    }
}
