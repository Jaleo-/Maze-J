/**
 *This our tester for the BasicRobot class.  It's alright.
 */

package falstad;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author aablohm
 *To avoid any complications that may occur from maze, especially with generating graphics, we have test extend maze and overwrite
 *any methods that serve no purpose here, and can thus be ignored.  The first methods dont need any heavy commenter as
 *they are merely copied from maze to give an abridged copy.
 */
public class BasicRobotTest extends Maze {

	BasicRobot bender;
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
		case 2 : mazebuilder2 = new MazeBuilder(true);
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
	 * Test method for {@link falstad.BasicRobot#rotate(int)}.
	 * We initially set the robot's current position to (0,0).  Based upon the maze always setting the current 
	 * direction to (1,0) we know that rotate(90) should shift direction to (0, 1).  Also, by rotating -90 degrees
	 * afterwards we should return to our initial direction (1,0).
	 */
	@Test
	public void testRotate() {
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder();
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		maze.setCurrentPosition(0,0);
		bender = new BasicRobot(maze);
		try {
			bender.rotate(90);
		} catch (UnsupportedArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(maze.dx == 0 && maze.dy == 1);

		
		try {
			bender.rotate(-90);
		} catch (UnsupportedArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(maze.dx == 1 && maze.dy == 0);
	}

	/**
	 * Test method for {@link falstad.BasicRobot#move(int, boolean)}.
	 * Tests out the move method. We have the maze set as deterministic to make sure that we can always move forward.
	 * We move the robot and check to make sure that the current position corresponds with our movement. 
	 */
	@Test
	public void testMove() {
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		maze.setCurrentPosition(0,0);
		bender = new BasicRobot(maze);
		if (!mz.cells.hasWallOnRight(0,0)){
			try {
				bender.move(1, true);
			} catch (HitObstacleException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertTrue(maze.px==1 && maze.py==0);
			assertTrue(bender.getCurrentPosition()[0] == 1 && bender.getCurrentPosition()[1] == 0);

			try {
				bender.move(1, false);
			} catch (HitObstacleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertTrue(maze.px == 0 && maze.py == 0);
			assertTrue(bender.getCurrentPosition()[0] == 0 && bender.getCurrentPosition()[1] == 0);
		}

		if (!mz.cells.hasWallOnBottom(0,0)){
			try {
				bender.rotate(90);
			} catch (UnsupportedArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			try {
				bender.move(1, true );
			} catch (HitObstacleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertTrue(maze.px == 0 && maze.py == 1);
			assertTrue(bender.getCurrentPosition()[0] == 0 && bender.getCurrentPosition()[1] == 1);

			try {
				bender.move(1, false );
			} catch (HitObstacleException e){
				e.printStackTrace();
			}
			assertTrue(maze.px == 0 && maze.py == 0);
			assertTrue(bender.getCurrentPosition()[0] == 0 && bender.getCurrentPosition()[1] == 0);
		}
	}


	/**
	 * Test method for {@link falstad.BasicRobot#isAtGoal()}.
	 * We test two conditions.  when it is at the exit position and when it is not.  When the robot is initially set
	 * it always not be at the exit.  Thus we can safely assume that isAtGoal() will be false.  After finding the exit
	 * we set the robot's current position  to that exact destination and isAtGoal() should return true. 
	 */
	@Test
	public void testIsAtGoal() {
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		bender = new BasicRobot(maze);
		
		assertFalse(bender.isAtGoal());
		
		int exit_x = mz.exit_x;
		int exit_y = mz.exit_y;
		maze.setCurrentPosition(exit_x, exit_y);
		assertTrue(bender.isAtGoal());
		
		
	}

	/**
	 * Test method for {@link falstad.BasicRobot#getCurrentDirection()}.
	 * We test to make sure that we have the correct current direction.
	 */
	@Test
	public void testGetCurrentDirection() {
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		bender = new BasicRobot(maze);
		maze.setCurrentPosition(0, 0);
		assertTrue(bender.getCurrentDirection()[0] == 1 && bender.getCurrentDirection()[1] == 0);
	
	}

	/**
	 * Test method for {@link falstad.BasicRobot#getCurrentBatteryLevel()}.
	 * We test to make sure that after a series of battery operations, the battery level has
	 *  been changed appropriately.  Otherwise, fail.
	 */
	@Test
	public void testGetCurrentBatteryLevel() {
		int batteryLevel = 2500;
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bender = new BasicRobot(maze);
		
		assertTrue(bender.getCurrentBatteryLevel() == batteryLevel);
		try {
			bender.rotate(90);
		} catch (UnsupportedArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		batteryLevel = batteryLevel - 3;
		assertTrue(bender.getCurrentBatteryLevel() == batteryLevel);
		
		if (!mz.cells.hasWallOnBottom(0, 0)) {
			try {
				bender.move(1, true);
			} catch (HitObstacleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			batteryLevel = batteryLevel - 5;
			assertTrue(bender.getCurrentBatteryLevel() == batteryLevel);
			
			try {
				bender.move(1, false);
			} catch (HitObstacleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			batteryLevel = batteryLevel - 5;
			assertTrue(bender.getCurrentBatteryLevel() == batteryLevel);
		}
		
		if (!mz.cells.hasWallOnLeft(0, 0)) {
			try {
				bender.rotate(-90);
			} catch (UnsupportedArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			batteryLevel = batteryLevel - 3;
			try {
				bender.move(1, true);
			} catch (HitObstacleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			batteryLevel = batteryLevel - 5;
			assertTrue(bender.getCurrentBatteryLevel() == batteryLevel);
			
			try {
				bender.move(1, false);
			} catch (HitObstacleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			batteryLevel = batteryLevel - 5;
			assertTrue(bender.getCurrentBatteryLevel() == batteryLevel);
		}
	}

	/**
	 * Makes sure that methods such as getEnergyForFullRotation and 
	 * getEnergyForStepForward are the appropriate amount (respectively 12 and 5).
	 */
	@Test
	public void testEnergyMethods() {
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bender = new BasicRobot(maze);
		
		assertTrue(bender.getEnergyForFullRotation() == 12);
		assertTrue(bender.getEnergyForStepForward() == 5);
	}

	/**
	 * Test method for {@link falstad.BasicRobot#hasStopped()}.
	 * Makes sure that hasStopped only occurs once the robot's battery is depleted.  Test takes time.
	 */
	@Test
	public void testHasStopped() {
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bender = new BasicRobot(maze);
		assertFalse(bender.hasStopped());
		
		while (bender.getCurrentBatteryLevel() > 0) {
			try {
				bender.rotate(90);
			} catch (UnsupportedArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		assertTrue(bender.hasStopped());
	}

	/**
	 * Test method for {@link falstad.BasicRobot#canSeeGoalAhead()}.
	 * We test to make sure that the robot can see the goal ahead when it is true.  Thus, when we start the maze, it 
	 * will not be able to see maze in any direction.  We then set up a situation where this method SHOULD be able to
	 * see the exit ahead.
	 */
	@Test
	public void testCanSeeGoalAhead() {
		boolean solution = false;
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		maze.walle = new BasicRobot(maze);
		maze.setCurrentPosition(3,0);
		try {
			solution = maze.walle.canSeeGoalAhead();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!solution);
		maze.setCurrentPosition(3, 0);
		maze.dx = -1;
		maze.dy = 0;
		try {
			solution = maze.walle.canSeeGoalAhead();
		} 
		catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(solution);
		maze.dx = 0;
		maze.dy = 1;
		
		try {
			solution = maze.walle.canSeeGoalAhead();
		} 
		catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!solution);
		
		
	}

	/**
	 * Test method for {@link falstad.BasicRobot#canSeeGoalBehind()}.
	 * We test to make sure that the robot can see the goal behind when it is true.  Thus, when we start the maze, it 
	 * will not be able to see maze in any direction.  We then set up a situation where this method SHOULD be able to
	 * see the exit behind.
	 */
	@Test
	public void testCanSeeGoalBehind() {
		boolean solution = false;
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bender = new BasicRobot(maze);
		maze.setCurrentPosition(0,2);
		try {
			solution = bender.canSeeGoalBehind();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!solution);
		maze.setCurrentPosition(3,0);
		maze.dx = 1;
		maze.dy = 0;
		try {
			solution = bender.canSeeGoalBehind();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(solution);
		maze.dx = 0;
		maze.dy = -1;
		try {
			solution = bender.canSeeGoalBehind();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!solution);
	}

	/**
	 * Test method for {@link falstad.BasicRobot#canSeeGoalOnLeft()}.
	 * We test to make sure that the robot can see the only goal on the left when it is true.  Thus, when we start the maze, it 
	 * will not be able to see maze in any direction.  We then set up a situation where this method SHOULD be able to
	 * see the exit on the left.
	 */
	@Test
	public void testCanSeeGoalOnLeft() {
		boolean solution = false;
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bender = new BasicRobot(maze);
		maze.setCurrentPosition(0,0);
		try {
			solution = bender.canSeeGoalOnLeft();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!solution);
		maze.setCurrentPosition(3,0);
		maze.dx = 0;
		maze.dy = 1;
		try {
			solution = bender.canSeeGoalOnLeft();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(solution);
		maze.dx = -1;
		maze.dy = 0;
		try {
			solution = bender.canSeeGoalOnLeft();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!solution);
	}
	

	/**
	 * Test method for {@link falstad.BasicRobot#canSeeGoalOnRight()}.
	 * We test to make sure that the robot can see the only goal on the right when it is true.  Thus, when we start the maze, it 
	 * will not be able to see maze in any direction.  We then set up a situation where this method SHOULD be able to
	 * see the exit on the right.
	 */
	@Test
	public void testCanSeeGoalOnRight() {
		boolean solution = false;
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bender = new BasicRobot(maze);
		maze.setCurrentPosition(0,0);
		try {
			solution = bender.canSeeGoalOnRight();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!solution);
		maze.setCurrentPosition(3,0);
		maze.dx = 0;
		maze.dy = -1;
		try {
			solution = bender.canSeeGoalOnRight();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(solution);
		maze.dx = 1;
		maze.dy = 0;
		try {
			solution = bender.canSeeGoalOnRight();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!solution);
	}

	/**
	 * Test method for {@link falstad.BasicRobot#distanceToObstacleAhead()}.
	 * We test to make sure that the robot determines the proper length from the wall ahead.  
	 */

	@Test
	public void testDistanceToObstacleAhead() {
		int distance = 0;
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bender = new BasicRobot(maze);
		maze.setCurrentPosition(0,1);
		try {
			 distance = bender.distanceToObstacleAhead();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 2);
		
		maze.setCurrentPosition(0,3);
		try {
			 distance = bender.distanceToObstacleAhead();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 1);
		

		maze.setCurrentPosition(2,1);
		try {
			 distance = bender.distanceToObstacleAhead();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 0);
	}

	/**
	 * Test method for {@link falstad.BasicRobot#distanceToObstacleOnLeft()}.
	 * We test to make sure that the robot determines the proper length from the wall to its left side.  
	 */
	@Test
	public void testDistanceToObstacleOnLeft() {
		int distance = 0;
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bender = new BasicRobot(maze);
		maze.setCurrentPosition(0,0);
		try {
			 distance = bender.distanceToObstacleOnLeft();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 0);
		
		maze.setCurrentPosition(0,2);
		try {
			 distance = bender.distanceToObstacleOnLeft();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 1);
		
		maze.setCurrentPosition(3,0);
		try {
			 distance = bender.distanceToObstacleOnLeft();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 3);
	}
	

	/**
	 * Test method for {@link falstad.BasicRobot#distanceToObstacleOnRight()}.
	 * We test to make sure that the robot determines the proper length from the wall to its right side.  
	 */
	@Test
	public void testDistanceToObstacleOnRight() {
		int distance = 0;
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bender = new BasicRobot(maze);
		maze.setCurrentPosition(0,0);
		try {
			 distance = bender.distanceToObstacleOnRight();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 0);
		
		maze.setCurrentPosition(3,3);
		try {
			 distance = bender.distanceToObstacleOnRight();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 3);
		
		maze.setCurrentPosition(0,2);
		try {
			 distance = bender.distanceToObstacleOnRight();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 1);
		
		maze.setCurrentPosition(0,3);
		try {
			 distance = bender.distanceToObstacleOnRight();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 2);
	}

	/**
	 * Test method for {@link falstad.BasicRobot#distanceToObstacleBehind()}.
	 * We test to make sure that the robot determines the proper length from the wall behind it.
	 */
	@Test
	public void testDistanceToObstacleBehind() {
		int distance = 0;
		BasicRobotTest maze = new BasicRobotTest();
		MazeBuilder mz = new MazeBuilder(true);
		mz.build(maze, skill_x[0], skill_y[0], skill_rooms[0], skill_partct[0]);
		try {
			mz.buildThread.join(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bender = new BasicRobot(maze);
		maze.setCurrentPosition(0,1);
		try {
			 distance = bender.distanceToObstacleBehind();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 0);
		
		maze.setCurrentPosition(3,3);
		try {
			 distance = bender.distanceToObstacleBehind();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 1);
		
		maze.setCurrentPosition(1,1);
		try {
			 distance = bender.distanceToObstacleBehind();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 1);
		
		maze.setCurrentPosition(2,2);
		try {
			 distance = bender.distanceToObstacleBehind();
		} catch (UnsupportedMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(distance == 2);
	}


}
