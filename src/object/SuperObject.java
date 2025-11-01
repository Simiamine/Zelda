package object;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;
import main.GameConstants;

public class SuperObject {

    public Image image;
    public String name;
    public boolean collision = false;
    public int worldX;
    public int worldY;
    public Rectangle solidArea = new Rectangle(0, 0, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public String description;
	public int mapIndex;

    public void render(GraphicsContext gc, GamePanel gPanel) {
        int screenX = worldX - gPanel.getPlayer().worldX + gPanel.getPlayer().screenX;
        int screenY = worldY - gPanel.getPlayer().worldY + gPanel.getPlayer().screenY;

        if (worldX + GameConstants.TILE_SIZE > gPanel.getPlayer().worldX - gPanel.getPlayer().screenX &&
            worldX - GameConstants.TILE_SIZE < gPanel.getPlayer().worldX + gPanel.getPlayer().screenX &&
            worldY + GameConstants.TILE_SIZE > gPanel.getPlayer().worldY - gPanel.getPlayer().screenY && 
            worldY - GameConstants.TILE_SIZE < gPanel.getPlayer().worldY + gPanel.getPlayer().screenY) {
                gc.drawImage(image, screenX, screenY, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        }
    }

    public boolean interact(GamePanel gPanel) {
        return false;
    }

    public int getAttackRange() {
        return 1; // Valeur par défaut de la portée d'attaque
    }

    public boolean use(GamePanel gPanel, Entity user) {
    	return false;
    }
}
