package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * 
 * Gestionnaire des tiles du jeu
 *
 */
public class TileManager {
    GamePanel m_gp;           //panel du jeu principal
    Tile[] m_tile;            //tableau de toutes les tiles possibles dans le jeu
    int m_maxTiles = 10;      //nombre maximum de tiles chargeable dans le jeu
    int m_mapTileNum[][];     //répartition des tiles dans la carte du jeu
    private BufferedImage m_backgroundImage;  // Image de fond

    /**
     * Constructeur
     * @param gp
     */
    public TileManager(GamePanel gp) {
        this.m_gp = gp;
        m_tile = new Tile[m_maxTiles];
        m_mapTileNum = new int[gp.MAX_SCREEN_COL][gp.MAX_SCREE_ROW];
        this.getTileImage();
        this.loadMap("/maps/map.txt");
    }
    
    /**
     * Chargement de toutes les tuiles du jeu
     */
    public void getTileImage() {
        try {
            m_tile[0] = new Tile();
            m_tile[0].m_image = ImageIO.read(getClass().getResource("/tiles/GRASS.png"));
            
            m_tile[1] = new Tile();
            m_tile[1].m_image = ImageIO.read(getClass().getResource("/tiles/BRICK2.png"));
            
            m_tile[2] = new Tile();
            m_tile[2].m_image = ImageIO.read(getClass().getResource("/tiles/enemy.png"));
            
            m_tile[3] = new Tile();
            m_tile[3].m_image = ImageIO.read(getClass().getResource("/tiles/coeur2.png"));
            
            m_tile[4] = new Tile();
            m_tile[4].m_image = ImageIO.read(getClass().getResource("/tiles/SAND.png"));
            
            m_tile[5] = new Tile();
            m_tile[5].m_image = ImageIO.read(getClass().getResource("/tiles/crown.png"));

            // Charger l'image de fond d'écran
            m_backgroundImage = ImageIO.read(getClass().getResource("/tiles/back3.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Lecture du fichier txt contenant la map et chargement des tuiles correspondantes.
     */
    public void loadMap(String filePath) {
        // Charger le fichier txt de la map
        try {
            
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
            int col = 0;
            int row = 0;
            
            // Parcourir le fichier txt pour récupérer les valeurs
            while (row < m_gp.MAX_SCREE_ROW) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                while (col < m_gp.MAX_SCREEN_COL) {
                    int num = Integer.parseInt(numbers[col]);
                    m_mapTileNum[col][row] = num;
                    col++;
                }
                col = 0;
                row++;
            }
            
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Affichage de la carte avec les différentes tuiles
     * @param g2
     */
    public void draw(Graphics2D g2) {
        // Dessiner l'image de fond d'écran sur toute la surface
        if (m_backgroundImage != null) {
            g2.drawImage(m_backgroundImage, 0, 0, m_gp.SCREEN_WIDTH, m_gp.SCREEN_HEIGHT, null);
        }

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < m_gp.MAX_SCREEN_COL && row < m_gp.MAX_SCREE_ROW) {
            int tileNum = m_mapTileNum[col][row];

            // Ne dessiner que les tuiles qui ne sont pas des 0 (background)
            if (tileNum != 0) {
                g2.drawImage(m_tile[tileNum].m_image, x, y, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
            }

            col++;
            x += m_gp.TILE_SIZE;
            if (col == m_gp.MAX_SCREEN_COL) {
                col = 0;
                row++;
                x = 0;
                y += m_gp.TILE_SIZE;
            }
        }
    }
}
