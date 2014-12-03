package wordfind;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
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
		// arbitrary root node '0'.
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
			// System.out.println(index);
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
	public void buildDictionary(String fileIn, boolean processFrench)
			throws IOException {
		size = 0;
		BufferedReader br = new BufferedReader(new FileReader(fileIn));
		String line;

		while ((line = br.readLine()) != null) {

			// if line contains accents, remove them. mainly for use with the
			// French dictionary.
			if (processFrench) {
				if (Pattern.matches(".*[éèàâùêôçïîû].*", line))
					line = removeAccents(line);
			}

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

	/**
	 * Checks if the dictionary contains a word with the next letter as the
	 * input char
	 * 
	 * @param c
	 * @return true if the dictionary contains the input char as a node, false
	 *         otherwise.
	 */
	public boolean hasChar(char c) {

		if (c == '*' && dict.get(26) != null)
			return true;

		int val = Character.toLowerCase(c) - 'a';
		if (val < 27 && val >= 0)
			return dict.get(val) != null;

		return false;
	}

	/**
	 * Retrieves the sub-dictionary of the input char.
	 * 
	 * @param c
	 * @return the sub-dictionary of the char.
	 */
	public Dictionary getSubTree(char c) {
		return dict.get(Character.toLowerCase(c) - 'a');
	}

	/**
	 * Get size of the dictionary
	 * 
	 * @return total number of words added to the dictionary
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Pass the current node and all its sub trees. Warning: Rep exposure
	 * intended only as an observer method. Do not mutate this, the dictionary
	 * tree may become broken.
	 * 
	 * Should only be used for detecting the stop node.
	 * 
	 * @return the current dictionary node
	 */
	public ArrayList<Dictionary> getList() {
		return dict;
	}

	/**
	 * Get the letter of current dictionary node.
	 * 
	 * @return the letter of current dictionary node.
	 */
	public char getLetter() {
		return letter;
	}

	/**
	 * Remove accents from letters that have them and convert them to regular
	 * english letters.
	 * 
	 * @param str
	 * @return str with accents removed.
	 */
	public String removeAccents(String str) {
		String nfdNormalizedString = Normalizer.normalize(str,
				Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}
}
