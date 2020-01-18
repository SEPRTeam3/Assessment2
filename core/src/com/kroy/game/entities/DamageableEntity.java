package com.kroy.game.entities;

public interface DamageableEntity
{
    /**
     * Interface for any entity that can be dealt damage
     */
    boolean takeDamage(int amount); // Boolean returns true if the entity was destroyed
}
