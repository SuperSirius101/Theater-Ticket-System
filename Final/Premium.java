public class Premium extends Theatre {
	static final int MAX_SIZE = 30;
	static final int NUM_ROWS = 3;
	static final int NUM_COLS = 10;
	
	public Premium (String showTime, int cost) {
		super(showTime, cost);
		super.createSeatList(NUM_ROWS, NUM_COLS);
	}

	public String toString () {
		String output = super.toString();
		return output;
	}
}
