package com.kroy.game.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.kroy.game.ETMastermind;
import com.kroy.game.MyGdxGame;
import com.kroy.game.entities.Firestation;
import com.kroy.game.entities.Firetruck;
import com.kroy.game.entities.Fortress;
import com.kroy.game.map.HighlightColours;
import com.kroy.game.map.Map;
import com.kroy.game.map.MapDrawer;
import com.kroy.game.map.MapParser;
import com.kroy.game.ui.GameHud;

public class GameScreen implements Screen
{
	/**
	 * Screen for main game
	 */

	// Who's turn it is
	public enum turnStates
	{
		PLAYER,
		POST_PLAYER,
		ET,
		POST_ET
	}

	// The action selected by the player
	public enum selectedMode
	{
		NONE,
		MOVE,
		ATTACK
	}
	
	final MyGdxGame game;

	// Technical objects
	private Map map;
	private TiledMap tileMap;
	private MapDrawer mapDrawer;
	private GameHud hud;
	private ETMastermind enemyAI;

	// Variables
	private int turnNumber = 0;
	Vector2 selected = null;
	turnStates turnState;
	selectedMode selectAction = selectedMode.NONE;


	public GameScreen(final MyGdxGame game)
	{
		// Initialise technical objects
		this.game = game;
		selected = null;
 		turnState = turnStates.PLAYER;
		hud = new GameHud(game.batch, game.skin);
		hud.createFireTruckUI(map, game.skin);

		// Initialise map
		map = new Map();
		tileMap = new TmxMapLoader().load("MapTestF.tmx");
		MapParser parser = new MapParser();
		parser.addAll(map, tileMap);
		mapDrawer = new MapDrawer(game, map, tileMap);

		//Initialise enemyAI
		enemyAI = new ETMastermind(this.map, this.mapDrawer);
	}

	@Override
	public void show() 
	{
	}

