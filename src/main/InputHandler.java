package main;

import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

public class InputHandler {
    private ArrayList<String> inputList = new ArrayList<>();
    private boolean spacePressed = false;
    private boolean iPressed = false;
    private boolean attackPressed = false; // Ajouter une variable pour la touche d'attaque

    public void handleKeyPressed(KeyEvent event) {
        String keyName = event.getCode().toString();
        if (!inputList.contains(keyName)) {
            inputList.add(keyName);
        }
        if (keyName.equals("SPACE")) {
            spacePressed = true;
        }
        if (keyName.equals("I")) {
            iPressed = true;
        }
        if (keyName.equals("A")) { // Vérifier si la touche "A" est pressée
            attackPressed = true;
        }
    }

    public void handleKeyReleased(KeyEvent event) {
        String keyName = event.getCode().toString();
        inputList.remove(keyName);
        if (keyName.equals("SPACE")) {
            spacePressed = false;
        }
        if (keyName.equals("I")) {
            iPressed = false;
        }
        if (keyName.equals("A")) { // Vérifier si la touche "A" est relâchée
            attackPressed = false;
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

    public boolean isAttackPressed() { // Ajouter un getter pour la touche d'attaque
        return attackPressed;
    }
}
