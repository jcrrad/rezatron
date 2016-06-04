import java.util.ArrayList;

public class ChessClock {
	public static int type;
	long whiteTime;
	long blackTime;
	ArrayList<Long> white;
	ArrayList<Long> black;
	ArrayList<Long> currentTurn;
	boolean isWhite;
	boolean isPaused;
	public static final int FISCHER = 1;
	public static final int BRONSTEIN = 2;
	public static final int SIMPLE = 3;
	public static final int OVERTIME = 4;
	public static final int HOURGLASS = 4;
	private int delay;
	private int moveCount;
	private int timeAdd;

	/*
	 * Fischer—before a player has made his move, a specified time increment is
	 * added to his clock. Time can be accumulated, so if the player moves
	 * within the delay period, his remaining time actually increases. For
	 * example, if the delay time is five seconds, and a player has four seconds
	 * left on his clock, as soon as his opponent moves, he receives the
	 * increment and has nine seconds to make a move. If he takes two seconds to
	 * move, on the start of his next move he has twelve seconds. There is also
	 * a variant of this time control that adds the delay after a player makes
	 * his move (Fischer after), so the delay is added to the player's remaining
	 * time and is available for his next move. If however time runs out during
	 * his move, the game ends without the delay time being added. This variant
	 * prevents the player who is in time-trouble to take advantage of the
	 * extra-time.
	 */

	/*
	 * Bronstein delay—with the Bronstein timing method, the increment is always
	 * added after the move. But unlike Fischer, not always the maximum
	 * increment is added. If a player expends more than the specified
	 * increment, then the entire increment is added to the player's clock. But
	 * if a player has moved faster than the time increment, only the exact
	 * amount of time expended by the player is added. For example, if the delay
	 * is five seconds, the player has ten seconds left in his clock before his
	 * turn and during his turn he spends three seconds, after he presses the
	 * clock button to indicate the end of his turn, his clock increases by only
	 * three seconds (not five).
	 */

	/*
	 * Simple delay—when it becomes a player's turn to move, the clock waits for
	 * the delay period before starting to subtract from the player's remaining
	 * time. For example, if the delay is five seconds, the clock waits for five
	 * seconds before counting down. The time is not accumulated. If the player
	 * moves within the delay period, no time is subtracted from his remaining
	 * time. This time control is similar to a Bronstein with time added before
	 * the move.
	 */

	/*
	 * Overtime penalty—it is a sudden death time control, without any increment
	 * nor delay. The difference here is that when the time expires by dropping
	 * to zero, a flag is set, and the clock immediately starts counting up
	 * without limit. This time control applies to games where the amount of
	 * time used after the allowed time can be subtracted from the player's
	 * score as a penalty, such as Tournament or Club Scrabble.
	 */

	/*
	 * Hour Glass—a player loses in this time control when he allows the
	 * difference between both clocks to reach the specified total amount. For
	 * example, if the total is defined as one minute, both players start their
	 * clocks at thirty seconds. Every second the first player uses to think in
	 * his moves is subtracted from his clock and added to his opponent's clock.
	 * If he uses thirty seconds to move, the difference between the clocks
	 * reaches one minute, and the time flag falls to indicate that he loses by
	 * time. If he has used twenty nine seconds and then pushes the clock's
	 * button, he has one second left on his clock and his opponent has
	 * fifty-nine seconds.
	 */

	public ChessClock(double whiteMinutes, double blackMinutes) {
		whiteTime = (long) (whiteMinutes * 60000L);
		blackTime = (long) (blackMinutes * 60000L);
		white = new ArrayList<Long>();
		black = new ArrayList<Long>();
		currentTurn = new ArrayList<Long>();
		isWhite = false;
		isPaused = true;
		type = SIMPLE;
		delay = 0;
		timeAdd = 0;
	}

