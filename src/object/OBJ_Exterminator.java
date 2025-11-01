package object;

import entity.Entity;
import javafx.scene.image.Image;
import main.GamePanel;
import main.GameConstants;

public class OBJ_Exterminator extends SuperObject {

    public OBJ_Exterminator() {
        name = "Exterminator";
        try {
            image = new Image("file:res/objects/exterminator.png"); // Assurez-vous que l'image existe à cet emplacement
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean interact(GamePanel gPanel) {
        // Logique pour ramasser l'item
        System.out.println("Vous avez ramassé l'exterminateur !");
        gPanel.getPlayer().inventory.addItem(this);
        return true;
    }

    @Override
    public boolean use(GamePanel gPanel, Entity user) {
        int currentMap = gPanel.getCurrentMap();
        gPanel.getMonsters().removeIf(monster -> monster.mapIndex == currentMap);
        System.out.println("Tous les monstres de la carte ont été exterminés !");
        return true;
    }
}
