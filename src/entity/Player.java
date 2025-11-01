package entity;

import java.util.ArrayList;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import main.GameConstants;
import main.InputHandler;
import object.SuperObject;

/**
 * Représente le joueur contrôlé par l'utilisateur.
 * Gère les mouvements, les attaques, l'inventaire et les interactions.
 */
public class Player extends Entity {

    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    private final Image[][] directionImages = new Image[4][8]; // [0] = up, [1] = down, [2] = right, [3] = left
    public final int screenX;
    public final int screenY;
    private final int SPRITE_WIDTH = GameConstants.PLAYER_SPRITE_WIDTH;
    private final int SPRITE_HEIGHT = GameConstants.PLAYER_SPRITE_HEIGHT;
    private boolean isMoving = false;
    
    private int rubies;
    private int hearts;
    public final int maxRubies = GameConstants.PLAYER_MAX_RUBIES;
    public final int maxHearts = GameConstants.PLAYER_MAX_HEARTS;
    private int force;
    private int attackRange;
    private boolean isAttacking = false;
    private int attackCooldownCounter = 0;
    private int attackAnimationCounter = 0;

    private InputHandler inputHandler;

    public int getSpriteWidth() {
        return SPRITE_WIDTH;
    }

    public int getSpriteHeight() {
        return SPRITE_HEIGHT;
    }
    
    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public Player(GamePanel gamePanel, InputHandler inputHandler) {
        super(gamePanel);
        this.inputHandler = inputHandler;

        screenX = GameConstants.SCREEN_WIDTH / 2 - (GameConstants.TILE_SIZE / 2);
        screenY = GameConstants.SCREEN_HEIGHT / 2 - (GameConstants.TILE_SIZE / 2);

        solidArea = new Rectangle(
            GameConstants.PLAYER_COLLISION_X,
            GameConstants.PLAYER_COLLISION_Y,
            GameConstants.PLAYER_COLLISION_WIDTH,
            GameConstants.PLAYER_COLLISION_HEIGHT
        );
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();

        setDefaultValues();
        loadPlayerImages();
        
        LOGGER.info("Joueur créé avec succès");
    }

    public void setDefaultValues() {
        worldX = GameConstants.PLAYER_START_WORLD_X;
        worldY = GameConstants.PLAYER_START_WORLD_Y;
        speed = GameConstants.PLAYER_DEFAULT_SPEED;
        direction = GameConstants.DIRECTION_DOWN;
        rubies = 0;
        hearts = maxHearts;
        force = GameConstants.PLAYER_DEFAULT_FORCE;
        attackRange = GameConstants.PLAYER_DEFAULT_ATTACK_RANGE;
        gPanel.setCurrentMap(GameConstants.PLAYER_START_MAP);
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
        updateAttackState();
        attackObjectsAndMonsters();
        updatePosition();
        updateSprite();
    }
    
    private void updateAttackState() {
        // Décrémenter le cooldown
        if (attackCooldownCounter > 0) {
            attackCooldownCounter--;
        }
        
        // Gérer l'animation d'attaque
        if (isAttacking) {
            attackAnimationCounter--;
            if (attackAnimationCounter <= 0) {
                isAttacking = false;
            }
        }
        
        // Démarrer une nouvelle attaque si le cooldown est fini
        if (inputHandler.isAttackPressed() && attackCooldownCounter == 0 && !isAttacking) {
            isAttacking = true;
            attackCooldownCounter = GameConstants.PLAYER_ATTACK_COOLDOWN;
            attackAnimationCounter = GameConstants.PLAYER_ATTACK_DURATION;
        }
    }

    private void updateForceAndRange() {
        boolean hasSword = inventory.getItems().stream().anyMatch(item -> item.name.equals("Sword"));
        force = hasSword ? GameConstants.PLAYER_SWORD_FORCE : GameConstants.PLAYER_DEFAULT_FORCE;
        attackRange = hasSword ? GameConstants.PLAYER_SWORD_ATTACK_RANGE : GameConstants.PLAYER_DEFAULT_ATTACK_RANGE;
    }

