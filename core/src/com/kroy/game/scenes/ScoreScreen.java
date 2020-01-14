//save the score directly to file when the game ends
package com.kroy.game.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kroy.game.MyGdxGame;
import com.kroy.game.score.ScoreRanks;
import com.kroy.game.score.Score;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;

public class ScoreScreen implements Screen
{
	final MyGdxGame game;
	private SpriteBatch batch;
	private BitmapFont font;
	//private BitmapFont.TextBounds;
	private String[] nameList, valueList;
	private String outputString;
	private ScoreRanks ScoreRanking;
	
	public ScoreScreen(final MyGdxGame game) {
		this.game = game;
		this.batch = new SpriteBatch();
		this.font = new BitmapFont();
		this.ScoreRanking = new ScoreRanks(this.game);
	}

	private String getString(){
		outputString = "Scores:\n";
		for (int i = 0; i < 10; i++){
			outputString += nameList[i] + ": "
					+ valueList[i] + "\n";
		}
		return outputString;
	}

	private void getOutputs(String scoreTypes){
		ArrayList<Score> outputScores = this.ScoreRanking.SelectScores(scoreTypes);
		for (int i = 0; i < 10; i++){
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//bounds = this.font.getData();
		getOutputs("TopTen");
		outputString = getString();

		this.batch.begin();
		this.font.draw(this.batch, outputString,
		Gdx.graphics.getWidth(),
		Gdx.graphics.getHeight());
		this.batch.end();
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
