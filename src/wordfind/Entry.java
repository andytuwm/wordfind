package wordfind;

import java.util.List;

public class Entry<A, B> {

	/**
	 * Quick and dirty coordinates class.
	 * 
	 * @author Andy
	 *
	 */
	private class coordinates {
		int x;
		int y;
		protected coordinates(int row, int column) {
			x = row;
			y = column;
		}
		protected int getRow() {
			return x;
		}
		protected int getColumn() {
			return y;
		}
	}

	private String word;
	private List<coordinates> path;
	private int maxVert, minVert;
	
	public Entry ( String word, List <coordinates> passedPath){
		this.word = word;
		path.addAll(passedPath);
		
		//maxVert = path.get(0).getRow() + ;
	}

}
