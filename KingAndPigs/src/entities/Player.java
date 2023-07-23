package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import bars.HealthBar;
import main.GamePanel;
import utilz.HelperMethods;

// add collisions to all walls
// for every character we need collision detection + gravity
public class Player extends Entity{
	private BufferedImage king; 
	private boolean left, right;
	private float playerSpeed = 4.0f; 
	
	private float ySpeed = 4.0f; 
	private boolean surfaceBeneath = false; 
	private GamePanel panel; 
	
	private boolean jump = false; 
	private HealthBar health; 
	
	private int mPressed = 0;
	private int prevM = 0; 
	
	private boolean launchRight = false; 
	private boolean launchLeft = false; 
	
	private int launchCount = 0; 
	
	// x and y is changing while stays the same for the boxes 
	public Player(int x, int y, int width, int height, GamePanel panel) {
		super(x, y, width, height);
		this.panel = panel;  
		this.health = new HealthBar(); 
		loadImage(); 
		// TODO Auto-generated constructor stub
	}
	
	// constantly call update with frame-updates
	public void update() { 
		applyGravity(); 
		updateSurface();
		updatePos(); 
		updateLaunch(); 
		updateHitbox(); 
	}
	
	public void render(Graphics g) {
		g.drawImage(king, x, y, width, height, null);
		drawHitbox(g); 
		health.draw(g);
	}
	
	private void loadImage() {
		InputStream is = getClass().getResourceAsStream("/01-King Human/Attack (78x58).png"); 
		
		try {
			king = ImageIO.read(is).getSubimage(0, 0, 78, 58);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateSurface() {
		if ((y + height) < panel.PLATFORM_HEIGHT) {
			surfaceBeneath = false; 
			if (jump == true) {
				ySpeed = -10.0f; 
				changeY((int)(y + ySpeed)); 
			}
		} else {
			surfaceBeneath = true; 
			// whenever hits the ground
			if (jump == true) {
				ySpeed = -20.0f; 
				changeY((int)(y + ySpeed)); 
				// after this line surfaceBeneath will be false
				// our gravity will launch into action
			} else {
				ySpeed = 4.0f; 
			}
		}
	}
	
	// the pig has the power
	// update depending on the frame
	private void updateLaunch() {
		if (launchRight == true) {
			// only change y like this the first time
			if(launchCount <= 5) {
				changeY((int)(y - 20)); 
			}
			
			if((surfaceBeneath == false) || (launchCount == 0)) {
				changeX((int)(x - 10)); 
			} else {
				launchRight = false; 
			}
			
			launchCount += 1; 
		} else if (launchLeft == true) {
			// give it like a jump 
			if (launchCount <= 5) {
				changeY((int)(y - 20)); 
			}
			// like a projectile
			if((surfaceBeneath == false) || (launchCount == 0)) {
				changeX((int)(x + 10)); 
			} else {
				launchLeft = false; 
			}
			launchCount += 1; 
		} 
		
	}
	

	
	// apply gravity is actually doing all of our jump power
	private void applyGravity() {
		if (!surfaceBeneath) {
			// basically push down
			changeY((int)(y + ySpeed)); 
			ySpeed += 0.5f; 
		}
	}
	
	// better way for collisions?!
	private void changeX(int newX) {
		
		
		Rectangle thePig = panel.getGame().getPig().hitBox; 
		
		if ((newX < 0) || (newX + width > panel.getWidth())) {
			return; 
		}
		
		if(!HelperMethods.collision(new Rectangle(newX, y, width, height), thePig)) {
			x = newX; 
		} else {
			if (x >= thePig.x) {
				// means you're on the right of the pig
				// you're going to also launch right 
				launchRight = true; 
			} else {
				launchLeft = true; 
			}
			
			
			if (prevM != mPressed) {
				health.attack(); 
				prevM = mPressed; 
			}
		}
	}
	
	private void changeY(int newY) {
		Rectangle thePig = panel.getGame().getPig().hitBox; 
		
		if(!HelperMethods.collision(new Rectangle(x, newY, width, height), thePig)) {
			y = newY; 
		} else {
			if (x >= thePig.x) {
				// means you're on the right of the pig
				// you're going to also launch right 
				launchRight = true; 
			} else {
				launchLeft = true; 
			}
			
			if (prevM != mPressed) {
				health.attack(); 
				prevM = mPressed; 
			}
		}
	}

	// called in keyboard inputs 
	private void updatePos() {
		// otherwise don't do anything
		// check for left and right

		if (left && !right) {
			// here you are going left
			// so collision should be checked in left direction
			changeX((int)(x - playerSpeed)); 
		} else if (right && !left) {
			changeX((int)(x + playerSpeed)); 
		}

	}
	
	public void resetDirBooleans() {
		left = false;
		right = false;
	}
	
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
	public void mPressed() {
		mPressed += 1; 
	}
	
	public HealthBar getBar() {
		return health; 
	}

}
