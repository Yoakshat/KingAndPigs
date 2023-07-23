package bars;

import java.awt.Color;
import java.awt.Graphics;

public class HealthBar {
	

	private int hp = 1000; 
	
	public HealthBar() {
	
	}
	
	public void attack() {
		hp -= 10; 
	}
	
	public void resetHp() {
		hp = 1000; 
	}
	
	// one attack can only be done if m is pressed again
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(5, 5, 500, 50); 
		g.setColor(Color.green);
		g.fillRect(5,5, hp/2, 50);
	}
	
	public int getHp() {
		return hp; 
	}
}
