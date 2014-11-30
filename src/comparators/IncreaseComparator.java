package comparators;

import wordfind.Entry;

public class IncreaseComparator implements java.util.Comparator<Entry> {

	@Override
	public int compare(Entry e1, Entry e2) {
		if (e1.getMaxIncrease() > e2.getMaxIncrease())
			return -1;

		if (e2.getMaxIncrease() > e1.getMaxIncrease())
			return 1;

		return 0;
	}
}
