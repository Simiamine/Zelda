package main;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import object.SuperObject;
import tile.TileManager;
import entity.Player;



public class GamePanel extends Canvas {
	
    // SCREEN SETTINGS
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int scale = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE*scale;
    public static int getScale() {
        return scale;
    }
    public static int getTileSize() {
        return TILE_SIZE;
    }
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 14;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL ; // 256*3 pixels
    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }    
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW ; // 224*3 pixels
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

    private ArrayList<String> inputList = new ArrayList<String>();

    public Player player = new Player(this);

    TileManager tileManager = new TileManager(this);

    public SuperObject obj[] = new SuperObject[10];
    public AssetSetter aSetter = new AssetSetter(this);

    public CollisionChecker cChecker = new CollisionChecker(this);
    
    Sound sound = new Sound();

    public GamePanel() {
        super(SCREEN_WIDTH, SCREEN_HEIGHT);
        gc = getGraphicsContext2D();
        gc.setImageSmoothing(false);
        // set up event handlers for key presses and releases
        setFocusTraversable(true); // enable keyboard input for the canvas
        setOnKeyPressed(this::handleKeyPressed);
        setOnKeyReleased(this::handleKeyReleased);
    }

    private void handleKeyPressed(KeyEvent event) {
        String keyName = event.getCode().toString();
        if (!inputList.contains(keyName)) {
            inputList.add(keyName);
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        String keyName = event.getCode().toString();
        inputList.remove(keyName);
    }

    public void setupGame() {
        aSetter.setObject();
        System.out.println(javafx.scene.media.Media.class);
        playMusic(0);
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
        player.update(inputList);
    }

    private void render() {
        // clear canvas
        gc.clearRect(0, 0, getWidth(), getHeight());

        // draw background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());

        tileManager.render(gc);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].render(gc, this);
            }
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
    	sound.stop();
    }
    
}