package main;

import entity.NPC;
import entity.NPC_Merchant;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import object.SuperObject;
import java.util.logging.Logger;

/**
 * Gestionnaire de l'interface utilisateur du jeu.
 * Gère l'affichage des statistiques, de l'inventaire, des dialogues et des menus.
 */
public class UI {

    private static final Logger LOGGER = Logger.getLogger(UI.class.getName());
    private static final int MAX_LINE_WIDTH = GameConstants.DIALOGUE_WINDOW_WIDTH - (GameConstants.TILE_SIZE * 2);

    private final GamePanel gamePanel;
    private final GraphicsContext gc;
    private Image rubyImage;
    private Image heartImage;
    private Image heartEmptyImage;
    private Font customFont;
    
    public String currentDialogue = "";
    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.gc = gamePanel.getGraphicsContext2D();
        loadUIImages();
        loadCustomFont();
        
        LOGGER.info("UI initialisée avec succès");
    }

    public void setCurrentDialogue(String dialogue) {
        this.currentDialogue = wrapText(dialogue);
    }
    
    private void loadUIImages() {
        rubyImage = new Image(GameConstants.RESOURCE_RUBY_IMAGE);
        heartImage = new Image(GameConstants.RESOURCE_HEART_IMAGE);
        heartEmptyImage = new Image(GameConstants.RESOURCE_HEART_EMPTY_IMAGE);
    }

    private void loadCustomFont() {
        try {
            customFont = Font.loadFont(GameConstants.FONT_PATH, GameConstants.FONT_SIZE);
        } catch (Exception e) {
            LOGGER.warning("Impossible de charger la police personnalisée, utilisation de la police par défaut");
            customFont = Font.font("Verdana", FontWeight.BOLD, GameConstants.FONT_SIZE);
        }
    }

    public void renderPlayerStats(Player player) {
        gc.setFont(customFont);
        gc.setFill(Color.WHITE);

        // Afficher les rubis
        gc.drawImage(rubyImage, GameConstants.UI_MARGIN, GameConstants.UI_MARGIN, 
                     GameConstants.UI_ICON_SIZE, GameConstants.UI_ICON_SIZE);
        gc.fillText(String.valueOf(player.getRubies()), 
                    GameConstants.UI_MARGIN * 3, GameConstants.UI_RUBY_Y_POSITION);

        // Afficher les cœurs
        for (int i = 0; i < player.maxHearts; i++) {
            int heartX = GameConstants.UI_MARGIN + (i * GameConstants.UI_HEART_SPACING);
            if (i < player.getHearts()) {
                gc.drawImage(heartImage, heartX, GameConstants.UI_HEART_Y_POSITION, 
                           GameConstants.UI_ICON_SIZE, GameConstants.UI_ICON_SIZE);
            } else {
                gc.drawImage(heartEmptyImage, heartX, GameConstants.UI_HEART_Y_POSITION, 
                           GameConstants.UI_ICON_SIZE, GameConstants.UI_ICON_SIZE);
            }
        }

        // Afficher la force
        String forceText = "Force: " + player.getForce();
        double forceTextWidth = gc.getFont().getSize() * forceText.length() / 2;
        gc.fillText(forceText, gamePanel.getWidth() - forceTextWidth - GameConstants.UI_MARGIN, 
                   GameConstants.UI_RUBY_Y_POSITION);
    }

    public void renderInventory(Player player) {
        // Fenêtre d'inventaire
        int frameX = GameConstants.INVENTORY_FRAME_X;
        int frameY = GameConstants.INVENTORY_FRAME_Y;
        int frameWidth = GameConstants.INVENTORY_FRAME_WIDTH;
        int frameHeight = GameConstants.INVENTORY_FRAME_HEIGHT;
        
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        final int slotXStart = frameX + GameConstants.INVENTORY_SLOT_PADDING;
        final int slotYStart = frameY + GameConstants.INVENTORY_SLOT_PADDING;
        int slotX = slotXStart;
        int slotY = slotYStart;
        
        for (int i = 0; i < player.inventory.getItems().size(); i++) {
            SuperObject item = player.inventory.getItems().get(i);

            if (item.image != null) {
                gc.drawImage(item.image, slotX, slotY, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            } else {
                gc.setFill(Color.WHITE);
                gc.fillText(item.name, slotX + 10, slotY + 25);
            }

            slotX += GameConstants.TILE_SIZE;

            if ((i + 1) % GameConstants.INVENTORY_COLUMNS == 0) {
                slotX = slotXStart;
                slotY += GameConstants.TILE_SIZE;
            }
        }
        
        // Curseur de sélection
        int cursorX = slotXStart + (GameConstants.TILE_SIZE * slotCol);
        int cursorY = slotYStart + (GameConstants.TILE_SIZE * slotRow);
        int cursorWidth = GameConstants.TILE_SIZE;
        int cursorHeight = GameConstants.TILE_SIZE;

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        
    }
    
    public void drawDialogueScreen() {
        Font font = Font.loadFont(GameConstants.FONT_PATH, GameConstants.FONT_SIZE);

        // Fenêtre de dialogue
        int x = GameConstants.DIALOGUE_WINDOW_X;
        int y = GameConstants.DIALOGUE_WINDOW_Y; 
        int width = GameConstants.DIALOGUE_WINDOW_WIDTH;
        int height = GameConstants.DIALOGUE_WINDOW_HEIGHT;

        drawSubWindow(x, y, width, height);
        
        x += GameConstants.TILE_SIZE;
        y += GameConstants.TILE_SIZE;

        gc.setFont(font);
        gc.setFill(Color.WHITE);

        String wrappedDialogue = wrapText(currentDialogue);
        for (String line : wrappedDialogue.split("\n")) {
            gc.fillText(line, x, y);
            y += GameConstants.TILE_SIZE;
        }
    }
    
    public boolean advanceDialogue() {
        NPC npc = gamePanel.getPlayer().getFacingNPC();
        if (npc != null) {
            String dialogue = npc.speak();
            if (!dialogue.isEmpty()) {
                setCurrentDialogue(dialogue);
                return true;
            }
        }
        return false;
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
    
    public void drawVictoryScreen() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);

        String victoryMessage = "Congratulations! You have obtained the Triforce!";
        Font font = Font.loadFont("file:res/font/font.ttf", 30);
        gc.setFont(font);
        gc.setFill(Color.WHITE);

        // Use Text object to calculate the width of the string
        Text text = new Text(victoryMessage);
        text.setFont(font);
        double textWidth = text.getLayoutBounds().getWidth();

        // Calculate position to center the text
        int x = (GameConstants.SCREEN_WIDTH - (int) textWidth) / 2;
        int y = GameConstants.SCREEN_HEIGHT / 2;

        gc.fillText(victoryMessage, x, y);
    }
    
    public void showEndOptions() {
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.setFill(Color.WHITE);

        String replayOption = "Press R to Replay";
        String quitOption = "Press Q to Quit";

        // Use Text object to calculate the width of the strings
        Text replayText = new Text(replayOption);
        replayText.setFont(gc.getFont());
        double replayTextWidth = replayText.getLayoutBounds().getWidth();

        Text quitText = new Text(quitOption);
        quitText.setFont(gc.getFont());
        double quitTextWidth = quitText.getLayoutBounds().getWidth();

        // Calculate positions to center the texts
        int xReplay = (GameConstants.SCREEN_WIDTH - (int) replayTextWidth) / 2;
        int y = GameConstants.SCREEN_HEIGHT / 2 + 50;

        int xQuit = (GameConstants.SCREEN_WIDTH - (int) quitTextWidth) / 2;

        gc.fillText(replayOption, xReplay, y);
        gc.fillText(quitOption, xQuit, y + 30);
    }
    public void showTradeWindow(NPC_Merchant merchant) {
        // Fenêtre de commerce
        int frameX = GameConstants.TRADE_WINDOW_X;
        int frameY = GameConstants.TRADE_WINDOW_Y;
        int frameWidth = GameConstants.TRADE_WINDOW_WIDTH;
        int frameHeight = GameConstants.TRADE_WINDOW_HEIGHT;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        final int slotXStart = frameX + GameConstants.INVENTORY_SLOT_PADDING;
        final int slotYStart = frameY + GameConstants.INVENTORY_SLOT_PADDING;
        int slotX = slotXStart;
        int slotY = slotYStart;

        for (int i = 0; i < merchant.inventory.getItems().size(); i++) {
            SuperObject item = merchant.inventory.getItems().get(i);

            if (item.image != null) {
                gc.drawImage(item.image, slotX, slotY, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            } else {
                gc.setFill(Color.WHITE);
                gc.fillText(item.name, slotX + 10, slotY + 25);
            }

            gc.setFill(Color.YELLOW);
            gc.fillText(GameConstants.ITEM_PRICE_DEFAULT + " Rubies", slotX + 10, slotY + 45);

            slotX += GameConstants.TILE_SIZE;

            if ((i + 1) % GameConstants.INVENTORY_COLUMNS == 0) {
                slotX = slotXStart;
                slotY += GameConstants.TILE_SIZE;
            }
        }

        // Curseur
        int cursorX = slotXStart + (GameConstants.TILE_SIZE * slotCol);
        int cursorY = slotYStart + (GameConstants.TILE_SIZE * slotRow);
        int cursorWidth = GameConstants.TILE_SIZE;
        int cursorHeight = GameConstants.TILE_SIZE;

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // Bouton d'achat
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(frameX + frameWidth - GameConstants.TILE_SIZE * 3, 
                        frameY + frameHeight - GameConstants.TILE_SIZE, 
                        GameConstants.TILE_SIZE * 2, 
                        GameConstants.TILE_SIZE / 2, 10, 10);
        gc.setFill(Color.WHITE);
        gc.fillText("Buy", frameX + frameWidth - GameConstants.TILE_SIZE * 2.5, 
                   frameY + frameHeight - GameConstants.TILE_SIZE / 2);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color backgroundColor = Color.rgb(0, 0, 0, GameConstants.SUBWINDOW_OPACITY);
        gc.setFill(backgroundColor);
        gc.fillRoundRect(x, y, width, height, 
                        GameConstants.SUBWINDOW_CORNER_RADIUS, 
                        GameConstants.SUBWINDOW_CORNER_RADIUS);

        Color borderColor = Color.rgb(255, 255, 255);
        gc.setStroke(borderColor);
        gc.setLineWidth(GameConstants.SUBWINDOW_BORDER_WIDTH);
        gc.strokeRoundRect(x, y, width, height, 
                          GameConstants.SUBWINDOW_BORDER_CORNER_RADIUS, 
                          GameConstants.SUBWINDOW_BORDER_CORNER_RADIUS);
    }
}
