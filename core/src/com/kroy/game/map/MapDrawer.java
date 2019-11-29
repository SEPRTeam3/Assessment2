package com.kroy.game.map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
	private float screenScalingCoefficient;

	private TiledMap backmap;
	private Viewport viewport;
	private IsometricTiledMapRenderer backmapRenderer;
	private OrthographicCamera camera;

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


		SpriteBatch spriteBatch = new SpriteBatch();

		backmapRenderer = new IsometricTiledMapRenderer(backmap, 1);

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
	}

	public void render()
	{
		backmapRenderer.setView(camera);
		backmapRenderer.render();

		//game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		for (int i = 0; i < Map.WIDTH; i++)
		{
			for (int j = 0; j < Map.HEIGHT; j++)
			{
				// Render entities
				Vector2 mapOriginFromTopRight = new Vector2(getMapScreenOrigin().x, Gdx.graphics.getHeight() - getMapScreenOrigin().y);	// I think my code has violated the Geneva Convention at this point.
				System.out.println(getMapUpVector());
				//System.out.println(i*getMapRightVector().x);
				Vector2 drawLocation = new Vector2
						(
							getMapScreenOrigin().x + j*getMapUpVector().x + i*getMapRightVector().x,
							getMapScreenOrigin().y + j*getMapUpVector().y + i*getMapRightVector().y
						);
				if (frontmap.getEntity(i, j) != null)
				{
					//System.out.println("Drawing entity at " + drawLocation.x + ", " + drawLocation.y);
					//game.batch.draw(frontmap.getEntity(i, j).getTexture(), (int)drawLocation.x, -(int)drawLocation.y, 32, 32);
					game.batch.draw(frontmap.getEntity(i, j).getTexture(), (int)mapOriginFromTopRight.x, (int)mapOriginFromTopRight.y, 32, 32);
				}
				// Render blocks
				if (frontmap.getBlock(i, j) != null)
				{
					game.batch.draw(frontmap.getBlock(i, j).getTexture(), (int)drawLocation.x, -(int)drawLocation.y, 32, 32);
				}
			}
		}
		game.batch.end();
	}

	public Vector2 getMapScreenOrigin()
	{
		return mapScreenOrigin;
	}

	public Vector2 getMapUpVector()
	{
		/*
		The up vector of the map is a vector that points in the direction of the up axis of the isometric map
		The up axis is taken to be south-east
		The length of this vector is the length of an edge of an isometric tile.
		 */
		return new Vector2(10f*getScreenScalingCoefficient(), 0).rotate(-45f);
	}

	public Vector2 getMapRightVector()
	{
		// The right vector of the drawn map is the up vector rotated 90 degrees clockwise
		return getMapUpVector().rotate90(1);
	}

	public float getScreenScalingCoefficient() {
		return screenScalingCoefficient;
	}

	public void setScreenScalingCoefficient(float screenScalingCoefficient) {
		this.screenScalingCoefficient = screenScalingCoefficient;
	}
}
