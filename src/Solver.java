import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * A utility class for solving a given word search, represented by a fully formed
 * Grid. Provides the ability to look for given words on the word search Grid,
 * records all words it finds and all it fails to find, and provides the ability
 * to colour in words it finds, highlighting them on the Grid.
 * 
 * @author Murdo B. Maclachlan
 */
public class Solver extends CoordinateSystem {
	
	private Grid grid;
	private HashMap<String, Line> foundWords;
	private ArrayList<String> failedWords;
	
	private static final HashMap<String, String> COLOURS = 
			new HashMap<String, String>();
	
	public Solver(Grid grid) {
		COLOURS.put("red", "\u001B[31m");
		COLOURS.put("green", "\u001B[32m");
		COLOURS.put("yellow", "\u001B[33m");
		COLOURS.put("blue", "\u001B[34m");
		COLOURS.put("purple", "\u001B[35m");
		COLOURS.put("cyan", "\u001B[36m");
		COLOURS.put("white", "\u001B[37m");
		
		this.grid = grid;
		foundWords = new HashMap<String, Line>();
		failedWords = new ArrayList<String>();
	}
	
	/**
	 * Given a word and the coordinates of a starting cell, pursues a line along
	 * the board from that cell in each direction, until it finds the word or
	 * exhausts all directions.
	 * 
	 * @param coords  The starting coordinates
	 * @param word    The word to look for
	 * 
	 * @return  Whether the full word was found
	 */
	private boolean findLine(Coordinate coords, String word) {
		for (String direction : DIRECTIONS) {
			for (String mode : MODES) {
				Line line = new Line(coords, direction, mode);
				try {
					if (!pursue(line, word.substring(1))) continue;
				} catch (IndexOutOfBoundsException e) {
					continue;
				}
				// If pursue() was successful, the full word was found, so record the line it
				// follows, including its starting coordinates
				line.resetPosition();
				foundWords.put(word, line);				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Iterates through all the found words and colours them in.
	 */
	public void colourFoundWords() {
		Set<String> possibleColours = COLOURS.keySet();
		int i = 0;
		for (String word : foundWords.keySet()) {
			String colour = possibleColours.toArray(new String[possibleColours.size()])[i];
			colourWord(colour, foundWords.get(word), word);
			++i;
		}
	}
	
	/**
	 * Colours a single word using colours from a predefined list.
	 * 
	 * @param colour  The colour to use
	 * @param line    The line to follow
	 * @param word    The word to colour
	 */
	private void colourWord(String colour, Line line, String word) {
		for (int i = 0; i < word.length(); ++i) {
			grid.getCell(line.getPosX(), line.getPosY()).setColour(COLOURS.get(colour));
			advanceAlongLine(line);
		}
	}
	
	/**
	 * Searches the board for a single word and reports back whether it was found.
	 * 
	 * If there are duplicate words, this function will only find the first instance
	 * (starting top-left). Ideally, duplicates would be disallowed by the Board, but
	 * current implementation does not work this way.
	 * 
	 * @param word  The word to search for
	 * 
	 * @return  Whether the word was found
	 */
	private boolean findWord(String word) {
		for (ArrayList<Cell> row : grid) {
			for (Cell cell : row)
				// Upon finding a cell that matches the first letter of the word,
				// we can use its coordinates as a start point to search adjacent
				// cells for the rest of the word
				if (cell.equals(word.charAt(0)))
					if (findLine(cell.getCoordinates(), word))
						return true;
		}
		return false;
	}
	
	public ArrayList<String> getFailedWords() {
		return failedWords;
	}
	
	public HashMap<String, Line> getFoundWords() {
		return foundWords;
	}
	
	/**
	 * Given a direction and word, pursues the word alone a line in the direction,
	 * starting from given coordinates, reporting back whether the full word was
	 * found.
	 * 
	 * @param coords  The starting coordinates
	 * @param line    The line to pursue
	 * @param word    The word to look for
	 * 
	 * @return  Whether the word was found
	 */
	private boolean pursue(Line line, String word) {
		// The first character of the word has already been checked, so we can
		// call modifyCoordinates() immediately to avoid duplicating that check
		advanceAlongLine(line);
		for (char c : word.toCharArray()) {
			// If the current cell matches the letter we're checking, call
			// modifyCoordinates() to step along the line and check the next cell;
			// if not, the word isn't here and we return false
			if (grid.getCell(line.getPosX(), line.getPosY()).equals(c)) {
				advanceAlongLine(line);
			} else {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Given a list of words, attempts to find each of them on the board.
	 * 
	 * @param words  The words to look for
	 */
	public void solve(String[] words) {
		for (String word : words) {
			if (!findWord(word))
				failedWords.add(word);
		}
	}
}