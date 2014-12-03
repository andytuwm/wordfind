package wordfind;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import comparators.IncreaseComparator;
import comparators.LengthComparator;
import comparators.OffsetComparator;
import comparators.PositionComparator;

public class MainMenu {

	public static void main(String[] args) throws IOException {

		Scanner reader = new Scanner(System.in);
		String ans, prevAns = "";
		List<Entry> entries = new ArrayList<Entry>();
		Dictionary dict = new Dictionary();
		Board brd = new Board();
		int bottomLimit = 0;
		int showLimit = 20;
		boolean quit = false;
		boolean processFrench = false;
		String dictFile = "files/english_dict.txt";
		String boardFile = "files/board.txt";
		File file = new File(boardFile);

		System.out.println("Wordfind: by Andy Tu");
		System.out.println("A solver for the mobile game Wordbase.");
		System.out
				.println("===============================================================================");
		dispHelp();
		if (file.exists()) {
			System.out.println("Using English Dictionary.");
			System.out.println("Board initialized to " + boardFile
					+ "\nPlease enter a command:");
		} else {
			System.out.println("Board file not found. Exiting...");
			reader.close();
			return;
		}
		do {

			System.out.println("");
			dict.buildDictionary(dictFile, processFrench);
			ans = reader.next().toLowerCase();

			switch (ans) {

			case "help":
				dispHelp();
				break;

			case "solve":
				prevAns = ans;
				bottomLimit = 0;
				showLimit = 20;
				entries = brd.solveBoard(dict, boardFile,
						new LengthComparator());
				System.out.println("Showing " + bottomLimit + " to "
						+ showLimit + " of " + entries.size() + " words.");
				for (int i = bottomLimit; i < showLimit; i++) {
					System.out.println(entries.get(i).getWord()
							+ "\tIncrease: "
							+ entries.get(i).getMaxIncrease());
				}
				break;

			case "next":
				bottomLimit += 20;
				showLimit += 20;
				System.out.println("Showing " + bottomLimit + " to "
						+ showLimit + " of " + entries.size() + " words.");
				if (prevAns.equals("solve")) {
					for (int i = bottomLimit; i < showLimit; i++) {
						System.out.println(entries.get(i).getWord()
								+ "\tIncrease: "
								+ entries.get(i).getMaxIncrease());
					}
				} else if (prevAns.equals("analyzeposition")) {
					for (int i = bottomLimit; i < showLimit; i++) {
						if (entries.get(i).getWord().length() < 4)
							continue;
						System.out.println(entries.get(i).getWord() + "\t"
								+ "Position: " + entries.get(i).getMaxVert());
					}
				} else if (prevAns.equals("analyzebestreach")) {
					for (int i = bottomLimit; i < showLimit; i++) {
						System.out.println(entries.get(i).getWord() + "\t\t"
								+ "Increase: "
								+ entries.get(i).getMaxIncrease());
					}
				} else if (prevAns.equals("analyzewin")
						|| prevAns.equals("analyzeopponent")) {
					for (int i = bottomLimit; i < showLimit; i++) {
						System.out.println(entries.get(i).getWord()
								+ "\t\tStarting Row: "
								+ entries.get(i).getOffset());
					}
				} else {
					System.out
							.println("Please solve or analyze the board before sending this command.");
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
				if (prevAns.equals("solve")) {
					for (int i = bottomLimit; i < showLimit; i++) {
						System.out.println(entries.get(i).getWord()
								+ "\tIncrease: "
								+ entries.get(i).getMaxIncrease());
					}
				} else if (prevAns.equals("analyzeposition")) {
					for (int i = bottomLimit; i < showLimit; i++) {
						if (entries.get(i).getWord().length() < 4)
							continue;
						System.out.println(entries.get(i).getWord() + "\t"
								+ "Position: " + entries.get(i).getMaxVert());
					}
				} else if (prevAns.equals("analyzebestreach")) {
					for (int i = bottomLimit; i < showLimit; i++) {
						System.out.println(entries.get(i).getWord() + "\t\t"
								+ "Increase: "
								+ entries.get(i).getMaxIncrease());
					}
				} else if (prevAns.equals("analyzewin")
						|| prevAns.equals("analyzeopponent")) {
					for (int i = bottomLimit; i < showLimit; i++) {
						System.out.println(entries.get(i).getWord()
								+ "\t\tStarting Row: "
								+ entries.get(i).getOffset());
					}
				} else {
					System.out
							.println("Please solve or analyze the board before sending this command.");
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
				String s = boardFile;
				boardFile = "files/" + reader.next();
				if(!Pattern.matches(".+\\.txt$", boardFile))
					boardFile += ".txt";
				file = new File(boardFile);
				if (file.exists()) {
					System.out
							.println("Board set successfully to " + boardFile);
				} else {
					System.out.println("Board not found. Please try again.");
					boardFile = s;
				}
				break;

			case "setdictionary":
				System.out.println("Choose between English and French dictionaries.");
				System.out.println("Enter 1 for English or 2 for French:");
				String dictLang = reader.next();
				if ( dictLang.equals("1") || dictLang.equalsIgnoreCase("english")){
					processFrench = false;
					dictFile = "files/english_dict.txt";
					System.out.println("Language set to English.");
				} else if (dictLang.equals("2")|| dictLang.equalsIgnoreCase("french")){
					processFrench = true;
					dictFile = "files/french_dict.txt";
					System.out.println("Language set to French.");
				} else
					System.out.println("Invalid dictionary selection, please try again.");
				
				break;
				
			case "analyzeposition":
				prevAns = ans;
				bottomLimit = 0;
				showLimit = 20;
				entries = brd.solveBoard(dict, boardFile,
						new PositionComparator());
				System.out.println("Showing " + bottomLimit + " to "
						+ showLimit + " of " + entries.size() + " words.");
				for (int i = bottomLimit; i < showLimit; i++) {
					if (entries.get(i).getWord().length() < 4)
						continue;
					System.out.println(entries.get(i).getWord() + "\t"
							+ "Position: " + entries.get(i).getMaxVert());
				}
				break;

			case "analyzebestreach":
				prevAns = ans;
				bottomLimit = 0;
				showLimit = 20;
				entries = brd.solveBoard(dict, boardFile,
						new IncreaseComparator());
				System.out.println("Showing " + bottomLimit + " to "
						+ showLimit + " of " + entries.size() + " words.");
				for (int i = bottomLimit; i < showLimit; i++) {
					System.out.println(entries.get(i).getWord() + "\t\t"
							+ "Increase: "
							+ entries.get(i).getMaxIncrease());
				}
				break;

			case "analyzewin":
				prevAns = ans;
				bottomLimit = 0;
				showLimit = 20;
				entries = brd.solveEntireBoard(dict, boardFile, true);
				Board.sortEntries(entries, new OffsetComparator());

				Iterator<Entry> itr = entries.iterator();
				while (itr.hasNext()) {
					Entry ent = itr.next();
					if (!ent.isWin())
						itr.remove();
				}
				System.out.println("Showing " + bottomLimit + " to "
						+ showLimit + " of " + entries.size() + " words.");
				for (int i = bottomLimit; i < showLimit; i++) {
					System.out
							.println(entries.get(i).getWord()
									+ "\t\tStarting Row: "
									+ entries.get(i).getOffset());
				}
				break;

			case "analyzeopponent":
				prevAns = ans;
				bottomLimit = 0;
				showLimit = 20;
				entries = brd.solveEntireBoard(dict, boardFile, false);
				Board.sortEntries(entries, new OffsetComparator());

				Iterator<Entry> it = entries.iterator();
				while (it.hasNext()) {
					Entry ent = it.next();
					if (!ent.isWin())
						it.remove();
				}
				System.out.println("Showing " + bottomLimit + " to "
						+ showLimit + " of " + entries.size() + " words.");
				for (int i = bottomLimit; i < showLimit; i++) {
					System.out
							.println(entries.get(i).getWord()
									+ "\t\tStarting Row: "
									+ entries.get(i).getOffset());
				}
				brd.baseReverse();
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
						+ "solve - Show longest words you can play. Relative increase towards opponent\n\tbase is shown.\n"
						+ "next - display next 20 solutions.\n"
						+ "back - display last 20 solutions.\n"
						+ "analyzeBestReach - show best words that allow for maximum distance gain towards \n\t\t   opponent's base.\n"
						+ "analyzePosition - show words closest to opponent's base.\n"
						+ "analyzeWin - show words that allow  you to win - processing may take time.\n"
						+ "analyzeOpponent - show words the opponent can potentially win with and their\n\t\t  respective rows.\n"
						+ "setBoard - set board file to build from; txt file must be in the folder files.\n"
						+ "setDictionary - select either English dictionary or French dictionary. Default is English.\n"
						+ "quit - end program.\n===============================================================================\n"
						+ "Note: Rows are always counted from the base (Yours or the Opponent's).\n      Base row = 1\n"
						+ "===============================================================================");
	}
}
