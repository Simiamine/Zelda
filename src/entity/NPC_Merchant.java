package entity;

import javafx.scene.image.Image;
import main.GamePanel;
import object.OBJ_Potion;
import object.OBJ_Key;

public class NPC_Merchant extends NPC {

    public NPC_Merchant(GamePanel gPanel) {
        super(gPanel);

        // Initialisation des dialogues du marchand
        dialogues = new String[] {
            "Bonjour, aventurier!",
            "Voulez-vous acheter quelque chose?",
            "Merci pour votre achat!"
        };

        // Chargement de l'image du marchand
        getImage();

        // Ajouter des objets à l'inventaire du marchand
        inventory.addItem(new OBJ_Potion());
        inventory.addItem(new OBJ_Key());
        inventory.addItem(new OBJ_Key());
        inventory.addItem(new OBJ_Key());
        inventory.addItem(new OBJ_Key());
        
    }

    @Override
    public void getImage() {
        try {
            npcImage = new Image("file:res/npc/merchant.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void trade() {
        // Logique pour le commerce
        System.out.println("Vous avez acheté un objet!");
        // Déduire des rubis du joueur et ajouter l'objet à son inventaire
    }
}
