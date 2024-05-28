package entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Coeur {
    public static final int HEART_WIDTH = 32;
    public static final int HEART_HEIGHT = 32;
    private static BufferedImage heartImage;


    static {
        try {
            heartImage = ImageIO.read(Coeur.class.getResource("/tiles/coeur2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int x;
    private int y;

    public Coeur(int x, int y) {
        this.x = x;
        this.y = y;
    }


        public void draw(Graphics2D g) {
            g.drawImage(heartImage, x, y, HEART_WIDTH, HEART_HEIGHT, null);
        }




}

