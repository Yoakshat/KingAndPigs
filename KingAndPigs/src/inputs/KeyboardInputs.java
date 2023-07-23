package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyboardInputs implements KeyListener {

	private GamePanel gamePanel;

	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			gamePanel.getGame().getPlayer().setLeft(false);
			break;
		case KeyEvent.VK_D:
			gamePanel.getGame().getPlayer().setRight(false);
			break;
		case KeyEvent.VK_SPACE: 
			gamePanel.getGame().getPlayer().setJump(false);
			break; 
		
		}
		
	}
	
	// just automate this with every frame
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			gamePanel.getGame().getPlayer().setLeft(true);
			break;
		case KeyEvent.VK_D:
			gamePanel.getGame().getPlayer().setRight(true);
			break;
		case KeyEvent.VK_SPACE: 
			if (gamePanel.gameOver) {
				gamePanel.setGameOver();
				// also reset hp here
				gamePanel.getGame().getPlayer().getBar().resetHp(); 
				gamePanel.getGame().resetTime();
			} else { 
				gamePanel.getGame().getPlayer().setJump(true);
			} 
			break; 
		}
	}
}