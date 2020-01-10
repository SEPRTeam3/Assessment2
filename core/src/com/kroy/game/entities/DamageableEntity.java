package com.kroy.game.entities;

public interface DamageableEntity
{
    boolean takeDamage(int amount); // Boolean returns true if the entity was destroyed
}
