package main;

import entity.NPC;
import entity.NPC_Old;
import java.lang.reflect.Constructor;

public class NPCSetter {

    private GamePanel gPanel;

    public NPCSetter(GamePanel gPanel) {
        this.gPanel = gPanel;
    }

    public void initNPC(Class<? extends NPC> npcClass, int x, int y) {
        int tileSize = GamePanel.getTileSize();
        try {
            Constructor<? extends NPC> constructor = npcClass.getConstructor(GamePanel.class);
            NPC npc = constructor.newInstance(gPanel);
            npc.worldX = x * tileSize;
            npc.worldY = y * tileSize;
            gPanel.npcs.add(npc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNPCs() {
        // Exemple d'ajout d'un NPC de type NPC_Old
        initNPC(NPC_Old.class, 16, 15); // Position en X et Y du deuxième NPC
    }
}
