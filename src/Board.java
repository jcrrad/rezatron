import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 
 * @author Rez
 * @version 0.1.3 - 04/02/14
 * 
 */
public class Board {
	private long wp, wr, wn, wb, wk, wq, qp, br, bn, bb, bk, bq, bp;
	private boolean bkc, bqc, wkc, wqc;
	private int moveCount;
	private long fortyMoveCount;
	// 7 + rank - file
	private static final short[] knightPlacement = { -50, -40, -30, -30, -30,
			-30, -40, -50, -40, -20, 0, 0, 0, 0, -20, -40, -30, 0, 10, 15, 15,
			10, 0, -30, -30, 5, 15, 20, 20, 15, 5, -30, -30, 0, 15, 20, 20, 15,
			0, -30, -30, 5, 10, 15, 15, 10, 5, -30, -40, -20, 0, 5, 5, 0, -20,
			-40, -50, -40, -30, -30, -30, -30, -40, -50, };
	private static final short[] blackKingPlacement = { -30, -40, -40, -50,
			-50, -40, -40, -30, -30, -40, -40, -50, -50, -40, -40, -30, -30,
			-40, -40, -50, -50, -40, -40, -30, -30, -40, -40, -50, -50, -40,
			-40, -30, -20, -30, -30, -40, -40, -30, -30, -20, -10, -20, -20,
			-20, -20, -20, -20, -10, 20, 20, 0, 0, 0, 0, 20, 20, 20, 30, 10, 0,
			0, 10, 30, 20 };
	private static final short[] whitePawnPlacement = { 0, 0, 0, 0, 0, 0, 0, 0,
			5, 10, 10, -20, -20, 10, 10, 5, 5, -5, -10, 0, 0, -10, -5, 5, 0, 0,
			0, 20, 20, 0, 0, 0, 5, 5, 10, 25, 25, 10, 5, 5, 10, 10, 20, 30, 30,
			20, 10, 10, 50, 50, 50, 50, 50, 50, 50, 50, 0, 0, 0, 0, 0, 0, 0, 0, };

	private static final short[] blackPawnPlacement = { 0, 0, 0, 0, 0, 0, 0, 0,
			50, 50, 50, 50, 50, 50, 50, 50, 10, 10, 20, 30, 30, 20, 10, 10, 5,
			5, 10, 25, 25, 10, 5, 5, 0, 0, 0, 20, 20, 0, 0, 0, 5, -5, -10, 0,
			0, -10, -5, 5, 5, 10, 10, -20, -20, 10, 10, 5, 0, 0, 0, 0, 0, 0, 0,
			0 };
	private static final short[] bishopPlacement = { -20, -10, -10, -10, -10,
			-10, -10, -20, -10, 0, 0, 0, 0, 0, 0, -10, -10, 0, 5, 10, 10, 5, 0,
			-10, -10, 5, 5, 10, 10, 5, 5, -10, -10, 0, 10, 10, 10, 10, 0, -10,
			-10, 10, 10, 10, 10, 10, 10, -10, -10, 5, 0, 0, 0, 0, 5, -10, -20,
			-10, -10, -10, -10, -10, -10, -20, };
	private static final short[] queenPlacement = { -20, -10, -10, -5, -5, -10,
			-10, -20, -10, 0, 0, 0, 0, 0, 0, -10, -10, 0, 5, 5, 5, 5, 0, -10,
			-5, 0, 5, 5, 5, 5, 0, -5, 0, 0, 5, 5, 5, 5, 0, -5, -10, 5, 5, 5, 5,
			5, 0, -10, -10, 0, 5, 0, 0, 0, 0, -10, -20, -10, -10, -5, -5, -10,
			-10, -20 };
	private static final short[] rookPlacement = { 0, 0, 0, 0, 0, 0, 0, 0, 5,
			10, 10, 10, 10, 10, 10, 5, -5, 0, 0, 0, 0, 0, 0, -5, -5, 0, 0, 0,
			0, 0, 0, -5, -5, 0, 0, 0, 0, 0, 0, -5, -5, 0, 0, 0, 0, 0, 0, -5,
			-5, 0, 0, 0, 0, 0, 0, -5, 0, 0, 0, 5, 5, 0, 0, 0 };
	private static final long[] diagonalMask = { 128L, 32832L, 8405024L,
			2151686160L, 550831656968L, 141012904183812L, 36099303471055874L,
			-9205322385119247871L, 4620710844295151872L, 2310355422147575808L,
			1155177711073755136L, 577588855528488960L, 288794425616760832L,
			144396663052566528L, 72057594037927936L };
	// rank + file
	private static final long[] antiDiagonalMask = { 1L, 258L, 66052L,
			16909320L, 4328785936L, 1108169199648L, 283691315109952L,
			72624976668147840L, 145249953336295424L, 290499906672525312L,
			580999813328273408L, 1161999622361579520L, 2323998145211531264L,
			4647714815446351872L, -9223372036854775808L };
	private static final long[] rankMask = { 255L, 65280L, 16711680L,
			4278190080L, 1095216660480L, 280375465082880L, 71776119061217280L,
			-72057594037927936L };
	private static final long[] fileMask = { 72340172838076673L,
			144680345676153346L, 289360691352306692L, 578721382704613384L,
			1157442765409226768L, 2314885530818453536L, 4629771061636907072L,
			-9187201950435737472L };
	private static final long[] squares = { 1L, 2L, 4L, 8L, 16L, 32L, 64L,
			128L, 256L, 512L, 1024L, 2048L, 4096L, 8192L, 16384L, 32768L,
			65536L, 131072L, 262144L, 524288L, 1048576L, 2097152L, 4194304L,
			8388608L, 16777216L, 33554432L, 67108864L, 134217728L, 268435456L,
			536870912L, 1073741824L, 2147483648L, 4294967296L, 8589934592L,
			17179869184L, 34359738368L, 68719476736L, 137438953472L,
			274877906944L, 549755813888L, 1099511627776L, 2199023255552L,
			4398046511104L, 8796093022208L, 17592186044416L, 35184372088832L,
			70368744177664L, 140737488355328L, 281474976710656L,
			562949953421312L, 1125899906842624L, 2251799813685248L,
			4503599627370496L, 9007199254740992L, 18014398509481984L,
			36028797018963968L, 72057594037927936L, 144115188075855872L,
			288230376151711744L, 576460752303423488L, 1152921504606846976L,
			2305843009213693952L, 4611686018427387904L, -9223372036854775808L };
	private static final int[] order = { 56, 57, 58, 59, 60, 61, 62, 63, 48,
			49, 50, 51, 52, 53, 54, 55, 40, 41, 42, 43, 44, 45, 46, 47, 32, 33,
			34, 35, 36, 37, 38, 39, 24, 25, 26, 27, 28, 29, 30, 31, 16, 17, 18,
			19, 20, 21, 22, 23, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5,
			6, 7 };
	private static final String[] letterSquares = { "a1", "b1", "c1", "d1",
			"e1", "f1", "g1", "h1", "a2", "b2", "c2", "d2", "e2", "f2", "g2",
			"h2", "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", "a4", "b4",
			"c4", "d4", "e4", "f4", "g4", "h4", "a5", "b5", "c5", "d5", "e5",
			"f5", "g5", "h5", "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
			"a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", "a8", "b8", "c8",
			"d8", "e8", "f8", "g8", "h8" };
	private static final long KING_SPAN = 460039L;
	private static long KNIGHT_SPAN = 43234889994L;
	private static final long FILE_A = 72340172838076673L;
	private static final long FILE_H = -9187201950435737472L;
	private static final long FILE_AB = 217020518514230019L;
	private static final long FILE_GH = -4557430888798830400L;
	private static final long RANK_1 = -72057594037927936L;
	private static final long RANK_4 = 1095216660480L;
	private static final long RANK_5 = 4278190080L;
	private static final long RANK_8 = 255L;
	private static final long CENTRE = 103481868288L;
	private static final long EXTENDED_CENTRE = 66229406269440L;
	private static final int wpn = 1;
	private static final int wrn = 2;
	private static final int wnn = 3;
	private static final int wbn = 4;
	private static final int wqn = 5;
	private static final int wkn = 6;

