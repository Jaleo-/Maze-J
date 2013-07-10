/**
 * 
 */
package falstad;

import static org.junit.Assert.*;

import java.awt.Graphics;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author aablohm
 *
 *This class creates a set of black box tests for the MazeBuilder class.  It does this by extending Maze so that MazeBuilderTest becomes
 *a kind of demo maze that can be used to check all of the methods of Mazebuilder.   
 *It tests that the maze is generated properly both regularly and deterministically.  It also tests that the maze is always bounded properly 
 *and that there is always an exit from any cell in the maze.
 */
public class MazeBuilderTest extends Maze {
	
	public int state;
	public int percentdone = 0; // describes progress during generation phase
	public boolean showMaze;		 	// toggle switch to show overall maze on screen
	public boolean showSolution;		// toggle switch to show solution in overall maze on screen
	public boolean solving;	
	public MazeBuilder mazebuilder2;

	//Overrode several methods in Maze because they were trying to update the graphics and redraw, which is not something my tests deal with.
	@Override
	public void build(int skill) {
		// switch screen
		state = STATE_GENERATING;
		percentdone = 0;
		redraw();
		// select generation method
		switch(method){
		case 1 : mazebuilder = new MazeBuilderPrim(); // generate with Prim's algorithm
		break ;
		case 2 : mazebuilder2 = new MazeBuilder();
		case 0: // generate with Falstad's original algorithm (0 and default), note the missing break statement
		default : mazebuilder = new MazeBuilder(true); 
		break ;
		}
		// adjust settings and launch generation in a separate thread
		mazew = skill_x[skill];
		mazeh = skill_y[skill];
		int roomcount = skill_rooms[skill];
		mazebuilder.build(this, mazew, mazeh, roomcount, skill_partct[skill]);
		try {
			mazebuilder.buildThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// mazebuilder calls back by calling newMaze() to return newly generated maze
			}
	
	@Override
	synchronized protected void solveStep() {
		solving = false;
		int d = mazedists[px][py];
		// case 1: we are not directly next to the final position
		if (d > 1) {
			int n = getDirectionIndexTowardsSolution(px,py,d);
			if (n == 4)
				dbg("HELP!");
			rotateTo(n);
			walk(1);
			solving = true;
			return;
		}
		// case 2: we are one step close to the final position
		int n;
		int[] masks = Cells.getMasks() ;
		for (n = 0; n < 4; n++) {
			// skip this direction if there is a wall or border
			if (mazecells.hasMaskedBitsGTZero(px, py, masks[n])){
				continue;}
			// stop if position in this direction is end position
			if (isEndPosition(px+MazeBuilder.dirsx[n], py+MazeBuilder.dirsy[n])){
				solving = false;
				break ;}
		}
		rotateTo(n);
		walk(1);
	}
	
	//This method no longer does anything to allow the tests to run properly.
	@Override
	public void redraw() {
		
	}
	
	@Override
	public boolean increasePercentage(int pc) {
		return true;
	}
	
	/**
	 * Tests that MazeBuilder runs properly regularly.  Builds a maze and checks to make sure it was actually
	 * built.  A later test makes sure mazes are solvable.
	 */
	@Test
	public void testMaze(){
		MazeBuilderTest maze = new MazeBuilderTest();
		maze.init();
		maze.build(0);
		assertTrue( STATE_GENERATING == maze.state);
		assertTrue( maze.mazew == maze.mazecells.width);
		assertTrue( maze.mazeh == maze.mazecells.height);
		
	}
	
	/**
	 * Tests that MazeBuilder can be called deterministically and that it will produce the same maze each time.
	 * It does this by iterating over the cells 2D array for both the created mazes to check that their cells
	 * are the same. 
	 */
	@Test

	//Changed randNo to only produce non-random numbers!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public void testDeterministicMaze() {
		MazeBuilderTest maze1 = new MazeBuilderTest(); 
		maze1.build(1);
		

		MazeBuilderTest maze2 = new MazeBuilderTest();
		maze2.build(1);

		for (int i = 0; i <= 11; i++){
			for (int j = 0; j <= 11; j++){
				assertTrue( maze1.mazecells.getCells(i, j) == maze2.mazecells.getCells(i, j));}
		}
	}

	/**
	 * Tests that the created maze has bounds on all four sides with one space open for the exit.  The entrance is placed inside of the maze 
	 * rather than coming from the outside into the maze and thus there is only one cell that should be missing a bound for the exit.
	 */
	@Test
	public void testMazeBoundedProperly(){
		MazeBuilderTest maze = new MazeBuilderTest();
		maze.init();
		maze.build(5);
		
		int count = 0;
		int i, j;
		
		//Counts the number of boundaries around the maze.
		for ( j = 0; j < maze.mazecells.height; j++){
			i = 0;
			if ( maze.mazecells.hasBoundOnLeft(i, j) ){
				count = count + 1;}
		}
		
		for( i = 0; i < maze.mazecells.width; i++){
			j = 0;
			if ( maze.mazecells.hasBoundOnTop(i, j) ){
				count = count + 1;}		
		}
		
		for( j = 0; j < maze.mazecells.height; j++){
			i = maze.mazecells.width - 1;
			if ( maze.mazecells.hasBoundOnRight(i, j) ){
				count = count + 1;}
		}
		
		for (i = 0; i < maze.mazecells.width - 1; i++){
			j = maze.mazecells.height -1;
			if ( maze.mazecells.hasBoundOnBottom(i, j)){
				count = count + 1;}
		}
		//Total number of boundaries not counting and the exit.
		int totalNumBounds = 2 * maze.mazecells.width + 2 * maze.mazecells.height;
		int numBounds = totalNumBounds - 1;
		assertTrue( count == numBounds );
		
	}
	
	/**
	 * Tests to see if there is always a way out of the maze.  This test goes through each cell in mazecells and tries
	 * to find the exit from there.  A large maze takes a while to test, so I have chosen the smallest size for the 
	 * purposes of this test.  Checks both a regular maze and one created deterministically.
	 */
	@Test
	public void testAlwaysAWayOut(){
		
		//Checks a regular maze.
		mazebuilder2 = new MazeBuilder();
		MazeBuilderTest maze1 = new MazeBuilderTest();
		maze1.init();
		maze1.build(0);   //Any larger size takes forever to test...
		
		//Iterates through all the cells in the maze to make sure the exit can be reaches from all of them.
		for ( int i = 0; i < maze1.mazecells.width; i++){
			for( int j = 0; j < maze1.mazecells.height; j++){
				maze1.solving = true;
				while (maze1.solving == true){
					maze1.solveStep();}
				assertTrue( false == maze1.solving );
				maze1.init();
				maze1.build(0);
			}
		}	
		
		//Tests a deterministic maze just to be sure.
		mazebuilder2 = new MazeBuilder(true);
		MazeBuilderTest maze2 = new MazeBuilderTest();
		maze2.init();
		maze2.build(0);
				
		//Iterates through all the cells in the maze to make sure the exit can be reached from all of them.
		for ( int k = 0; k < maze2.mazecells.width; k++){
			for( int l = 0; l < maze2.mazecells.height; l++){
				maze2.solving = true;
				while (maze2.solving == true){
					maze2.solveStep();}
				assertTrue( false == maze2.solving );
				maze2.init();
				maze2.build(0);
				
			}
		}
	
		
	}
}