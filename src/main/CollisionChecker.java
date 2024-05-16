package main;

import entity.Entity;

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

        int tileNum1;
        int tileNum2;

        switch (entity.direction) {
            case "UP":
                entityTopRow = (int) ((entityTopWorldY - entity.speed) / GamePanel.getTileSize());
                tileNum1 = gPanel.tileManager.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gPanel.tileManager.getMapTileNum()[entityRightCol][entityTopRow];
                if (gPanel.tileManager.getTiles()[tileNum1].collision == true || gPanel.tileManager.getTiles()[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                
                break;

            case "DOWN":
                entityBottomRow = (int) ((entityBottomWorldY + entity.speed) / GamePanel.getTileSize());
                tileNum1 = gPanel.tileManager.getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gPanel.tileManager.getMapTileNum()[entityRightCol][entityBottomRow];
                if (gPanel.tileManager.getTiles()[tileNum1].collision == true || gPanel.tileManager.getTiles()[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
        
            break;

            case "RIGHT":
                entityRightCol = (int) ((entityRightWorldX + entity.speed) / GamePanel.getTileSize());
                tileNum1 = gPanel.tileManager.getMapTileNum()[entityRightCol][entityTopRow];
                tileNum2 = gPanel.tileManager.getMapTileNum()[entityRightCol][entityBottomRow];
                if (gPanel.tileManager.getTiles()[tileNum1].collision == true || gPanel.tileManager.getTiles()[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }

            
            break;

            case "LEFT":
                entityLeftCol = (int) ((entityLeftWorldX - entity.speed) / GamePanel.getTileSize());
                tileNum1 = gPanel.tileManager.getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gPanel.tileManager.getMapTileNum()[entityLeftCol][entityBottomRow];
                if (gPanel.tileManager.getTiles()[tileNum1].collision == true || gPanel.tileManager.getTiles()[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        
            default:
                break;
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
                        if (entity.solidArea.getBoundsInLocal().intersects(gPanel.obj[i].solidArea.getBoundsInLocal()))
                        	if(gPanel.obj[i].collision == true) {
                        		entity.collisionOn = true;
                        	}
                        if(player == true) {
                        	index = i;
                        }
                        break;
                    case "DOWN":
                        entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
                        if (entity.solidArea.getBoundsInLocal().intersects(gPanel.obj[i].solidArea.getBoundsInLocal()))
                        	if(gPanel.obj[i].collision == true) {
                        		entity.collisionOn = true;
                        	}
                        if(player == true) {
                        	index = i;
                        }
                        break;
	                        
                    case "LEFT":
                        entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
                        if (entity.solidArea.getBoundsInLocal().intersects(gPanel.obj[i].solidArea.getBoundsInLocal()))
                        	if(gPanel.obj[i].collision == true) {
                        		entity.collisionOn = true;
                        	}
                        if(player == true) {
                        	index = i;
                        }
                        break;
                    case "RIGHT":
                        entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
                        if (entity.solidArea.getBoundsInLocal().intersects(gPanel.obj[i].solidArea.getBoundsInLocal()))
                        	if(gPanel.obj[i].collision == true) {
                        		entity.collisionOn = true;
                        	}
                        if(player == true) {
                        	index = i;
                        }
                        break;


                    default:
                        break;
                }

                entity.solidArea.setX(entity.solidAreaDefaultX);
                entity.solidArea.setY(entity.solidAreaDefaultY);
                gPanel.obj[i].solidArea.setX(gPanel.obj[i].solidAreaDefaultX);
                gPanel.obj[i].solidArea.setY(gPanel.obj[i].solidAreaDefaultY);
            }
        }
        
        return index;
    }
}
