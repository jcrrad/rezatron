import javax.swing.JFrame;

public class View {
	public ClockView clock;

	public View(ChessClock c) {
		JFrame f = new JFrame("Rezatron - CHESS ENGINE");
		f.setSize(600, 600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clock = new ClockView(c);
		BoardView board = new BoardView();
		f.add(clock);
		// f.add(board);
		f.setVisible(true);
	}

	public void update() {
		clock.repaint();
	}
}
