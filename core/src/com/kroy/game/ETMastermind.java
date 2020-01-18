package com.kroy.game;

import com.badlogic.gdx.math.Vector2;
import com.kroy.game.entities.Firetruck;
import com.kroy.game.entities.Fortress;
import com.kroy.game.map.Map;
import com.kroy.game.map.MapDrawer;

import java.util.ArrayList;

public class ETMastermind
{
    /**
     * ET AI class
     */

    private Map map;
    private MapDrawer mapDrawer;

    public final int LEVEL_UP_FREQUENCY = 3;   // The number of turns that must elapse before fortresses become stronger

    public ETMastermind(Map map, MapDrawer mapDrawer)
    {
        /**
         * Constructor for ETMastermind
         * @param map the Map object for the Mastermind to manipulate in heinous ways
         */
        this.map = map;
        this.mapDrawer = mapDrawer;
        drawCorruption();
    }

    public int getFortressNumber()
    {
        /**
         * Returns the number of fortresses in the map
         * @return The number of fortresses in the map
         */
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
        /**
         * Triggers the Mastermind to think deeply and take coordinated action
         */

        // For each fortress, look for nearby truck to attack
        for (int i = 0; i < map.HEIGHT; i++)
        {
            for (int j = 0; j < map.WIDTH; j++)
            {
                if (map.getEntity(j, i) != null && map.getEntity(j, i) instanceof Fortress)
                {
                    Fortress f = (Fortress) map.getEntity(j, i);
                    attackNInRadius(f.getAttackStrength(), f.getAttackRadius(), f.getTargetsPerTurn(), j, i);
                }
            }
        }
    }

    public void levelUpFortresses()
    {
        /**
         * Increases the strength of all fortresses in the map by calling 'levelUp' on each of them
         */
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

        // Update corruption
        drawCorruption();
    }

    public void drawCorruption()
    {
        /**
         * set all tiles reachable by any fortress to be displayed as 'corruption'
         */
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
        /**
         * Attacks the first n firetrucks found within a radius of (x, y) where the damage dealt is given.
         * @param damage The number of points of damage to be dealt to the enemy
         * @param radius The radius of the circle centred at (x, y) within which to deal damage
         * @param x The x location of the locus
         * @param y The y location fo the locus
         * @param n The number of enemies to attack
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
