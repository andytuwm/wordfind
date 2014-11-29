package wordfind;

/**
 * Quick and dirty coordinates class that is determined in terms of rows and
 * columns.
 * 
 * @author Andy
 *
 */
public class Coordinates {
	private int x;
	private int y;

	protected Coordinates(int row, int column) {
		x = row;
		y = column;
	}

	protected int getRow() {
		return x;
	}

	protected int getColumn() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}