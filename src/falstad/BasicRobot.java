/**
 * 
 */
package falstad;

/**
 * @author aablohm, jaelo
 *A robot of simple means.  Carries all four distance sensors and can rotate/move upon the user's wishers (as long as it obeys the
 *maze's strict regulations).  
 */
public class BasicRobot implements Robot {
	int num_steps = 0;
	int battery = 2500;
	int rotationEnergy = 3;
	int stepEnergy = 5;
	Maze maze;
	int exit_x = -1, exit_y = -1;
	boolean EXIT = false;
	
	/**
	 * An empty constructor
	 */
	public BasicRobot(){
		
	}
	/**
	 * A constructor that records the maze it is given.
	 * @param maze
	 */
	public BasicRobot(Maze maze){
		this.maze = maze;
	}

	@Override
	/**
	 * Rotate method that only takes in two degrees (90, -90).  It will then rotate either left or right.
	 * If you want to try and spice things up by putting a really hot number like 245.66734 well then tough cause
	 * nothing will happen.
	 */
	public void rotate(int degree) throws UnsupportedArgumentException {
		battery = battery - rotationEnergy;
		if (degree == 90) 
			maze.rotate(1);
		else if (degree ==-90) {
			maze.rotate(-1);
			}
	}

	@Override
	/**
	 * Move method that will move forward or backwards.  If the forward variable is declared true then it will move forward.
	 * If false it will move backwards.  If indecisive, then nothing will happen.  Function continues to occur until distance occurs.
	 * Note that if battery is less than 0 then it will not move as it does not operate on fairy dust.
	 */
	public void move(int distance, boolean forward) throws HitObstacleException {
		while (distance > 0){
			battery = battery -stepEnergy;
			distance -= 1;
			if (forward&& !hasStopped()) {
				num_steps+=1;
				maze.walk(1);
				}
			else if(!hasStopped()) {
				num_steps+=1;
				maze.walk(-1);
				}
	
			isAtGoal();
		}
	}

	@Override
	/**
	 *Gets the x and y position from maze, specifying the specific cell the robot is in.  Num_steps is also included in this array as it 
	 *permits the driver to have an easily accessible way to get the path length.  
	 */
	public int[] getCurrentPosition() {
		int[] positionArray = {maze.px, maze.py, num_steps};
		return positionArray;
	}

	@Override
	/**
	 *Checks to see if we are at the exit position.  If we are not at the exit position
	 * then it returns false.
	 */
	public boolean isAtGoal() {
		if(exit_x ==-1 && exit_y == -1 )
			findExit();
		int[] exit = {exit_x, exit_y};
		int[] other = getCurrentPosition();
		if (exit_x == other[0] && exit_y== other[1]){
			EXIT = true;
			return EXIT;
		}
		return EXIT;
	}
	/**
	 *A method created that searches the border of maze so that we may find the exit to the maze.  The exit is found as one of the walls on the
	 *border must be torn.  If no wall is torn from a section of the border, and thus no exit, we can safely assume that the maze was created
	 *to kill our poor little robot. 
	 */
	private void findExit(){
		int i , j, count =0;
		for ( j = 0; j < maze.mazecells.height-1; j++){
			i = 0;
			if ( maze.mazecells.hasNoWallOnLeft(i, j) ){
				exit_x = i;
				exit_y = j;}
		}
		
		for( i = 0; i < maze.mazecells.width-1; i++){
			j = 0;
			if ( maze.mazecells.hasNoWallOnTop(i, j) ){
				exit_x = i;
				exit_y = j;}	
		}
		
		for( j = 0; j < maze.mazecells.height-1; j++){
			i = maze.mazecells.width - 1;
			if ( maze.mazecells.hasNoWallOnRight(i, j) ){
				exit_x = i;
				exit_y = j;}
		}
		
		for (i = 0; i < maze.mazecells.width - 1; i++){
			j = maze.mazecells.height -1;
			if ( maze.mazecells.hasNoWallOnBottom(i, j)){
				exit_x = i;
				exit_y = j;}
		}
	}

	@Override
	/**
	 * Returns the current direction in the maze.
	 */
	public int[] getCurrentDirection() {
		int[] currentDirection = {maze.dx, maze.dy};
		return currentDirection;
	}

	@Override
	/**
	 * Returns the current battery level of our fellow robot.
	 */
	public float getCurrentBatteryLevel() {
		return battery;
	}

	@Override
	/**
	 * Returns for a full rotation (360) thus it would be the rotation energy (90) and times four.  This works as 90*4 =360.
	 * Math is fun. 
	 */
	public float getEnergyForFullRotation() {
		return rotationEnergy * 4;
	}

	@Override
	/**
	 * Returns the required energy for the robot to step forward(or backward)
	 */
	public float getEnergyForStepForward() {
		return stepEnergy;
	}

