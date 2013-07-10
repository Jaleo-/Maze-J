/**
 * 
 */
package falstad;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author aablohm and jaleo
 * 
 * This class defines a robot driver that uses the traditional maze-solving algorithm of constantly following the wall on the left until the
 * exit is reached.
 *
 */
public class WallFollower implements RobotDriver,KeyListener {
	
	Robot zero;
	
	public WallFollower(){
		
	}

	@Override
	public void setRobot(Robot r) throws UnsuitableRobotException {
		zero = r;	
	}

	/**
	 * The robot will continue to be able to drive until it's battery is depleted.  Then it remains frozen, only terminating him now will
	 * he truly be saved....and they said this was a kid friendly game.  This method checks to make sure there is a wall on the left.  
	 * If there is not, the robot must be at a corner where it will have to turn to the left in order to keep touching the left wall.
	 * If there is a wall ahead and a wall to the left, the robot will turn to the right and continue. 
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		int left_wall=0, front_wall=0;
		while (!zero.hasStopped()&& !(zero.isAtGoal())){

			try {
				left_wall = zero.distanceToObstacleOnLeft();
				front_wall = zero.distanceToObstacleAhead();
			} catch (UnsupportedMethodException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if( left_wall == 0 && front_wall!=0){
					try {
						zero.move(1, true);
					} catch (HitObstacleException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			else if(left_wall != 0){
					try {
						zero.rotate(90);
					} catch (UnsupportedArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						zero.move(1, true);
					} catch (HitObstacleException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			else if(front_wall == 0 && left_wall==0){
				try {
					zero.rotate(-90);
					front_wall = zero.distanceToObstacleAhead();
					if(front_wall == 0)
						zero.rotate(-90);
					zero.move(1, true);
				} catch (UnsupportedArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(!zero.hasStopped()&& zero.isAtGoal()){

			if (zero.distanceToObstacleAhead() == -1){
				zero.move(1, true);
				return true;
			}
			if (zero.distanceToObstacleOnLeft() == -1){
				zero.rotate(90);
				zero.move(1, true);
				return true;
			}
			zero.rotate(-90);
			zero.move(1, true);
			return true;
			
		}
		
		return false;
	}

	/**
	 * The robot's actions require a certain amount of energy to function.  This function keeps track of the the amount of the total
	 * energy consumed by this robot. 
	 * 
	 */
	@Override
	public float getEnergyConsumption() {
		return 2500 - zero.getCurrentBatteryLevel();
	}

	/**
	 * Finds the number of steps that the robot has taken.
	 * 
	 */
	@Override
	public int getPathLength() {
		return zero.getCurrentPosition()[2];
	}

	/**
	 * This method is needed for WallFollower to implement KeyListener
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
	}
	
	/**
	 * This method is needed for WallFollower to implement KeyListener
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	/**
	 * This method is needed for WallFollower to implement KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
	

