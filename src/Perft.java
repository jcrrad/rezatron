import java.text.DecimalFormat;
import java.util.ArrayList;

public class Perft {
	private Board board;

	public Perft(String fen) {
		board = new Board(fen);
	}

	public Perft() {
		board = new Board();
	}

	public long perft(int depth) {
		ArrayList<Integer> moveList = board.generateMovesNeo(true);
		if (depth == 1)
			return moveList.size();
		long nodes = 0;
		if (!moveList.equals("")) {
			for (int i = 0; i < moveList.size(); i++) {
				board.move(moveList.get(i));
				nodes += diveIn(depth - 1);
				board.undo();
			}
		}
		return nodes;
	}

	public void perft(int start, int end) {
		String pattern = "0,000.000";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		for (int i = start; i < end; i++) {
			String line = "Perft(";
			if (i < 10)
				line += " " + i + ")=";
			Timer t = new Timer();
			t.start();
			long n = perft(i);
			t.stop();
			long ms = t.getLastLap();
			String nodeCount = "" + n;
			String timeString = "( " + decimalFormat.format(ms / 1000.0)
					+ " sec)";
			String output = line + nodeCount + timeString;
			int length = output.length();
			System.out.print(line);
			for (int x = 0; x < 40 - length; x++) {
				System.out.print(" ");
			}
			System.out.println(nodeCount + " " + timeString);
		}
	}

	public String divide(int depth) {
		ArrayList<Integer> moveList = board.generateMovesNeo(true);
		String[] ary1 = new String[moveList.size()];
		long[] count = new long[ary1.length];
		String answer = "";
		int temp = 0;
		if (!(moveList.size() == 0)) {
			for (int i = 0; i < moveList.size(); i++) {
				ary1[temp] = board.translate("" + moveList.get(i));
				board.move(moveList.get(i));
				count[temp] = diveIn(depth - 1);
				board.undo();
				temp++;
			}
			int sum = 0;
			for (int i = 0; i < ary1.length; i++) {
				answer += ary1[i] + ": " + count[i] + "\n";
				sum += count[i];
			}
			answer += "\n Moves: " + ary1.length + "\n Nodes: " + sum;
		}
		return answer;
	}

	/*
	 * private long diveIn(int depth) { String moveList =
	 * board.generateMoves(true); if (moveList.equals("") && depth != 0) {
	 * return 0; } else if (moveList.equals("") || depth == 1) { return
	 * moveList.length() / 5; } long nodes = 0; if (depth == 0) { return 1; }
	 * 
	 * if (!moveList.equals("")) { for (int i = 0; i < moveList.length(); i +=
	 * 5) { board.move(moveList.substring(i, i + 5)); nodes += diveIn(depth -
	 * 1); board.undo(); } } return nodes; }
	 */

	private long diveIn(int depth) {
		ArrayList<Integer> moveList = board.generateMovesNeo(true);
		if (moveList.size() == 0 && depth != 0) {
			return 0;
		} else if (moveList.size() == 0 || depth == 1) {
			return moveList.size();
		}
		long nodes = 0;
		if (depth == 0) {
			return 1;
		}

		if (!moveList.equals("")) {
			for (int i = 0; i < moveList.size(); i++) {
				board.move(moveList.get(i));
				nodes += diveIn(depth - 1);
				board.undo();
			}
		}
		return nodes;
	}
}
