package main.java;

import com.badlogic.gdx.math.Vector2;
import com.kroy.game.map.ShortestPathfinder;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.kroy.game.entities.Firestation;
import com.kroy.game.map.Map;
import static org.junit.Assert.*;
import java.util.*;

@RunWith(GdxTestRunner.class)
public class ShortestPathfinderTest {

    @Test
    public void getDistanceMatrix() {
        Map testmap=new Map();
        boolean[][] occlusionMap = new boolean[testmap.WIDTH][testmap.HEIGHT];

        int[][] distancematrixtest=testmap.pathfinder.getDistanceMatrix(0,0);
        //horizontally
        //basecase
        assertEquals(distancematrixtest[0][0],0);
        assertEquals(distancematrixtest[0][1],1);
        //inductive step
        assertEquals(distancematrixtest[4][6],10);
        assertEquals(distancematrixtest[4][7],11);

        //vertically
        //basecase
        assertEquals(distancematrixtest[0][0],0);
        assertEquals(distancematrixtest[1][0],1);
        //inductive step
        assertEquals(distancematrixtest[4][6],10);
        assertEquals(distancematrixtest[5][6],11);

    }
}