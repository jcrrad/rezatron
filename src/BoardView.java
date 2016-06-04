import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class BoardView extends JPanel implements MouseListener {
	int width;
	int height;
	int centerWidth;
	int centerHeight;
	int min;
	int squareSize;

	public BoardView() {
		this.repaint();
		width = this.getWidth() - 1;
		height = this.getHeight() - 1;
		System.out.println("Width:  " + width);
		System.out.println("height:  " + height);
		addMouseListener(this);
	}

	public void paint(Graphics g) {
		width = this.getWidth() - 1;
		height = this.getHeight() - 1;
		if (width > height) {
			min = height;
		} else {
			min = width;
		}
		squareSize = (int) (.90 * min / 8);
		centerWidth = width / 2;
		centerHeight = height / 2;
		System.out.println("inside Width:  " + width);
		System.out.println("inside height:  " + height);
		Graphics2D g2d = (Graphics2D) g;
		Color tan = new Color(255, 222, 173);
		Color brown = new Color(156, 102, 31);
		Color[] colors = { brown, tan };
		g2d.setColor(brown);
		int count = 0;
		for (int i = 1; i < 9; i++) {
			g2d.setColor(colors[count]);
			count = 1 - count;
			g2d.fillRect(centerWidth - 4 * squareSize, centerHeight + (4 - i)
					* squareSize, squareSize, squareSize);
			g2d.setColor(colors[count]);
			count = 1 - count;
			g2d.fillRect(centerWidth - 3 * squareSize, centerHeight + (4 - i)
					* squareSize, squareSize, squareSize);
			g2d.setColor(colors[count]);
			count = 1 - count;
			g2d.fillRect(centerWidth - 2 * squareSize, centerHeight + (4 - i)
					* squareSize, squareSize, squareSize);
			g2d.setColor(colors[count]);
			count = 1 - count;
			g2d.fillRect(centerWidth - 1 * squareSize, centerHeight + (4 - i)
					* squareSize, squareSize, squareSize);
			g2d.setColor(colors[count]);
			count = 1 - count;
			g2d.fillRect(centerWidth, centerHeight + (4 - i) * squareSize,
					squareSize, squareSize);
			g2d.setColor(colors[count]);
			count = 1 - count;
			g2d.fillRect(centerWidth + 1 * squareSize, centerHeight + (4 - i)
					* squareSize, squareSize, squareSize);
			g2d.setColor(colors[count]);
			count = 1 - count;
			g2d.fillRect(centerWidth + 2 * squareSize, centerHeight + (4 - i)
					* squareSize, squareSize, squareSize);
			g2d.setColor(colors[count]);
			count = 1 - count;
			g2d.fillRect(centerWidth + 3 * squareSize, centerHeight + (4 - i)
					* squareSize, squareSize, squareSize);
			count = 1 - count;
		}

	}

	public void mouseClicked(MouseEvent e) {
		System.out.println(e);
		System.out.println("HELLO WORLD");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println(e);
		System.out.println("hello");

	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println(e);
		System.out.println("GOOD BYE");

	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(e);
		System.out.println("FUCK YOU WORLD");

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
