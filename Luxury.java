/*
Luxury class
extends theater class and adjusts number of seats available

*/

public class Luxury extends Theatre {
	static final int MAX_SEATS = 20;
	static final int NUM_ROWS = 2;
	static final int NUM_COLS = 10;
	
	public Luxury (String showTime, int cost) {
		super(showTime, cost);
		super.createSeatList(NUM_ROWS, NUM_COLS);
	}
	
	public String toString () {
		String output = super.toString();
		return output;
	}
}
