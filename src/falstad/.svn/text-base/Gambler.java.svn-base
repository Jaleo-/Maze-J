package falstad;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * 
 * @author aablohm and jaleo
 * 
 * This class defines a robot driver that gambles when faced with options.  The robot used only has two distance sensors, one on the left and one ahead.  The gambler uses its
 * distance sensors to check if there are walls on the left or ahead.  If there are, it takes an appropriate action.  If not, the driver randomly picks either the forward
 * or left direction and sends the robot in that direction.  
 *
 */
public class Gambler implements RobotDriver, KeyListener{
	Robot zero = new BasicRobot();
	Random random = new Random();
	
	public Gambler(){	
	}
	
	public Gambler(int num){
		random = new Random(num);
	}
	/**
	 * Maze calls this function so that we can assign the robot that will be used within the application.  
	 */
	public void setRobot(Robot r) throws UnsuitableRobotException {
		zero = r;	
	}

	@Override
	/**
	 * The robot will continue to be able to drive until it's battery is depleted.  Then it remains frozen, only terminating him now will
	 * he truly be saved....and they said this was a kid friendly game.  Since the robot only has two distance sensors, if there is a wall on its left and none ahead, it will
	 * move forward.  If there is a wall ahead and none on its left, then it will move to the left.  If neither the left wall or the forward wall exist, it will randomly choose one 
	 * of those options and move in that direction.  If there is a left wall and a forward wall, the robot rotates to the right and then moves a step forward and checks its sensors
	 * again.
	 */
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
			if(left_wall!=0 && front_wall == 0){
				zero.rotate(90);
				zero.move(1, true);
			}
			else if(front_wall != 0 && left_wall ==0)
				zero.move(1,true);
			else if(front_wall!=0 &&left_wall!=0){
				if(random.nextInt()%2==0){
					zero.rotate(90);
					zero.move(1, true);
				}
				else{
					zero.move(1, true);
				}
			}
			else{
				zero.rotate(-90);
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
				else {
					zero.rotate(-90);
					zero.move(1, true);}
				if (zero.isAtGoal()){;
					count -=1;
			}
		}
			if(zero.isAtGoal()&&!zero.hasStopped()){
				return true;
			}
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

	/**
	 * This method is needed for Gambler to implement KeyListener
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This method is needed for Gambler to implement KeyListener
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is needed for Gambler to implement KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
