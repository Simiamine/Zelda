package object;

import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Key extends SuperObject {

    public OBJ_Key() {
        
        name = "Key";

        try {
            image = new Image("file:res/objects/key.png");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    @Override
    public boolean interact(GamePanel gPanel) {
        // Logique pour ramasser la clé
        System.out.println("La clé a été ramassée !");
        // Par exemple, ajouter la clé à l'inventaire du joueur
        return true;
    }
}
