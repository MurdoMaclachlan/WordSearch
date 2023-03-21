import java.util.ArrayList;
import java.util.Iterator;

public class Grid implements Iterable<ArrayList<Cell>> {

	ArrayList<ArrayList<Cell>> grid;
	
	public Grid() {
		this.grid = new ArrayList<ArrayList<Cell>>();
	}
	
	public void addRow(ArrayList<Cell> row) {
		grid.add(row);
	}
	
	public ArrayList<Cell> getRow(int y) {
		return grid.get(y);
	}
	
	public Cell getCell(int x, int y) {
		return grid.get(y).get(x);
	}
	
	public void setCell(int x, int y, Cell c) {
		grid.get(y).set(x, c);
	}
	
	public int size() {
		return grid.size();
	}
	
	@Override
	public Iterator<ArrayList<Cell>> iterator() {
		return grid.iterator();
	}
}
