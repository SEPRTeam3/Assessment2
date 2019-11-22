package com.kroy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kroy.game.scenes.GameScreen;
import com.kroy.game.scenes.ScoreScreen;
import com.kroy.game.scenes.TitleScreen;;

public class MyGdxGame extends Game 
{	
	public SpriteBatch batch;
	public BitmapFont font;
	
	public enum eScreen
	{
		TITLE,
		GAME,
		SCORE
	}
	
	@Override
	public void create () 
	{
		// Register all resources that are being used
		batch = new SpriteBatch();
		font = new BitmapFont();
		// Initialise screens
		this.setScreen(new TitleScreen(this));
	}

	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render all the things that are on screen
		super.render();

	}
	
	@Override
	public void dispose () 
	{
		// Unregister all resources that have been used
		batch.dispose();
		font.dispose();
	}
}
