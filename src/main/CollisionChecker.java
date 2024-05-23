package main;

import entity.Entity;
import entity.Monster;

public class CollisionChecker {

    // Référence au panneau de jeu pour accéder à des méthodes et des propriétés communes
    private GamePanel gPanel;

    // Constructeur de la classe CollisionChecker
    public CollisionChecker(GamePanel gPanel) {
        this.gPanel = gPanel;
    }

    // Méthode pour vérifier les collisions avec les tuiles
    public void checkTile(Entity entity) {
        // Calcul des coordonnées des bords de la zone solide de l'entité
        int entityLeftWorldX = (int) (entity.worldX + entity.solidArea.getX());
        int entityRightWorldX = (int) (entity.worldX + entity.solidArea.getX() + entity.solidArea.getWidth());
        int entityTopWorldY = (int) (entity.worldY + entity.solidArea.getY());
        int entityBottomWorldY = (int) (entity.worldY + entity.solidArea.getY() + entity.solidArea.getHeight());

        // Calcul des colonnes et lignes de la grille de tuiles où se trouve l'entité
        int entityLeftCol = entityLeftWorldX / GamePanel.getTileSize();
        int entityRightCol = entityRightWorldX / GamePanel.getTileSize();
        int entityTopRow = entityTopWorldY / GamePanel.getTileSize();
        int entityBottomRow = entityBottomWorldY / GamePanel.getTileSize();

        // Numéros des tuiles pour les vérifications de collision
        int tileNum1, tileNum2;

        // Ajustement des coordonnées en fonction de la direction du mouvement
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

        // Vérification des tuiles pour les collisions
        tileNum1 = gPanel.tileManager.getMapTileNum()[entityLeftCol][entityTopRow];
        tileNum2 = gPanel.tileManager.getMapTileNum()[entityRightCol][entityBottomRow];

        // Si l'une des tuiles est une tuile de collision, activer la collision pour l'entité
        if (gPanel.tileManager.getTiles()[tileNum1].collision || gPanel.tileManager.getTiles()[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }

    // Méthode pour vérifier les collisions avec les objets
    public int checkObject(Entity entity, boolean player) {
        int index = 999; // Valeur par défaut si aucune collision n'est trouvée

        for (int i = 0; i < gPanel.obj.length; i++) {
            if (gPanel.obj[i] != null) {
                // Mettre à jour la position de la zone solide de l'entité
                entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
                entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

                // Mettre à jour la position de la zone solide de l'objet
                gPanel.obj[i].solidArea.setX(gPanel.obj[i].worldX + gPanel.obj[i].solidArea.getX());
                gPanel.obj[i].solidArea.setY(gPanel.obj[i].worldY + gPanel.obj[i].solidArea.getY());

                // Ajuster les coordonnées en fonction de la direction du mouvement
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

                // Vérification de la collision entre l'entité et l'objet
                if (entity.solidArea.getBoundsInLocal().intersects(gPanel.obj[i].solidArea.getBoundsInLocal())) {
                    if (gPanel.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i; // Retourner l'index de l'objet avec lequel il y a collision
                    }
                }

                // Réinitialiser les positions par défaut des zones solides
                entity.solidArea.setX(entity.solidAreaDefaultX);
                entity.solidArea.setY(entity.solidAreaDefaultY);
                gPanel.obj[i].solidArea.setX(gPanel.obj[i].solidAreaDefaultX);
                gPanel.obj[i].solidArea.setY(gPanel.obj[i].solidAreaDefaultY);
            }
        }

        return index;
    }

    // Méthode pour vérifier les collisions entre deux entités
    public void checkEntityCollision(Entity entity, Entity target) {
        if (target != null) {
            // Mettre à jour la position de la zone solide de l'entité
            entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
            entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

            // Mettre à jour la position de la zone solide de la cible
            target.solidArea.setX(target.worldX + target.solidArea.getX());
            target.solidArea.setY(target.worldY + target.solidArea.getY());

            // Ajuster les coordonnées en fonction de la direction du mouvement
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

            // Vérification de la collision entre l'entité et la cible
            if (entity.solidArea.getBoundsInLocal().intersects(target.solidArea.getBoundsInLocal())) {
                if (target.isSolid) {
                    entity.collisionOn = true;
                }
            }

            // Réinitialiser les positions par défaut des zones solides
            entity.solidArea.setX(entity.solidAreaDefaultX);
            entity.solidArea.setY(entity.solidAreaDefaultY);
            target.solidArea.setX(target.solidAreaDefaultX);
            target.solidArea.setY(target.solidAreaDefaultY);
        }
    }

    // Méthode pour vérifier les collisions avec les monstres
    public void checkMonsterCollision(Entity entity) {
        for (Monster monster : gPanel.monsters) {
            checkEntityCollision(entity, monster);
        }
    }
}
