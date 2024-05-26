package object;

import javafx.scene.image.Image;
import main.GamePanel;

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
        gPanel.player.inventory.addItem(this);
        return true;
    }

    @Override
    public boolean use(GamePanel gPanel) {
        // Logique pour tuer tous les monstres de la carte
        int currentMap = gPanel.currentMap;
        gPanel.monsters.removeIf(monster -> monster.mapIndex == currentMap);
        System.out.println("Tous les monstres de la carte ont été exterminés !");
        return true;
    }
}
