//save the score directly to file when the game ends
package com.kroy.game.scenes;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.game.MyGdxGame;
import com.kroy.game.score.ScoreRanks;
import com.kroy.game.score.Score;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;

public class ScoreScreen implements Screen
{
	/**
	 * Generates a screen that displays 10 scores
	 */
	final MyGdxGame game;
	private SpriteBatch batch;
	private BitmapFont font;
	private GlyphLayout layout;
	private String[] nameList, valueList;
	private String outputString;
	private ScoreRanks ScoreRanking;
	private Viewport viewport;
	public ScoreScreen(final MyGdxGame game) {
		/**
		 * Generates a score screen with the top 10 scores
		 */
		this.game = game;
		this.batch = new SpriteBatch();
		this.font = new BitmapFont();
		this.ScoreRanking = new ScoreRanks();
		getOutputs("TopTen");
		this.outputString = getString();

		viewport = new FitViewport(10, 10);
		Gdx.graphics.setWindowedMode(512, 512);
	}
	public ScoreScreen(final MyGdxGame game, Score playerScore) {
		/**
		 * Generates a screen with the top 8, plus the users score and the score below it.
		 * @param
		 * @param
		 */
		this.game = game;
		this.batch = new SpriteBatch();
		this.font = new BitmapFont();
		this.ScoreRanking = new ScoreRanks();
		this.ScoreRanking.setPlayerScore(playerScore);
		getOutputs("TopAndNew");
		this.outputString = getString();
	}

	private String getString(){
		/**
		 * Gets the output in a string format so it can be put on the screen
		 */
		outputString = "Scores:\n";
		for (int i = 0; i < 10; i++){
			outputString += nameList[i] + ": "
					+ valueList[i] + "\n";
		}
		return outputString;
	}

	private void getOutputs(String scoreTypes){
		/**
		 * Gets the 10 scores and names from the Score Rank class and puts them into lists.
		 * @param scoreTypes Either "TopTen" or "TopAndNew", decides whether its the top 10 scores, or top 8 and new and 1 below new.
		 */
		ArrayList<Score> outputScores = this.ScoreRanking.SelectScores(scoreTypes);
		this.nameList = new String[10];
		this.valueList = new String[10];
		for (int i = 0; i < 8; i++){
			this.nameList[i] = outputScores.get(i).getName();
			this.valueList[i] = outputScores.get(i).getValuestr();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		/**
		 * Renders the screen to be displayed
		 * @param
		 */
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		getOutputs("TopTen");
		outputString = getString();

		layout = new GlyphLayout(game.font, outputString);

		float fontX = Gdx.graphics.getWidth()/2 - layout.width/2;
		float fontY = Gdx.graphics.getHeight()/2 + layout.height/2;

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
		{
			System.out.println("Going to Home");
			game.setScreen(new TitleScreen(game));
		}

		game.batch.begin();
		game.font.draw(game.batch, outputString,
		fontX,
		fontY);
		game.font.draw(game.batch, "You WIN\n   High score for you \n \n Space to restart", 128, 50);
		game.batch.end();
		// TODO Auto-generated method stub
		
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
