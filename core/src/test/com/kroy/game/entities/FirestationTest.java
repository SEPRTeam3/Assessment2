package com.kroy.game.entities;

import org.junit.jupiter.api.Test;

/*import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;*/
import static org.junit.jupiter.api.Assertions.*;

class FirestationTest {

    @Test
    void takeDamage() {
        Firestation teststation = new Firestation();
        assertEquals(false,teststation.takeDamage(5));
    }
}