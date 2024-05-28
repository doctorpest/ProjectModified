package tile;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import javax.imageio.ImageIO;
import entity.Entity;
import main.GamePanel;
public class Obstacle{

	public int m_x, m_y;
	public int m_width = 48;
	public int m_height = 48;
	public BufferedImage m_image;
	public int speed =2;

	public Obstacle(int x, int y, BufferedImage image){
		m_x = x;
		m_y = y;
		m_image = image;
		//loadImage();
	}

//	private void loadImage(){
//		try{
//			m_image = ImageIO.read(getClass().getResource("/enemy/enemy.jpg"));
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//	}

	public void move(){
		m_x += speed;
		if (m_x <- m_width)
//			m_x = 800;// on réinitialise la position de l'obstacle
			// Vérifier les limites de l'écran pour inverser la direction si nécessaire
			if (m_x <= 0 || m_x >= 768 - m_width) { // Adapter SCREEN_WIDTH et m_width selon votre configuration
				speed = -speed;
			}
	}

	public void draw(Graphics2D g){
		g.drawImage(m_image,m_x, m_y, null);
	}

	public int getM_x(){return m_x;}
	public int getM_y(){return m_y;}
	public int getM_width(){return m_width;}
	public int getM_height(){return m_height;}

}

//public class Obstacle extends Entity{
//	private int speedX;
//	private int speedY;
//	private GamePanel m_gp;
//
//	public Obstacle(int x, int y, BufferedImage image, GamePanel gp) {
//		super(x, y, image);
//		this.speedX =1; //vitesse horizontale
//		this.speedY =1; //vitesse verticale
//		this.m_gp = gp;
//	}
//	public void update() {
//		// Mettre à jour la position de l'obstacle en fonction de sa vitesse
//		this.m_x += speedX;
//		this.m_y += speedY;
//
//		// Ajoutez ici des conditions pour gérer les collisions avec d'autres obstacles ou les limites de la carte
//		// Par exemple, si l'obstacle atteint un bord de l'écran, changez sa direction
//		if (m_x < 0 || m_x + m_width > m_gp.SCREEN_WIDTH) {
//			speedX = -speedX; // Changez la direction horizontale
//		}
//		if (m_y < 0 || m_y + m_height > m_gp.SCREEN_HEIGHT) {
//			speedY = -speedY; // Changez la direction verticale
//		}
//	}
//
//	@Override
//	public void draw(Graphics2D a_g2) {
//		a_g2.drawImage(m_idleImage,m_x,m_y,null);
//	}
//}