package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Entity {
	
	protected int x, y; 
	protected int width, height; 
	protected Rectangle hitBox; 
	
	public Entity(int x, int y, int width, int height) {
		this.x = x; 
		this.y = y; 
		this.width = width; 
		this.height = height; 
		
		hitBox = new Rectangle((int)this.x, (int)this.y, this.width, this.height); 
	}
	
	protected void drawHitbox(Graphics g) {
		g.setColor(Color.PINK);
		g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height); 
	}
	
	protected void updateHitbox() {
		hitBox.x = (int) x; 
		hitBox.y = (int) y; 
	}
	
	public Rectangle getHitbox() {
		return this.hitBox; 
	}
	
	// now that there's a hitbox you can check if any two rectangles overlap
	
	
}
