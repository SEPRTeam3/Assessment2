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

    public ArrayList<Vector2> shortestPath(int x1, int y1, int x2, int y2)
    {
		/*
		Finds the shortest path from (x1, y1) to (x2, y2) or returns null if the journey is impossible.
		The output is the series of tiles that must be passed over as vector coordinates.
		eg from (0, 0) to (3, 3) the result might be [(0, 1), (1, 1), (1, 2), (2, 2), (2, 3), (3, 3)]
		 */
		this.buildOcclusionMap();

		ArrayList<Vector2>[][] pathArray = new ArrayList[map.WIDTH][map.HEIGHT];
		pathArray[x1][y1] = new ArrayList<Vector2>();
		ArrayDeque<Vector2> queue = new ArrayDeque<>();
		Vector2 start = new Vector2(x1, y1);
		Vector2 goal = new Vector2(x2, y2);
		queue.add(start);

		while (!queue.isEmpty())
        {
            Vector2 v = queue.pop();
            System.out.println("Looking for moves from" + v);
            // Check each side for valid moves

            if (v.x+1<occlusionMap.length && occlusionMap[(int) v.x+1][(int) v.y])
            {
                if (pathArray[(int) v.x+1][(int) v.y] == null)
                {
                    ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                    p.add(v);
                    pathArray[(int) v.x+1][(int) v.y] = p;
                    queue.add(new Vector2(v.x+1, v.y));

                    System.out.println("Move to right takes " + p);
                }
                else
                {
                    if (pathArray[(int) v.x+1][(int) v.y].size() > pathArray[(int) v.x][(int) v.y].size()+1)
                    {
                        ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                        p.add(v);
                        pathArray[(int) v.x+1][(int) v.y] = p;
                        queue.add(new Vector2(v.x+1, v.y));

                        System.out.println("Move to right takes " + p);
                    }
                }
            }

            if (v.x-1>0 && occlusionMap[(int) v.x-1][(int) v.y])
            {
                if (pathArray[(int) v.x-1][(int) v.y] == null)
                {
                    ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                    p.add(v);
                    pathArray[(int) v.x-1][(int) v.y] = p;
                    queue.add(new Vector2(v.x-1, v.y));

                    System.out.println("Move to left takes " + p);
                }
                else
                {
                    if (pathArray[(int) v.x-1][(int) v.y].size() > pathArray[(int) v.x][(int) v.y].size()+1)
                    {
                        ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                        p.add(v);
                        pathArray[(int) v.x-1][(int) v.y] = p;
                        queue.add(new Vector2(v.x-1, v.y));

                        System.out.println("Move to left takes " + p);
                    }
                }
            }

            if (v.y+1<occlusionMap[0].length && occlusionMap[(int) v.x][(int) v.y+1])
            {
                if (pathArray[(int) v.x][(int) v.y+1] == null)
                {
                    ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                    p.add(v);
                    pathArray[(int) v.x][(int) v.y+1] = p;
                    queue.add(new Vector2(v.x, v.y+1));

                    System.out.println("Move to up takes " + p);
                }
                else
                {
                    if (pathArray[(int) v.x][(int) v.y+1].size() > pathArray[(int) v.x][(int) v.y].size()+1)
                    {
                        ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                        p.add(v);
                        pathArray[(int) v.x][(int) v.y+1] = p;
                        queue.add(new Vector2(v.x, v.y+1));

                        System.out.println("Move to up takes " + p);
                    }
                }
            }

            if (v.y-1>0 && occlusionMap[(int) v.x][(int) v.y-1])
            {
                if (pathArray[(int) v.x][(int) v.y-1] == null)
                {
                    ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                    p.add(v);
                    pathArray[(int) v.x][(int) v.y-1] = p;
                    queue.add(new Vector2(v.x, v.y-1));

                    System.out.println("Move to down takes " + p);
                }
                else
                {
                    if (pathArray[(int) v.x][(int) v.y-1].size() > pathArray[(int) v.x][(int) v.y].size()+1)
                    {
                        ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                        p.add(v);
                        pathArray[(int) v.x][(int) v.y-1] = p;
                        queue.add(new Vector2(v.x, v.y-1));

                        System.out.println("Move to down takes " + p);
                    }
                }
            }
        }

		return pathArray[x2][y2];
    }
}
