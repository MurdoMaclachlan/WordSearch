import java.util.ArrayList;
import java.util.Random;

public class Board extends CoordinateManager {
	
	int failedWordCount;
	int size;
	int timeout;
	
	ArrayList<ArrayList<Cell>> grid;
	
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
	public boolean addWord(Coordinate coords, Line line, String word) {
		Coordinate startCoords = coords.clone();
		
		for (char c : word.toCharArray()) {
			Cell cell = grid.get(coords.getY()).get(coords.getY());
			// We can only insert a character into any cell that is empty or already contains that
			// letter; otherwise we'd words we already added.
			if (cell.equals(c) || cell.equals(' ')) {
				coords = modifyCoordinates(coords, line);
			} else {
				return false;
			}
		}
		
		// Insert the word into the board, using the starting coordinates and moving down the
		// same line, this time actually setting the characters
		coords = startCoords;
		for (char c : word.toCharArray()) {
			grid.get(coords.getY()).get(coords.getX()).setCharacter(c);
			coords = modifyCoordinates(coords, line);
		}
		
		return true;
	}
	
	/**
	 * Fills all empty cells on the grid with random alphabetical characters.
	 */
	public void fillRemainder() {
		Random r = new Random();
		for (ArrayList<Cell> row : grid) {
			for (Cell cell : row) {
				if (cell.equals(' ')) {
					cell.setCharacter(Character.toUpperCase(((char)('a' + r.nextInt(26)))));
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
		Random r = new Random();
		boolean success;
		int attempts;
		
		for (String word : words) {
			success = false;
			attempts = 0;
			
			// The easiest way to insert each word is to simply loop, choosing random coordinates
			// and directions until we either succeed or time out
			while (!success && attempts < timeout) {
				attempts++;
				Coordinate coords = new Coordinate(r.nextInt(size), r.nextInt(size));
				Line line = new Line(coords, DIRECTIONS[r.nextInt(4)], MODES[r.nextInt(2)]);
				
				// An IndexOutOfBoundsException will occur if the word runs past the edge of the board, so we
				// have to catch that
				try {
					success = addWord(coords, line, word);
				} catch (IndexOutOfBoundsException e) {
					;
				}
				
				// We need a timeout to avoid an infinite loop in case the word combination doesn't fit anywhere
				if (!success && attempts == timeout) {
					System.out.println(
						String.format("Failed to add %s; timed out on too many failed attempts.", word)
					);
					failedWordCount++;
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
		 grid = new ArrayList<ArrayList<Cell>>();
		 ArrayList<Cell> item;
		 for (int i = 0; i < size; ++i) {
			 item = new ArrayList<Cell>();
			 for (int j = 0; j < size; ++j) {
				 item.add(new Cell(' '));
			 }
			 grid.add(item);
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
	public ArrayList<ArrayList<Cell>> getGrid() {
		return grid;
	}
}
