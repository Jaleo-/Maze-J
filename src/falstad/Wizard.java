package falstad;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * @author aablohm and jaleo
 * 
 * This class defines a magic robot driver that seduces the maze into revealing her solution.  It then uses this solution to escape and then doesn't return her calls.
 *
 */
public class Wizard implements RobotDriver, KeyListener {
	
	Robot zero = new BasicRobot();
	Maze mz;
	int[][]dists;
	
	public Wizard(Maze maze){
	mz = maze;
	dists = mz.mazedists;
		
	}

	@Override
	/**
	 * Maze calls this function so that we can assign the robot that will be used within the application.  
	 */
	public void setRobot(Robot r) throws UnsuitableRobotException {
		zero = r;	
	}

	@Override
	/**
	 * The robot will continue to be able to drive until it's battery is depleted.  Then it remains frozen, only terminating him now will
	 * he truly be saved....and they said this was a kid friendly game.  Drive to exit implements a method very similar to solvestep() within
	 * Maze.  It is guided by getDirectionIndexTowardsSolution() which tells the driver where to go based on its current position.  
	 */
	public boolean drive2Exit() throws Exception {
		while (!zero.hasStopped()&& !zero.isAtGoal()){
			int d = mz.mazedists[zero.getCurrentPosition()[0]][zero.getCurrentPosition()[1]];
			// case 1: we are not directly next to the final position
			if (d > 1) {
				int n = mz.getDirectionIndexTowardsSolution(zero.getCurrentPosition()[0],zero.getCurrentPosition()[1],d);
				rotateTo(n);
				zero.move(1, true);
				mz.repaint(25);
			}
		}
		if(!zero.hasStopped()){
			int count = 1;
			while (count >0 && !zero.hasStopped()){
				if (zero.distanceToObstacleAhead() == -1 || zero.canSeeGoalAhead()){
					zero.move(1, true);

				}
				else if ((zero.distanceToObstacleOnLeft() == -1)|| (zero.canSeeGoalOnLeft())){
					zero.rotate(90);
					zero.move(1, true);
				}
				else{
					zero.rotate(-90);
					zero.move(1, true);}
				if (zero.isAtGoal()){;
					count -=1;
				}
			}
			if(zero.isAtGoal()&&!zero.hasStopped())
				return true;
			return false;	
		}
		return false;
	}
	

	@Override
	/**
	 * The robot's actions require a certain amount of energy to function.  This function keeps track of the the amount of the total
	 * energy consumed by this robot. 
	 * 
	 */
	public float getEnergyConsumption() {
		return 2500 - zero.getCurrentBatteryLevel();
	}

	@Override
	/**
	 * Finds the number of steps that the robot has taken.
	 * 
	 */
	public int getPathLength() {
		return zero.getCurrentPosition()[2];
	}

	@Override
	/**
	 * This method is needed for Wizard to implement KeyListener
	 */
	public void keyPressed(KeyEvent arg0) {

	}
	
	/**
	 * This method is needed for Wizard to implement KeyListener
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	/**
	 * This method is needed for Wizard to implement KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {

		
	}
	
	/**
	 * The same method used within Maze that enables a robot to rotate a certain number of degrees.
	 * @param n
	 */
	protected void rotateTo(int n) {
		int a = mz.ang/90;
		if (n == a)
			;
		else if (n == ((a+2) & 3)) {
			try {
				zero.rotate(90);
			} catch (UnsupportedArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				zero.rotate(90);
			} catch (UnsupportedArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (n == ((a+1) & 3)) {
			try {
				zero.rotate(90);
			} catch (UnsupportedArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			try {
				zero.rotate(-90);
			} catch (UnsupportedArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


	

}
