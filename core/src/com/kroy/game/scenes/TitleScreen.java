package com.kroy.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.kroy.game.MyGdxGame;
import com.kroy.game.MyGdxGame.eScreen;

public class TitleScreen implements Screen
{
	/**
	 * Title screen
	 */

	final MyGdxGame game;
	private Texture titleImage;

	public TitleScreen(final MyGdxGame game)
	{
		/**
		 * Sets the game to the title screen
		 * @param game Instance of the game
		 */

		this.game = game;
		titleImage = new Texture(Gdx.files.internal("mainmenu.jpg")); 
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta)
	{
		/**
		 * Renders title screen and sets the game to the game screen on an input
		 * @param delta This represents the time between the last frame and this frame, given in seconds
		 */
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
		{
			System.out.println("Going to game screen");
			game.setScreen(new GameScreen(game));
		}

		// Do rendering
		game.batch.begin();
		game.batch.draw(titleImage, 0, 0, 512, 512);

		game.font.draw(game.batch, "TEST GAME PLEASE IGNORE \n Press SPACE to start", 128, 256);
		game.batch.end();
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
