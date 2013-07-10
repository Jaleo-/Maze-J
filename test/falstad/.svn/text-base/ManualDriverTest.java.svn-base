/**
 *This our tester for the ManualDriver class.  Please be amazed, it helps our self esteem(well her self esteem anyways).
 */
package falstad;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author aablohm and jaleo
 *To avoid any complications that may occur from maze, especially with generating graphics, we have test extend maze and overwrite
 *any methods that serve no purpose here, and can thus be ignored.  The first methods dont need any heavy commenter as
 *they are merely copied from maze to give an abridged copy.
 */
public class ManualDriverTest extends Maze {

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
	 * To make sure that the testDrive2Exit we need to test on two conditions, that it returns when battery
	 * level is above 0 and when the battery is dead.  Thus when we initially create the robot, Drive2Exit
	 * should return true.  Next we proceed to wear our little robot to the brink of death(battery =<0).  Drive2Exit
	 * should return false now.  Note test requires time.
	 */
	@Test
	public void testDrive2Exit() {
		
		boolean driving = false;
		ManualDriverTest maze = new ManualDriverTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		maze.mo = new ManualDriver();
		maze.walle = new BasicRobot(maze);
		try {
			maze.mo.setRobot(maze.walle);
		} catch (UnsuitableRobotException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			driving = maze.mo.drive2Exit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(driving);
		
		while (maze.walle.getCurrentBatteryLevel() > 0)
			try {
				maze.walle.rotate(90);
			} catch (UnsupportedArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		try {
			driving = maze.mo.drive2Exit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!driving);
	}

	/**
	 * Test method for {@link falstad.ManualDriver#getEnergyConsumption()}.
	 * Makes sure that proper amount of total energy consumption is recorded.  
	 */
	@Test
	public void testGetEnergyConsumption() {
		int batteryConsumption = 0;
		ManualDriverTest maze = new ManualDriverTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		maze.mo = new ManualDriver();
		maze.walle = new BasicRobot(maze);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		maze.setCurrentPosition(0,0);

		try {
			maze.mo.setRobot(maze.walle);
		} catch (UnsuitableRobotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(maze.mo.getEnergyConsumption() == 0);
		

		if (!mz.cells.hasWallOnRight(0,0)){

			try {
				maze.walle.move(1, true);
			} catch (HitObstacleException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			batteryConsumption += 5;
			assertTrue(maze.mo.getEnergyConsumption() == batteryConsumption);

			try {
				maze.walle.move(1, false);
			} catch (HitObstacleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			batteryConsumption += 5;
			assertTrue(maze.mo.getEnergyConsumption() == batteryConsumption);

		}

		if (!mz.cells.hasWallOnTop(0,0)){
			try {
				maze.walle.rotate(90);
			} catch (UnsupportedArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			try {
				maze.walle.move(1, true );
			} catch (HitObstacleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			batteryConsumption += 5;
			assertTrue(maze.mo.getEnergyConsumption() == batteryConsumption);

			try {
				maze.walle.move(1, false );
			} catch (HitObstacleException e){
				e.printStackTrace();
			}
			batteryConsumption += 5;
			assertTrue(maze.mo.getEnergyConsumption() == batteryConsumption);
		}
		
		try {
			maze.walle.rotate(-90);
		} catch (UnsupportedArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		batteryConsumption += 3;
		assertTrue(maze.mo.getEnergyConsumption() == batteryConsumption);
		
	
		
	}

	/**
	 * Test method for {@link falstad.ManualDriver#getPathLength()}.
	 * Test makes sure that the number of steps the robot takes is properly recorded.  
	 * We have made Maze deterministic so that we could appropriate a proper move( not running into a wall) 
	 * otherwise if we had a random maze, we could run into a wall and the test is defeated and dishonored.
	 */
	@Test
	public void testGetPathLength() {
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		maze.mo = new ManualDriver();
		maze.walle = new BasicRobot(maze);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		maze.setCurrentPosition(0,0);
		try {
			maze.mo.setRobot(maze.walle);
		} catch (UnsuitableRobotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(maze.mo.getPathLength() == 0);
		try {
			maze.walle.move(1, true);
		} catch (HitObstacleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(maze.mo.getPathLength() == 1);
		try {
			maze.walle.move(1, false);
		} catch (HitObstacleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(maze.mo.getPathLength() == 2);
		try {
			maze.walle.move(1, true);
		} catch (HitObstacleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(maze.mo.getPathLength() == 3);
		try {
			maze.walle.move(1, false);
		} catch (HitObstacleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(maze.mo.getPathLength() == 4);

	}

	


}
