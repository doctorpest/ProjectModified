package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class DynamicObstacle extends Entity {
    GamePanel m_gamePanel;
    BufferedImage m_obstacle;

    public DynamicObstacle(GamePanel gamePanel, int x, int y, int speed) {
        super(x, y, speed, loadImage()); // Appel au constructeur parent avec les arguments appropriés
        this.m_gamePanel = gamePanel;
    }

    private static BufferedImage loadImage() {
        try {
            return ImageIO.read(DynamicObstacle.class.getResource("/tiles/enemy.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Retourne null en cas d'erreur
        }
    }

    public void update() {
        m_x += m_speed;
        if (m_x + m_gamePanel.TILE_SIZE < 0) {
            m_x = m_gamePanel.SCREEN_WIDTH;
        }
    }

    public void draw(Graphics2D g, int x, int y) {
        g.drawImage(m_obstacle, x, y, m_gamePanel.TILE_SIZE, m_gamePanel.TILE_SIZE, null);
    }

}
