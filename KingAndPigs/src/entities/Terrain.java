package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Terrain extends Entity {
	public BufferedImage block; 

	public Terrain(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		loadImage(); 
	}
	
	private void loadImage() {
		InputStream is = getClass().getResourceAsStream("/14-TileSets/Terrain(32x32).png"); 
		
		try {
			block = ImageIO.read(is).getSubimage(608/3 - 45, 416/2 - 50, 40, 40);;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void render (Graphics g) {
		g.drawImage(block, (int) x, (int) y, this.width, this.height, null); 
		drawHitbox(g); 
	}

}
