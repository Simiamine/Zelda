package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import main.GameConstants;

public abstract class NPC extends Entity {

    protected String[] dialogues;
    protected int dialogueIndex;
    protected Image npcImage;

    public NPC(GamePanel gPanel) {
        super(gPanel);
        direction = "DOWN";
        speed = 0;
        dialogueIndex = 0;
        int tileSize = GameConstants.TILE_SIZE;
        solidArea = new Rectangle(0, 0, tileSize, tileSize * 1.5);
        collisionOn = isSolid = true;
        inventory = new Inventory();
    }

    public void setDialogues(String[] dialogues) {
        this.dialogues = dialogues;
    }

    public String speak() {
        if (dialogues != null && dialogues.length > 0) {
            if (dialogueIndex == -1) {
                return ""; // Dialogue terminé
            }
            String dialogue = dialogues[dialogueIndex];
            dialogueIndex++;
            if (dialogueIndex >= dialogues.length) {
                dialogueIndex = -1; // Indique la fin du dialogue
            }
            return dialogue;
        }
        return "";
    }

    public boolean isDialogueFinished() {
        return dialogueIndex == -1;
    }


    public abstract void getImage();

    public void update() {
        checkCollisions();
    }

    private void checkCollisions() {
        collisionOn = false;
        gPanel.getCollisionChecker().checkTile(this);
    }

    public void render(GraphicsContext gc) {
        int screenX = worldX - gPanel.getPlayer().worldX + gPanel.getPlayer().screenX;
        int screenY = worldY - gPanel.getPlayer().worldY + gPanel.getPlayer().screenY;
        if (npcImage != null) gc.drawImage(npcImage, screenX, screenY, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE * 1.5);
    }
}
