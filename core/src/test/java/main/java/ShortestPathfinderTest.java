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

        int[][] distancematrixtest=testmap.pathfinder.getDistanceMatrix(0,0);


        //empty map with no obstructions should have format distancematrix[i][j]=n distancematrix[i+1][j]=n+1 and
        // distancematrix[i][j+1]=n+1

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


        //Distance matrix with obstructions should show -1 where there are obstructions
        testmap.spawnObstacle(2,2);
        int[][] distancematrixtest1=testmap.pathfinder.getDistanceMatrix(0,0);
        assertEquals(distancematrixtest1[2][2],-1);
    }

    @Test
    public void straightPath() {
        Map testmap=new Map();
        //There is a "straight path" from a block to itself, method returns true
        assertTrue(testmap.pathfinder.straightPath(2,2,2,2));

        //there is a straight path from a block on the same y axis as another block - no obstructions
        assertTrue(testmap.pathfinder.straightPath(3,4,3,3));
        assertTrue(testmap.pathfinder.straightPath(3,3,3,4));

        //there is a no straight path from a block on the same y axis as another block with obstructions
        testmap.spawnObstacle(5,6);
        assertFalse(testmap.pathfinder.straightPath(5,7,5,5));
        assertFalse(testmap.pathfinder.straightPath(5,5,5,7));

        //there is a straight path from a block on the same x axis as another block - no obstructions
        assertTrue(testmap.pathfinder.straightPath(4,3,3,3));
        assertTrue(testmap.pathfinder.straightPath(3,3,4,3));

        //there is a no straight path from a block on the same x axis as another block with obstructions
        testmap.spawnObstacle(6,5);
        assertFalse(testmap.pathfinder.straightPath(7,5,5,5));
        assertFalse(testmap.pathfinder.straightPath(5,5,7,5));

    }
}