package com.kroy.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kroy.game.MyGdxGame;

import kroy.game.entities.Entity.entityID;
import kroy.game.map.Map;

public class GameScreen implements Screen
{
	
	final MyGdxGame game;
	Map map;
	Vector2 selected;
	
	public GameScreen(final MyGdxGame game)
	{
		this.game = game;
		this.map = new Map();
		this.selected = null;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta)
	{
		// Render
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1, 0, 1, 1);
		game.batch.begin();
		for (int i = 0; i < Map.WIDTH; i++)
		{
			for (int j = 0; j < Map.HEIGHT; j++)
			{
				// Render background
				game.batch.draw(map.getBackground(i, j).getTexture(), i*32, j*32, 32, 32);
				// Render entities
				if (map.getEntity(i, j) != null)
				{
					game.batch.draw(map.getEntity(i, j).getTexture(), i*32, j*32, 32, 32);
				}
			}
		}
		game.batch.end();
		
		// Get player input
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
		{
			int tileX = (int)Math.floor(Gdx.input.getX()/32);
			int tileY = Map.HEIGHT - (int)Math.floor(Gdx.input.getY()/32) - 1;
			System.out.println("x: " + tileX + " y: " + tileY);
			if (map.getEntity(tileX, tileY) != null && map.getEntity(tileX, tileY).id == entityID.FIRETRUCK)
			{
				selected = new Vector2(tileX, tileY);
				System.out.println("Selected: " + selected);
			}
			else if (map.getEntity(tileX, tileY) == null && selected != null)
			{
				map.moveEntity((int)selected.x, (int)selected.y, tileX, tileY);
			}
			//map.debugMakeFiretruck(tileX, tileY);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
