package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import main.GamePanel;

public abstract class NPC extends Entity {

    // Tableau de dialogues que le NPC peut dire
    protected String[] dialogues;
    
    // Index actuel du dialogue en cours
    protected int dialogueIndex;
    
    // Image représentant le NPC
    protected Image npcImage;

    // Constructeur de la classe NPC
    public NPC(GamePanel gPanel) {
        super(gPanel);

        // Initialisation des propriétés du NPC
        direction = "DOWN";
        speed = 0; // NPC fixe par défaut
        dialogueIndex = 0;
        
        // Initialisation de la zone solide pour le NPC
        int tileSize = GamePanel.getTileSize();
        solidArea = new Rectangle(0, 0, tileSize, tileSize * 1.5); // Taille de la collision pour le NPC
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        collisionOn = true; // Activer la collision par défaut
        isSolid = true; // NPC est solide par défaut
        
        // Initialisation de l'inventaire pour le NPC
        inventory = new Inventory();
    }

    // Méthode pour définir les dialogues du NPC
    public void setDialogues(String[] dialogues) {
        this.dialogues = dialogues;
    }

    // Méthode pour que le NPC parle et retourne le dialogue actuel
    public String speak() {
        if (dialogues != null && dialogues.length > 0) {
            String dialogue = dialogues[dialogueIndex];
            dialogueIndex = (dialogueIndex + 1) % dialogues.length; // Passer au dialogue suivant
            return dialogue;
        }
        return "";
    }

    // Méthode abstraite pour obtenir l'image du NPC (à implémenter dans les sous-classes)
    public abstract void getImage();

    // Méthode pour mettre à jour l'état du NPC (par défaut, pas de mouvement)
    public void update() {
        // Pas de mouvement pour ce NPC par défaut
    }

    // Méthode pour dessiner le NPC à l'écran
    public void render(GraphicsContext gc) {
        // Calculer les coordonnées de l'écran en fonction de la position du joueur
        int screenX = worldX - gPanel.player.worldX + gPanel.player.screenX;
        int screenY = worldY - gPanel.player.worldY + gPanel.player.screenY;

        // Dessiner l'image du NPC si elle est chargée
        if (npcImage != null) {
            gc.drawImage(npcImage, screenX, screenY, GamePanel.getTileSize(), GamePanel.getTileSize() * 1.5); // Ajuster la taille de l'image rendue
        }
    }
}
