package com.kroy.game.map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
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

public class MapDrawer
{
	/*
	 * Object responsible for rendering the tiledmap, entities and blocks
	 */
	
	private MyGdxGame game;
	private Map frontmap;

	private Vector2 mapScreenOrigin;
	private Vector2 mapViewportOrigin;
	private float screenScalingCoefficient;
	private final float TILE_WIDTH = 9.6f;
	private final Vector2 TILE_OFFSET = new Vector2(-7.5f,-15 );
	private final Vector2 HIGHLIGHT_OFFSET = new Vector2(0, -2);

	private TiledMap backmap;
	private Viewport viewport;
	private IsometricTiledMapRenderer backmapRenderer;
	private OrthographicCamera camera;

	private HighlightColours[][] highlightColours;

	private Texture debugTexture;
	private Texture highlightTexture;

	public MapDrawer(MyGdxGame g, Map m, TiledMap t)
	{
		this.game = g;
		this.frontmap = m;
		this.backmap = t;

		screenScalingCoefficient = (float) Math.min(Gdx.graphics.getHeight(), Gdx.graphics.getWidth()) / 512f;
		int adjustedHeight = (Gdx.graphics.getHeight() - Gdx.graphics.getWidth()) / 2;
		mapScreenOrigin = new Vector2(Gdx.graphics.getWidth() / 2,
				(Gdx.graphics.getHeight() - adjustedHeight) * 0.025f
		);

		viewport = new FitViewport(10, 10); //(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera = new OrthographicCamera();
		camera.setToOrtho(false, (int)backmap.getProperties().get("width") * (int)backmap.getProperties().get("tilewidth"), (int)backmap.getProperties().get("height") * (int)backmap.getProperties().get("tileheight"));
		camera.translate(new Vector2(0, -((int)backmap.getProperties().get("height") * (int)backmap.getProperties().get("tileheight") / 2)));
		viewport.setCamera(camera);
		//viewport.setScreenSize(10, 10);
		viewport.getCamera().update();
		camera.zoom = 85f;
		camera.update();

		mapViewportOrigin = new Vector2(256, 492);

		debugTexture = new Texture(Gdx.files.internal("Firetruck2.png"));
		highlightTexture = new Texture(Gdx.files.internal("selectTile.png"));
		SpriteBatch spriteBatch = new SpriteBatch();

		backmapRenderer = new IsometricTiledMapRenderer(backmap, 1);

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
		viewport.update(width, height);
		//camera.position.set(0, 0, 0);//(camera.viewportWidth/2,camera.viewportHeight/2,0);
		//camera.translate(camera.viewportWidth/2,camera.viewportHeight/2,0);
		viewport.getCamera().update();
		screenScalingCoefficient = (float) Math.min(Gdx.graphics.getHeight(), Gdx.graphics.getWidth()) / 512f;
		int adjustedHeight = (Gdx.graphics.getHeight() - Gdx.graphics.getWidth()) / 2;
		mapScreenOrigin = new Vector2(Gdx.graphics.getWidth() / 2,
				(Gdx.graphics.getHeight() - adjustedHeight) * 0.025f
		);
		System.out.println("Viewport dimensions: " + viewport.getScreenWidth() + ", " + viewport.getScreenHeight());
		System.out.println("Viewpoint origin at: " + getMapViewportOrigin().x + ", " + getMapViewportOrigin().y);
	}

	public void render()
	{
		backmapRenderer.setView(camera);
		backmapRenderer.render();

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
				Color c = game.batch.getColor();
				game.batch.setColor(c.r, c.g, c.b, .3f);
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
				game.batch.setColor(c.r, c.g, c.b, 1f);

				// Render entities
				if (frontmap.getEntity(i, j) != null)
				{
					//System.out.println("Drawing entity at " + drawLocation.x + ", " + drawLocation.y);
					//game.batch.draw(frontmap.getEntity(i, j).getTexture(), (int)drawLocation.x, -(int)drawLocation.y, 32, 32);
					game.batch.draw(frontmap.getEntity(i, j).getTexture(), drawLocation.x, drawLocation.y, 16, 16);
				}

				// Render blocks
				if (frontmap.getBlock(i, j) != null)
				{
					game.batch.draw(frontmap.getBlock(i, j).getTexture(), drawLocation.x, drawLocation.y, 16, 16);
				}
			}
		}
		game.batch.end();
	}

	public Vector2 getMapScreenOrigin()
	{
		/*
		Returns the location of the top point of the map as a 2d vector with reference to the whole screen.
		 */
		return mapScreenOrigin;
	}

	public Vector2 getMapViewportOrigin()
	{
		/*
		Returns the location of the top point of the map as a 2d vector with reference to the viewpoint, which is a
		subset of the screen, as it does not include the white bars.
		 */
		return mapViewportOrigin;
	}

	public Vector2 getMapUpVector()
	{
		/*
		The up vector of the map is a vector that points in the direction of the up axis of the isometric map
		The up axis is taken to be south-east
		The length of this vector is the length of an edge of an isometric tile.
		 */
		return new Vector2(TILE_WIDTH, -TILE_WIDTH);
	}

	public Vector2 getMapRightVector()
	{
		// The right vector of the drawn map is the up vector rotated 90 degrees clockwise
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
		for (int i = 0; i < Map.WIDTH; i++)
		{
			for (int j = 0; j < Map.HEIGHT; j++)
			{
				if (blocks[j][i])
				{
				highlightColours[j][i] = HighlightColours.GREEN;
				}
			}
		}
	}

	public void highlightBlocks(boolean[][] blocks, HighlightColours colour)
	{
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
}
