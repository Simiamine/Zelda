package main;

import entity.Entity;
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

        int entityLeftCol = entityLeftWorldX / GameConstants.TILE_SIZE;
        int entityRightCol = entityRightWorldX / GameConstants.TILE_SIZE;
        int entityTopRow = entityTopWorldY / GameConstants.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / GameConstants.TILE_SIZE;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "UP":
                entityTopRow = (entityTopWorldY - entity.speed) / GameConstants.TILE_SIZE;
                tileNum1 = gPanel.getTileManager().getMapTileNum()[gPanel.getCurrentMap()][entityLeftCol][entityTopRow];
                tileNum2 = gPanel.getTileManager().getMapTileNum()[gPanel.getCurrentMap()][entityRightCol][entityTopRow];
                break;
            case "DOWN":
                entityBottomRow = (entityBottomWorldY + entity.speed) / GameConstants.TILE_SIZE;
                tileNum1 = gPanel.getTileManager().getMapTileNum()[gPanel.getCurrentMap()][entityLeftCol][entityBottomRow];
                tileNum2 = gPanel.getTileManager().getMapTileNum()[gPanel.getCurrentMap()][entityRightCol][entityBottomRow];
                break;
            case "LEFT":
                entityLeftCol = (entityLeftWorldX - entity.speed) / GameConstants.TILE_SIZE;
                tileNum1 = gPanel.getTileManager().getMapTileNum()[gPanel.getCurrentMap()][entityLeftCol][entityTopRow];
                tileNum2 = gPanel.getTileManager().getMapTileNum()[gPanel.getCurrentMap()][entityLeftCol][entityBottomRow];
                break;
            case "RIGHT":
                entityRightCol = (entityRightWorldX + entity.speed) / GameConstants.TILE_SIZE;
                tileNum1 = gPanel.getTileManager().getMapTileNum()[gPanel.getCurrentMap()][entityRightCol][entityTopRow];
                tileNum2 = gPanel.getTileManager().getMapTileNum()[gPanel.getCurrentMap()][entityRightCol][entityBottomRow];
                break;
            default:
                tileNum1 = tileNum2 = 0;
                break;
        }

        if (gPanel.getTileManager().getTiles()[tileNum1].collision || gPanel.getTileManager().getTiles()[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gPanel.getObjects().length; i++) {
            if (gPanel.getObject(i) != null && gPanel.getObject(i).mapIndex == gPanel.getCurrentMap()) {
                entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
                entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

                gPanel.getObject(i).solidArea.setX(gPanel.getObject(i).worldX + gPanel.getObject(i).solidArea.getX());
                gPanel.getObject(i).solidArea.setY(gPanel.getObject(i).worldY + gPanel.getObject(i).solidArea.getY());

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

                if (entity.solidArea.getBoundsInLocal().intersects(gPanel.getObject(i).solidArea.getBoundsInLocal())) {
                    if (gPanel.getObject(i).collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.solidArea.setX(entity.solidAreaDefaultX);
                entity.solidArea.setY(entity.solidAreaDefaultY);
                gPanel.getObject(i).solidArea.setX(gPanel.getObject(i).solidAreaDefaultX);
                gPanel.getObject(i).solidArea.setY(gPanel.getObject(i).solidAreaDefaultY);
            }
        }

        return index;
    }

    public void checkEntityCollision(Entity entity, Entity target) {
        if (target != null && target.mapIndex == gPanel.getCurrentMap()) {
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
        for (Monster monster : gPanel.getMonsters()) {
            if (monster.mapIndex == gPanel.getCurrentMap()) {
                checkEntityCollision(entity, monster);
            }
        }
    }
}
