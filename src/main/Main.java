package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage mainStage) throws Exception
    {
        mainStage.setTitle("The Legend of Zelda: A Link to the Past");

        mainStage.getIcons().add(new Image("file:res/icon.png"));
        BorderPane root = new BorderPane();

        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        GamePanel gamePanel = new GamePanel();

        root.setCenter(gamePanel);

        mainStage.show();
        
        // Initialiser le jeu après que la fenêtre soit affichée
        Platform.runLater(() -> {
            gamePanel.setupGame();
            gamePanel.startGameLoop();
        });
    }

    public static void main(String[] args) 
    {
        launch(args);
    }

}
