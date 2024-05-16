package object;

import javafx.scene.image.Image;

public class OBJ_Chest extends SuperObject {

    public OBJ_Chest() {
        
        name = "Chest";

        try {
            image = new Image("file:res/objects/chest.png");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
