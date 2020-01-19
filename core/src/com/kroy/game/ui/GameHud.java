
package com.kroy.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.game.entities.Firetruck;
import com.kroy.game.entities.Fortress;
import com.kroy.game.map.Map;

public class GameHud {
    public Stage stage;
    private Viewport viewport;

    public Table inGameTable;
    private Table turnStateTable;
    private Table dialogTable;
    private Table backgroundDialogTable;
    private Table backgroundTurnStateTable;
    public Table container1, container2;

    private int numFiretrucks, numEtFortresses;

    private TextureRegionDrawable textureRegionDrawableBg;

    private Label test;
    private TextButton attack, move, refill, wait, endTurn;
    private ImageButton image;
    private Label turnState, dialog, difficultyLabel;

    private Label[] fireTruckStats = new Label[2], etFortressStats = new Label[3];
    private Image[] fireTruckImg = new Image[2], etFortressImg = new Image[3];

    private Label healthLabel, waterLevelLabel, attackStrengthLabel, movementRangeLabel, attackRangeLabel, hasMovedLabel, hasAttackedLabel;
    private Image fireTruckImgSingle, etFortressImgSingle;


    private Image player;
    private Texture playerIconTexture, fireTruckIconTexture, etFortressIconTexture;
    public boolean attackClicked, moveClicked, refillClicked, waitClicked, endTurnClicked, menuOpen;

    private int sizeX, sizeY;
    public int difficulty;

    public int ftPosY, ftPosX, etPosY, etPosX, iniPosY, iniPosX;




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

        fireTruckImgSingle = new Image(fireTruckIconTexture);
        etFortressImgSingle = new Image(etFortressIconTexture);

