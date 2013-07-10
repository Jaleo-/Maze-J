/**
 * 
 */
package falstad;



import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author aablohm
 *This class creates a listener to keep track of the changes in the slider bar.  This indicates the skill level the user wishes to generate the maze with.
 */
public class SkillLevelListener implements ChangeListener {
	
	Generate generateListener;
	JSlider difficulty;
	
	public SkillLevelListener(Generate generateListener, JSlider difficulty){
		this.generateListener = generateListener;
		this.difficulty = difficulty;
	}


	@Override
	public void stateChanged(ChangeEvent arg0) {
		generateListener.level = difficulty.getValue();
		
	}

}
