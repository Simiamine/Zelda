package main;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import entity.Player;
import entity.NPC;
import entity.NPC_Merchant;
import entity.Monster;
import object.SuperObject;
import tile.TileManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Panneau principal du jeu gérant la boucle de jeu, le rendu et les états.
 * Cette classe centralise la logique du jeu et coordonne les différents systèmes.
 */
public class GamePanel extends Canvas {

    private static final Logger LOGGER = Logger.getLogger(GamePanel.class.getName());
    
    // DEBUG
    private boolean showDebugText = false;

    // Entités et systèmes du jeu (encapsulation)
    private Player player;
    private InputHandler inputHandler;
    private TileManager tileManager;
    private SuperObject[] objects;
    private AssetSetter assetSetter;
    private NPCSetter npcSetter;
    private MonsterSetter monsterSetter;
    private final List<Monster> monsters;
    private CollisionChecker collisionChecker;
    private Sound sound;
    private final List<NPC> npcs;
    private Event event;
    private UI ui;
    
    // Configuration du monde
    private int currentMap;
    
    // Contexte graphique et boucle de jeu
    private GraphicsContext gc;
    private AnimationTimer gameLoop;

    // État du jeu
    private int gameState;
    private boolean hasWon;
    
    // Référence au marchand (pour le système de commerce)
    private NPC_Merchant merchant; 


    public GamePanel() {
        super(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
        
        // Initialisation des listes
        this.monsters = new ArrayList<>();
        this.npcs = new ArrayList<>();
        
        // Initialisation des systèmes
        this.inputHandler = new InputHandler(this);
        this.player = new Player(this, inputHandler);
        this.gc = getGraphicsContext2D();
        this.ui = new UI(this);
        this.tileManager = new TileManager(this);
        this.objects = new SuperObject[GameConstants.INITIAL_OBJECT_ARRAY_SIZE];
        this.assetSetter = new AssetSetter(this);
        this.npcSetter = new NPCSetter(this);
        this.monsterSetter = new MonsterSetter(this);
        this.collisionChecker = new CollisionChecker(this);
        this.sound = new Sound();
        this.event = new Event(this);
        
        // Configuration graphique
        gc.setImageSmoothing(false);
        setFocusTraversable(true);
        setOnKeyPressed(inputHandler::handleKeyPressed);
        setOnKeyReleased(inputHandler::handleKeyReleased);
        
        // État initial
        this.currentMap = GameConstants.PLAYER_START_MAP;
        this.hasWon = false;
        
        LOGGER.info("GamePanel initialisé avec succès");
    }
    
    // ==================== GETTERS ====================
    
    public Player getPlayer() {
        return player;
    }
    
    public InputHandler getInputHandler() {
        return inputHandler;
    }
    
    public TileManager getTileManager() {
        return tileManager;
    }
    
    public SuperObject[] getObjects() {
        return objects;
    }
    
    public SuperObject getObject(int index) {
        if (index >= 0 && index < objects.length) {
            return objects[index];
        }
        return null;
    }
    
    public void setObject(int index, SuperObject object) {
        if (index >= 0 && index < objects.length) {
            objects[index] = object;
        }
    }
    
    public List<Monster> getMonsters() {
        return monsters;
    }
    
    public void addMonster(Monster monster) {
        monsters.add(monster);
    }
    
    public void removeMonster(Monster monster) {
        monsters.remove(monster);
    }
    
    public void clearMonsters() {
        monsters.clear();
    }
    
    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }
    
    public List<NPC> getNpcs() {
        return npcs;
    }
    
    public void addNPC(NPC npc) {
        npcs.add(npc);
    }
    
    public void removeNPC(NPC npc) {
        npcs.remove(npc);
    }
    
    public void clearNPCs() {
        npcs.clear();
    }
    
    public void addNpc(NPC npc) {
        npcs.add(npc);
    }
    
    public Event getEvent() {
        return event;
    }
    
    public UI getUI() {
        return ui;
    }
    