	@Override
	public void render(float delta)
	{
		// Render map
		mapDrawer.viewport.apply();
		mapDrawer.render();

		// Render HUD
		hud.stage.getViewport().apply();
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();

		// Get player input
		switch(turnState)
		{
		case PLAYER:
			// Update HUD
			if(!hud.menuOpen) 
			{
				hud.setDialog("Player:", "\nPlayer Turn", 4.0f);
			}

			// Left click handling
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
			{

				Vector2 tileClicked = mapDrawer.toMapSpace(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

				if (tileClicked != null)
				{
					// Clicked inside map
					int tileX = (int) tileClicked.x;
					int tileY = (int) tileClicked.y;
					if (map.getEntity(tileX, tileY) != null && map.getEntity(tileX, tileY) instanceof Firetruck)

					{
						// Player clicked firetruck with nothing selected, select firetruck
						selected = new Vector2(tileX, tileY);
						hud.createGameTable();
						if(!hud.moveClicked || !hud.attackClicked){
							selectAction = selectedMode.NONE;
						}
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
						selectAction = selectedMode.NONE;
						selected = null;
					}
					else
					{
						// Player clicked on nothing
						if(!hud.menuOpen) {
							selectAction = selectedMode.NONE;
							selected = null;
						}
					}
				}
				else
				{
					// Clicked outside of map
					if(!hud.clickInTable()) {
						hud.clickOffInGameTable();
						System.out.print("UI??");
						selected = null;
					}

				}
			}

			// Player triggers movement
			if (Gdx.input.isKeyJustPressed(Input.Keys.M) || hud.moveClicked)
			{
				hud.moveClicked = false;
				if
				(
					selected != null
					&&
					map.getEntity((int)selected.x, (int)selected.y) != null
					&&
					map.getEntity((int)selected.x, (int)selected.y) instanceof Firetruck
				)
				{
					selectAction = selectedMode.MOVE;
				}
			}

			// Player triggers attack
			if (Gdx.input.isKeyJustPressed(Input.Keys.N) || hud.attackClicked)
			{
				hud.attackClicked = false;
				if
				(
					selected != null
							&&
					map.getEntity((int)selected.x, (int)selected.y) != null
					&&
					map.getEntity((int)selected.x, (int)selected.y) instanceof Firetruck
				)
				{
					selectAction = selectedMode.ATTACK;
				}
			}

			// Player triggers restock
			if (Gdx.input.isKeyJustPressed(Input.Keys.B) || hud.refillClicked)
			{
				hud.refillClicked = false;
				if
				(
					selected != null
					&&
					map.getEntity((int) selected.x, (int) selected.y) != null
					&&
					map.getEntity((int) selected.x, (int) selected.y) instanceof Firetruck
				)
				{
					Firetruck f = (Firetruck) map.getEntity((int) selected.x, (int) selected.y);
					Vector2 firestationLocation = map.getFirestationLocation();
					if (firestationLocation.sub(selected).len() <= Firestation.restockingRadius)
					{
						f.restock();
						System.out.println("The firetruck has been restocked");
					}
					else
					{
						System.out.println("The firetruck wasn't close enough to the station to restock");
					}
				}
			}

			// Draw highlights for movement or attack patterns
			if (selected != null && map.getEntity((int)selected.x, (int)selected.y) != null)
			{
				if (selectAction == selectedMode.MOVE && map.getEntity((int)selected.x, (int)selected.y) instanceof Firetruck)
				{
					// Movement pattern
					Firetruck f = (Firetruck) map.getEntity((int)selected.x, (int)selected.y);
					boolean[][] b = new boolean[map.HEIGHT][map.WIDTH];
					int[][] distanceMatrix = map.pathfinder.getDistanceMatrix((int)selected.x, (int)selected.y);
					for (int i = 0; i < map.HEIGHT; i++) {
						for (int j = 0; j < map.WIDTH; j++) {
							b[i][j] = (distanceMatrix[j][i] <= f.getMovementDistance()) && distanceMatrix[j][i] != -1;
						}
					}
					mapDrawer.highlightBlocks(b, HighlightColours.GREEN);
				}
				else if (selectAction == selectedMode.ATTACK && map.getEntity((int)selected.x, (int)selected.y) instanceof Firetruck)
				{
					// Attack pattern
					Firetruck f = (Firetruck) map.getEntity((int)selected.x, (int)selected.y);
					boolean[][] b = new boolean[map.HEIGHT][map.WIDTH];
					for (int i = 0; i < map.HEIGHT; i++) {
						for (int j = 0; j < map.WIDTH; j++) {
							b[i][j] = f.isAttackPossible((int) selected.x, (int) selected.y, j, i)
									&&
									map.pathfinder.straightPath((int) selected.x, (int) selected.y, j, i);
						}
					}
					mapDrawer.highlightBlocks(b, HighlightColours.RED);
				}
			}

			// Space key handling
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
			{	
				hud.endTurnClicked = false;
				this.turnState = turnStates.POST_PLAYER;
			}

			if (enemyAI.getFortressNumber() == 0)
			{
				System.out.println("Victory!");
				// Transition to scorescreen
				game.setScreen(new ScoreScreen(game));
			}

			break;


		case POST_PLAYER:
			this.turnState = turnStates.ET;
			break;
			
		case ET:
			System.out.println("ETs are taking their turn");
			hud.setDialog("ETs:", "\nETs are taking \ntheir turn", 0.0f);
			enemyAI.takeTurn();
			this.turnState = turnStates.POST_ET;
			break;
			
		case POST_ET:
			// Test for failure
			int firetrucks = 0;
			for (int i = 0; i < map.HEIGHT; i++)
			{
				for (int j = 0; j < map.WIDTH; j++)
				{
					if (map.getEntity(j, i) != null && map.getEntity(j, i) instanceof Firetruck)
					{
						firetrucks++;
					}
				}
			}
			if (firetrucks <= 0)
			{
				System.out.println("Failure!");
				// In the case the player has failed
				game.setScreen(new DeathScreen(game));
			}

			// Condition for leveling up ET Fortresses
			if (turnNumber % enemyAI.LEVEL_UP_FREQUENCY == 0 && turnNumber != 0)
			{
				enemyAI.levelUpFortresses();
				hud.difficulty++;
				hud.updateDifficulty(hud.difficulty);
				hud.setDialog("Narrator:", "\nThe ETs are getting\n stronger", 0.0f);
			}
			this.turnState = turnStates.PLAYER;
			map.resetTurn();
			turnNumber++;
			break;
			
		}
		
		
	}

	public int getTurnNumber() { return turnNumber; }

	@Override
	public void resize(int width, int height)
	{
		mapDrawer.resize(width, height);
		hud.resize(width, height);
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
		hud.dispose();
	}
}
