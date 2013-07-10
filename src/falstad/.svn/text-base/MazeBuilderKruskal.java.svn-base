package falstad;
import java.util.ArrayList;
/**
 * Note that in order to implement the Kruskal maze.  A case has been added, similar to what is done with prim, execpt
 *it's case 2.  
 * @author jaleo
 *
 */
public class MazeBuilderKruskal extends MazeBuilder implements Runnable {
	public MazeBuilderKruskal() {
		super();
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
	}
	public MazeBuilderKruskal(boolean det) {
		super(det);
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
	}
	@Override
	public void build(Maze mz, int w, int h, int roomct, int pc) {
		width = w;
		height = h;
		maze = mz;
		rooms = 0;
		cells = new Cells(w,h) ;
		origdirs = new int[w][h];
		dists = new int[w][h];
		expected_partiters = pc;
		buildThread = new Thread(this);
		buildThread.start();
		
	}
	/** The generate function creates the maze through Kruskal's algorithm.  A list of walls are set up that mimic the initialized cells (all walls are up).
	 *  The next step creates a while loop, that stops once the count reaches width*height -1, signifies that everyone shares the same root.  Important to note 
	 *  is the equation implemented for Value1 and Value2, the equation ensures that every cell has a unique value and that it is within the range of the disjoint
	 *  set.  For a 3 x 3 maze, the values corresponding with the cell's position would look like this
	 *  [0 3 6]
	 *  [1 4 7]
	 *  [2 5 8]
	 * Now during the while loop, we pick a random wall from the list.  We then proceed to calculate the value for the two cells that
	 * touch that wall.  A comparison is then made to make sure that they do not share the same root.  Given that they don't, the wall
	 * is then removed from both cells (as each cell has a bit value to signify that wall). Otherwise the wall remains as a path already exists that links
	 * the two together.  Regardless of the situation, the wall is removed from the list of walls as a solution has been found or made.      
	 * 
	 **/
	@Override
	public void generate() {
		int x ;
		int y ;
		int i, j;
		int Value1;
		int Value2;
		int Find1;
		int Find2;
		int count = 0;
		DisjointSet set = new DisjointSet(width *height);
		ArrayList<Wall> walls = new ArrayList<Wall>();
		for(i = 0; i < (width) ; i++){
			for(j = 0; j< (height); j++ ){
				if (cells.canGo(i, j, 0, 1))
				{
					walls.add(new Wall(i, j, 0, 1));
				}
				if (cells.canGo(i, j, 0, -1))
				{
					walls.add(new Wall(i, j, 0, -1));
				}
				if (cells.canGo(i, j, 1, 0))
				{
					walls.add(new Wall(i, j, 1, 0));
				}
				if (cells.canGo(i, j, -1, 0))
				{
					walls.add(new Wall(i, j, -1, 0));
				}
		}
		}
		Wall curWall;
	while(count < (width*height-1)){
		
		int r = randNo(0, walls.size()-1);
		curWall = walls.get(r);
		Value1 = curWall.y + curWall.x * height;
		Value2 = (curWall.y + curWall.dy) + (curWall.x +curWall.dx) * height ;
		Find1 = set.find(Value1);
		Find2 = set.find(Value2);
		if(Find1 != Find2){
			/*The set.union checks to see if the two values share the same parent.  If they do then a path already exists that connects them.*/
			set.union(Find1, Find2);
			cells.deleteWall(curWall.x, curWall.y, curWall.dx, curWall.dy);
			x = curWall.x + curWall.dx;
			y = curWall.y + curWall.dy;
			cells.deleteWall(x, y, -curWall.dx, -curWall.dy);
			count++;
			
		}

		walls.remove(r);
	
			
		}
	computeDists(width/2, height/2);

	int[] remote = findRemotePoint();
	// recompute distances for an exit point (x,y) = (remotex,remotey)
	computeDists(remote[0], remote[1]);

	// identify cell with the greatest distance
	setStartPositionToCellWithMaxDistance();

	// make exit position at true exit 
	setExitPosition(remote[0], remote[1]);

}
}
