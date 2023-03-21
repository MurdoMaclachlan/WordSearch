/**
 * Represents a line extending from a given set of starting coordinates.
 * 
 * Each line holds a direction (diagonal_down, diagonal_up, horizontal, vertical)
 * to traverse, and a mode (backward, forward) in which to traverse it. Additionally,
 * each line keeps track of the current position along itself, and provides the
 * ability to reset back to the starting coordinates.
 * 
 * @author Murdo B. Maclachlan
 */
public class Line {

	Coordinate startingCoordinates;
	Coordinate currentCoordinates;
	String direction;
	String mode;
	
	public Line(Coordinate coords, String direction, String mode) {
		this.startingCoordinates = coords.clone();
		this.currentCoordinates = coords.clone();
		this.direction = direction;
		this.mode = mode;
	}
	
	/**
	 * Gets the coordinates of the current position on the Line.
	 * 
	 * @return  The Coordinate for the current position on the Line
	 */
	public Coordinate getCurrentCoordinates() {
		return currentCoordinates;
	}
	
	/**
	 * Gets the coordinates of the position at which the Line starts.
	 * 
	 * @return  The Coordinate for the start position on the Line
	 */
	public Coordinate getStartCoordinates() {
		return startingCoordinates;
	}
	
	/**
	 * Gets the direction (diagonal_down, diagonal_up, horizontal, vertical) in
	 * which the Line travels.
	 * 
	 * @return  The direction in which the Line travels
	 */
	public String getDirection() {
		return direction;
	}
	
	/**
	 * Gets the mode (backward, forward) in which the Line traverses its direction.
	 * 
	 * @return  The mode in which the Line traverses its direction
	 */
	public String getMode() {
		return mode;
	}
	
	/**
	 * Get the X coordinate of the current position on the Line.
	 * 
	 * @return  The X coordinate of the current position on the Line
	 */
	public int getPosX() {
		return currentCoordinates.getX();
	}
	
	/**
	 * Get the Y coordinate of the current position on the Line.
	 * 
	 * @return  The X coordinate of the current position on the Line
	 */
	public int getPosY() {
		return currentCoordinates.getY();
	}
	
	/**
	 * Resets the current position on the line, setting it back to the starting point.
	 */
	public void resetPosition() {
		currentCoordinates = startingCoordinates.clone();
	}
}
