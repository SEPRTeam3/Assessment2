package com.kroy.game.map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
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
	private TiledMap backmap;
	private Viewport viewport;
	private IsometricTiledMapRenderer backmapRenderer;
	private OrthographicCamera camera;
	
	public MapDrawer(MyGdxGame g, Map m, TiledMap t)
	{
		this.game = g;
		this.frontmap = m;
		this.backmap = t;

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
				if (frontmap.getEntity(i, j) != null)
				{
					game.batch.draw(frontmap.getEntity(i, j).getTexture(), i*32, j*32, 32, 32);
				}
				// Render blocks
				if (frontmap.getBlock(i, j) != null)
				{
					game.batch.draw(frontmap.getBlock(i, j).getTexture(), i*32, j*32, 32, 32);
				}
			}
		}
		game.batch.end();
	}

}
