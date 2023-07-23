package main;

import java.awt.Font;
import java.awt.Graphics;

import entities.Pig;
import entities.Player;
import entities.Terrain;

public class Game implements Runnable {
	private GameWindow gameWindow;
	
	// gameWindow.setLocation(x + playerWidth/2, y + playerHeight/2)
	private GamePanel gamePanel; 
	private Thread gameThread; 
	
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private String finalSecs = "0"; 
	
	public Terrain[] blocks; 
	private Player kingHuman; 
	private Pig pig; 
	
	private int TIME_SECS; 

	public Game() {
		
		gamePanel = new GamePanel(this); 
		gameWindow = new GameWindow(gamePanel);
		
		blocks = gamePanel.getBlocks(); 
		kingHuman = new Player(500, 0, 5*78, 5*58, gamePanel); 
		pig = new Pig(0, 0, 5*190/5, 5*28, gamePanel); 
		
		TIME_SECS = (int)(System.currentTimeMillis()/1000); 
		gamePanel.requestFocus(); 

		startGameLoop();

	} 
	
	private void startGameLoop() {
		gameThread = new Thread(this); 
		gameThread.start();
	}
	
	public void resetTime() {
		TIME_SECS = (int)(System.currentTimeMillis()/1000); 
	}
	
	public void render(Graphics g) {
		if (kingHuman.getBar().getHp() <= 0) {
			
			gamePanel.setGameOver(); 
			// only change it the first time
			if (finalSecs == "0") {
				finalSecs = String.valueOf((int)(System.currentTimeMillis()/1000) - TIME_SECS); 
			}
			
			// then just render the text game over
			g.setFont(new Font("Arial", Font.PLAIN, 100));
			// right in the center of the screen
	        g.drawString("GAME OVER", gamePanel.getWidth()/2 - 300, gamePanel.getHeight()/2);
	        g.drawString(finalSecs, gamePanel.getWidth()/2 - 300, gamePanel.getHeight()/2 + 100); 
		} else {
			// also good to set a timer
			drawTimer(g); 
			
			kingHuman.render(g);
			pig.render(g);
			gamePanel.renderBlocks(g, blocks); 
		}
	}
	
	public void drawTimer(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 50));
		int seconds = (int)(System.currentTimeMillis()/1000) - TIME_SECS; 
		
		String secs = String.valueOf(seconds); 
		g.drawString(secs, gamePanel.getWidth() - 500, 200); 
	}
	
	public Player getPlayer() {
		return kingHuman; 
	}
	
	public Pig getPig() {
		return pig; 
	}
	
	public Terrain[] getBlocks() {
		return blocks; 
	}
	

	@Override
	public void run() {
		// frames vs updates
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;
		
		int totalUpdates = 0; 

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				// gameWindow.getFrame().setLocation(0, kingHuman.getHitbox().y); 
				// gamePanel.setLocation(new Point(0, kingHuman.getHitbox().y));
				kingHuman.update();
				pig.update(); 
				
				updates++;
				totalUpdates++; 
				deltaU--;
			}
			
			if (totalUpdates % 200 == 0) {
				pig.dash(); 
				kingHuman.mPressed(); 
				pig.mPressed();
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
		}

	}
}
