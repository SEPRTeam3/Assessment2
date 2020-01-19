package main.java;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kroy.game.blocks.*;
import com.kroy.game.entities.*;
import com.kroy.game.map.Map;
import com.kroy.game.map.ShortestPathfinder;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

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

    }

    @Test
    public void moveEntity() {
        Map testmap = new Map();

        //test that moving a null entity has no effect
        testmap.moveEntity(0,0,1,1);
        assertNull(testmap.getEntity(1,1));
        assertNull(testmap.getEntity(0,0));

        //test that moving a fortress entity has no effect
        testmap.spawnFortress(2,2);
        testmap.moveEntity(2,2,3,3);
        assertTrue(testmap.getEntity(2,2) instanceof Fortress);
        assertNull(testmap.getEntity(3,3));

        //test that moving a truck that has already moved this turn has no effect
        Firetruck testtruck=testmap.spawnFiretruck(4,4);
        testtruck.setMovedThisTurn();
        testmap.moveEntity(4,4,5,5);
        assertTrue(testmap.getEntity(4,4) instanceof Firetruck);
        assertNull(testmap.getEntity(5,5));

        //test that moving truck outside truck moving range has no effect
        testmap.spawnFiretruck(6,6);
        testmap.moveEntity(6,6,23,23);
        assertTrue(testmap.getEntity(6,6) instanceof Firetruck);
        assertNull(testmap.getEntity(23,23));

        //test that moving truck inside truck moving range moves truck to x2, y2
        testmap.spawnFiretruck(8,8);
        testmap.moveEntity(8,8,9,9);
        assertNull(testmap.getEntity(8,8));
        assertTrue(testmap.getEntity(9,9) instanceof Firetruck);

        testmap.spawnFiretruck(22,22);
        testmap.moveEntity(22,22,23,23);
        assertNull(testmap.getEntity(22,22));
        assertTrue(testmap.getEntity(23,23) instanceof Firetruck);

        testmap.spawnFiretruck(0,0);
        testmap.moveEntity(0,0,1,1);
        assertNull(testmap.getEntity(0,0));
        assertTrue(testmap.getEntity(1,1) instanceof Firetruck);

    }


    @Test
    public void attackEntity() {
        Map testmap = new Map();
        Firetruck testtruck= testmap.spawnFiretruck(0,0);
        Fortress testfortress= testmap.spawnFortress(0,2);

        //test a firetruck with appropriate resources attacking a fortress
        testmap.attackEntity(0,0,0,2);
        assertEquals(testfortress.getHealth(),3);

        //test a fortress with appropriate resources attacking a firetruck
        testmap.attackEntity(0,2,0,0);
        assertEquals(testtruck.getHealth(),4);

        //test attacking  firetruck that has already attacked has no effect
        testtruck.setAttackedThisTurn();
        testmap.attackEntity(0,0,0,2);
        assertEquals(testfortress.getHealth(),3);

        //test a firetruck attacking with no water has no effect
        testtruck.resetTurn();
        testtruck.setWater(0);
        testmap.attackEntity(0,0,0,2);
        assertEquals(testfortress.getHealth(),3);
    }

    @Test
    public void damageLocation() {
        Map testmap = new Map();

        //test valid attacks
        Firetruck testtruck=testmap.spawnFiretruck(0,0);
        testmap.damageLocation(2,0,0);

        assertEquals(testtruck.getHealth(),3);

        testmap.damageLocation(3,0,0);
        assertEquals(testtruck.getHealth(),0);

    }

    @Test
    public void isSpaceEmpty() {
        Map testmap = new Map();

        assertTrue(testmap.isSpaceEmpty(0,0));

        testmap.spawnFiretruck(0,0);
        assertFalse(testmap.isSpaceEmpty(0,0));

        testmap.spawnObstacle(1,1);
        assertFalse(testmap.isSpaceEmpty(1,1));

    }

    @Test
    public void spawnFiretruck() {
        Map testmap = new Map();
        Firetruck defaultTruck=testmap.spawnFiretruck(0,0);
        assertTrue(defaultTruck instanceof Firetruck );

        Texture testTexture= new Texture(Gdx.files.internal("firetruck3.png"));
        Firetruck customFiretruck=testmap.spawnFiretruck(1,1,testTexture,2,5,5,5,5);
        assertTrue(customFiretruck instanceof Firetruck );

    }

    @Test
    public void spawnFortress() {
        Map testmap = new Map();
        testmap.spawnFortress(0,0);
        assertTrue(testmap.getEntity(0,0) instanceof Fortress );
    }

    @Test
    public void spawnFirestation() {
        Map testmap = new Map();
        testmap.spawnFirestation(0,0);
        assertTrue(testmap.getEntity(0,0) instanceof Firestation );
    }

    @Test
    public void spawnObstacle() {
        Map testmap = new Map();
        testmap.spawnObstacle(0,0);
        assertTrue(testmap.getBlock(0,0) instanceof Obstacle );
    }

    @Test
    public void resetTurn() {
        Map testmap = new Map();

        Firetruck testtruck=testmap.spawnFiretruck(0,0);
        testmap.resetTurn();
        assertFalse(testtruck.hasMovedThisTurn());
        assertFalse(testtruck.hasAttackedThisTurn());    }

    @Test
    public void testGetFirestationLocation(){
        Map testmap = new Map();

        Firestation teststation=testmap.spawnFirestation(0,0);
        assertEquals(testmap.getFirestationLocation(), new Vector2(0,0));
    }
}