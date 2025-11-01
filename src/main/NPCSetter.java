package main;

import entity.NPC;
import entity.NPC_Old;
import entity.NPC_Merchant;
import java.lang.reflect.Constructor;

public class NPCSetter {

    private GamePanel gPanel;

    public NPCSetter(GamePanel gPanel) {
        this.gPanel = gPanel;
    }

    public void initNPC(Class<? extends NPC> npcClass, int x, int y, int mapIndex) {
        int tileSize = GameConstants.TILE_SIZE;
        try {
            Constructor<? extends NPC> constructor = npcClass.getConstructor(GamePanel.class);
            NPC npc = constructor.newInstance(gPanel);
            npc.worldX = x * tileSize;
            npc.worldY = y * tileSize;
            npc.mapIndex = mapIndex; // Assigner la carte
            gPanel.addNPC(npc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNPCs() {
    	gPanel.getNpcs().clear();
        initNPC(NPC_Old.class, 16, 15, 0);
        initNPC(NPC_Merchant.class, 20, 20, 0);
    }
}
