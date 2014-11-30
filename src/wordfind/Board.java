package wordfind;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import comparators.LengthComparator;

public class Board {

	private int rows, columns;
	private List<String> boardList;
	private boolean topBase = false;
	List<Coordinates> startPoints;
	List<Coordinates> startCopy;

	public Board() {
		boardList = new ArrayList<String>();
		startPoints = new ArrayList<Coordinates>();
	}

	/**
	 * Solve the board and find potential moves to make.
	 * 
	 * @param rootDict
	 *            pass the built dictionary tree here.
	 * @param fileIn
	 *            pass the text file of the board.
	 * @return a sorted list of valid words, by length.
	 * @throws IOException
	 */
	public List<Entry> solveBoard(Dictionary rootDict, String fileIn)
			throws IOException {
		List<Entry> entries = new ArrayList<Entry>();

		makeBoard(fileIn);

		// Check whether if your base is at the top or not by checking the
		// corner of the board's case.
		if (Character.isUpperCase(getChar(new Coordinates(0, 0))))
			topBase = true;

		// Get all start points (Upper Case Letters).
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Coordinates coord = new Coordinates(i, j);
				if (Character.isUpperCase(getChar(coord)))
					startPoints.add(coord);
			}
		}
		
		startCopy = new ArrayList<Coordinates>(startPoints);
		
		// Convert all letters in boardList to lower case for ease of processing
		// once start points have been found.
		ListIterator<String> iterator = boardList.listIterator();
		while (iterator.hasNext()) {
			iterator.set(iterator.next().toLowerCase());
		}

		for (Coordinates start : startPoints) {
			List<Coordinates> path = new ArrayList<Coordinates>();

			match(rootDict, start, path, entries, 0);
		}

		Collections.sort(entries, new LengthComparator());

		return entries;
	}

	/**
	 * Finds all available words from a given point.
	 * 
	 * @param dict
	 *            the dictionary node.
	 * @param pos
	 *            the start point on grid in the beginning; the point being
	 *            traversed during recursion.
	 * @param path
	 *            the path traversed. Starts with only starting point
	 *            coordinates; when entering recursion, pass the coordinate in
	 *            here appended.
	 * @param entries
	 *            modifies: this
	 */
	private void match(Dictionary dict, Coordinates pos,
			List<Coordinates> path, List<Entry> entries, int check) {

		// Check if there's a stop node within the current dictionary node. If
		// there is, the current traversed path down the tree is a valid word
		// and should be added to the list of valid words.
		if (dict.hasChar('*')) {
			String validWord = "";
			for (Coordinates coord : path) {
				validWord += getChar(coord);
			}
			// System.out.println(validWord);
			entries.add(new Entry(validWord, path, topBase, rows, columns));
		}

		// The following section adds all valid neighbours of the point pos to a
		// list.
		List<Coordinates> neighbours = new ArrayList<Coordinates>();

		// If input pos has these four sides on the board, add them to the list
		// of neighbours.
		// A check is added here because on first run, the only neighbour is the
		// actual coordinatae itself. During recursion however we would add all
		// adjacent points to begin with.
		if (check == 0)
			neighbours.add(pos);
		else {
			if (pos.getRow() > 0)
				neighbours.add(new Coordinates(pos.getRow() - 1, pos
						.getColumn()));
			if (pos.getColumn() > 0)
				neighbours.add(new Coordinates(pos.getRow(),
						pos.getColumn() - 1));
			if (pos.getRow() < rows - 1)
				neighbours.add(new Coordinates(pos.getRow() + 1, pos
						.getColumn()));
			if (pos.getColumn() < columns - 1)
				neighbours.add(new Coordinates(pos.getRow(),
						pos.getColumn() + 1));
			// If input pos has these four diagonal corners on the board, add
			// thme
			// to the list of neighbours.
			if (pos.getRow() > 0 && pos.getColumn() > 0)
				neighbours.add(new Coordinates(pos.getRow() - 1, pos
						.getColumn() - 1));
			if (pos.getRow() < (rows - 1) && pos.getColumn() < (columns - 1))
				neighbours.add(new Coordinates(pos.getRow() + 1, pos
						.getColumn() + 1));
			if (pos.getRow() < (rows - 1) && pos.getColumn() > 0)
				neighbours.add(new Coordinates(pos.getRow() + 1, pos
						.getColumn() - 1));
			if (pos.getRow() > 0 && pos.getColumn() < (columns - 1))
				neighbours.add(new Coordinates(pos.getRow() - 1, pos
						.getColumn() + 1));

			// Remove all coordinates in neighbours if the coordinate has
			// already
			// been traversed in path, or is not contained in the dictionary
			// tree.

			// Check in path.
			neighbours.removeAll(path);
			// Check in dictionary tree.
			Iterator<Coordinates> itr = neighbours.iterator();
			while (itr.hasNext()) {
				Coordinates coord = itr.next();
				if (!dict.hasChar(getChar(coord))) {
					itr.remove();
				}
			}
		}

		// use getSubTree to traverse into dictionary tree.
		while (neighbours.size() > 0) {
			// Pop the last element of neighbours for processing.
			Coordinates currentPoint = neighbours.remove(neighbours.size() - 1);
			// Add to path for passing into recursive method.
			path.add(currentPoint);
			match(dict.getSubTree(getChar(currentPoint)), currentPoint, path,
					entries, 1); // pass 1 to get adjacent points.
			// Once out of recursion for that point, remove the added point from
			// path.
			path.remove(path.size() - 1);
		}
	}

	/**
	 * Build the game board from the input file. First line must be the number
	 * of columns (horizontal length) and the number of rows (vertical length)
	 * in order, separated by a space. The rest of lines are the game board;
	 * each letter should not be separated by space, only rows are separated by
	 * a new line. Letters you have access to should be capitalized.
	 * 
	 * @param fileIn
	 * @throws IOException
	 */
	public void makeBoard(String fileIn) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileIn));
		String line = br.readLine();
		String[] xy = line.split("\\s+");
		columns = Integer.parseInt(xy[0]);
		rows = Integer.parseInt(xy[1]);
		int i = 0;

		while ((line = br.readLine()) != null) {
			if (line.length() != columns) {
				System.out.println("Invalid file column size.");
				return;
			}

			if (!Pattern.matches("[a-zA-Z]+", line))
				System.out.println("Invalid Character.");

			boardList.add(i, line);
			i++;
		}
		br.close();
	}

	/**
	 * Finds the character at the specified coordinate.
	 * 
	 * @param p
	 *            the input coordinate
	 * @return the char at the coordinate on the board grid; 0 if off grid.
	 */
	public char getChar(Coordinates p) {
		if (p.getRow() >= rows || p.getColumn() >= columns)
			return '*';
		char[] chars = boardList.get(p.getRow()).toCharArray();
		return chars[p.getColumn()];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public List<Coordinates> getStarts() {
		return startCopy;
	}

}
