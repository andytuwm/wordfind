package comparators;

import wordfind.Entry;

public class PositionComparator implements java.util.Comparator<Entry> {

	@Override
	public int compare(Entry e1, Entry e2) {
		if (e1.getMaxVert() > e2.getMaxVert())
			return -1;

		if (e2.getMaxVert() > e1.getMaxVert())
			return 1;

		return 0;
	}
}
