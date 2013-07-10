/**
 * 
 */
package falstad;

/**
 * @author aablohm
 *Class used to assign each cell a set id number.
 */
public class CellAndID {
		public int x;
		public int y;
		public int id;
		
		/**
		 * Constructor
		 * @param x x position of the cell
		 * @param y y position of the cell
		 * @param id id set id for the cell

		 */
		public CellAndID(int xx, int yy, int setId){
			x=xx;
			y=yy;
			id = setId;
		}


}
