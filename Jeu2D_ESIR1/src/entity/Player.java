package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

/**
 * Définition du comportement d'un joueur
 *
 */
public class Player extends Entity {

    GamePanel m_gp;
    KeyHandler m_keyH;

    /**
     * Constructeur de Player
     * @param a_gp GamePanel, pannel principal du jeu
     * @param a_keyH KeyHandler, gestionnaire des touches 
     */
    public Player(GamePanel a_gp, KeyHandler a_keyH) {
        this.m_gp = a_gp;
        this.m_keyH = a_keyH;
        this.setDefaultValues();
        this.getPlayerImage();
    }

    /**
     * Initialisation des données membres avec des valeurs par défaut
     */
    protected void setDefaultValues() {
        m_x = 60;
        m_y = 498;
        m_speed = 4;
    }

    /**
     * Récupération de l'image du personnage
     */
    public void getPlayerImage() {
        //gestion des exceptions 
        try {
            m_idleImage = ImageIO.read(getClass().getResource("/player/pinnn.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mise à jour des données du joueur
     */
    public void update() {
        if (m_keyH.upPressed) {
            m_y -= m_speed;
        } else if (m_keyH.downPressed) {
            m_y += m_speed;
        } else if (m_keyH.leftPressed) {
            m_x -= m_speed;
        } else if (m_keyH.rightPressed) {
            m_x += m_speed;
        }
    }

    /**
     * Affichage de l'image du joueur dans la fenêtre du jeu
     * @param a_g2 Graphics2D 
     */
    public void draw(Graphics2D a_g2) {
        // récupère l'image du joueur
        BufferedImage l_image = m_idleImage;
        // affiche le personnage avec l'image "image", avec les coordonnées x et y, et de taille tileSize (16x16) sans échelle, et 48x48 avec échelle)
        a_g2.drawImage(l_image, m_x, m_y, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
    }
}
