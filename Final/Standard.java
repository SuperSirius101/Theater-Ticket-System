public class Standard extends Theatre {
	static final int MAX_SEATS = 50;
	static final int NUM_ROWS = 5;
	static final int NUM_COLS = 10;
	
	public Standard (String showTime, int cost) {
		super (showTime, cost);
		super.createSeatList(NUM_ROWS, NUM_COLS);
	}
	
	public String toString () {
      String output = super.toString();
      return output;
	}
}
