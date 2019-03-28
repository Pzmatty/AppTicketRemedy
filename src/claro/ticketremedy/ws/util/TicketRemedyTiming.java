package claro.ticketremedy.ws.util;

public class TicketRemedyTiming {

	private int timing = 0;
	private long start = 0L;
	private long elapsed = 0L;

	public TicketRemedyTiming(int timing) {

		this.timing = timing;
	}

	public void startTimingInMillis() {

		if (timing != 0)
			start = System.currentTimeMillis();
	}

	public long stopTimingInMillis() {

		if (timing != 0)
			elapsed = System.currentTimeMillis() - start;
		return elapsed;
	}

	public long getElapsedTimeInMillis() {

		return elapsed;
	}

	public int getTiming() {

		return timing;
	}
}
