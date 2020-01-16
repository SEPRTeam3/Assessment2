package main.java;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.kroy.game.entities.Firestation;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class FirestationTest {

    @Test
    public void takeDamage() {
        Firestation teststation = new Firestation();
        assertEquals(false,teststation.takeDamage(5));
    }
}