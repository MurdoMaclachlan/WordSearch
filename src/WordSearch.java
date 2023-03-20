import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WordSearch {
	
	Board board;
	int wordCount = 10;
	
	/**
	 * Program entry point.
	 * 
	 * @param args  Command-line arguments
	 */
	public static void main(String[] args) {
		WordSearch wordSearch = new WordSearch();
		wordSearch.run();
	}
	
	/**
	 * The primary program.
	 */
	public void run() {
		String[] words = fetchWords(wordCount);
		
		board = new Board(getMaxStringLength(words) + 5, 100);
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
			System.out.println("All words successfully added.");
		}
		
		printGrid(board.getGrid());
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
	private void printGrid(ArrayList<ArrayList<Character>> grid) {
		for (ArrayList<Character> row : grid) {
			for (Character cell : row) {
				System.out.print(cell);
				System.out.print(' ');
			}
			System.out.print('\n');
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
