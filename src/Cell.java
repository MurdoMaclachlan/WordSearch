/**
 * Represents a single cell on a grid. Stores the character to be printed at
 * said cell, the colour to print it in, and its coordinates. Also provides
 * a number of methods for interacting with and updating its data, as well as
 * comparing it to other cells.
 * 
 * @author Murdo B. Maclachlan
 */
public class Cell {

	private char character;
	private String colour;
	private final Coordinate coordinates;
	private static final String RESET = "\033[0m";
	
	public Cell(char character, int x, int y) {
		this.character = character;
		this.colour = "";
		this.coordinates = new Coordinate(x, y);
	}
	
	/**
	 * Fetch the character stored in the Cell.
	 * 
	 * @return  The character stored in the Cell
	 */
	public char getCharacter() {
		return character;
	}
	
	/**
	 * Set the character stored in the Cell.
	 * 
	 * @param character  The new character to store in the Cell
	 */
	public void setCharacter(char character) {
		this.character = character;
	}
	
	/**
	 * Fetch the colour associated with the Cell.
	 * 
	 * @return  The colour associated with the Cell
	 */
	public String getColour() {
		return colour;
	}
	
	/**
	 * Set the colour associated with the Cell.
	 * 
	 * @param colour  The new colour to associate with the Cell
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}
	
	/**
	 * Fetch the coordinates for the Cell.
	 * 
	 * @return  The coordinates for the Cell
	 */
	public Coordinate getCoordinates() {
		return coordinates;
	}
	
	/**
	 * Determine if the Cell is equal to a given char.
	 * 
	 * @param c  The char to compare with
	 * 
	 * @return  Whether the Cell is equal to the char
	 */
	public boolean equals(char c) {
		return this.character == c;
	}
	
	/**
	 * Determine if the Cell is equal to a given Object. A Cell is considered equal if the
	 * given Object is another Cell, and their coordinates match.
	 * 
	 * @param o  The Object to compare with
	 * 
	 * @return  Whether the Cell is equal to the Object
	 */
	public boolean equals(Object o) {
		if (o instanceof Cell)
			return this.coordinates.equals(((Cell)o).getCoordinates());
		return false;
	}
	
	/**
	 * Convert the Cell to a String. The String is made up of a single character,
	 * coloured with the Cell's colour.
	 * 
	 * @return  The String conversion of the Cell
	 */
	public String toString() {
		return colour.length() > 0 ? colour + character + RESET : Character.toString(character);
	}
}