	private static final int bpn = 7;
	private static final int brn = 8;
	private static final int bnn = 9;
	private static final int bbn = 10;
	private static final int bqn = 11;
	private static final int bkn = 12;
	// private String[] history1;
	private ArrayList<Long> history;
	private ArrayList<String> enPassantTargetHistory;
	private int[] moves;
	private String enPassantTarget;
	private boolean isWhitesTurn;

	// private boolean temp=true;

	// private static String moves;

	public Board() {
		enPassantTarget = "-";
		history = new ArrayList<Long>();
		enPassantTargetHistory = new ArrayList<String>();
		moves = new int[6300];
		wp = 65280L;
		wr = 129L;
		wn = 66L;
		wb = 36L;
		wq = 8L;
		wk = 16L;

		bp = 71776119061217280L;
		br = -9151314442816847872L;
		bn = 4755801206503243776L;
		bb = 2594073385365405696L;
		bq = 576460752303423488L;
		bk = 1152921504606846976L;

		bkc = bqc = wkc = wqc = true;
		fortyMoveCount = 0;
		isWhitesTurn = true;
		// moves = "";
		moveCount = 0;
		updateHistory();
		moveCount = 1;
	}

	public Board(String fenString) {
		enPassantTarget = "-";
		// not chess960 compatible

		history = new ArrayList<Long>();
		enPassantTargetHistory = new ArrayList<String>();
		moves = new int[6300];
		wp = 0;
		wn = 0;
		wb = 0;
		wr = 0;
		wq = 0;
		wk = 0;

		bp = 0;
		bn = 0;
		bb = 0;
		br = 0;
		bq = 0;
		bk = 0;

		wkc = false;
		wqc = false;
		bkc = false;
		bqc = false;
		int charIndex = 0;
		int boardIndex = 0;
		while (fenString.charAt(charIndex) != ' ') {
			switch (fenString.charAt(charIndex++)) {
			case 'P':
				wp |= squares[order[boardIndex++]];
				break;
			case 'p':
				bp |= squares[order[boardIndex++]];
				break;
			case 'N':
				wn |= squares[order[boardIndex++]];
				break;
			case 'n':
				bn |= squares[order[boardIndex++]];
				break;
			case 'B':
				wb |= squares[order[boardIndex++]];
				break;
			case 'b':
				bb |= squares[order[boardIndex++]];
				break;
			case 'R':
				wr |= squares[order[boardIndex++]];
				break;
			case 'r':
				br |= squares[order[boardIndex++]];
				break;
			case 'Q':
				wq |= squares[order[boardIndex++]];
				break;
			case 'q':
				bq |= squares[order[boardIndex++]];
				break;
			case 'K':
				wk |= squares[order[boardIndex++]];
				break;
			case 'k':
				bk |= squares[order[boardIndex++]];
				break;
			case '/':
				break;
			case '1':
				boardIndex++;
				break;
			case '2':
				boardIndex += 2;
				break;
			case '3':
				boardIndex += 3;
				break;
			case '4':
				boardIndex += 4;
				break;
			case '5':
				boardIndex += 5;
				break;
			case '6':
				boardIndex += 6;
				break;
			case '7':
				boardIndex += 7;
				break;
			case '8':
				boardIndex += 8;
				break;
			default:
				break;
			}
		}
		if (fenString.charAt(++charIndex) == 'w')
			isWhitesTurn = true;
		else
			isWhitesTurn = false;
		charIndex += 2;
		while (fenString.charAt(charIndex) != ' ') {
			switch (fenString.charAt(charIndex++)) {
			case '-':
				break;
			case 'K':
				wkc = true;
				break;
			case 'Q':
				wqc = true;
				break;
			case 'k':
				bkc = true;
				break;
			case 'q':
				bqc = true;
				break;
			default:
				break;
			}
		}
		if (pieceAtSquare(0) != wrn)
			wqc = false;
		if (pieceAtSquare(7) != wrn)
			wkc = false;
		if (pieceAtSquare(56) != brn)
			bqc = false;
		if (pieceAtSquare(63) != brn)
			bkc = false;
		enPassantTarget = "-";
		if (fenString.charAt(++charIndex) != '-') {
			enPassantTarget = fenString.substring(charIndex, charIndex + 2);
		}

		// the rest of the fenString is not yet utilized
		// moves = "";
		moveCount = 0;
		updateHistory();
		moveCount = 1;
	}

	public void undo() {
		isWhitesTurn = !isWhitesTurn;

		enPassantTargetHistory.remove(enPassantTargetHistory.size() - 1);
		enPassantTarget = enPassantTargetHistory.get(enPassantTargetHistory
				.size() - 1);

		for (int i = 0; i < 17; i++) {
			history.remove(history.size() - 1);
		}

		fortyMoveCount = (history.get(history.size() - 1));
		// history.remove(history.size()-1);

		bqc = (history.get(history.size() - 2)) != 0;
		// history.remove(history.size()-1);
		bkc = (history.get(history.size() - 3)) != 0;
		// history.remove(history.size()-1);
		wqc = (history.get(history.size() - 4)) != 0;
		// history.remove(history.size()-1);
		wkc = (history.get(history.size() - 5)) != 0;
		// history.remove(history.size()-1);

		bk = (history.get(history.size() - 6));
		// history.remove(history.size()-1);

		bq = (history.get(history.size() - 7));
		// history.remove(history.size()-1);

		bb = (history.get(history.size() - 8));
		// history.remove(history.size()-1);

		bn = (history.get(history.size() - 9));
		// history.remove(history.size()-1);

		br = (history.get(history.size() - 10));
		// history.remove(history.size()-1);

		bp = (history.get(history.size() - 11));
		// history.remove(history.size()-1);

		wk = (history.get(history.size() - 12));
		// history.remove(history.size()-1);

		wq = (history.get(history.size() - 13));
		// history.remove(history.size()-1);

		wb = (history.get(history.size() - 14));
		// history.remove(history.size()-1);

		wn = (history.get(history.size() - 15));
		// history.remove(history.size()-1);

		wr = (history.get(history.size() - 16));
		// history.remove(history.size()-1);

		wp = (history.get(history.size() - 17));
		// history.remove(history.size()-1);
		moveCount -= 1;
		// wp + "," + wr + "," + wn + "," + wb + "," + wq
		// + "," + wk + "," + bp + "," + br + "," + bn + "," + bb + ","
		// + bq + "," + bk + "," + wkc + "," + wqc + "," + bkc + "," + bqc
		// + "," + enPassantTarget + "," + fortyMoveCount

	}

