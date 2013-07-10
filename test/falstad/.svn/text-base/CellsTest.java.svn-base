/**
 * 
 */
package falstad;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

/**
 * @author aablohm
 *
 *This class provides black box tests for the cells class.  It tests all the non-trivial methods by creating one of two different
 *sets of example cells.
 */
public class CellsTest {
	
	//Creates a small scale set of cells for testing.
	private Cells createCells(){
		int[][] cells = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
		return new Cells(cells);
		
	}
	
	//Creates a larger set of cells for testing.
	private Cells createMoreCells(){
		int[][] cells = {{0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}};
		return new Cells(cells);
	}


	/**
	 * Tests the getCells method with valid input.
	 */
	@Test
	public void testGetCells() {
		Cells cells = createCells();
		assertTrue( 0 == cells.getCells(0, 0));
		assertTrue( 0 == cells.getCells(1, 3));
		
	}

	/**
	 * Tests the getMasks method to make sure that all the masks can be accessed from their array.
	 */
	@Test
	public void testGetMasks() {
		Cells cells = createCells();
		int[] mask = cells.getMasks();
		assertTrue( 8 == cells.getMasks()[0]);
		assertTrue( 2 == cells.getMasks()[1]);
		assertTrue( 4 == cells.getMasks()[2]);
		assertTrue( 1 == cells.getMasks()[3]);
	}

	/**
	 * Tests the canGo methodTest method with valid and invalid input.  If there is not a bound or a wall
	 * in the way, canGo should return true.
	 */
	@Test
	public void testCanGo() {
		Cells cells = createCells();
		cells.initialize();
		assertTrue( true == cells.canGo(0, 0, 1, 0));
		assertTrue( true == cells.canGo(0, 0, 0, 1));
		assertTrue( true == cells.canGo(1, 3, 0, -1));
		
		cells.setBoundAndWallToOne(0, 0, 0, 1);
		assertTrue( false == cells.canGo(0, 0, 0, 1));
		assertTrue( true == cells.canGo(0, 0, 1, 0));
	}

	/**
	 * Tests the setBitToZero method.  The method should take down whatever wall is listed as its third input
	 * from the cell given by the first two.  This test was mostly used to understand the inner workings of other
	 * methods such as setWallToZero.  
	 */
	@Test
	public void testSetBitToZero() {
		Cells cells = createCells();
		cells.setBitToOne(0, 0, 1);
		cells.setBitToZero(0, 0, 1);
		assertTrue( true == cells.hasNoWallOnTop(0, 0));
		cells.setBitToOne(0, 0, 2);
		cells.setBitToZero(0, 0, 2);
		assertTrue( true == cells.hasNoWallOnBottom(0, 0));
		cells.setBitToOne(0, 0, 4);
		cells.setBitToZero(0, 0, 4);
		assertTrue( true == cells.hasNoWallOnLeft(0, 0));
		cells.setBitToOne(0, 0, 8);
		cells.setBitToZero(0, 0, 8);
		assertTrue( true == cells.hasNoWallOnRight(0, 0));
		
	}

	/**
	 * Tests the setAllToZero method.  This method should take down all walls in a given cell and is tested by asserting 
	 * that the given cell has no walls.
	 */
	@Test
	public void testSetAllToZero() {
		Cells cells = createCells();
		cells.setBitToOne(0, 0, 1);
		cells.setBitToOne(0, 0, 2);
		cells.setBitToOne(0, 0, 4);
		cells.setBitToOne(0, 0, 8);
		cells.setAllToZero(0, 0);
		assertTrue( true == cells.hasNoWallOnTop(0, 0));
		assertTrue( true == cells.hasNoWallOnBottom(0, 0));
		assertTrue( true == cells.hasNoWallOnLeft(0, 0));
		assertTrue( true == cells.hasNoWallOnRight(0, 0));
	}

	/**
	 * Tests the setVirginToZero method.  A virgin is a cell that has not yet been used by the maze
	 * builder and has CW_VIRGIN marked to 1.  This method marks that the cell has already been 
	 * visited by setting CW_VIRGIN to 0.
	 */
	@Test
	public void testSetVirginToZero() {
		Cells cells = createCells();
		cells.setBitToOne(0, 0, 16);
		cells.setVirginToZero(0, 0);
		assertTrue( false == cells.isVirgin(0, 0)); 
	}

