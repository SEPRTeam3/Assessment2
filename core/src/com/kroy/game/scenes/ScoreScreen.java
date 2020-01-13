package com.kroy.game.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kroy.game.MyGdxGame;
import java.util.ArrayList;
import java.util.List;

public class ScoreScreen implements Screen
{
	final MyGdxGame game;
	
	public ScoreScreen(final MyGdxGame game) {
		this.game = game;
	}

	private void DisplayScoreScreen(){
		List<Label> nameLabels= new ArrayList<Label>();
		List<Label> scoreLabels = new ArrayList<Label>();
		Table scoreTable = new Table();
		for (int i = 0; i < 10; i++){
			/* Label nameLabel = new Label();
			scoreTable.add();
			 */
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
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
