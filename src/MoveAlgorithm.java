import java.text.DecimalFormat;
import java.util.ArrayList;

public class MoveAlgorithm {
	protected Board board;
	private int depthGoal;
	String bestLine;
	private String pattern;
	private String pattern2;
	private DecimalFormat decimalFormat;
	private DecimalFormat decimalFormat2;
	private int nodeCount;
	private boolean changed;
	Timer t;

	public MoveAlgorithm() {
		this(new Board(), 4);

	}

	public MoveAlgorithm(Board b, int depthGoal) {
		this.board = b;
		this.depthGoal = depthGoal;
		bestLine = "";
		pattern = "00,000.000";
		pattern2 = "00000";
		decimalFormat = new DecimalFormat(pattern);
		decimalFormat2 = new DecimalFormat(pattern2);
	}

	public String iterativeDeepening(int goal) {
		int[] pv = new int[0];
		for (int i = 1; i <= goal; i++) {
			t = new Timer();
			nodeCount = 0;
			Timer t = new Timer();
			t.start();
			int score = 0;
			if (board.isWhitesTurn()) {
				score = alphaBetaMax(Integer.MIN_VALUE, Integer.MAX_VALUE, i,
						"", goal, pv);
			} else {
				score = alphaBetaMin(Integer.MIN_VALUE, Integer.MAX_VALUE, i,
						"", goal, pv);
			}
			t.stop();
			long ms = t.getLastLap();

			if (ms > 1000) {
				int seconds = (int) (ms / 1000.0);
				int NPS = nodeCount / seconds;
				changed = true;
				System.out.println("info depth " + i + " time " + ms
						+ " nodes " + nodeCount + " NPS " + NPS + " pv"
						+ UCIBestLine(bestLine) + "score cp " + score + "");
			} else {
				System.out.println("info depth " + i + " time " + ms
						+ " nodes " + nodeCount + " pv" + UCIBestLine(bestLine)
						+ "score cp " + score + "");
			}
			String[] split = bestLine.split(" ");
			pv = new int[split.length - 1];
			for (int k = 1; k < split.length; k++) {
				pv[k - 1] = Integer.parseInt(split[k].substring(5, 10));
			}
		}
		System.out.println(bestLine);
		String ans = "bestmove " + bestLine.substring(0, 5);
		if (bestLine.length() > 13) {
			ans += " ponder " + bestLine.substring(13, 17);
		}
		return ans;
	}

	public String getMove(int depthleft) {
		depthGoal = depthleft;
		t = new Timer();
		nodeCount = 0;
		Timer t = new Timer();
		t.start();
		int score = alphaBeta(depthleft);
		t.stop();
		long ms = t.getLastLap();

		if (ms > 1000) {
			int seconds = (int) (ms / 1000.0);
			int NPS = nodeCount / seconds;
			changed = true;
			System.out.println("info depth " + depthGoal + " time " + ms
					+ " nodes " + nodeCount + " NPS " + NPS + " pv"
					+ UCIBestLine(bestLine) + "score cp " + score + "");
		} else {
			System.out.println("info depth " + depthGoal + " time " + ms
					+ " nodes " + nodeCount + " pv" + UCIBestLine(bestLine)
					+ "score cp " + score + "");
		}
		return "bestmove " + bestLine.substring(0, 5);
	}

	public int alphaBeta(int depthleft) {
		bestLine = "";
		int score = 0;
		if (board.isWhitesTurn()) {
			score = alphaBetaMax(Integer.MIN_VALUE, Integer.MAX_VALUE,
					depthleft, "");
		} else {
			score = alphaBetaMin(Integer.MIN_VALUE, Integer.MAX_VALUE,
					depthleft, "");
		}
		// info depth 5 score cp 6 hashfull 0 time 1 nodes 501 nps 513024 pv
		// b1c3 b8c6 g1f3 g8f6 d2d3 d7d6 c1e3 c8e6
		return score;
	}

	int alphaBetaMax(int alpha, int beta, int depthleft, String input,
			int goal, int[] pv) {
		if (goal == depthleft) {
			System.out.println("info depth " + goal);
		}
		nodeCount++;
		// ArrayList<Integer> moves = board.sortMoves(
		// board.generateMovesNeo(true), 1);
		ArrayList<Integer> moves = board.generateMovesNeo(true);
		if (pv.length > (goal - depthleft)) {
			int temp = moves.indexOf(pv[goal - depthleft]);
			if (temp > 0) {
				moves.remove(temp);
				moves.add(0, pv[goal - depthleft]);
			}
		}
		if (depthleft == 0 || moves.size() == 0) {
			if (changed) {
				// System.out.println("info pv" + UCIBestLine(bestLine));
				changed = false;
			}
			return board.getValue();
		}
		for (int i = 0; i < moves.size(); i++) {

			if (goal == depthleft) {
				System.out.println("info Currmove "
						+ UCIBestLine(board.translate(moves.get(i)))
						+ " currmovenumber " + (1 + i));
			}
			board.move(moves.get(i));
			String temp = input + " " + board.translate(moves.get(i));
			int score = alphaBetaMin(alpha, beta, depthleft - 1, temp, goal, pv);
			board.undo();
			if (score >= beta)
				return beta; // fail hard beta-cutoff
			if (score > alpha) {
				if (!bestLine.toLowerCase().contains(temp.toLowerCase())) {
					bestLine = temp;
					changed = true;
				}
				alpha = score; // alpha acts like max in MiniMax
			}
		}
		return alpha;
	}

