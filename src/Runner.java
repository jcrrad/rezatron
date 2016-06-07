import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Runner {
	public static void main(String[] args) {
		/*
		 * UCI u = new UCI(); u.uciCommunication();
		 */
		/*
		 * for (int x = 0; x < 100; x++) { Timer timer = new Timer();
		 * timer.start(); Perft p = new Perft(); System.out.println(p.perft(5));
		 * timer.lap();
		 * 
		 * System.out.println(timer.displayLap()+"\n"); }
		 */
		// PerftSuiteTwo ps =new PerftSuiteTwo();
		Board b = new Board(
				"rnb1kbnr/pp1pp1pp/1qp2p2/8/Q1P5/N7/PP1PPPPP/1RB1KBNR b Kkq - 2 4");
		System.out.println(b);
		// e8f7(60530): 767
		// b.move(60530);
		System.out.println(b);
		Perft p = new Perft(b.getFEN());
		p.perft(1, 7);
	}

}