package wordfind;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * The Dictionary object is a tree composed of letters of valid english words as
 * nodes.
 * 
 * @author Andy Tu
 *
 */
public class Dictionary {

	private ArrayList<Dictionary> dict;
	private char letter;
	private static int size;

	public Dictionary() {
		// root node '0'.
		letter = '0';
		// 27 indexes for all alphabets + a stop node character.
		dict = new ArrayList<Dictionary>(Arrays.asList(new Dictionary[27]));
	}

	public Dictionary(char letter) {
		this.letter = Character.toLowerCase(letter);
		// 27 indexes for all alphabets + a stop node character.
		dict = new ArrayList<Dictionary>(Arrays.asList(new Dictionary[27]));
	}

	/**
	 * Add word to the dictionary tree with each letter as a node.
	 * 
	 * @param word
	 * @param iteration
	 */
	public void addWord(String word, int iteration) {

		if (iteration < word.length()) {
			int index = word.charAt(iteration) - 'a';
			System.out.println(index);
			if (dict.get(index) == null)
				dict.set(index, new Dictionary(word.charAt(iteration)));

			Dictionary node = dict.get(index);
			node.addWord(word, iteration + 1);

		} else {
			// Last index of ArrayList is reserved for the stop node character.
			// '*' is used as the stop node character.
			dict.set(26, new Dictionary('*'));
		}
	}

	/**
	 * Determines whether the specified word is a legitimate word.
	 * 
	 * @param word
	 * @param iteration
	 *            requires: when calling at first, put 0 here.
	 * @return true if word is in the dictionary tree, and false otherwise.
	 */
	public boolean hasWord(String word, int iteration) {

		if (iteration < word.length()) {

			Dictionary node = dict.get(word.charAt(iteration) - 'a');

			if (node == null)
				return false;

			return node.hasWord(word, iteration + 1);
		}

		return dict.get(26) != null;
	}

	/**
	 * Builds a Dictionary tree from an input dictionary text file.
	 * 
	 * @param fileIn
	 *            text file to be read.
	 * @throws IOException
	 */
	public void buildDictionary(String fileIn) throws IOException {
		size = 0;
		BufferedReader br = new BufferedReader(new FileReader(fileIn));
		String line;

		while ((line = br.readLine()) != null) {

			System.out.println(line);
			// Checks if word is allowed to be added to the Dictionary through
			// the following criteria:
			// 1) Does not contain upper case letters; proper nouns not allowed.
			// 2) Is not empty.
			// 3) Contains only alphabet letters; no apostrophes etc.
			if (!line.equals(line.toLowerCase()) || line.length() == 0
					|| !Pattern.matches("[a-z]+", line))
				continue;

			// If word passes criteria, add to dictionary.
			this.addWord(line, 0);
			size++; // keep track of size of dictionary.
		}
		br.close();
	}

	public boolean hasChar(char c) {
		return dict.get(Character.toLowerCase(c) - 'a') != null;
	}

	public Dictionary getSubTree(char c) {
		return dict.get(Character.toLowerCase(c) - 'a');
	}

	public int getSize() {
		return size;
	}
}