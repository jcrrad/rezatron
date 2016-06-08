import java.text.DecimalFormat;
import java.util.ArrayList;

public class MoveAlgorithm {
	protected Board board;
	private int depthGoal;
	int bestMove;
	private ArrayList<Integer> bestPath;
	private int topBranch;
	private long nodeCount;
	private long startTime;
	private String pattern;
	private DecimalFormat decimalFormat;

	public MoveAlgorithm() {
		board = new Board();
		depthGoal = 4;
		bestMove = 0;

	}

	public MoveAlgorithm(Board b, int depthGoal) {
		this.board = b;
		this.depthGoal = depthGoal;
		bestMove = 0;
		pattern = "00,000.000";
		decimalFormat = new DecimalFormat(pattern);

	}

	/**
	 * A search tree that will use alphaBeta pruning to search the tree to a
	 * given depth.
	 * 
	 * @return
	 */
	public int alphaBeta() {
		return alphaBeta(depthGoal);
	}

	public int alphaBeta(int goal) {
		bestMove = 0;
		topBranch = board.generateMovesNeo(true).size();
		nodeCount = 0;
		startTime = System.currentTimeMillis();
		bestPath = new ArrayList<Integer>();
		ArrayList<Integer> path = new ArrayList<Integer>();
		int score = 0;
		if (board.isWhitesTurn()) {
			score = alphaBetaMax(-99999999, 99999999, goal, path);
		} else {
			score = alphaBetaMin(-99999999, 99999999, goal, path);
		}
		System.out.print(score + "-");
		for (int i = 0; i < bestPath.size(); i++) {
			System.out.print("\t" + board.translate(bestPath.get(i)));
		}
		System.out.println();
		return bestPath.get(0);
	}

	public int IDD(long timeLimit) {
		int ans = 0;
		int goal = 1;
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() < (start + timeLimit)) {
			System.out.println("Current Depth: " + goal + "\n\t");
			ans = alphaBeta(goal);
			goal++;
			for (int i = 0; i < bestPath.size(); i++) {
				System.out.print("\t" + board.translate(bestPath.get(i)));
			}
			bestPath = new ArrayList<Integer>();
			System.out.println();
		}
		return ans;
	}

	private int alphaBetaMax(int alpha, int beta, int depthleft,
			ArrayList<Integer> input) {
		if (depthleft == 0)
			return board.getValue();
		ArrayList<Integer> moves = board.generateMovesNeo(true);
		if (moves.size() == 0) {
			return board.getValue();
		}

		for (int i = 0; i < moves.size(); i++) {
			if (depthleft == depthGoal) {
				System.out.println("info currmove "
						+ board.translate(moves.get(i)) + " nodes " + nodeCount
						+ " nps " + this.NPS(nodeCount, startTime));
			}
			nodeCount++;
			board.move(moves.get(i));
			ArrayList<Integer> path = new ArrayList<Integer>();
			for (int q = 0; q < input.size(); q++) {
				path.add(input.get(q));
			}
			path.add(moves.get(i));
			int score = alphaBetaMin(alpha, beta, depthleft - 1, path);
			board.undo();
			if (score >= beta)
				return beta; // fail hard beta-cutoff
			if (score > alpha) {
				bestPath = new ArrayList<Integer>();
				for (int q = 0; q < path.size(); q++) {
					bestPath.add(path.get(q));
				}
				// System.out.println("Found a new best in max move:"
				// + board.translateMove(moves.get(i)));
				alpha = score; // alpha acts like max in MiniMax

			}
		}
		return alpha;
	}

	int alphaBetaMin(int alpha, int beta, int depthleft,
			ArrayList<Integer> input) {
		if (depthleft == 0)
			return board.getValue();
		ArrayList<Integer> moves = board.generateMovesNeo(true);
		if (moves.size() == 0) {
			return board.getValue();
		}
		for (int i = 0; i < moves.size(); i++) {
			if (depthleft == depthGoal) {
				System.out.println("info currmove "
						+ board.translate(moves.get(i)) + " nodes " + nodeCount
						+ " nps " + this.NPS(nodeCount, startTime));
			}
			nodeCount++;
			board.move(moves.get(i));
			ArrayList<Integer> path = new ArrayList<Integer>();
			for (int q = 0; q < input.size(); q++) {
				path.add(input.get(q));
			}
			path.add(moves.get(i));
			int score = alphaBetaMax(alpha, beta, depthleft - 1, path);
			board.undo();
			if (score <= alpha)
				return alpha; // fail hard alpha-cutoff
			if (score < beta) {
				bestPath = new ArrayList<Integer>();
				for (int q = 0; q < path.size(); q++) {
					bestPath.add(path.get(q));
				}
				// System.out.println("Found a new best in min move:"
				// + board.translateMove(moves.get(i)));
				beta = score; // beta acts like min in MiniMax
			}
		}
		return beta;
	}

	public String NPS(long nodes, long startTime) {
		double seconds = (System.currentTimeMillis() - startTime) / (1000.0);
		return decimalFormat.format(nodes / seconds);
	}
}
