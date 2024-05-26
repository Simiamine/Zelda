package entity;

import javafx.scene.image.Image;
import main.GamePanel;
import object.OBJ_Bomb;
import object.SuperObject;

import java.util.Random;

public class Monster_Goron extends Monster {

    public Monster_Goron(GamePanel gPanel) {
        super(gPanel);
        direction = "DOWN";
        speed = 1;
        health = 30;
        getImage();

        // Ajouter une bombe à l'inventaire du Goron
        inventory.addItem(new OBJ_Bomb());
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

    @Override
    public void update() {
        super.update();
        if (health < 15 && !inventory.getItems().isEmpty()) {
            useBomb();
        }
    }

    private void useBomb() {
        // Utiliser la première bombe de l'inventaire
        SuperObject bomb = inventory.getItems().stream().filter(item -> item instanceof OBJ_Bomb).findFirst().orElse(null);
        if (bomb != null && bomb.use(gPanel, this)) {
            inventory.removeItem(bomb);
        }
    }
}
