public class Controller implements Runnable {
	View v;

	Controller(View v) {
		this.v = v;
	}

	@Override
	public void run() {
		while (true) {
			v.update();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("got interrupted!");
			}
		}

	}
}
