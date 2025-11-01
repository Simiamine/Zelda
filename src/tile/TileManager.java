package tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.GamePanel;
import main.GameConstants;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Gestionnaire des tuiles du jeu.
 * Charge les tuiles depuis un fichier de configuration et gère le rendu de la carte.
 */
public class TileManager {

    private static final Logger LOGGER = Logger.getLogger(TileManager.class.getName());
    private static final String TILES_CONFIG_PATH = "res/tiles_config.txt";

    private final int tileSize;
    private final GamePanel gamePanel;
    private Tile[] tiles;
    private int[][][] mapTileNum;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tileSize = GameConstants.TILE_SIZE;

        tiles = new Tile[GameConstants.MAX_TILES];
        mapTileNum = new int[GameConstants.MAX_MAP][GameConstants.WORLD_WIDTH][GameConstants.WORLD_HEIGHT];

        loadTileImages();
        loadMaps();
        
        LOGGER.info("TileManager initialisé avec succès");
    }

    /**
     * Charge les images des tuiles depuis le fichier de configuration.
     * Lit le fichier tiles_config.txt qui contient index,nom_fichier,collision
     */
    private void loadTileImages() {
        try (BufferedReader br = new BufferedReader(new FileReader(TILES_CONFIG_PATH))) {
            String line;
            int tilesLoaded = 0;
            
            while ((line = br.readLine()) != null) {
                // Ignorer les commentaires et les lignes vides
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        int index = Integer.parseInt(parts[0].trim());
                        String imagePath = parts[1].trim();
                        boolean collision = Boolean.parseBoolean(parts[2].trim());
                        
                        setup(index, imagePath, collision);
                        tilesLoaded++;
                    } catch (NumberFormatException e) {
                        LOGGER.warning("Ligne de configuration invalide : " + line);
                    }
                }
            }
            
            LOGGER.info(tilesLoaded + " tuiles chargées depuis " + TILES_CONFIG_PATH);
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement des tuiles : " + e.getMessage(), e);
        }
    }

    private void setup(int index, String imagePath, boolean collision) {
        try {
            tiles[index] = new Tile(
                new Image(GameConstants.TILE_DIRECTORY + imagePath), 
                collision
            );
        } catch (Exception e) {
            LOGGER.warning("Erreur lors du chargement de la tuile " + index + " : " + imagePath);
        }
    }

    private void loadMaps() {
        loadMap(GameConstants.MAP_SPAWN, 0);
        loadMap(GameConstants.MAP_HOUSE, 1);
    }

    /**
     * Charge une carte depuis un fichier texte.
     * Utilise try-with-resources pour une gestion appropriée des ressources.
     * 
     * @param fileName Chemin du fichier de carte
     * @param mapIndex Index de la carte à charger
     */
    private void loadMap(String fileName, int mapIndex) {
        try (FileInputStream fis = new FileInputStream(fileName);
             BufferedReader br = new BufferedReader(new java.io.InputStreamReader(fis))) {

            String line;
            int row = 0;

            while ((line = br.readLine()) != null && row < GameConstants.WORLD_HEIGHT) {
                String[] numbers = line.trim().split("\\s+");
                for (int col = 0; col < GameConstants.WORLD_WIDTH && col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[mapIndex][col][row] = num;
                }
                row++;
            }
            
            LOGGER.info("Carte chargée : " + fileName + " (index: " + mapIndex + ")");

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement de la carte : " + fileName, e);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Format de nombre invalide dans la carte : " + fileName, e);
        }
    }

    /**
     * Rend les tuiles de la carte actuelle à l'écran.
     * Optimise le rendu en n'affichant que les tuiles visibles.
     * 
     * @param gc Le contexte graphique JavaFX
     */
    public void render(GraphicsContext gc) {
        for (int col = 0; col < GameConstants.WORLD_WIDTH; col++) {
            for (int row = 0; row < GameConstants.WORLD_HEIGHT; row++) {
                int tileNum = mapTileNum[gamePanel.getCurrentMap()][col][row];
                int screenX = col * tileSize - gamePanel.getPlayer().worldX + gamePanel.getPlayer().screenX;
                int screenY = row * tileSize - gamePanel.getPlayer().worldY + gamePanel.getPlayer().screenY;

                // Optimisation : ne dessiner que les tuiles visibles
                if (screenX + tileSize > 0 && screenX < gamePanel.getWidth() && 
                    screenY + tileSize > 0 && screenY < gamePanel.getHeight()) {
                    
                    gc.drawImage(tiles[tileNum].getImage(), screenX, screenY, tileSize, tileSize);
                    
                    // Debug : afficher les collisions en rose
                    if (tiles[tileNum].collision) {
                        gc.setFill(Color.rgb(255, 0, 255, GameConstants.COLLISION_DEBUG_OPACITY));
                        gc.fillRect(screenX, screenY, tileSize, tileSize);
                    }
                }
            }
        }
    }

    public int[][][] getMapTileNum() {
        return mapTileNum;
    }

    public void setMapTileNum(int[][][] mapTileNum) {
        this.mapTileNum = mapTileNum;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }
}
