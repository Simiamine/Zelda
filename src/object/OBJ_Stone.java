package object;

import javafx.scene.image.Image;

public class OBJ_Stone extends SuperObject {

    public OBJ_Stone() {
        
        name = "Stone";
        description = "[" + name + "]";

        try {
            image = new Image("file:res/zeldaobjects/stone.png");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
