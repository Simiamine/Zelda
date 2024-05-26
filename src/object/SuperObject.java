package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;

public class SuperObject {

    public Image image;
    public String name;
    public boolean collision = false;
    public int worldX;
    public int worldY;
    public Rectangle solidArea = new Rectangle(0, 0, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public String description;
	public int mapIndex;

    public void render(GraphicsContext gc, GamePanel gPanel) {
        int screenX = worldX - gPanel.player.worldX + gPanel.player.screenX;
        int screenY = worldY - gPanel.player.worldY + gPanel.player.screenY;

        if (worldX + GamePanel.getTileSize() > gPanel.player.worldX - gPanel.player.screenX &&
            worldX - GamePanel.getTileSize() < gPanel.player.worldX + gPanel.player.screenX &&
            worldY + GamePanel.getTileSize() > gPanel.player.worldY - gPanel.player.screenY && 
            worldY - GamePanel.getTileSize() < gPanel.player.worldY + gPanel.player.screenY) {
                gc.drawImage(image, screenX, screenY, GamePanel.getTileSize(), GamePanel.getTileSize());
        }
    }

    public boolean interact(GamePanel gPanel) {
        return false;
    }

    public int getAttackRange() {
        return 1; // Valeur par défaut de la portée d'attaque
    }

    public boolean use(GamePanel gPanel) {
    	return false;
    }
}
