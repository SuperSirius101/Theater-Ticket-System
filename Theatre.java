/*
Theatre class
creates showtimes and sets price
and holds tickets 
*/

public abstract class Theatre {

   private String showTime;
   private String unformattedShowTime;
   private int cost;
   private Seats[][] seatList;
   
   public Theatre(String showTime, int cost) {
      int index = showTime.indexOf("0 ") - 2;
      String newString = "";
      for (int i = 0; i < showTime.length(); i++) {
         newString += showTime.charAt(i);
         if (i == index) {
            newString += ":";
         }
      }
      this.showTime = newString;
      this.cost = cost;
      this.unformattedShowTime = showTime;
   }
   
   public String getShowTime() { return this.showTime; }
   public String getUnformattedShowTime() { return this.unformattedShowTime; }
   public int getCost() { return this.cost; }
   public Seats [][] getSeatList () { return this.seatList; }
   public Seats getSeat (int row, int col) { return this.seatList[row][col]; }
   
   public void setShowTime (String showTime) { 
      int index = showTime.indexOf("00 ") - 1;
      String newString = "";
      for (int i = 0; i < showTime.length(); i++) {
         newString += showTime.charAt(i);
         if (i == index) {
            newString += ":";
         }
      }
      this.showTime = newString;
      this.unformattedShowTime = showTime;
    }
    
   public void setCost(int cost) {this.cost = cost; }
   public void createSeatList (int rows, int cols) { this.seatList = new Seats [rows][cols]; }
   public void createSeat (int row, int col) { this.seatList[row][col] = new Seats (row, col); }
   
   public String toString() {
      String output = "";
      return output;
   }
}
