package main;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    public List<Monster> monsters = new ArrayList<>(); // Ajout de la liste de monstres

    public CollisionChecker cChecker = new CollisionChecker(this);
    
    Sound sound = new Sound();
    public List<NPC> npcs = new ArrayList<>();

    // GAME STATES
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    
    private Image rubyImage;
    private Image heartImage;
    private Image heartEmptyImage;
    private Font customFont;

    public GamePanel() {
        super(SCREEN_WIDTH, SCREEN_HEIGHT);
        gc = getGraphicsContext2D();
        gc.setImageSmoothing(false);
        setFocusTraversable(true);
        setOnKeyPressed(getInputHandler()::handleKeyPressed);
        setOnKeyReleased(getInputHandler()::handleKeyReleased);
        loadUIImages();
        loadCustomFont();
    }

    private void loadUIImages() {
        rubyImage = new Image("file:res/ui/ruby.png");
        heartImage = new Image("file:res/ui/heart.png");
        heartEmptyImage = new Image("file:res/ui/heart_empty.png");
    }

    private void loadCustomFont() {
        try {
            customFont = Font.loadFont("file:res/font/font.ttf", 30);
        } catch (Exception e) {
            e.printStackTrace();
            customFont = Font.font("Verdana", FontWeight.BOLD, 30); // Fallback font
        }
    }

    public void setupGame() {
        aSetter.setObject();
        npcSetter.setNPCs(); // Ajouter les NPCs
        addMonster(new Monster(this), 10, 10); // Ajouter un monstre à la position (10, 10)
        playMusic(1);
        gameState = playState;
    }

    public void addMonster(Monster monster, int col, int row) {
        monster.worldX = col * GamePanel.getTileSize();
        monster.worldY = row * GamePanel.getTileSize();
        monsters.add(monster);
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
            for (Monster monster : monsters) {
                monster.update();
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

        for (Monster monster : monsters) {
            monster.render(gc);
        }

        player.render(gc);

        renderPlayerStats();
    }

    private void renderPlayerStats() {
        gc.setFont(customFont);
        gc.setFill(Color.BLACK);

        // Draw rubies
        gc.drawImage(rubyImage, 20, 20, 32, 32);
        gc.fillText(String.valueOf(player.getRubies()), 60, 45);

        // Draw hearts
        for (int i = 0; i < player.maxHearts; i++) {
            if (i < player.getHearts()) {
                gc.drawImage(heartImage, 20 + (i * 40), 60, 32, 32);
            } else {
                gc.drawImage(heartEmptyImage, 20 + (i * 40), 60, 32, 32);
            }
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

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public int findEmptyObjectIndex() {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public void resetGame() {
        player.setDefaultValues();
        monsters.clear();
        npcSetter.setNPCs(); // Réinitialiser les NPCs
        addMonster(new Monster(this), 10, 10); // Ajouter un monstre à la position (10, 10)
        gameState = playState;
    }
}