        numFiretrucks = numEtFortresses = 0;

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
                System.out.println("Clicked!End");
                endTurnClicked = true;
                inGameTable.setVisible(false);
                menuOpen = false;

            }
        });

        container1 = new Table();
        container2 = new Table();

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

        stage.addActor(container1);
        stage.addActor(container2);
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
        float midHeight = Gdx.graphics.getHeight()/2;
        float midWidth = Gdx.graphics.getWidth()/2;

        float tableHeight = inGameTable.getHeight();
        float tableWidth = inGameTable.getWidth();

        menuOpen = true;
        moveClicked = attackClicked = waitClicked = refillClicked = false;
        Vector2 position = stage.screenToStageCoordinates( new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        if(position.x <= midWidth && position.y <= midHeight) {
            inGameTable.setPosition(position.x ,position.y);
        } else if (position.x >= midWidth && position.y <= midHeight) {
            inGameTable.setPosition(position.x-tableWidth, position.y);
        } else if (position.x <= midWidth && position.y >= midHeight) {
            inGameTable.setPosition(position.x, position.y-tableHeight);
        } else {
            inGameTable.setPosition(position.x-tableWidth, position.y-tableHeight);
        }

        inGameTable.setVisible(true);

    }
    public void clickOffTable(Table table) {
        table.setVisible(false);
        menuOpen = false;
    }
    public void setVisibilityOfTable(Table table, boolean state) {
        if(state) {
            table.setVisible(true);
        } else {
            table.setVisible(false);
        }
    }

    public void setDialog(final String voice, final String statement, float delayTime) {
        float waitTime = 0;

        while (waitTime <= delayTime) {

            switch (voice) {
                case "Narrator:":
                    dialog.setText(voice + statement);
                    backgroundDialogTable.setBackground(new TextureRegionDrawable(tableBackground(Color.GOLDENROD)));
                    break;
                case "ETs":
                case "Player":
                    dialog.setText(voice + ":" + statement);
                    backgroundDialogTable.setBackground(new TextureRegionDrawable(tableBackground(Color.SKY)));
                    break;
                case "ETs:":

                    backgroundTurnStateTable.setBackground(new TextureRegionDrawable(tableBackground(Color.RED)));
                    turnState.setText(voice + statement);

                    break;
                case "Player:":

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

    public boolean clickInTable(Table table) {

        Vector2 position = stage.screenToStageCoordinates( new Vector2(Gdx.input.getX(), Gdx.input.getY()) );

        if(position.x>= table.getX() && position.x<= (table.getX()+table.getWidth()) && position.y>= table.getY() && position.y<= (table.getY()+table.getHeight())) {
            System.out.print("true bitches");
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
        int maxWaterLevel;
        int maxFireTruckHealth;

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
                    f.restock();
                    waterLevel = f.getWater();
                    maxWaterLevel = f.getMaxWater();
                    firetruckHealth = f.getHealth();
                    maxFireTruckHealth = f.getMaxHealth();

                    String stats = String.format("Health: %s/%s \nWater Level: %s/%s", firetruckHealth, maxFireTruckHealth ,waterLevel, maxWaterLevel);

                    fireTruckImg[x] = new Image(fireTruckIconTexture);
                    fireTruckImg[x].setPosition(ftPosX, ftPosY);
                    fireTruckImg[x].setScale(0.3f);

                    if(x == 0) {
                        fireTruckImg[x].setColor(Color.CYAN);
                    }
                    fireTruckStats[x] = new Label(stats, skin);

                    fireTruckStats[x].setPosition(ftPosX + labelOffset, ftPosY);
                    fireTruckStats[x].setSize(20, 20);
                    fireTruckStats[x].setFontScale(0.8f);
                    fireTruckStats[x].setColor(Color.BLACK);

                    container1.addActor(fireTruckImg[x]);
                    container1.addActor(fireTruckStats[x]);

                    ftPosX += 40;
                    ftPosY -= 40;
                    x++;

                } else if(map.getEntity(i, j) != null && map.getEntity(i, j) instanceof Fortress) {
                    if(y < 3) {
                        Fortress f = (Fortress) map.getEntity(i, j);
                        int fortressHealth = f.getHealth();

                        String stats = String.format("Health: %s \n", fortressHealth);

                        etFortressImg[y] = new Image(etFortressIconTexture);
                        etFortressImg[y].setPosition(etPosX, etPosY);

                        etFortressStats[y] = new Label(stats, skin);

                        etFortressStats[y].setPosition(etPosX + labelOffset, etPosY);
                        etFortressStats[y].setSize(20, 20);
                        etFortressStats[y].setFontScale(0.8f);
                        etFortressStats[y].setColor(Color.BLACK);

                        container1.addActor(etFortressImg[y]);
                        container1.addActor(etFortressStats[y]);

                        etPosX += 30;
                        etPosY -= 30;
                        y++;
                    }
                }
            }
        }


    }
    public void getEntityStats(Firetruck f, Skin skin) {
        boolean movedThisTurn = f.hasAttackedThisTurn();
        boolean attackedThisTurn = f.hasAttackedThisTurn();

        int movementDistance = f.getMovementDistance();
        int attackDistance = f.getAttackDistance();

        int attackStrength = f.getAttackStrength();

        int maxHealth = f.getMaxHealth();
        int health = f.getHealth();
        int maxWater = f.getMaxWater();
        int water = f.getWater();

        iniPosY = Gdx.graphics.getHeight() - 40;
        iniPosX = Gdx.graphics.getWidth() - 240;


        fireTruckImgSingle.setPosition(iniPosX, iniPosY);
        fireTruckImgSingle.setScale(0.3f);
        container2.addActor(fireTruckImgSingle);

        iniPosY -= 15;
        iniPosX += 20;
        String stats = String.format("Health: %s/%s", health, maxHealth);
        healthLabel = new Label(stats, skin);
        healthLabel.setPosition(iniPosX, iniPosY);
        healthLabel.setSize(20, 20);
        healthLabel.setFontScale(0.8f);
        healthLabel.setColor(Color.BLACK);
        container2.addActor(healthLabel);





        if(f.getTexture().toString() == "SpecialFiretrucks1.png") {
            fireTruckImgSingle.setColor(Color.CYAN);
        }
    }

    public void getEntityStats(Fortress f, Skin skin) {

    }

    public void updateStatsUI(Map map) {
        int waterLevel;
        int firetruckHealth;
        int maxWaterLevel;
        int maxFireTruckHealth;
        int x = 0;
        int y = 0;

        for (int i = 0; i < map.HEIGHT; i++)
        {
            for (int j = 0; j < map.WIDTH; j++)
            {
                if (map.getEntity(i, j) != null && map.getEntity(i, j) instanceof Firetruck)
                {

                    Firetruck f = (Firetruck) map.getEntity(i, j);
                    waterLevel = f.getWater();
                    maxWaterLevel = f.getMaxWater();

                    firetruckHealth = f.getHealth();
                    maxFireTruckHealth = f.getMaxHealth();
                    String stats = String.format("Health: %s/%s \n Water Level: %s/%s", firetruckHealth, maxFireTruckHealth, waterLevel, maxWaterLevel);

                    fireTruckStats[x].setText(stats);

                    x++;

                } else if(map.getEntity(i, j) != null && map.getEntity(i, j) instanceof Fortress) {
                    if(y < 3) {
                        Fortress f = (Fortress) map.getEntity(i, j);
                        int fortressHealth = f.getHealth();

                        String stats = String.format("Health: %s \n", fortressHealth);

                        etFortressStats[y].setText(stats);

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

    public void dispose(){
        stage.dispose();

    }
}

