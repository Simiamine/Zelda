package object;

import javafx.scene.image.Image;
import main.GamePanel;

public class OBJ_Door extends SuperObject {

    private boolean isOpen = false;

    public OBJ_Door() {
        name = "Door";
        description = "[" + name + "]";

        try {
        	if (isOpen) {
        		image = new Image("file:res/objects/door_open.png");
        	}
            image = new Image("file:res/objects/door.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        collision = true;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        isOpen = true;
        image = new Image("file:res/objects/door_open.png"); // Remplacez par une image de porte ouverte ou un carré noir
    }

    @Override
    public boolean interact(GamePanel gPanel) {
        if (!isOpen) {
            return false;
        } else {
            System.out.println("Vous avez ouvert la porte.");
             // La porte ne bloque plus le passage
            return true;
        }
    }
}
