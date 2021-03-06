package main.java;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.kroy.game.entities.Firetruck;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class FiretruckTest {

    @Test
    public void testFiretuck(){
        Firetruck testDefaultFiretruck = new Firetruck();
        assertTrue(testDefaultFiretruck instanceof Firetruck);

        Texture testTexture = new Texture(Gdx.files.internal("firetruck3.png"));
        Firetruck testCustomFiretruck = new Firetruck(testTexture,2,5,2,3,1);
        assertTrue(testCustomFiretruck instanceof Firetruck);
    }

    @Test
    public void testSetHealth(){
        Firetruck testtruck = new Firetruck();
        testtruck.setHealth(4);
        assertEquals(4,testtruck.getHealth());

    }

    @Test
    public void testGetWater(){
        Firetruck testtruck = new Firetruck();
        assertEquals(2,testtruck.getWater());
    }

    @Test
    public void testSetWater(){
        Firetruck testtruck = new Firetruck();
        testtruck.setWater(1);
        assertEquals(1,testtruck.getWater());
    }

    @Test
    public void testUseWater(){
        Firetruck testtruck = new Firetruck();
        assertTrue(testtruck.useWater());
        testtruck.setWater(0);
        assertFalse(testtruck.useWater());
    }

    @Test
    public void testGetHealth() {
        Firetruck testtruck = new Firetruck();
        assertEquals(5,testtruck.getHealth());
    }

    @Test
    public void getAttackDistance(){
        Firetruck testtruck = new Firetruck();
        assertEquals(5,testtruck.getAttackDistance());

        Texture testTexture = new Texture(Gdx.files.internal("firetruck3.png"));
        Firetruck testCustomFiretruck = new Firetruck(testTexture,2,5,2,3,1);
        assertEquals(3,testCustomFiretruck.getAttackDistance());

    }




    @Test
    public void testHasMovedThisTurn() {
        /*
        Test should pass if the method hasMovedThis returns False as the default turn setting
        then returns True after setting the turn to True using setMovedThisTurn

         */
        Firetruck testtruck = new Firetruck();
        assertFalse(testtruck.hasAttackedThisTurn());
        testtruck.setMovedThisTurn();
        assertTrue(testtruck.hasMovedThisTurn());
    }

    @Test
    public void testSetMovedThisTurn() {
        /*
        Test should pass if the method hasMovedThisTurn returns True
        after setting the turn to True using setMovedThisTurn
         */
        Firetruck testtruck = new Firetruck();
        assertFalse(testtruck.hasMovedThisTurn());
        testtruck.setMovedThisTurn();
        assertTrue(testtruck.hasMovedThisTurn());
    }

    @Test
    public void testHasAttackedThisTurn() {
        /*
        Test should return the attack turn as the default value False, so it's the players go first to attack
         */
        Firetruck testtruck = new Firetruck();
        assertFalse(testtruck.hasAttackedThisTurn());
        /*
        Test should return True after player has had a go
         */
        testtruck.setAttackedThisTurn();
        assertTrue(testtruck.hasAttackedThisTurn());
    }

    @Test
    public void testSetAttackedThisTurn(){
        /*
        Test should pass if the method hasAttackedThisTurn returns True
        after setting the attack turn to True using setAttackedThisTurn
         */
        Firetruck testtruck = new Firetruck();
        testtruck.setAttackedThisTurn();
        assertTrue(testtruck.hasAttackedThisTurn());
    }


    @Test
    public void testResetTurn() {

        /*
        Test should pass if the attack and current turn are set to False using the ResetTurn method
         */
        Firetruck testtruck = new Firetruck();
        testtruck.resetTurn();
        assertFalse(testtruck.hasMovedThisTurn());
        assertFalse(testtruck.hasAttackedThisTurn());

        Firetruck testtruck1 = new Firetruck();
        testtruck1.setAttackedThisTurn();
        testtruck1.setMovedThisTurn();
        testtruck1.resetTurn();
        assertFalse(testtruck1.hasMovedThisTurn());
        assertFalse(testtruck1.hasAttackedThisTurn());

    }

    @Test
    public void testGetMovementDistance() {
        /*
        Testing parameters are values 5,4 and a boolean value True to ensure
        method doesn't return unexpected values
        Passes if the method returns the movement distance as 5
         */
        Firetruck testtruck = new Firetruck();
        assertEquals(5,testtruck.getMovementDistance());
        assertNotEquals(4,testtruck.getMovementDistance());
        assertNotEquals(true, testtruck.getMovementDistance());

        Texture testtexture=new Texture(Gdx.files.internal("firetruck3.png"));
        Firetruck testtruck1 = new Firetruck(testtexture,3,5,1,2,3);
        assertEquals(3,testtruck1.getMovementDistance());
        assertNotEquals(4,testtruck1.getMovementDistance());
        assertNotEquals(true, testtruck1.getMovementDistance());

    }

    @Test
    public void testRestock(){
        Firetruck testtruck = new Firetruck();
        testtruck.setHealth(0);
        testtruck.setWater(0);
        testtruck.restock();
        assertEquals(5,testtruck.getHealth());
        assertEquals(2,testtruck.getWater());
    }

    @Test
    public void testIsAttackPossible() {
        Firetruck testtruck = new Firetruck();
        /*
        Method should return True if both X and Y values are the same, if only X is the same or only Y is the same
         */
        assertTrue(testtruck.isAttackPossible(5, 10, 5, 10));
        assertTrue(testtruck.isAttackPossible(10, 5, 10, 5));
        assertFalse(testtruck.isAttackPossible(10, 3, 10, 10));
        assertFalse(testtruck.isAttackPossible(4, 3, 10, 3));
        /*
        Method should return True if negative values are placed
         */
        assertTrue(testtruck.isAttackPossible(-4,3,-4,3));
        assertTrue(testtruck.isAttackPossible(-4,-3,-4,-3));
        assertFalse(testtruck.isAttackPossible(-4,-3,-10,-3));
        /*
        Method should return True on values of 0
         */
        assertTrue(testtruck.isAttackPossible(0, 0, 0, 0));
        assertTrue(testtruck.isAttackPossible(0, 1, 0, 2));
        assertTrue(testtruck.isAttackPossible(1, 0, 2, 0));
        /*
        Method should return false on values that don't correspond to relative X and Y values
        regardless of whether they're positive, negative or 0
         */
        assertFalse(testtruck.isAttackPossible(5,3 ,2 , 1));
        assertFalse(testtruck.isAttackPossible(3, 4, -3, -4));
        assertFalse(testtruck.isAttackPossible(-1, -2, -3, -4));
        assertFalse(testtruck.isAttackPossible(1, 0, 0, 1));

    }


    @Test
    public void testTakeDamage() {
        /*
        Method should return True on postive parameters greater than 5
         */
        Firetruck testtruck = new Firetruck();
        assertTrue(testtruck.takeDamage(5));
        testtruck.setHealth(4);
        assertTrue(testtruck.takeDamage(10));
        testtruck.setHealth(4);

          /*
        Method should return False on any value below 1
         */
        assertFalse(testtruck.takeDamage(0));
        testtruck.setHealth(4);
        assertFalse(testtruck.takeDamage(-2));



    }

    @Test
    public void testGetAttackStrength() {
        /*
        Method should return 1 as the default attack strength of the firetruck
         */
        Firetruck testtruck = new Firetruck();
        assertEquals(1,testtruck.getAttackStrength());
    }

}
