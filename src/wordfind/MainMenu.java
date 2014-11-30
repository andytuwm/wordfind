package wordfind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

	public static void main(String[] args) throws IOException {

		Scanner reader = new Scanner(System.in);
		String ans;
		List<Entry> entries = new ArrayList<Entry>();
		Dictionary dict = new Dictionary();
		Board brd = new Board();
		int bottomLimit = 0;
		int showLimit = 20;
		boolean quit = false;
		String boardFile = "files/board.txt";

		System.out.println("Wordfind: by Andy Tu");
		System.out.println("A solver for the mobile game Wordbase.");
		dispHelp();
		do {
			ans = reader.next();

			switch (ans.toLowerCase()) {

			case "help":
				dispHelp();
				break;

			case "solve":
				bottomLimit = 0;
				showLimit = 20;
				dict.buildDictionary("files/dict.txt");
				entries = brd.solveBoard(dict, boardFile);
				System.out.println("Showing " + bottomLimit + " to "
						+ showLimit + " of " + entries.size() + " words.");
				for (int i = bottomLimit; i < showLimit; i++) {
					System.out.println(entries.get(i).getWord());
				}
				break;

			/*case "solvefrom":
				System.out.println("Enter x coordinate (from 0 to "
						+ (brd.getColumns() - 1) + "):");
				int x = reader.nextInt();
				System.out.println("Enter y coordinate(from 0 to "
						+ (brd.getRows() - 1) + "):");
				int y = reader.nextInt();
				Coordinates c = new Coordinates(brd.getRows() - y, x);
				if (x < 0 || x > brd.getColumns() - 1 || y < 0
						|| y > brd.getRows() - 1) {
					System.out.println("Invalid Coordinates.");
					break;
				}
				for (Coordinates coord : brd.getStarts()) {
					boolean check = false;
					if (!coord.equals(c)) {
						check = true;
					}
					if (check) {
						System.out.println("Invalid Coordinates.");
						break;
					}
				}
				int occurences = 0;
				for (int count = 0; occurences < 20; count++) {
					if (entries.get(count).getPath().get(0).equals(c)) {
						System.out.println(entries.get(count).getWord());
						occurences++;
					}
				}
				break;*/

			case "next":
				bottomLimit += 20;
				showLimit += 20;
				System.out.println("Showing " + bottomLimit + " to "
						+ showLimit + " of " + entries.size() + " words.");
				for (int i = bottomLimit; i < showLimit; i++) {
					System.out.println(entries.get(i).getWord());
				}
				break;

			case "back":
				if (!(bottomLimit >= 20 && showLimit >= 20)) {
					System.out.println("Already showing first 20 words.");
					continue;
				}
				bottomLimit -= 20;
				showLimit -= 20;
				System.out.println("Showing " + bottomLimit + " to "
						+ showLimit + " of " + entries.size() + " words.");
				for (int i = bottomLimit; i < showLimit; i++) {
					System.out.println(entries.get(i).getWord());
				}
				break;

			case "quit":
				quit = true;
				break;
			case "setboard":
				System.out
						.println("Enter Board File Name (should be a .txt file inside the folders \"files\"):");
				System.out
						.println("Include .txt file extension and type solve again after setting board.");
				boardFile = "files/" + reader.next();
				break;

			case "analyzePosition":
				break;
			default:
				System.out.println("Invalid Command, try again.");
				break;
			}
		} while (!quit);
		reader.close();
	}

	private static void dispHelp() {
		System.out.println("\nCommands Available:");
		System.out
				.println("help - displays this help menu.\n"
						+ "solve - rebuild board from file and solve again.\n"
						+ "solveFrom - show solutions from the given coordinate. Shows only 20 entries.\n"
						+ "next - display next 20 solutions.\n"
						+ "back - display last 20 solutions.\n"
						+ "analyzeBestReach - show best words that allow for maximum distance gain towards \n\t\t   opponent's base.\n"
						+ "analyzePosition - show words closest to opponent's base.\n"
						+ "analyzeOpponent - show the most dangerous tiles in opponent's possession with \n\t\t  their longest words.\n"
						+ "setBoard - set board file to build from; txt file must be in the folder files.\n"
						+ "quit - end program.");
	}

}
