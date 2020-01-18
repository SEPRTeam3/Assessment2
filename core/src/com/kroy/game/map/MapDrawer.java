package com.kroy.game.map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.game.MyGdxGame;
import com.kroy.game.blocks.Obstacle;

public class MapDrawer
{
	/**
	 * Object responsible for rendering the tiledmap, entities and blocks
	 */
	
	private MyGdxGame game;
	private Map frontmap;

	private float screenScalingCoefficient;
	private final float TILE_WIDTH = 9.64f;
	private final Vector2 TILE_OFFSET = new Vector2(-7.5f,-15 );
	private final Vector2 HIGHLIGHT_OFFSET = new Vector2(0, -2);

	private TiledMap backmap;
	public Viewport viewport;
	private IsometricTiledMapRenderer backmapRenderer;
	private OrthographicCamera camera;

	private HighlightColours[][] highlightColours;

	private Texture highlightTexture;

	public MapDrawer(MyGdxGame g, Map m, TiledMap t)
	{
		/**
		 * Initialise the MapDrawer
		 * @param g the game object
		 * @param m the map object that the map drawer should render
		 * @param t the TiledMap object that the drawer should render
		 */
		this.game = g;
		this.frontmap = m;
		this.backmap = t;

		screenScalingCoefficient = (float) Math.min(Gdx.graphics.getHeight(), Gdx.graphics.getWidth()) / 512f;

		// Setup camera
		viewport = new FitViewport(10, 10);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, (int)backmap.getProperties().get("width") * (int)backmap.getProperties().get("tilewidth"), (int)backmap.getProperties().get("height") * (int)backmap.getProperties().get("tileheight"));
		camera.translate(new Vector2(0, -((int)backmap.getProperties().get("height") * (int)backmap.getProperties().get("tileheight") / 2)));
		viewport.setCamera(camera);
		viewport.getCamera().update();
		camera.zoom = 85f;
		camera.update();

		// Setup isometric map renderer
		backmapRenderer = new IsometricTiledMapRenderer(backmap, 1);

