package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;
import main.GameConstants;

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
        return true;
    }

    @Override
    public boolean use(GamePanel gPanel, Entity user) {
        int doorIndex = gPanel.getPlayer().getFacingObject(1);
        if (doorIndex != -1) {
            SuperObject obj = gPanel.getObject(doorIndex);
            if (obj instanceof OBJ_Door) {
                ((OBJ_Door) obj).open();
                System.out.println("Vous avez ouvert la porte.");
                return true; // Retourne vrai si la clé a été utilisée pour ouvrir une porte
            }
        }
        System.out.println("Pas de porte à ouvrir.");
        return false;
    }
}
