package com.kroy.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kroy.game.MyGdxGame;
import com.kroy.game.entities.Entity.entityID;
import com.kroy.game.map.Map;
import com.kroy.game.map.MapDrawer;

public class GameScreen implements Screen
{
	
	public enum turnStates
	{
		PLAYER,
		POST_PLAYER,
		ET,
		POST_ET
	}
	
	final MyGdxGame game;
	
	private Map map;
	private TiledMap tileMap;
	private MapDrawer mapDrawer;
	
	Vector2 selected;
	turnStates turnState;

	
	public GameScreen(final MyGdxGame game)
	{
		
		this.game = game;
		selected = null;
		turnState = turnStates.PLAYER;
		
		map = new Map();
		tileMap = new TmxMapLoader().load("test2.tmx");
		mapDrawer = new MapDrawer(game, map, tileMap);
		
		this.map.debugMakeBuilding(5, 5);
		System.out.println("Tile width: " + mapDrawer.getTilePixelWidth());
	}

	@Override
	public void show() 
	{
	}

	@Override
	public void render(float delta)
	{
		// Render
		mapDrawer.render();

		// Get player input
		switch(turnState)
		{
		case PLAYER:
			
			// Left click handling
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
			{

				Vector2 clicked = new Vector2(Gdx.input.getX()-Gdx.graphics.getWidth()/2, Gdx.input.getY()-Gdx.graphics.getHeight()/2);
				clicked = clicked.rotate(45f);
				clicked = new Vector2(clicked.x / mapDrawer.getTilePixelWidth(), clicked.y / mapDrawer.getTilePixelWidth());
				clicked.scl(1.79104478f);
				clicked.x = (float) Math.floor(clicked.x);
				clicked.y = (float) Math.floor(clicked.y);
				System.out.println("x: " + clicked.x + " y: " + clicked.y);
				System.out.println("x: " + Gdx.input.getX() + " y: " + Gdx.input.getY());

				/*
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
				 */
			}
			
			// Space key handling
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
			{
				this.turnState = turnStates.POST_PLAYER;
			}
			break;
			
		case POST_PLAYER:
			this.turnState = turnStates.ET;
			break;
			
		case ET:
			System.out.println("ETs are taking their turn");
			this.turnState = turnStates.POST_ET;
			break;
			
		case POST_ET:
			this.turnState = turnStates.PLAYER;
			map.resetTurn();
			break;
			
		}
		
		
	}

	@Override
	public void resize(int width, int height)
	{
		System.out.println("Tile width: " + mapDrawer.getTilePixelWidth());
		mapDrawer.resize(width, height);
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
		this.tileMap.dispose();
	}
}
