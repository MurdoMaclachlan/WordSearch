import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the board for a word search puzzle. Capable of generating an empty
 * Grid of a given size, adding given words to that grid, and filling out any
 * remaining empty spaces with random letters. Everything necessary to create a
 * basic word search.
 * 
 * @author Murdo B. Maclachlan
 */
public class Board extends CoordinateSystem {
	
	int failedWordCount;
	int size;
	int timeout;
	
	Grid grid;
	Random random = new Random();
	
	public Board(int size, int timeout) {
		this.size = size;
		this.timeout = timeout;
		
		failedWordCount = 0;
		this.generateEmptyGrid();
	}
	
	/**
	 * Attempts to add a single word at a given set of coordinates on the grid, using a given line to
	 * determine the direction the word should face.
	 * 
	 * @param coords  The coordinates to add the word at
	 * @param line    The line direction the word should follow
	 * @param word    The word to add
	 * 
	 * @return  Whether the word was successfully added
	 */
	public boolean addWord(Line line, String word) {
		for (char c : word.toCharArray()) {
			Cell cell = grid.getCell(line.getPosX(), line.getPosY());
			// We can only insert a character into any cell that is empty or already contains that
			// letter; otherwise we'd overwrite words we already added.
			if (cell.equals(c) || cell.equals(' ')) {
				advanceAlongLine(line);
			} else {
				return false;
			}
		}
		
		// Insert the word into the board, using the starting coordinates and moving down the
		// same line, this time actually setting the characters
		line.resetPosition();
		for (char c : word.toCharArray()) {
			grid.getCell(line.getPosX(), line.getPosY()).setCharacter(c);
			advanceAlongLine(line);
		}
		
		return true;
	}
	
	/**
	 * Fills all empty cells on the grid with random alphabetical characters.
	 */
	public void fillRemainder() {
		for (ArrayList<Cell> row : grid) {
			for (Cell cell : row) {
				if (cell.equals(' ')) {
					cell.setCharacter(Character.toUpperCase(((char)('a' + random.nextInt(26)))));
				}
			}
		}
	}
	
	/**
	 * Fills the grid with given words at random positions and in random directions.
	 * 
	 * @param words  The words to add
	 */
	public void fillWords(String[] words) {
		boolean success;
		int attempts;
		
		for (String word : words) {
			success = false;
			attempts = 0;
			
			// The easiest way to insert each word is to simply loop, choosing random coordinates
			// and directions until we either succeed or time out
			while (!success && attempts < timeout) {
				++attempts;
				Coordinate coords = new Coordinate(random.nextInt(size), random.nextInt(size));
				Line line = new Line(coords, DIRECTIONS[random.nextInt(4)], MODES[random.nextInt(2)]);
				
				// An IndexOutOfBoundsException will occur if the word runs past the edge of the board, so we
				// have to catch it. Given it just signifies a failure, we don't need to do anything with it.
				try {
					success = addWord(line, word);
				} catch (IndexOutOfBoundsException e) {
					;
				}
				
				// We need a timeout to avoid an infinite loop in case the word combination doesn't fit anywhere
				if (!success && attempts == timeout) {
					System.out.println(
						String.format("Failed to add %s; timed out on too many failed attempts.", word)
					);
					++failedWordCount;
					break;
				}
			}
		}
	}
	
	/**
	 * Constructs a 2D grid as an array list of array lists of characters. Each array list of characters
	 * is a row, and each character is a single cell on  a given row.
	 */
	public void generateEmptyGrid() {
		 grid = new Grid();
		 for (int y = 0; y < size; ++y) {
			 ArrayList<Cell> row = new ArrayList<Cell>();
			 for (int x = 0; x < size; ++x)
				 row.add(new Cell(' ', x, y));
			 grid.addRow(row);
		 }
	}
	
	/**
	 * Fetch the number of words that failed to be added to the board.
	 * 
	 * @return  The number of failed words
	 */
	public int getFailedWordCount() {
		return failedWordCount;
	}
	
	/**
	 * Fetch the grid.
	 * 
	 * @return  The grid
	 */
	public Grid getGrid() {
		return grid;
	}
}
