package entity;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import main.GameConstants;
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

        int tileSize = GameConstants.TILE_SIZE;
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
            gPanel.removeMonster(this);
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
        if (index != GameConstants.NO_OBJECT_FOUND) {
            gPanel.setObject(index, drop);
        }
    }

    public void update() {
        if (attackCooldownCounter > 0) {
            attackCooldownCounter--;
        }
        move();
        checkPlayerCollision(gPanel.getPlayer());
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
        int monsterRightX = worldX + GameConstants.TILE_SIZE;
        int monsterTopY = worldY;
        int monsterBottomY = worldY + GameConstants.TILE_SIZE;

        int entityLeftX = entity.worldX;
        int entityRightX = entity.worldX + GameConstants.TILE_SIZE;
        int entityTopY = entity.worldY;
        int entityBottomY = entity.worldY + GameConstants.TILE_SIZE;

        return monsterRightX > entityLeftX && monsterLeftX < entityRightX && monsterBottomY > entityTopY && monsterTopY < entityBottomY;
    }

    public abstract void getImage();

    public void render(GraphicsContext gc) {
        int screenX = worldX - gPanel.getPlayer().worldX + gPanel.getPlayer().screenX;
        int screenY = worldY - gPanel.getPlayer().worldY + gPanel.getPlayer().screenY;

        if (monsterImage != null) {
            gc.drawImage(monsterImage, screenX, screenY, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        }

        renderHealthBar(gc, screenX, screenY);
    }

    private void renderHealthBar(GraphicsContext gc, int screenX, int screenY) {
        double healthBarWidth = GameConstants.TILE_SIZE * (health / (double) maxHealth);

        gc.setFill(Color.RED);
        gc.fillRect(screenX, screenY - 10, GameConstants.TILE_SIZE, 5); // Background (empty health bar)

        gc.setFill(Color.BLUE);
        gc.fillRect(screenX, screenY - 10, healthBarWidth, 5); // Foreground (current health)
    }
}
