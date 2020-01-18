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
        /**
         * Constructor for shortest pathfinder helper class
         * @param map   This is the map object with the positions of entities and blocks so the path finder can navigate
         */
        this.map = map;
    }

    private void buildOcclusionMap()
    {
        /**
         * Builds and stores a matrix the same size as a map where each element is a boolean
         */
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
        /**
         * Helper function that takes a location and generates an array of the shortest paths to every tile in the map
         * @param fromX The x location to start from
         * @param fromY the y location to start from
         * @return a map-size array of lists of vectors representing the list of tiles traversed to get to that tile
         */
        this.buildOcclusionMap();

        // Setup queue
        ArrayList<Vector2>[][] pathArray = new ArrayList[map.WIDTH][map.HEIGHT];
        pathArray[fromX][fromY] = new ArrayList<Vector2>();
        ArrayDeque<Vector2> queue = new ArrayDeque<>();
        Vector2 start = new Vector2(fromX, fromY);
        queue.add(start);

        while (!queue.isEmpty())
        {
            Vector2 v = queue.pop();

            // Check each side for valid moves

            // Check right
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

            // Check left
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

            // Check up
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

            // Check down
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

    public boolean straightPath(int x1, int y1, int x2, int y2)
    {
        /**
         * looks for a unobstructed horizontal or vertical line from (x1, y1) to (x2, y2)
         * @param x1 The x location to travel from
         * @param y1 The y location to travel from
         * @param x2 The x location to travel to
         * @param y2 the y location to travel to
         * @return true if a path exists, returns false if it doesn't
         */
        buildOcclusionMap();

        if (x1 == x2 && y1 == y2)
        {
            // Same location
            return true;
        }
        else if (x1 == x2)
        {
            if (y1 < y2)
            {
                // target is up from source
                for (int i = y1 + 1; i < y2; i++)
                {
                    if (this.occlusionMap[x1][i] == false)
                    {
                        return false;
                    }
                }
            }
            else
            {
                // target is down from source
                for (int i = y1 -1; i > y1; i--)
                {
                    if (this.occlusionMap[x1][i] == false)
                    {
                        return false;
                    }
                }
            }
        }
        else if (y1 == y2)
        {
            if (x1 < x2)
            {
                // target is right from source
                for (int i = x1 + 1; i < x2; i++)
                {
                    if (this.occlusionMap[i][y1] == false)
                    {
                        return false;
                    }
                }
            }
            else
            {
                // target is left from source
                for (int i = x1 - 1; i > x2; i--)
                {
                    if (this.occlusionMap[i][y1] == false)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<Vector2> shortestPath(int x1, int y1, int x2, int y2)
    {
		/**
		 * Finds the shortest path from (x1, y1) to (x2, y2) or returns null if the journey is impossible.
		 * The output is the series of tiles that must be passed over as vector coordinates.
		 * eg from (0, 0) to (3, 3) the result might be [(0, 1), (1, 1), (1, 2), (2, 2), (2, 3), (3, 3)]
         * @param x1 The x location to travel from
         * @param y1 The y location to travel from
         * @param x2 The x location to travel to
         * @param y2 the y location to travel to
         * @result List of tiles traversed as coordinates
		 */

        ArrayList<Vector2> path = this.getPathMap(x1, y1)[x2][y2];
        if (path != null)
        {
            path.add(new Vector2(x2, y2));
        }
		return path;
    }

    public int[][] getDistanceMatrix(int fromX, int fromY)
    {
        /**
         * Gets a matrix of the shortest distance to each tile given the origin (x, y)
         * @param fromX The x location to travel from
         * @param fromY The y location to travel from
         * @return an integer array the size of the map where the i by j th element represents the number of moves taken
         * to traverse from (x, y) to (i, j). Elements are set to -1 if the space is unreachable
         */
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

        return distanceMap;
    }
}
