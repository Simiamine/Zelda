package main;

import entity.Entity;
import entity.NPC;
import entity.Monster;

public class CollisionChecker {

    private GamePanel gPanel;

    public CollisionChecker(GamePanel gPanel) {
        this.gPanel = gPanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = (int) (entity.worldX + entity.solidArea.getX());
        int entityRightWorldX = (int) (entity.worldX + entity.solidArea.getX() + entity.solidArea.getWidth());
        int entityTopWorldY = (int) (entity.worldY + entity.solidArea.getY());
        int entityBottomWorldY = (int) (entity.worldY + entity.solidArea.getY() + entity.solidArea.getHeight());

        int entityLeftCol = entityLeftWorldX / GamePanel.getTileSize();
        int entityRightCol = entityRightWorldX / GamePanel.getTileSize();
        int entityTopRow = entityTopWorldY / GamePanel.getTileSize();
        int entityBottomRow = entityBottomWorldY / GamePanel.getTileSize();

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "UP":
                entityTopRow = (entityTopWorldY - entity.speed) / GamePanel.getTileSize();
                break;
            case "DOWN":
                entityBottomRow = (entityBottomWorldY + entity.speed) / GamePanel.getTileSize();
                break;
            case "LEFT":
                entityLeftCol = (entityLeftWorldX - entity.speed) / GamePanel.getTileSize();
                break;
            case "RIGHT":
                entityRightCol = (entityRightWorldX + entity.speed) / GamePanel.getTileSize();
                break;
        }

        tileNum1 = gPanel.tileManager.getMapTileNum()[entityLeftCol][entityTopRow];
        tileNum2 = gPanel.tileManager.getMapTileNum()[entityRightCol][entityBottomRow];

        if (gPanel.tileManager.getTiles()[tileNum1].collision || gPanel.tileManager.getTiles()[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gPanel.obj.length; i++) {
            if (gPanel.obj[i] != null) {
                // get entity solid area position
                entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
                entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

                // get object solid area position
                gPanel.obj[i].solidArea.setX(gPanel.obj[i].worldX + gPanel.obj[i].solidArea.getX());
                gPanel.obj[i].solidArea.setY(gPanel.obj[i].worldY + gPanel.obj[i].solidArea.getY());

                switch (entity.direction) {
                    case "UP":
                        entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
                        break;
                    case "DOWN":
                        entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
                        break;
                    case "LEFT":
                        entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
                        break;
                    case "RIGHT":
                        entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
                        break;
                }

                if (entity.solidArea.getBoundsInLocal().intersects(gPanel.obj[i].solidArea.getBoundsInLocal())) {
                    if (gPanel.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.solidArea.setX(entity.solidAreaDefaultX);
                entity.solidArea.setY(entity.solidAreaDefaultY);
                gPanel.obj[i].solidArea.setX(gPanel.obj[i].solidAreaDefaultX);
                gPanel.obj[i].solidArea.setY(gPanel.obj[i].solidAreaDefaultY);
            }
        }

        return index;
    }

    public void checkEntityCollision(Entity entity, Entity target) {
        if (target != null) {
            entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
            entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

            target.solidArea.setX(target.worldX + target.solidArea.getX());
            target.solidArea.setY(target.worldY + target.solidArea.getY());

            switch (entity.direction) {
                case "UP":
                    entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
                    break;
                case "DOWN":
                    entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
                    break;
                case "LEFT":
                    entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
                    break;
                case "RIGHT":
                    entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
                    break;
            }

            if (entity.solidArea.getBoundsInLocal().intersects(target.solidArea.getBoundsInLocal())) {
                if (target.isSolid) {
                    entity.collisionOn = true;
                }
            }

            entity.solidArea.setX(entity.solidAreaDefaultX);
            entity.solidArea.setY(entity.solidAreaDefaultY);
            target.solidArea.setX(target.solidAreaDefaultX);
            target.solidArea.setY(target.solidAreaDefaultY);
        }
    }

    public void checkMonsterCollision(Entity entity) {
        for (Monster monster : gPanel.monsters) {
            checkEntityCollision(entity, monster);
        }
    }
}