	/**
	 * Tests the setWallToZero method.  Checks to make sure all walls can be taken down for a given cell.
	 * Also includes a test where an incorrect wall bit mask is used.  This results in output to the console
	 * that reads, "Cells: getBit problem 0 0" and it does not take down the wall. 
	 */
	@Test
	public void testSetWallToZero() {
		Cells cells = createCells();
		cells.setBitToOne(0, 0, 1);
		cells.setWallToZero(0, 0, 0, -1);
		assertTrue( true == cells.hasNoWallOnTop(0, 0));
		cells.setBitToOne(0, 0, 2);
		cells.setWallToZero(0, 0, 0, 1);
		assertTrue( true == cells.hasNoWallOnBottom(0, 0));
		cells.setBitToOne(0, 0, 4);
		cells.setWallToZero(0, 0, -1, 0);
		assertTrue( true == cells.hasNoWallOnLeft(0, 0));
		cells.setBitToOne(0, 0, 8);
		cells.setWallToZero(0, 0, 1, 0);
		assertTrue( true == cells.hasNoWallOnRight(0, 0));
		
		//Tests an incorrect wall bit mask.  This should not change the wall that was set.
		cells.setBitToOne(0, 0, 1);
		cells.setWallToZero(0, 0, 0, 0);
		assertTrue( true == cells.hasWallOnTop(0, 0));
	}

	/**
	 * Tests the setBoundToZero method.  After bounds and walls are set to one, this method takes down a bound.
	 * This is checked by asserting that there is no bound on a given cell after that bound has been set to
	 * one.
	 */
	@Test
	public void testSetBoundToZero() {
		Cells cells = createCells();
		cells.setBoundAndWallToOne(0, 0, 0, -1);
		cells.setBoundToZero(0, 0, 0, -1);
		assertTrue( true == cells.hasNoBoundOnTop(0, 0));
		cells.setBoundAndWallToOne(1, 3, 0, 1);
		cells.setBoundToZero(1, 3, 0, 1);
		assertTrue( true == cells.hasNoBoundOnBottom(1, 3));
		cells.setBoundAndWallToOne(0, 4, -1, 0);
		cells.setBoundToZero(0, 4, -1, 0);
		assertTrue( true == cells.hasNoBoundOnLeft(0, 4));
		cells.setBoundAndWallToOne(0, 0, 1, 0);
		cells.setBoundToZero(0, 0, 1, 0);
		assertTrue( true == cells.hasNoBoundOnRight(0, 0));
	}

	/**
	 * This tests the setBitToOne method.  This test was also used as a way to understand what was going on
	 * in the code.  Checks to make sure a given cell now has a wall after its bit has been set to one.
	 */
	@Test
	public void testSetBitToOne() {
		Cells cells = createCells();
		cells.setBitToOne(0, 0, 1);
		assertTrue( true == cells.hasWallOnTop(0, 0) );
		cells.setBitToOne(0, 0, 2);
		assertTrue( true == cells.hasWallOnBottom(0, 0));
		cells.setBitToOne(0, 0, 4);
		assertTrue( true == cells.hasWallOnLeft(0, 0));
		cells.setBitToOne(0, 0, 8);
		assertTrue( true == cells.hasWallOnRight(0, 0));
	}

	/**
	 * This tests the setBoundAndWallToOne method.  Checks to make sure that both a wall and a bound
	 * have been placed in the given cell.
	 */
	@Test
	public void testSetBoundAndWallToOne() {
		Cells cells = createCells();
		cells.setBoundAndWallToOne(0, 0, 0, 1);
		assertTrue( true == cells.hasWallOnBottom(0, 0));
		assertTrue( true == cells.hasBoundOnBottom(0, 0));
		cells.setBoundAndWallToOne(1, 0, 1, 0);
		assertTrue( true == cells.hasWallOnRight(1, 0));
		assertTrue( true == cells.hasBoundOnRight(1, 0));
		cells.setBoundAndWallToOne(1, 3, 0, -1);
		assertTrue( true == cells.hasWallOnTop(1, 3));
		assertTrue( true == cells.hasBoundOnTop(1, 3));
		cells.setBoundAndWallToOne(0, 2, -1, 0);
		assertTrue( true == cells.hasWallOnLeft(0, 2));
		assertTrue( true == cells.hasBoundOnLeft(0, 2));
	}

