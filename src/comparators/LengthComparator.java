package comparators;

import wordfind.Entry;

public class LengthComparator implements java.util.Comparator<Entry> {

	@Override
    public int compare(Entry e1, Entry e2) {
    	if(e1.getWord().length() > e2.getWord().length())
            return -1;

        if(e2.getWord().length() > e1.getWord().length())
            return 1;

        return 0;
    }
}
