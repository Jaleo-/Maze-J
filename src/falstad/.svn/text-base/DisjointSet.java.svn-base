package falstad;
/**
 * This code is taken from Mark Allen Weiss from the link http://www.cs.berkeley.edu/~jrs/61bs02/hw/hw9/set/DisjointSets.java.  
 * Comments are my own and meant to reflect my understanding of the code.
 */

public class DisjointSet {

	  int[] array;

	  /**
	   * The class is initialized here with the constructor.  All of the values within the array start with a value -1, to 
	   * show that they have not been made yet.  
	   **/
	  public DisjointSet(int dimension) {
	    array = new int [dimension];
	    for (int i = 0; i < array.length; i++) {
	      array[i] = -1;
	    }
	  }

	  /**
	   * Union is intended to make to combine two disjoint sets together.  It is important that the smaller set latches on to the
	   * bigger set.  It is important as the alternative (bigger set latches on to smaller set) could result in a really long linked list
	   * instead of a tree (makes traversing through the set much harder).  However, height is measured differently here.  Given that for find(int x) 
	   * we need array[root] to always be less than 0 or the find function fails.  Thus the root with the lowest value means they have the greatest height. 
	   **/
	  public void union(int root1, int root2) {
		  //root2 is the bigger set
		  if (array[root2] < array[root1]) {
	      array[root1]= root2;
	     
	    } else {
	    	//root1 is the bigger set
	      if (array[root1] == array[root2]) {
	    	/**The only situation that the height of the root will change is when the two roots have the same height.  The smaller root points itself to the bigger root
	    	 * thus the height from that subtree will be the smaller root height plus 1 (thus the height wouldn't change).  However, if they were the same then we could expect 
	    	 * height to increase by one.  Thus root1 is decreased by 1, signifies an increase by height by 1.
	    	 */
	        array[root1]--;            
	      }
	      array[root2] = root1;      
	    }
	  }

	  /**
	   * Two scenarios exist for this case.  If the array turns out with a negative 1 then we know then we have reached the root. Otherwise the method
	   * continues to traverse through the disjoint set until we reach the root.  
	   **/
	  public int find(int x) {
	    if (array[x] < 0) {
	      return x;                        
	    } else {
	      array[x] = find(array[x]);
	      return array[x];                                       
	    }
	  }

}