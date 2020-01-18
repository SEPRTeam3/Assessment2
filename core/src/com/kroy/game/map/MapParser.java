package com.kroy.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MapParser
{
    /**
     * Class for turning elements of the tiled map into interactive objects in the map
     */
    public MapParser() {}

    private int firetrucksAdded = 0;

    private Texture truckOneTexture = new Texture(Gdx.files.internal("SpecialFiretrucks1.png"));
    private Texture truckTwoTexture = new Texture(Gdx.files.internal("SpecialFiretrucks2.png"));

    public void addAll(Map map, TiledMap tilemap)
    {
        /**
         * spawns all entities designated in the tilemap
         * @param map the Map object to add entities to
         * @param tilemap the TiledMap object to read layers from
         */
        addTrucks(map, tilemap);
        addObstacles(map, tilemap);
        addFortresses(map, tilemap);
        addFirestations(map, tilemap);
    }

    private void addTrucks(Map map, TiledMap tilemap)
    {
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) tilemap.getLayers().get("TrucksTiles");

        for (int x = 0; x < tileLayer.getWidth(); x++)
        {
            for (int y = 0; y < tileLayer.getHeight(); y++)
            {
                TiledMapTileLayer.Cell cell =  tileLayer.getCell(x, y);
                if (cell != null)
                {
                    switch (firetrucksAdded)
                    {
                        case 0:
                            map.spawnFiretruck(x,tileLayer.getHeight()-y-1, truckOneTexture, 3, 10, 10, 10, 1);
                            firetrucksAdded++;
                            break;
                        case 1:
                            map.spawnFiretruck(x,tileLayer.getHeight()-y-1, truckTwoTexture, 10, 3, 1, 5, 2);
                            firetrucksAdded++;
                            break;
                        default:
                            map.spawnFiretruck(x,tileLayer.getHeight()-y-1);
                            firetrucksAdded++;
                            break;
                    }
                }
            }
        }
    }

    private void addObstacles(Map map, TiledMap tilemap)
    {
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) tilemap.getLayers().get("Obstacles");

        for (int x = 0; x < tileLayer.getWidth(); x++)
        {
            for (int y = 0; y < tileLayer.getHeight(); y++)
            {
                TiledMapTileLayer.Cell cell =  tileLayer.getCell(x, y);
                if (cell != null)
                {
                    map.spawnObstacle(x, tileLayer.getHeight()-y-1);
                }
            }
        }
    }

    private void addFortresses(Map map, TiledMap tilemap)
    {
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) tilemap.getLayers().get("Fortress");

        for (int x = 0; x < tileLayer.getWidth(); x++)
        {
            for (int y = 0; y < tileLayer.getHeight(); y++)
            {
                TiledMapTileLayer.Cell cell =  tileLayer.getCell(x, y);
                if (cell != null)
                {
                    map.spawnFortress(x, tileLayer.getHeight()-y-1);
                }
            }
        }
    }

    private void addFirestations(Map map, TiledMap tilemap)
    {
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) tilemap.getLayers().get("FireStation");

        for (int x = 0; x < tileLayer.getWidth(); x++)
        {
            for (int y = 0; y < tileLayer.getHeight(); y++)
            {
                TiledMapTileLayer.Cell cell =  tileLayer.getCell(x, y);
                if (cell != null)
                {
                    map.spawnFirestation(x, tileLayer.getHeight()-y-1);
                }
            }
        }
    }

    public static TiledMapTileLayer.Cell getCorruption(TiledMap tilemap)
    {
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) tilemap.getLayers().get("FireStation");
        for (int x = 0; x < tileLayer.getWidth(); x++)
        {
            for (int y = 0; y < tileLayer.getHeight(); y++)
            {
                TiledMapTileLayer.Cell cell =  tileLayer.getCell(x, y);
                if (cell != null)
                {
                    return cell;
                }
            }
        }
        return null;
    }
}
