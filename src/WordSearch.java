import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Main class to run the word search generation and solution program.
 * 
 * @author Murdo B. Maclachlan
 */
public class WordSearch {
	
	private boolean colourFoundWords = false;
	private int wordCount = 10;
	
	/**
	 * Program entry point.
	 * 
	 * @param args  Command-line arguments
	 */
	public static void main(String[] args) {
		WordSearch wordSearch = new WordSearch();
		wordSearch.processArguments(args);
		wordSearch.run();
	}
	
	/**
	 * The primary program.
	 * 
	 * Generates a word search using words from user input, prints it, then attempts to
	 * solve it and prints its solution, complete or partial, noting any words it failed
	 * to find in its generated Board.
	 */
	public void run() {
		String[] words = fetchWords(wordCount);
		
		Board board = new Board(getMaxStringLength(words) + 5, 100);
		initialiseBoard(board, words);
		
		Grid grid = board.getGrid();
		printGrid(grid);
		
		Solver solver = new Solver(grid);
		solveBoard(solver, words);
		
		System.out.println("\nThe solved board is:\n");
		printGrid(grid);
		
		System.out.println("\nPositions and directions of all words:\n");
		HashMap<String, Line> foundWords = solver.getFoundWords();
		for (String word : foundWords.keySet()) {
			Line wordLine = foundWords.get(word);
			System.out.println(
				String.format(
					"%s: %s, [ %s, %s ]",
					word,
					wordLine.getStartCoordinates().toString(),
					wordLine.getDirection(),
					wordLine.getMode()
				)
			);
		}
	}
	
	/**
	 * Initialises the board while printing relevant console output.
	 * 
	 * @param board  The board to initialise
	 * @param words  The words for the board
	 */
	private void initialiseBoard(Board board, String[] words) {
		System.out.println("Generating grid...\nAdding words...");
		board.fillWords(words);
		System.out.println("Filling empty cells...");
		board.fillRemainder();
		
		int failedWordCount = board.getFailedWordCount();
		if (failedWordCount > 0) {
			System.out.println(
				String.format("Failed to add %d word(s)", failedWordCount)
			);
		} else {
			System.out.println("All words successfully added.\n");
		}
	}
	
	/**
	 * Solves the board while printing relevant console output.
	 * 
	 * @param solver  The solver
	 * @param words   The words find
	 */
	private void solveBoard(Solver solver, String[] words) {
		solver.solve(words);
		ArrayList<String> failedWords = solver.getFailedWords();
		if (failedWords.size() > 0) {
			System.out.println("\nFailed the following words:");
			for (String word : failedWords)
				System.out.println(word);
		}
		if (colourFoundWords) solver.colourFoundWords();
	}
	
	/**
	 * Fetches a list of purely alphabetical words from user input.
	 * 
	 * @param count  The number of words to fetch
	 * 
	 * @return  The array of words
	 */
	private String[] fetchWords(int count) {
		Scanner scanner = new Scanner(System.in);
		String[] words = new String[count];
		for (int i = 0; i < count; ++i)
			words[i] = validateInput(i, scanner);
		return words;
	}
	
	/**
	 * Calculates the length of the longest string in an array of strings.
	 * 
	 * @param strings  The array of strings
	 * 
	 * @return  The length of the longest string in the array
	 */
	private int getMaxStringLength(String[] strings) {
		List<String> list = Arrays.asList(strings);
		return list.stream().map(String::length).max(Integer::compareTo).get();
	}
	
	/**
	 * Print a given grid of characters in its entirety.
	 * 
	 * @param grid  The grid to print
	 */
	private void printGrid(Grid grid) {
		for (ArrayList<Cell> row : grid) {
			for (Cell cell : row) {
				System.out.print(cell.toString());
				System.out.print(' ');
			}
			System.out.print('\n');
		}
	}
	
	/**
	 * Process any command-line arguments.
	 * 
	 * @param args  The list of command-line arguments
	 */
	private void processArguments(String[] args) {
		for (int i = 0; i < args.length; ++i) {
			String arg = args[i];
			switch (arg) {
				case "-c", "--colour" -> {
					colourFoundWords = true;
				}
				case "-w", "--word-count" -> {
					try {
						wordCount = Integer.parseInt(args[i+1]);
					} catch (NumberFormatException e) {
						System.out.println(arg + " was provided, but not succeeded by a number. Defaulting to word count of 10.");
					}
				}
				default -> {
					if (!(args[i-1].equals("-w") || args[i-1].equals("--word-count")))
							System.out.println("Unknown argument: " + arg);
				}
			}
		}
	}
	
	/**
	 * Receive and validate user input as an alphabetical string, and converts it to upper case.
	 * 
	 * @param number   The number of the string to validate
	 * @param scanner  An input scanner
	 * 
	 * @return  The validated string, converted to upper case
	 */
	private String validateInput(int number, Scanner scanner) {
		String word;
		
		System.out.print(String.format("Please enter a word number %d: ", number+1));
		word = scanner.nextLine();
		while (!isStringAlpha(word)) {
			System.out.print("Please ensure the word contains only alphabetical characters: ");
			word = scanner.nextLine();
		}
		
		// We'll convert the words to upper case to make the printed boards easier to read
		return word.toUpperCase();
	}
	
	/**
	 * Determine if a given string consists entirely of alphabetical characters.
	 * 
	 * @param string  The string to check
	 * 
	 * @return  Whether the string is purely alphabetical
	 */
	private boolean isStringAlpha(String string) {
		int length = string.length();
		for (int i = 0; i < length; ++i) {
			if (!Character.isLetter(string.charAt(i)))
				return false;
		}
		return length > 0;
	}
}
