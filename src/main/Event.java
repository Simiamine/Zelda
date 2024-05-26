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
        gPanel.currentMap = map;
        gPanel.player.worldX = x * GamePanel.getTileSize();
        gPanel.player.worldY = y * GamePanel.getTileSize();

    }

    public void addTeleportationSquare(int map, int x, int y, int width, int height, int targetMap, int targetX, int targetY) {
        teleportationSquares.add(new TeleportationSquare(map, x, y, width, height, targetMap, targetX, targetY));
    }

    public void checkTeleportation() {
        int playerX = gPanel.player.worldX / GamePanel.getTileSize();
        int playerY = gPanel.player.worldY / GamePanel.getTileSize();

        for (TeleportationSquare square : teleportationSquares) {
            if (gPanel.currentMap == square.map &&
                playerX >= square.x && playerX < square.x + square.width &&
                playerY >= square.y && playerY < square.y + square.height) {
                teleport(square.targetMap, square.targetX, square.targetY);
                break;
            }
        }
    }
    public void renderTeleportationSquares(GraphicsContext gc) {
        gc.setFill(Color.rgb(128, 0, 128, 0.25)); // Violet avec une opacité de 25%
        for (TeleportationSquare square : teleportationSquares) {
            if (gPanel.currentMap == square.map) {
                int screenX = square.x * GamePanel.getTileSize() - gPanel.player.worldX + gPanel.player.screenX;
                int screenY = square.y * GamePanel.getTileSize() - gPanel.player.worldY + gPanel.player.screenY;
                gc.fillRect(screenX, screenY, square.width * GamePanel.getTileSize(), square.height * GamePanel.getTileSize());
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
