package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;
import main.GameConstants;

public class OBJ_Potion extends SuperObject {

    public OBJ_Potion() {
        name = "Potion";
        description = "[" + name + "]";
        try {
            image = new Image("file:res/objects/potion.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean interact(GamePanel gPanel) {
        System.out.println("You picked up a potion!");
        gPanel.getPlayer().inventory.addItem(this); // Potion redonne 1 point de vie
        return true;
    }

    @Override
    public boolean use(GamePanel gPanel, Entity user) {
        if (gPanel.getPlayer().getHearts() == gPanel.getPlayer().maxHearts) {
            return false;
        }
        System.out.println("Utilisation de la potion !");
        gPanel.getPlayer().addHeart(3); // Potion redonne 3 points de vie
        return true;
    }
}
