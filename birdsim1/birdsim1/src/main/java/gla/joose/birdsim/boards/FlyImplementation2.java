package gla.joose.birdsim.boards;

import gla.joose.birdsim.pieces.Bird;
import java.util.Random;
import gla.joose.birdsim.pieces.Grain;
import gla.joose.birdsim.pieces.Piece;
import gla.joose.birdsim.util.*;


public class FlyImplementation2 implements FlyBehaviourInterface {
	
	protected Random rand;
	Board b2;
	int indicator;
	
	public FlyImplementation2 (Board b) { // pass in a board to work with
		this.b2 = b;
		
		rand = new Random();
		
	}
		public void fly(){
			Bird bird = new Bird();
			
			int randRow = rand.nextInt((b2.getRows() - 3) + 1) + 0;
	    	int randCol = rand.nextInt((b2.getColumns() - 3) + 1) + 0;    	
			b2.place(bird,randRow, randCol);
			bird.setDraggable(false);
			bird.setSpeed(20);
			b2.updateStockDisplay();
			
			while(!b2.scareBirds){
				DistanceMgr dmgr = new DistanceMgr();
				int current_row = bird.getRow();
				int current_col = bird.getColumn();
				
				synchronized(b2.allPieces){
					for (int i=0;i< b2.getAllPieces().size(); i++) {
		                Piece piece = b2.getAllPieces().get(i);
		                if(piece instanceof Grain){
		                	
		                int dist_from_food_row = current_row - piece.getRow();
			                	int dist_from_food_col = piece.getColumn() - current_col;
			                	Distance d = null;
			                	if(dist_from_food_row <= dist_from_food_col){
			                		d = new Distance(bird, (Grain)piece, dist_from_food_row, dist_from_food_col);
			                	}
			                	else{
			               		 	d = new Distance(bird, (Grain)piece, dist_from_food_row, dist_from_food_col);
			                	}                    	
			                	dmgr.addDistance(d);		
		                	
		                	
		                	}
						}	       
					}
				////
				
				Distance distances[] = dmgr.getDistances();
				boolean movedone = false;
				


				for(int i =0; i< distances.length;i++){
					Distance d = distances[i];
					
					if(d.getRowDist() <= d.getColDist()){

						
						if(d.getRowDist() >0){
							boolean can_move_down= bird.canMoveTo(current_row-1, current_col);
				    		if(can_move_down){
								bird.moveTo(current_row-1, current_col);
								movedone = true;
								break;
							}
						}
						else if(d.getRowDist() < 0){
							boolean can_move_down= bird.canMoveTo(current_row+1, current_col);
				    		if(can_move_down){
								bird.moveTo(current_row+1, current_col);
								movedone = true;
								break;
							}
						}
						else if(d.getRowDist() ==0){
							if(d.getColDist() >0){
								boolean can_move_right = bird.canMoveTo(current_row, current_col+1);
								if(can_move_right){
									bird.moveTo(current_row, current_col+1);
									movedone = true;
									break;
								}
							}
							else if(d.getColDist()< 0){
								boolean can_move_left = bird.canMoveTo(current_row, current_col-1);
								if(can_move_left){
									bird.moveTo(current_row, current_col-1);
									movedone = true;
									break;
								}
							}
							else if(d.getColDist() ==0){
								//bingo -food found (eat and move away)
								Grain grain = (Grain)d.getTargetpiece();
								grain.deplete();
								
								// add a bird every bite (every perch) -----------------------------------------------------------------------------------------------------//
								//b2.performFly();
								b2.performFly();
								// make birds fly
								// choose where they hatch
							
								
								// condition for moving/static board
								if(b2 instanceof MovingForageBoard){
									int randRowf = rand.nextInt((b2.getRows() - 3) + 1) + 0;
				            		int randColf= rand.nextInt((b2.getColumns() - 3) + 1) + 0; 
				            		grain.moveTo(randRowf, randColf);
									grain.setSpeed(10);

								}

								
								if(b2.starveBirds){
			                		grain.remove();
			                		b2.updateStockDisplay();
			                	}
								else if(grain.getRemaining() <=0){
				        			grain.remove();	
				        			b2.updateStockDisplay();
				        		} 
				        		int randRow1 = rand.nextInt((b2.getRows() - 3) + 1) + 0;
				            	int randCol2 = rand.nextInt((b2.getColumns() - 3) + 1) + 0; 
				            	bird.moveTo(randRow1, randCol2);
				        		bird.setSpeed(20);
								movedone = true;
								break;


							}
							
						}
					}
					///////
					else if(d.getRowDist() > d.getColDist()){
						

		            	
						if(d.getColDist() >0){
							boolean can_move_right = bird.canMoveTo(current_row, current_col+1);
							if(can_move_right){
								bird.moveTo(current_row, current_col+1);
								movedone = true;
								break;
							}
						}
						else if(d.getColDist()<0){
							boolean can_move_left = bird.canMoveTo(current_row, current_col-1);
							if(can_move_left){
								bird.moveTo(current_row, current_col-1);
								movedone = true;
								break;
							}
						}
						else if(d.getColDist() == 0){
							if(d.getRowDist() >0){
								boolean can_move_up= bird.canMoveTo(current_row-1, current_col);
					    		if(can_move_up){
									bird.moveTo(current_row-1, current_col);
									movedone = true;
									break;
								}
								
							}
							else if(d.getRowDist() < 0){
								boolean can_move_down = bird.canMoveTo(current_row+1, current_col);///kkkk
					    		if(can_move_down){
									bird.moveTo(current_row+1, current_col);
									movedone = true;
									break;
								} 
							}
							else if(d.getRowDist() ==0){
								//bingo -food found (eat and move away)
								Grain grain = (Grain)d.getTargetpiece();
								grain.deplete();
								// add a bird every bite (every perch) -----------------------------------------------------------------------------------------------------//
								
								// condition for moving/static board
								
								if(b2 instanceof MovingForageBoard){
									int randRowf = rand.nextInt((b2.getRows() - 3) + 1) + 0;
				            		int randColf= rand.nextInt((b2.getColumns() - 3) + 1) + 0; 
				            		grain.moveTo(randRowf, randColf);
									grain.setSpeed(10);
								}

								
								
								if(b2.starveBirds){

			                		grain.remove();
			                		b2.updateStockDisplay();
			                	}
								else if(grain.getRemaining() <=0){

				        			grain.remove();	
				        			b2.updateStockDisplay();
				        		} 

								
								
				        		int randRow1 = rand.nextInt((b2.getRows() - 3) + 1) + 0;
				            	int randCol2 = rand.nextInt((b2.getColumns() - 3) + 1) + 0; 
				            	bird.moveTo(randRow1, randCol2);	
				        		bird.setSpeed(20);
								movedone = true;
								break;

							}
						}
					}
				}
				if(!movedone){
					int randRow1 = rand.nextInt((b2.getRows() - 3) + 1) + 0;
	            	int randCol2 = rand.nextInt((b2.getColumns() - 3) + 1) + 0; 
	            	bird.moveTo(randRow1, randCol2);
				}
				 
			}
			bird.remove();
			b2.updateStockDisplay();
	       
			
		}
		
}
