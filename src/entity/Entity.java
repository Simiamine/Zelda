package entity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Entity {

    public int worldX;
    public int worldY;
    public int speed;

    public Image up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public boolean collisionOn = false;
    
}