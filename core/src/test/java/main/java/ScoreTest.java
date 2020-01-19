package main.java;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.entities.Firetruck;
import main.java.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import com.kroy.game.score.Score;

@RunWith(GdxTestRunner.class)
public class ScoreTest {
    @Test
    public void testScore() {
        Score testDefaultScore = new Score();
        assertTrue(testDefaultScore instanceof Score);

        Score testCustomScore = new Score("testName",10,1);
        assertTrue(testCustomScore instanceof Score);
    }

    @Test
    public void getName() {
        Score testScore = new Score("TestName", 10, 1);
        assertEquals("TestName", testScore.getName());
    }

    @Test
    public void getValue() {
        Score testScore = new Score("TestName", 10, 1);
        assertEquals(Integer.valueOf(10), testScore.getValue());
    }

    @Test
    public void getValuestr() {
        Score testScore = new Score("TestName", 10, 1);
        assertEquals("10", testScore.getValuestr());
    }

    @Test
    public void getIDint() {
        Score testScore = new Score("TestName", 10, 1);
        assertEquals(testScore.getIDint(), Integer.valueOf(1));
    }

    @Test
    public void getIDstr() {
        Score testScore = new Score("TestName", 10, 1);
        assertEquals("1", testScore.getIDstr());
    }

    @Test
    public void addScore() {
        Score testScore = new Score("TestName", 10, 1);
        testScore.addScore(5);
        assertEquals(Integer.valueOf(15), testScore.getValue());
    }

    @Test
    public void multiplyScore() {
        Score testScore = new Score("TestName", 10, 1);
        testScore.multiplyScore(5);
        assertEquals(Integer.valueOf(50), testScore.getValue());
    }

    @Test
    public void subtractScore() {
        Score testScore = new Score("TestName", 10, 1);
        testScore.subtractScore(5);
        assertEquals(Integer.valueOf(5), testScore.getValue());
    }

    @Test
    public void setName() {
        Score testScore = new Score("TestName", 10, 1);
        testScore.setName("NewName");
        assertEquals("NewName", testScore.getName());
    }

    @Test
    public void setID() {
        Score testScore = new Score("TestName", 10, 1);
        testScore.setID(5);
        assertEquals(Integer.valueOf(5), testScore.getIDint());
    }
}