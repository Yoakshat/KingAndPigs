package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.GamePanel;
import utilz.HelperMethods;

public class Pig extends Entity {
	private BufferedImage pig; 
	private float xSpeed = 16.0f; 
	private float ySpeed = 4.0f; 
	
	// once we've finally reached the position reset to false again
	private boolean dash = false; 
	// to check this get platform height
	private boolean surfaceBeneath = false; 
	
	private int playerX; 
	private int playerY; 
	
	private GamePanel panel; 
	
	private int mPressed = 0;
	private int prevM = 0; 
	
	public Pig(int x, int y, int width, int height, GamePanel panel) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		this.panel = panel;  
		loadImage(); 
	}
	
	public void update() {
		// changes position but not instantaneously.	
		updateDash(); 
		updateSurface(); 
		applyGravity(); 
		updateHitbox(); 
	}
	
	public void updateSurface() {
		if ((y + height + 20) < panel.PLATFORM_HEIGHT) {
			surfaceBeneath = false; 
		} else {
			surfaceBeneath = true; 
			ySpeed = 4.0f; 
		}
	}

	private void loadImage() {
		InputStream is = getClass().getResourceAsStream("/02-KingPig/Attack(38x28).png"); 
		
		try {
			pig = ImageIO.read(is).getSubimage(0, 0, 190/5, 28);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void changeX(int newX) {
		
		Rectangle thePlayer = panel.getGame().getPlayer().hitBox; 
		if(!HelperMethods.collision(new Rectangle(newX, y, width, height), thePlayer)) {
			x = newX; 
		} else {
			if (prevM != mPressed) {
				panel.getGame().getPlayer().getBar().attack(); 
				prevM = mPressed; 
			}
		}
	}
	
	private void changeY(int newY) {
		Rectangle thePlayer = panel.getGame().getPlayer().hitBox; 
		if(!HelperMethods.collision(new Rectangle(x, newY, width, height), thePlayer)) {
			y = newY; 
		} else {
			if (prevM != mPressed) {
				panel.getGame().getPlayer().getBar().attack(); 
				prevM = mPressed; 
			}
		}
	}

	
	// dashes whenever hits space bar
	// but notice how he has to get there slowly (not super fast)
	public void dash() {
		// can only be attacked once until presses m again 
		dash = true;
		
		Player player = panel.getGame().getPlayer(); 
		
		Rectangle hitBox = player.getHitbox(); 
		
		// dash to position hitBox.x and hitBox.y 	
		playerX = hitBox.x; 
		playerY = hitBox.y; 
		
		
	}
	
	private void applyGravity() {
		if (!dash && !surfaceBeneath) {
			// basically push down
			changeY((int)(y + ySpeed)); 
			ySpeed += 0.5f; 
		}
	}
	
	// when we're dashing we are partly jumping
	// effects of airSpeed should only kick in when we stop dashing
	private void updateDash() {
		
		if (dash) { 
			
			int deltaX = (playerX - x); 
			int deltaY = (playerY - y); 
			
			// ratio deltaX / deltaY is 3:1 meaning (deltaY/deltaX) * pigSpeed
			
			// not checking deltaX checking playerX
			if (deltaX > 0) {
				changeX((int)(x + xSpeed)); 
				changeY((int)(y + (deltaY/deltaX) * xSpeed));
				// but if it does collide with a block or the player
				// it falls down 
				if (x >= playerX) { 
					dash = false; 
				}
			} 
			else { 
				changeX((int)(x - xSpeed)); 
				if (deltaX == 0) {
					changeY((int)(deltaY)); 
				} else { 
					changeY((int)(y - (deltaY/deltaX) * xSpeed)); 
				} 
				
				if (x <= playerX) {
					dash = false; 
				}
			}
		}

	}
	
	public void mPressed() {
		mPressed += 1; 
	}
	
	// now add collisions with ground and player
	// inAir is not defined by jump but simply by above platform 
	public void render(Graphics g) {
		g.drawImage(pig, (int) x, (int) y, this.width, this.height, null);
		drawHitbox(g); 
	}
	
	
}
