package main;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import entity.Player;
import entity.NPC;
import entity.Monster;
import object.SuperObject;
import tile.TileManager;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends Canvas {

    // SCREEN SETTINGS
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int scale = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * scale;
    
    //DEBUG
    boolean showDebugText = false;

    public Player player;
    public static int getScale() { return scale; }
    public static int getTileSize() { return TILE_SIZE; }
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 14;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static int getScreenWidth() { return SCREEN_WIDTH; }
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    public static int getScreenHeight() { return SCREEN_HEIGHT; }

    // WORLD SETTINGS
    public final int MAX_WORLD_COL = 32;
    public final int MAX_WORLD_ROW = 32;
    public final int WORLD_WIDTH = MAX_WORLD_COL * 16;
    public final int WORLD_HEIGHT = MAX_WORLD_ROW * 16;
    public final int maxMap = 10;
    public int currentMap = 0;

    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    public InputHandler inputHandler;
    TileManager tileManager = new TileManager(this);
    public SuperObject[] obj = new SuperObject[10];
    public AssetSetter aSetter = new AssetSetter(this);
    public NPCSetter npcSetter = new NPCSetter(this);
    public MonsterSetter monsterSetter = new MonsterSetter(this);
    public List<Monster> monsters = new ArrayList<>();
    public CollisionChecker cChecker = new CollisionChecker(this);
    Sound sound = new Sound();
    public List<NPC> npcs = new ArrayList<>();
    public Event event = new Event(this);

    // GAME STATES
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int inventoryState = 5;
    public final int commerceState = 6;

    public UI ui;

    // Victory and failure conditions
    private boolean hasWon = false;

    public GamePanel() {
        super(SCREEN_WIDTH, SCREEN_HEIGHT);
        inputHandler = new InputHandler(this);
        player = new Player(this, inputHandler);
        gc = getGraphicsContext2D();
        ui = new UI(this);
        gc.setImageSmoothing(false);
        setFocusTraversable(true);
        setOnKeyPressed(inputHandler::handleKeyPressed);
        setOnKeyReleased(inputHandler::handleKeyReleased);
    }

    public void setupGame() {
        aSetter.setObject();
        npcSetter.setNPCs();
        monsterSetter.setMonsters();
        setupTeleportationSquares();
        playMusic(1);
        gameState = playState;
    }

    public void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        };
        gameLoop.start();
    }

    private void update() {
        if (gameState == playState) {
            player.update(inputHandler.getInputList());
            for (NPC npc : npcs) {
                if (npc.mapIndex == currentMap) {
                    npc.update();
                }
            }
            for (Monster monster : monsters) {
            	if (monster.mapIndex == currentMap) {
                    monster.update();
                }
            }
            event.checkTeleportation();
            checkVictoryConditions();
            checkFailureConditions();
        }
    }

    private void render() {
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());

        tileManager.render(gc);

        for (SuperObject obj : obj) {
            if (obj != null && obj.mapIndex == currentMap) {
                obj.render(gc, this);
            }
        }

        for (NPC npc : npcs) {
            if (npc.mapIndex == currentMap) {
                npc.render(gc);
            }
        }

        for (Monster monster : monsters) {
            if (monster.mapIndex == currentMap) {
                monster.render(gc);
            }
        }

        player.render(gc);
        
        event.renderTeleportationSquares(gc);

        if (gameState == playState) {
            ui.renderPlayerStats(player);
        } else if (gameState == inventoryState) {
            ui.renderInventory(player);
            ui.renderPlayerStats(player);
        } else if (gameState == dialogueState) {
            ui.drawDialogueScreen();
            ui.renderPlayerStats(player);
        } 
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public int findEmptyObjectIndex() {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] == null) {
                return i;
            }
        }
        return -1;
    }
    public void removeObject(SuperObject obj) {
        for (int i = 0; i < this.obj.length; i++) {
            if (this.obj[i] == obj) {
                this.obj[i] = null;
                break;
            }
        }
    }

    public void resetGame() {
        player.setDefaultValues();
        monsters.clear();
        npcSetter.setNPCs();
        monsterSetter.setMonsters();
        gameState = playState;
    }
    
    public void setVictoryCondition() {
        hasWon = true;
        checkVictoryConditions();
    }
    
    private void checkVictoryConditions() {
        if (hasWon) {
            System.out.println("You win!");
            gameState = dialogueState;
            ui.currentDialogue = "Congratulations! You have obtained the Triforce!";
            ui.drawDialogueScreen();
            
        }
    }

    private void checkFailureConditions() {
        if (player.getHearts() <= 0) {
            System.out.println("Game Over");
            gameState = dialogueState;
        }
    }
    private void setupTeleportationSquares() {
        event.addTeleportationSquare(1, 6, 9, 2, 1, 0, 11, 17);
        event.addTeleportationSquare(0, 11, 15, 2, 1, 1, 6, 9);

    }
}
