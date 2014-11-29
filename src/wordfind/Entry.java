package wordfind;

import java.util.ArrayList;
import java.util.List;

public class Entry {

	private String word;
	private List<Coordinates> path;
	private int maxVert, minVert;
	
	public Entry ( String word, List <Coordinates> passedPath){
		this.word = word;
		path = new ArrayList<Coordinates>(passedPath);
		
		//maxVert = path.get(0).getRow() + ;
	}

	public String getWord(){
		return word;
	}
	
}
