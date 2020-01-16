package main.java;

import com.kroy.game.blocks.*;
import com.kroy.game.entities.*;
import com.kroy.game.map.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class MapTest {

    @Test
    public void testGetEntity() {
        Map testmap = new Map();

        //testing edge boundaries and Firetruck entity
        assertNull(testmap.getEntity(0, 0));
        assertNull(testmap.getEntity(23, 23));
        testmap.spawnFiretruck(0,0);
        testmap.spawnFiretruck(23,23);
        assertTrue(testmap.getEntity(0,0) instanceof Firetruck);
        assertTrue(testmap.getEntity(23,23) instanceof Firetruck);

        //testing other entities and any other values on the map
        assertNull(testmap.getEntity(22, 2));
        testmap.spawnFortress(22,2);
        assertTrue(testmap.getEntity(22,2) instanceof Fortress);

        //these test locations for the same equivilance class as with the above test, only changing the entity
        assertNull(testmap.getEntity(21, 3));
        testmap.spawnFirestation(21,3);
        assertTrue(testmap.getEntity(21,3) instanceof Firestation);
    }

    @Test
    public void testGetBlock() {
        Map testmap = new Map();

        //testing edge boundaries and Firetruck entity
        assertNull(testmap.getBlock(0, 0));
        assertNull(testmap.getBlock(23, 23));
        testmap.spawnObstacle(0,0);
        testmap.spawnObstacle(23,23);
        assertTrue(testmap.getBlock(0,0) instanceof Obstacle);
        assertTrue(testmap.getBlock(23,23) instanceof Obstacle);

        //testing other entities and any other values on the map
        assertNull(testmap.getBlock(22, 2));
        testmap.spawnBuilding(22,2);
        assertTrue(testmap.getBlock(22,2) instanceof Building);
    }

    @Test
    public void moveEntity() {

    }

    @Test
    public void attackEntity() {
    }

    @Test
    public void damageLocation() {
    }

    @Test
    public void isSpaceEmpty() {
    }

    @Test
    public void spawnFiretruck() {
    }

    @Test
    public void spawnBuilding() {
    }

    @Test
    public void spawnFortress() {
    }

    @Test
    public void spawnFirestation() {
    }

    @Test
    public void spawnObstacle() {
    }

    @Test
    public void resetTurn() {
    }
}