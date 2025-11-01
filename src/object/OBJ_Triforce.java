package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;
import main.GameConstants;

public class OBJ_Triforce extends SuperObject {

    public OBJ_Triforce() {
        name = "Triforce";
        description = "[" + name + "]";
        try {
            image = new Image("file:res/objects/triforce.png"); // Assurez-vous que l'image existe à cet emplacement
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean interact(GamePanel gPanel) {
        // Logique pour ramasser la Triforce
        System.out.println("Vous avez ramassé la Triforce !");
        gPanel.getPlayer().inventory.addItem(this);
        return true;
    }

    @Override
    public boolean use(GamePanel gPanel, Entity user) {
        // Activer la condition de victoire
        gPanel.setVictoryCondition();
        System.out.println("Victoire activée !");
        return true;
    }
}
