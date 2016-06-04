import java.util.ArrayList;

public class Timer {
	/*
	 * Split Time: This is your overall time at any given point in your run. For
	 * example, in a 4 mile race you might have split times of 7:00, 14:00,
	 * 21:00 and 28:00 at each mile. Your split time would be your overall time
	 * at each specified waypoint if your run ended there.
	 */

	/*
	 * Lap Time: This is your time in between splits. In the above example, each
	 * of your mile laps would have been 7:00 minutes. The lap time is how long
	 * it takes you to get from one split to the next. The clock then starts
	 * over on the next lap.
	 */
	private ArrayList<Long> times;
	private final long WEEK = 604800000L;
	private final long DAY = 86400000L;
	private final long HOUR = 3600000L;
	private final long MINUTE = 60000L;
	private final long SECOND = 1000L;

	public Timer() {
		times = new ArrayList<Long>();
	}

	public void start() {
		times.clear();
		times.add(System.currentTimeMillis());
	}

	public void stop() {
		times.add(System.currentTimeMillis());
	}

	public void lap() {
		times.add(System.currentTimeMillis());
	}

	public void split() {
		times.add(System.currentTimeMillis());
	}

	public String getTime() {
		long time = (times.get(times.size() - 1) - times.get(0));
		return displayTime(time);
	}

	public void clear() {
		times.clear();
	}

	public String displayLap() {
		String answer = "\n0:";
		for (int i = 1; i < times.size(); i++) {
			long time = (times.get(i) - times.get(i - 1));
			answer += "\n" + (i + ": " + displayTime(time));
		}
		return answer;
	}

	public String displaySplit() {
		String answer = "\n0:";
		for (int i = 1; i < times.size(); i++) {
			long time = (times.get(i) - times.get(0));
			answer += "\n" + (i + ": " + displayTime(time));
		}
		return answer;
	}

	public String displayLap(int i) {
		long time = (times.get(i) - times.get(i - 1));
		return (i + ": " + displayTime(time));
	}

	public String displayLastSplit() {
		int i = times.size() - 1;
		long time = (times.get(i) - times.get(0));
		return (i + ": " + displayTime(time));
	}

	public String displayLastLap() {
		int i = times.size() - 1;
		long time = (times.get(i) - times.get(i - 1));
		return (i + ": " + displayTime(time));
	}

	public String displaySplit(int i) {
		long time = (times.get(i) - times.get(0));
		return (i + ": " + displayTime(time));
	}

	public String displayTime(long time) {
		long fullTime = time;
		String ans = "";
		if (time >= WEEK) {
			if (time / WEEK == 1)
				ans += (time / WEEK) + " week";
			else
				ans += (time / WEEK) + " weeks";
			time = time % WEEK;
		}
		if (time >= DAY) {
			if (!ans.equals(""))
				ans += " ";
			if (time / DAY == 1)
				ans += (time / DAY) + " day";
			else
				ans += (time / DAY) + " days";
			time = time % DAY;
		}
		if (time >= HOUR) {
			if (!ans.equals(""))
				ans += " ";
			if (time / HOUR == 1)
				ans += (time / HOUR) + " hour";
			else
				ans += (time / HOUR) + " hours";
			time = time % HOUR;
		}
		if (time >= MINUTE) {
			if (!ans.equals(""))
				ans += " ";
			if (time / MINUTE == 1)
				ans += (time / MINUTE) + " minute";
			else
				ans += (time / MINUTE) + " minutes";
			time = time % MINUTE;
		}
		if (time >= SECOND) {
			if (!ans.equals(""))
				ans += " ";
			if (time / SECOND == 1)
				ans += (time / SECOND) + " second";
			else
				ans += (time / SECOND) + " seconds";
			time = time % SECOND;
		}
		if (!ans.equals(""))
			ans += " ";
		if (time == 1)
			ans += time + " millisecond (" + (fullTime) + ")";
		else
			ans += time + " milliseconds (" + (fullTime) + ")";
		return ans;

	}
}
