package com.kroy.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.game.entities.Entity;
import com.kroy.game.entities.Firetruck;
import com.kroy.game.map.Map;

public class GameHud {
    private OrthographicCamera camera;
    public Stage stage;
    private Viewport viewport;
    private Table inGameTable, topLeftTable, topRightTable, bottomRightTable, bottomLeftTable;

    private TextureRegionDrawable textureRegionDrawableBg;

    private Label test;
    private TextButton attack, move, refill, wait;
    private ImageButton image;
    private Label fireTruckStats;

    public boolean attackClicked, moveClicked, refillClicked, waitClicked, menuOpen;

    private int sizeX, sizeY;

    private Map map;
    public GameHud(SpriteBatch batch, Skin skin){
        sizeX = 120;
        sizeY = 60;

        attackClicked = moveClicked = refillClicked = waitClicked = false;
        menuOpen = false;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply(true);

        stage = new Stage(viewport, batch);

        //test = new Label("Lucas", new Label.LabelStyle(new BitmapFont(), Color.CORAL));

        attack = new TextButton("attack", skin);
        attack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Clicked!");
                attackClicked = true;
                inGameTable.setVisible(false);
                menuOpen = false;

            }
        });

        move = new TextButton("move", skin);
        move.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Clicked!");
                moveClicked = true;
                inGameTable.setVisible(false);
                menuOpen = false;

            }
        });
        refill = new TextButton("refill", skin);
        refill.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Clicked!");
                refillClicked = true;
                inGameTable.setVisible(false);
                menuOpen = false;
            }
        });



        wait = new TextButton("wait", skin);
        wait.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Clicked!");
                waitClicked = true;
                inGameTable.setVisible(false);
                menuOpen = false;

            }
        });

        inGameTable = new Table();
        inGameTable.setBackground(tableBackground());
        inGameTable.setSize(60, 120);

        inGameTable.add(attack).padBottom(2);
        inGameTable.row();
        inGameTable.add(move).padBottom(2);
        inGameTable.row();
        inGameTable.add(refill).padBottom(2);
        inGameTable.row();
        inGameTable.add(wait);

        inGameTable.setVisible(false);


        topLeftTable = new Table();
        topLeftTable.setSize(sizeX, sizeY);
        topLeftTable.setBackground(tableBackground());
        topLeftTable.setPosition(20, Gdx.graphics.getHeight()-60);

        topRightTable = new Table();
        topRightTable.setSize(sizeX, sizeY);
        topRightTable.setBackground(tableBackground());
        topRightTable.setPosition(Gdx.graphics.getWidth()-140, Gdx.graphics.getHeight()-60);

        bottomLeftTable = new Table();
        bottomLeftTable.setSize(sizeX, sizeY);
        bottomLeftTable.setBackground(tableBackground());
        bottomLeftTable.setPosition(20, 20);

        bottomRightTable = new Table();
        bottomRightTable.setSize(sizeX, sizeY);
        bottomRightTable.setBackground(tableBackground());
        bottomRightTable.setPosition(Gdx.graphics.getWidth()-140, 20);

        setFireTruckUI(skin);

        stage.addActor(inGameTable);
        stage.addActor(topLeftTable);
        stage.addActor(topRightTable);
        stage.addActor(bottomLeftTable);
        stage.addActor(bottomRightTable);

        Gdx.input.setInputProcessor(stage);
    }

    public void createGameTable() {
        menuOpen = true;
        Vector2 position = stage.screenToStageCoordinates( new Vector2(Gdx.input.getX(), Gdx.input.getY()) );


        inGameTable.setPosition(position.x ,position.y);
        inGameTable.setVisible(true);

    }
    public TextureRegionDrawable tableBackground() {
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.GRAY);
        bgPixmap.fill();
        return textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
        stage.getViewport().apply();

    }

    public void setFireTruckUI(Skin skin) {
        String stats = String.format("Health: %n Range: %n", 10, 100);

        fireTruckStats = new Label(stats, skin);
        fireTruckStats.setSize(50, 50);

        topRightTable.add(fireTruckStats);


    }

    public void dispose(){
        stage.dispose();

    }
}

