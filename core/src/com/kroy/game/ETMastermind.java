package com.kroy.game;

import com.badlogic.gdx.math.Vector2;
import com.kroy.game.entities.Firetruck;
import com.kroy.game.entities.Fortress;
import com.kroy.game.map.Map;
import com.kroy.game.map.MapDrawer;

import java.util.ArrayList;

public class ETMastermind
{
    private Map map;
    private MapDrawer mapDrawer;

    public final int LEVEL_UP_FREQUENCY = 3;   // The number of turns that must elapse before fortresses become stronger

    public ETMastermind(Map map, MapDrawer mapDrawer)
    {
        this.map = map;
        this.mapDrawer = mapDrawer;
        drawCorruption();
    }

    public int getFortressNumber()
    {
        int count = 0;
        for (int i = 0; i < map.HEIGHT; i++)
        {
            for (int j = 0; j < map.WIDTH; j++)
            {
                if (map.getEntity(j, i) != null && map.getEntity(j, i) instanceof Fortress)
                {
                    count++;
                }
            }
        }

        return count;
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

    public void levelUpFortresses()
    {
        System.out.println("The fortresses grow stronger...");
        for (int i = 0; i < map.HEIGHT; i++)
        {
            for (int j = 0; j < map.WIDTH; j++)
            {
                if (map.getEntity(j, i) != null && map.getEntity(j, i) instanceof Fortress)
                {
                    Fortress f = (Fortress) map.getEntity(j, i);
                    f.levelUp();
                }
            }
        }
        drawCorruption();
    }

    public void drawCorruption()
    {
        for (int i = 0; i < map.HEIGHT; i++)
        {
            for (int j = 0; j < map.WIDTH; j++)
            {
                if (map.getEntity(j, i) != null && map.getEntity(j, i) instanceof Fortress)
                {
                    Fortress f = (Fortress) map.getEntity(j, i);
                    // Draw corruption to show ET fire range
                    for (int n = 0; n < map.HEIGHT; n++)
                    {
                        for (int m = 0; m < map.WIDTH; m++)
                        {
                            if (new Vector2(j, i).sub(m, n).len() <= f.getAttackRadius() + 1)
                            {
                                mapDrawer.setCorruption(m, n);
                            }
                        }
                    }
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
                if
                (
                    (0 <= i && i < Map.WIDTH) && (0 <= j && j < Map.HEIGHT)
                    &&
                    map.getEntity(i, j) != null
                    &&
                    map.getEntity(i, j) instanceof Firetruck
                )
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
