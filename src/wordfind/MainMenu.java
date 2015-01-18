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
		int tempLimit = 20;
		int rows = 13; // unfortunately this is temporarily hard-coded.
		int columns = 10;
		boolean quit = false;
		boolean processFrench = false;
		boolean atEnd = false;
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
							+ "\tIncrease: " + entries.get(i).getMaxIncrease());
				}
				break;

			case "next":
				if ((showLimit + 20 > entries.size() && atEnd)
						|| bottomLimit + 20 > entries.size()) {
					System.out.println("Already at end of list.");
					break;
				}
				bottomLimit += 20;
				tempLimit = showLimit;
				showLimit += 20;
				if (showLimit > entries.size()) {
					showLimit = entries.size();
					atEnd = true;
				}

				System.out.println("Showing " + bottomLimit + " to "
						+ showLimit + " of " + entries.size() + " words.");

				if (prevAns.equals("solve")
						|| prevAns.equals("analyzebestreach")
						|| prevAns.equals("analyzepoint")
						|| prevAns.equals("analyzecutoff")) {
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
				if (!atEnd)
					showLimit -= 20;
				else {
					showLimit = tempLimit;
					atEnd = false;
				}
				System.out.println("Showing " + bottomLimit + " to "
						+ showLimit + " of " + entries.size() + " words.");

				if (prevAns.equals("solve") || prevAns.equals("analyzecutoff")
						|| prevAns.equals("analyzepoint")
						|| prevAns.equals("analyzebestreach")) {
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
				if (!Pattern.matches(".+\\.txt$", boardFile))
					boardFile += ".txt";
				file = new File(boardFile);
				if (file.exists()) {
					System.out
							.println("Board set successfully to " + boardFile);
					brd.makeBoard(boardFile);
					brd.checkBaseSide();
				} else {
					System.out.println("Board not found. Please try again.");
					boardFile = s;
				}
				break;

			case "setdictionary":
				System.out
						.println("Choose between English and French dictionaries.");
				System.out.println("Enter 1 for English or 2 for French:");
				String dictLang = reader.next();
				if (dictLang.equals("1")
						|| dictLang.equalsIgnoreCase("english")) {
					processFrench = false;
					dictFile = "files/english_dict.txt";
					dict = new Dictionary();
					System.out.println("Language set to English.");
				} else if (dictLang.equals("2")
						|| dictLang.equalsIgnoreCase("french")) {
					processFrench = true;
					dictFile = "files/french_dict.txt";
					dict = new Dictionary();
					System.out.println("Language set to French.");
				} else
					System.out
							.println("Invalid dictionary selection, please try again.");

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
							+ "Increase: " + entries.get(i).getMaxIncrease());
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

			case "analyzecutoff":
				prevAns = ans;
				System.out
						.println("Enter X-Y Coordinates of desired point on board, separated by a space.");
				System.out
						.println("For multiple points, separate each pair of coordinates by a comma.");
				reader.nextLine(); // Eat floating \n char

				String pts = reader.nextLine();
				if (Pattern.matches(" *([0-9]+ +[0-9]+){1} *", pts)) {

					// One coordinate case
					pts = pts.trim(); // Remove trailing/leading spaces.
					String[] points = pts.split("[\\s]+");

					int x = Integer.parseInt(points[0]);
					int y = (rows - 1) - Integer.parseInt(points[1]);
					// Check if entered coordinates are within bounds.
					if (y > rows - 1 || x > columns - 1) {
						System.out.println("Coordinates outside of board.");
						break;
					}
					// Convert input to coordinates. Reversed because the way
					// coordinates are counted in the method solveBoard and
					// solveEntireBoard are reversed.
					Coordinates cd = new Coordinates(y, x);

					entries = brd.solveBoard(dict, boardFile,
							new LengthComparator());

					// Keep only entries where specified coordinate is in path.
					Iterator<Entry> iter = entries.iterator();
					while (iter.hasNext()) {
						Entry ent = iter.next();
						if (!ent.containsCoord(cd))
							iter.remove();
					}

					bottomLimit = 0;
					if (entries.size() <= 20)
						showLimit = entries.size();
					// set limit here because there might not be at least 20
					// possible words from specified point.
					else
						showLimit = 20;
					System.out.println("");
					System.out.println("Showing words containing "
							+ Character.toUpperCase(brd.getChar(cd)));
					System.out.println("Showing " + bottomLimit + " to "
							+ showLimit + " of " + entries.size() + " words.");
					for (int i = bottomLimit; i < showLimit; i++) {
						System.out.println(entries.get(i).getWord()
								+ "\tIncrease: "
								+ entries.get(i).getMaxIncrease());
					}

				} else if (Pattern
						.matches(
								" *([0-9]+ +[0-9]+){1}( *,{1} *([0-9]+ +[0-9]+){1} *)*",
								pts)) {

					List<Coordinates> list = new ArrayList<>();
					// Multi-coordinate case
					String[] coordList = pts.split(" *,{1} *");
					int numPoints = coordList.length;

					// Process first and last coordinates in case there are
					// extra spaces.
					coordList[0] = coordList[0].trim();
					coordList[numPoints - 1] = coordList[numPoints - 1].trim();

					for (int i = 0; i < numPoints; i++) {

						String[] point = coordList[i].split("[\\s]+");

						int x = Integer.parseInt(point[0]);
						int y = (rows - 1) - Integer.parseInt(point[1]);
						// Check if entered coordinates are within bounds.
						if (y > rows - 1 || x > columns - 1) {
							System.out.println("Coordinates outside of board.");
							break;
						}
						// Convert input to coordinates. Reversed because the
						// way
						// coordinates are counted in the method solveBoard and
						// solveEntireBoard are reversed.
						Coordinates cd = new Coordinates(y, x);
						list.add(cd);
					}

					entries = brd.solveBoard(dict, boardFile,
							new LengthComparator());

					// Keep only entries where specified coordinate is in path.
					Iterator<Entry> iter = entries.iterator();
					while (iter.hasNext()) {
						Entry ent = iter.next();
						if (!ent.containsCoords(list))
							iter.remove();
					}

					bottomLimit = 0;
					if (entries.size() <= 20)
						showLimit = entries.size();
					// set limit here because there might not be at least 20
					// possible words from specified point.
					else
						showLimit = 20;
					System.out.print("\nShowing words containing: ");
					for (int i = 0; i < list.size(); i++) {
						if (i != 0)
							System.out.print(",");
						System.out.print(Character.toUpperCase(brd.getChar(list
								.get(i))));
					}
					System.out.println("\nShowing " + bottomLimit + " to "
							+ showLimit + " of " + entries.size() + " words.");
					for (int i = bottomLimit; i < showLimit; i++) {
						System.out.println(entries.get(i).getWord()
								+ "\tIncrease: "
								+ entries.get(i).getMaxIncrease());
					}

				} else {
					System.out.println("Invalid Coordinates.");
				}

				break;

			case "analyzepoint":
				prevAns = ans;
				System.out
						.println("Enter X-Y Coordinates of point on board, separated by a space:");
				reader.nextLine(); // Eat floating \n character with arbitrary
									// nextLine call.
				String pt = reader.nextLine();
				if (Pattern.matches("([0-9 ]*[a-zA-Z]+[0-9 ]*)*", pt)
				// handles anything with alphabets
						|| Pattern.matches(" *(\n)*", pt)
						// handles empty inputs
						|| Pattern.matches(" *[0-9]+ *", pt)
				/* handles singular inputs */) {
					System.out.println("Invalid Coordinates.");
				} else {
					// Parse and split the input
					String[] points = pt.split("[\\s]+");
					int x = Integer.parseInt(points[0]);
					int y = Integer.parseInt(points[1]);
					// Check if entered coordinates are within bounds.
					if (y > rows - 1 || x > columns - 1) {
						System.out.println("Coordinates outside of board.");
						break;
					}
					// convert input to coordinates.
					Coordinates cd = new Coordinates(x, y);

					entries = brd.solveEntireBoard(dict, boardFile, true);
					Board.sortEntries(entries, new LengthComparator());

					Iterator<Entry> iter = entries.iterator();
					while (iter.hasNext()) {
						Entry ent = iter.next();
						if (!ent.getFirstCoord().equals(cd))
							iter.remove();
					}

					bottomLimit = 0;
					if (entries.size() <= 20)
						showLimit = entries.size();
					// set limit here because there might not be at least 20
					// possible words from specified point.
					else
						showLimit = 20;
					// Grab letter from arbitrary first entry since all entries
					// should have same starting point now.
					System.out.println("");
					System.out.println("Analyzing from the letter "
							+ Character.toUpperCase(brd.getChar(entries.get(0)
									.getPath().get(0))));
					System.out.println("Showing " + bottomLimit + " to "
							+ showLimit + " of " + entries.size() + " words.");
					for (int i = bottomLimit; i < showLimit; i++) {
						System.out.println(entries.get(i).getWord()
								+ "\tIncrease: "
								+ entries.get(i).getMaxIncrease());
					}
				}
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
						+ "analyzePoint - show longest words from the letter specified by input coordinates.\n\t       point (0,0) is always bottom left.\n"
						+ "analyzeOpponent - show words the opponent can potentially win with and their\n\t\t  respective rows.\n"
						+ "analyzeCutoff - show words that traverse through the specified point.\n"
						+ "setBoard - set board file to build from; txt file must be in the folder files.\n"
						+ "setDictionary - select either English dictionary or French dictionary. Default is English.\n"
						+ "quit - end program.\n===============================================================================\n"
						+ "Note: Rows are always counted from the base (Yours or the Opponent's).\n      Base row = 1\n"
						+ "===============================================================================");
	}
}
