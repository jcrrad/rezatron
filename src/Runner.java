import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Runner {
	public static void main(String[] args) {

		UCI u = new UCI();
		UCI.uciCommunication();

		// PerftSuiteTwo ps = new PerftSuiteTwo();

		String fen = "3r2rk/5p1p/pp1p1PpQ/2p5/8/1P1b1R2/P5PP/3R2K1 w - -";
		fen = "rnbqkb1r/pppppppp/7P/5Q2/2B1P3/8/PPPP1P1P/RNB1K1NR b KQq - 3 8  ";
		fen = "1rbq1b1r/p1p2Q2/npkppN2/3NP3/2B5/8/PPPP1P1P/R1B2RK1 w - - 0 17 ";
		// fen =
		// "Q1bqkbr1/p1pppppp/7B/8/4P3/3Q1NP1/PPP2P1P/RN2KB1R b KQ - 0 10 ";
		Board b = new Board();
		System.out.println(b);
		System.out.println(b.getFEN());

		MoveAlgorithm ma = new MoveAlgorithm(b, 1);
		int i = 4;
		System.out.println(ma.iterativeDeepening(i));
		System.out.println("\n\n\n\n\n");
		System.out.println(ma.getMove(i));

	}
}