    private void handleInput(ArrayList<String> inputList) {
        isMoving = inputList.contains(GameConstants.DIRECTION_LEFT) || 
                   inputList.contains(GameConstants.DIRECTION_RIGHT) || 
                   inputList.contains(GameConstants.DIRECTION_UP) || 
                   inputList.contains(GameConstants.DIRECTION_DOWN);
        
        if (inputList.contains(GameConstants.DIRECTION_LEFT)) direction = GameConstants.DIRECTION_LEFT;
        if (inputList.contains(GameConstants.DIRECTION_RIGHT)) direction = GameConstants.DIRECTION_RIGHT;
        if (inputList.contains(GameConstants.DIRECTION_UP)) direction = GameConstants.DIRECTION_UP;
        if (inputList.contains(GameConstants.DIRECTION_DOWN)) direction = GameConstants.DIRECTION_DOWN;
    }

    private void checkCollisions() {
        collisionOn = false;
        gPanel.getCollisionChecker().checkTile(this);
        int objIndex = gPanel.getCollisionChecker().checkObject(this, true);
        pickUpObject(objIndex);
        checkEntityCollisions();
        gPanel.getCollisionChecker().checkMonsterCollision(this);
    }

    private void checkEntityCollisions() {
        for (NPC npc : gPanel.getNpcs()) {
            gPanel.getCollisionChecker().checkEntityCollision(this, npc);
        }
    }

    private void interactWithEntities() {
        if (inputHandler.isSpacePressed()) {
            NPC npc = getFacingNPC();
            if (npc != null) {
                // Utiliser la méthode centralisée de GamePanel au lieu de dupliquer le code
                gPanel.interactWithNPC(npc);
            }
        }
    }

    private void attackObjectsAndMonsters() {
        // Attaquer seulement pendant l'animation d'attaque
        if (!isAttacking) {
            return;
        }
        
        // Vérifier si le joueur a l'épée
        boolean hasSword = inventory.containsItem("Sword");
        
        // Attaquer les monstres
        ArrayList<Monster> monstersToAttack = new ArrayList<>();
        for (Monster monster : gPanel.getMonsters()) {
            if (isInAttackRange(monster)) {
                monstersToAttack.add(monster);
            }
        }
        for (Monster monster : monstersToAttack) {
            if (monster.mapIndex == gPanel.getCurrentMap()) {
                monster.receiveDamage(force);
                LOGGER.fine("Attaque infligée au monstre : " + force + " dégâts");
            }
        }
        
        // Détruire l'herbe automatiquement avec l'épée (une seule fois par attaque)
        if (hasSword && attackAnimationCounter == GameConstants.PLAYER_ATTACK_DURATION - 1) {
            destroyGrassInRange();
        }
    }
    
    /**
     * Détruit automatiquement l'herbe dans la portée de l'épée
     */
    private void destroyGrassInRange() {
        SuperObject[] objects = gPanel.getObjects();
        for (int i = 0; i < objects.length; i++) {
            SuperObject obj = objects[i];
            if (obj != null && obj.name.equals("Grass") && obj.mapIndex == gPanel.getCurrentMap()) {
                if (isObjectInAttackRange(obj)) {
                    // Simuler l'interaction de l'herbe
                    if (obj.interact(gPanel)) {
                        gPanel.setObject(i, null);  // Supprimer l'herbe
                        LOGGER.fine("Herbe détruite par l'épée");
                    }
                }
            }
        }
    }
    
    /**
     * Vérifie si un objet est dans la portée d'attaque
     */
    private boolean isObjectInAttackRange(SuperObject obj) {
        int playerLeftX = worldX;
        int playerRightX = worldX + GameConstants.TILE_SIZE;
        int playerTopY = worldY;
        int playerBottomY = worldY + GameConstants.TILE_SIZE;

        int objLeftX = obj.worldX;
        int objRightX = obj.worldX + GameConstants.TILE_SIZE;
        int objTopY = obj.worldY;
        int objBottomY = obj.worldY + GameConstants.TILE_SIZE;

        switch (direction) {
            case GameConstants.DIRECTION_UP:
                return playerTopY - attackRange * GameConstants.TILE_SIZE < objBottomY &&
                        playerBottomY > objTopY &&
                        playerRightX > objLeftX &&
                        playerLeftX < objRightX;
            case GameConstants.DIRECTION_DOWN:
                return playerBottomY + attackRange * GameConstants.TILE_SIZE > objTopY &&
                        playerTopY < objBottomY &&
                        playerRightX > objLeftX &&
                        playerLeftX < objRightX;
            case GameConstants.DIRECTION_LEFT:
                return playerLeftX - attackRange * GameConstants.TILE_SIZE < objRightX &&
                        playerRightX > objLeftX &&
                        playerBottomY > objTopY &&
                        playerTopY < objBottomY;
            case GameConstants.DIRECTION_RIGHT:
                return playerRightX + attackRange * GameConstants.TILE_SIZE > objLeftX &&
                        playerLeftX < objRightX &&
                        playerBottomY > objTopY &&
                        playerTopY < objBottomY;
            default:
                return false;
        }
    }

