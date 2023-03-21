
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
	
	public Coordinate getCurrentCoordinates() {
		return currentCoordinates;
	}
	
	public Coordinate getStartCoordinates() {
		return startingCoordinates;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public String getMode() {
		return mode;
	}
	
	public int getPosX() {
		return currentCoordinates.getX();
	}
	
	public int getPosY() {
		return currentCoordinates.getY();
	}
	
	public void resetPosition() {
		currentCoordinates = startingCoordinates.clone();
	}
}
