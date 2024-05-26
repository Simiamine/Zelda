package entity;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import object.OBJ_Heart;
import object.OBJ_Ruby;
import object.SuperObject;

import java.util.Random;

public abstract class Monster extends Entity {

    protected int health;
    protected int maxHealth;
    protected Image monsterImage;
    protected int attackCooldown;
    protected int attackCooldownCounter;

    public Monster(GamePanel gPanel) {
        super(gPanel);
        direction = "DOWN";
        speed = 1;
        health = maxHealth = 30;

        int tileSize = GamePanel.getTileSize();
        solidArea = new Rectangle(0, 0, tileSize, tileSize);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        collisionOn = true;
        isSolid = true;

        attackCooldown = 120;
        attackCooldownCounter = 0;
    }

    public void receiveDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            dropItem();
            gPanel.monsters.remove(this);
        }
    }

    public void dropItem() {
        Random rand = new Random();
        SuperObject drop;

        if (rand.nextBoolean()) {
            drop = new OBJ_Ruby();
        } else {
            drop = new OBJ_Heart();
        }

        drop.worldX = worldX;
        drop.worldY = worldY;

        int index = gPanel.findEmptyObjectIndex();
        if (index != -1) {
            gPanel.obj[index] = drop;
        } else {
            gPanel.obj = expandArray(gPanel.obj);
            gPanel.obj[gPanel.obj.length - 1] = drop;
        }
    }

    protected SuperObject[] expandArray(SuperObject[] array) {
        SuperObject[] newArray = new SuperObject[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    public void update() {
        if (attackCooldownCounter > 0) {
            attackCooldownCounter--;
        }
        move();
        checkPlayerCollision(gPanel.player);
    }

    protected abstract void move();

    public void checkPlayerCollision(Player player) {
        if (isNear(player) && attackCooldownCounter == 0) {
            player.takeDamage(1);
            attackCooldownCounter = attackCooldown;
        }
    }

    protected boolean isNear(Entity entity) {
        int monsterLeftX = worldX;
        int monsterRightX = worldX + GamePanel.getTileSize();
        int monsterTopY = worldY;
        int monsterBottomY = worldY + GamePanel.getTileSize();

        int entityLeftX = entity.worldX;
        int entityRightX = entity.worldX + GamePanel.getTileSize();
        int entityTopY = entity.worldY;
        int entityBottomY = entity.worldY + GamePanel.getTileSize();

        return monsterRightX > entityLeftX && monsterLeftX < entityRightX && monsterBottomY > entityTopY && monsterTopY < entityBottomY;
    }

    public abstract void getImage();

    public void render(GraphicsContext gc) {
        int screenX = worldX - gPanel.player.worldX + gPanel.player.screenX;
        int screenY = worldY - gPanel.player.worldY + gPanel.player.screenY;

        if (monsterImage != null) {
            gc.drawImage(monsterImage, screenX, screenY, GamePanel.getTileSize(), GamePanel.getTileSize());
        }

        renderHealthBar(gc, screenX, screenY);
    }

    private void renderHealthBar(GraphicsContext gc, int screenX, int screenY) {
        double healthBarWidth = GamePanel.getTileSize() * (health / (double) maxHealth);

        gc.setFill(Color.RED);
        gc.fillRect(screenX, screenY - 10, GamePanel.getTileSize(), 5); // Background (empty health bar)

        gc.setFill(Color.BLUE);
        gc.fillRect(screenX, screenY - 10, healthBarWidth, 5); // Foreground (current health)
    }
}
