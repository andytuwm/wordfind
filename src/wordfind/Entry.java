package wordfind;

import java.util.ArrayList;
import java.util.List;

/**
 * An entry contains a valid English word, its path traversed on the board, and
 * various data computed from its path in relation to your base.
 * 
 * @author Andy
 *
 */
public class Entry {

	private String word;
	private List<Coordinates> path;
	private int maxVert, maxIncrease, offset, numRows, numCols;
	private boolean topBase = false;

	public Entry(String word, List<Coordinates> passedPath) {
		this.word = word;
		path = new ArrayList<Coordinates>(passedPath);
	}

	public Entry(String word, List<Coordinates> passedPath, boolean topCheck,
			int rows, int columns) {
		this.word = word;
		this.topBase = topCheck;
		path = new ArrayList<Coordinates>(passedPath);

		// Information is calculated depending on which side the base is on,
		// because row 1 would be different.
		if (topBase) {
			maxVert = path.get(path.size() - 1).getRow() + 1;
			for (Coordinates coords : path) {
				if (coords.getRow() + 1 > maxVert)
					maxVert = coords.getRow() + 1;
			}
			offset = path.get(0).getRow() + 1;
		} else {
			maxVert = rows - path.get(path.size() - 1).getRow();
			for (Coordinates coords : path) {
				if ((rows - coords.getRow()) > maxVert)
					maxVert = (rows - coords.getRow());
			}
			offset = rows - path.get(0).getRow();
		}
		maxIncrease = this.getMaxVert() - this.getOffset();
		numRows = rows;
		numCols = columns;
	}

	// Public getter methods, do not modify the object at the reference.
	public String getWord() {
		return word;
	}

	public int getOffset() {
		return offset;
	}

	public int getMaxVert() {
		return maxVert;
	}

	public int getMaxIncrease() {
		return maxIncrease;
	}

	public boolean isWin() {
		return maxVert == 13;
	}

	public List<Coordinates> getPath() {
		return path;
	}

	public Coordinates getFirstCoord() {
		int x = path.get(0).getColumn(); 
		int y = numRows - path.get(0).getRow() - 1;
		Coordinates cd = new Coordinates(x,y);
		return cd;
	}

}