		// Setup map highlights
		highlightTexture = new Texture(Gdx.files.internal("selectTile.png"));
		highlightColours = new HighlightColours[Map.HEIGHT][Map.WIDTH];
		for (int i = 0; i < Map.WIDTH; i++)
		{
			for (int j = 0; j < Map.HEIGHT; j++)
			{
				highlightColours[i][j] = HighlightColours.NONE;
			}
		}
	}

	public void resize(int width, int height)
	{
		/**
		 * Resize the window
		 * @param width the new width of the window
		 * @param height the new height of the window
		 */
		viewport.update(width, height);
		viewport.getCamera().update();
		screenScalingCoefficient = (float) Math.min(Gdx.graphics.getHeight(), Gdx.graphics.getWidth()) / 512f;
	}

	public void render()
	{
		/**
		 * Render background map (TiledMap) and foreground map (Map) to screen
		 */

		// Render background
		backmapRenderer.setView(camera);
		backmapRenderer.render();

		// Render foreground
		game.batch.begin();
		for (int i = 0; i < Map.WIDTH; i++)
		{
			for (int j = 0; j < Map.HEIGHT; j++)
			{

				Vector2 drawLocation = new Vector2
						(
							getMapViewportOrigin().x + j*(getMapRightVector().x) + i*(getMapUpVector().x) + TILE_OFFSET.x,
							getMapViewportOrigin().y + j*(getMapRightVector().y) + i*(getMapUpVector().y) + TILE_OFFSET.y
						);

				// Render highlights
				Color c = new Color(game.batch.getColor());
				switch(highlightColours[j][i])
				{
					// Set the colour of the sprite batch based on the highlight colour of the current tile
					case RED:
						game.batch.setColor(.5f, 0f, 0f, 0.5f);
						break;
					case GREY:
						game.batch.setColor(0.2f, 0.2f, 0.2f, 0.5f);
						break;
					case GREEN:
						game.batch.setColor(0f, .5f, 0f, 0.5f);
						break;
					case NONE:
						break;
				}
				if (highlightColours[j][i] != HighlightColours.NONE)
				{
					game.batch.draw
					(
							highlightTexture,
							drawLocation.x + HIGHLIGHT_OFFSET.x,
							drawLocation.y + HIGHLIGHT_OFFSET.y,
							16, 16
					);
					highlightColours[j][i] = HighlightColours.NONE;
				}
				game.batch.setColor(c);

				// Render entities
				if (frontmap.getEntity(i, j) != null)
				{
					game.batch.draw(frontmap.getEntity(i, j).getTexture(), drawLocation.x, drawLocation.y, 16, 16);
				}

				// Render blocks
				if (frontmap.getBlock(i, j) != null && !(frontmap.getBlock(i, j) instanceof Obstacle))
				{
					game.batch.draw(frontmap.getBlock(i, j).getTexture(), drawLocation.x, drawLocation.y, 16, 16);
				}
			}
		}
		game.batch.end();
	}

	public Vector2 getMapScreenOrigin()
	{
		/**
		 * Returns the location of the top point of the map as a 2d vector with reference to the whole screen.
		 * @return map origin in screenspace
		 */
		int adjustedHeight = (Gdx.graphics.getHeight() - Gdx.graphics.getWidth()) / 2;
		return new Vector2(Gdx.graphics.getWidth() / 2,
				(Gdx.graphics.getHeight() - adjustedHeight) * 0.025f
		);
	}

	public Vector2 getMapViewportOrigin()
	{
		/**
		 * Returns the location of the top point of the map as a 2d vector with reference to the viewpoint, which is a
		 * subset of the screen, as it does not include the white bars.
		 * @return map origin in viewport space
		 */
		return new Vector2(255.5f, 496.5f);
	}

	public Vector2 getMapUpVector()
	{
		/**
		 * The up vector of the map is a vector that points in the direction of the up axis of the isometric map
		 * The up axis is taken to be south-east
		 * The length of this vector is the length of an edge of an isometric tile.
		 * @return unit vector pointing up in tile space
		 */
		return new Vector2(TILE_WIDTH, -TILE_WIDTH);
	}

	public Vector2 getMapRightVector()
	{
		/**
		 * The right vector of the map is a vector that points in the direction of the right axis of the isometric map
		 * The right axis is taken to be south-west
		 * The length of this vector is the length of an edge of an isometric tile.
		 * @return unit vector pointing right in tile space
		 */
		return new Vector2(-TILE_WIDTH, -TILE_WIDTH);
	}

	public float getScreenScalingCoefficient() {
		return screenScalingCoefficient;
	}

	public void setScreenScalingCoefficient(float screenScalingCoefficient) {
		this.screenScalingCoefficient = screenScalingCoefficient;
	}

	public void highlightBlocks(boolean[][] blocks)
	{
		/**
		 * Highlights all squares given as true in blocks in red
		 * @param blocks 2*2 matrix the same size as the map
		 */
		for (int i = 0; i < Map.WIDTH; i++)
		{
			for (int j = 0; j < Map.HEIGHT; j++)
			{
				if (blocks[j][i])
				{
				highlightColours[j][i] = HighlightColours.RED;
				}
			}
		}
	}

	public void highlightBlocks(boolean[][] blocks, HighlightColours colour)
	{
		/**
		 * Highlights all squares given as true in blocks in the given colour
		 * @param colour enum designating the colour of the highlights
		 * @param blocks 2*2 matrix the same size as the map
		 */
		for (int i = 0; i < Map.WIDTH; i++)
		{
			for (int j = 0; j < Map.HEIGHT; j++)
			{
				if (blocks[j][i] == true)
				{
					highlightColours[j][i] = colour;
				}
			}
		}
	}

	public Vector2 toMapSpace(Vector2 click)
	{
		/**
		 * Maps a coordinate as a vector from screenspace to isometric map space.
		 * Precondition:
		 * 	Origin is screen bottom left
		 * 	Up vector is screen up
		 * 	Right vector is screen right
		 * 	Scale is the window resolution
		 * Postcondition:
		 * 	Origin is the top point of the isometric diamond
		 * 	Up vector is south-east
		 * 	Right vector is south-west
		 * 	Scale is such that one map tile is one unit
		 * If the coordinate is outside of the map, return null
		 *  @param click coordinates in screen space
		 */
		Vector2 clicked;

		// Size of grid is bounded by shortest axis
		if (Gdx.graphics.getHeight() > Gdx.graphics.getWidth())
		{
			clicked = new Vector2
			(
					click.x - getMapScreenOrigin().x,
					click.y - getMapScreenOrigin().y
			);
		}
		else
		{
			clicked = new Vector2
			(
					click.x - Gdx.graphics.getWidth() / 2,
					Gdx.input.getY() - Gdx.graphics.getHeight() * 0.025f
			);
		}
		clicked = clicked.rotate(-45f);	// Rotate to match isometric perspective

		clicked.x = (float) Math.floor(clicked.x);	// Snap to match tilespace
		clicked.y = (float) Math.floor(clicked.y);

		// Scale to grid
		clicked.scl(1f/(getScreenScalingCoefficient()));	// Is relative to the scaling coefficient
		clicked.scl(24f/328f);	// Divide max (328 for some reason) by 24 to get appropriately sized tiles
		clicked.x = (float) Math.floor(clicked.x);	// Floor values
		clicked.y = (float) Math.floor(clicked.y);

		// If the position is outside of the map, return null
		if (clicked.x >= 0f && clicked.x < Map.WIDTH && clicked.y >= 0f && clicked.y < Map.HEIGHT)
		{
			return clicked;
		}
		else
		{
			return null;
		}
	}

	public void setCorruption(int x, int y)
	{
		/**
		 * Corruption is a sprite that appears on the map as a visual representation of ET strength
		 * Sets location (x,y) to render as 'corrupted'
		 * @param x The x location of the corruption
		 * @param y The y location of the corruption
		 */
		TiledMapTileLayer tileLayer = (TiledMapTileLayer) backmap.getLayers().get("Corruption");
		tileLayer.setCell(x, tileLayer.getHeight()-y-1, MapParser.getCorruption(backmap));
	}
}