	/**
	 * Checks if the robot has run out of energy.  If so it has stopped.
	 */
	@Override
	public boolean hasStopped() {
		if (battery <= 0)
			return true;
		else
			return false;
	}


	@Override
	/**
	 * Checks if the exit is seen ahead.
	 */
	public boolean canSeeGoalAhead() throws UnsupportedMethodException {
		if(exit_x ==-1 && exit_y == -1 )
			findExit();
		if(distanceToObstacleAhead()==-1)
			return true;
		return false;
		
	}

	@Override
	/**
	 * Checks if the exit is seen behind.
	 */
	public boolean canSeeGoalBehind() throws UnsupportedMethodException {
		if(exit_x ==-1 && exit_y == -1 )
			findExit();

		if(distanceToObstacleBehind()==-1)
			return true;
		return false;
	}

	@Override
	/**
	 * Checks if the exit is seen from the left side of the robot.
	 */
	public boolean canSeeGoalOnLeft() throws UnsupportedMethodException {
		if(exit_x ==-1 && exit_y == -1 )
			findExit();
	
		
		if(distanceToObstacleOnLeft()==-1)
			return true;
		return false;
	}

	@Override
	/**
	 * Checks if the exit is seen from the right side of the robot.
	 */
	public boolean canSeeGoalOnRight() throws UnsupportedMethodException {
		if(exit_x ==-1 && exit_y == -1 )
			findExit();
	
		if(distanceToObstacleOnRight()==-1)
			return true;
		return false;
}
	@Override
	/**
	 * Finds the path length to next wall in front of the robot. Note that this requires the robot to use up 1 piece of energy. 
	 */
	public int distanceToObstacleAhead() throws UnsupportedMethodException {
		battery = battery - 1;
		int numSteps = 0;
		int temp_x = getCurrentPosition()[0];
		int temp_y = getCurrentPosition()[1];
		
		int dx = getCurrentDirection()[0];
		int dy = getCurrentDirection()[1];
		int dir = dx + dy * 2;
		if(dir ==1){
			while (!maze.mazecells.hasWallOnRight(temp_x, temp_y) && !maze.mazecells.hasBoundOnRight(temp_x, temp_y)){
				temp_x += 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnRight(temp_x, temp_y)&& !maze.mazecells.hasWallOnRight(temp_x, temp_y))
				numSteps = -1;
		}
		if(dir ==-1){
			while (!maze.mazecells.hasWallOnLeft(temp_x, temp_y) && !maze.mazecells.hasBoundOnLeft(temp_x, temp_y)){
				temp_x -= 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnLeft(temp_x, temp_y)&& !maze.mazecells.hasWallOnLeft(temp_x, temp_y))
				numSteps = -1;
		}
		if(dir ==2){
			while (!maze.mazecells.hasWallOnBottom(temp_x, temp_y) && !maze.mazecells.hasBoundOnBottom(temp_x, temp_y)){
				temp_y += 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnBottom(temp_x, temp_y)&&!maze.mazecells.hasWallOnBottom(temp_x, temp_y) )
				numSteps = -1;
		}
		if(dir ==-2){
			while (!maze.mazecells.hasWallOnTop(temp_x, temp_y) && !maze.mazecells.hasBoundOnTop(temp_x, temp_y)){
				temp_y -= 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnTop(temp_x, temp_y)&& !maze.mazecells.hasWallOnTop(temp_x, temp_y))
				numSteps = -1;
		}
		return numSteps;
	}

	@Override
	/**
	 * Finds the path length to next wall to the left of the robot. Note that this requires the robot to use up 1 piece of energy. 
	 */
	public int distanceToObstacleOnRight() throws UnsupportedMethodException {
		battery = battery - 1;
		int numSteps = 0;
		int temp_x = getCurrentPosition()[0];
		int temp_y = getCurrentPosition()[1];
		
		int dx = getCurrentDirection()[0];
		int dy = getCurrentDirection()[1];
		int dir = -2*dx + dy;
		if(dir ==1){

			while (!maze.mazecells.hasWallOnRight(temp_x, temp_y) && !maze.mazecells.hasBoundOnRight(temp_x, temp_y)){
				temp_x += 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnRight(temp_x, temp_y)&& !maze.mazecells.hasBoundOnRight(temp_x, temp_y))
				numSteps = -1;
		}
		if(dir ==-1){
			while (!maze.mazecells.hasWallOnLeft(temp_x, temp_y) && !maze.mazecells.hasBoundOnLeft(temp_x, temp_y)){
				temp_x -= 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnLeft(temp_x, temp_y)&& !maze.mazecells.hasWallOnLeft(temp_x, temp_y))
				numSteps = -1;
		}
		if(dir ==2){
			while (!maze.mazecells.hasWallOnBottom(temp_x, temp_y) && !maze.mazecells.hasBoundOnBottom(temp_x, temp_y)){
				temp_y += 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnBottom(temp_x, temp_y)&&!maze.mazecells.hasWallOnBottom(temp_x, temp_y) )
				numSteps = -1;
		}
		if(dir ==-2){
			while (!maze.mazecells.hasWallOnTop(temp_x, temp_y) && !maze.mazecells.hasBoundOnTop(temp_x, temp_y)){
				temp_y -= 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnTop(temp_x, temp_y)&& !maze.mazecells.hasBoundOnTop(temp_x, temp_y))
				numSteps = -1;
		}
		return numSteps;
	}

