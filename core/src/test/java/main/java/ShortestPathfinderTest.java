package main.java;

import com.kroy.game.map.ShortestPathfinder;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.kroy.game.entities.Firestation;
import com.kroy.game.map.Map;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ShortestPathfinderTest {

    @Test
    public void shortestPath() {
        Map testmap=new Map();

        testmap.spawnObstacle(0,0);
        testmap.spawnFiretruck(5,5);
        testmap.spawnBuilding(10,10);
        testmap.spawnFortress(15,15);
        testmap.spawnFirestation(20,20);



    }

    @Test
    public void getDistanceMatrix() {
    }
}