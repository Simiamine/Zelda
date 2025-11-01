package object;

import javafx.scene.image.Image;
import main.GamePanel;
import main.GameConstants;
import entity.Monster;
import entity.Entity;
import entity.Player;

public class OBJ_Bomb extends SuperObject {

    public OBJ_Bomb() {
        name = "Bomb";
        description = "[" + name + "]";

        try {
            image = new Image("file:res/objects/bomb.png"); // Assurez-vous que l'image existe à cet emplacement
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean interact(GamePanel gPanel) {
        // Logique pour ramasser la bombe
        System.out.println("La bombe a été ramassée !");
        gPanel.getPlayer().inventory.addItem(this);
        return true;
    }

    @Override
    public boolean use(GamePanel gPanel, Entity user) {
        // Logique pour utiliser la bombe
        int userX = user.worldX;
        int userY = user.worldY;
        int tileSize = GameConstants.TILE_SIZE;

        if (user instanceof Player) {
            // Si le joueur utilise la bombe
            for (Monster monster : gPanel.getMonsters()) {
                int monsterX = monster.worldX;
                int monsterY = monster.worldY;

                int distanceX = Math.abs(userX - monsterX);
                int distanceY = Math.abs(userY - monsterY);

                if (distanceX <= tileSize * 3 && distanceY <= tileSize * 3) {
                    monster.receiveDamage(20);
                }
            }
            System.out.println("La bombe a explosé !");
        } else if (user instanceof Monster) {
            // Si un monstre utilise la bombe
            Player player = gPanel.getPlayer();
            int playerX = player.worldX;
            int playerY = player.worldY;

            int distanceX = Math.abs(userX - playerX);
            int distanceY = Math.abs(userY - playerY);

            if (distanceX <= tileSize * 3 && distanceY <= tileSize * 3) {
                player.takeDamage(1);
            }
            System.out.println("La bombe a explosé !");
        }
        return true;
    }
}
