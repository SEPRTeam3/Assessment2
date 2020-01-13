package com.kroy.game;

import com.badlogic.gdx.math.Vector2;
import com.kroy.game.entities.Firetruck;
import com.kroy.game.entities.Fortress;
import com.kroy.game.map.Map;

import java.util.ArrayList;

public class ETMastermind
{
    private Map map;

    public ETMastermind(Map map)
    {
        this.map = map;
    }

    private ArrayList<Fortress> getFortresses()
    {
        ArrayList<Fortress> fortresses = new ArrayList<Fortress>();

        return fortresses;
    }

    public void takeTurn()
    {
        for (int i = 0; i < map.HEIGHT; i++)
        {
            for (int j = 0; j < map.WIDTH; j++)
            {
                if (map.getEntity(j, i) != null && map.getEntity(j, i) instanceof Fortress)
                {
                    Vector2 fortressLocation = new Vector2(j, i);
                    Fortress f = (Fortress) map.getEntity(j, i);
                    // For each fortress, look for nearby truck to attack
                    attackNInRadius(f.getAttackStrength(), f.getAttackRadius(), f.getTargetsPerTurn(), j, i);
                }
            }
        }
    }

    private void attackNInRadius(int damage, int radius, int n, int x, int y)
    {
        /*
        Attacks the first n firetrucks found within a radius of (x, y) where the damage dealt is given.
         */
        int attacksLeft = n;

        for (int i = x-radius; i <= x + radius; i++)
        {
            for (int j = y-radius; j <= y + radius; j++)
            {
                if (map.getEntity(i, j) != null && map.getEntity(i, j) instanceof Firetruck)
                {
                    map.damageLocation(damage, i, j);
                    attacksLeft--;
                    if (attacksLeft <= 0)
                    {
                        return;
                    }
                }
            }
        }
    }
}
