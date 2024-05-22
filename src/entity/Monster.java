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

    private int health;
    private Image monsterImage;

    public Monster(GamePanel gPanel) {
        super(gPanel);
        direction = "DOWN";
        speed = 1;
        health = 30; // Monster starts with 3 health points

        int tileSize = GamePanel.getTileSize();
        solidArea = new Rectangle(0, 0, tileSize, tileSize);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        collisionOn = true; // Activer la collision par défaut
        isSolid = true; // Monster est solide par défaut

        getImage();
    }

    private void getImage() {
        try {
            monsterImage = new Image("file:res/monster/monster.png"); // Assurez-vous que l'image existe à cet emplacement
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            dropItem();
            // Remove the monster from the game world
            gPanel.monsters.remove(this);
        }
    }

    private void dropItem() {
        Random rand = new Random();
        SuperObject drop;

        if (rand.nextBoolean()) {
            drop = new OBJ_Ruby();
        } else {
            drop = new OBJ_Heart();
        }

        drop.worldX = worldX;
        drop.worldY = worldY;

        // Add the drop to the game world
        int index = gPanel.findEmptyObjectIndex();
        if (index != -1) {
            gPanel.obj[index] = drop;
        } else {
            // If no empty slot is found, expand the array
            gPanel.obj = expandArray(gPanel.obj);
            gPanel.obj[gPanel.obj.length - 1] = drop;
        }
    }

    private SuperObject[] expandArray(SuperObject[] array) {
        SuperObject[] newArray = new SuperObject[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    public void update() {
        // Add movement logic if needed
    }

    public void render(GraphicsContext gc) {
        int screenX = worldX - gPanel.player.worldX + gPanel.player.screenX;
        int screenY = worldY - gPanel.player.worldY + gPanel.player.screenY;

        if (monsterImage != null) {
            gc.drawImage(monsterImage, screenX, screenY, GamePanel.getTileSize(), GamePanel.getTileSize());
        }
    }
}
