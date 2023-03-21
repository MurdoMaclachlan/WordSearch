import java.util.ArrayList;
import java.util.Iterator;

/**
 * Holds an array of rows of Cells representing a grid. Provides a limited API for
 * interacting with this grid, as well as the option to iterate over rows.
 * 
 * @author Murdo B. Maclachlan
 */
public class Grid implements Iterable<ArrayList<Cell>> {

	ArrayList<ArrayList<Cell>> grid;
	
	public Grid() {
		this.grid = new ArrayList<ArrayList<Cell>>();
	}
	
	/**
	 * Add a row of Cells to the Grid.
	 * 
	 * @param row  The row to add
	 */
	public void addRow(ArrayList<Cell> row) {
		grid.add(row);
	}
	
	/**
	 * Fetch a row of Cells at a specific Y coordinate.
	 * 
	 * @param y  The Y coordinate of the row to fetch
	 * 
	 * @return  The row
	 */
	public ArrayList<Cell> getRow(int y) {
		return grid.get(y);
	}
	
	/**
	 * Fetch a Cell at given coordinates on the Grid.
	 * 
	 * @param x  The X coordinate of the Cell
	 * @param y  The Y coordinate of the Cell
	 * 
	 * @return  The Cell
	 */
	public Cell getCell(int x, int y) {
		return grid.get(y).get(x);
	}
	
	/**
	 * Set given coordinates on the Grid to hold a given Cell.
	 * 
	 * @param x  The X coordinate of the Cell
	 * @param y  The Y coordinate of the Cell
	 * @param c  The Cell to set at the given coordinates
	 */
	public void setCell(int x, int y, Cell c) {
		grid.get(y).set(x, c);
	}
	
	/**
	 * Returns an iterator over elements of type Cell.
	 * 
	 * @return  An Iterator
	 */
	@Override
	public Iterator<ArrayList<Cell>> iterator() {
		return grid.iterator();
	}
}
