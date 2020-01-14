package com.kroy.game.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MapParser
{
    public MapParser() {}

    public void addAll(Map map, TiledMap tilemap)
    {
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
                    map.spawnFiretruck(x, tileLayer.getHeight()-y-1);
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
}
