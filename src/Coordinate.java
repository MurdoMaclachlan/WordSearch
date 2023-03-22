/**
 * A simple data type for storing, reading and modifying the coordinates of a
 * single point on a 2D plane.
 * 
 * @author Murdo B. Maclachlan
 */
public class Coordinate {

	private int x;
	private int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Set the values of the coordinates.
	 * 
	 * @param x  The value of the X coordinate
	 * @param y  The value of the Y coordinate
	 */
	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the value of the X coordinate.
	 * 
	 * @return  The value of the X coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Set the value of the X coordinate.
	 * 
	 * @param x  The new value for the X coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Get the value of the Y coordinate.
	 * 
	 * @return  The value of the Y coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set the value of the Y coordinate.
	 * 
	 * @param y  The new value for the Y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Create a deep copy of the Coordinate.
	 */
	public Coordinate clone() {
		return new Coordinate(this.x, this.y);
	}
	
	/**
	 * Determine if the Coordinate is equal to a given Object. A Coordinate is considered
	 * equal if the given Object is another Coordinate, and their X and Y values match.
	 * 
	 * @param o  The Object to compare with
	 * 
	 * @return  Whether the Coordinate is equal to the Object
	 */
	public boolean equals(Object o) {
		if (o instanceof Coordinate)
			return this.x == ((Coordinate)o).getX() && this.y == ((Coordinate)o).getY();
		return false;
	}
	
	/**
	 * Returns a string representation of the Coordinate.
	 */
	public String toString() {
		return String.format("[ x: %d, y: %d ]", x, y);
	}
}