	public void move(int move) {
		fortyMoveCount++;
		int from = move / 1000 % 1000;
		// move-=from*1000;
		int to = move / 10 % 100;
		int attackedPiece = pieceAtSquare(to);
		int promote = move % 10;
		int piece = pieceAtSquare(from);
		enPassantTarget = "-";
		if (move == 4061) {
			fortyMoveCount = 0;
			wk += (squares[6] - squares[4]);
			wr += (squares[5] - squares[7]);
		} else if (move == 4021) {
			fortyMoveCount = 0;
			wk += (squares[2] - squares[4]);
			wr += (squares[3] - squares[0]);
		} else if (move == 60621) {
			fortyMoveCount = 0;
			bk += (squares[62] - squares[60]);
			br += (squares[61] - squares[63]);
		} else if (move == 60581) {
			fortyMoveCount = 0;
			bk += (squares[58] - squares[60]);
			br += (squares[59] - squares[56]);
		} else {
			// AABBC
			// AA - From square
			// BB - To Square
			switch (piece) {
			case wpn:
				wp += (squares[to] - squares[from]);
				if (to - from == 16) {
					enPassantTarget = letterSquares[from + 8];
				}
				fortyMoveCount = 0;
				break;
			case wrn:
				wr += (squares[to] - squares[from]);
				break;
			case wnn:
				wn += (squares[to] - squares[from]);
				break;
			case wbn:
				wb += (squares[to] - squares[from]);
				break;
			case wqn:
				wq += (squares[to] - squares[from]);
				break;
			case wkn:
				wk += (squares[to] - squares[from]);
				break;
			case bpn:
				bp += (squares[to] - squares[from]);
				fortyMoveCount = 0;
				if (from - to == 16) {
					enPassantTarget = letterSquares[from - 8];
				}
				break;
			case brn:
				br += (squares[to] - squares[from]);
				break;
			case bnn:
				bn += (squares[to] - squares[from]);
				break;
			case bbn:
				bb += (squares[to] - squares[from]);
				break;
			case bqn:
				bq += (squares[to] - squares[from]);
				break;
			case bkn:
				bk += (squares[to] - squares[from]);
				break;
			}
			switch (attackedPiece) {
			case wpn:
				fortyMoveCount = 0;
				wp -= squares[to];
				break;
			case wrn:
				fortyMoveCount = 0;
				wr -= squares[to];
				break;
			case wnn:
				fortyMoveCount = 0;
				wn -= squares[to];
				break;
			case wbn:
				fortyMoveCount = 0;
				wb -= squares[to];
				break;
			case wqn:
				fortyMoveCount = 0;
				wq -= squares[to];
				break;
			case bpn:
				fortyMoveCount = 0;
				bp -= squares[to];
				break;
			case brn:
				fortyMoveCount = 0;
				br -= squares[to];
				break;
			case bnn:
				fortyMoveCount = 0;
				bn -= squares[to];
				break;
			case bbn:
				fortyMoveCount = 0;
				bb -= squares[to];
				break;
			case bqn:
				fortyMoveCount = 0;
				bq -= squares[to];
				break;
			}
			if (promote > 1) {
				fortyMoveCount = 0;
				if (promote == 9) {
					if (piece == wpn)
						bp -= squares[to - 8];
					else
						wp -= squares[to + 8];
				} else {
					if (piece == wpn) {
						if (promote == 2) {
							wp -= squares[to];
							wq += squares[to];
						} else if (promote == 3) {
							wp -= squares[to];
							wn += squares[to];
						} else if (promote == 4) {
							wp -= squares[to];
							wr += squares[to];
						} else if (promote == 5) {
							wp -= squares[to];
							wb += squares[to];
						}
					} else {
						if (promote == 2) {
							bp -= squares[to];
							bq += squares[to];
						} else if (promote == 3) {
							bp -= squares[to];
							bn += squares[to];
						} else if (promote == 4) {
							bp -= squares[to];
							br += squares[to];
						} else if (promote == 5) {
							bp -= squares[to];
							bb += squares[to];
						}
					}
				}
			}
			// TODO take care of en Passant
		}
		// moves += move;
		if (from == 0 || to == 0) {
			wqc = false;
		}
		if (from == 7 || to == 7) {
			wkc = false;
		}
		if (from == 56 || to == 56) {
			bqc = false;
		}
		if (from == 63 || to == 63) {
			bkc = false;
		}
		if (from == 4) {
			wkc = false;
			wqc = false;
		}
		if (from == 60) {
			bkc = false;
			bqc = false;
		}
		isWhitesTurn = !isWhitesTurn;
		updateHistory();
		// moves[moveCount] = move;
		moveCount++;
	}

	public String toString() {
		String rowString = "   +---+---+---+---+---+---+---+---+";
		String printBoard = "\n\n" + rowString + "\n";
		int row = 8;
		for (int x = 0; x < order.length; x++) {
			int i = order[x];
			if (i % 8 == 0) {
				printBoard += row + "  |";
				row--;
			}

			if (((wp >> i) & 1) == 1) {
				printBoard += "(P)" + "|";
			} else if (((wr >> i) & 1) == 1) {
				printBoard += "(R)" + "|";
			} else if (((wn >> i) & 1) == 1) {
				printBoard += "(N)" + "|";
			} else if (((wb >> i) & 1) == 1) {
				printBoard += "(B)" + "|";
			} else if (((wq >> i) & 1) == 1) {
				printBoard += "(Q)" + "|";
			} else if (((wk >> i) & 1) == 1) {
				printBoard += "(K)" + "|";
			} else if (((bp >> i) & 1) == 1) {
				printBoard += "*p*" + "|";
			} else if (((br >> i) & 1) == 1) {
				printBoard += "*r*" + "|";
			} else if (((bn >> i) & 1) == 1) {
				printBoard += "*n*" + "|";
			} else if (((bb >> i) & 1) == 1) {
				printBoard += "*b*" + "|";
			} else if (((bq >> i) & 1) == 1) {
				printBoard += "*q*" + "|";
			} else if (((bk >> i) & 1) == 1) {
				printBoard += "*k*" + "|";
			} else {
				printBoard += "   " + "|";
			}
			if (i % 8 == 7)
				printBoard += "\n" + rowString + "\n";
		}
		printBoard += "     a   b   c   d   e   f   g   h \n\n";
		return printBoard;
	}

	private String toString(Long test) {
		String rowString = "   +---+---+---+---+---+---+---+---+";
		String printBoard = "\n\n" + rowString + "\n";
		int row = 8;
		for (int x = 0; x < order.length; x++) {
			int i = order[x];
			if (i % 8 == 0) {
				printBoard += row + "  |";
				row--;
			}

			if (((test >> i) & 1) == 1) {
				printBoard += "(X)" + "|";
			} else {
				printBoard += " " + String.format("%02d", i) + "|";
			}
			if (i % 8 == 7)
				printBoard += "\n" + rowString + "\n";
		}
		printBoard += "     a   b   c   d   e   f   g   h \n\n";
		return printBoard;
	}

	private long whitePawnMoveOne() {
		return wp << 8;
	}

	private long whitePawnMoveTwo() {
		return ((((wp << 8) & getEmpty()) << 8) & rankMask[3]);
	}

	private long whitePawnAttackLeft() {
		return (wp << 7) & ~FILE_H;
	}

	private long whitePawnAttackRight() {
		return (wp << 9) & ~FILE_A;
	}

	private long blackPawnMoveOne() {
		return bp >> 8;
	}

	private long blackPawnMoveTwo() {
		return ((((bp >> 8) & getEmpty()) >> 8) & rankMask[4]);
	}

	private long blackPawnAttackRight() {
		return (bp >> 7) & ~FILE_A;
	}

	private long blackPawnAttackLeft() {
		return (bp >> 9) & ~FILE_H;
	}

	private long blackEnPassantLeft() {
		if (enPassantTarget.equals("-") || isWhitesTurn)
			return 0L;
		return getLong(enPassantTarget) & blackPawnAttackLeft();
	}

	private long blackEnPassantRight() {
		if (enPassantTarget.equals("-") || isWhitesTurn)
			return 0L;
		return getLong(enPassantTarget) & blackPawnAttackRight();
	}

	private long whiteEnPassantLeft() {
		if (enPassantTarget.equals("-") || !isWhitesTurn)
			return 0L;
		return getLong(enPassantTarget) & whitePawnAttackLeft();
	}

	private long whiteEnPassantRight() {
		if (enPassantTarget.equals("-") || !isWhitesTurn)
			return 0L;
		return getLong(enPassantTarget) & whitePawnAttackRight();
	}

	private int countBits(long x) {
		long m1 = 0x5555555555555555L; // binary: 0101...
		long m2 = 0x3333333333333333L; // binary: 00110011..
		long m4 = 0x0f0f0f0f0f0f0f0fL; // binary: 4 zeros, 4 ones ...

		x -= (x >> 1) & m1; // put count of each 2 bits into those 2 bits
		x = (x & m2) + ((x >> 2) & m2); // put count of each 4 bits into those 4
		// bits
		x = (x + (x >> 4)) & m4; // put count of each 8 bits into those 8 bits
		x += x >> 8; // put count of each 16 bits into their lowest 8 bits
		x += x >> 16; // put count of each 32 bits into their lowest 8 bits
		x += x >> 32; // put count of each 64 bits into their lowest 8 bits
		return (int) (x & 0x7fL);
	}

