package entity;

import javafx.scene.image.Image;
import main.GamePanel;
import object.OBJ_Sword;

public class NPC_Old extends NPC {

    public NPC_Old(GamePanel gPanel) {
        super(gPanel);
        dialogues = new String[] {
            "Bonjour, aventurier!",
            "Le trésor est caché quelque part dans cette forêt.",
            "Faites attention aux monstres qui rôdent la nuit.",
            "Je t'offre cette épée pour t'aider dans ta quête." // Dialogue pour donner l'épée
        };
        getImage();
        inventory.addItem(new OBJ_Sword()); // Ajouter l'épée à l'inventaire du PNJ
    }

    @Override
    public void getImage() {
        npcImage = new Image("file:res/npc/old.png");
    }
}
