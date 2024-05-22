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
    
    private int rubies;
    private int hearts;
    private final int maxRubies = 999;
    private final int maxHearts = 3;

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
        rubies = 0;
        hearts = maxHearts;
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
        handleInput(inputList);
        checkCollisions();
        interactWithEntities();
        attackMonsters(); // Ajouter la vérification d'attaque
        updatePosition();
        updateSprite();
        checkInventoryDisplay();
    }

    private void handleInput(ArrayList<String> inputList) {
        isMoving = inputList.contains("LEFT") || inputList.contains("RIGHT") || inputList.contains("UP") || inputList.contains("DOWN");
        if (inputList.contains("LEFT")) direction = "LEFT";
        if (inputList.contains("RIGHT")) direction = "RIGHT";
        if (inputList.contains("UP")) direction = "UP";
        if (inputList.contains("DOWN")) direction = "DOWN";
    }

    private void checkCollisions() {
        collisionOn = false;
        gPanel.cChecker.checkTile(this);
        int objIndex = gPanel.cChecker.checkObject(this, true);
        pickUpObject(objIndex);
        checkEntityCollisions();
        gPanel.cChecker.checkMonsterCollision(this); // Ajouter la vérification de collision avec les monstres
    }

    private void checkEntityCollisions() {
        for (NPC npc : gPanel.npcs) {
            gPanel.cChecker.checkEntityCollision(this, npc);
        }
    }

    private void interactWithEntities() {
        if (gPanel.getInputHandler().isSpacePressed()) {
            NPC npc = getFacingNPC();
            if (npc != null) {
                interactWithNPC(npc);
            }
        }
    }

    private void attackMonsters() {
        if (gPanel.getInputHandler().isAttackPressed()) {
            Monster monster = getFacingMonster();
            if (monster != null) {
                monster.receiveDamage(1); // Attaque le monstre avec 1 point de dégât
            }
        }
    }

    private void updatePosition() {
        if (!collisionOn && isMoving) {
            switch (direction) {
                case "UP": worldY -= speed; break;
                case "DOWN": worldY += speed; break;
                case "LEFT": worldX -= speed; break;
                case "RIGHT": worldX += speed; break;
            }
        }
    }

    private void updateSprite() {
        spriteCounter++;
        if (spriteCounter > 3) {
            spriteNum = (spriteNum % 8) + 1; // Change de sprite de 1 à 8
            spriteCounter = 0;
        }
    }

    private void checkInventoryDisplay() {
        if (gPanel.getInputHandler().isIPressed()) {
            if (inventory.getItems().isEmpty()) {
                System.out.println("Inventaire vide");
            } else {
                inventory.displayInventory();
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

    private Monster getFacingMonster() {
        for (Monster monster : gPanel.monsters) {
            if (monster != null && isNear(monster) && isFacing(monster)) {
                return monster;
            }
        }
        return null;
    }

    private boolean isNear(Entity entity) {
        int playerLeftX = worldX - interactionMargin;
        int playerRightX = worldX + GamePanel.getTileSize() + interactionMargin;
        int playerTopY = worldY - interactionMargin;
        int playerBottomY = worldY + GamePanel.getTileSize() + interactionMargin;

        int entityLeftX = entity.worldX;
        int entityRightX = entity.worldX + GamePanel.getTileSize();
        int entityTopY = entity.worldY;
        int entityBottomY = entity.worldY + (int) (GamePanel.getTileSize() * 1.5);

        return playerRightX > entityLeftX && playerLeftX < entityRightX && playerBottomY > entityTopY && playerTopY < entityBottomY;
    }

    private boolean isFacing(Entity entity) {
        int entityX = entity.worldX;
        int entityY = entity.worldY;
        int playerX = worldX;
        int playerY = worldY;

        switch (direction) {
            case "UP":
                return playerX + GamePanel.getTileSize() > entityX && playerX < entityX + GamePanel.getTileSize() && playerY > entityY;
            case "DOWN":
                return playerX + GamePanel.getTileSize() > entityX && playerX < entityX + GamePanel.getTileSize() && playerY < entityY;
            case "LEFT":
                return playerY + GamePanel.getTileSize() > entityY && playerY < entityY + GamePanel.getTileSize() * 1.5 && playerX > entityX;
            case "RIGHT":
                return playerY + GamePanel.getTileSize() > entityY && playerY < entityY + GamePanel.getTileSize() * 1.5 && playerX < entityX;
            default:
                return false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            SuperObject obj = gPanel.obj[i];
            if (obj.interact(gPanel)) {
                gPanel.obj[i] = null; // Remove the object from the game world
            }
        }
    }

    public void interactWithNPC(NPC npc) {
        if (npc != null) {
            String dialogue = npc.speak();
            System.out.println(dialogue);
            // Example interaction: transfer an item from NPC to player
            if (!npc.inventory.getItems().isEmpty()) {
                SuperObject item = npc.inventory.getItems().get(0);
                npc.inventory.removeItem(item);
                inventory.addItem(item);
                System.out.println("Received " + item.name + " from " + npc.getClass().getSimpleName());
            }
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

    public void addRuby(int amount) {
        rubies = Math.min(rubies + amount, maxRubies);
        System.out.println("Rubies: " + rubies);
    }

    public void addHeart(int amount) {
        hearts = Math.min(hearts + amount, maxHearts);
        System.out.println("Hearts: " + hearts);
    }

    public void takeDamage(int damage) {
        hearts = Math.max(hearts - damage, 0);
        if (hearts == 0) {
            System.out.println("Player is dead");
            // Handle player death
        } else {
            System.out.println("Hearts: " + hearts);
        }
    }

    public int getRubies() {
        return rubies;
    }

    public int getHearts() {
        return hearts;
    }
}
