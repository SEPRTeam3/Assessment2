package main.java;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.entities.DestroyedEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class DestroyedEntityTest {

    @Test
    public void TestDestroyedEntity(){
        DestroyedEntity testentity = new DestroyedEntity();
        assertEquals(Texture(Gdx.files.internal("Destroyed.png")),testentity.getTexture());
    }
}