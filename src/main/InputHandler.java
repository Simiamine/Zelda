package main;

import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

public class InputHandler {
    private ArrayList<String> inputList = new ArrayList<>();
    private boolean spacePressed = false;

    public void handleKeyPressed(KeyEvent event) {
        String keyName = event.getCode().toString();
        if (!inputList.contains(keyName)) {
            inputList.add(keyName);
        }
        if (keyName.equals("SPACE")) {
            spacePressed = true;
        }
    }

    public void handleKeyReleased(KeyEvent event) {
        String keyName = event.getCode().toString();
        inputList.remove(keyName);
        if (keyName.equals("SPACE")) {
            spacePressed = false;
        }
    }

    public ArrayList<String> getInputList() {
        return inputList;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }
}
