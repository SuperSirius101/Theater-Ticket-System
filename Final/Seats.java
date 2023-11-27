public class Seats {
   private boolean reserved;
   private String seatID;
   private String theatreType;
   private int ticketCost;
   private String showTime;
   private String unformattedShowTime;
   public static final String [] ROW_LIST = {"A", "B", "C", "D", "E"};
   public static final int [] COL_LIST = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
   
   public Seats (int row, int col) {
      String index = ROW_LIST [row] + COL_LIST [col];
      this.seatID = index;
    }
    
    public String getSeatID () { return this.seatID; }
    public String getTheatreType () { return this.theatreType; }
    public int getTicketCost () { return this.ticketCost; }
   public String getShowTime() { return this.showTime; }
   public String getUnformattedShowTime() { return this.unformattedShowTime; }    
   public boolean getReserved() { return this.reserved; }
    
    public void setTheatreType (String type) { this.theatreType = type; }
    public void setTicketCost (int cost) { this.ticketCost = cost; }
    
     public void setShowTime (String showTime) { this.showTime = showTime; }
    
   public void setReserved(boolean choice) {
      if (choice)
         this.reserved = choice;
      else
         this.reserved = choice;
   }
   
   public boolean isEquals(Seats temp) {
      if (this.seatID != temp.getSeatID())
         return false;
      if (this.theatreType != temp.getTheatreType())
         return false;
      if (this.showTime != temp.getShowTime())
         return false;
      return true;
   }
   
   }