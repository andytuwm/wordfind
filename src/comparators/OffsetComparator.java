package comparators;

import wordfind.Entry;

public class OffsetComparator implements java.util.Comparator<Entry> {

	@Override
	public int compare(Entry e1, Entry e2) {
		if (e1.getOffset() > e2.getOffset())
			return 1;

		if (e2.getOffset() > e1.getOffset())
			return -1;

		return 0;
	}
}
