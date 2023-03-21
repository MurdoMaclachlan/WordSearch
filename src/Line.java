
public class Line {

	Coordinate coords;
	String direction;
	String mode;
	
	public Line(Coordinate coords, String direction, String mode) {
		this.coords = coords;
		this.direction = direction;
		this.mode = mode;
	}
	
	public Coordinate getCoordinates() {
		return coords;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public String getMode() {
		return mode;
	}
}
