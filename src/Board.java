import java.util.ArrayList;
import java.util.Random;

public class Board {
	
	int failedWordCount;
	int size;
	int timeout;
	
	String[] directions = {
		"diagonal_down",
		"diagonal_up",
		"horizontal",
		"vertical"
	};
	String[] modes = {
		"backward",
		"forward"
	};
	
	ArrayList<ArrayList<Character>> grid;
	
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
	public boolean addWord(Coordinate coords, String[] line, String word) {
		Coordinate startCoords = coords.clone();
		
		for (char c : word.toCharArray()) {
			char gridChar = grid.get(coords.getY()).get(coords.getY());
			
			// We can only insert a character into any cell that is empty or already contains that
			// letter; otherwise we'd words we already added.
			if (gridChar == c || gridChar == ' ') {
				coords = modifyCoordinates(coords, line);
			} else {
				return false;
			}
		}
		
		// Insert the word into the board, using the starting coordinates and moving down the
		// same line, this time actually setting the characters
		coords = startCoords;
		for (char c : word.toCharArray()) {
			grid.get(coords.getY()).set(coords.getX(), c);
			coords = modifyCoordinates(coords, line);
		}
		
		return true;
	}
	
	/**
	 * Fills all empty cells on the grid with random alphabetical characters.
	 */
	public void fillRemainder() {
		Random r = new Random();
		for (ArrayList<Character> row : grid) {
			for (int i = 0; i < row.size(); ++i) {
				if (row.get(i) == ' ') {
					row.set(i, Character.toUpperCase(((char)('a' + r.nextInt(26)))));
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
				String[] line = { directions[r.nextInt(4)], modes[r.nextInt(2)] };
				
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
		 grid = new ArrayList<ArrayList<Character>>();
		 ArrayList<Character> item;
		 for (int i = 0; i < size; ++i) {
			 item = new ArrayList<Character>();
			 for (int j = 0; j < size; ++j) {
				 item.add(' ');
			 }
			 grid.add(item);
		 }
	}
	
	/**
	 * Increments or decrements given X and Y coordinates as appropriate, based on a given direction.
	 * 
	 * @param coords  The coordinates to modify
	 * @param line    The direction to modify them in
	 * 
	 * @return  The modified coordinates
	 */
	private Coordinate modifyCoordinates(Coordinate coords, String[] line) {
		// Determine whether to increment or decrement coordinates
		int modifier = line[1].equals("forward") ? 1 : -1;
		
		int x = coords.getX();
		int y = coords.getY();
		
		// Note that, for the purposes of the grid, the origin point, 0, exists at the top-left corner.  Thus, for each
		// step DOWN, the Y coordinate increments, as opposed to the more standard UP correlation in the Cartesian
		// coordinate system.
		switch (line[0]) {
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
				System.out.println("WARNING: Board.modifyCoordinates() does not know how to handle direction: " + line[0]);
				System.out.println("WARNING: Skipping the above attempt to modify coordinates. May cause further errors.");
			}
		}
		
		coords.setCoordinates(x, y);
		return coords;
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
	public ArrayList<ArrayList<Character>> getGrid() {
		return grid;
	}
}
