package tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GamePanel;
//import main.UtilityTool;

import java.io.BufferedReader;
import java.io.FileInputStream;
//import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManager {

    private int tileSize; // Taille des tuiles
    private GamePanel gPanel; // Référence à GamePanel
    private Tile[] tiles; // Tableau de tuiles
    private int[][] mapTileNum; // Numéros des tuiles de la carte
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();
    
    public TileManager(GamePanel gPanel) {
        this.gPanel = gPanel;
        this.tileSize = GamePanel.getTileSize(); // Obtient la taille des tuiles depuis GamePanel
        
        
        tiles = new Tile[200];
        mapTileNum = new int[gPanel.WORLD_WIDTH][gPanel.WORLD_HEIGHT];

        loadTileImages();
        loadMap("file:res/maps/spawn.txt"); // Assurez-vous que loadMap est appelé ici pour remplir mapTileNum
    }


    private void loadTileImages() {
        try {
        	setup(0, "image_part_001.png", true);
        	setup(1, "image_part_002.png", true);
        	setup(2, "image_part_003.png", true);
        	setup(3, "image_part_004.png", true);
        	setup(4, "image_part_005.png", false);
        	setup(5, "image_part_006.png", false);
        	setup(6, "image_part_007.png", false);
        	setup(7, "image_part_008.png", false);
        	setup(8, "image_part_009.png", true);
        	setup(9, "image_part_010.png", true);
        	setup(10, "image_part_011.png", false);
        	setup(11, "image_part_012.png", false);
        	setup(12, "image_part_013.png", true);
        	setup(13, "image_part_014.png", true);
        	setup(14, "image_part_015.png", true);
        	setup(15, "image_part_016.png", true);
        	setup(16, "image_part_017.png", true);
        	setup(17, "image_part_018.png", false);
        	setup(18, "image_part_019.png", false);
        	setup(19, "image_part_020.png", false);
        	setup(20, "image_part_021.png", false);
        	setup(21, "image_part_022.png", false);
        	setup(22, "image_part_023.png", true);
        	setup(23, "image_part_024.png", true);
        	setup(24, "image_part_025.png", true);
        	setup(25, "image_part_026.png", true);
        	setup(26, "image_part_027.png", true);
        	setup(27, "image_part_028.png", false);
        	setup(28, "image_part_029.png", true);
        	setup(29, "image_part_030.png", true);
        	setup(30, "image_part_031.png", true);
        	setup(31, "image_part_032.png", true);
        	setup(32, "image_part_033.png", true);
        	setup(33, "image_part_034.png", true);
        	setup(34, "image_part_035.png", false);
        	setup(35, "image_part_036.png", false);
        	setup(36, "image_part_037.png", false);
        	setup(37, "image_part_038.png", false);
        	setup(38, "image_part_039.png", true);
        	setup(39, "image_part_040.png", true);
        	setup(40, "image_part_041.png", false);
        	setup(41, "image_part_042.png", false);
        	setup(42, "image_part_043.png", false);
        	setup(43, "image_part_044.png", false);
        	setup(44, "image_part_045.png", false);
        	setup(45, "image_part_046.png", false);
        	setup(46, "image_part_047.png", false);
        	setup(47, "image_part_048.png", false);
        	setup(48, "image_part_049.png", false);
        	setup(49, "image_part_050.png", false);
        	setup(50, "image_part_051.png", false);
        	setup(51, "image_part_052.png", false);
        	setup(52, "image_part_053.png", false);
        	setup(53, "image_part_054.png", false);
        	setup(54, "image_part_055.png", true);
        	setup(55, "image_part_056.png", true);
        	setup(56, "image_part_057.png", true);
        	setup(57, "image_part_058.png", true);
        	setup(58, "image_part_059.png", true);
        	setup(59, "image_part_060.png", true);
        	setup(60, "image_part_061.png", true);
        	setup(61, "image_part_062.png", false);
        	setup(62, "image_part_063.png", false);
        	setup(63, "image_part_064.png", false);
        	setup(64, "image_part_065.png", false);
        	setup(65, "image_part_066.png", false);
        	setup(66, "image_part_067.png", false);
        	setup(67, "image_part_068.png", false);
        	setup(68, "image_part_069.png", true);
        	setup(69, "image_part_070.png", false);
        	setup(70, "image_part_071.png", false);
        	setup(71, "image_part_072.png", true);
        	setup(72, "image_part_073.png", true);
        	setup(73, "image_part_074.png", true);
        	setup(74, "image_part_075.png", false);
        	setup(75, "image_part_076.png", true);
        	setup(76, "image_part_077.png", true);
        	setup(77, "image_part_078.png", true);
        	setup(78, "image_part_079.png", true);
        	setup(79, "image_part_080.png", true);
        	setup(80, "image_part_081.png", false);
        	setup(81, "image_part_082.png", false);
        	setup(82, "image_part_083.png", true);
        	setup(83, "image_part_084.png", true);
        	setup(84, "image_part_085.png", false);
        	setup(85, "image_part_086.png", true);
        	setup(86, "image_part_087.png", false);
        	setup(87, "image_part_088.png", true);
        	setup(88, "image_part_089.png", true);
        	setup(89, "image_part_090.png", true);
        	setup(90, "image_part_091.png", true);
        	setup(91, "image_part_092.png", true);
        	setup(92, "image_part_093.png", false);
        	setup(93, "image_part_094.png", true);
        	setup(94, "image_part_095.png", true);
        	setup(95, "image_part_096.png", true);
        	setup(96, "image_part_097.png", true);
        	setup(97, "image_part_098.png", true);
        	setup(98, "image_part_099.png", true);
        	setup(99, "image_part_100.png", true);
        	setup(100, "image_part_101.png", true);
        	setup(101, "image_part_102.png", true);
        	setup(102, "image_part_103.png", true);
        	setup(103, "image_part_104.png", true);
        	setup(104, "image_part_105.png", false);
        	setup(105, "image_part_106.png", false);
        	setup(106, "image_part_107.png", true);
        	setup(107, "image_part_108.png", true);
        	setup(108, "image_part_109.png", true);
        	setup(109, "image_part_110.png", true);
        	setup(110, "image_part_111.png", true);
        	setup(111, "image_part_112.png", true);
        	setup(112, "image_part_113.png", true);
        	setup(113, "image_part_114.png", true);
        	setup(114, "image_part_115.png", true);
        	setup(115, "image_part_116.png", true);
        	setup(116, "image_part_117.png", true);
        	setup(117, "image_part_118.png", false);
        	setup(118, "image_part_119.png", false);
        	setup(119, "image_part_120.png", true);
        	setup(120, "image_part_121.png", true);
        	setup(121, "image_part_122.png", true);
        	setup(122, "image_part_123.png", true);
        	setup(123, "image_part_124.png", true);
        	setup(124, "image_part_125.png", false);
        	setup(125, "image_part_126.png", false);
        	setup(126, "image_part_127.png", true);
        	setup(127, "image_part_128.png", true);
        	setup(128, "image_part_129.png", true);
        	setup(129, "image_part_130.png", true);
        	setup(130, "image_part_131.png", false);
        	setup(131, "image_part_132.png", true);
        	setup(132, "image_part_133.png", true);
        	setup(133, "image_part_134.png", false);
        	setup(134, "image_part_135.png", false);
        	setup(135, "image_part_136.png", false);
        	setup(136, "image_part_137.png", false);
        	setup(137, "image_part_138.png", true);
        	setup(138, "image_part_139.png", false);
        	setup(139, "image_part_140.png", false);
        	setup(140, "image_part_141.png", false);
        	setup(141, "image_part_142.png", true);
        	setup(142, "image_part_143.png", false);
        	setup(143, "image_part_144.png", false);
        	setup(144, "image_part_145.png", true);
        	setup(145, "image_part_146.png", false);
        	setup(146, "image_part_147.png", false);
        	setup(147, "image_part_148.png", false);
        	setup(148, "image_part_149.png", false);
        	setup(149, "image_part_150.png", false);
        	setup(150, "image_part_151.png", false);
        	setup(151, "image_part_152.png", false);
        	setup(152, "image_part_153.png", true);
        	setup(153, "image_part_154.png", true);
        	setup(154, "image_part_155.png", true);
        	setup(155, "image_part_156.png", true);
        	setup(156, "image_part_157.png", true);
        	setup(157, "image_part_158.png", true);
        	setup(158, "image_part_159.png", true);
        	setup(159, "image_part_160.png", true);
        	setup(160, "image_part_161.png", true);
        	setup(161, "image_part_162.png", true);
        	setup(162, "image_part_163.png", true);
        	setup(163, "image_part_164.png", true);
        	setup(164, "image_part_165.png", true);
        	setup(165, "image_part_166.png", false);
        	setup(166, "image_part_167.png", false);
        	setup(167, "image_part_168.png", false);
        	setup(168, "image_part_169.png", false);
        	setup(169, "image_part_170.png", false);
        	setup(170, "image_part_171.png", true);
        	setup(171, "image_part_172.png", false);
        	setup(172, "image_part_173.png", true);
        	setup(173, "image_part_174.png", true);
        	setup(174, "image_part_175.png", true);
        	setup(175, "image_part_176.png", true);
        	setup(176, "image_part_177.png", true);
        	setup(177, "image_part_178.png", false);
        	setup(178, "image_part_179.png", false);
        	setup(179, "image_part_180.png", true);
        	setup(180, "image_part_181.png", false);
        	setup(181, "image_part_182.png", false);
        	setup(182, "image_part_183.png", true);
        	setup(183, "image_part_184.png", false);
        	setup(184, "image_part_185.png", true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setup(int index, String imagePath, boolean collision) {
    	
    	
    	try {
    		getTiles()[index] = new Tile(new Image("file:res/zeldatile/"+ imagePath), collision);
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private void loadMap(String fileName) {
        try {
        	InputStream is = new FileInputStream("res/maps/spawn.txt"); // Chemin relatif pour accéder au fichier
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            setMapTileNum(new int[gPanel.WORLD_WIDTH][gPanel.WORLD_HEIGHT]);

            String line;
            int row = 0;
            
            while ((line = br.readLine()) != null && row < gPanel.WORLD_HEIGHT) {
                String[] numbers = line.trim().split("\\s+"); // Séparation des nombres par des espaces
                for (int col = 0; col < gPanel.WORLD_WIDTH && col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    getMapTileNum()[col][row] = num;
                }
                row++;
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render(GraphicsContext gc) {
        for (int col = 0; col < gPanel.WORLD_WIDTH; col++) {
            for (int row = 0; row < gPanel.WORLD_HEIGHT; row++) {
                int tileNum = getMapTileNum()[col][row];
                int screenX = col * tileSize - gPanel.player.worldX + gPanel.player.screenX;
                int screenY = row * tileSize - gPanel.player.worldY + gPanel.player.screenY;

                if (screenX + tileSize > 0 && screenX < gPanel.getWidth() && screenY + tileSize > 0 && screenY < gPanel.getHeight()) {
                    gc.drawImage(getTiles()[tileNum].getImage(), screenX, screenY, tileSize, tileSize);
                }
            }
        }
    }

	public int[][] getMapTileNum() {
		return mapTileNum;
	}

	public void setMapTileNum(int[][] mapTileNum) {
		this.mapTileNum = mapTileNum;
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[] tiles) {
		this.tiles = tiles;
	}
}