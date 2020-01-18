
package com.kroy.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.game.entities.Firetruck;
import com.kroy.game.entities.Fortress;
import com.kroy.game.map.Map;

public class GameHud {
    private OrthographicCamera camera;
    public Stage stage;
    private Viewport viewport;
    private Table inGameTable;
    private Table turnStateTable;
    private Table dialogTable;
    private Table backgroundDialogTable;
    private Table backgroundTurnStateTable;

    private TextureRegionDrawable textureRegionDrawableBg;

    private Label test;
    private TextButton attack, move, refill, wait, endTurn;
    private ImageButton image;
    private Label turnState, dialog, difficultyLabel;

    private Label[] fireTruckStats = new Label[2];
    private Image[] fireTruckImg = new Image[2], etFortressImg = new Image[3];

    private Image player;
    private Texture playerIconTexture, fireTruckIconTexture, etFortressIconTexture;
    public boolean attackClicked, moveClicked, refillClicked, waitClicked, endTurnClicked, menuOpen;

    private int sizeX, sizeY;
    public int difficulty;

    public int ftPosY, ftPosX, etPosY, etPosX;




    public GameHud(SpriteBatch batch, Skin skin) {
        sizeX = 140;
        sizeY = 80;

        difficulty = 1;
        difficultyLabel = new Label("Difficulty Level: " + difficulty, skin);
        difficultyLabel.setPosition(60, 445);
        difficultyLabel.setColor(Color.BLACK);

        playerIconTexture = new Texture(Gdx.files.internal("fox-1.png.png"));
        player = new Image(playerIconTexture);
        player.setPosition(10, 430);
        player.setScale(0.3f);

        fireTruckIconTexture = new Texture(Gdx.files.internal("fire engine 64 by 64-1.png.png"));
        etFortressIconTexture = new Texture(Gdx.files.internal("etFortress.png"));


        attackClicked = moveClicked = refillClicked = waitClicked = false;
        menuOpen = false;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply(true);

        stage = new Stage(viewport, batch);

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

        endTurn = new TextButton("End Turn", skin);
        endTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Clicked!");
                endTurnClicked = true;
                inGameTable.setVisible(false);
                menuOpen = false;

            }
        });

        inGameTable = new Table();
        inGameTable.setBackground(skin.getDrawable("default-scroll"));
        inGameTable.setSize(60, 120);

        inGameTable.add(attack).padBottom(2);
        inGameTable.row();
        inGameTable.add(move).padBottom(2);
        inGameTable.row();
        inGameTable.add(refill).padBottom(2);
        inGameTable.row();
        inGameTable.add(wait);

        inGameTable.setVisible(false);

        backgroundDialogTable = new Table();
        backgroundDialogTable.setSize(sizeX+6, sizeY+6);
        backgroundDialogTable.setBackground(new TextureRegionDrawable(tableBackground(Color.GOLDENROD)));
        backgroundDialogTable.setPosition(17, 17);

        dialog = new Label("Narrator:\n Welcome savior\n Please save York", skin);
        dialog.setAlignment(Align.left);

        dialogTable = new Table();
        dialogTable.setSize(sizeX, sizeY);
        dialogTable.setBackground(skin.getDrawable("default-scroll"));
        dialogTable.setPosition(20, 20);

        dialogTable.add(dialog);

        backgroundTurnStateTable = new Table();
        backgroundTurnStateTable.setSize(sizeX+6, sizeY+6);
        backgroundTurnStateTable.setBackground(new TextureRegionDrawable(tableBackground(Color.GOLDENROD)));
        backgroundTurnStateTable.setPosition(Gdx.graphics.getWidth()-163, 17);

        turnState = new Label("Narrator:\n Welcome savior\n Please save York", skin);
        turnState.setAlignment(Align.center);

        turnStateTable = new Table();
        turnStateTable.setSize(sizeX, sizeY);
        turnStateTable.setBackground(skin.getDrawable("default-scroll"));
        turnStateTable.setPosition(Gdx.graphics.getWidth()-160, 20);

        turnStateTable.add(turnState);
        turnStateTable.row();
        turnStateTable.add(endTurn);
        setFireTruckUI(skin);

        stage.addActor(difficultyLabel);
        stage.addActor(player);
        stage.addActor(inGameTable);
        stage.addActor(backgroundDialogTable);
        stage.addActor(dialogTable);
        stage.addActor(backgroundTurnStateTable);
        stage.addActor(turnStateTable);

        Gdx.input.setInputProcessor(stage);
    }

    public void createGameTable() {
        menuOpen = true;
        moveClicked = attackClicked = waitClicked = refillClicked = false;
        Vector2 position = stage.screenToStageCoordinates( new Vector2(Gdx.input.getX(), Gdx.input.getY()) );

        inGameTable.setPosition(position.x ,position.y);
        inGameTable.setVisible(true);

    }
    public void clickOffInGameTable() {
        inGameTable.setVisible(false);
    }

    public void setDialog(final String voice, final String statement, float delayTime) {
        float waitTime = 0;

        while (waitTime <= delayTime) {

            switch (voice) {
                case "Narrator:":
                    dialog.setText(voice + statement);

                    break;
                case "ETs":
                case "Player":
                    dialog.setText(voice + ":" + statement);
                    break;
                case "ETs:":
                    backgroundDialogTable.setBackground(new TextureRegionDrawable(tableBackground(Color.RED)));
                    backgroundTurnStateTable.setBackground(new TextureRegionDrawable(tableBackground(Color.RED)));
                    turnState.setText(voice + statement);

                    break;
                case "Player:":
                    backgroundDialogTable.setBackground(new TextureRegionDrawable(tableBackground(Color.SKY)));
                    backgroundTurnStateTable.setBackground(new TextureRegionDrawable(tableBackground(Color.SKY)));
                    turnState.setText(voice + statement);
                    break;
                default:
                    break;
            }
            waitTime += Gdx.graphics.getDeltaTime();
        }
    }





    public TextureRegionDrawable tableBackground(Color color) {
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(color);
        bgPixmap.fill();
        return textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }

    public boolean clickInTable() {

        Vector2 position = stage.screenToStageCoordinates( new Vector2(Gdx.input.getX(), Gdx.input.getY()) );

        if(position.x>= inGameTable.getX() && position.x<= (inGameTable.getX()+inGameTable.getWidth()) && position.y>= inGameTable.getY() && position.y<= (inGameTable.getY()+inGameTable.getHeight())) {
            return true;
        } else {
            return false;
        }

    }

    public void createFireTruckUI(Map map, Skin skin) {
        int x = 0;
        int y = 0;
        int waterLevel;
        int firetruckHealth;
        int etFortressHealth;
        int etFortressName;

        int labelOffset = 30;

        ftPosY = Gdx.graphics.getHeight() - 40;
        ftPosX = Gdx.graphics.getWidth() - 240;

        etPosY = ftPosY - 80;
        etPosX = ftPosX + 80;

        for (int i = 0; i < map.HEIGHT; i++)
        {
            for (int j = 0; j < map.WIDTH; j++)
            {
                if (map.getEntity(i, j) != null && map.getEntity(i, j) instanceof Firetruck)
                {

                    Firetruck f = (Firetruck) map.getEntity(i, j);
                    waterLevel = f.getWater();
                    firetruckHealth = f.getHealth();
                    String stats = String.format("Health: %s \nWater Level: %s", firetruckHealth, waterLevel);

                    fireTruckImg[x] = new Image(fireTruckIconTexture);
                    fireTruckImg[x].setPosition(ftPosX, ftPosY);
                    fireTruckImg[x].setScale(0.3f);

                    fireTruckStats[x] = new Label(stats, skin);
                    fireTruckStats[x].setPosition(ftPosX + labelOffset, ftPosY);
                    fireTruckStats[x].setSize(20, 20);
                    fireTruckStats[x].setFontScale(0.8f);
                    fireTruckStats[x].setColor(Color.BLACK);

                    stage.addActor(fireTruckImg[x]);
                    stage.addActor(fireTruckStats[x]);

                    ftPosX += 40;
                    ftPosY -= 40;
                    x++;

                } else if(map.getEntity(i, j) != null && map.getEntity(i, j) instanceof Fortress) {
                    if(y < 3) {
                        Fortress f = (Fortress) map.getEntity(i, j);
                        int fortressHealth = f.getHealth();

                        etFortressImg[y] = new Image(etFortressIconTexture);
                        etFortressImg[y].setPosition(etPosX, etPosY);


                        stage.addActor(etFortressImg[y]);
                        etPosX += 40;
                        etPosY -= 40;
                        y++;
                    }
                }
            }
        }


    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
        stage.getViewport().apply();

    }
    public void updateDifficulty(int difficulty) {
        difficultyLabel.setText(String.format("Difficulty Level %d", difficulty));
    }
    public void setFireTruckUI(Skin skin) {


    }

    public void dispose(){
        stage.dispose();

    }
}

