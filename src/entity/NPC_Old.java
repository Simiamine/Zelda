package entity;

import javafx.scene.image.Image;
import main.GamePanel;

public class NPC_Old extends NPC {

    public NPC_Old(GamePanel gPanel) {
        super(gPanel);
        dialogues = new String[] {
            "Bonjour, aventurier!",
            "Le trésor est caché quelque part dans cette forêt.",
            "Faites attention aux monstres qui rôdent la nuit."
        };
        getImage();
    }

    @Override
    public void getImage() {
        npcImage = new Image("file:res/npc/old.png");
    }
}
