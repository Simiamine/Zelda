package main;

/**
 * Constantes globales du jeu.
 * Centralise tous les nombres magiques pour faciliter la maintenance.
 */
public final class GameConstants {
    
    // Empêcher l'instanciation
    private GameConstants() {
        throw new AssertionError("Cette classe ne doit pas être instanciée");
    }
    
    // ==================== CONFIGURATION ÉCRAN ====================
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 14;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    
    // ==================== CONFIGURATION MONDE ====================
    public static final int MAX_WORLD_COL = 32;
    public static final int MAX_WORLD_ROW = 32;
    public static final int WORLD_WIDTH = MAX_WORLD_COL * 16;
    public static final int WORLD_HEIGHT = MAX_WORLD_ROW * 16;
    public static final int MAX_MAP = 10;
    
    // ==================== JOUEUR ====================
    public static final int PLAYER_MAX_RUBIES = 999;
    public static final int PLAYER_MAX_HEARTS = 6;
    public static final int PLAYER_DEFAULT_SPEED = 6;  // Réduit de 10 à 6 pour meilleur gameplay
    public static final int PLAYER_DEFAULT_FORCE = 1;
    public static final int PLAYER_DEFAULT_ATTACK_RANGE = 1;
    public static final int PLAYER_SWORD_FORCE = 3;
    public static final int PLAYER_SWORD_ATTACK_RANGE = 2;
    public static final int PLAYER_ATTACK_COOLDOWN = 15;  // Frames entre chaque attaque
    public static final int PLAYER_ATTACK_DURATION = 10;  // Durée de l'animation d'attaque
    public static final int PLAYER_SPRITE_WIDTH = SCALE * 16;
    public static final int PLAYER_SPRITE_HEIGHT = SCALE * 24;
    
    // ==================== POSITION INITIALE ====================
    public static final int PLAYER_START_WORLD_X = TILE_SIZE * 11 + 24;
    public static final int PLAYER_START_WORLD_Y = TILE_SIZE * 17;
    public static final int PLAYER_START_MAP = 0;
    
    // ==================== MONSTRES ====================
    public static final int MONSTER_DEFAULT_HEALTH = 30;
    public static final int MONSTER_DEFAULT_SPEED = 1;
    public static final int MONSTER_ATTACK_COOLDOWN = 120;
    public static final int MONSTER_ATTACK_DAMAGE = 1;
    
    // ==================== SPRITES ====================
    public static final int SPRITE_ANIMATION_SPEED = 3;
    public static final int SPRITE_FRAMES = 8;
    
    // ==================== INDICES ====================
    public static final int NO_OBJECT_FOUND = -1;
    public static final int OBJECT_NOT_FOUND_INDEX = 999;
    public static final int INITIAL_OBJECT_ARRAY_SIZE = 10;
    
    // ==================== ÉTATS DU JEU ====================
    public static final int GAME_STATE_PLAY = 1;
    public static final int GAME_STATE_PAUSE = 2;
    public static final int GAME_STATE_DIALOGUE = 3;
    public static final int GAME_STATE_CHARACTER = 4;
    public static final int GAME_STATE_INVENTORY = 5;
    public static final int GAME_STATE_COMMERCE = 6;
    
    // ==================== UI ====================
    public static final int UI_ICON_SIZE = 32;
    public static final int UI_HEART_SPACING = 40;
    public static final int UI_MARGIN = 20;
    public static final int UI_HEART_Y_POSITION = 60;
    public static final int UI_RUBY_Y_POSITION = 45;
    
    public static final int INVENTORY_FRAME_X = TILE_SIZE * 10;
    public static final int INVENTORY_FRAME_Y = TILE_SIZE * 2;
    public static final int INVENTORY_FRAME_WIDTH = TILE_SIZE * 5;
    public static final int INVENTORY_FRAME_HEIGHT = TILE_SIZE * 7;
    public static final int INVENTORY_SLOT_PADDING = 20;
    public static final int INVENTORY_COLUMNS = 4;
    public static final int INVENTORY_MAX_ROWS = 6;
    
    public static final int DIALOGUE_WINDOW_X = TILE_SIZE * 2;
    public static final int DIALOGUE_WINDOW_Y = TILE_SIZE * 10;
    public static final int DIALOGUE_WINDOW_WIDTH = SCREEN_WIDTH - (TILE_SIZE * 4);
    public static final int DIALOGUE_WINDOW_HEIGHT = TILE_SIZE * 3;
    
    public static final int TRADE_WINDOW_X = TILE_SIZE * 3;
    public static final int TRADE_WINDOW_Y = TILE_SIZE * 3;
    public static final int TRADE_WINDOW_WIDTH = TILE_SIZE * 10;
    public static final int TRADE_WINDOW_HEIGHT = TILE_SIZE * 6;
    
    // ==================== FICHIERS ====================
    public static final String FONT_PATH = "file:res/font/font.ttf";
    public static final int FONT_SIZE = 30;
    public static final int FONT_SIZE_SMALL = 20;
    
    public static final String RESOURCE_ICON = "file:res/icon.png";
    public static final String RESOURCE_RUBY_IMAGE = "file:res/ui/ruby.png";
    public static final String RESOURCE_HEART_IMAGE = "file:res/ui/heart.png";
    public static final String RESOURCE_HEART_EMPTY_IMAGE = "file:res/ui/heart_empty.png";
    
    public static final String MAP_SPAWN = "res/maps/spawn.txt";
    public static final String MAP_HOUSE = "res/maps/house.txt";
    
    public static final String TILE_DIRECTORY = "file:res/zeldatile/";
    public static final int MAX_TILES = 300;
    
    // ==================== COLLISION ====================
    public static final int PLAYER_COLLISION_X = 12;
    public static final int PLAYER_COLLISION_Y = 40;
    public static final int PLAYER_COLLISION_WIDTH = 24;
    public static final int PLAYER_COLLISION_HEIGHT = 24;
    public static final int PLAYER_INTERACTION_MARGIN = 1;
    
    // ==================== RENDERING ====================
    public static final double COLLISION_DEBUG_OPACITY = 0.25;
    public static final double SUBWINDOW_OPACITY = 0.80;
    public static final int SUBWINDOW_BORDER_WIDTH = 5;
    public static final int SUBWINDOW_CORNER_RADIUS = 35;
    public static final int SUBWINDOW_BORDER_CORNER_RADIUS = 25;
    
    // ==================== COMMERCE ====================
    public static final int ITEM_PRICE_DEFAULT = 10;
    
    // ==================== DIRECTIONS ====================
    public static final String DIRECTION_UP = "UP";
    public static final String DIRECTION_DOWN = "DOWN";
    public static final String DIRECTION_LEFT = "LEFT";
    public static final String DIRECTION_RIGHT = "RIGHT";
}

