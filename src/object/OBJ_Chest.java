package object;

import javafx.scene.image.Image;
import main.GamePanel;
import java.util.ArrayList;
import java.util.List;

public class OBJ_Chest extends SuperObject {

    private List<SuperObject> items; // Liste des objets contenus dans le coffre

    public OBJ_Chest() {
        name = "Chest";
        description = "[" + name + "]";

        try {
            image = new Image("file:res/objects/chest.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        items = new ArrayList<>();
    }

    @Override
    public boolean interact(GamePanel gPanel) {
        // Logique pour ouvrir le coffre
        System.out.println("Le coffre est ouvert !");
        
        // Transférer les objets du coffre dans l'inventaire du joueur
        for (SuperObject item : items) {
            gPanel.player.inventory.addItem(item);
            System.out.println("Vous avez obtenu : " + item.name);
        }

        items.clear(); // Vider le coffre après avoir transféré les objets
        return true;
    }

    public void addItem(SuperObject item) {
        items.add(item);
    }

    public List<SuperObject> getItems() {
        return items;
    }
}
