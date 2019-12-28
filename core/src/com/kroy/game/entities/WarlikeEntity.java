package com.kroy.game.entities;

import com.kroy.game.map.Map;

public interface WarlikeEntity
{
    public void attack(int targetX, int targetY, Map map);

    public void takeDamage(int i);
}
