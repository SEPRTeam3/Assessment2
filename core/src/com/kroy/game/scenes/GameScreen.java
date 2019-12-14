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
import com.kroy.game.entities.Firetruck;
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
		tileMap = new TmxMapLoader().load("MapTestF.tmx");
		mapDrawer = new MapDrawer(game, map, tileMap);
		
		this.map.debugMakeBuilding(23, 23);
		this.map.debugMakeFiretruck(1, 1);
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

				// Scale click to isometric grid
				// TODO: Bad unmodular code! Redo without arbitrary constants!
				Vector2 clicked;
				// Move origin to top of iso diamond
				if (Gdx.graphics.getHeight() > Gdx.graphics.getWidth())		// Size of grid is bounded by shortest axis
				{
					clicked = new Vector2(
										  Gdx.input.getX() - mapDrawer.getMapScreenOrigin().x,
							              Gdx.input.getY() - mapDrawer.getMapScreenOrigin().y
										 );
				}
				else
				{
					clicked = new Vector2(Gdx.input.getX() - Gdx.graphics.getWidth() / 2, Gdx.input.getY() - Gdx.graphics.getHeight() * 0.025f);
				}
				clicked = clicked.rotate(-45f);	// Rotate

				clicked.x = (float) Math.floor(clicked.x);
				clicked.y = (float) Math.floor(clicked.y);
				if (Gdx.graphics.getHeight() > Gdx.graphics.getWidth())
				{
					int extra = Gdx.graphics.getHeight() - Gdx.graphics.getWidth();
					int distanceIn = Gdx.input.getY() - extra / 2;
					float ratio =  (float) distanceIn / (float) Gdx.graphics.getWidth();
					//System.out.println("Ratio down screen: " + ratio);
				}
				else
				{
					float ratio =  (float) Gdx.input.getY() / (float) Gdx.graphics.getWidth();
					//System.out.println("Ratio down screen: " + ratio);
				}
				// Scale to grid
				clicked.scl(1f/(mapDrawer.getScreenScalingCoefficient()));	// Is relative to the scaling coefficient
				clicked.scl(24f/328f);	// Divide max (328 for some reason) by 24 to get appropriately sized tiles
				clicked.x = (float) Math.floor(clicked.x);	// Floor values
				clicked.y = (float) Math.floor(clicked.y);

				System.out.println("Clicked.x: " + clicked.x + " Clicked.y: " + clicked.y);
				System.out.println("map origin is " + mapDrawer.getMapScreenOrigin().x + " " + mapDrawer.getMapScreenOrigin().y);
				if (clicked.x >= 0f && clicked.x < 24f && clicked.y >= 0f && clicked.y < 24f)
				{
					// Clicked inside map
					int tileX = (int) clicked.x;
					int tileY = (int) clicked.y;
					System.out.println("clicked at (" + tileX + ", " + tileY + ")");
					if (map.getEntity(tileX, tileY) != null && map.getEntity(tileX, tileY).id == entityID.FIRETRUCK)
					{
						selected = new Vector2(tileX, tileY);
						System.out.println("Selected: " + selected);


					}
					else if (map.getEntity(tileX, tileY) == null && selected != null)
					{
						map.moveEntity((int)selected.x, (int)selected.y, tileX, tileY);
						selected = null;
					}
					else
					{
						selected = null;
					}
				}
				else
				{
					// Clicked outside of map
					selected = null;
				}
			}
			// Draw movement radius around fire engine
			if (selected != null && map.getEntity((int)selected.x, (int)selected.y) != null)
			{
				if (map.getEntity((int)selected.x, (int)selected.y).id == entityID.FIRETRUCK)
				{
					Firetruck f = (Firetruck) map.getEntity((int)selected.x, (int)selected.y);
					boolean[][] b = map.getShortestPaths((int)selected.x, (int)selected.y, f.getMovementDistance());
					mapDrawer.highlightBlocks(b);
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
