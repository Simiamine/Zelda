package entity;

import javafx.scene.image.Image;
import main.GamePanel;

public class Monster_Goblin extends Monster {

    public Monster_Goblin(GamePanel gPanel) {
        super(gPanel);
        direction = "LEFT";
        speed = 2;
        health = 20;
        getImage();
    }

    @Override
    public void getImage() {
        try {
            monsterImage = new Image("file:res/monster/goblin.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void move() {
//        if ("LEFT".equals(direction)) {
//            worldX -= speed;
//            if (worldX < 0) direction = "RIGHT";
//        } else if ("RIGHT".equals(direction)) {
//            worldX += speed;
//            if (worldX > gPanel.WORLD_WIDTH - GamePanel.getTileSize()) direction = "LEFT";
//        }
    }
}
