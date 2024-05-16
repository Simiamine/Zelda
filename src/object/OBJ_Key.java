package object;

import javafx.scene.image.Image;

public class OBJ_Key extends SuperObject {

    public OBJ_Key() {
        
        name = "Key";

        try {
            image = new Image("file:res/objects/key.png");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