	/**
	 * Tests the setInRoomToOne method.  This checks to make sure that when a cell is set as being in a room, 
	 * it returns true for cells.isInRoom.
	 */
	@Test
	public void testSetInRoomToOne() {
		Cells cells = createCells();
		cells.setInRoomToOne(0, 0);
		assertTrue( true == cells.isInRoom(0, 0));
		cells.setInRoomToOne(1, 4);
		assertTrue( true == cells.isInRoom(1, 4));
	}

	/**
	 * This tests the setTopToOne method which uses the setBitToOne to just set the top wall.  
	 * Checks to make sure the wall is actually set.
	 */
	@Test
	public void testSetTopToOne() {
		Cells cells = createCells();
		cells.setTopToOne(0, 0);
		assertTrue( true == cells.hasWallOnTop(0, 0));
		cells.setTopToOne(1, 4);
		assertTrue( true == cells.hasWallOnTop(1, 4));

	}

	/**
	 * Tests the initialize method.  Checks to make sure that all the cells are marked as virgins 
	 * and that all of them have the proper bounds and walls.  All cells should have all four
	 * walls up after initialization.  All outer cells should be properly bounded.
	 */
	@Test
	public void testInitialize() {
		Cells cells = createCells();
		cells.initialize();
		
		//Checks that all cells are marked as virgins.
		assertTrue( true == cells.isVirgin(0, 0));
		assertTrue( true == cells.isVirgin(0, 1));
		assertTrue( true == cells.isVirgin(0, 2));
		assertTrue( true == cells.isVirgin(0, 3));
		assertTrue( true == cells.isVirgin(0, 4));
		assertTrue( true == cells.isVirgin(1, 0));
		assertTrue( true == cells.isVirgin(1, 1));
		assertTrue( true == cells.isVirgin(1, 2));
		assertTrue( true == cells.isVirgin(1, 3));
		assertTrue( true == cells.isVirgin(1, 4));
		
		//Checks that the cells are properly bounded
		assertTrue( true == cells.hasBoundOnTop(0, 0) );
		assertTrue( true == cells.hasBoundOnTop(1, 0) );
		assertTrue( true == cells.hasBoundOnBottom(0, 4) );
		assertTrue( true == cells.hasBoundOnBottom(1, 4) );
		assertTrue( true == cells.hasBoundOnLeft(0, 0) );
		assertTrue( true == cells.hasBoundOnLeft(0, 1));
		assertTrue( true == cells.hasBoundOnLeft(0, 2) );
		assertTrue( true == cells.hasBoundOnLeft(0, 3) );
		assertTrue( true == cells.hasBoundOnLeft(0, 4) );
		assertTrue( true == cells.hasBoundOnRight(1, 0) );
		assertTrue( true == cells.hasBoundOnRight(1, 2) );
		assertTrue( true == cells.hasBoundOnRight(1, 3) );
		assertTrue( true == cells.hasBoundOnRight(1, 4) );
		
		//Checks that all cells have all four walls up.
		for (int i = 0; i < cells.width; i++){
			for (int j = 0; j < cells.height; j++){
				assertTrue( true == cells.hasWallOnBottom(i, j));
				assertTrue( true == cells.hasWallOnLeft(i, j));
				assertTrue( true == cells.hasWallOnRight(i, j));
				assertTrue( true == cells.hasWallOnTop(i, j));
			}
		}
		
	}

