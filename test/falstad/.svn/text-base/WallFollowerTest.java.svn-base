package falstad;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
/**
 * 
 * @author aablohm and jaleo
 *
 *Tests the WallFollower driver.  Extends Maze in order to override the redraw methods since those are not used in the tests. 
 */
public class WallFollowerTest extends Maze {

	static int skill_x[] =     { 4, 12, 15, 20, 25, 25, 35, 35, 40, 60,
		70, 80, 90, 110, 150, 300 };
	static int skill_y[] =     { 4, 12, 15, 15, 20, 25, 25, 35, 40, 60,
		70, 75, 75,  90, 120, 240 };
	static int skill_rooms[] = { 0,  2,  2,  3,  4,  5, 10, 10, 20, 45,
		45, 50, 50,  60,  80, 160 };
	static int skill_partct[] = { 60,
		600, 900, 1200,
		2100, 2700, 3300,
		5000, 6000, 13500,
		19800, 25000, 29000,
		45000, 85000, 85000*4 };

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
		default : mazebuilder = new MazeBuilder(); 
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



	//This method no longer does anything to allow the tests to run properly.
	@Override
	public void redraw() {
		
	}

	@Override
	public boolean increasePercentage(int pc) {
		return true;
	}

	/**
	 * Tests that the WallFollower driver can get a robot to the exit of a small deterministic maze, where it will return true.  It also tests the driver on 
	 * a larger maze where the robot's battery will die before reaching the end of the maze.  Because of this, the method should return false. 
	 */

	@Test
	public void testDrive2Exit() {
		boolean finish = false;
		WallFollowerTest maze = new WallFollowerTest();
		
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		maze.mo = new WallFollower();
		maze.walle = new BasicRobot(maze);
		try {
			maze.mo.setRobot(maze.walle);
		} catch (UnsuitableRobotException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			finish = maze.mo.drive2Exit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(finish);


		mz.build(maze, skill_x[1], skill_y[1], skill_rooms[1], skill_partct[1]);
		try {
			mz.buildThread.join(60000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		maze.mo = new WallFollower();
		maze.walle = new BasicRobot(maze);
		try {
			maze.mo.setRobot(maze.walle);
		} catch (UnsuitableRobotException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			finish = maze.mo.drive2Exit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(finish);
	}

	/**
	 * Tests the getEnergyConsumption() method on a small deterministic maze.  It should return the amount of battery power the 
	 * robot went through in its attempt to exit the maze.
	 */
	@Test
	public void testGetEnergyConsumption() {
		WallFollowerTest maze = new WallFollowerTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		maze.mo = new WallFollower();
		maze.walle = new BasicRobot(maze);
		try {
			maze.mo.setRobot(maze.walle);
		} catch (UnsuitableRobotException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			maze.mo.drive2Exit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertTrue(maze.mo.getEnergyConsumption() == 168.0);
	}	

	/**
	 * Tests getPathLength() for a small path length.  It should return the number of cells the robot went through on its
	 * path to exit the maze.
	 */

	@Test
	public void testGetPathLength() {
		WallFollowerTest maze = new WallFollowerTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		maze.mo = new WallFollower();
		maze.walle = new BasicRobot(maze);
		try {
			maze.mo.setRobot(maze.walle);
		} catch (UnsuitableRobotException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			maze.mo.drive2Exit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertTrue(maze.mo.getPathLength() == 18);
	}


}
