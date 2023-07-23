package utilz;

import java.awt.Rectangle;

public class HelperMethods {
	
	// just boxes that may have hit each other
	public static Boolean collision(Rectangle hit1, Rectangle hit2) {
		// use whatever one 
		int[][] allRects = subRects(hit1); 
		for(int i = 0; i < allRects.length; i+=1) {
			if(hit2.contains(allRects[i][0], allRects[i][1])) {
				return true; 
			}
		}
		return false; 
	}
	
	private static int[][] subRects(Rectangle hit1){
		int totalSquares = ((int)(hit1.width/5) + 1) * ((int)(hit1.height/5) + 1); 
		
		int[][] array = new int[totalSquares][2]; 
		
		int count = 0; 
		for (int i = 0; i <= hit1.width; i+=5) {
			for (int j=0; j <= hit1.height; j+=5) {
				
				// start at top left corner
				array[count][0] = hit1.x + i;  
				array[count][1] = hit1.y + j; 
				count += 1; 
			}
		}
		
		return array; 
		
		
	}
	
	
}