	public String getWhite() {
		long sum = 0L;
		long sum2 = 0L;
		for (int i = 0; i < white.size(); i++) {
			sum += white.get(i);
		}
		if (isWhite && isPaused) {
			for (int i = 0; i < currentTurn.size(); i += 2) {
			}
		} else if (isWhite && !isPaused) {
			for (int i = 0; i < currentTurn.size() - 1; i += 2) {
				sum2 += currentTurn.get(i + 1) - currentTurn.get(i);
			}
			sum2 += (System.currentTimeMillis() - currentTurn.get(currentTurn
					.size() - 1));
		}
		long time = whiteTime - (sum + sum2);
		if (time >= 60000) {
			long min = time / 60000;
			time = time % 60000;
			long sec = time / 1000;
			time = time % 1000;
			return min + ":" + String.format("%02d", sec);
		} else {
			long min = time / 60000;
			time = time % 60000;
			long sec = time / 1000;
			time = time % 1000;
			return min + ":" + String.format("%02d", sec) + "."
					+ String.format("%03d", time);
		}
	}

	public long getTimeLeft() {
		long sum = 0L;
		long sum2 = 0L;
		long sum3 = 0L;
		long sum4 = 0L;
		for (int i = 0; i < white.size(); i++) {
			sum += white.get(i);
		}
		if (isWhite && isPaused) {
			for (int i = 0; i < currentTurn.size(); i += 2) {
			}
		} else if (isWhite && !isPaused) {
			for (int i = 0; i < currentTurn.size() - 1; i += 2) {
				sum2 += currentTurn.get(i + 1) - currentTurn.get(i);
			}
			sum2 += (System.currentTimeMillis() - currentTurn.get(currentTurn
					.size() - 1));
		}
		long time = whiteTime - (sum + sum2);
		for (int i = 0; i < black.size(); i++) {
			sum3 += black.get(i);
		}
		if (!isWhite && isPaused) {
			for (int i = 0; i < currentTurn.size(); i += 2) {
			}
		} else if (!isWhite && !isPaused) {
			for (int i = 0; i < currentTurn.size() - 1; i += 2) {
				sum4 += currentTurn.get(i + 1) - currentTurn.get(i);
			}
			sum4 += (System.currentTimeMillis() - currentTurn.get(currentTurn
					.size() - 1));
		}
		long time2 = whiteTime - (sum + sum2);
		if (time > time2) {
			return time2;
		} else
			return time;
	}

	public String getBlack() {
		long sum = 0L;
		long sum2 = 0L;
		for (int i = 0; i < black.size(); i++) {
			sum += black.get(i);
		}
		if (!isWhite && isPaused) {
			for (int i = 0; i < currentTurn.size(); i += 2) {
			}
		} else if (!isWhite && !isPaused) {
			for (int i = 0; i < currentTurn.size() - 1; i += 2) {
				sum2 += currentTurn.get(i + 1) - currentTurn.get(i);
			}
			sum2 += (System.currentTimeMillis() - currentTurn.get(currentTurn
					.size() - 1));
		}
		long time = blackTime - (sum + sum2);
		if (time >= 60000) {
			long min = time / 60000;
			time = time % 60000;
			long sec = time / 1000;
			time = time % 1000;
			return min + ":" + String.format("%02d", sec);
		} else {
			long min = time / 60000;
			time = time % 60000;
			long sec = time / 1000;
			time = time % 1000;
			return min + ":" + String.format("%02d", sec) + "."
					+ String.format("%03d", time);
		}
	}

	public void start() {
		currentTurn = new ArrayList<Long>();
		currentTurn.add(System.currentTimeMillis());
		isWhite = true;
		isPaused = false;
	}

	public void press() {
		if (isPaused) {
			isPaused = false;
			currentTurn.add(System.currentTimeMillis());
		} else {
			currentTurn.add(System.currentTimeMillis());
			if (isWhite) {
				// if it was whites turn add the time to whites clock. and then
				// start blacks clock
				long sum = 0L;
				for (int i = 0; i < currentTurn.size() - 1; i += 2) {
					sum += currentTurn.get(i + 1) - currentTurn.get(i);
				}
				white.add(sum);
			} else {
				// if it was whites turn add the time to whites clock. and then
				// start blacks clock
				long sum = 0L;
				for (int i = 0; i < currentTurn.size() - 1; i += 2) {
					sum += currentTurn.get(i + 1) - currentTurn.get(i);
				}
				black.add(sum);
			}
			currentTurn = new ArrayList<Long>();
			currentTurn.add(System.currentTimeMillis());
			isWhite = !isWhite;
			isPaused = false;
		}

	}

	public void pause() {
		isPaused = true;
	}

	public void unPasue() {
		isPaused = false;
	}
}