    public int getCurrentMap() {
        return currentMap;
    }
    
    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
        LOGGER.fine("Carte changée vers : " + currentMap);
    }
    
    public int getGameState() {
        return gameState;
    }
    
    public void setGameState(int gameState) {
        int oldState = this.gameState;
        this.gameState = gameState;
        LOGGER.fine("État du jeu changé de " + oldState + " vers " + gameState);
    }
    
    public NPC_Merchant getMerchant() {
        return merchant;
    }
    
    public void setMerchant(NPC_Merchant merchant) {
        this.merchant = merchant;
    }
    
    public AssetSetter getAssetSetter() {
        return assetSetter;
    }
    
    public NPCSetter getNpcSetter() {
        return npcSetter;
    }
    
    public MonsterSetter getMonsterSetter() {
        return monsterSetter;
    }

    public void setupGame() {
        assetSetter.setObject();
        npcSetter.setNPCs();
        monsterSetter.setMonsters();
        setupTeleportationSquares();
        playMusic(1);
        gameState = GameConstants.GAME_STATE_PLAY;
        LOGGER.info("Jeu configuré et prêt à démarrer");
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
        if (gameState == GameConstants.GAME_STATE_PLAY) {
            player.update(inputHandler.getInputList());
            
            // Mise à jour des NPCs de la carte actuelle
            for (NPC npc : npcs) {
                if (npc.mapIndex == currentMap) {
                    npc.update();
                }
            }
            
            // Mise à jour des monstres de la carte actuelle
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
        // Effacer l'écran
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());

        // Rendu des tuiles
        tileManager.render(gc);

        // Rendu des objets de la carte actuelle
        for (SuperObject object : objects) {
            if (object != null && object.mapIndex == currentMap) {
                object.render(gc, this);
            }
        }

        // Rendu des NPCs de la carte actuelle
        for (NPC npc : npcs) {
            if (npc.mapIndex == currentMap) {
                npc.render(gc);
            }
        }

        // Rendu des monstres de la carte actuelle
        for (Monster monster : monsters) {
            if (monster.mapIndex == currentMap) {
                monster.render(gc);
            }
        }

        // Rendu du joueur
        player.render(gc);
        
        // Rendu des zones de téléportation (debug)
        event.renderTeleportationSquares(gc);

        // Rendu de l'interface selon l'état du jeu
        if (gameState == GameConstants.GAME_STATE_PLAY) {
            ui.renderPlayerStats(player);
        } else if (gameState == GameConstants.GAME_STATE_INVENTORY) {
            ui.renderInventory(player);
            ui.renderPlayerStats(player);
        } else if (gameState == GameConstants.GAME_STATE_DIALOGUE) {
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
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                return i;
            }
        }
        return GameConstants.NO_OBJECT_FOUND;
    }
    
    public void removeObject(SuperObject object) {
        for (int i = 0; i < this.objects.length; i++) {
            if (this.objects[i] == object) {
                this.objects[i] = null;
                LOGGER.fine("Objet supprimé à l'index : " + i);
                break;
            }
        }
    }

    public void resetGame() {
        player.setDefaultValues();
        monsters.clear();
        npcSetter.setNPCs();
        monsterSetter.setMonsters();
        gameState = GameConstants.GAME_STATE_PLAY;
        hasWon = false;
        LOGGER.info("Jeu réinitialisé");
    }
    
    public void setVictoryCondition() {
        hasWon = true;
        checkVictoryConditions();
    }
    
    private void showVictoryDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Victoire");
            alert.setHeaderText(null);
            alert.setContentText("Vous avez gagné ! Voulez-vous :");

            ButtonType quitButton = new ButtonType("Quitter");
            ButtonType replayButton = new ButtonType("Relancer");

            alert.getButtonTypes().setAll(quitButton, replayButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == quitButton) {
                    LOGGER.info("Sortie du jeu après victoire");
                    Platform.exit();
                } else if (result.get() == replayButton) {
                    LOGGER.info("Relance du jeu après victoire");
                    resetGame();
                }
            }
        });
    }
        
    private void checkVictoryConditions() {
        if (hasWon) {
            showVictoryDialog();
        }
    }

    private void checkFailureConditions() {
        if (player.getHearts() <= 0) {
            LOGGER.warning("Game Over - le joueur n'a plus de points de vie");
            gameState = GameConstants.GAME_STATE_DIALOGUE;
        }
    }
    
    /**
     * Gère l'interaction entre le joueur et un PNJ.
     * Cette méthode centralise toute la logique d'interaction pour éviter la duplication.
     * 
     * @param npc Le PNJ avec lequel interagir
     */
    public void interactWithNPC(NPC npc) {
        if (npc == null) {
            return;
        }
        
        // Cas spécial : marchand
        if (npc instanceof NPC_Merchant) {
            NPC_Merchant merchant = (NPC_Merchant) npc;
            merchant.interact();
            return;
        }
        
        // Interaction standard : dialogue
        String dialogue = npc.speak();
        if (!dialogue.isEmpty()) {
            ui.setCurrentDialogue(dialogue);
            gameState = GameConstants.GAME_STATE_DIALOGUE;
        } else {
            gameState = GameConstants.GAME_STATE_PLAY;
        }
        
        // Transfert d'objet du NPC vers le joueur (si disponible)
        if (!npc.inventory.getItems().isEmpty()) {
            SuperObject item = npc.inventory.getItems().get(0);
            npc.inventory.removeItem(item);
            player.inventory.addItem(item);
            LOGGER.info("Objet reçu : " + item.name + " de " + npc.getClass().getSimpleName());
        }
    }
    private void setupTeleportationSquares() {
        event.addTeleportationSquare(1, 6, 9, 2, 1, 0, 11, 17);
        event.addTeleportationSquare(0, 11, 15, 2, 1, 1, 6, 9);

    }
}
