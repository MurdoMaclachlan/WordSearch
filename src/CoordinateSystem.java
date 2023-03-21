/**
 * A simple base class for storing directions and allowing coordinates to be
 * modified based on information about a line to follow. Provides a basis for
 * properly implemented coordinate systems, such as Board and Solver.
 * 
 * @author Murdo B. Maclachlan
 */
public class CoordinateSystem {

	protected static final String[] DIRECTIONS = {
		"diagonal_down",
		"diagonal_up",
		"horizontal",
		"vertical"
	};
	protected static final String[] MODES = {
		"backward",
		"forward"
	};
	
	/**
	 * Advances along a given line in its defined direction, by exactly one step.
	 * 
	 * @param line  The line to follow
	 */
	protected void advanceAlongLine(Line line) {
		// Determine whether to increment or decrement coordinates
		int modifier = line.getMode().equals("forward") ? 1 : -1;
		
		Coordinate currentCoordinates = line.getCurrentCoordinates();
		int x = currentCoordinates.getX();
		int y = currentCoordinates.getY();
		
		// Note that, for the purposes of the grid, the origin point, 0, exists at the top-left corner.  Thus, for each
		// step DOWN, the Y coordinate increments, as opposed to the more standard UP correlation in the Cartesian
		// coordinate system.
		switch (line.getDirection()) {
			case "diagonal_down" -> {
				x += modifier;
				y += modifier;
			}
			case "diagonal_up" -> {
				x += modifier;
				y -= modifier;
			}
			case "horizontal" -> {
				x += modifier;
			}
			case "vertical" -> {
				y += modifier;
			}
			default -> { 
				System.out.println("WARNING: Board.modifyCoordinates() does not know how to handle direction: " + line.getDirection());
				System.out.println("WARNING: Skipping the above attempt to modify coordinates. May cause further errors.");
			}
		}
		
		currentCoordinates.setCoordinates(x, y);
	}
}
