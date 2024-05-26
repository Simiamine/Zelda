package entity;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import main.InputHandler;
//import object.OBJ_Grass;
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
    public final int maxRubies = 999;
    public final int maxHearts = 6;
    private int force;
    private int attackRange;
    private boolean isAttacking = false; // Ajouter un drapeau pour vérifier si le joueur attaque

    public InputHandler inputHandler;

    public int getSpriteWidth() {
        return SPRITE_WIDTH;
    }

    public int getSpriteHeight() {
        return SPRITE_HEIGHT;
    }

    public Player(GamePanel gPanel, InputHandler inputHandler) {
        super(gPanel);
        this.gPanel = gPanel;
        this.inputHandler = inputHandler;

        screenX = GamePanel.getScreenWidth() / 2 - (GamePanel.getTileSize() / 2);
        screenY = GamePanel.getScreenHeight() / 2 - (GamePanel.getTileSize() / 2);

        solidArea = new Rectangle(12,40,24,24);
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
        force = 1;
        attackRange = 1;
        gPanel.currentMap = 0;
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
        updateForceAndRange();
        attackObjectsAndMonsters();
        updatePosition();
        updateSprite();
        isAttacking = inputHandler.isAttackPressed(); // Mettre à jour l'état d'attaque du joueur
    }

    private void updateForceAndRange() {
        boolean hasSword = inventory.getItems().stream().anyMatch(item -> item.name.equals("Sword"));
        force = hasSword ? 3 : 1; // Force de base est 1, si l'épée est présente, force est 3
        attackRange = hasSword ? 2 : 1; // Portée de base est 1, si l'épée est présente, portée est 2
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
        if (inputHandler.isSpacePressed()) {
            NPC npc = getFacingNPC();
            if (npc != null) {
                interactWithNPC(npc);
            }
        }
    }

    private void attackObjectsAndMonsters() {
        if (inputHandler.isAttackPressed()) {
            ArrayList<Monster> monstersToAttack = new ArrayList<>();
            for (Monster monster : gPanel.monsters) {
                if (isInAttackRange(monster)) {
                    monstersToAttack.add(monster);
                }
            }
            for (Monster monster : monstersToAttack) {
                if (monster.mapIndex == gPanel.currentMap) {
                    monster.receiveDamage(force);
                }
            }
        }
    }

    private boolean isInAttackRange(Monster monster) {
        int playerLeftX = worldX;
        int playerRightX = worldX + GamePanel.getTileSize();
        int playerTopY = worldY;
        int playerBottomY = worldY + GamePanel.getTileSize();

        int monsterLeftX = monster.worldX;
        int monsterRightX = monster.worldX + GamePanel.getTileSize();
        int monsterTopY = monster.worldY;
        int monsterBottomY = monster.worldY + GamePanel.getTileSize();

        switch (direction) {
            case "UP":
                return playerTopY - attackRange * GamePanel.getTileSize() < monsterBottomY &&
                        playerBottomY > monsterTopY &&
                        playerRightX > monsterLeftX &&
                        playerLeftX < monsterRightX;
            case "DOWN":
                return playerBottomY + attackRange * GamePanel.getTileSize() > monsterTopY &&
                        playerTopY < monsterBottomY &&
                        playerRightX > monsterLeftX &&
                        playerLeftX < monsterRightX;
            case "LEFT":
                return playerLeftX - attackRange * GamePanel.getTileSize() < monsterRightX &&
                        playerRightX > monsterLeftX &&
                        playerBottomY > monsterTopY &&
                        playerTopY < monsterBottomY;
            case "RIGHT":
                return playerRightX + attackRange * GamePanel.getTileSize() > monsterLeftX &&
                        playerLeftX < monsterRightX &&
                        playerBottomY > monsterTopY &&
                        playerTopY < monsterBottomY;
            default:
                return false;
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

    public NPC getFacingNPC() {
        for (NPC npc : gPanel.npcs) {
            if (npc != null && isNear(npc)) {
                return npc;
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
            if (!dialogue.isEmpty()) {
                gPanel.ui.setCurrentDialogue(dialogue);
                gPanel.gameState = gPanel.dialogueState; // Passe le jeu en mode dialogue
            } else {
                gPanel.gameState = gPanel.playState; // Revenir en mode jeu si le dialogue est terminé
            }
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
        if (isAttacking) {
            renderAttackRange(gc); // Afficher les cases d'attaque avant de dessiner le joueur
        }

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

        // Draw the player's collision box
        renderCollisionBox(gc);
    }

    private void renderAttackRange(GraphicsContext gc) {
        gc.setFill(Color.rgb(255, 0, 0, 0.25)); // Rouge avec une opacité de 25%

        for (int range = 1; range <= attackRange; range++) {
            int attackX = screenX;
            int attackY = screenY;

            switch (direction) {
                case "UP":
                    attackY = screenY - GamePanel.getTileSize() * range;
                    break;
                case "DOWN":
                    attackY = screenY + GamePanel.getTileSize() * range;
                    break;
                case "LEFT":
                    attackX = screenX - GamePanel.getTileSize() * range;
                    break;
                case "RIGHT":
                    attackX = screenX + GamePanel.getTileSize() * range;
                    break;
            }

            gc.fillRect(attackX, attackY, GamePanel.getTileSize(), GamePanel.getTileSize());
        }
    }

    private void renderCollisionBox(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 255, 0, 0.25)); // Vert avec une opacité de 25%
        gc.fillRect(screenX + solidArea.getX(), screenY + solidArea.getY(), solidArea.getWidth(), solidArea.getHeight());
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
            System.out.println("Game Over");
            gPanel.resetGame(); // Reset the game
            resetStats(); // Réinitialise les statistiques et l'inventaire du joueur
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
    
    public int getForce() {
        return force;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void addForce(int amount) {
        force += amount;
        System.out.println("Force: " + force);
    }
    
    public void resetStats() {
        setDefaultValues();
        inventory.getItems().clear();
    }

    public void useItem(int index) {
        if (index >= 0 && index < inventory.getItems().size()) {
            SuperObject item = inventory.getItems().get(index);
            if (item.use(gPanel, this)) {
                inventory.removeItem(item);
            }
        }
    }
    public int getFacingObject(int range) {
        int playerLeftX = worldX;
        int playerRightX = worldX;
        int playerTopY = worldY;
        int playerBottomY = worldY;

        switch (direction) {
            case "UP":
                playerTopY -= GamePanel.getTileSize() * range;
                playerBottomY = playerTopY + GamePanel.getTileSize();
                break;
            case "DOWN":
                playerBottomY += GamePanel.getTileSize() * range;
                playerTopY = playerBottomY - GamePanel.getTileSize();
                break;
            case "LEFT":
                playerLeftX -= GamePanel.getTileSize() * range;
                playerRightX = playerLeftX + GamePanel.getTileSize();
                break;
            case "RIGHT":
                playerRightX += GamePanel.getTileSize() * range;
                playerLeftX = playerRightX - GamePanel.getTileSize();
                break;
        }

        for (int i = 0; i < gPanel.obj.length; i++) {
            SuperObject obj = gPanel.obj[i];
            if (obj != null && obj.mapIndex == gPanel.currentMap) {
                int objLeftX = obj.worldX;
                int objRightX = obj.worldX + GamePanel.getTileSize();
                int objTopY = obj.worldY;
                int objBottomY = obj.worldY + GamePanel.getTileSize();

                if (playerRightX > objLeftX && playerLeftX < objRightX &&
                    playerBottomY > objTopY && playerTopY < objBottomY) {
                    return i;
                }
            }
        }
        return -1;
    }
}
