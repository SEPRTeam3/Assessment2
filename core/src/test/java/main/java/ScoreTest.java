package main.java;

import main.java.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import com.kroy.game.score.Score;

@RunWith(GdxTestRunner.class)
public class ScoreTest {

    @Test
    public void getName() {
        Score testScore = new Score("TestName", 10, 1);
        assertEquals("TestName", testScore.getName());
    }

    @Test
    public void getValue() {
        Score testScore = new Score("TestName", 10, 1);
        assertEquals(10, testScore.getValue());
    }

    @Test
    public void getValuestr() {
        Score testScore = new Score("TestName", 10, 1);
        assertEquals("10", testScore.getValuestr());
    }

    @Test
    public void getIDint() {
        Score testScore = new Score("TestName", 10, 1);
        assertEquals(1, testScore.getIDint());
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
        assertEquals(15, testScore.getValue());
    }

    @Test
    public void multiplyScore() {
        Score testScore = new Score("TestName", 10, 1);
        testScore.multiplyScore(5);
        assertEquals(50, testScore.getValue());
    }

    @Test
    public void subtractScore() {
        Score testScore = new Score("TestName", 10, 1);
        testScore.subtractScore(5);
        assertEquals(5, testScore.getValue());
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
        assertEquals(5, testScore.getIDint());
    }
}