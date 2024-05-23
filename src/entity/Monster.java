package entity;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import object.OBJ_Heart;
import object.OBJ_Ruby;
import object.SuperObject;

import java.util.Random;

public class Monster extends Entity {

    // Points de vie du monstre
    private int health;
    
    // Image représentant le monstre
    private Image monsterImage;

    // Constructeur de la classe Monster
    public Monster(GamePanel gPanel) {
        super(gPanel);

        // Initialisation des propriétés du monstre
        direction = "DOWN";
        speed = 1;
        health = 30; // Le monstre commence avec 30 points de vie

        // Initialisation de la zone solide
        int tileSize = GamePanel.getTileSize();
        solidArea = new Rectangle(0, 0, tileSize, tileSize);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        collisionOn = true; // Collision activée par défaut
        isSolid = true; // Le monstre est solide par défaut

        // Chargement de l'image du monstre
        getImage();
    }

    // Méthode pour charger l'image du monstre
    private void getImage() {
        try {
            monsterImage = new Image("file:res/monster/monster.png"); // Assurez-vous que l'image existe à cet emplacement
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour recevoir des dégâts
    public void receiveDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            dropItem();
            // Retirer le monstre du monde du jeu
            gPanel.monsters.remove(this);
        }
    }

    // Méthode pour faire tomber un objet lorsque le monstre est vaincu
    private void dropItem() {
        Random rand = new Random();
        SuperObject drop;

        if (rand.nextBoolean()) {
            drop = new OBJ_Ruby();
        } else {
            drop = new OBJ_Heart();
        }

        // Positionner l'objet à l'endroit où le monstre a été vaincu
        drop.worldX = worldX;
        drop.worldY = worldY;

        // Ajouter l'objet au monde du jeu
        int index = gPanel.findEmptyObjectIndex();
        if (index != -1) {
            gPanel.obj[index] = drop;
        } else {
            // Si aucun emplacement vide n'est trouvé, agrandir le tableau
            gPanel.obj = expandArray(gPanel.obj);
            gPanel.obj[gPanel.obj.length - 1] = drop;
        }
    }

    // Méthode pour agrandir le tableau d'objets
    private SuperObject[] expandArray(SuperObject[] array) {
        SuperObject[] newArray = new SuperObject[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    // Méthode pour mettre à jour l'état du monstre (à compléter si nécessaire)
    public void update() {
        // Ajouter la logique de mouvement si nécessaire
    }

    // Méthode pour dessiner le monstre à l'écran
    public void render(GraphicsContext gc) {
        // Calculer les coordonnées de l'écran en fonction de la position du joueur
        int screenX = worldX - gPanel.player.worldX + gPanel.player.screenX;
        int screenY = worldY - gPanel.player.worldY + gPanel.player.screenY;

        // Dessiner l'image du monstre si elle est chargée
        if (monsterImage != null) {
            gc.drawImage(monsterImage, screenX, screenY, GamePanel.getTileSize(), GamePanel.getTileSize());
        }
    }
}