    private boolean isInAttackRange(Monster monster) {
        int playerLeftX = worldX;
        int playerRightX = worldX + GameConstants.TILE_SIZE;
        int playerTopY = worldY;
        int playerBottomY = worldY + GameConstants.TILE_SIZE;

        int monsterLeftX = monster.worldX;
        int monsterRightX = monster.worldX + GameConstants.TILE_SIZE;
        int monsterTopY = monster.worldY;
        int monsterBottomY = monster.worldY + GameConstants.TILE_SIZE;

        switch (direction) {
            case GameConstants.DIRECTION_UP:
                return playerTopY - attackRange * GameConstants.TILE_SIZE < monsterBottomY &&
                        playerBottomY > monsterTopY &&
                        playerRightX > monsterLeftX &&
                        playerLeftX < monsterRightX;
            case GameConstants.DIRECTION_DOWN:
                return playerBottomY + attackRange * GameConstants.TILE_SIZE > monsterTopY &&
                        playerTopY < monsterBottomY &&
                        playerRightX > monsterLeftX &&
                        playerLeftX < monsterRightX;
            case GameConstants.DIRECTION_LEFT:
                return playerLeftX - attackRange * GameConstants.TILE_SIZE < monsterRightX &&
                        playerRightX > monsterLeftX &&
                        playerBottomY > monsterTopY &&
                        playerTopY < monsterBottomY;
            case GameConstants.DIRECTION_RIGHT:
                return playerRightX + attackRange * GameConstants.TILE_SIZE > monsterLeftX &&
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
                case GameConstants.DIRECTION_UP: worldY -= speed; break;
                case GameConstants.DIRECTION_DOWN: worldY += speed; break;
                case GameConstants.DIRECTION_LEFT: worldX -= speed; break;
                case GameConstants.DIRECTION_RIGHT: worldX += speed; break;
            }
        }
    }

    private void updateSprite() {
        spriteCounter++;
        if (spriteCounter > GameConstants.SPRITE_ANIMATION_SPEED) {
            spriteNum = (spriteNum % GameConstants.SPRITE_FRAMES) + 1;
            spriteCounter = 0;
        }
    }

    public NPC getFacingNPC() {
        for (NPC npc : gPanel.getNpcs()) {
            if (npc != null && isNear(npc)) {
                return npc;
            }
        }
        return null;
    }

    private boolean isNear(Entity entity) {
        int playerLeftX = worldX - GameConstants.PLAYER_INTERACTION_MARGIN;
        int playerRightX = worldX + GameConstants.TILE_SIZE + GameConstants.PLAYER_INTERACTION_MARGIN;
        int playerTopY = worldY - GameConstants.PLAYER_INTERACTION_MARGIN;
        int playerBottomY = worldY + GameConstants.TILE_SIZE + GameConstants.PLAYER_INTERACTION_MARGIN;

        int entityLeftX = entity.worldX;
        int entityRightX = entity.worldX + GameConstants.TILE_SIZE;
        int entityTopY = entity.worldY;
        int entityBottomY = entity.worldY + (int) (GameConstants.TILE_SIZE * 1.5);

        return playerRightX > entityLeftX && playerLeftX < entityRightX && 
               playerBottomY > entityTopY && playerTopY < entityBottomY;
    }

    public void pickUpObject(int index) {
        if (index != GameConstants.OBJECT_NOT_FOUND_INDEX) {
            SuperObject obj = gPanel.getObject(index);
            if (obj != null && obj.interact(gPanel)) {
                gPanel.setObject(index, null);
                LOGGER.fine("Objet ramassé : " + obj.name);
            }
        }
    }

    public void render(GraphicsContext gc) {
        if (isAttacking) {
            renderAttackRange(gc);
        }

        Image image = null;

        switch (direction) {
            case GameConstants.DIRECTION_UP:
                image = isMoving ? directionImages[0][spriteNum - 1] : directionImages[0][0];
                break;
            case GameConstants.DIRECTION_DOWN:
                image = isMoving ? directionImages[1][spriteNum - 1] : directionImages[1][0];
                break;
            case GameConstants.DIRECTION_RIGHT:
                image = isMoving ? directionImages[2][spriteNum - 1] : directionImages[2][0];
                break;
            case GameConstants.DIRECTION_LEFT:
                image = isMoving ? directionImages[3][spriteNum - 1] : directionImages[3][0];
                break;
        }

        if (image != null) {
            gc.drawImage(image, screenX, screenY, getSpriteWidth(), getSpriteHeight());
        }

        // Debug : afficher la boîte de collision
        renderCollisionBox(gc);
    }

    private void renderAttackRange(GraphicsContext gc) {
        gc.setFill(Color.rgb(255, 0, 0, GameConstants.COLLISION_DEBUG_OPACITY));

        for (int range = 1; range <= attackRange; range++) {
            int attackX = screenX;
            int attackY = screenY;

            switch (direction) {
                case GameConstants.DIRECTION_UP:
                    attackY = screenY - GameConstants.TILE_SIZE * range;
                    break;
                case GameConstants.DIRECTION_DOWN:
                    attackY = screenY + GameConstants.TILE_SIZE * range;
                    break;
                case GameConstants.DIRECTION_LEFT:
                    attackX = screenX - GameConstants.TILE_SIZE * range;
                    break;
                case GameConstants.DIRECTION_RIGHT:
                    attackX = screenX + GameConstants.TILE_SIZE * range;
                    break;
            }

            gc.fillRect(attackX, attackY, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        }
    }

    private void renderCollisionBox(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 255, 0, GameConstants.COLLISION_DEBUG_OPACITY));
        gc.fillRect(screenX + solidArea.getX(), screenY + solidArea.getY(), 
                    solidArea.getWidth(), solidArea.getHeight());
    }

    public void addRuby(int amount) {
        rubies = Math.min(rubies + amount, maxRubies);
        LOGGER.fine("Rubies ajoutés: " + amount + ", Total: " + rubies);
    }

    public void addHeart(int amount) {
        hearts = Math.min(hearts + amount, maxHearts);
        LOGGER.fine("Coeurs ajoutés: " + amount + ", Total: " + hearts);
    }

    public void takeDamage(int damage) {
        hearts = Math.max(hearts - damage, 0);
        if (hearts == 0) {
            LOGGER.warning("Game Over - Le joueur est mort");
            gPanel.resetGame();
            resetStats();
        } else {
            LOGGER.fine("Dégâts reçus: " + damage + ", Coeurs restants: " + hearts);
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
    
    public boolean isAttacking() {
        return isAttacking;
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
            case GameConstants.DIRECTION_UP:
                playerTopY -= GameConstants.TILE_SIZE * range;
                playerBottomY = playerTopY + GameConstants.TILE_SIZE;
                break;
            case GameConstants.DIRECTION_DOWN:
                playerBottomY += GameConstants.TILE_SIZE * range;
                playerTopY = playerBottomY - GameConstants.TILE_SIZE;
                break;
            case GameConstants.DIRECTION_LEFT:
                playerLeftX -= GameConstants.TILE_SIZE * range;
                playerRightX = playerLeftX + GameConstants.TILE_SIZE;
                break;
            case GameConstants.DIRECTION_RIGHT:
                playerRightX += GameConstants.TILE_SIZE * range;
                playerLeftX = playerRightX - GameConstants.TILE_SIZE;
                break;
        }

        SuperObject[] objects = gPanel.getObjects();
        for (int i = 0; i < objects.length; i++) {
            SuperObject obj = objects[i];
            if (obj != null && obj.mapIndex == gPanel.getCurrentMap()) {
                int objLeftX = obj.worldX;
                int objRightX = obj.worldX + GameConstants.TILE_SIZE;
                int objTopY = obj.worldY;
                int objBottomY = obj.worldY + GameConstants.TILE_SIZE;

                if (playerRightX > objLeftX && playerLeftX < objRightX &&
                    playerBottomY > objTopY && playerTopY < objBottomY) {
                    return i;
                }
            }
        }
        return GameConstants.NO_OBJECT_FOUND;
    }
}