	private long getRookMovement(long OCCUPIED, int square) {
		long binaryS = 1L << square;
		long r = Long.reverse(binaryS);
		long twoR = 2 * r;
		long fMask = fileMask[square % 8];
		long one = ((OCCUPIED & fMask) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & fMask) - (twoR));
		long two = (OCCUPIED - 2 * binaryS)
				^ Long.reverse(Long.reverse(OCCUPIED) - twoR);
		return (one & fileMask[square % 8]) + (two & rankMask[square / 8]);

	}

	private long getKingMovement(int square) {
		long moves = 0L;

		if (square > 9) {
			moves = KING_SPAN << (square - 9);
		} else {
			moves = KING_SPAN >> (9 - square);
		}
		if (square % 8 < 4)
			return moves & ~FILE_GH;
		else
			return moves & ~FILE_AB;

	}

	private long getKnightMovement(int square) {
		long moves = 0L;

		if (square > 18) {
			moves = KNIGHT_SPAN << (square - 18);
		} else {
			moves = KNIGHT_SPAN >> (18 - square);
		}
		if (square % 8 < 4)
			return (moves & ~FILE_GH);
		else
			return (moves & ~FILE_AB);
	}

	private long getBishopMovement(long OCCUPIED, int square) {
		long binaryS = 1L << square;
		long r = Long.reverse(binaryS);
		long twoR = 2 * r;
		long dMask = diagonalMask[7 + square / 8 - square % 8];
		long adMask = antiDiagonalMask[(square / 8) + (square % 8)];
		long one = ((OCCUPIED & dMask) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & dMask) - (twoR));
		long two = ((OCCUPIED & adMask) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & adMask) - (twoR));
		return (one & dMask) | (two & adMask);

	}

	private long getQueenMovement(long OCCUPIED, int square) {
		long binaryS = 1L << square;
		long r = Long.reverse(binaryS);
		long twoR = 2 * r;
		long dMask = diagonalMask[7 + square / 8 - square % 8];
		long adMask = antiDiagonalMask[(square / 8) + (square % 8)];
		long fMask = fileMask[square % 8];
		long one = ((OCCUPIED & dMask) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & dMask) - (twoR));
		long two = ((OCCUPIED & adMask) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & adMask) - (twoR));
		long three = ((OCCUPIED & fMask) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & fMask) - (twoR));
		long four = (OCCUPIED - 2 * binaryS)
				^ Long.reverse(Long.reverse(OCCUPIED) - twoR);
		return ((four & rankMask[square / 8]) | (three & fMask))
				| (one & dMask) | (two & adMask);

	}

	private long xrayRookAttacks(long occ, long blockers, int rookSq) {
		long attacks = getRookMovement(occ, rookSq);
		blockers &= attacks;
		return attacks ^ getRookMovement(occ ^ blockers, rookSq);
	}

	private long xrayBishopAttacks(long occ, long blockers, int bishopSq) {
		long attacks = getBishopMovement(occ, bishopSq);
		blockers &= attacks;
		return attacks ^ getBishopMovement(occ ^ blockers, bishopSq);
	}

	private long getWhitePins() {
		long me = getWhite();
		long all = getAll();
		long diag = xrayBishopAttacks(all, me, getWhiteKingSquare());
		long cross = xrayRookAttacks(all, me, getWhiteKingSquare());
		long pinningDiag = (diag & (bb | bq));
		long attackDiag = 0L;
		long attackCross = 0L;
		long pinningCross = (cross & (br | bq));
		for (long temp = pinningDiag; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			attackDiag |= getBishopMovement(all,
					Long.numberOfTrailingZeros(temp));
		}
		for (long temp = pinningCross; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			attackCross |= getRookMovement(all,
					Long.numberOfTrailingZeros(temp));
		}
		attackCross &= me;
		attackDiag &= me;
		return attackDiag | attackCross;

	}

	private long getBlackPins() {
		long me = getBlack();
		long all = getAll();
		long diag = xrayBishopAttacks(all, me, getBlackKingSquare());
		long cross = xrayRookAttacks(all, me, getBlackKingSquare());
		long pinningDiag = (diag & (wb | wq));
		long attackDiag = 0L;
		long attackCross = 0L;
		long pinningCross = (cross & (wr | wq));
		for (long temp = pinningDiag; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			attackDiag |= getBishopMovement(all,
					Long.numberOfTrailingZeros(temp));
		}
		for (long temp = pinningCross; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			attackCross |= getRookMovement(all,
					Long.numberOfTrailingZeros(temp));
		}
		attackCross &= me;
		attackDiag &= me;
		return attackDiag | attackCross;

	}

	private ArrayList<Integer> writeRookMovementNeo(int square) {
		ArrayList<Integer> ans = new ArrayList<Integer>();
		long binaryS = 1L << square;
		long r = Long.reverse(binaryS);
		long twoR = 2 * r;
		long OCCUPIED = getAll();
		long fMask = fileMask[square % 8];
		long moves = 0L;

		long one = ((OCCUPIED & fMask) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & fMask) - (twoR));
		moves &= rankMask[square / 8];
		long two = (OCCUPIED - 2 * binaryS)
				^ Long.reverse(Long.reverse(OCCUPIED) - twoR);
		moves = (one & fileMask[square % 8]) + (two & rankMask[square / 8]);

		if ((((wr >> square) & 1) == 1))
			moves &= (getBlack() | getEmpty());
		else
			moves &= (getWhite() | getEmpty());
		for (int i = 0; i < 64; i++) {

			long temp = moves & (1L << i);
			if (temp != 0) {
				ans.add(1000 * square + 10 * i);
			}
		}
		return ans;
	}

	private ArrayList<Integer> writeKingnMovementNeo(int square) {
		ArrayList<Integer> ans = new ArrayList<Integer>();
		long moves = 0L;

		if (square > 9) {
			moves = KING_SPAN << (square - 9);
		} else {
			moves = KING_SPAN >> (9 - square);
		}
		if (square % 8 < 4) {
			moves &= ~FILE_GH;
		} else {
			moves &= ~FILE_AB;
		}
		if ((((wk >> square) & 1) == 1))
			moves &= (getBlack() | getEmpty());
		else
			moves &= (getWhite() | getEmpty());

		for (int i = 0; i < 64; i++) {

			long temp = moves & (1L << i);
			if (temp != 0) {
				ans.add(1000 * square + 10 * i);
			}
		}
		return ans;
	}

	private ArrayList<Integer> writeKnightMovementNeo(int square) {
		ArrayList<Integer> ans = new ArrayList<Integer>();
		long moves = 0L;

		if (square > 18) {
			moves = KNIGHT_SPAN << (square - 18);
		} else {
			moves = KNIGHT_SPAN >> (18 - square);
		}
		if (square % 8 < 4) {
			moves &= ~FILE_GH;
		} else {
			moves &= ~FILE_AB;
		}
		if ((((wn >> square) & 1) == 1))
			moves &= (getBlack() | getEmpty());
		else
			moves &= (getWhite() | getEmpty());

		for (int i = 0; i < 64; i++) {

			long temp = moves & (1L << i);
			if (temp != 0) {
				ans.add(1000 * square + 10 * i);
			}
		}
		return ans;
	}

	private ArrayList<Integer> writeBishopMovementNeo(int square) {
		ArrayList<Integer> ans = new ArrayList<Integer>();
		long binaryS = 1L << square;
		long r = Long.reverse(binaryS);
		long twoR = 2 * r;
		long OCCUPIED = getAll();
		long dMask = diagonalMask[7 + square / 8 - square % 8];
		long adMask = antiDiagonalMask[(square / 8) + (square % 8)];
		long moves = 0L;
		long one = ((OCCUPIED & dMask) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & dMask) - (twoR));
		long two = ((OCCUPIED & adMask) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & adMask) - (twoR));
		moves = (one & dMask) | (two & adMask);
		if ((((wb >> square) & 1) == 1))
			moves &= (getBlack() | getEmpty());
		else
			moves &= (getWhite() | getEmpty());

		for (int i = 0; i < 64; i++) {

			long temp = moves & (1L << i);
			if (temp != 0) {
				ans.add(1000 * square + 10 * i);
			}
		}
		return ans;

	}

	public static ArrayList<Integer> writeMoves(long moves, int square) {

		ArrayList<Integer> ans = new ArrayList<Integer>();

		for (int i = 0; i < 64; i++) {

			long temp = moves & (1L << i);
			if (temp != 0) {
				ans.add(1000 * square + 10 * i);
			}
		}
		return ans;
	}

	private ArrayList<Integer> writeQueenMovementNeo(int square) {
		ArrayList<Integer> ans = new ArrayList<Integer>();
		long binaryS = 1L << square;
		long r = Long.reverse(binaryS);
		long twoR = 2 * r;
		long OCCUPIED = getAll();
		long dMask = diagonalMask[7 + square / 8 - square % 8];
		long adMask = antiDiagonalMask[(square / 8) + (square % 8)];
		long fMask = fileMask[square % 8];
		long moves = 0L;
		long one = ((OCCUPIED & dMask) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & dMask) - (twoR));
		long two = ((OCCUPIED & adMask) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & adMask) - (twoR));
		long three = ((OCCUPIED & fMask) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & fMask) - (twoR));
		long four = (OCCUPIED - 2 * binaryS)
				^ Long.reverse(Long.reverse(OCCUPIED) - twoR);
		moves = ((four & rankMask[square / 8]) | (three & fMask))
				| (one & dMask) | (two & adMask);
		if ((((wq >> square) & 1) == 1))
			moves &= (getBlack() | getEmpty());
		else
			moves &= (getWhite() | getEmpty());

		for (int i = 0; i < 64; i++) {

			long temp = moves & (1L << i);
			if (temp != 0) {
				ans.add(1000 * square + 10 * i);
			}
		}
		return ans;
	}

	private ArrayList<Integer> writeWhiteMovementNeo() {
		ArrayList<Integer> ans = new ArrayList<Integer>();
		for (long temp = (wr | wb | wn | wq); temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			switch (pieceAtSquare(Long.numberOfTrailingZeros(temp))) {
			case wrn:
				ans.addAll(writeRookMovementNeo((Long
						.numberOfTrailingZeros(temp))));
				// System.out.println("After ROOK: " + ans);
				break;
			case wnn:
				ans.addAll(writeKnightMovementNeo((Long
						.numberOfTrailingZeros(temp))));
				// System.out.println("After KNIGHT: " + ans);
				break;
			case wbn:
				ans.addAll(writeBishopMovementNeo((Long
						.numberOfTrailingZeros(temp))));
				// System.out.println("After BISHOP: " + ans);
				break;
			case wqn:
				ans.addAll(writeQueenMovementNeo((Long
						.numberOfTrailingZeros(temp))));
				// System.out.println("After QUEEN: " + ans);
				break;
			}
		}
		ans.addAll(writeKingnMovementNeo(Long.numberOfTrailingZeros(wk)));

		return ans;
	}

	private ArrayList<Integer> writeBlackMovementNeo() {
		ArrayList<Integer> ans = new ArrayList<Integer>();
		for (long temp = (br | bb | bn | bq); temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			switch (pieceAtSquare(Long.numberOfTrailingZeros(temp))) {
			case brn:
				ans.addAll(writeRookMovementNeo((Long
						.numberOfTrailingZeros(temp))));
				break;
			case bnn:
				ans.addAll(writeKnightMovementNeo((Long
						.numberOfTrailingZeros(temp))));
				break;
			case bbn:
				ans.addAll(writeBishopMovementNeo((Long
						.numberOfTrailingZeros(temp))));
				break;
			case bqn:
				ans.addAll(writeQueenMovementNeo((Long
						.numberOfTrailingZeros(temp))));
				break;
			}
		}
		ans.addAll(writeKingnMovementNeo(Long.numberOfTrailingZeros(bk)));

		return ans;
	}

	private long getBlackMovement() {
		Long ans = 0L;
		long all = getAll();
		for (long temp = (br | bb | bn | bq); temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			switch (pieceAtSquare(Long.numberOfTrailingZeros(temp))) {
			case brn:
				ans |= getRookMovement(all, Long.numberOfTrailingZeros(temp));
				break;
			case bnn:
				ans |= getKnightMovement(Long.numberOfTrailingZeros(temp));
				break;
			case bbn:
				ans |= getBishopMovement(all, Long.numberOfTrailingZeros(temp));
				break;
			case bqn:
				ans |= getQueenMovement(all, Long.numberOfTrailingZeros(temp));
				break;
			}
		}
		ans |= getKingMovement(Long.numberOfTrailingZeros(bk));
		return ans;
	}

	private long getWhiteMovement() {
		Long ans = 0L;
		long all = getAll();
		for (long temp = (wr | wb | wn | wq); temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			switch (pieceAtSquare(Long.numberOfTrailingZeros(temp))) {
			case wrn:
				ans |= getRookMovement(all, Long.numberOfTrailingZeros(temp));
				break;
			case wnn:
				ans |= getKnightMovement(Long.numberOfTrailingZeros(temp));
				break;
			case wbn:
				ans |= getBishopMovement(all, Long.numberOfTrailingZeros(temp));
				break;
			case wqn:
				ans |= getQueenMovement(all, Long.numberOfTrailingZeros(temp));
				break;
			}
		}
		ans |= getKingMovement(Long.numberOfTrailingZeros(wk));
		return ans;
	}

	private ArrayList<Integer> writeWhitePawnMovementNeo() {
		ArrayList<Integer> ans = new ArrayList<Integer>();
		long them = getBlack();
		long empty = getEmpty();
		long moveOne = whitePawnMoveOne() & empty;
		long moveTwo = whitePawnMoveTwo() & empty;
		long attackLeft = whitePawnAttackLeft() & them;
		long attackRight = whitePawnAttackRight() & them;
		long promoteMove = moveOne & rankMask[7];
		long enPassantLeft = whiteEnPassantLeft();
		long enPassantRight = whiteEnPassantRight();
		moveOne = moveOne & ~rankMask[7];
		long attackLeftPromote = attackLeft & rankMask[7];
		attackLeft = attackLeft & ~rankMask[7];
		long attackRightPromote = attackRight & rankMask[7];
		attackRight = attackRight & ~rankMask[7];
		for (long temp = moveOne; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			ans.add(1000 * (Long.numberOfTrailingZeros(temp) - 8) + 10
					* Long.numberOfTrailingZeros(temp));
		}
		for (long temp = moveTwo; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			ans.add(1000 * (Long.numberOfTrailingZeros(temp) - 16) + 10
					* Long.numberOfTrailingZeros(temp));
		}

		for (long temp = attackLeft; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			ans.add(1000 * (Long.numberOfTrailingZeros(temp) - 7) + 10
					* Long.numberOfTrailingZeros(temp));
		}

		for (long temp = attackRight; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			ans.add(1000 * (Long.numberOfTrailingZeros(temp) - 9) + 10
					* Long.numberOfTrailingZeros(temp));
		}
		for (long temp = attackLeftPromote; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			int to = Long.numberOfTrailingZeros(temp);
			int from = Long.numberOfTrailingZeros(temp) - 7;
			ans.add(1000 * from + 10 * to + 2);
			ans.add(1000 * from + 10 * to + 3);
			ans.add(1000 * from + 10 * to + 4);
			ans.add(1000 * from + 10 * to + 5);
		}

		for (long temp = attackRightPromote; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			int to = Long.numberOfTrailingZeros(temp);
			int from = Long.numberOfTrailingZeros(temp) - 9;
			ans.add(1000 * from + 10 * to + 2);
			ans.add(1000 * from + 10 * to + 3);
			ans.add(1000 * from + 10 * to + 4);
			ans.add(1000 * from + 10 * to + 5);
		}
		for (long temp = promoteMove; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			int to = Long.numberOfTrailingZeros(temp);
			int from = Long.numberOfTrailingZeros(temp) - 8;
			ans.add(1000 * from + 10 * to + 2);
			ans.add(1000 * from + 10 * to + 3);
			ans.add(1000 * from + 10 * to + 4);
			ans.add(1000 * from + 10 * to + 5);
		}
		for (long temp = enPassantLeft; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			ans.add(1000 * (Long.numberOfTrailingZeros(temp) - 7) + 10
					* Long.numberOfTrailingZeros(temp) + 9);
		}

		for (long temp = enPassantRight; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			ans.add(1000 * (Long.numberOfTrailingZeros(temp) - 9) + 10
					* Long.numberOfTrailingZeros(temp) + 9);
		}
		return ans;
	}

	private ArrayList<Integer> writeBlackPawnMovementNeo() {
		ArrayList<Integer> ans = new ArrayList<Integer>();
		long them = getWhite();
		long empty = getEmpty();
		long moveOne = blackPawnMoveOne() & empty;
		long moveTwo = blackPawnMoveTwo() & empty;
		long attackLeft = blackPawnAttackLeft() & them;
		long attackRight = blackPawnAttackRight() & them;
		long promoteMove = moveOne & rankMask[0];
		moveOne = moveOne & ~rankMask[0];
		long attackLeftPromote = attackLeft & rankMask[0];
		attackLeft = attackLeft & ~rankMask[0];
		long attackRightPromote = attackRight & rankMask[0];
		attackRight = attackRight & ~rankMask[0];
		long enPassantLeft = blackEnPassantLeft();
		long enPassantRight = blackEnPassantRight();

		for (long temp = moveOne; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			ans.add(1000 * (Long.numberOfTrailingZeros(temp) + 8) + 10
					* Long.numberOfTrailingZeros(temp));
		}
		for (long temp = moveTwo; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			ans.add(1000 * (Long.numberOfTrailingZeros(temp) + 16) + 10
					* Long.numberOfTrailingZeros(temp));
		}

		for (long temp = attackLeft; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			ans.add(1000 * (Long.numberOfTrailingZeros(temp) + 9) + 10
					* Long.numberOfTrailingZeros(temp));
		}

		for (long temp = attackRight; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			ans.add(1000 * (Long.numberOfTrailingZeros(temp) + 7) + 10
					* Long.numberOfTrailingZeros(temp));
		}
		for (long temp = attackLeftPromote; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			int to = Long.numberOfTrailingZeros(temp);
			int from = Long.numberOfTrailingZeros(temp) + 9;
			ans.add(1000 * from + 10 * to + 2);
			ans.add(1000 * from + 10 * to + 3);
			ans.add(1000 * from + 10 * to + 4);
			ans.add(1000 * from + 10 * to + 5);
		}

		for (long temp = attackRightPromote; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			int to = Long.numberOfTrailingZeros(temp);
			int from = Long.numberOfTrailingZeros(temp) + 7;
			ans.add(1000 * from + 10 * to + 2);
			ans.add(1000 * from + 10 * to + 3);
			ans.add(1000 * from + 10 * to + 4);
			ans.add(1000 * from + 10 * to + 5);
		}
		for (long temp = promoteMove; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			int to = Long.numberOfTrailingZeros(temp);
			int from = Long.numberOfTrailingZeros(temp) + 8;
			ans.add(1000 * from + 10 * to + 2);
			ans.add(1000 * from + 10 * to + 3);
			ans.add(1000 * from + 10 * to + 4);
			ans.add(1000 * from + 10 * to + 5);

		}
		for (long temp = enPassantLeft; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			ans.add(1000 * (Long.numberOfTrailingZeros(temp) + 9) + 10
					* Long.numberOfTrailingZeros(temp) + 9);
		}

		for (long temp = enPassantRight; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			ans.add(1000 * (Long.numberOfTrailingZeros(temp) + 7) + 10
					* Long.numberOfTrailingZeros(temp) + 9);
		}
		return ans;
	}

	private int getWhiteKingSquare() {
		return Long.numberOfTrailingZeros(wk);
	}

	private int getBlackKingSquare() {
		return Long.numberOfTrailingZeros(bk);
	}

	private boolean isWhiteChecked() {
		long attacks = blackPawnAttackLeft() | blackPawnAttackRight()
				| getBlackMovement();
		if (isAttackedbyBlack(getWhiteKingSquare(), attacks))
			return true;
		return false;
	}

	private boolean isBlackChecked() {
		long attacks = whitePawnAttackLeft() | whitePawnAttackRight()
				| getWhiteMovement();
		if (isAttackedbyWhite(getBlackKingSquare(), attacks))
			return true;
		return false;
	}

	private boolean isWhiteChecked(long attacks) {
		if (isAttackedbyBlack(getWhiteKingSquare(), attacks))
			return true;
		return false;
	}

	private boolean isBlackChecked(long attacks) {
		if (isAttackedbyWhite(getBlackKingSquare(), attacks))
			return true;
		return false;
	}

	private boolean isAttackedbyWhite(int square, long attack) {
		if ((squares[square] & attack) != 0)
			return true;
		return false;
	}

	private boolean isAttackedbyBlack(int square, long attack) {
		if ((squares[square] & attack) != 0)
			return true;
		return false;
	}

	private int pieceAtSquare(int sqaure) {
		if (((wp >> sqaure) & 1) == 1)
			return wpn;
		if (((wr >> sqaure) & 1) == 1)
			return wrn;
		if (((wn >> sqaure) & 1) == 1)
			return wnn;
		if (((wb >> sqaure) & 1) == 1)
			return wbn;
		if (((wq >> sqaure) & 1) == 1)
			return wqn;
		if (((wk >> sqaure) & 1) == 1)
			return wkn;

		if (((bp >> sqaure) & 1) == 1)
			return bpn;
		if (((br >> sqaure) & 1) == 1)
			return brn;
		if (((bn >> sqaure) & 1) == 1)
			return bnn;
		if (((bb >> sqaure) & 1) == 1)
			return bbn;
		if (((bq >> sqaure) & 1) == 1)
			return bqn;
		if (((bk >> sqaure) & 1) == 1)
			return bkn;
		return 0;

	}

	public ArrayList<Integer> generateMovesNeo(boolean legal) {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		long attacks = 0L;
		if (isWhitesTurn) {
			moves.addAll(writeWhitePawnMovementNeo());
			moves.addAll(writeWhiteMovementNeo());
			attacks = getBlackMovement() | blackPawnAttackLeft()
					| blackPawnAttackRight();
			if (wkc && isSquareEmpty(5) && isSquareEmpty(6)
					&& !isAttackedbyBlack(4, attacks)
					&& !isAttackedbyBlack(5, attacks)
					&& !isAttackedbyBlack(6, attacks)) {
				moves.add(4061);
			}
			if (wqc && isSquareEmpty(2) && isSquareEmpty(3) && isSquareEmpty(1)
					&& !isAttackedbyBlack(4, attacks)
					&& !isAttackedbyBlack(3, attacks)
					&& !isAttackedbyBlack(2, attacks)) {
				moves.add(4021);

			}

		} else {
			moves.addAll(writeBlackPawnMovementNeo());
			moves.addAll(writeBlackMovementNeo());
			attacks = getWhiteMovement() | whitePawnAttackLeft()
					| whitePawnAttackRight();
			if (bkc && isSquareEmpty(61) && isSquareEmpty(62)
					&& !isAttackedbyWhite(60, attacks)
					&& !isAttackedbyWhite(61, attacks)
					&& !isAttackedbyWhite(62, attacks)) {

				moves.add(60621);
			}
			if (bqc && isSquareEmpty(58) && isSquareEmpty(59)
					&& isSquareEmpty(57) && !isAttackedbyWhite(60, attacks)
					&& !isAttackedbyWhite(58, attacks)
					&& !isAttackedbyWhite(59, attacks)) {
				moves.add(60581);
			}

		}
		if (legal) {
			return legalizeMovesNeo(moves);
		} else
			return moves;
	}

	private ArrayList<Integer> legalizeMovesNeo(ArrayList<Integer> moves) {
		long attacks = 0L;
		long pins = 0L;
		long kingMoves = 0L;
		int kingSquare;
		boolean check = false;
		boolean kingSaftey = false;
		long star = 0L;
		if (isWhitesTurn) {
			kingSquare = getWhiteKingSquare();
			attacks = getBlackMovement() | blackPawnAttackLeft()
					| blackPawnAttackRight();
			kingMoves = getKingMovement(kingSquare);
			star = getQueenMovement(getAll(), kingSquare)
					| getKnightMovement(kingSquare);
			check = isWhiteChecked(attacks);
			pins = getWhitePins();
		} else {
			kingSquare = getBlackKingSquare();
			attacks = getWhiteMovement() | whitePawnAttackLeft()
					| whitePawnAttackRight();
			kingMoves = getKingMovement(kingSquare);
			star = getQueenMovement(getAll(), kingSquare)
					| getKnightMovement(kingSquare);
			check = isBlackChecked(attacks);
			pins = getBlackPins();
		}
		if ((attacks & kingMoves) != 0)
			kingSaftey = true;
		for (int i = 0; i < moves.size(); i++) {
			int move = moves.get(i);
			int from = move / 1000 % 1000;
			if ((((pins >> from) & 1) == 1)
					|| (kingSaftey & from == kingSquare)) {// ||
				// Integer.parseInt(move.substring(4,
				// 5))
				// == 9)
				// {
				if ((kingSaftey & from == kingSquare)) {
					long to = 1L << move / 10 % 100;
					if ((attacks & to) == to) {
						moves.remove(i);
						i--;
					} else {
						move(move);
						if (isWhitesTurn && isBlackChecked()) {
							moves.remove(i);
							i--;
						} else if (!isWhitesTurn && isWhiteChecked()) {
							moves.remove(i);
							i--;
						}
						undo();
					}
				} else {
					move(move);
					if (isWhitesTurn && isBlackChecked()) {
						moves.remove(i);
						i--;
					} else if (!isWhitesTurn && isWhiteChecked()) {
						moves.remove(i);
						i--;
					}
					undo();
				}
			} else if (check) {
				long to = 1L << move / 10 % 100;
				if ((to & star) != 0) {
					move(move);
					if (isWhitesTurn && isBlackChecked()) {
						moves.remove(i);
						i--;
					} else if (!isWhitesTurn && isWhiteChecked()) {
						moves.remove(i);
						i--;
					}
					undo();
				} else {
					moves.remove(i);
					i--;

				}
			}

		}
		return moves;
	}

	private long getWhite() {
		return wr | wn | wb | wq | wk | wp;
	}

	public String translate(String string) {
		String move = String.format("%05d", Integer.parseInt(string));
		if (move == null)
			return "";
		String ans = "";
		int from = Integer.parseInt(move.substring(0, 2));
		int to = Integer.parseInt(move.substring(2, 4));
		ans += getSqaureName(from) + "" + getSqaureName(to) + "(" + move + ")";
		return ans;
	}

	public String translate(int string) {

		String move = "" + string;
		move = String.format("%05d", Integer.parseInt(move));
		if (move == null)
			return "";
		String ans = "";
		int from = Integer.parseInt(move.substring(0, 2));
		int to = Integer.parseInt(move.substring(2, 4));
		ans += getSqaureName(from) + "" + getSqaureName(to) + "(" + move + ")";
		return ans;
	}

	public static int translateToInt(String move) {

		if (move.equalsIgnoreCase("e1g1")) {
			return (04061);
		} else if (move.equalsIgnoreCase("e1c1")) {
			return (04021);
		} else if (move.equalsIgnoreCase("e8g8")) {
			return (60621);
		} else if (move.equalsIgnoreCase("e8c8")) {
			return (60581);
		}
		String[] letterSquares = { "a1", "b1", "c1", "d1", "e1", "f1", "g1",
				"h1", "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2", "a3",
				"b3", "c3", "d3", "e3", "f3", "g3", "h3", "a4", "b4", "c4",
				"d4", "e4", "f4", "g4", "h4", "a5", "b5", "c5", "d5", "e5",
				"f5", "g5", "h5", "a6", "b6", "c6", "d6", "e6", "f6", "g6",
				"h6", "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", "a8",
				"b8", "c8", "d8", "e8", "f8", "g8", "h8" };
		String temp = "" + move;
		String from = temp.substring(0, 2);
		String to = temp.substring(2, 4);
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
			return 1000 * Integer.parseInt(fromS) + 10 * Integer.parseInt(toS)
					+ 2;
		if (promote.equalsIgnoreCase("N"))
			return 1000 * Integer.parseInt(fromS) + 10 * Integer.parseInt(toS)
					+ 3;
		if (promote.equalsIgnoreCase("R"))
			return 1000 * Integer.parseInt(fromS) + 10 * Integer.parseInt(toS)
					+ 4;
		if (promote.equalsIgnoreCase("B"))
			return 1000 * Integer.parseInt(fromS) + 10 * Integer.parseInt(toS)
					+ 5;

		return 1000 * Integer.parseInt(fromS) + 10 * Integer.parseInt(toS) + 0;
	}

	private boolean isSquareEmpty(int square) {
		long all = getAll();
		if (((all >> square) & 1) == 1)
			return false;
		return true;
	}

	private String getSqaureName(int from) {
		return letterSquares[from];
	}

	private long getLong(String square) {
		for (int i = 0; i < letterSquares.length; i++) {
			if (square.equalsIgnoreCase(letterSquares[i])) {
				return squares[i];
			}
		}
		return 0L;
	}

	private long getBlack() {
		return br | bn | bb | bq | bk | bp;
	}

	private long getAll() {
		return wr | wn | wb | wq | wk | wp | br | bn | bb | bq | bk | bp;
	}

	private long getEmpty() {
		return ~(wr | wn | wb | wq | wk | wp | br | bn | bb | bq | bk | bp);
	}

	public String getFEN() {
		String ans = "";
		int count = 0;
		for (int i = 0; i < order.length; i++) {

			switch (pieceAtSquare(order[i])) {
			case wpn:
				if (count > 0)
					ans += count;
				ans += "P";
				count = 0;
				break;
			case wrn:
				if (count > 0)
					ans += count;
				ans += "R";
				count = 0;
				break;
			case wnn:
				if (count > 0)
					ans += count;
				ans += "N";
				count = 0;
				break;
			case wbn:
				if (count > 0)
					ans += count;
				ans += "B";
				count = 0;
				break;
			case wqn:
				if (count > 0)
					ans += count;
				ans += "Q";
				count = 0;
				break;
			case wkn:
				if (count > 0)
					ans += count;
				ans += "K";
				count = 0;
				break;
			case bpn:
				if (count > 0)
					ans += count;
				ans += "p";
				count = 0;
				break;
			case brn:
				if (count > 0)
					ans += count;
				ans += "r";
				count = 0;
				break;
			case bnn:
				if (count > 0)
					ans += count;
				ans += "n";
				count = 0;
				break;
			case bbn:
				if (count > 0)
					ans += count;
				ans += "b";
				count = 0;
				break;
			case bqn:
				if (count > 0)
					ans += count;
				ans += "q";
				count = 0;
				break;
			case bkn:
				if (count > 0)
					ans += count;
				ans += "k";
				count = 0;
				break;

			default:
				count++;
				break;
			}
			if (i % 8 == 7 && i < 60) {
				if (count > 0)
					ans += count;
				ans += "/";
				count = 0;
			}
		}
		if (count != 0)
			ans += count;
		if (isWhitesTurn)
			ans += " w ";
		else
			ans += " b ";
		if (wkc)
			ans += "K";
		if (wqc)
			ans += "Q";
		if (bkc)
			ans += "k";
		if (bqc)
			ans += "q";
		if (!(wkc || wqc || bkc || bqc)) {
			ans += "-";
		}

		ans += " " + enPassantTarget;
		ans += " " + fortyMoveCount + " " + (1 + moveCount) / 2;
		return ans;
	}

	private int getMoveCount() {
		ArrayList<Integer> moves = generateMovesNeo(true);
		return moves.size();
	}

	private int getMove(int i) {
		return moves[i];
	}

	private void updateHistory() {
		/*
		 * history[moveCount] = wp + "," + wr + "," + wn + "," + wb + "," + wq +
		 * "," + wk + "," + bp + "," + br + "," + bn + "," + bb + "," + bq + ","
		 * + bk + "," + wkc + "," + wqc + "," + bkc + "," + bqc + "," +
		 * enPassantTarget + "," + fortyMoveCount;
		 */
		history.add(wp);
		history.add(wr);
		history.add(wn);
		history.add(wb);
		history.add(wq);
		history.add(wk);
		history.add(bp);
		history.add(br);
		history.add(bn);
		history.add(bb);
		history.add(bq);
		history.add(bk);
		history.add((wkc ? 1L : 0L));
		history.add((wqc ? 1L : 0L));
		history.add((bkc ? 1L : 0L));
		history.add((bqc ? 1L : 0L));
		enPassantTargetHistory.add(enPassantTarget);
		history.add((long) fortyMoveCount);

	}

	private String reorder(String moves) {
		String newMoves = "";
		for (int i = 0; i < moves.length(); i += 5) {
			newMoves = moves.substring(i, i + 5) + newMoves;
		}
		return newMoves;
	}

	private boolean canContinue() {
		ArrayList<Integer> moves = generateMovesNeo(true);
		if (moves.size() == 0) {
			return false;
		}

		if (isWhitesTurn && isWhiteChecked() && moves.equals("")) {
			return false;
		}
		if (!isWhitesTurn && isBlackChecked() && moves.equals("")) {
			return false;
		}
		return true;
	}

	public int getValue() {
		boolean old = isWhitesTurn;
		isWhitesTurn = true;
		int whiteMoveCount = this.generateMovesNeo(true).size();

		isWhitesTurn = false;
		int blackMoveCount = this.generateMovesNeo(true).size();
		isWhitesTurn = old;

		if (isWhitesTurn && whiteMoveCount == 0) {
			if (isWhiteChecked()) {
				return -10000000 + moveCount;
			} else
				return 0;
		}
		if (!isWhitesTurn && blackMoveCount == 0) {
			if (isBlackChecked()) {
				return 10000000 - moveCount;
			} else
				return 0;
		}

		long blackMoves = getBlackMovement() | blackPawnAttackLeft()
				| blackPawnAttackRight();
		long whiteMoves = getWhiteMovement() | whitePawnAttackLeft()
				| whitePawnAttackRight();

		long whiteKingMoves = getKingMovement(getWhiteKingSquare());
		long wPins = getWhitePins();

		long blackKingMoves = getKingMovement(getBlackKingSquare());
		long bPins = getBlackPins();

		int wpc = countBits(wp);
		int bpc = countBits(bp);

		int wrc = countBits(wr);
		int brc = countBits(br);

		int wnc = countBits(wn);
		int bnc = countBits(bn);

		int wbc = countBits(wb);
		int bbc = countBits(bb);

		int wqc = countBits(wq);
		int bqc = countBits(bq);

		int whiteAttacks = countBits(whiteMoves & getBlack());
		int blackAttacks = countBits(blackMoves & getWhite());

		int whiteDefend = countBits(whiteMoves & getWhite());
		int blackDefend = countBits(blackMoves & getBlack());

		int whitePins = countBits(wPins);
		int blackPins = countBits(bPins);

		int whiteDoublePawn = 0;
		int blackDoublePawn = 0;

		int whiteKingAttack = countBits(whiteMoves & blackKingMoves);
		int blackKingAttack = countBits(blackMoves & whiteKingMoves);

		int whiteKingDefend = countBits(whiteMoves & whiteKingMoves);
		int blackKingDefend = countBits(blackMoves & blackKingMoves);

		int whiteAttackCenter = countBits(whiteMoves & CENTRE);
		int blackAttackCenter = countBits(blackMoves & CENTRE);

		int wrp = 0;
		int brp = 0;
		for (long temp = wr; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			wrp += rookPlacement[Long.numberOfTrailingZeros(temp)];
		}
		for (long temp = br; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			brp += rookPlacement[Long.numberOfTrailingZeros(temp)];
		}

		/*
		 * int wpp = 0; int bpp = 0; for (long temp = wp; temp != 0; temp -= 1L
		 * << Long .numberOfTrailingZeros(temp)) { wpp +=
		 * pawnPlacement[Long.numberOfTrailingZeros(temp)]; } for (long temp =
		 * bp; temp != 0; temp -= 1L << Long .numberOfTrailingZeros(temp)) { bpp
		 * += pawnPlacement[Long.numberOfTrailingZeros(temp)]; }
		 */

		int wbp = 0;
		int bbp = 0;
		for (long temp = wb; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			wbp += bishopPlacement[Long.numberOfTrailingZeros(temp)];
		}
		for (long temp = bb; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			bbp += bishopPlacement[Long.numberOfTrailingZeros(temp)];
		}

		int wnp = 0;
		int bnp = 0;
		for (long temp = wn; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			wnp += knightPlacement[Long.numberOfTrailingZeros(temp)];
		}
		for (long temp = bn; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			bnp += knightPlacement[Long.numberOfTrailingZeros(temp)];
		}

		for (int i = 0; i < rankMask.length; i++) {
			countBits(wp & rankMask[2]);
		}

		int wqp = 0;
		int bqp = 0;
		for (long temp = wq; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			wnp += queenPlacement[Long.numberOfTrailingZeros(temp)];
		}
		for (long temp = bq; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			bqp += queenPlacement[Long.numberOfTrailingZeros(temp)];
		}
		int wpp = 0;
		int bpp = 0;
		for (long temp = wp; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			wpp += whitePawnPlacement[Long.numberOfTrailingZeros(temp)];
		}
		for (long temp = bp; temp != 0; temp -= 1L << Long
				.numberOfTrailingZeros(temp)) {
			bpp += blackPawnPlacement[Long.numberOfTrailingZeros(temp)];
		}
		int wkp = 0;
		/*
		 * for (long temp = wk; temp != 0; temp -= 1L << Long
		 * .numberOfTrailingZeros(temp)) { wkp +=
		 * whiteKingPlacement[Long.numberOfTrailingZeros(temp)]; }
		 */

		for (int i = 0; i < rankMask.length; i++) {
			countBits(wp & rankMask[2]);
		}

		for (int i = 0; i < fileMask.length; i++) {
			if (countBits(fileMask[i] & wp) > 1)
				whiteDoublePawn++;
			if (countBits(fileMask[i] & bp) > 1)
				blackDoublePawn++;
		}

		int whitePawnMovement = 2 * countBits(wp & rankMask[1]);
		whitePawnMovement += 3 * countBits(wp & rankMask[2]);
		whitePawnMovement += 4 * countBits(wp & rankMask[3]);
		whitePawnMovement += 5 * countBits(wp & rankMask[4]);
		whitePawnMovement += 6 * countBits(wp & rankMask[5]);
		whitePawnMovement += 7 * countBits(wp & rankMask[6]);
		whitePawnMovement += 8 * countBits(wp & rankMask[7]);

		int blackPawnMovement = 2 * countBits(bp & rankMask[7]);
		blackPawnMovement += 3 * countBits(bp & rankMask[6]);
		blackPawnMovement += 4 * countBits(bp & rankMask[5]);
		blackPawnMovement += 5 * countBits(bp & rankMask[4]);
		blackPawnMovement += 6 * countBits(bp & rankMask[3]);
		blackPawnMovement += 7 * countBits(bp & rankMask[2]);
		blackPawnMovement += 8 * countBits(bp & rankMask[1]);

		// int wkc = countBits(wk); // int bkc = countBits(bk);
		int material = 900 * (wqc - bqc) + 500 * (wrc - brc) + 300
				* (wbc - bbc) + 250 * (wnc - bnc) + 100 * (wpc - bpc);
		int otherStuff = 15 * (whiteMoveCount - blackMoveCount) - 5
				* (whiteDoublePawn - blackDoublePawn) + 25
				* (whiteAttacks - blackAttacks) + 25
				* (whiteDefend - blackDefend) + 10 * (whitePins - blackPins)
				+ 35 * (whiteKingAttack - blackKingAttack) + 25
				* (whiteKingDefend - blackKingDefend) + 15
				* (whiteAttackCenter - blackAttackCenter) + 15
				* (whitePawnMovement - blackPawnMovement);
		otherStuff += (wrp - brp) + (wbp - bbp) + (wnp - bnp) + (wqp + bqp)
				+ wkp + (wpp - bpp);
		// if (isWhitesTurn)
		return 2 * material + otherStuff;
		// else
		// return -1 * (2 * material + otherStuff);

	}

	private int getSmallValue() {
		int wpc = countBits(wp);
		int bpc = countBits(bp);

		int wrc = countBits(wr);
		int brc = countBits(br);

		int wnc = countBits(wn);
		int bnc = countBits(bn);

		int wbc = countBits(wb);
		int bbc = countBits(bb);

		int wqc = countBits(wq);
		int bqc = countBits(bq);

		int material = 900 * (wqc - bqc) + 500 * (wrc - brc) + 300
				* (wbc - bbc) + 250 * (wnc - bnc) + 100 * (wpc - bpc);
		// if (isWhitesTurn)
		return material;
		// else
		// return -1 * (material);

	}

	private static void qs(String items[], int[] values, int left, int right) {
		int i, j;
		int xN, yN;
		String xM, yM;
		i = left;
		j = right;
		xN = values[(left + right) / 2];
		do {
			while ((values[i] < xN) && (i < right))
				i++;
			while ((xN < values[j]) && (j > left))
				j--;
			if (i <= j) {
				yN = values[i];
				values[i] = values[j];
				values[j] = yN;

				yM = items[i];
				items[i] = items[j];
				items[j] = yM;

				i++;
				j--;
			}
		} while (i <= j);
		if (left < j)
			qs(items, values, left, j);
		if (i < right)
			qs(items, values, i, right);
	}

	private ArrayList<Integer> sortMoves(ArrayList<Integer> moves) {
		return moves;
	}

	public boolean isWhitesTurn() {
		return isWhitesTurn;
	}

}
