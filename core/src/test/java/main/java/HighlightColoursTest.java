package main.java;

import com.kroy.game.map.HighlightColours;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class HighlightColoursTest {
    //THIS CLASS SHOULD BE TESTED MANUALLY find manual test on website
    @Test
    public void testNone() {
        assertEquals("NONE", HighlightColours.NONE.name());
    }

    @Test
    public void testGrey() {
        assertEquals("GREY", HighlightColours.GREY.name());
    }

    @Test
    public void testRed() {
        assertEquals("RED", HighlightColours.RED.name());
    }

    @Test
    public void testGreen() {
        assertEquals("GREEN", HighlightColours.GREEN.name());
    }
}