package com.kroy.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kroy.game.MyGdxGame;
import com.kroy.game.entities.Entity.entityID;
import com.kroy.game.entities.Firetruck;
import com.kroy.game.map.HighlightColours;
import com.kroy.game.map.Map;
import com.kroy.game.map.MapDrawer;

import java.util.Iterator;

public class GameScreen implements Screen
{
	
	public enum turnStates
	{
		PLAYER,
		POST_PLAYER,
		ET,
		POST_ET
	}

	public enum selectedMode
	{
		NONE,
		MOVE,
		ATTACK
	}
	
	final MyGdxGame game;
	
	private Map map;
	private TiledMap tileMap;
	private MapDrawer mapDrawer;
	
	Vector2 selected = null;
	turnStates turnState;
	selectedMode selectAction = selectedMode.NONE;

	
	public GameScreen(final MyGdxGame game)
	{
		
		this.game = game;
		selected = null;
		turnState = turnStates.PLAYER;
		
		map = new Map();
		tileMap = new TmxMapLoader().load("MapTestF.tmx");
		MapLayer objLayer = tileMap.getLayers().get("Trucks");

		// Gets truck locations from tilemap
		TiledMapTileLayer tileLayer = (TiledMapTileLayer) tileMap.getLayers().get("TrucksTiles");
		for (int x = 0; x < tileLayer.getWidth(); x++)
		{
			for (int y = 0; y < tileLayer.getHeight(); y++)
			{
				TiledMapTileLayer.Cell cell =  tileLayer.getCell(x, y);
				if (cell != null)
				{
					map.debugMakeFiretruck(x, tileLayer.getHeight()-y);
				}
			}
		}
		tileLayer = (TiledMapTileLayer) tileMap.getLayers().get("Obstacles");
		for (int x = 0; x < tileLayer.getWidth(); x++)
		{
			for (int y = 0; y < tileLayer.getHeight(); y++)
			{
				System.out.println("(" + x + ", " + (tileLayer.getHeight()-y-1) + ")");
				TiledMapTileLayer.Cell cell =  tileLayer.getCell(x, y);
				if (cell != null)
				{
					map.debugMakeBuilding(x, tileLayer.getHeight()-y-1);
				}
			}
		}
		mapDrawer = new MapDrawer(game, map, tileMap);
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

				Vector2 tileClicked = mapDrawer.toMapSpace(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

				if (tileClicked != null)
				{
					// Clicked inside map
					int tileX = (int) tileClicked.x;
					int tileY = (int) tileClicked.y;
					if (map.getEntity(tileX, tileY) != null && map.getEntity(tileX, tileY).id == entityID.FIRETRUCK)
					{
						// Player clicked firetruck with nothing selected, select firetruck
						selected = new Vector2(tileX, tileY);
					}
					else if (map.getEntity(tileX, tileY) == null && selected != null && selectAction == selectedMode.MOVE)
					{
						// Player clicked an empty space with move selected, so move to that area
						map.moveEntity((int)selected.x, (int)selected.y, tileX, tileY);
						selectAction = selectedMode.NONE;
						selected = null;
					}
					else if (selected != null && selectAction == selectedMode.ATTACK)
					{
						// Player clicked with attack selected, so attack that area
						map.attackEntity((int)selected.x, (int)selected.y, tileX, tileY);
						selected = null;
					}
					else
					{
						selectAction = selectedMode.NONE;
						selected = null;
					}
				}
				else
				{
					// Clicked outside of map
					selected = null;
				}
			}

			// Handling for M key for move action
			if (Gdx.input.isKeyJustPressed(Input.Keys.M))
			{
				if
				(
					selected != null
					&&
					map.getEntity((int)selected.x, (int)selected.y) != null
					&&
					map.getEntity((int)selected.x, (int)selected.y).id == entityID.FIRETRUCK
				)
				{
					selectAction = selectedMode.MOVE;
				}
			}

			// Handling for N key for attack action
			if (Gdx.input.isKeyJustPressed(Input.Keys.N))
			{
				if
				(
					selected != null
							&&
					map.getEntity((int)selected.x, (int)selected.y) != null
					&&
					map.getEntity((int)selected.x, (int)selected.y).id == entityID.FIRETRUCK
				)
				{
					selectAction = selectedMode.ATTACK;
				}
			}

			// Draw highlights around fire engine
			if (selected != null && map.getEntity((int)selected.x, (int)selected.y) != null)
			{
				if (selectAction == selectedMode.MOVE && map.getEntity((int)selected.x, (int)selected.y).id == entityID.FIRETRUCK)
				{
					Firetruck f = (Firetruck) map.getEntity((int)selected.x, (int)selected.y);
					boolean[][] b = new boolean[map.HEIGHT][map.WIDTH];
					for (int i = 0; i < map.HEIGHT; i++) {
						for (int j = 0; j < map.WIDTH; j++) {
							b[i][j] = f.isMovementPossible((int) selected.x, (int) selected.y, j, i);
						}
					}
					mapDrawer.highlightBlocks(b, HighlightColours.GREEN);
				}
				else if (selectAction == selectedMode.ATTACK && map.getEntity((int)selected.x, (int)selected.y).id == entityID.FIRETRUCK)
				{
					Firetruck f = (Firetruck) map.getEntity((int)selected.x, (int)selected.y);
					boolean[][] b = new boolean[map.HEIGHT][map.WIDTH];
					for (int i = 0; i < map.HEIGHT; i++) {
						for (int j = 0; j < map.WIDTH; j++) {
							b[i][j] = f.isAttackPossible((int) selected.x, (int) selected.y, j, i);
						}
					}
					mapDrawer.highlightBlocks(b, HighlightColours.RED);
				}
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
