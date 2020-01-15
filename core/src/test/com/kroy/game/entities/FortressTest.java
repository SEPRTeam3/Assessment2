package com.kroy.game.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FortressTest {


    @Test
    void takeDamage() {
        Fortress testfortress = new Fortress();
        assertTrue(testfortress.takeDamage(5));
        testfortress.setHealth(4);
        assertTrue(testfortress.takeDamage(10));
        testfortress.setHealth(4);
        assertFalse(testfortress.takeDamage(0));
        //assertFalse(testfortress.takeDamage(-2));
        //assertFalse(testfortress.takeDamage(3));
    }

    @Test
    void getAttackStrength() {
    }

    @Test
    void getAttackRadius() {
    }

    @Test
    void getTargetsPerTurn() {
    }

    @Test
    void levelUp() {
    }
}