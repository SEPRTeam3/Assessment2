package com.kroy.game.map;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class ShortestPathfinder
{
    /*
    Container class for shortest path finding
     */

    private Map map;
    private boolean[][] occlusionMap = new boolean[map.WIDTH][map.HEIGHT];

    public ShortestPathfinder(Map map)
    {
        this.map = map;
    }

    private void buildOcclusionMap()
    {
        for (int i = 0; i < map.HEIGHT; i++)
        {
            for (int j = 0; j < map.WIDTH; j++)
            {
                occlusionMap[j][i] = map.isSpaceEmpty(j, i);
            }
        }
    }

    public Vector2[] shortestPath(int x1, int y1, int x2, int y2)
    {
		/*
		Finds the shortest path from (x1, y1) to (x2, y2) or returns null if the journey is impossible.
		The output is the series of tiles that must be passed over as vector coordinates.
		eg from (0, 0) to (3, 3) the result might be [(0, 1), (1, 1), (1, 2), (2, 2), (2, 3), (3, 3)]
		 */
		ArrayList<Vector2>[][] pathArray = new ArrayList[map.WIDTH][map.HEIGHT];
		ArrayDeque<Vector2> queue = new ArrayDeque<>();
		Vector2 start = new Vector2(x1, y1);
		Vector2 goal = new Vector2(x2, y2);
		queue.add(start);

		while (!queue.isEmpty())
        {
            Vector2 v = queue.pop();
            // Check each side for valid moves

            if (!occlusionMap[(int) v.x+1][(int) v.y])
            {
                if (pathArray[(int) v.x+1][(int) v.y] == null)
                {
                    ArrayList<Vector2> p = pathArray[(int) v.x+1][(int) v.y];
                    p.add(0, v);
                    pathArray[(int) v.x+1][(int) v.y] = p;
                    queue.add(new Vector2(v.x+1, v.y));
                }
                else
                {
                    //if ()
                }
            }

            if (!occlusionMap[(int) v.x-1][(int) v.y])
            {

            }

            if (!occlusionMap[(int) v.x][(int) v.y+1])
            {

            }

            if (!occlusionMap[(int) v.x][(int) v.y-1])
            {

            }
        }

		return null;
    }
}
