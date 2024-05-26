package entity;

import javafx.scene.image.Image;
import main.GamePanel;
import object.OBJ_Potion;
import object.SuperObject;
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

    public void interact() {
        if (gPanel.player.getRubies() >= 10) {
            gPanel.ui.showTradeWindow(this);
            gPanel.gameState = gPanel.commerceState;
        } else {
            gPanel.ui.setCurrentDialogue("Vous n'avez pas assez de rubis.");
            gPanel.gameState = gPanel.dialogueState;
        }
    }

    public void trade(SuperObject item) {
        if (gPanel.player.getRubies() >= 10) {
            gPanel.player.addRuby(-10);
            gPanel.player.inventory.addItem(item);
            inventory.removeItem(item);
            System.out.println("Vous avez acheté : " + item.name);
        } else {
            System.out.println("Pas assez de rubis pour acheter cet objet.");
        }
    }
}
