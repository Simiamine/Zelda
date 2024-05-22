package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;

public abstract class NPC extends Entity {

    protected String[] dialogues;
    protected int dialogueIndex;
    protected Image npcImage;

    public NPC(GamePanel gPanel) {
        super(gPanel);
        direction = "DOWN";
        speed = 0; // NPC fixe par défaut
        dialogueIndex = 0;
        int tileSize = GamePanel.getTileSize();
        solidArea = new Rectangle(0, 0, tileSize, tileSize * 1.5); // Taille de la collision pour le NPC
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        collisionOn = true; // Activer la collision par défaut
        isSolid = true; // NPC est solide par défaut
        inventory = new Inventory(); // Initialize inventory for NPC
    }

    public void setDialogues(String[] dialogues) {
        this.dialogues = dialogues;
    }

    public String speak() {
        if (dialogues != null && dialogues.length > 0) {
            String dialogue = dialogues[dialogueIndex];
            dialogueIndex = (dialogueIndex + 1) % dialogues.length;
            return dialogue;
        }
        return "";
    }

    public abstract void getImage();

    public void update() {
        // Pas de mouvement pour ce NPC par défaut
    }

    public void render(GraphicsContext gc) {
        int screenX = worldX - gPanel.player.worldX + gPanel.player.screenX;
        int screenY = worldY - gPanel.player.worldY + gPanel.player.screenY;

        if (npcImage != null) {
            gc.drawImage(npcImage, screenX, screenY, GamePanel.getTileSize(), GamePanel.getTileSize() * 1.5); // Ajuster la taille de l'image rendue
        }
    }
}