	int alphaBetaMin(int alpha, int beta, int depthleft, String input,
			int goal, int[] pv) {
		nodeCount++;
		if (goal == depthleft) {
			System.out.println("info depth " + goal);
		}
		// ArrayList<Integer> moves = board.sortMoves(
		// board.generateMovesNeo(true), 1);
		ArrayList<Integer> moves = board.generateMovesNeo(true);
		if (pv.length > (goal - depthleft)) {
			int temp = moves.indexOf(pv[goal - depthleft]);
			if (temp > 0) {
				moves.remove(temp);
				moves.add(0, pv[goal - depthleft]);
			}
		}
		if (depthleft == 0 || moves.size() == 0) {
			if (changed) {
				// System.out.println("info nodes " + nodeCount + " pv"
				// + UCIBestLine(bestLine));
				changed = false;
			}
			return board.getValue();
		}
		for (int i = 0; i < moves.size(); i++) {
			if (goal == depthleft) {
				System.out.println("info Currmove "
						+ UCIBestLine(board.translate(moves.get(i)))
						+ " currmovenumber " + (1 + i));
			}
			board.move(moves.get(i));
			String temp = input + " " + board.translate(moves.get(i));
			int score = alphaBetaMax(alpha, beta, depthleft - 1, temp, goal, pv);
			board.undo();
			if (score <= alpha)
				return alpha; // fail hard alpha-cutoff
			if (score < beta) {
				if (!bestLine.toLowerCase().contains(temp.toLowerCase())) {
					changed = true;
					bestLine = temp;
				}
				beta = score; // beta acts like min in MiniMax
			}
		}
		return beta;
	}

	int alphaBetaMax(int alpha, int beta, int depthleft, String input) {
		if (depthGoal == depthleft) {
			System.out.println("info depth " + depthGoal);
		}
		nodeCount++;
		// ArrayList<Integer> moves = board.sortMoves(
		// board.generateMovesNeo(true), 1);
		ArrayList<Integer> moves = board.generateMovesNeo(true);
		if (depthleft == 0 || moves.size() == 0) {
			if (changed) {
				// System.out.println("info pv" + UCIBestLine(bestLine));
				changed = false;
			}
			return board.getValue();
		}
		for (int i = 0; i < moves.size(); i++) {

			if (depthGoal == depthleft) {
				System.out.println("info Currmove "
						+ UCIBestLine(board.translate(moves.get(i)))
						+ " currmovenumber " + (1 + i));
			}
			board.move(moves.get(i));
			String temp = input + " " + board.translate(moves.get(i));
			int score = alphaBetaMin(alpha, beta, depthleft - 1, temp);
			board.undo();
			if (score >= beta)
				return beta; // fail hard beta-cutoff
			if (score > alpha) {
				if (!bestLine.toLowerCase().contains(temp.toLowerCase())) {
					bestLine = temp;
					changed = true;
				}
				alpha = score; // alpha acts like max in MiniMax
			}
		}
		return alpha;
	}

	int alphaBetaMin(int alpha, int beta, int depthleft, String input) {
		nodeCount++;
		if (depthGoal == depthleft) {
			System.out.println("info depth " + depthGoal);
		}
		// ArrayList<Integer> moves = board.sortMoves(
		// board.generateMovesNeo(true), 1);
		ArrayList<Integer> moves = board.generateMovesNeo(true);
		if (depthleft == 0 || moves.size() == 0) {
			if (changed) {
				// System.out.println("info nodes " + nodeCount + " pv"
				// + UCIBestLine(bestLine));
				changed = false;
			}
			return board.getValue();
		}
		for (int i = 0; i < moves.size(); i++) {
			if (depthGoal == depthleft) {
				System.out.println("info Currmove "
						+ UCIBestLine(board.translate(moves.get(i)))
						+ " currmovenumber " + (1 + i));
			}
			board.move(moves.get(i));
			String temp = input + " " + board.translate(moves.get(i));
			int score = alphaBetaMax(alpha, beta, depthleft - 1, temp);
			board.undo();
			if (score <= alpha)
				return alpha; // fail hard alpha-cutoff
			if (score < beta) {
				if (!bestLine.toLowerCase().contains(temp.toLowerCase())) {
					changed = true;
					bestLine = temp;
				}
				beta = score; // beta acts like min in MiniMax
			}
		}
		return beta;
	}

	public String UCIBestLine(String line) {
		line = line.replaceAll("\\s*\\([^\\)]*\\)\\s*", " ");
		return line;
	}

	public String NPS(long nodes, long startTime) {
		double seconds = (System.currentTimeMillis() - startTime) / (1000.0);
		return decimalFormat.format(nodes / seconds);
	}
}
