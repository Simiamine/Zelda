package main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;


public class Sound {
    MediaPlayer mediaPlayer;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/music/HyruleFieldloop.wav");

    }

    public void setFile(int i) {
        try {
            Media media = new Media(soundURL[i].toString());
            mediaPlayer = new MediaPlayer(media);
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

//Media media = new Media("/music/HyruleFieldloop.wav");
//mediaPlayer = new MediaPlayer(media);
//mediaPlayer.play();
//mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//mediaPlayer.play()