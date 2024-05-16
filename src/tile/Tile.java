package tile;

import javafx.scene.image.Image;

public class Tile {
    
    public Image image;
    public boolean collision;

    // Constructeur prenant une image et un booléen pour la collision
    public Tile(Image image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }

    // Méthode pour obtenir l'image de la tuile
    public Image getImage() {
        return image;
    }
}
