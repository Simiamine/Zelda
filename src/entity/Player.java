package entity;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;

public class Player extends Entity {

    private final GamePanel gPanel;
    private final Image[] upImages = new Image[8];
    private final Image[] downImages = new Image[8];
    private final Image[] rightImages = new Image[8];
    private final Image[] leftImages = new Image[8];
    public final int screenX;
    public final int screenY;
    private final int SPRITE_WIDTH = GamePanel.getScale() * 16;
    private final int SPRITE_HEIGHT = GamePanel.getScale() * 24;
    private boolean isMoving = false;

    public int getSpriteWidth() {
        return SPRITE_WIDTH;
    }

    public int getSpriteHeight() {
        return SPRITE_HEIGHT;
    }

    public Player(GamePanel gPanel)
    {
        this.gPanel = gPanel;

        screenX = GamePanel.getScreenWidth() / 2 - (GamePanel.getTileSize() / 2);
        screenY = GamePanel.getScreenHeight() / 2 - (GamePanel.getTileSize() / 2);

        // Initialisation de solidArea
        solidArea = new Rectangle();
        solidArea.setX(8);
        solidArea.setY(50);
        solidArea.setWidth(16); // Ajuster la largeur selon vos besoins
        solidArea.setHeight(16); // Ajuster la hauteur selon vos besoins

        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = GamePanel.getTileSize() * 11 + 24;
        worldY = GamePanel.getTileSize() * 17;
        speed = 10;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            for (int i = 0; i < 8; i++) {
                upImages[i] = new Image("file:res/player/U" + (i ) + ".png");
                downImages[i] = new Image("file:res/player/D" + (i ) + ".png");
                rightImages[i] = new Image("file:res/player/R" + (i ) + ".png");
                leftImages[i] = new Image("file:res/player/L" + (i ) + ".png");
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        //int objIndex = gPanel.cChecker.checkObject(this, true);

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
                default:
                    break;
            }
            spriteCounter++;
            if (spriteCounter > 3) {
                spriteNum = (spriteNum % 8) + 1; // Change de sprite de 1 à 8
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
    	
    	if(i != 999) {
    		
    		gPanel.obj[i] = null;
    	}
    }
    public void render(GraphicsContext gc) {
        Image image = null;

        switch (direction) {
            case "UP":
                if (isMoving) {
                    image = upImages[spriteNum - 1];
                } else {
                    image = upImages[0];
                }
                break;
            case "DOWN":
                if (isMoving) {
                    image = downImages[spriteNum - 1];
                } else {
                    image = downImages[0];
                }
                break;
            case "RIGHT":
                if (isMoving) {
                    image = rightImages[spriteNum - 1];
                } else {
                    image = rightImages[0];
                }
                break;
            case "LEFT":
                if (isMoving) {
                    image = leftImages[spriteNum - 1];
                } else {
                    image = leftImages[0];
                }
                break;
            default:
            	image = downImages[0];
                break;
        }

        if (image != null) {
            gc.drawImage(image, screenX, screenY, getSpriteWidth(), getSpriteHeight());
        }
    }
}
