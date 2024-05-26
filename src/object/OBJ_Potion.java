package object;

import javafx.scene.image.Image;
import main.GamePanel;

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
        gPanel.player.inventory.addItem(this); // Potion redonne 1 point de vie
        return true;
    }

    @Override
    public boolean use(GamePanel gPanel) {
    	if (gPanel.player.getHearts()==gPanel.player.maxHearts) {
    		return false;
    	}
        System.out.println("Using potion!");
        gPanel.player.addHeart(3); // Potion redonne 3 points de vie
        return true;
    }
}
