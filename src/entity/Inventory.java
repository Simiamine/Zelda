package entity;

import java.util.ArrayList;
import java.util.List;
import object.SuperObject;

public class Inventory {
    private List<SuperObject> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(SuperObject item) {
        items.add(item);
    }

    public void removeItem(SuperObject item) {
        items.remove(item);
    }

    public List<SuperObject> getItems() {
        return items;
    }

    public boolean contains(SuperObject item) {
        return items.contains(item);
    }

    public void displayInventory() {
        System.out.println("Inventory:");
        for (SuperObject item : items) {
            System.out.println("- " + item.name);
        }
    }
}
