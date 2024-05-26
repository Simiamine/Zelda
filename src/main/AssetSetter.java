package main;

import java.util.Arrays;
import object.OBJ_Grass;
//import object.OBJ_Potion;
//import object.OBJ_Ruby;
import object.OBJ_Stone;
import object.OBJ_Triforce;
import object.SuperObject;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Exterminator;

public class AssetSetter {

    // Référence au panneau de jeu pour accéder à des méthodes et des propriétés communes
    GamePanel gPanel;

    // Constructeur de la classe AssetSetter
    public AssetSetter(GamePanel gPanel) {
        this.gPanel = gPanel;
    }

    // Méthode pour initialiser un objet dans le jeu
    public void initObject(Class<?> objClass, int x, int y, int mapIndex) {
        int tileSize = GamePanel.getTileSize();
        try {
            // Trouve le premier indice null dans gPanel.obj
            int i = findEmptyIndex(gPanel.obj);
            if (i != -1) {
                // Crée une nouvelle instance de l'objet
                SuperObject obj = (SuperObject) objClass.getDeclaredConstructor().newInstance();
                gPanel.obj[i] = obj;
                // Positionne l'objet dans le monde du jeu
                obj.getClass().getField("worldX").set(obj, x * tileSize);
                obj.getClass().getField("worldY").set(obj, y * tileSize);
                obj.mapIndex = mapIndex; 
            } else {
                System.out.println("Impossible d'ajouter un nouvel objet, tableau plein.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void initObjectexact(Class<?> objClass, int x, int y, int mapIndex) {

        try {
            // Trouve le premier indice null dans gPanel.obj
            int i = findEmptyIndex(gPanel.obj);
            if (i != -1) {
                // Crée une nouvelle instance de l'objet
                SuperObject obj = (SuperObject) objClass.getDeclaredConstructor().newInstance();
                gPanel.obj[i] = obj;
                // Positionne l'objet dans le monde du jeu
                obj.getClass().getField("worldX").set(obj, x );
                obj.getClass().getField("worldY").set(obj, y );
            } else {
                System.out.println("Impossible d'ajouter un nouvel objet, tableau plein.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void initChest(int x, int y, int mapIndex, SuperObject... items) {
        int tileSize = GamePanel.getTileSize();
        try {
            int i = findEmptyIndex(gPanel.obj);
            if (i != -1) {
                OBJ_Chest chest = new OBJ_Chest();
                gPanel.obj[i] = chest;
                chest.worldX = x * tileSize;
                chest.worldY = y * tileSize;
                chest.mapIndex = mapIndex;
                
                for (SuperObject item : items) {
                    chest.addItem(item);
                }
            } else {
                System.out.println("Impossible d'ajouter un nouvel objet, tableau plein.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour trouver le premier indice vide dans le tableau d'objets
    private int findEmptyIndex(SuperObject[] objArray) {
        for (int i = 0; i < objArray.length; i++) {
            if (objArray[i] == null) {
                return i;
            }
        }
        // Si aucun indice n'est vide, rallonge le tableau et renvoie l'index de la première case vide
        SuperObject[] newArray = Arrays.copyOf(objArray, objArray.length + 1);
        gPanel.obj = newArray;
        return objArray.length;
    }

    // Méthode pour initialiser les objets du jeu
    public void setObject() {
        
    	// Ajouter des objets de type "Grass" (herbe) à différentes positions
    	initObject(OBJ_Grass.class, 2, 17, 0);
    	initObject(OBJ_Grass.class, 3, 17, 0);
    	initObject(OBJ_Grass.class, 2, 18, 0);
    	initObject(OBJ_Grass.class, 3, 18, 0);
    	initObject(OBJ_Grass.class, 2, 19, 0);
    	initObject(OBJ_Grass.class, 3, 19, 0);
    	initObject(OBJ_Grass.class, 7, 17, 0);
    	initObject(OBJ_Grass.class, 7, 18, 0);
    	initObject(OBJ_Grass.class, 8, 17, 0);
    	initObject(OBJ_Grass.class, 8, 18, 0);
    	initObject(OBJ_Grass.class, 9, 17, 0);
    	initObject(OBJ_Grass.class, 9, 18, 0);
    	initObject(OBJ_Grass.class, 14, 17, 0);
    	initObject(OBJ_Grass.class, 15, 17, 0);
    	initObject(OBJ_Grass.class, 14, 18, 0);
    	initObject(OBJ_Grass.class, 15, 18, 0);
    	initObject(OBJ_Grass.class, 10, 4, 0);
    	initObject(OBJ_Grass.class, 11, 3, 0);
    	initObject(OBJ_Grass.class, 17, 3, 0);
    	initObject(OBJ_Grass.class, 19, 4, 0);
    	initObject(OBJ_Grass.class, 26, 21, 0);
    	initObject(OBJ_Grass.class, 26, 22, 0);
    	initObject(OBJ_Grass.class, 27, 22, 0);
    	initObject(OBJ_Grass.class, 27, 23, 0);
    	initObject(OBJ_Grass.class, 28, 23, 0);

    	// Ajouter des objets de type "Stone" (pierre) à différentes positions
    	initObject(OBJ_Stone.class, 8, 20, 0);
    	initObject(OBJ_Stone.class, 9, 20, 0);
    	initObject(OBJ_Stone.class, 13, 20, 0);
    	initObject(OBJ_Stone.class, 14, 20, 0);
    	initObject(OBJ_Stone.class, 15, 20, 0);
    	initObject(OBJ_Stone.class, 14, 31, 0);
    	initObject(OBJ_Stone.class, 15, 31, 0);
    	initObject(OBJ_Stone.class, 13, 31, 0);
    	initObject(OBJ_Stone.class, 0, 24, 0);
    	initObject(OBJ_Stone.class, 0, 25, 0);
    	initObject(OBJ_Stone.class, 0, 26, 0);
    	initObject(OBJ_Stone.class, 10, 0, 0);
    	initObject(OBJ_Stone.class, 11, 0, 0);
    	initObject(OBJ_Stone.class, 12, 0, 0);
    	initObject(OBJ_Stone.class, 13, 0, 0);
    	initObject(OBJ_Stone.class, 14, 0, 0);
    	initObject(OBJ_Stone.class, 15, 0, 0);
    	initObject(OBJ_Stone.class, 16, 0, 0);
    	initObject(OBJ_Stone.class, 17, 0, 0);
    	initObject(OBJ_Stone.class, 18, 0, 0);
    	initObject(OBJ_Stone.class, 19, 0, 0);
    	initObject(OBJ_Stone.class, 20, 0, 0);
    	initObject(OBJ_Stone.class, 21, 0, 0);
    	initObject(OBJ_Stone.class, 22, 0, 0);
    	initObject(OBJ_Stone.class, 23, 0, 0);
    	initObject(OBJ_Stone.class, 24, 0, 0);
    	initObject(OBJ_Stone.class, 25, 0, 0);
    	initObject(OBJ_Stone.class, 26, 0, 0);
    	initObject(OBJ_Stone.class, 27, 0, 0);
    	initObject(OBJ_Stone.class, 31, 23, 0);
    	initObject(OBJ_Stone.class, 31, 24, 0);
    	initObject(OBJ_Stone.class, 31, 25, 0);
    	initObject(OBJ_Stone.class, 31, 26, 0);
    	initObject(OBJ_Stone.class, 0, 16, 0);
    	initObject(OBJ_Stone.class, 0, 17, 0);
    	initObject(OBJ_Stone.class, 0, 18, 0);
    	initObject(OBJ_Stone.class, 0, 19, 0);
    	initObject(OBJ_Stone.class, 0, 20, 0);

    	// Ajouter des objets de type "Chest" (coffre) à différentes positions
    	initChest(10, 8, 1, new OBJ_Triforce());

    	// Porte
    	initObjectexact(OBJ_Door.class, 184*3, 256*3, 0);

    	initObject(OBJ_Exterminator.class, 10, 10, 0);
        

        
        
    }
}
