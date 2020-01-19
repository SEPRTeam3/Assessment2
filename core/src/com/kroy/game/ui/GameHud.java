
package com.kroy.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.game.entities.DestroyedEntity;
import com.kroy.game.entities.Firetruck;
import com.kroy.game.entities.Fortress;
import com.kroy.game.map.Map;

public class GameHud {
    /**
     * Object responsible for rendering the UI
     */
    //New view
    public Stage stage;
    private Viewport viewport;
    //Tables to order UI
    public Table inGameTable;
    private Table turnStateTable;
    private Table dialogTable;
    private Table backgroundDialogTable;
    private Table backgroundTurnStateTable;
    public Table containerMultiple, containerSingle;

    //Buttons and corresponding UI
    private TextButton attack, move, refill, wait, endTurn;
    public boolean attackClicked, moveClicked, refillClicked, waitClicked, endTurnClicked, menuOpen;

    private Label turnState, dialog, difficultyLabel;

    //Images and labels for showing stats in multi view
    private Label[] fireTruckStats = new Label[2], etFortressStats = new Label[3];
    private Image[] fireTruckImg = new Image[2], etFortressImg = new Image[3];

    //Images and labels for showing stats in singe view
    private Label healthLabel, waterLevelLabel, attackStrengthLabel, movementRangeLabel, attackRangeLabel, hasMovedLabel, hasAttackedLabel;
    private Image fireTruckImgSingleNormal, fireTruckImgSingleBlue, etFortressImgSingle;


    private Image player;
    //Get textures for images
    private Texture playerIconTexture, fireTruckIconTexture, etFortressIconTexture, destroyedTexture;

    //Standard table size
    private int sizeX, sizeY;

    public int difficulty;

    //Relative positions for positioning of stats UI
    public int ftPosY, ftPosX, etPosY, etPosX, iniPosY, iniPosX;

    //Total entities
    private int numFireTruck, numFortress;


    //For colouring
    private TextureRegionDrawable textureRegionDrawableBg;

    public GameHud(SpriteBatch batch, Skin skin) {
        /**
         * Creates an instance of the Game Hud which is the object responsible for
         * initializing the UI stage, viewport, tables and non dynamic elements of the stage.
         * Then displays the tables on the stage
         * The stage handles all inputs from buttons and sets corresponding boolean expressions
         * @param batch The SpriteBatch used to draw all the UI to the screen
         * @param skin The skin used for styling scene2D components
         */
        //Set standard size
        sizeX = 140;
        sizeY = 80;

        //Set difficulty
        difficulty = 1;
        difficultyLabel = new Label("Difficulty Level: " + difficulty, skin);
        difficultyLabel.setPosition(60, 445);
        difficultyLabel.setColor(Color.BLACK);

        //Set player image
        playerIconTexture = new Texture(Gdx.files.internal("fox-1.png.png"));
        player = new Image(playerIconTexture);
        player.setPosition(10, 430);
        player.setScale(0.3f);

        //Assign textures and images
        fireTruckIconTexture = new Texture(Gdx.files.internal("fire engine 64 by 64-1.png.png"));
        etFortressIconTexture = new Texture(Gdx.files.internal("etFortress.png"));
        destroyedTexture = new Texture(Gdx.files.internal("Destroyed.png"));

        fireTruckImgSingleNormal = new Image(fireTruckIconTexture);
        fireTruckImgSingleBlue = new Image(fireTruckIconTexture);

        etFortressImgSingle = new Image(etFortressIconTexture);

        attackClicked = moveClicked = refillClicked = waitClicked = false;
        menuOpen = false;

        //Centre viewport, stretch needs to be used in later implementations
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply(true);

        stage = new Stage(viewport, batch);

        //Initialize buttons with action listeners
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

        containerMultiple = new Table();
        containerSingle = new Table();

        //Set up tables
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

        //Create dialog
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


        turnState = new Label("", skin);
        turnState.setAlignment(Align.center);

        turnStateTable = new Table();
        turnStateTable.setSize(sizeX, sizeY);
        turnStateTable.setBackground(skin.getDrawable("default-scroll"));
        turnStateTable.setPosition(Gdx.graphics.getWidth()-160, 20);

        turnStateTable.add(turnState);
        turnStateTable.row();
        turnStateTable.add(endTurn);

        //Add tables to stage
        stage.addActor(containerMultiple);
        stage.addActor(containerSingle);
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
        /**
         * Sets the orientation and position of the in game table
         * and displays it
         * Converts screen space to stage coordinates to apply to te table
         * @see stage#screenToStageCoordinates(Vector2)
         */

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
        /**
         * Set the visibility of a table to be false
         * and sets menuOpen to false signifying the in game menu is closed
         *@param table The table to be set
         */
        table.setVisible(false);
        menuOpen = false;
    }
    public void setVisibilityOfTable(Table table, boolean state) {
        /**
         * Set the visibility of a table
         *@param table The table to be set
         *@param state Determines if the table is visible (true) or invisible (false)
         */
        if(state) {
            table.setVisible(true);
        } else {
            table.setVisible(false);
        }
    }

