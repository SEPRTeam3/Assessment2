package kroy.game.map;

import kroy.game.entities.Entity;
import kroy.game.entities.Firetruck;
import kroy.game.map.tiles.Grass;

public class Map
{
	public final static int HEIGHT = 16;
	public final static int WIDTH = 16;
	
	private Tile backgroundLayer[][] = new Tile[WIDTH][HEIGHT];
	private Entity entityLayer[][] = new Entity[WIDTH][HEIGHT];
	
	public Map()
	{
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				backgroundLayer[j][i] = new Grass();
				entityLayer[i][j] = null;
			}
		}
		entityLayer[2][3] = new Firetruck();
	}
	
	public Tile getBackground(int x, int y)
	{
		return backgroundLayer[x][y];
	}
	
	public Entity getEntity(int x, int y)
	{
		return entityLayer[x][y];
	}
	
	public void moveEntity(int x1, int y1, int x2, int y2)
	{
		entityLayer[x2][y2] = entityLayer[x1][y1];
		entityLayer[x1][y1] = null;
	}
	
	public void debugMakeFiretruck(int x, int y)
	{
		entityLayer[x][y] = new Firetruck();
	}
	
}
