package entity;

import javafx.scene.image.Image;
import main.GamePanel;

import java.util.Random;

public class Monster_Goron extends Monster {

    public Monster_Goron(GamePanel gPanel) {
        super(gPanel);
        direction = "DOWN";
        speed = 1;
        health = 30;
        getImage();
    }

    @Override
    public void getImage() {
        try {
            monsterImage = new Image("file:res/monster/monster.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void move() {

        actionLockCounter++;
        
        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up number between 1 to 100
    
            if (i <= 25) {
                direction = "UP";
            }
            if (i > 25 && i <= 50) {
                direction = "DOWN";
            }
            if (i > 50 && i <= 75) {
                direction = "LEFT";
            }
            if (i > 75 && i <= 100) {
                direction = "RIGHT";
            }

            actionLockCounter = 0;
        }
    }
}
