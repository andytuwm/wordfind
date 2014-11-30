package wordfind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class WordTest {

	Dictionary dictionary;
	
	@Before
	public void setUp() {
		dictionary = new Dictionary();
	}
	
	//@Test
	public void testDictionary() throws IOException {
		dictionary.buildDictionary("files/dict.txt");
	}
	
	@Test
	public void testSolve() throws IOException {
		dictionary.buildDictionary("files/dict.txt");
		List<Entry> entries = new ArrayList<Entry>();
		Board brd = new Board();
		
		entries = brd.solveBoard(dictionary, "files/board.txt");
		
		System.out.println(entries);
	}
	
}
