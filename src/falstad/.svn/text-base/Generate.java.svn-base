/**
 * 
 */
package falstad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * @author aablohm
 * This class creates a listener to use when generating a maze.  The level should correspond with whatever value the slider is currently pointing to.
 * When the generate and play button is selected, the maze is built with the skill level selected on the slider and the type indicated by the radio buttons.
 */
public class Generate implements ActionListener {
	
	public static int level;
	Maze maze;
	public Generate(Maze maze, int level){
		this.maze = maze;
		this.level = level;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	
		//System.out.println(level);
		maze.build(level);
		
	}
	public int returnLevel(){
		return level;
	}
}
