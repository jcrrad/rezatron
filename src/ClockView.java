import java.awt.Graphics;

import javax.swing.JPanel;

public class ClockView extends JPanel {
	long whiteTotal, blackTotal, current;
	boolean isWhite;
	ChessClock clock;

	public ClockView(ChessClock clock) {
		this.setSize(255, 255);
		this.clock = clock;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("White: " + clock.getWhite(), 20, 20);
		g.drawString("Black: " + clock.getBlack(), 20, 40);
	}
}
