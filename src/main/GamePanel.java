package main;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import entity.Player;
import entity.NPC;
import object.SuperObject;
import tile.TileManager;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends Canvas {

    // SCREEN SETTINGS
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int scale = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * scale;

    public static int getScale() {
        return scale;
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }

    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 14;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    // WORLD SETTINGS
    public final int MAX_WORLD_COL = 32;
    public final int MAX_WORLD_ROW = 32;
    public final int WORLD_WIDTH = MAX_WORLD_COL * 16;
    public final int WORLD_HEIGHT = MAX_WORLD_ROW * 16;

    private GraphicsContext gc;
    private AnimationTimer gameLoop;

    private InputHandler inputHandler = new InputHandler();

    public Player player = new Player(this);

    TileManager tileManager = new TileManager(this);

    public SuperObject[] obj = new SuperObject[10];
    public AssetSetter aSetter = new AssetSetter(this);
    public NPCSetter npcSetter = new NPCSetter(this);

    public CollisionChecker cChecker = new CollisionChecker(this);
    
    Sound sound = new Sound();
    public List<NPC> npcs = new ArrayList<>();

    
    // GAME STATES
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    
    public GamePanel() {
        super(SCREEN_WIDTH, SCREEN_HEIGHT);
        gc = getGraphicsContext2D();
        gc.setImageSmoothing(false);
        setFocusTraversable(true);
        setOnKeyPressed(getInputHandler()::handleKeyPressed);
        setOnKeyReleased(getInputHandler()::handleKeyReleased);
    }

    public void setupGame() {
        aSetter.setObject();
        npcSetter.setNPCs(); // Ajouter les NPCs
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
    	if(gameState == playState) {
            player.update(getInputHandler().getInputList());
            for (NPC npc : npcs) {
                npc.update();
    	}
            if(gameState == pauseState) {
            	
            }
        }
    }

    private void render() {
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());

        tileManager.render(gc);

        for (SuperObject obj : obj) {
            if (obj != null) {
                obj.render(gc, this);
            }
        }

        for (NPC npc : npcs) {
            npc.render(gc);
        }

        player.render(gc);
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

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }
}
