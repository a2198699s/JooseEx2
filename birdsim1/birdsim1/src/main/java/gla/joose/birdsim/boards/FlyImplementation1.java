package gla.joose.birdsim.boards;

import gla.joose.birdsim.pieces.Bird;
import java.util.Random;

public class FlyImplementation1 implements FlyBehaviourInterface {
	
	protected Random rand;
	Board b1;
	
	public FlyImplementation1 (Board b) { // pass in a board to work with
		this.b1 = b;
		
		rand = new Random();
		
	}
	

	
	public void fly(){
		
		//this implementation is the code from Board
		
		
		Bird bird = new Bird();
		
		int randRow = rand.nextInt((b1.getRows() - 3) + 1) + 0;
    	int randCol = rand.nextInt((b1.getColumns() - 3) + 1) + 0;
    	
		b1.place(bird,randRow, randCol);
		bird.setDraggable(false);
		bird.setSpeed(20);
		b1.updateStockDisplay();
		
		while(!b1.scareBirds){
			randRow = rand.nextInt((b1.getRows() - 3) + 1) + 0;
        	randCol = rand.nextInt((b1.getColumns() - 3) + 1) + 0; 
        	bird.moveTo(randRow, randCol);
    		bird.setSpeed(20);
			
		} 

		bird.remove();
		b1.updateStockDisplay();
		
	}
	
	

}

