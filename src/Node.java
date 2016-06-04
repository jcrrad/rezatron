public class Node extends Thread {

	public static final int BLACKWIN = -20000000;
	public static final int WHITEWIN = 2147483640;
	private final double TIE = 0;
	private Board board;
	private String move;
	private long start;
	private int globalDepth;
	private long count = 0;
	private int moveNumber = 0;
	private boolean cont;

	public Node(String fen, int depth) {
		board = new Board(fen);
		globalDepth = depth;
	}

	public Node() {
		board = new Board();
		globalDepth = 3;
	}

	public synchronized void setBestMove(String move) {
		this.move = move;
	}

	public synchronized void setFlag(boolean flag) {
		cont = flag;
	}

	public synchronized boolean getFlag() {
		return cont;
	}

	public synchronized String getBestMove() {
		return move;
	}

	public void start() {
		move = "";
		start = System.currentTimeMillis();
		count = 0;
		// alphaBetaMax(-20000000, 20000000, globalDepth);
		alphaBeta(-90000000, 90000000, globalDepth);
		if (move.equals("")) {
			int error = 0 / 0;
			if (board.isWhitesTurn()) {
				String moves = board.sortMoves(board.generateMoves(true));
				setBestMove(moves.substring(moves.length() - 5));
			} else {
				String moves = board.sortMoves(board.generateMoves(true));
				setBestMove(board.sortMoves(moves.substring(0, 0 + 5)));
			}
		}
	}

	public int alphaBetaMax(int alpha, int beta, int depthleft) {
		if (depthleft >= globalDepth - 1) {
			moveNumber++;
			System.out.println("info currmove " + board.UCI(board.getMove(1))
					+ " currmovenumber " + moveNumber);
		}
		count++;
		if (depthleft == 0 || !board.canContinue()) {
			// return quiesce(alpha, beta);
			return board.getValue();
		}
		String moves = board.sortMoves(board.generateMoves(true));
		for (int i = 0; i < moves.length(); i += 5) {
			board.move(moves.substring(i, i + 5));
			// int score = alphaBetaMin(alpha, beta, depthleft - 1);
			int score = alphaBetaMax(alpha, beta, depthleft - 1);
			board.undo();
			if (score >= beta) {

				return beta; // fail hard beta-cutoff
			}
			if (score > alpha) {
				if (!move.equals(board.getMove(1))) {
					System.out.println("info depth "
							+ (globalDepth - depthleft) + " seldepth "
							+ globalDepth + " pv " + board.getPV()
							+ " score cp " + score);
					System.out
							.println("info nps "
									+ (int) (count / (((System
											.currentTimeMillis() - start)) / 1000.0)));
					System.out.println("info nodes " + count);
					System.out.println("info time "
							+ ((System.currentTimeMillis() - start)));
					setBestMove(board.getMove(1));
				}
				alpha = score; // alpha acts like max in MiniMax
			}
		}
		return alpha;
	}

	public int alphaBetaMin(int alpha, int beta, int depthleft) {
		int error = 0 / 0;
		if (depthleft >= globalDepth - 1) {
			moveNumber++;
			System.out.println("info currmove " + board.UCI(board.getMove(1))
					+ " currmovenumber " + moveNumber);
		}
		count++;
		if (depthleft == 0 || !board.canContinue()) {
			// return -1 * Quiesce(alpha, beta);
			return -1 * board.getValue();
		}
		String moves = board
				.reorder(board.sortMoves(board.generateMoves(true)));
		for (int i = 0; i < moves.length(); i += 5) {
			board.move(moves.substring(i, i + 5));
			int score = alphaBetaMax(alpha, beta, depthleft - 1);
			board.undo();
			if (score <= alpha) {
				return alpha; // fail hard alpha-cutoff
			}
			if (score < beta) {
				if (!move.equals(moves.substring(i, i + 5))) {
					System.out.println("info depth "
							+ (globalDepth - depthleft) + " seldepth "
							+ globalDepth + " pv " + board.getPV()
							+ " score cp " + score);
					System.out
							.println("info nps "
									+ (int) (count / (((System
											.currentTimeMillis() - start)) / 1000.0)));
					System.out.println("info nodes " + count);
					System.out.println("info time "
							+ ((System.currentTimeMillis() - start)));
					setBestMove(board.getMove(1));
				}
				beta = score; // beta acts like min in MiniMax
			}
		}
		return beta;
	}

	public int alphaBeta(int alpha, int beta, int depthleft) {
		if (depthleft >= globalDepth - 1) {
			moveNumber++;
			System.out.println("info currmove " + board.UCI(board.getMove(1))
					+ " currmovenumber " + moveNumber);
		}
		if (depthleft == 0) {
			// return board.getValue();
			return quiesce(alpha, beta);
		}
		if (!board.canContinue()) {
			return board.getValue();
		}
		String moves = board.sortMoves(board.generateMoves(true));
		for (int i = 0; i < moves.length(); i += 5) {
			board.move(moves.substring(i, i + 5));
			count++;
			int score = -alphaBeta(-beta, -alpha, depthleft - 1);
			board.undo();
			if (score >= beta)
				return beta; // fail hard beta-cutoff
			if (score > alpha) {
				if (!move.equals(moves.substring(i, i + 5))) {
					System.out.println("info depth "
							+ (globalDepth - depthleft) + " seldepth "
							+ globalDepth + " pv " + board.getPV()
							+ " score cp " + score);
					System.out
							.println("info nps "
									+ (int) (count / (((System
											.currentTimeMillis() - start)) / 1000.0)));
					System.out.println("info nodes " + count);
					System.out.println("info time "
							+ ((System.currentTimeMillis() - start)));
					setBestMove(board.getMove(1));
				}
				alpha = score; // alpha acts like max in MiniMax
			}
		}
		return alpha;
	}

	public int quiesce(int alpha, int beta) {
		int stand_pat = board.getValue();
		if (stand_pat >= beta)
			return beta;
		if (alpha < stand_pat)
			alpha = stand_pat;
		String attackMoves = board.sortMoves(board.generateAttackMoves());
		for (int i = 0; i < attackMoves.length(); i += 5) {
			board.move(attackMoves.substring(i, i + 5));
			count++;
			int score = -quiesce(-beta, -alpha);
			System.out.println("info depth " + globalDepth + "+ seldepth "
					+ globalDepth + " pv " + board.getPV() + " score cp "
					+ score);
			System.out
					.println("info nps "
							+ (int) (count / (((System.currentTimeMillis() - start)) / 1000.0)));
			System.out.println("info nodes " + count);
			System.out.println("info time "
					+ ((System.currentTimeMillis() - start)));
			board.undo();

			if (score >= beta)
				return beta;
			if (score > alpha)
				alpha = score;
		}
		return alpha;
	}
}
