package wordfind;

import java.util.ArrayList;
import java.util.List;

public class Entry {

	private String word;
	private List<Coordinates> path;
	private int maxVert, maxIncrease;
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

		if (topBase) {
			maxIncrease = path.get(path.size() - 1).getRow()
					- path.get(0).getRow();
			maxVert = path.get(path.size() - 1).getRow();
		} else {
			maxVert = rows - path.get(path.size() - 1).getRow();
			maxIncrease = 0 - (path.get(path.size() - 1).getRow() - path.get(0)
					.getRow());
		}
	}

	public String getWord() {
		return word;
	}

	public int getMaxVert() {
		return maxVert;
	}
	
	public int getMaxIncrease() {
		return maxIncrease;
	}
	
	public List<Coordinates> getPath(){
		return path;
	}

}
