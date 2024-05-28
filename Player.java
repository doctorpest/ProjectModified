package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import tile.TileManager;

public class Player extends Entity {
    GamePanel m_gamePanel;
    KeyHandler m_keyHandler;
    TileManager m_tileManager;
    BufferedImage m_playerImage;
    private int lives =5;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, TileManager tileManager) {
        this.m_gamePanel = gamePanel;
        this.m_keyHandler = keyHandler;
        this.m_tileManager = tileManager;
        this.setDefaultValues();
        this.loadPlayerImage();
        this.lives =5;
    }

    protected void setDefaultValues() {
        m_x = 60;
        m_y = 498;
        m_speed = 4;
    }

    public void loadPlayerImage() {
        try {
            m_playerImage = ImageIO.read(getClass().getResource("/player/pinnn.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        int nextX = m_x;
        int nextY = m_y;

        if (m_keyHandler.upPressed) {
            nextY -= m_speed;
        } else if (m_keyHandler.downPressed) {
            nextY += m_speed;
        } else if (m_keyHandler.leftPressed) {
            nextX -= m_speed;
        } else if (m_keyHandler.rightPressed) {
            nextX += m_speed;
        }

        // Vérifier les limites de l'écran
        if (nextX >= 0 && nextX <= m_gamePanel.SCREEN_WIDTH - m_gamePanel.TILE_SIZE &&
                nextY >= 0 && nextY <= m_gamePanel.SCREEN_HEIGHT - m_gamePanel.TILE_SIZE) {
            // Vérifier la collision avec les tuiles BRICK2
            int tileX = nextX / m_gamePanel.TILE_SIZE;
            int tileY = nextY / m_gamePanel.TILE_SIZE;

            boolean canMove = true;

            // Vérifier les collisions avec les bords de la tuile BRICK2
            if (m_tileManager.m_mapTileNum[tileX][tileY] == 1) {
                canMove = false;
            }
            if (tileX + 1 < m_tileManager.m_mapTileNum.length && m_tileManager.m_mapTileNum[tileX + 1][tileY] == 1) {
                canMove = false;
            }
            if (tileY + 1 < m_tileManager.m_mapTileNum[0].length && m_tileManager.m_mapTileNum[tileX][tileY + 1] == 1) {
                canMove = false;
            }
            if (tileX + 1 < m_tileManager.m_mapTileNum.length && tileY + 1 < m_tileManager.m_mapTileNum[0].length && m_tileManager.m_mapTileNum[tileX + 1][tileY + 1] == 1) {
                canMove = false;
            }

            if (canMove) {
                m_x = nextX;
                m_y = nextY;
            }
        }

//        // Vérifier les collisions avec les obstacles dynamiques
//        for (DynamicObstacle obstacle : m_gamePanel.m_dynamicObstacles) {
//            if (collidesWith(obstacle)) {
//                // Gérer la collision avec l'obstacle
//                // Par exemple, réinitialiser la position du joueur
//                setDefaultValues();
//                break; // Sortir de la boucle dès qu'une collision est détectée
//            }
//        }

        //joueur entre en collision avec un obstacle
        for(DynamicObstacle obstacle : m_gamePanel.m_dynamicObstacles) {
            if (collidesWith(obstacle)) {

                lives--;
                break;
            }
        }
    }

    public int getLives(){return lives;}
    // Ajoutez cette méthode pour vérifier la collision avec un obstacle dynamique
    private boolean collidesWith(DynamicObstacle obstacle) {
        return m_x < obstacle.m_x + obstacle.m_width &&
                m_x + m_width > obstacle.m_x &&
                m_y < obstacle.m_y + obstacle.m_height &&
                m_y + m_height > obstacle.m_y;
    }


    public void draw(Graphics2D g) {
        g.drawImage(m_playerImage, m_x, m_y, m_gamePanel.TILE_SIZE, m_gamePanel.TILE_SIZE, null);
    }
}
