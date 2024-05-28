package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.GamePanel;
import tile.TileManager;

import java.awt.Graphics2D;


public class DynamicObstacle extends Entity {
    GamePanel m_gamePanel;
    //BufferedImage m_obstacle;
    TileManager m_tileM;
    private int direction; // 1 for right, -1 for left


    public DynamicObstacle(GamePanel gamePanel, TileManager tileM, int startX, int startY) {
        this.m_gamePanel = gamePanel;
        this.m_tileM = tileM;
        this.direction = 1;
        this.setDefaultValues(startX, startY);
        this.getEnemyImage();
    }

    /**
     * Initialisation des données membres avec des valeurs par défaut
     */
    protected void setDefaultValues(int startX, int startY) {
        m_x = startX;
        m_y = startY;
        m_speed = 5;
    }

    /**
     * Récupération de l'image de l'ennemi
     */
    public void getEnemyImage() {
        try {
            m_idleImage = ImageIO.read(getClass().getResource("/tiles/enemy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static BufferedImage loadImage() {
//        try {
//            return ImageIO.read(DynamicObstacle.class.getResource("/tiles/enemy.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null; // Retourne null en cas d'erreur
//        }
//    }

//    public void update() {
//        m_x += m_speed;
//        if (m_x + m_gamePanel.TILE_SIZE < 0) {
//            m_x = m_gamePanel.SCREEN_WIDTH;
//        }
//    }

    /**
     * Mise à jour des données de l'ennemi
     */
    public void update() {
        int nextX = m_x + (direction * m_speed);

        // Vérifier les limites de l'écran
        if (nextX < 0 || nextX > m_gamePanel.SCREEN_WIDTH - m_gamePanel.TILE_SIZE) {
            direction *= -1; // Changer de direction lorsqu'il atteint les bords de l'écran
        } else {
            // Vérifier la collision avec les tuiles BRICK2
            int tileX = nextX / m_gamePanel.TILE_SIZE;
            int tileY = (m_y + m_gamePanel.TILE_SIZE - 1) / m_gamePanel.TILE_SIZE; // vérifier la tuile sous l'ennemi

            if (m_tileM.m_mapTileNum[tileX][tileY] != 1) {
                direction *= -1; // Changer de direction si l'ennemi ne peut pas marcher sur la tuile suivante
            } else {
                m_x = nextX;
            }
        }
    }

    /**
     * Affichage de l'image de l'ennemi dans la fenêtre du jeu
     * @param a_g2 Graphics2D
     */
    public void draw(Graphics2D a_g2) {
        BufferedImage l_image = m_idleImage;
        a_g2.drawImage(l_image, m_x, m_y, m_gamePanel.TILE_SIZE, m_gamePanel.TILE_SIZE, null);
    }

//    public void draw(Graphics2D g, int x, int y) {
//        g.drawImage(m_obstacle, x, y, m_gamePanel.TILE_SIZE, m_gamePanel.TILE_SIZE, null);
//    }

//    public void draw(Graphics2D g) {
//        // Dessiner l'obstacle (par exemple, un rectangle rouge pour la démonstration)
//        g.setColor(Color.RED);
//        g.fillRect(m_x, m_y, m_gamePanel.TILE_SIZE, m_gamePanel.TILE_SIZE);
//    }

}
