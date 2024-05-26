package main;

//import javafx.application.Platform;
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
                if (gPanel.gameState == gPanel.inventoryState) {
                    gPanel.player.useItem(gPanel.ui.slotRow * 4 + gPanel.ui.slotCol); // Utilise l'objet sélectionné
                } 
                break;
            case I:
                iPressed = true;
                if (gPanel.gameState == gPanel.playState) {
                    gPanel.gameState = gPanel.inventoryState;
                } else if (gPanel.gameState == gPanel.inventoryState) {
                    gPanel.gameState = gPanel.playState;
                }
                break;
            case A:
                attackPressed = true;
                break;
            case ENTER:
                if (gPanel.gameState == gPanel.dialogueState) {
                    if (!gPanel.ui.advanceDialogue()) {
                        gPanel.gameState = gPanel.playState; // Revenir en mode jeu si le dialogue est terminé
                    }
                } else if (gPanel.gameState == gPanel.playState) {
                    enterPressed = true;
                }
                break;
            case UP:
                if (gPanel.gameState == gPanel.playState) {
                    moveUp = true;
                } else if (gPanel.gameState == gPanel.inventoryState) {
                    gPanel.ui.slotRow = Math.max(gPanel.ui.slotRow - 1, 0);
                }
                break;
            case DOWN:
                if (gPanel.gameState == gPanel.playState) {
                    moveDown = true;
                } else if (gPanel.gameState == gPanel.inventoryState) {
                    gPanel.ui.slotRow = Math.min(gPanel.ui.slotRow + 1, 5); // assuming 6 rows
                }
                break;
            case LEFT:
                if (gPanel.gameState == gPanel.playState) {
                    moveLeft = true;
                } else if (gPanel.gameState == gPanel.inventoryState) {
                    gPanel.ui.slotCol = Math.max(gPanel.ui.slotCol - 1, 0);
                }
                break;
            case RIGHT:
                if (gPanel.gameState == gPanel.playState) {
                    moveRight = true;
                } else if (gPanel.gameState == gPanel.inventoryState) {
                    gPanel.ui.slotCol = Math.min(gPanel.ui.slotCol + 1, 3); // assuming 4 columns
                }
                break;
            case T:
                showTextDebug = !showTextDebug;
                break;
//            case R:
//                if (gPanel.gameState == gPanel.dialogueState) {
//                    gPanel.resetGame();
//                }
//                break;
//            case Q:
//                if (gPanel.gameState == gPanel.dialogueState) {
//                    Platform.exit();
//                }
//                break;
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
