package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Entit? de base du jeu
 *
 */
public abstract class Entity {
	public int m_x, m_y;				//position sur la map
	public int m_speed;					//D?placement de l'entit?
	public int m_width = 30;
	public int m_height = 30;
	public BufferedImage m_idleImage; 	//Une image de l'entit?

	public Entity() {
		// Initialisation par défaut
		this.m_x = 0;
		this.m_y = 0;
		this.m_speed = 0;
		this.m_idleImage = null;
	}
	Entity(int x, int y, int speed, BufferedImage idleImage) {
		m_x = x;
		m_y = y;
		m_speed = speed;
		m_idleImage = idleImage;
	}

}
