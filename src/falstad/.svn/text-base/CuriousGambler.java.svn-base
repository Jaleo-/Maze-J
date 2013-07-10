package falstad;
import java.util.Collections;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * @author aablohm and jaleo
 * 
 * This class defines a robot driver that uses an array to keep track of all the unique positions in the maze.  It can use this array to make sure it visits a cell to 
 * its left or ahead that has not been visited as often.  If the cells have been visited an equal number of times, it behaves like the Gambler driver in that it uses 
 * a random number to determine whether to go left or ahead if it has the option.
 *
 */
public class CuriousGambler implements RobotDriver, KeyListener{
	Robot zero = new BasicRobot();

	Random random = new Random();
	
	public CuriousGambler(){	
	}
	
	public CuriousGambler(int num){
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
	 * he truly be saved....and they said this was a kid friendly game.  While running, the robot senses in the left or forward directions to see if there
	 * are walls there.  If there are, it chooses the direction not blocked by a wall.  If it has the option to go either left or forward, it will check to 
	 * see which cell has been visited less and move there.  If both have been visited equally, it will randomly pick between the two.
	 */
	public boolean drive2Exit() throws Exception {
		int left_wall=0, front_wall=0;
		Random random = new Random();
		int[] array = new int[300*240];
		for(int i = 0; i<300*240; i++)
			array[i]=0;
		int temp[] =new int [3];
		int dir [] = new int [2];
		while (!zero.hasStopped()&& (!(zero.isAtGoal()))){
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
				temp = zero.getCurrentPosition();
				array[(temp[0]+temp[1]*(240))] += 1;
			}
			else if(front_wall != 0 && left_wall ==0){
				zero.move(1,true);
				temp = zero.getCurrentPosition();
				array[(temp[0]+temp[1]*(240))] += 1;
			}
			else if(front_wall!=0 &&left_wall!=0){
				temp = zero.getCurrentPosition();
				dir = zero.getCurrentDirection();
				if(array[((temp[0] + dir[0]) +(temp[1] +dir[1])*240)] > array[((temp[0] -dir[1]) + (temp[1] + dir[0])*240)]){
					zero.rotate(90);
					zero.move(1, true);
					temp = zero.getCurrentPosition();
					array[(temp[0]+temp[1]*(240))] += 1;	
				}
				else if(array[((temp[0] + dir[0]) +(temp[1] +dir[1])*240)] < array[((temp[0] -dir[1]) + (temp[1] + dir[0])*240)]){
					zero.move(1, true);
					temp = zero.getCurrentPosition();
					array[(temp[0]+temp[1]*(240))] += 1;
				}
				else{
					if(random.nextInt()%2==0) {
						zero.rotate(90);
						zero.move(1, true);
						temp = zero.getCurrentPosition();
						array[(temp[0]+temp[1]*(240))] += 1;	
					}
					else{
						zero.move(1, true);
						temp = zero.getCurrentPosition();
						array[(temp[0]+temp[1]*(240))] += 1;
					}
				}
			}
			else
				zero.rotate(-90);	
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
	
	/**
	 * This method is needed for CuriousGambler to implement KeyListener
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
	}
	
	/**
	 * This method is needed for CuriousGambler to implement KeyListener
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	
	}
	
	/**
	 * This method is needed for CuriousGambler to implement KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	
	}

}
