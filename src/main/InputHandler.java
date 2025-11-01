package main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class InputHandler implements EventHandler<KeyEvent> {
    private ArrayList<String> inputList = new ArrayList<>();
    private boolean spacePressed = false;
    private boolean iPressed = false;
    private boolean attackPressed = false;
    private boolean moveUp = false, moveDown = false, moveLeft = false, moveRight = false, enterPressed = false;
    private boolean showTextDebug = false;
    private GamePanel gPanel;

    public InputHandler(GamePanel gPanel) {
        this.gPanel = gPanel;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getEventType().toString()) {
            case "KEY_PRESSED":
                handleKeyPressed(event);
                break;

            case "KEY_RELEASED":
                handleKeyReleased(event);
                break;

            default:
                break;
        }
    }

    public void handleKeyPressed(KeyEvent event) {
        String keyName = event.getCode().toString();
        if (!inputList.contains(keyName)) {
            inputList.add(keyName);
        }
        switch (event.getCode()) {
            case SPACE:
                spacePressed = true;
                if (gPanel.getGameState() == GameConstants.GAME_STATE_INVENTORY) {
                    gPanel.getPlayer().useItem(gPanel.getUI().slotRow * 4 + gPanel.getUI().slotCol);
                } 
                break;
            case I:
                iPressed = true;
                if (gPanel.getGameState() == GameConstants.GAME_STATE_PLAY) {
                    gPanel.setGameState(GameConstants.GAME_STATE_INVENTORY);
                } else if (gPanel.getGameState() == GameConstants.GAME_STATE_INVENTORY) {
                    gPanel.setGameState(GameConstants.GAME_STATE_PLAY);
                }
                break;
            case A:
                attackPressed = true;
                break;
            case ENTER:
                if (gPanel.getGameState() == GameConstants.GAME_STATE_DIALOGUE) {
                    if (!gPanel.getUI().advanceDialogue()) {
                        gPanel.setGameState(GameConstants.GAME_STATE_PLAY);
                    }
                } else if (gPanel.getGameState() == GameConstants.GAME_STATE_PLAY) {
                    enterPressed = true;
                } else if (gPanel.getGameState() == GameConstants.GAME_STATE_COMMERCE) {
                    int itemIndex = gPanel.getUI().slotRow * 4 + gPanel.getUI().slotCol;
                    if (gPanel.getMerchant() != null && itemIndex < gPanel.getMerchant().inventory.getItems().size()) {
                        gPanel.getMerchant().trade(gPanel.getMerchant().inventory.getItems().get(itemIndex));
                    }
                }
                break;
            case UP:
                if (gPanel.getGameState() == GameConstants.GAME_STATE_PLAY) {
                    moveUp = true;
                } else if (gPanel.getGameState() == GameConstants.GAME_STATE_INVENTORY) {
                    gPanel.getUI().slotRow = Math.max(gPanel.getUI().slotRow - 1, 0);
                } else if (gPanel.getGameState() == GameConstants.GAME_STATE_COMMERCE) {
                    gPanel.getUI().slotRow = Math.max(gPanel.getUI().slotRow - 1, 0);
                }
                break;
            case DOWN:
                if (gPanel.getGameState() == GameConstants.GAME_STATE_PLAY) {
                    moveDown = true;
                } else if (gPanel.getGameState() == GameConstants.GAME_STATE_INVENTORY) {
                    gPanel.getUI().slotRow = Math.min(gPanel.getUI().slotRow + 1, GameConstants.INVENTORY_MAX_ROWS - 1);
                } else if (gPanel.getGameState() == GameConstants.GAME_STATE_COMMERCE) {
                    gPanel.getUI().slotRow = Math.min(gPanel.getUI().slotRow + 1, GameConstants.INVENTORY_MAX_ROWS - 1);
                }
                break;
            case LEFT:
                if (gPanel.getGameState() == GameConstants.GAME_STATE_PLAY) {
                    moveLeft = true;
                } else if (gPanel.getGameState() == GameConstants.GAME_STATE_INVENTORY) {
                    gPanel.getUI().slotCol = Math.max(gPanel.getUI().slotCol - 1, 0);
                } else if (gPanel.getGameState() == GameConstants.GAME_STATE_COMMERCE) {
                    gPanel.getUI().slotCol = Math.max(gPanel.getUI().slotCol - 1, 0);
                }
                break;
            case RIGHT:
                if (gPanel.getGameState() == GameConstants.GAME_STATE_PLAY) {
                    moveRight = true;
                } else if (gPanel.getGameState() == GameConstants.GAME_STATE_INVENTORY) {
                    gPanel.getUI().slotCol = Math.min(gPanel.getUI().slotCol + 1, GameConstants.INVENTORY_COLUMNS - 1);
                } else if (gPanel.getGameState() == GameConstants.GAME_STATE_COMMERCE) {
                    gPanel.getUI().slotCol = Math.min(gPanel.getUI().slotCol + 1, GameConstants.INVENTORY_COLUMNS - 1);
                }
                break;
            case T:
                showTextDebug = !showTextDebug;
                break;
            default:
                break;
        }
    }

    public void handleKeyReleased(KeyEvent event) {
        String keyName = event.getCode().toString();
        inputList.remove(keyName);
        switch (event.getCode()) {
            case SPACE:
                spacePressed = false;
                break;
            case I:
                iPressed = false;
                break;
            case A:
                attackPressed = false;
                break;
            case UP:
                moveUp = false;
                break;
            case DOWN:
                moveDown = false;
                break;
            case LEFT:
                moveLeft = false;
                break;
            case RIGHT:
                moveRight = false;
                break;
            default:
                break;
        }
    }

    public ArrayList<String> getInputList() {
        return inputList;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public boolean isIPressed() {
        return iPressed;
    }

    public boolean isAttackPressed() {
        return attackPressed;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public boolean isShowTextDebug() {
        return showTextDebug;
    }
}
