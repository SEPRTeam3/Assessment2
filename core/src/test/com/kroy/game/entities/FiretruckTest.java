package com.kroy.game.entities;

import static org.junit.jupiter.api.Assertions.*;
import com.kroy.game.entities.Entity;
import com.kroy.game.entities.Firetruck;

import static org.junit.jupiter.api.Assertions.*;




public class FiretruckTest {


    @org.junit.jupiter.api.Test
    public void testFiretruck() {
//        assertTrue(testSetMovedThisTurn());
//        assertTrue(testHasMovedThisTurn());
//        assertTrue(testResetTurn());
//        assertTrue(testGetMovementDistance());
//        assertTrue(testSetMovedThisTurn());
//        assertTrue(testGetAttackStrength());
    }

    @org.junit.jupiter.api.Test
    public void testHasMovedThisTurn() {

        Firetruck testtruck = new Firetruck();
        testtruck.setMovedThisTurn();
        assertTrue(testtruck.hasMovedThisTurn());
    }

    @org.junit.jupiter.api.Test
    public void testSetMovedThisTurn() {
        Firetruck testtruck = new Firetruck();
        testtruck.setMovedThisTurn();
        assertTrue(testtruck.hasMovedThisTurn());
    }

    @org.junit.jupiter.api.Test
    public void testResetTurn() {
        Firetruck testtruck = new Firetruck();
        testtruck.resetTurn();
        assertFalse(testtruck.hasMovedThisTurn());

    }

    @org.junit.jupiter.api.Test
    public void testGetMovementDistance() {
        Firetruck testtruck = new Firetruck();
        assertEquals(5,testtruck.getMovementDistance());
        assertNotEquals(4,testtruck.getMovementDistance());
        assertNotEquals(true, testtruck.getMovementDistance());


    }

    @org.junit.jupiter.api.Test
    public void testIsAttackPossible() {
        Firetruck testtruck = new Firetruck();
        assertTrue(testtruck.isAttackPossible(5, 10, 5, 10));
        assertTrue(testtruck.isAttackPossible(10, 5, 10, 5));
        assertTrue(testtruck.isAttackPossible(10, 3, 10, 10));
        assertTrue(testtruck.isAttackPossible(4, 3, 10, 3));
        assertTrue(testtruck.isAttackPossible(-4,3,-4,3));
        assertTrue(testtruck.isAttackPossible(-4,-3,-4,-3));
        assertTrue(testtruck.isAttackPossible(-4,-3,-10,-3));

        assertTrue(testtruck.isAttackPossible(0, 0, 0, 0));
        assertTrue(testtruck.isAttackPossible(0, 1, 0, 2));
        assertTrue(testtruck.isAttackPossible(1, 0, 2, 0));

        assertFalse(testtruck.isAttackPossible(5,3 ,2 , 1));
        assertFalse(testtruck.isAttackPossible(3, 4, -3, -4));
        assertFalse(testtruck.isAttackPossible(-1, -2, -3, -4));
        assertFalse(testtruck.isAttackPossible(1, 0, 0, 1));

    }

    @org.junit.jupiter.api.Test
    public void testIsMovementPossible() {
        Firetruck testtruck = new Firetruck();

    }

    @org.junit.jupiter.api.Test
    public void testTakeDamage() {
        Firetruck testtruck = new Firetruck();
        assertTrue(testtruck.takeDamage(5));
        assertTrue(testtruck.takeDamage(10));
        assertFalse(testtruck.takeDamage(0));
        assertFalse(testtruck.takeDamage(-2));
        assertFalse(testtruck.takeDamage(3));


    }

    @org.junit.jupiter.api.Test
    public void testGetAttackStrength() {
        Firetruck testtruck = new Firetruck();
        assertEquals(1,testtruck.getAttackStrength());
    }

}
