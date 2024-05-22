package object;

import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Ruby extends SuperObject {

    public OBJ_Ruby() {
        name = "Ruby";
        try {
            image = new Image("file:res/objects/ruby.png"); // Assurez-vous que l'image existe à cet emplacement
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean interact(GamePanel gPanel) {
        // Ajouter des rubis au joueur
        gPanel.player.addRuby(1);
        return true;
    }
}
