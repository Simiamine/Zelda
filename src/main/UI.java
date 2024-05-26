package main;

import entity.NPC;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import object.SuperObject;

public class UI {

    private GamePanel gPanel;
    private GraphicsContext gc;
    private Image rubyImage;
    private Image heartImage;
    private Image heartEmptyImage;
    private Font customFont;
    
    public String currentDialogue = "";
    private static final int MAX_LINE_WIDTH = GamePanel.SCREEN_WIDTH - (GamePanel.TILE_SIZE * 4) - (GamePanel.TILE_SIZE * 2);
    
    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GamePanel gPanel) {
        this.gPanel = gPanel;
        this.gc = gPanel.getGraphicsContext2D();
        loadUIImages();
        loadCustomFont();
    }

    public void setCurrentDialogue(String dialogue) {
        this.currentDialogue = wrapText(dialogue);
    }
    
    private void loadUIImages() {
        rubyImage = new Image("file:res/ui/ruby.png");
        heartImage = new Image("file:res/ui/heart.png");
        heartEmptyImage = new Image("file:res/ui/heart_empty.png");
    }

    private void loadCustomFont() {
        try {
            customFont = Font.loadFont("file:res/font/font.ttf", 30);
        } catch (Exception e) {
            e.printStackTrace();
            customFont = Font.font("Verdana", FontWeight.BOLD, 30); // Fallback font
        }
    }

    public void renderPlayerStats(Player player) {
        gc.setFont(customFont);
        gc.setFill(Color.WHITE);

        // Draw rubies
        gc.drawImage(rubyImage, 20, 20, 32, 32);
        gc.fillText(String.valueOf(player.getRubies()), 60, 45);

        // Draw hearts
        for (int i = 0; i < player.maxHearts; i++) {
            if (i < player.getHearts()) {
                gc.drawImage(heartImage, 20 + (i * 40), 60, 32, 32);
            } else {
                gc.drawImage(heartEmptyImage, 20 + (i * 40), 60, 32, 32);
            }
        }

        // Draw force
        
        String forceText = "Force: " + player.getForce();
        double forceTextWidth = gc.getFont().getSize() * forceText.length() / 2;
        gc.fillText(forceText, gPanel.getWidth() - forceTextWidth - 20, 45);
    }

    public void renderInventory(Player player) {
        // FRAME
        int frameX = GamePanel.TILE_SIZE * 10;
        int frameY = GamePanel.TILE_SIZE * 2;
        int frameWidth = GamePanel.TILE_SIZE * 5;
        int frameHeight = GamePanel.TILE_SIZE * 7;
        
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        
//        gc.setFill(Color.BLACK);
//        gc.fillRect(100, 100, 600, 400);
//        gc.setFill(Color.WHITE);
//        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
//        gc.fillText("INVENTORY", 350, 130);
//
        for (int i = 0; i < player.inventory.getItems().size(); i++) {
            SuperObject item = player.inventory.getItems().get(i);

//            if (item == player.currentWeapon || item == player.currentShield) {
//                gc.setFill(Color.YELLOW);
//                gc.fillRoundRect(slotX, slotY, GamePanel.getTileSize(), GamePanel.getTileSize(), 10, 10);
//            }

            if (item.image != null) {
                gc.drawImage(item.image, slotX, slotY, GamePanel.getTileSize(), GamePanel.getTileSize());
            } else {
                gc.setFill(Color.WHITE);
                gc.fillText(item.name, slotX + 10, slotY + 25);
            }

            slotX += GamePanel.getTileSize();

            if ((i + 1) % 4 == 0) {
                slotX = slotXStart;
                slotY += GamePanel.getTileSize();
            }
            
            
        }
        
//        int y = 180;
//        for (SuperObject item : player.inventory.getItems()) {
//            gc.fillText(item.name, 120, y);
//            y += 30;
//        }
        
        // CURSOR
        int cursorX = slotXStart + (GamePanel.TILE_SIZE * slotCol);
        int cursorY = slotYStart + (GamePanel.TILE_SIZE * slotRow);
        int cursorWidth = GamePanel.TILE_SIZE;
        int cursorHeight = GamePanel.TILE_SIZE;

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        
//        int dFrameX = frameX;
//        int dframeY = frameY + frameHeight;
//        int dFrameWidth = frameWidth;
//        int dFrameHeight = GamePanel.TILE_SIZE * 3;
//        drawSubWindow(dFrameX, dframeY, dFrameWidth, dFrameHeight);
//        
//        int textX = dFrameX + 20;
//        int textY = dframeY + GamePanel.TILE_SIZE;
        
    }
    
    public void drawDialogueScreen() {

        Font font = Font.loadFont("file:res/font/font.ttf", 30);

        // WINDOW
        int x = GamePanel.TILE_SIZE * 2;
        int y = GamePanel.TILE_SIZE *10; 
        int width = GamePanel.SCREEN_WIDTH - (GamePanel.TILE_SIZE * 4);
        int height = GamePanel.TILE_SIZE * 3;

        drawSubWindow(x, y, width, height);
        

        
        x += GamePanel.TILE_SIZE;
        y += GamePanel.TILE_SIZE;

        gc.setFont(font);
        gc.setFill(Color.WHITE);

        String wrappedDialogue = wrapText(currentDialogue);
        for (String line : wrappedDialogue.split("\n")) {
            gc.fillText(line, x, y);
            y += GamePanel.TILE_SIZE;
        }
    }
    public boolean advanceDialogue() {
        NPC npc = gPanel.player.getFacingNPC();
        if (npc != null) {
            String dialogue = npc.speak();
            if (!dialogue.isEmpty()) {
                setCurrentDialogue(dialogue);
                return true; // Indique que le dialogue continue
            }
        }
        return false; // Indique que le dialogue est terminé
    }

    public String wrapText(String text) {
        Text tempText = new Text();
        tempText.setFont(Font.font("Arial", 30));

        StringBuilder wrappedText = new StringBuilder();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (!line.toString().isEmpty()) {
                tempText.setText(line.toString() + " " + word);
            } else {
                tempText.setText(word);
            }

            double textWidth = tempText.getBoundsInLocal().getWidth();

            if (textWidth < MAX_LINE_WIDTH) {
                if (!line.toString().isEmpty()) {
                    line.append(" ");
                }
                line.append(word);
            } else {
                wrappedText.append(line.toString()).append("\n");
                line = new StringBuilder(word);
            }
        }

        wrappedText.append(line.toString());
        return wrappedText.toString();
    }
    
    public void drawSubWindow(int x, int y, int width, int height) {
        
        Color color = Color.rgb(0, 0, 0, 0.80);
        gc.setFill(color);
        gc.fillRoundRect(x, y, width, height, 35, 35);

        color = Color.rgb(255, 255, 255);
        gc.setStroke(color);
        gc.setLineWidth(5);
        gc.strokeRoundRect(x, y, width, height, 25, 25);
    }
}
