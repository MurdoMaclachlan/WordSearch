
public class Cell {

	private char character;
	private String colour;
	private static final String RESET = "\033[0m";
	
	public Cell(char character) {
		this.character = character;
		this.colour = "";
	}
	
	public char getCharacter() {
		return character;
	}
	
	public void setCharacter(char character) {
		this.character = character;
	}
	
	public String getColour() {
		return colour;
	}
	
	public void setColour(String colour) {
		this.colour = colour;
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
	 * Determine if the Cell is equal to a given Object. A cell is considered
	 * equal the given Object is another Cell, and their its coordinates and
	 * characters match.
	 * 
	 * @param o  The Object to compare with
	 * 
	 * @return  Whether the Cell is equal to the Object
	 */
	public boolean equals(Object o) {
		if (o instanceof Cell)
			return this.character == ((Cell)o).getCharacter() && this.colour == ((Cell)o).getColour();
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
