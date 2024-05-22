package object;

import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Sword extends SuperObject {

    public OBJ_Sword() {
        name = "Sword";
        try {
            image = new Image("file:res/objects/sword.png"); // Assurez-vous que l'image existe à cet emplacement
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean interact(GamePanel gPanel) {
        // Logique pour ramasser l'épée
        System.out.println("You picked up a sword!");
        // Ajouter des comportements supplémentaires si nécessaire
        return true;
    }
}
