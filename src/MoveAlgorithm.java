import java.text.DecimalFormat;
import java.util.ArrayList;

public class MoveAlgorithm {
	protected Board board;
	private int depthGoal;
	int bestMove;
	private String bestPath;
	private int topBranch;
	private long nodeCount;
	private long startTime;
	private String pattern;
	private String pattern2;
	private DecimalFormat decimalFormat;
	private DecimalFormat decimalFormat2;
	private int MAXSCORE;
	private int MINSCORE;

	public MoveAlgorithm() {
		this(new Board(), 4);

	}

	public MoveAlgorithm(Board b, int depthGoal) {
		this.board = b;
		this.depthGoal = depthGoal;
		bestMove = 0;
		pattern = "00,000.000";
		pattern2 = "00000";
		decimalFormat = new DecimalFormat(pattern);
		decimalFormat2 = new DecimalFormat(pattern2);

	}

	/**
	 * A search tree that will use alphaBeta pruning to search the tree to a
	 * given depth.
	 * 
	 * @return
	 */
	public String alphaBeta(int alpha, int beta, int depthleft, String moveList) {
		String myList = moveList;
		ArrayList<Integer> moves = board.generateMovesNeo(true);
		if (depthleft == 0 || moves.size() == 0)
			return board.getValue() + "|" + myList;

		for (int i = 0; i < moves.size(); i++) {

			String score = (alphaBeta(-beta, -alpha, depthleft - 1, myList));
			if (score >= beta)
				return beta; // fail hard beta-cutoff
			if (score > alpha)
				alpha = score; // alpha acts like max in MiniMax
		}
		return alpha;
	}

	public String NPS(long nodes, long startTime) {
		double seconds = (System.currentTimeMillis() - startTime) / (1000.0);
		return decimalFormat.format(nodes / seconds);
	}
}
