package com.kroy.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.kroy.game.ETMastermind;
import com.kroy.game.MyGdxGame;
import com.kroy.game.entities.Firestation;
import com.kroy.game.entities.Firetruck;
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

	/**
	 *  Turn states
	 *  <li>{@link #PLAYER}</li>
	 * 	<li>{@link #POST_PLAYER}<li>
	 * 	<li>{@link #ET}<li>
	 * 	<li>{@link #POST_ET}</li>
	 */
	public enum turnStates
	{
		/**
		 * Player turn
		 */
		PLAYER,
		/**
		 * Post player turn
		 */
		POST_PLAYER,
		/**
		 * ET turn
		 */
		ET,
		/**
		 * Post ET turn
		 */
		POST_ET
	}

	/**
	 *  Player actions
	 *  <li>{@link #NONE}</li>
	 * 	<li>{@link #MOVE}<li>
	 * 	<li>{@link #ATTACK}</li>
	 */
	public enum selectedMode
	{
		/**
		 * No action
		 */
		NONE,
		/**
		 * Move action
		 */
		MOVE,
		/**
		 * Attack action
		 */
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
		/**
		 * Sets the game to the game screen
		 * Initializes all the game classes and loads assets
		 * @param game Instance of the game
		 */
		// Initialise technical objects
		this.game = game;
		selected = null;
 		turnState = turnStates.PLAYER;

		// Initialise map
		map = new Map();
		tileMap = new TmxMapLoader().load("MapTestF.tmx");
		MapParser parser = new MapParser();
		parser.addAll(map, tileMap);
		mapDrawer = new MapDrawer(game, map, tileMap);

		// Initialise HUD
		hud = new GameHud(game.batch, game.skin);
		hud.createFireTruckUI(map, game.skin);
		hud.getFiretruckEntityStats(game.skin);
		hud.setVisibilityOfTable(hud.containerSingle, false);
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
		/**
		 * Renders the map, entities and the Hud using a separate viewport for the Hud
		 * Depending on the turn, gets player input and carries out an action, updating the UI and map
		 * Sets graphics for actions and sets win condition
		 * On ET turn, runs ET actions and updates the UI and map
		 * Sets graphics for actions and sets loss and difficulty increase conditions
		 * @param delta This represents the time between the last frame and this frame, given in seconds
		 * @see MapDrawer#render()
		 */
		// Render map
		mapDrawer.viewport.apply();
		mapDrawer.render();

		// Render HUD
		hud.stage.getViewport().apply();
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		hud.updateStatsUI(map);

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

						//Update stats and switch view to single table of entity
						hud.updateEntityStats((Firetruck) map.getEntity(tileX, tileY));
						hud.setVisibilityOfTable(hud.containerMultiple, false);
						hud.setVisibilityOfTable(hud.containerSingle, true);

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
						//deselected so switch view
						hud.setVisibilityOfTable(hud.containerMultiple, true);
						hud.setVisibilityOfTable(hud.containerSingle, false);
					}
					else if (selected != null && selectAction == selectedMode.ATTACK)
					{
						// Player clicked with attack selected, so attack that area
						map.attackEntity((int)selected.x, (int)selected.y, tileX, tileY);
						selectAction = selectedMode.NONE;
						selected = null;
						//deselected so switch view
						hud.setVisibilityOfTable(hud.containerMultiple, true);
						hud.setVisibilityOfTable(hud.containerSingle, false);
					}
					else
					{
						// Player clicked on nothing and not in table
						if (!hud.clickInTable(hud.inGameTable)) {
							selectAction = selectedMode.NONE;
							selected = null;
							hud.setVisibilityOfTable(hud.containerSingle, false);
							hud.setVisibilityOfTable(hud.containerMultiple, true);
							hud.clickOffTable(hud.inGameTable);
						}


					}
				}
				else
				{
					//deselected so switch table view
					hud.setVisibilityOfTable(hud.containerSingle, false);
					hud.setVisibilityOfTable(hud.containerMultiple, true);

					// Clicked outside of map and not in a table
					if(!hud.clickInTable(hud.inGameTable)) {
						hud.clickOffTable(hud.inGameTable);

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
						hud.setDialog("Player", "\nRestocked!", 0.0f);
					}
					else
					{
						hud.setDialog("Narrator:", "\nNot close enough \n to restock", 0.0f);
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
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || hud.endTurnClicked)
			{
				//Turned ended so get rid of in game table
				hud.clickOffTable(hud.inGameTable);
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
