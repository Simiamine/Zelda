package entity;

import java.util.ArrayList;
import java.util.List;
import object.SuperObject;

public class Inventory {

    // Liste des objets contenus dans l'inventaire
    private List<SuperObject> items;

    // Constructeur de la classe Inventory
    public Inventory() {
        // Initialisation de la liste des objets
        items = new ArrayList<>();
    }

    // Méthode pour ajouter un objet à l'inventaire
    public void addItem(SuperObject item) {
        items.add(item);
    }

    // Méthode pour supprimer un objet de l'inventaire
    public void removeItem(SuperObject item) {
        items.remove(item);
    }

    // Méthode pour obtenir la liste des objets de l'inventaire
    public List<SuperObject> getItems() {
        return items;
    }

    // Méthode pour vérifier si un objet est présent dans l'inventaire
    public boolean contains(SuperObject item) {
        return items.contains(item);
    }

    // Méthode pour afficher le contenu de l'inventaire dans la console
    public void displayInventory() {
        System.out.println("Inventory:");
        for (SuperObject item : items) {
            System.out.println("- " + item.name);
        }
    }
}
