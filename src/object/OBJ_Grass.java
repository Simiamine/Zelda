package object;

import javafx.scene.image.Image;

public class OBJ_Grass extends SuperObject {

    public OBJ_Grass() {
        
        name = "Grass";

        try {
            image = new Image("file:res/zeldaobjects/Grass.png");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
