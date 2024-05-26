package entity;

import java.util.ArrayList;
import java.util.List;
import object.SuperObject;

public class Inventory {

    private List<SuperObject> items; // Liste des objets de l'inventaire

    public Inventory() {
        items = new ArrayList<>();
    }

    // Ajoute un objet à l'inventaire
    public void addItem(SuperObject item) {
        items.add(item);
    }

    // Retire un objet de l'inventaire
    public void removeItem(SuperObject item) {
        items.remove(item);
    }

    // Retourne la liste des objets
    public List<SuperObject> getItems() {
        return items;
    }

    // Vérifie si l'inventaire contient un objet spécifique
    public boolean contains(SuperObject item) {
        return items.contains(item);
    }

    // Vérifie si l'inventaire contient un objet par son nom
    public boolean containsItem(String itemName) {
        return items.stream().anyMatch(item -> item.name.equalsIgnoreCase(itemName));
    }

    // Affiche le contenu de l'inventaire
    public void displayInventory() {
        System.out.println("Inventory:");
        items.forEach(item -> System.out.println("- " + item.name));
    }
}
