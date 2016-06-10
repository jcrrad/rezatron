import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Runner {
	public static void main(String[] args) {

		// UCI u = new UCI();
		// UCI.uciCommunication();
		/*
		 * String fen = "r6r/3Q1p1p/pk4n1/1p4P1/5B2/P1N2P2/1PP5/4RK2 w - -";
		 * Board b = new Board(fen); System.out.println(b); // b.move(5140); //
		 * b.move(33250); System.out.println(b.getValue()); MoveAlgorithm ma =
		 * new MoveAlgorithm(b, 1); System.out.println(ma.alphaBetaNeo(4,
		 * Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0)); System.out.println(b);
		 */
		PerftSuiteTwo ps = new PerftSuiteTwo();
		Board b = new Board("r3k2r/1b4bq/8/8/8/8/7B/R3K2R w KQkq - 0 1;");

		Perft p = new Perft(b.getFEN());

		p.perft(1, 4);
		System.out.println(b.getFEN());
		System.out.println(p.divide(4));

		System.out.println(b);

	}
}