    public void setDialog(final String voice, final String statement, float delayTime) {
        /**
         * Updates the dialog label with a new String
         * and sets the background of backgroundDialogTable depending on the voice parameter
         *@param voice The String representing a character talking
         *@param statement The String message to be displayed below voice
         *@param delayTime Displays the dialog label for a time before it changes in case of the method being called in succession
         *@return true if a click is in the table
         *@return false if a click is not in the table
         */
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
        /**
         * Creates a drawable texture that is a region of one color
         *@param color The colour of the texture
         *@return A new drawable texture
         */
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(color);
        bgPixmap.fill();
        return textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }

    public boolean clickInTable(Table table) {
        /**
         * Checks if a click is within a table
         *@param table The table to be checked
         *@return true if a click is in the table
         *@return false if a click is not in the table
         */
        Vector2 position = stage.screenToStageCoordinates( new Vector2(Gdx.input.getX(), Gdx.input.getY()) );

        if(position.x>= table.getX() && position.x<= (table.getX()+table.getWidth()) && position.y>= table.getY() && position.y<= (table.getY()+table.getHeight())) {

            return true;
        } else {
            return false;
        }

    }

    public void createFireTruckUI(Map map, Skin skin) {

        /**
         * Finds all instances of firetrucks and fortresses on the map.
         * Accesses each instances' health and water stats then
         * initializes the health and water labels of the onscreen UI table, containerMultiple for multiple objects.
         *@param map The rendered instance of the map object
         */

        int x = 0;
        int y = 0;

        int waterLevel;
        int firetruckHealth;
        int maxWaterLevel;
        int maxFireTruckHealth;

        int labelOffset = 30;

        //Initial positions
        ftPosY = Gdx.graphics.getHeight() - 40;
        ftPosX = Gdx.graphics.getWidth() - 240;

        //Initial positions for non-firetruck entity
        etPosY = ftPosY - 80;
        etPosX = ftPosX + 80;



        for (int i = 0; i < map.HEIGHT; i++)
        {
            for (int j = 0; j < map.WIDTH; j++)
            {
                if (map.getEntity(i, j) != null && map.getEntity(i, j) instanceof Firetruck)
                {
                    Firetruck f = (Firetruck) map.getEntity(i, j);

                    //Calls to reset stats at the start of game to get correct stats, quick fix
                    f.restock();

                    waterLevel = f.getWater();
                    maxWaterLevel = f.getMaxWater();
                    firetruckHealth = f.getHealth();
                    maxFireTruckHealth = f.getMaxHealth();

                    String stats = String.format("Health: %s/%s \nWater Level: %s/%s", firetruckHealth, maxFireTruckHealth ,waterLevel, maxWaterLevel);

                    fireTruckImg[x] = new Image(fireTruckIconTexture);
                    fireTruckImg[x].setPosition(ftPosX, ftPosY);
                    fireTruckImg[x].setScale(0.3f);

                    if(x == 1) {
                        fireTruckImg[x].setColor(Color.CYAN);
                    }
                    fireTruckStats[x] = new Label(stats, skin);

                    fireTruckStats[x].setPosition(ftPosX + labelOffset, ftPosY);
                    fireTruckStats[x].setSize(20, 20);
                    fireTruckStats[x].setFontScale(0.8f);
                    fireTruckStats[x].setColor(Color.BLACK);

                    containerMultiple.addActor(fireTruckImg[x]);
                    containerMultiple.addActor(fireTruckStats[x]);

                    //offset for next position
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

                        containerMultiple.addActor(etFortressImg[y]);
                        containerMultiple.addActor(etFortressStats[y]);

                        //offset for next position
                        etPosX += 30;
                        etPosY -= 30;
                        y++;
                    }
                }
            }
        }

    }
    public void setLabel(Label label, float positionX, float positionY, float height, float width, float scale, Color color) {
        /**
         *Sets the label parameters for a given label
         *@param  label The label to be set
         *@param  positionX The X coordinate of the label on the screen
         *@param  positionY The Y coordinate of the label on the screen
         *@param  height The height of the label
         *@param  width The width of the label
         *@param  scale Sets the scale of the X and Y
         *@param  color The colour of the text inside the label
         */
        label.setPosition(positionX, positionY);
        label.setSize(width, height);
        label.setScale(scale);
        label.setColor(color);
    }
    public void getFiretruckEntityStats(Skin skin) {
        /**
         *Initialize the labels and creates icons for the table containerSingle,
         *@param  skin An instance of the skin used for styling
         */
        iniPosY = Gdx.graphics.getHeight() - 40;
        iniPosX = Gdx.graphics.getWidth() - 240;


        fireTruckImgSingleNormal.setPosition(iniPosX, iniPosY);
        fireTruckImgSingleNormal.setScale(0.5f);
        fireTruckImgSingleBlue.setPosition(iniPosX, iniPosY);
        fireTruckImgSingleBlue.setScale(0.5f);


        iniPosY -= 20;
        iniPosX += 20;
        healthLabel = new Label("", skin);
        setLabel(healthLabel, iniPosX, iniPosY, 20, 20, 0.8f, Color.BLACK);
        containerSingle.addActor(healthLabel);

        iniPosY -= 15;
        iniPosX += 15;

        waterLevelLabel = new Label("", skin);
        setLabel(waterLevelLabel, iniPosX, iniPosY, 20, 20, 0.8f, Color.BLACK);
        containerSingle.addActor(waterLevelLabel);

        iniPosY -= 15;
        iniPosX += 15;

        attackStrengthLabel = new Label("", skin);
        setLabel(attackStrengthLabel, iniPosX, iniPosY, 20, 20, 0.8f, Color.BLACK);
        containerSingle.addActor(attackStrengthLabel);

        iniPosY -= 15;
        iniPosX += 15;

        attackRangeLabel = new Label("", skin);
        setLabel(attackRangeLabel, iniPosX, iniPosY, 20, 20, 0.8f, Color.BLACK);
        containerSingle.addActor(attackRangeLabel);

        iniPosY -= 15;
        iniPosX += 15;

        movementRangeLabel = new Label("", skin);
        setLabel(movementRangeLabel, iniPosX, iniPosY, 20, 20, 0.8f, Color.BLACK);
        containerSingle.addActor(movementRangeLabel);

        iniPosY -= 15;
        iniPosX += 15;

        stage.addActor(containerSingle);


    }

    public void updateEntityStats(Firetruck f) {

        /**
         * Gets an instance of a firetruck object and accesses all it's stats,
         * then updates all the labels of the onscreen UI table, containerSingle for a single object
         *@param f An instance of a firetruck object that is on the map
         */

        int movementDistance = f.getMovementDistance();
        int attackDistance = f.getAttackDistance();

        int attackStrength = f.getAttackStrength();

        int maxHealth = f.getMaxHealth();
        int health = f.getHealth();
        int maxWater = f.getMaxWater();
        int water = f.getWater();

        if(f.getTexture().toString() == "SpecialFiretrucks2.png") {
          fireTruckImgSingleBlue.setColor(Color.CYAN);
          containerSingle.addActor(fireTruckImgSingleBlue);
          containerSingle.removeActor(fireTruckImgSingleNormal);
        } else {
            containerSingle.removeActor(fireTruckImgSingleBlue);
            containerSingle.addActor(fireTruckImgSingleNormal);
        }

        String statH = String.format("Health: %s/%s", health, maxHealth);
        String statW = String.format("Water level: %s/%s", water, maxWater);
        String statAS = String.format("Attack strength: %s", attackStrength);
        String statAD = String.format("Attack range: %s", attackDistance);
        String statMD = String.format("Movement range: %s", movementDistance);


        healthLabel.setText(statH);
        waterLevelLabel.setText(statW);
        attackStrengthLabel.setText(statAS);
        attackRangeLabel.setText(statAD);
        movementRangeLabel.setText(statMD);

    }

    public void updateStatsUI(Map map) {
        /**
         * Finds all instances of firetrucks and fortresses on the map.
         * Accesses their health and water stats then
         * updates the health and water labels of the onscreen UI table, containerMultiple for multiple objects
         * Removes labels from the stage on death and updates sprite
         *@param map The rendered instance of the map object
         */
        int waterLevel;
        int firetruckHealth;
        int maxWaterLevel;
        int maxFireTruckHealth;
        int x = 0;
        int y = 0;

        numFireTruck = numFortress = 0;


        for (int i = 0; i < map.HEIGHT; i++)
        {
            for (int j = 0; j < map.WIDTH; j++)
            {
                if (map.getEntity(i, j) != null && map.getEntity(i, j) instanceof Firetruck)
                {
                    numFireTruck++;
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
                        numFortress++;
                        Fortress f = (Fortress) map.getEntity(i, j);
                        int fortressHealth = f.getHealth();

                        String stats = String.format("Health: %s \n", fortressHealth);

                        etFortressStats[y].setText(stats);

                        if(fortressHealth <= 0) {
                            etFortressImg[x].setDrawable(new SpriteDrawable(new Sprite(destroyedTexture)));
                            containerMultiple.removeActor(etFortressStats[x]);
                        }
                        y++;
                    }
                }

            }
        }
        for (int i = 0; i < map.HEIGHT; i++) {
            for (int j = 0; j < map.WIDTH; j++) {
                if (map.getEntity(i, j) != null && map.getEntity(i, j) instanceof DestroyedEntity) {
                    if (numFireTruck < 2) {
                        fireTruckImg[x].setDrawable(new SpriteDrawable(new Sprite(destroyedTexture)));
                        containerMultiple.removeActor(fireTruckStats[x]);
                        if (numFireTruck < 1) {

                        }
                    }
                    if (numFortress < 3) {
                        etFortressImg[y].setDrawable(new SpriteDrawable(new Sprite(destroyedTexture)));
                        containerMultiple.removeActor(etFortressStats[y]);
                        if (numFortress < 2) {
                            etFortressImg[x].setDrawable(new SpriteDrawable(new Sprite(destroyedTexture)));
                            containerMultiple.removeActor(etFortressStats[y]);
                        }
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
        /**
         * Updates the difficulty level label with the current difficulty
         *@param difficulty The current difficulty of the game
         */
        difficultyLabel.setText(String.format("Difficulty Level %d", difficulty));
    }

    public void dispose(){
        stage.dispose();

    }
}