	/**
	 * Tests the areaOverLapsWithRoom.  A room cannot be on the border.  This checks to make sure that any 
	 * area that overlaps with a room returns true.
	 */
	@Test
	public void testAreaOverlapsWithRoom() {
		Cells cells = createMoreCells();
		Random generator = new Random();
		cells.markAreaAsRoom(1, 1, 2, 2, 2, 2, generator);
		assertTrue( true == cells.areaOverlapsWithRoom(1, 1, 2, 2));
		assertTrue( false == cells.areaOverlapsWithRoom(1, 1, 3, 1));
		assertTrue( true == cells.areaOverlapsWithRoom(2, 2, 3, 3));
		assertTrue( false == cells.areaOverlapsWithRoom(3, 1, 3, 1));
		
		cells.markAreaAsRoom(2, 2, 4, 1, 5, 5, generator);
		assertTrue( true == cells.areaOverlapsWithRoom(4, 2, 4, 3));
		assertTrue( true == cells.areaOverlapsWithRoom(1, 2, 5, 5));
		assertTrue( true == cells.areaOverlapsWithRoom(3, 2, 4, 4));
		assertTrue( true == cells.areaOverlapsWithRoom(0, 0, 6, 6));
		assertTrue( false == cells.areaOverlapsWithRoom(2, 4, 2, 4));

	}

	/**
	 * Tests the addBoundWall method.  This test was used to increase understanding of the underlying code.
	 * It checks to make sure that a bound was added.
	 */
	@Test
	public void testAddBoundWall() {
		Cells cells = createCells();
		cells.addBoundWall(0, 0, 0, 1);
		assertTrue( true == cells.hasBoundOnBottom(0, 0));
		assertTrue( true == cells.hasWallOnBottom(0, 0));
		assertTrue( true == cells.hasBoundOnTop(0, 1));
		assertTrue( true == cells.hasWallOnTop(0, 1));
		
	}

	/**
	 * Tests the deleteWall method.  This checks to make sure that if a bound and wall are both set and 
	 * the wall is deleted that the bound stays.  It also ensures that the wall of the cell that touches
	 * the one being checked hasn't lost any of its walls.
	 */
	@Test
	public void testDeleteWall() {
		Cells cells = createCells();
		cells.addBoundWall(0, 0, 0, 1);
		cells.deleteWall(0, 0, 0, 1);
		assertTrue( true == cells.hasNoWallOnBottom(0, 0));
		assertTrue( true == cells.hasNoWallOnTop(0, 1));
		assertTrue( true == cells.hasBoundOnBottom(0, 0));
	}

	/**
	 * Tests the markAreaAsRoom method to make sure that when an area is marked as being a room, each of the 
	 * cells in the area is marked as being in the room. 
	 */
	@Test
	public void testMarkAreaAsRoom() {
		Cells cells = createMoreCells();
		Random generator = new Random();
		cells.markAreaAsRoom(1, 1, 1, 1, 1, 1, generator );
		assertTrue( true == cells.isInRoom(1, 1));
		
		cells.markAreaAsRoom(1, 2, 3, 2, 3, 3, generator );
		assertTrue( true == cells.isInRoom(3, 2));
		assertTrue( true == cells.isInRoom(3, 3));
		
		cells.markAreaAsRoom(2, 4, 4, 1, 5, 4, generator);
		assertTrue( true == cells.isInRoom(4, 1));
		assertTrue( true == cells.isInRoom(4, 2));
		assertTrue( true == cells.isInRoom(4, 3));
		assertTrue( true == cells.isInRoom(4, 4));
		assertTrue( true == cells.isInRoom(5, 1));
		assertTrue( true == cells.isInRoom(5, 2));
		assertTrue( true == cells.isInRoom(5, 3));
		assertTrue( true == cells.isInRoom(5, 4));
		assertTrue( false == cells.isInRoom(5, 5));
		
	}


	/**
	 * Tests the hasMaskedBitsGTZero method. 
	 */
	@Test
	public void testHasMaskedBitsGTZero() {
		Cells cells = createCells();
		cells.setBoundAndWallToOne(0, 0, 0, -1);
		assertTrue( true == cells.hasMaskedBitsGTZero(0, 0, 1));
		assertTrue( false == cells.hasMaskedBitsGTZero(1, 1, 16));
	}

}
