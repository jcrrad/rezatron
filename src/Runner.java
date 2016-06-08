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
		String fen = "6k1/1b4pp/1B1Q4/4p1P1/p3q3/2P3r1/P1P2PP1/R5K1 w - - 1 0";
		fen = "3r3r/1Q5p/p3q2k/3NBp1B/3p3n/5P2/PP4PP/4R2K w - - 1 0";
		fen = "6rk/2N5/1p1p3p/p2Pb2B/P1P1b3/1R4PK/5r2/6R1 b - -";
		MoveAlgorithm ma = new MoveAlgorithm(new Board(), 6);
		Timer t = new Timer();
		System.out.println("Starting");
		t.start();
		ma.alphaBeta(10);
		t.stop();
		System.out.println("That took " + t.getTime());

	}
}