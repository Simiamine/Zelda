package main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class Sound {
    MediaPlayer mediaPlayer;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/res/music/HyruleFieldloop.wav");
        soundURL[1] = getClass().getResource("/res/music/overworld.mp3");

    }

    public void setFile(int i) {
        try {
            if (soundURL[i] != null) {
                Media media = new Media(soundURL[i].toString());
                mediaPlayer = new MediaPlayer(media);
            } else {
                System.out.println("URL for index " + i + " is null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void loop() {
        if (mediaPlayer != null) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
