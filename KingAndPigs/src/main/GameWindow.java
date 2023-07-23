package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameWindow {
	private JFrame jframe; 
	public GameWindow(GamePanel gamePanel) {
		jframe = new JFrame(); 
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        
        
		jframe.setSize(screenWidth, screenHeight);
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(gamePanel); 	
		jframe.setLocationRelativeTo(null);		// at bottom
		jframe.setVisible(true);
	}
	
	public JFrame getFrame() {
		return jframe; 
	}
}
