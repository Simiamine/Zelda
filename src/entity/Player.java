package entity;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import object.SuperObject;

public class Player extends Entity {

    private final Image[][] directionImages = new Image[4][8]; // [0] = up, [1] = down, [2] = right, [3] = left
    public final int screenX;
    public final int screenY;
    private final int SPRITE_WIDTH = GamePanel.getScale() * 16;
    private final int SPRITE_HEIGHT = GamePanel.getScale() * 24;
    private boolean isMoving = false;
    private final int interactionMargin = 1; // Marge de tolérance pour l'interaction

    public int getSpriteWidth() {
        return SPRITE_WIDTH;
    }

    public int getSpriteHeight() {
        return SPRITE_HEIGHT;
    }

    public Player(GamePanel gPanel) {
        super(gPanel);
        this.gPanel = gPanel;

        screenX = GamePanel.getScreenWidth() / 2 - (GamePanel.getTileSize() / 2);
        screenY = GamePanel.getScreenHeight() / 2 - (GamePanel.getTileSize() / 2);

        solidArea = new Rectangle(8, 50, 16, 16);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();

        setDefaultValues();
        loadPlayerImages();
    }

    public void setDefaultValues() {
        worldX = GamePanel.getTileSize() * 11 + 24;
        worldY = GamePanel.getTileSize() * 17;
        speed = 10;
        direction = "DOWN"; // Use uppercase for consistency
    }

    public void loadPlayerImages() {
        String[] directions = {"U", "D", "R", "L"};
        for (int dir = 0; dir < directions.length; dir++) {
            for (int i = 0; i < 8; i++) {
                directionImages[dir][i] = new Image("file:res/player/" + directions[dir] + i + ".png");
            }
        }
    }

    public void update(ArrayList<String> inputList) {
        if (inputList.contains("LEFT") || inputList.contains("RIGHT") || inputList.contains("UP") || inputList.contains("DOWN")) {
            if (inputList.contains("LEFT")) {
                direction = "LEFT";
            }
            if (inputList.contains("RIGHT")) {
                direction = "RIGHT";
            }
            if (inputList.contains("UP")) {
                direction = "UP";
            }
            if (inputList.contains("DOWN")) {
                direction = "DOWN";
            }
            isMoving = true;
        } else {
            isMoving = false;
        }

        collisionOn = false;
        gPanel.cChecker.checkTile(this);
        int objIndex = gPanel.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        // Vérifier les collisions avec les NPCs
        for (NPC npc : gPanel.npcs) {
            gPanel.cChecker.checkEntityCollision(this, npc);
        }

        // Vérifier l'interaction avec les NPCs
        if (gPanel.getInputHandler().isSpacePressed()) {
            NPC npc = getFacingNPC();
            if (npc != null) {
                interactWithNPC(npc);
            }
        }

        if (!collisionOn && isMoving) {
            switch (direction) {
                case "UP":
                    worldY -= speed;
                    break;
                case "DOWN":
                    worldY += speed;
                    break;
                case "LEFT":
                    worldX -= speed;
                    break;
                case "RIGHT":
                    worldX += speed;
                    break;
            }
            spriteCounter++;
            if (spriteCounter > 3) {
                spriteNum = (spriteNum % 8) + 1; // Change de sprite de 1 à 8
                spriteCounter = 0;
            }
        }
    }

    private NPC getFacingNPC() {
        for (NPC npc : gPanel.npcs) {
            if (npc != null && isNear(npc) && isFacing(npc)) {
                return npc;
            }
        }
        return null;
    }

    private boolean isNear(NPC npc) {
        int playerLeftX = worldX - interactionMargin;
        int playerRightX = worldX + GamePanel.getTileSize() + interactionMargin;
        int playerTopY = worldY - interactionMargin;
        int playerBottomY = worldY + GamePanel.getTileSize() + interactionMargin;

        int npcLeftX = npc.worldX;
        int npcRightX = npc.worldX + GamePanel.getTileSize();
        int npcTopY = npc.worldY;
        int npcBottomY = npc.worldY + (int) (GamePanel.getTileSize() * 1.5);

        return playerRightX > npcLeftX && playerLeftX < npcRightX && playerBottomY > npcTopY && playerTopY < npcBottomY;
    }

    private boolean isFacing(NPC npc) {
        int npcX = npc.worldX;
        int npcY = npc.worldY;
        int playerX = worldX;
        int playerY = worldY;

        switch (direction) {
            case "UP":
                return playerX + GamePanel.getTileSize() > npcX && playerX < npcX + GamePanel.getTileSize() && playerY > npcY;
            case "DOWN":
                return playerX + GamePanel.getTileSize() > npcX && playerX < npcX + GamePanel.getTileSize() && playerY < npcY;
            case "LEFT":
                return playerY + GamePanel.getTileSize() > npcY && playerY < npcY + GamePanel.getTileSize() * 1.5 && playerX > npcX;
            case "RIGHT":
                return playerY + GamePanel.getTileSize() > npcY && playerY < npcY + GamePanel.getTileSize() * 1.5 && playerX < npcX;
            default:
                return false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            SuperObject obj = gPanel.obj[i];
            if (obj.interact(gPanel)) {
                gPanel.obj[i] = null; // Retirer l'objet après interaction si nécessaire
            }
        }
    }

    public void interactWithNPC(NPC npc) {
        if (npc != null) {
            String dialogue = npc.speak();
            System.out.println(dialogue);
        }
    }

    public void render(GraphicsContext gc) {
        Image image = null;

        switch (direction) {
            case "UP":
                image = isMoving ? directionImages[0][spriteNum - 1] : directionImages[0][0];
                break;
            case "DOWN":
                image = isMoving ? directionImages[1][spriteNum - 1] : directionImages[1][0];
                break;
            case "RIGHT":
                image = isMoving ? directionImages[2][spriteNum - 1] : directionImages[2][0];
                break;
            case "LEFT":
                image = isMoving ? directionImages[3][spriteNum - 1] : directionImages[3][0];
                break;
        }

        if (image != null) {
            gc.drawImage(image, screenX, screenY, getSpriteWidth(), getSpriteHeight());
        }
    }
}
