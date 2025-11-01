package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private GamePanel gPanel;
    private List<TeleportationSquare> teleportationSquares;

    public Event(GamePanel gPanel) {
        this.gPanel = gPanel;
        this.teleportationSquares = new ArrayList<>();
    }

    public void teleport(int map, int x, int y) {
        gPanel.setCurrentMap(map);
        gPanel.getPlayer().worldX = x * GameConstants.TILE_SIZE;
        gPanel.getPlayer().worldY = y * GameConstants.TILE_SIZE;
    }

    public void addTeleportationSquare(int map, int x, int y, int width, int height, int targetMap, int targetX, int targetY) {
        teleportationSquares.add(new TeleportationSquare(map, x, y, width, height, targetMap, targetX, targetY));
    }

    public void checkTeleportation() {
        int playerX = gPanel.getPlayer().worldX / GameConstants.TILE_SIZE;
        int playerY = gPanel.getPlayer().worldY / GameConstants.TILE_SIZE;

        for (TeleportationSquare square : teleportationSquares) {
            if (gPanel.getCurrentMap() == square.map &&
                playerX >= square.x && playerX < square.x + square.width &&
                playerY >= square.y && playerY < square.y + square.height) {
                teleport(square.targetMap, square.targetX, square.targetY);
                break;
            }
        }
    }
    
    public void renderTeleportationSquares(GraphicsContext gc) {
        gc.setFill(Color.rgb(128, 0, 128, 0.25));
        for (TeleportationSquare square : teleportationSquares) {
            if (gPanel.getCurrentMap() == square.map) {
                int screenX = square.x * GameConstants.TILE_SIZE - gPanel.getPlayer().worldX + gPanel.getPlayer().screenX;
                int screenY = square.y * GameConstants.TILE_SIZE - gPanel.getPlayer().worldY + gPanel.getPlayer().screenY;
                gc.fillRect(screenX, screenY, square.width * GameConstants.TILE_SIZE, square.height * GameConstants.TILE_SIZE);
            }
        }
    }
    
    private static class TeleportationSquare {
        int map, x, y, width, height, targetMap, targetX, targetY;

        TeleportationSquare(int map, int x, int y, int width, int height, int targetMap, int targetX, int targetY) {
            this.map = map;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.targetMap = targetMap;
            this.targetX = targetX;
            this.targetY = targetY;
        }
    }
}
