package entity;

import javafx.scene.image.Image;
import main.GamePanel;
import object.OBJ_Sword;

public class NPC_Old extends NPC {

    // Constructeur de la classe NPC_Old
    public NPC_Old(GamePanel gPanel) {
        super(gPanel);

        // Initialisation des dialogues du NPC
        dialogues = new String[] {
            "Bonjour, aventurier!",
            "Le trésor est caché quelque part dans cette forêt.",
            "Faites attention aux monstres qui rôdent la nuit.",
            "Je t'offre cette épée pour t'aider dans ta quête." // Dialogue pour donner l'épée
        };

        // Chargement de l'image du NPC
        getImage();

        // Ajouter l'épée à l'inventaire du NPC
        inventory.addItem(new OBJ_Sword());
    }

    // Méthode pour charger l'image du NPC
    @Override
    public void getImage() {
        try {
            npcImage = new Image("file:res/npc/old.png"); // Assurez-vous que l'image existe à cet emplacement
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
