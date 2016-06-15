import java.util.*;

/**
 * V0.01.01 - Board evaulation based on material and 1 cp is given for each avaialble move.
 * V0.01.02 - 6/14/16 Alpha-Beta fixed.
 * V0.01.03 - 6/14/16 Moves are now sorted before going through Alpha-Beta.
 * 
 * @author Rez
 * 
 */

public class UCI {
	static String ENGINENAME = "Rezatron v0.01.05";
	static String ENGINEAUTHOR = "Michael Radaszkiewicz";
	static Board b;

	public static void uciCommunication() {
		Scanner input = new Scanner(System.in);
		while (true) {
			String inputString = input.nextLine();
			if ("uci".equals(inputString)) {
				inputUCI();
			} else if (inputString.startsWith("setoption")) {
				inputSetOption(inputString);
			} else if ("isready".equals(inputString)) {
				inputIsReady();
			} else if ("ucinewgame".equals(inputString)) {
				inputUCINewGame();
			} else if (inputString.startsWith("position")) {
				inputPosition(inputString);
			} else if (inputString.startsWith("go")) {
				inputGo();
			} else if (inputString.equals("quit")) {
				inputQuit();
			}
		}
	}

	public static void inputUCI() {
		System.out.println("id name " + ENGINENAME);
		System.out.println("id author Michael Radaszkiewicz");
		// options go here
		System.out.println("uciok");
	}

	public static void inputSetOption(String inputString) {
		// set options
	}

	public static void inputIsReady() {
		System.out.println("readyok");
	}

	public static void inputUCINewGame() {
		// add code here
	}

	public static void inputPosition(String input) {
		input = input.substring(9).concat(" ");
		if (input.contains("startpos ")) {
			input = input.substring(9);
			b = new Board(
					"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		} else if (input.contains("fen")) {
			input = input.substring(4);
			b = new Board(input);
		}
		if (input.contains("moves")) {
			String[] moves = input.split(" ");
			input = input.substring(input.indexOf("moves") + 6);
			int start = 0;
			for (int i = 0; i < moves.length; i++) {
				if (moves[i].equals("moves")) {
					start = i;
					break;
				}
			}
			for (int i = start + 1; i < moves.length; i++) {
				String inputMove = moves[i];
				b.move(b.translateToInt(inputMove));
			}

		}
	}

	public static void inputGo() {
		MoveAlgorithm n = new MoveAlgorithm();
		n = new MoveAlgorithm(b, 3);
		System.out.println(n.iterativeDeepening(5));
	}

	public static void inputQuit() {
		System.exit(0);
	}

}