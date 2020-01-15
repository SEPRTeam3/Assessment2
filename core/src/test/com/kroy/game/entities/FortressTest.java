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
        testfortress.setHealth(4);
        assertFalse(testfortress.takeDamage(-2));
        testfortress.setHealth(4);
        assertFalse(testfortress.takeDamage(3));
    }

    @Test
    void getAttackStrength() {
        Fortress testfortress = new Fortress();
        assertEquals(1,testfortress.getAttackStrength());
    }

    @Test
    void getAttackRadius() {
        Fortress testfortress = new Fortress();
        assertEquals(2,testfortress.getAttackRadius());
    }

    @Test
    void getTargetsPerTurn() {
        Fortress testfortress = new Fortress();
        assertEquals(1,testfortress.getTargetsPerTurn());
    }

    @Test
    void levelUp() {
        Fortress testfortress = new Fortress();
        testfortress.levelUp();
        assertEquals(3,testfortress.getAttackRadius());
        assertEquals(2,testfortress.getAttackStrength());
    }

    @Test
    void getHealth() {
        Fortress testfortress = new Fortress();
        assertEquals(4,testfortress.getHealth());
    }

    @Test
    void setHealth() {
        Fortress testfortress = new Fortress();
        testfortress.setHealth(2);
        assertEquals(2,testfortress.getHealth());

    }
}