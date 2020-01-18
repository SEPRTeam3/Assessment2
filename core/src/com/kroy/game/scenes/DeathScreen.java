package com.kroy.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.game.MyGdxGame;

public class DeathScreen implements Screen
{
    /**
     * Screen for player death
     */
    final MyGdxGame game;
    private Viewport viewport;

    public DeathScreen(final MyGdxGame game)
    {
        this.game = game;
        viewport = new FitViewport(10, 10);
        Gdx.graphics.setWindowedMode(512, 512);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "You are ded\n   No high score for you \n \n Space to restart", 128, 255);
        game.batch.end();


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            System.out.println("Going to Home");
            game.setScreen(new TitleScreen(game));
        }
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
