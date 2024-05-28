package main;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.DynamicObstacle;
import entity.Player;
import tile.Obstacle;
import tile.TileManager;
import entity.Coeur;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel principal du jeu contenant la map principale
 *
 */
public class GamePanel extends JPanel implements Runnable {

    // Paramètres de l'écran
    final int ORIGINAL_TILE_SIZE = 16; // une tuile de taille 16x16
    final int SCALE = 3; // échelle utilisée pour agrandir l'affichage
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREE_ROW = 12; // ces valeurs donnent une résolution 4:3
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREE_ROW; // 576 pixels

    private final int HEART_SPACING = 10;
    private final int HEART_START_X = 10;
    private final int HEART_START_Y = 10;
    private Coeur[] hearts;

    // FPS : taux de rafraichissement
    int m_FPS;

    // Création des différentes instances (Player, KeyHandler, TileManager, GameThread ...)
    KeyHandler m_keyH;
    Thread m_gameThread;
    Player m_player;
    TileManager m_tileM;

    //Liste d'obstacle dynamique
    public List<DynamicObstacle> m_dynamicObstacles;

    // Image de fond
    BufferedImage backgroundImage;

    /**
     * Constructeur
     */
    public GamePanel() {
        m_FPS = 60;
        m_keyH = new KeyHandler();
        m_tileM = new TileManager(this); // Initialisation de TileManager avant de créer le joueur
        m_player = new Player(this, m_keyH, m_tileM); // Passer la référence à TileManager
// Initialisation de la liste des obstacles
        m_dynamicObstacles = new ArrayList<>(); //initialiser les obstacles
        //ajouter des obstacles
        m_dynamicObstacles.add(new DynamicObstacle(this,m_tileM,100,300));
        m_dynamicObstacles.add(new DynamicObstacle(this,m_tileM,400,300));

        hearts = new Coeur[5];
        int currentX = HEART_START_X;
        int currentY = HEART_START_Y;
        for (int i = 0; i < hearts.length; i++) {
            hearts[i] = new Coeur(currentX, currentY);
            currentX += Coeur.HEART_WIDTH + HEART_SPACING;
        }


        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(m_keyH);
        this.setFocusable(true);

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/tiles/back.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public TileManager getTileManager() {
        return m_tileM;
    }

    // Méthode pour générer et mettre à jour les obstacles dynamiques
//    public void updateObstacles() {
//        // Nettoyer la liste des obstacles
//        m_dynamicObstacles.clear();
//
//        // Générer les obstacles dynamiques sur les tuiles BRICK2
//        for (int i = 0; i < m_tileM.m_mapTileNum.length; i++) {
//            for (int j = 0; j < m_tileM.m_mapTileNum[0].length; j++) {
//                if (m_tileM.m_mapTileNum[i][j] == 1) { // Vérifier si c'est une tuile BRICK2
//                    int x = i * TILE_SIZE;
//                    int y = j * TILE_SIZE;
//                    DynamicObstacle obstacle = new DynamicObstacle(this, x, y, 4); // Créer un obstacle dynamique
//                    m_dynamicObstacles.add(obstacle); // Ajouter l'obstacle à la liste
//                }
//            }
//        }
//
//        // Mettre à jour la position des obstacles
//        for (DynamicObstacle obstacle : m_dynamicObstacles) {
//            obstacle.update();
//        }
//    }

    /**
     * Lancement du thread principal
     */
    public void startGameThread() {
        m_gameThread = new Thread(this);
        m_gameThread.start();
    }

    public void run() {

        double drawInterval = 1000000000 / m_FPS; // rafraichissement chaque 0.0166666 secondes
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (m_gameThread != null) { // Tant que le thread du jeu est actif

            // Permet de mettre à jour les différentes variables du jeu
            this.update();

            // Dessine sur l'écran le personnage et la map avec les nouvelles informations. la méthode "paintComponent" doit obligatoirement être appelée avec "repaint()"
            this.repaint();

            // Calcule le temps de pause du thread
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mise à jour des données des entités
     */
    public void update() {
        m_player.update();
        for (DynamicObstacle dynamicObstacle : m_dynamicObstacles) {
            dynamicObstacle.update();
        }

    }

    /**
     * Affichage des éléments
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Dessiner l'image de fond
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        }

        m_tileM.draw(g2);
        m_player.draw(g2);

        // Affichage des cœurs en fonction du nombre de vies du joueur
        for (int i = 0; i < m_player.getLives(); i++) {
            hearts[i].draw(g2);
        }


        for (DynamicObstacle dynamicObstacle : m_dynamicObstacles) {
            dynamicObstacle.draw(g2);
        }


        g2.dispose();
    }
}

//
//package main;
//
//import java.awt.Dimension;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import javax.imageio.ImageIO;
//import javax.swing.JPanel;
//import java.util.ArrayList;
//import java.util.List;
//
//import entity.DynamicObstacle;
//import entity.Player;
//import tile.TileManager;
//
//import java.io.IOException;
//
///**
// * Panel principal du jeu contenant la map principale
// *
// */
//public class GamePanel extends JPanel implements Runnable {
//
//    // Paramètres de l'écran
//    final int ORIGINAL_TILE_SIZE = 16; // une tuile de taille 16x16
//    final int SCALE = 3; // échelle utilisée pour agrandir l'affichage
//    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48
//    public final int MAX_SCREEN_COL = 16;
//    public final int MAX_SCREE_ROW = 12; // ces valeurs donnent une résolution 4:3
//    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
//    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREE_ROW; // 576 pixels
//
//    // FPS : taux de rafraichissement
//    int m_FPS;
//
//    // Création des différentes instances (Player, KeyHandler, TileManager, GameThread ...)
//    KeyHandler m_keyH;
//    Thread m_gameThread;
//    Player m_player;
//    TileManager m_tileM;
//    List<DynamicObstacle> m_dynamicObstacles;
//
//    // Image de fond
//    BufferedImage backgroundImage;
//
//    /**
//     * Constructeur
//     */
//    public GamePanel() {
//        m_FPS = 60;
//        m_keyH = new KeyHandler();
//        m_tileM = new TileManager(this); // Initialisation de TileManager avant de créer le joueur
//        m_player = new Player(this, m_keyH, m_tileM); // Passer la référence à TileManager
//        m_dynamicObstacles = new ArrayList<>();
//
//        // Ajout de quelques obstacles dynamiques pour tester
//        m_dynamicObstacles.add(new DynamicObstacle(this, 100, 100, 2));
//        m_dynamicObstacles.add(new DynamicObstacle(this, 200, 200, 3));
//        m_dynamicObstacles.add(new DynamicObstacle(this, 300, 300, 1));
//
//        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
//        this.setBackground(Color.black);
//        this.setDoubleBuffered(true);
//        this.addKeyListener(m_keyH);
//        this.setFocusable(true);
//
//        try {
//            backgroundImage = ImageIO.read(getClass().getResource("/tiles/back.jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public TileManager getTileManager() {
//        return m_tileM;
//    }
//
//    /**
//     * Lancement du thread principal
//     */
//    public void startGameThread() {
//        m_gameThread = new Thread(this);
//        m_gameThread.start();
//    }
//
//    public void run() {
//
//        double drawInterval = 1000000000 / m_FPS; // rafraichissement chaque 0.0166666 secondes
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while (m_gameThread != null) { // Tant que le thread du jeu est actif
//
//            // Permet de mettre à jour les différentes variables du jeu
//            this.update();
//
//            // Dessine sur l'écran le personnage et la map avec les nouvelles informations. la méthode "paintComponent" doit obligatoirement être appelée avec "repaint()"
//            this.repaint();
//
//            // Calcule le temps de pause du thread
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime / 1000000;
//
//                if (remainingTime < 0) {
//                    remainingTime = 0;
//                }
//
//                Thread.sleep((long) remainingTime);
//                nextDrawTime += drawInterval;
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * Mise à jour des données des entités
//     */
//    public void update() {
//        m_player.update();
//        for (DynamicObstacle obstacle : m_dynamicObstacles) {
//            obstacle.update();
//        }
//    }
//
//    /**
//     * Affichage des éléments
//     */
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
//
//        // Dessiner l'image de fond
//        if (backgroundImage != null) {
//            g2.drawImage(backgroundImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
//        }
//
//        m_tileM.draw(g2);
//        m_player.draw(g2);
//
//        // Générer et dessiner les obstacles dynamiques sur les tuiles BRICK2
//        for (int i = 0; i < m_tileM.m_mapTileNum.length; i++) {
//            for (int j = 0; j < m_tileM.m_mapTileNum[0].length; j++) {
//                if (m_tileM.m_mapTileNum[i][j] == 1) { // Vérifier si c'est une tuile BRICK2
//                    int x = i * TILE_SIZE;
//                    int y = j * TILE_SIZE;
//                    DynamicObstacle obstacle = new DynamicObstacle(this, x, y, 3); // Créer un obstacle dynamique
//                    m_dynamicObstacles.add(obstacle); // Ajouter l'obstacle à la liste
//                }
//            }
//        }
//
//        // Dessiner les obstacles dynamiques
//        int obstacleCount=0;
//        for (DynamicObstacle obstacle : m_dynamicObstacles) {
//            if (obstacleCount < 3) { // Limiter à trois obstacles dessinés
//                // Calculer les coordonnées de dessin en fonction des coordonnées de l'obstacle
//                int obstacleX = obstacle.m_x * TILE_SIZE;
//                int obstacleY = obstacle.m_y * TILE_SIZE;
//                // Appeler la méthode draw avec les coordonnées de dessin calculées
//                obstacle.draw(g2, obstacleX, obstacleY);
//                obstacleCount++;
//            } else {
//                break; // Sortir de la boucle une fois que trois obstacles ont été dessinés
//            }
//        }
//
//        g2.dispose();
//    }
//
//}
//
