package com.kroy.game.map;

import com.badlogic.gdx.math.Vector2;
import com.kroy.game.DebugClass;

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

    private ArrayList<Vector2>[][] getPathMap(int fromX, int fromY)
    {
        this.buildOcclusionMap();

        ArrayList<Vector2>[][] pathArray = new ArrayList[map.WIDTH][map.HEIGHT];
        pathArray[fromX][fromY] = new ArrayList<Vector2>();
        ArrayDeque<Vector2> queue = new ArrayDeque<>();
        Vector2 start = new Vector2(fromX, fromY);
        queue.add(start);

        while (!queue.isEmpty())
        {
            Vector2 v = queue.pop();

            // Check each side for valid moves

            if (v.x+1<occlusionMap.length && occlusionMap[(int) v.x+1][(int) v.y])
            {
                if (pathArray[(int) v.x+1][(int) v.y] == null)
                {
                    ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                    p.add(v);
                    pathArray[(int) v.x+1][(int) v.y] = p;
                    queue.add(new Vector2(v.x+1, v.y));

                }
                else
                {
                    if (pathArray[(int) v.x+1][(int) v.y].size() > pathArray[(int) v.x][(int) v.y].size()+1)
                    {
                        ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                        p.add(v);
                        pathArray[(int) v.x+1][(int) v.y] = p;
                        queue.add(new Vector2(v.x+1, v.y));

                    }
                }
            }

            if (v.x-1>=0 && occlusionMap[(int) v.x-1][(int) v.y])
            {
                if (pathArray[(int) v.x-1][(int) v.y] == null)
                {
                    ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                    p.add(v);
                    pathArray[(int) v.x-1][(int) v.y] = p;
                    queue.add(new Vector2(v.x-1, v.y));

                }
                else
                {
                    if (pathArray[(int) v.x-1][(int) v.y].size() > pathArray[(int) v.x][(int) v.y].size()+1)
                    {
                        ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                        p.add(v);
                        pathArray[(int) v.x-1][(int) v.y] = p;
                        queue.add(new Vector2(v.x-1, v.y));

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

                }
                else
                {
                    if (pathArray[(int) v.x][(int) v.y+1].size() > pathArray[(int) v.x][(int) v.y].size()+1)
                    {
                        ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                        p.add(v);
                        pathArray[(int) v.x][(int) v.y+1] = p;
                        queue.add(new Vector2(v.x, v.y+1));

                    }
                }
            }

            if (v.y-1>=0 && occlusionMap[(int) v.x][(int) v.y-1])
            {
                if (pathArray[(int) v.x][(int) v.y-1] == null)
                {
                    ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                    p.add(v);
                    pathArray[(int) v.x][(int) v.y-1] = p;
                    queue.add(new Vector2(v.x, v.y-1));

                }
                else
                {
                    if (pathArray[(int) v.x][(int) v.y-1].size() > pathArray[(int) v.x][(int) v.y].size()+1)
                    {
                        ArrayList<Vector2> p = new ArrayList<Vector2>(pathArray[(int) v.x][(int) v.y]);
                        p.add(v);
                        pathArray[(int) v.x][(int) v.y-1] = p;
                        queue.add(new Vector2(v.x, v.y-1));

                    }
                }
            }
        }
        return pathArray;
    }

    public ArrayList<Vector2> shortestPath(int x1, int y1, int x2, int y2)
    {
		/*
		Finds the shortest path from (x1, y1) to (x2, y2) or returns null if the journey is impossible.
		The output is the series of tiles that must be passed over as vector coordinates.
		eg from (0, 0) to (3, 3) the result might be [(0, 1), (1, 1), (1, 2), (2, 2), (2, 3), (3, 3)]
		 */


		return this.getPathMap(x1, y1)[x2][y2];
    }

    public int[][] getDistanceMatrix(int fromX, int fromY)
    {
        ArrayList<Vector2>[][] pathMap = this.getPathMap(fromX, fromY);
        int[][] distanceMap = new int[map.WIDTH][map.HEIGHT];
        for (int i = 0; i < map.HEIGHT; i++)
        {
            for (int j = 0; j < map.WIDTH; j++)
            {
                if (pathMap[j][i] == null)
                {
                    distanceMap[j][i] = -1;
                }
                else
                {
                    distanceMap[j][i] = pathMap[j][i].size();
                }
            }
        }
        //System.out.println(DebugClass.get2DIntArrayPrint(distanceMap));
        return distanceMap;
    }
}
