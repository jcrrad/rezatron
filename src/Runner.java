import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Runner {
	public static void main(String[] args) {
		// UCI();
		PerftSuiteTwo ps = new PerftSuiteTwo();
		Perft p = new Perft();
		System.out.println(p.perft(1));
		System.out.println(p.perft(2));
		System.out.println(p.perft(3));
		System.out.println(p.perft(4));
		System.out.println(p.perft(5));
		System.out.println(p.perft(6));
		System.out.println(p.perft(7));
		System.out.println(p.perft(8));
		System.out.println(p.perft(9));
		System.out.println(p.perft(10));
		System.out.println(p.perft(11));
		System.out.println(p.perft(12));
		// Board b = new Board();
		// System.out.println(b.getValue());
	}

	/*public static void UCI() {
		Node n = new Node();
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("id name Rezatron");
		System.out.println("id author Michael Radaszkiewicz");
		try {
			input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("uciOK");
		try {
			input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Board b = new Board();
		System.out.println("start");
		System.out.println("readyok");
		String move = null;
		try {
			move = input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while (move != null) {
				move = input.readLine();
				System.out.println("Just took in: " + move);
				if (move.contains("position")) {
					String[] moves = move.split(" ");
					if (moves[1].equals("startpos"))
						b = new Board();
					else if (moves[1].equals("fen")) {

						String fen = moves[2];
						for (int i = 3; i < moves.length; i++) {
							if (!moves[i].equals("moves"))
								fen += " " + moves[i];
							else
								break;
						}
						System.out.println(fen);
						b = new Board(fen);
						System.out.println(b);
					}
					if (move.contains("moves") || move.contains("move")) {
						int start = 0;
						for (int i = 0; i < moves.length; i++) {
							if (moves[i].equals("moves")) {
								start = i;
								break;
							}
						}
						for (int i = start + 1; i < moves.length; i++) {
							String inputMove = moves[i];
							System.out.println("We are going to try to move: "
									+ inputMove);
							b.move(understandMove(inputMove));
						}
					}
				}
				if (move.contains("go")) {
					System.out.println("Rezatron will now go and get a move");
					System.out.println(b + b.getFEN());
					n = (Node) new Node(b.getFEN(), 3);
					n.start();
					while (n.isAlive()) {
						System.out.println("We are wating");
					}
					String moveToMake = n.getBestMove();
					b.move(moveToMake);
					System.out.println("bestmove " + b.UCI(moveToMake));
				}
				if (move.contains("ucinewgame")) {
					b = new Board();
				}
			}
		} catch (Exception e) {
			System.out.println("Error from " + move + "\n\n" + e);
		}
	}

	public static String understandMove(String move) {

		if (move.equalsIgnoreCase("e1g1")) {
			return ("04061");
		} else if (move.equalsIgnoreCase("e1c1")) {
			return ("04021");
		} else if (move.equalsIgnoreCase("e8g8")) {
			return ("60621");
		} else if (move.equalsIgnoreCase("e8c8")) {
			return ("60581");
		}
		String[] letterSquares = { "a1", "b1", "c1", "d1", "e1", "f1", "g1",
				"h1", "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2", "a3",
				"b3", "c3", "d3", "e3", "f3", "g3", "h3", "a4", "b4", "c4",
				"d4", "e4", "f4", "g4", "h4", "a5", "b5", "c5", "d5", "e5",
				"f5", "g5", "h5", "a6", "b6", "c6", "d6", "e6", "f6", "g6",
				"h6", "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", "a8",
				"b8", "c8", "d8", "e8", "f8", "g8", "h8" };
		String from = move.substring(0, 2);
		String to = move.substring(2, 4);
		String promote = "";
		try {
			promote = move.substring(4, 5);
		} catch (StringIndexOutOfBoundsException e) {
		}
		String toS = "";
		String fromS = "";
		for (int i = 0; i < letterSquares.length; i++) {
			if (to.equals(letterSquares[i]))
				toS = (String.format("%02d", i));
			if (from.equals(letterSquares[i]))
				fromS = (String.format("%02d", i));
		}
		String ans = fromS + toS;
		if (promote.equalsIgnoreCase("Q"))
			ans += "2";
		if (promote.equalsIgnoreCase("N"))
			ans += "3";
		if (promote.equalsIgnoreCase("R"))
			ans += "4";
		if (promote.equalsIgnoreCase("B"))
			ans += "5";
		if (promote.equalsIgnoreCase(""))
			ans += "0";
		return ans;
	}*/
}