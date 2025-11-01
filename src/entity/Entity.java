package entity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import main.GameConstants;

public class Entity {

    protected GamePanel gPanel;

    public int worldX;
    public int worldY;
    public int mapIndex;
    public int speed;

    public Inventory inventory;

    public Image up1, up2, down1, down2, left1, left2, right1, right2;

    public String direction;

    public int actionLockCounter = 0;
    
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public boolean collisionOn = true;
    public boolean isSolid = true;

    public Entity(GamePanel gPanel) {
        this.gPanel = gPanel;
        solidArea = new Rectangle(0, 0, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        inventory = new Inventory();
    }
}
