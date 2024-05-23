package entity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;

public class Entity {

    // Référence au panneau de jeu pour accéder à des méthodes et des propriétés communes
    GamePanel gPanel;

    // Position de l'entité dans le monde (coordonnées X et Y)
    public int worldX;
    public int worldY;
    
    // Vitesse de déplacement de l'entité
    public int speed;

    // Inventaire de l'entité pour stocker les objets
    public Inventory inventory;

    // Images pour les différentes directions de déplacement
    public Image up1, up2, down1, down2, left1, left2, right1, right2;

    // Direction actuelle de l'entité (UP, DOWN, LEFT, RIGHT)
    public String direction;

    // Compteurs pour gérer les animations de sprite
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Zone solide pour gérer les collisions
    public Rectangle solidArea;

    // Positions par défaut de la zone solide
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    // Indicateurs pour gérer les collisions
    public boolean collisionOn = false;
    public boolean isSolid = false;

    // Constructeur de la classe Entity
    public Entity(GamePanel gPanel) {
        // Initialisation de la référence au panneau de jeu
        this.gPanel = gPanel;
        
        // Taille de la tuile du jeu (utilisée pour dimensionner la zone solide)
        int tileSize = GamePanel.getTileSize();

        // Initialisation de la zone solide à la taille de la tuile
        solidArea = new Rectangle(0, 0, tileSize, tileSize);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;

        // Initialisation de l'inventaire
        inventory = new Inventory();
    }
}
