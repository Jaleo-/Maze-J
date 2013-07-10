package falstad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
	
/**
 * 
 * @author aablohm
 *This class creates a listener to determine which algorithm is being used to generate the maze.  The radio button are in a button group and thus only
 *one can be selected at a time.
 */
public class RadioListener implements ActionListener {
	Maze maze;
	ButtonGroup radioButtons;
	JButton selectedButton;
	String type;
	public RadioListener(Maze maze, ButtonGroup radioButtons){
		this.maze = maze;
		this.radioButtons = radioButtons;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		type = e.getActionCommand();
		if (type.equals("Original"))
			maze.method = 0;
		else if(type.equals("Prim's Algorithm"))
			maze.method = 1;
		else if(type.equals("Kruskal's Algorithm"))
			maze.method = 2;
	}

}
