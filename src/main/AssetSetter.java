package main;


import java.util.Arrays;

import object.OBJ_Grass;
import object.OBJ_Stone;
//import object.OBJ_Chest;
import object.SuperObject;

public class AssetSetter {

    GamePanel gPanel;

    public AssetSetter(GamePanel gPanel) {
        this.gPanel = gPanel;
    }

    public void initObject(Class<?> objClass, int x, int y) {
        int tileSize = GamePanel.getTileSize();
        try {
            int i = findEmptyIndex(gPanel.obj); // Trouve le premier indice null dans gPanel.obj
            if (i != -1) {
                SuperObject obj = (SuperObject) objClass.getDeclaredConstructor().newInstance();
                gPanel.obj[i] = obj;
                obj.getClass().getField("worldX").set(obj, x * tileSize);
                obj.getClass().getField("worldY").set(obj, y * tileSize);
            } else {
                System.out.println("Impossible d'ajouter un nouvel objet, tableau plein.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int findEmptyIndex(SuperObject[] objArray) {
        for (int i = 0; i < objArray.length; i++) {
            if (objArray[i] == null) {
                return i;
            }
        }
        // Si aucun indice n'est vide, on rallonge le tableau et on renvoie l'index de la première case vide
        SuperObject[] newArray = Arrays.copyOf(objArray, objArray.length + 1);
        gPanel.obj = newArray;
        return objArray.length;
    }
    public void setObject() {
    	
    	// Objets grass
    	
        initObject(OBJ_Grass.class, 2, 17);
        initObject(OBJ_Grass.class, 3, 17);
        initObject(OBJ_Grass.class, 2, 18);
        initObject(OBJ_Grass.class, 3, 18);
        initObject(OBJ_Grass.class, 2, 19);
        initObject(OBJ_Grass.class, 3, 19);
        initObject(OBJ_Grass.class, 7, 17);
        initObject(OBJ_Grass.class, 7, 18);
        initObject(OBJ_Grass.class, 8, 17);
        initObject(OBJ_Grass.class, 8, 18);
        initObject(OBJ_Grass.class, 9, 17);
        initObject(OBJ_Grass.class, 9, 18);
        initObject(OBJ_Grass.class, 14, 17);
        initObject(OBJ_Grass.class, 15, 17);
        initObject(OBJ_Grass.class, 14, 18);
        initObject(OBJ_Grass.class, 15, 18);
        initObject(OBJ_Grass.class, 10, 4);
        initObject(OBJ_Grass.class, 11, 3);
        initObject(OBJ_Grass.class, 17, 3);
        initObject(OBJ_Grass.class, 19, 4);
        initObject(OBJ_Grass.class, 26, 21);
        initObject(OBJ_Grass.class, 26, 22);
        initObject(OBJ_Grass.class, 27, 22);
        initObject(OBJ_Grass.class, 27, 23);
        initObject(OBJ_Grass.class, 28, 23);
        
        // Objets Stone
        
        initObject(OBJ_Stone.class, 8, 20);
        initObject(OBJ_Stone.class, 9, 20);
        initObject(OBJ_Stone.class, 13, 20);
        initObject(OBJ_Stone.class, 14, 20);
        initObject(OBJ_Stone.class, 15, 20);

    }
}
