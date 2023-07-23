package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entities.Pig;
import entities.Player;
import entities.Terrain;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel{

	// but game panel has blocks
	// plus don't want to move it to player
	
	private int screenWidth; 
	private int screenHeight; 
	private int playerXOffset = 10, playerYOffset = 15; 
	public int PLATFORM_HEIGHT; 
	
	private MouseInputs mouseInputs; 
	private Game game; 
	
	public Boolean gameOver = false; 
	
	// creating mouse inputs and keyboard inputs
	public GamePanel(Game game) { 
		// close enough subimage
		this.game = game; 
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
		
        PLATFORM_HEIGHT = getPlatformHeight(); 
		
		getBlocks(); 
		
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	
	private BufferedImage importImg(String path) {
		// TODO Auto-generated method stub
		BufferedImage img = null; 
		InputStream is = getClass().getResourceAsStream(path); 
		
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return img; 
		
	}
	
	private int getPlatformHeight() {
		return screenHeight/2 + playerYOffset; 
	}
	
	private int getNumBlocks() {
		int count = 0; 
		int blockLeading = 100; 
		while (blockLeading <= screenWidth + playerXOffset + 100) {
			blockLeading += (100 - 20); 
			count += 1; 
		}
		return count; 
	}
	
	// collisions between blocks and player
	// notice that we can call getBlocks in the game
	public Terrain[] getBlocks() {
		Terrain[] blockArr = new Terrain[getNumBlocks()]; 
		
		int blockLeading = 100; 
		int i = 0; 
		while (blockLeading <= screenWidth + playerXOffset + 100) {
			blockArr[i] = new Terrain(blockLeading-100-playerXOffset, screenHeight/2+playerYOffset, 100, 100); 
			blockLeading += (100 - 20); 
			i += 1; 
		}
		
		return blockArr; 
	}
	
	public void renderBlocks (Graphics g, Terrain[] blocks) {
		for (int i = 0; i<blocks.length; i+=1) {
			blocks[i].render(g);
		}
	}
	
	public void setGameOver() {
		gameOver = !gameOver; 
	}
	
	
	
	// panel.getGame()
	public Game getGame() {
		return this.game; 
	}

	// paintComponent is called every frame @ 120fps
	public void paintComponent(Graphics g) {
		// calls the paint component
		super.paintComponent(g);
		// to check how good of a fit this is we can draw a line around it
		game.render(g); 
		
	}
	
	public int getWidth() {
		return screenWidth; 
	}
	
	public int getHeight() {
		return screenHeight; 
	}
}