	@Override
	/**
	 * Finds the path length to next wall to the right of the robot. Note that this requires the robot to use up 1 piece of energy. 
	 */
	public int distanceToObstacleOnLeft() throws UnsupportedMethodException {
		battery = battery - 1;
		int numSteps = 0;
		int temp_x = getCurrentPosition()[0];
		int temp_y = getCurrentPosition()[1];
		
		int dx = getCurrentDirection()[0];
		int dy = getCurrentDirection()[1];
		int dir = 2*dx + (-1)*dy;
		if(dir ==1){
			while (!maze.mazecells.hasWallOnRight(temp_x, temp_y) && !maze.mazecells.hasBoundOnRight(temp_x, temp_y)){
				temp_x += 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnRight(temp_x, temp_y)&& !maze.mazecells.hasWallOnRight(temp_x, temp_y))
				numSteps = -1;
		}
		if(dir ==-1){
			while (!maze.mazecells.hasWallOnLeft(temp_x, temp_y) && !maze.mazecells.hasBoundOnLeft(temp_x, temp_y)){
				temp_x -= 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnLeft(temp_x, temp_y)&& !maze.mazecells.hasWallOnLeft(temp_x, temp_y))
				numSteps = -1;
		}
		if(dir ==2){
			while (!maze.mazecells.hasWallOnBottom(temp_x, temp_y) && !maze.mazecells.hasBoundOnBottom(temp_x, temp_y)){
				temp_y += 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnBottom(temp_x, temp_y)&&!maze.mazecells.hasWallOnBottom(temp_x, temp_y) )
				numSteps = -1;
		}
		if(dir ==-2){
			while (!maze.mazecells.hasWallOnTop(temp_x, temp_y) && !maze.mazecells.hasBoundOnTop(temp_x, temp_y)){
				temp_y -= 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnTop(temp_x, temp_y)&& !maze.mazecells.hasWallOnTop(temp_x, temp_y))
				numSteps = -1;
		}
		return numSteps;
	}
	@Override
	/**
	 * Finds the path length to next wall behind the robot. Note that this requires the robot to use up 1 piece of energy. 
	 */
	public int distanceToObstacleBehind() throws UnsupportedMethodException {
		battery = battery - 1;
		int numSteps = 0;
		int temp_x = getCurrentPosition()[0];
		int temp_y = getCurrentPosition()[1];
		
		int dx = getCurrentDirection()[0];
		int dy = getCurrentDirection()[1];
		int dir = (-1)*(dx + dy * 2);
		if(dir ==1){
			while (!maze.mazecells.hasWallOnRight(temp_x, temp_y) && !maze.mazecells.hasBoundOnRight(temp_x, temp_y)){
				temp_x += 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnRight(temp_x, temp_y)&& !maze.mazecells.hasWallOnRight(temp_x, temp_y))
				numSteps = -1;
		}
		if(dir ==-1){
			while (!maze.mazecells.hasWallOnLeft(temp_x, temp_y) && !maze.mazecells.hasBoundOnLeft(temp_x, temp_y)){
				temp_x -= 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnLeft(temp_x, temp_y)&& !maze.mazecells.hasWallOnLeft(temp_x, temp_y))
				numSteps = -1;
		}
		if(dir ==2){
			while (!maze.mazecells.hasWallOnBottom(temp_x, temp_y) && !maze.mazecells.hasBoundOnBottom(temp_x, temp_y)){
				temp_y += 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnBottom(temp_x, temp_y)&&!maze.mazecells.hasWallOnBottom(temp_x, temp_y) )
				numSteps = -1;
		}
		if(dir ==-2){
			while (!maze.mazecells.hasWallOnTop(temp_x, temp_y) && !maze.mazecells.hasBoundOnTop(temp_x, temp_y)){
				temp_y -= 1;
				numSteps++;
			}
			if(maze.mazecells.hasBoundOnTop(temp_x, temp_y)&& !maze.mazecells.hasWallOnTop(temp_x, temp_y))
				numSteps = -1;
		}
		return numSteps;
	}
	
	

}
