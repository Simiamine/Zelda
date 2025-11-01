package main;

import entity.Monster;
import entity.Monster_Goblin;
import entity.Monster_Goron;

import java.lang.reflect.Constructor;

public class MonsterSetter {

    private GamePanel gPanel;

    public MonsterSetter(GamePanel gPanel) {
        this.gPanel = gPanel;
    }

    public void initMonster(Class<? extends Monster> monsterClass, int x, int y, int mapIndex) {
        int tileSize = GameConstants.TILE_SIZE;
        try {
            Constructor<? extends Monster> constructor = monsterClass.getConstructor(GamePanel.class);
            Monster monster = constructor.newInstance(gPanel);
            monster.worldX = x * tileSize;
            monster.worldY = y * tileSize;
            monster.mapIndex = mapIndex; // Assigner la carte
            gPanel.addMonster(monster);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMonsters() {
    	gPanel.getMonsters().clear();
        initMonster(Monster_Goblin.class, 14, 30, 0);
        initMonster(Monster_Goron.class, 22, 23, 0);
    }
}
