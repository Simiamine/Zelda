package object;

import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Chest extends SuperObject {

    public OBJ_Chest() {
        
        name = "Chest";

        try {
            image = new Image("file:res/objects/chest.png");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean interact(GamePanel gPanel) {
        // Logique pour ouvrir le coffre
        System.out.println("Le coffre est ouvert !");
        // Par exemple, ajouter des objets à l'inventaire du joueur
        return true;
    }

}
