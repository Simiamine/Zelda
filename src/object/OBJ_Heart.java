package object;

import javafx.scene.image.Image;
import main.GamePanel;
import main.GameConstants;

public class OBJ_Heart extends SuperObject {

    public OBJ_Heart() {
        name = "Heart";
        description = "[" + name + "]";
        try {
            image = new Image("file:res/objects/heart.png"); // Assurez-vous que l'image existe à cet emplacement
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean interact(GamePanel gPanel) {
        // Ajouter des cœurs au joueur
        gPanel.getPlayer().addHeart(1);
        return true;
    }
}
