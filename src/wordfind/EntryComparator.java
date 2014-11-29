package wordfind;

public class EntryComparator implements java.util.Comparator<Entry>{

	//private int strlen;

	@Override
	public int compare(Entry e1, Entry e2) {
		
		return e1.getWord().compareTo(e2.getWord());
		
		/*int dist1 = Math.abs(e1.getWord().length() - strlen);
		int dist2 = Math.abs(e2.getWord().length() - strlen);
		
		return dist1 - dist2;*/
	}
	
}
