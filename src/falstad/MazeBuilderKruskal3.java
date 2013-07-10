/**
 * 
 */
package falstad;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author aablohm
 *
 */
public class MazeBuilderKruskal3 extends MazeBuilder implements Runnable {
	

	
	public ArrayList<CellAndID> cellSets = new ArrayList<CellAndID>();
	public ArrayList<Wall> walls = new ArrayList<Wall>();
	public int numWalls;
	

	public MazeBuilderKruskal3(){
		super();
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze");
	}
	
	public MazeBuilderKruskal3(boolean det) {
		super(det);
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
	}
	
	@Override
	/**
	 * This method generates a maze.
	 * It computes distances, determines a start and exit position that are as far apart as possible. 
	 */
	protected void generate() {
		
		createWallList();
		createCellSets();
		// generate paths in cells such that there is one strongly connected component
		// i.e. between any two cells in the maze there is a path to get from one to the other
		// the search algorithms starts at some random point
		generatePathways();

		// compute temporary distances for an (exit) point (x,y) = (width/2,height/2) 
		// which is located in the center of the maze
		computeDists(width/2, height/2);

		int[] remote = findRemotePoint();
		// recompute distances for an exit point (x,y) = (remotex,remotey)
		computeDists(remote[0], remote[1]);

		// identify cell with the greatest distance
		setStartPositionToCellWithMaxDistance();

		// make exit position at true exit 
		setExitPosition(remote[0], remote[1]);
	}
	
	@Override
	/**
	 * Main method to run construction of a new maze with a MazeBuilder in a thread of its own.
	 * This method is implicitly called by the build method when it sets up and starts a new thread for this object.
	 */
	public void run() {
		int tries = 250;

		colchange = randNo(0, 255);
		// create a maze where all walls and borders are up
		cells.initialize();

		// put pathways into the maze, determine its starting and end position and calculate distances
		generate();
		// determine segments, i.e. walls over multiple cells in vertical or horizontal direction
		seglist = generateSegments();
		setPartitionBitForCertainSegments(seglist); // partition bit true means that those are not considered any further for node generation
		cells.setTopToOne(0, 0); // TODO: check why this is done. It creates a top wall on position (0,0). This may even corrupt a maze and block its exit!
		

		partiters = 0;
		BSPNode root = genNodes();
		//dbg("partiters = "+partiters);
		// communicate results back to maze object
		maze.newMaze(root, cells, dists, startx, starty);


	}

	/**
	 * Creates an array list of all the walls in the maze with the exception of the outside borders (since those cannot be deleted during the 
	 * completion of the algorithm).  It also counts up the total number of walls added to the list to use in another method.  It
	 * also shuffles the walls within the array list so that they are in random order when they are selected later.
	 */
	protected void createWallList() {
		numWalls = 0;
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				if (cells.hasNoBoundOnBottom(i, j)){
					walls.add(new Wall(i, j, 0, 1)); //Adds the bottom wall
					numWalls++;}
				if (cells.hasNoBoundOnTop(i, j)){
					walls.add(new Wall(i, j, 0, -1)); //Adds the top wall
					numWalls++;}
				if (cells.hasNoBoundOnLeft(i, j)){
					walls.add(new Wall(i, j, -1, 0)); //Adds the left wall
					numWalls++;}
				if (cells.hasNoBoundOnRight(i, j)){
					walls.add(new Wall(i, j, 1, 0)); //Adds the right wall
					numWalls++;}
				}
		}	
		Collections.shuffle(walls);
	}
	
	/**
	 * Creates an array list of all the cell sets which are created by assigning each cell an individual id number and placing
	 * that object in the list.
	 */
	protected void createCellSets() {
		int count = 0;
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				CellAndID cell = new CellAndID(i, j, count);
				cellSets.add(cell);
				count++;
			}
		}
	}
	
	/**
	 * Generates the pathways into the maze.  Selects a random wall and checks to see if the cell next to that wall is in the set of the random 
	 * wall's cell.  If it is not, the wall is deleted.  A new random wall is selected and the process begins again until there is a 
	 * pathway through the entire maze.  When the algorithm is completed, all cells will be part of one set.
	 */
	@Override
	protected void generatePathways(){
		//int totalNumWalls = 4 * (width * height);
		int newX;
		int newY;
		int indexFirstCell;
		int indexSecondCell;
		Wall randomWall;
		
		//Picks the next wall in the randomized list of walls and finds the cell next to it.
		for (int i = 0; i < numWalls; i++){
			randomWall = walls.get(i);
			if (randomWall.dx == 1){  //If the wall being looked at is on the right, find the cell that is one space to the right.
				newX = randomWall.x + randomWall.dx ;
				newY = randomWall.y;}
			else if (randomWall.dx == -1){ //If the wall being looked at is on the left, find the cell that is one space to the left.
				newX = randomWall.x + randomWall.dx ;
				newY = randomWall.y;}
			else{  
				if (randomWall.dy == 1){  //If the wall being looked at is on the bottom, find the cell that is one space to the bottom.
					newX = randomWall.x ;
					newY = randomWall.y + randomWall.dy;}
				else{  //If the wall being looked at is on the top, find the cell that is one space to the top.
					newX = randomWall.x;
					newY = randomWall.y + randomWall.dy;}
			}
			
			//Checks to make sure the new cell will not be out of the bounds of the maze.
			if (newX == -1 || newX == width || newY == -1 || newY == height){
				continue;}
			
			//If the two cells are in different sets, it combines the two sets.  Otherwise, it does nothing.
			else{
				//Uses a helper method to find the index of each cell within the array list.
				indexFirstCell = findCell( randomWall.x, randomWall.y);
				indexSecondCell = findCell( newX, newY );
				
				//Checks to see if the ids of the cells are the same.
				if (cellSets.get(indexFirstCell).id != cellSets.get(indexSecondCell).id){
					int id1 = cellSets.get(indexFirstCell).id;
					int id2 = cellSets.get(indexSecondCell).id;
					
					//Changes the ids of all the cells of one of the sets.
					for (int k = 0; k < height * width; k++){
						if (cellSets.get(k).id == id1)
							cellSets.get(k).id = id2;
					}
					//Deletes the wall
					cells.deleteWall(randomWall.x, randomWall.y, randomWall.dx, randomWall.dy);
				}
			}	
		}
	}
			

	/**
	 * Helper method used by generatePathways to find the index of a given cell in the array list.
	 * Returns the index or -1 if the cell doesn't exist in the array (which it always should, given
	 * that we have been using that cell's wall to generate a path).
	 * @param x
	 * @param y
	 * @return
	 */
	private int findCell( int x, int y ){
		int totalNumCells = width * height;
		
		//Checks each cell to see if it is the one being looked for.
		for (int i = 0; i < totalNumCells; i++){
			if (cellSets.get(i).x == x && cellSets.get(i).y == y)
				return i;
		}
		return -1;
	}
}
