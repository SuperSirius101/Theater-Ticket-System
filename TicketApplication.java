/*
TicketApplication class
serves as ui 
provides user with menu options for creating, editing or deleting a ticket
uses text files to fill up theatre class along with available showtimes and seats
passes information to ddc classes about a theatre, showtime and booking of a seat(s).
stores location of tickets
displays a receipt of a ticket,and writes to text files to reflect created tickets
*/


import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;

public class TicketApplication {
    public static void main (String [] args) {

      Theatre [] standard = new Standard [7];
        Theatre [] premium = new Premium [5];
        Theatre [] luxury = new Luxury [3];
        Seats[] selectedSeats = new Seats[20];
        int totalTickets = 0;

        readFile(standard);
        readFile(premium);
        readFile(luxury);

        /**
         * Prompts the user for their menu choice and calls the appropriate method
         */
        int menuChoice = getMenuChoice();
        boolean exit = false;
        do {
            switch (menuChoice) {
                case 1:
                    if (totalTickets == 20) {
                        JOptionPane.showMessageDialog(null, "You are at the maximum allowed amount of tickets.");
                    } else {
                        addTicket(selectedSeats, standard, premium, luxury, totalTickets);
                        totalTickets++;
                    }
                    break;
                case 2:
                    if (removeTicket(selectedSeats, standard, premium, luxury, totalTickets)) {
                        totalTickets--;
                    }
                    break;
                case 3:
                    editTicket(selectedSeats, standard, premium, luxury);
                    break;
                case 4:
                    if (totalTickets == 0 ) {
                        JOptionPane.showMessageDialog(null, "You haven't selected any seats yet.");
                        break;
                    }
                    if(checkout(selectedSeats, standard, premium, luxury)) {
                        exit = true;
                    }
                    break;
                case 5:
                    exit = true;
                    break;
            }
            if (!exit)
                menuChoice = getMenuChoice();
        }
        while (!exit);

    }
/**
     * Prompts the user for their menu choice
     * @return: int - the selected menu choice
     */
   public static int getMenuChoice() {
      int choice;
      String menu = "1) Add Tickets\n"
                           + "2) Remove Tickets\n"
                           + "3) Edit Ticket Seat-Number\n"
                           + "4) Checkout\n"
                           + "5) Exit";

      do {
         try {
            choice = Integer.parseInt(JOptionPane.showInputDialog("Please select a menu option\n" + menu));
            }
         catch (NumberFormatException e) {
             choice = 0;
         }
         if (choice < 1 || choice > 5)
             JOptionPane.showMessageDialog(null, "Please enter a valid menu option");
     }
     while (choice < 1 || choice > 5);
     return choice;
   }
   
   /**
     * Creates a scanner object depending on the type of theatre array passed to it
     * @param: Theatre [] - theatre array containing the showtimes
     */
   public static void readFile (Theatre [] array) {
      String fileName = "";
      if (array instanceof Standard [])
         fileName = "TextFiles/Standard.txt";  
      if (array instanceof Premium [])
         fileName = "TextFiles/Premium.txt";
      if (array instanceof Luxury [])
         fileName = "TextFiles/Luxury.txt";
         
      try {
         Scanner in = new Scanner (new FileInputStream(new File(fileName)));
         processFile(in, array);
      }
      catch (FileNotFoundException e) {
         JOptionPane.showMessageDialog(null, "File Not Found!");
      }
   }
   
   /**
     * Processes the file and adds the contents to the theatre array
     * @param: Scanner - the file containing the theatre data
     * @param: Theatre [] - the array for the theatre type chosen
     * @return: int - a counter set to the size of 
     */
   public static void processFile (Scanner in, Theatre [] array) {
      int count = 0;
      String [] theatreTypes = {"Standard", "Premium", "Luxury"};
      while (in.hasNextLine()) {
         for (int i = 0; i < array.length; i++) {   
            if (in.hasNextInt()) {
               String showTime = in.next() + " " + in.next();
               int cost = in.nextInt();
               if (array instanceof Standard [])
                  array[count] = new Standard(showTime, cost);
               if (array instanceof Premium [])
                  array[count] = new Premium(showTime, cost);
               if (array instanceof Luxury [])
                  array[count] = new Luxury(showTime, cost);
               
               count++;
            } // if (in.hasNextInt()
            //Seats[][] temp;
            while (!in.hasNextInt() && in.hasNextLine()) {
               Seats [][] temp;
               String tOrF = "";
               if (array instanceof Standard []) {
                  for (int r = 0; r < Standard.NUM_ROWS; r++) {
                     for (int c = 0; c < Standard.NUM_COLS; c++) {
                        if (in.hasNextLine()) {
                           tOrF = in.next();
                           array[i].createSeat(r,c);
                           array[i].getSeat(r,c).setTheatreType(theatreTypes[0]);
                           array[i].getSeat(r,c).setTicketCost(array[i].getCost());
                           array[i].getSeat(r,c).setShowTime(array[i].getShowTime());
                        }
                        if (tOrF.equals("F,")) {
                           array[i].getSeat(r,c).setReserved(false);
                        } // if (tOrF = F
                        if (tOrF.equals("T,")) {
                           array[i].getSeat(r,c).setReserved(true);
                        } // if (tOrF = T
                     } // for c
                  } // for r
               } // if instanceof Standard
               
               if (array instanceof Premium []) {
                  for (int r = 0; r < Premium.NUM_ROWS; r++) {
                     for (int c = 0; c < Premium.NUM_COLS; c++) {
                        if (in.hasNextLine()) {
                           tOrF = in.next();
                           array[i].createSeat(r,c);
                           array[i].getSeat(r,c).setTheatreType(theatreTypes[1]);
                           array[i].getSeat(r,c).setTicketCost(array[i].getCost());
                           array[i].getSeat(r,c).setShowTime(array[i].getShowTime());
                        }
                        if (tOrF.equals("F,")) {
                           array[i].getSeat(r,c).setReserved(false);
                        } // if (tOrF = F
                        if (tOrF.equals("T,")) {
                           array[i].getSeat(r,c).setReserved(true);
                        } // if (tOrF = T
                     } // for c
                  } // for r
               } // if instanceof Premium
               
               if (array instanceof Luxury []) {
                  for (int r = 0; r < Luxury.NUM_ROWS; r++) {
                     for (int c = 0; c < Luxury.NUM_COLS; c++) {
                        if (in.hasNextLine())  {
                           tOrF = in.next();
                           array[i].createSeat(r,c);
                           array[i].getSeat(r,c).setTheatreType(theatreTypes[2]);
                           array[i].getSeat(r,c).setTicketCost(array[i].getCost());
                           array[i].getSeat(r,c).setShowTime(array[i].getShowTime());
                        }
                        if (tOrF.equals("F,")) {
                           array[i].getSeat(r,c).setReserved(false);
                        } // if (tOrF = F
                        if (tOrF.equals("T,")) {
                           array[i].getSeat(r,c).setReserved(true);
                        } // if (tOrF = T
                     } // for c
                  } // for r
               } // if instanceof Luxury
               
            } // while1
         } // while 2
      } // for i
      in.close();
   }//method
   
   /**
     * Promts the user for the movie they would like to see, the theatre type they would like to be in,
     * the showtime they would like to attend, and finally the seat they want to sit in. Adds the seat to
     * the reserved seats array and marks them as reserved.
     * @param: Seats [] - array containing the reserved seats
     *                Theatre [] - three theatre arrays, one for each type of theatre
     *                int - the number of seats added to the reservedSeats array
     */
   public static void addTicket(Seats[] seats, Theatre[] standard, Theatre[] premium, Theatre[] luxury, int index){
        int theater = 0;
        int showing = 0;
        String chosenSeat = null;
        String message = "";
        Seats currentSeat;
        boolean invalidInput = false;
        int count = 0;
        int row = -1;
        int col = -1;
        do { // Select which type of theatre
            invalidInput = false;
            boolean validChoice = false;
            int movieChoice = 0;
            do {
               try {
                  movieChoice = Integer.parseInt(JOptionPane.showInputDialog("Select a movie:\n1) Joker (2019)"));
                  if (movieChoice != 1) {
                     JOptionPane.showMessageDialog(null, "Please select a valid choice");
                     movieChoice = 0;
                  }
                  else
                     validChoice = true;
               }
               catch (NumberFormatException e) {
                  JOptionPane.showMessageDialog(null, "Please enter a valid value");
               }
            }
            while (!validChoice);
            
            theater = Integer.parseInt(JOptionPane.showInputDialog("\nPlease select a theatre type:\n1: Standard\n2: Premium\n3: Luxury\n"));
            switch (theater) {
                case 1:
                    message = "Please select a showtime:\n";
                    for (int i = 0; i < standard.length; i++) { // Get showtimes
                        message += (i + 1) + ": " + standard[i].getShowTime() + "\n";
                    }
                    do { // Select Showtime for theatre
                        invalidInput = false;
                        try {
                            showing = Integer.parseInt(JOptionPane.showInputDialog(message));
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Please enter a number.");
                            invalidInput = true;
                        }
                        if (!invalidInput && (showing < 1 || showing > 7)) {
                            JOptionPane.showMessageDialog(null, "Invalid showtime.");
                            invalidInput = true;
                        }
                    } while (invalidInput);
                    showing--;
                    message = "Please select a seat:\n\n\n";
                    for (int r = 0; r < Standard.NUM_ROWS; r++) {
                        for (int c = 0; c < Standard.NUM_COLS; c++) {
                            if (!standard[showing].getSeat(r, c).getReserved()) { // If not reserved
                                if (c == 9) {
                                    message += standard[showing].getSeat(r, c).getSeatID() + "\n";
                                } else {
                                    message += standard[showing].getSeat(r, c).getSeatID() + " - ";
                                }
                            } else {
                                if (c == 9) {
                                    message += "X\n";
                                } else {
                                    message += "X - ";
                                }
                            }
                        } // Cols
                    } // Rows
                    do {
                        invalidInput = false;
                        chosenSeat = JOptionPane.showInputDialog(message);
                        if (!validateSeatInput(chosenSeat)) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid seat.");
                            invalidInput = true;
                        } else {
                            invalidInput = true;
                            boolean exit = false;
                            for (int r = 0; r < Standard.NUM_ROWS; r++) {
                                for (int c = 0; c < Standard.NUM_COLS; c++) {
                                    if (chosenSeat.equalsIgnoreCase(standard[showing].getSeat(r, c).getSeatID())) {
                                        if (!standard[showing].getSeat(r, c).getReserved()) {
                                            invalidInput = false;
                                            standard[showing].getSeat(r, c).setReserved(true);
                                            seats[index] = standard[showing].getSeat(r, c);
                                            JOptionPane.showMessageDialog(null, "Seat is added");
                                            exit = true;
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Seat is currently reserved");
                                            exit = true;
                                        }
                                    }
                                }
                            }
                        }
                    }while(invalidInput);
                    break;
                case 2:
                    message = "Please select a showtime:\n";
                    for (int i = 0; i < premium.length; i++) { // Get showtimes
                        message += (i + 1) + ": " + premium[i].getShowTime() + "\n";
                    }
                    do { // Select Showtime for theatre
                        invalidInput = false;
                        try {
                            showing = Integer.parseInt(JOptionPane.showInputDialog(message));
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Please enter a number.");
                            invalidInput = true;
                        }
                        if (!invalidInput && (showing < 1 || showing > 7)) {
                            JOptionPane.showMessageDialog(null, "Invalid showtime.");
                            invalidInput = true;
                        }
                    } while (invalidInput);
                    showing--;
                    message = "Please select a seat:\n\n\n";
                      for (int r = 0; r < Premium.NUM_ROWS; r++) {
                        for (int c = 0; c < Premium.NUM_COLS; c++) {
                            if (!premium[showing].getSeat(r, c).getReserved()) { // If not reserved
                                if (c == 9) {
                                    message += premium[showing].getSeat(r, c).getSeatID() + "\n";
                                } else {
                                    message += premium[showing].getSeat(r, c).getSeatID() + " - ";
                                }
                            } else {
                                if (c == 9) {
                                    message += "X\n";
                                } else {
                                    message += "X - ";
                                }
                            }
                        }
                    }
                    do {
                        invalidInput = false;
                        chosenSeat = JOptionPane.showInputDialog(message);
                        if (!validateSeatInput(chosenSeat)) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid seat.");
                            invalidInput = true;
                        } else {
                            invalidInput = true;
                            boolean exit = false;
                            for (int r = 0; r < Premium.NUM_ROWS; r++) {
                                for (int c = 0; c < Premium.NUM_COLS ; c++) {
                                    if (chosenSeat.equalsIgnoreCase(premium[showing].getSeat(r, c).getSeatID())) {
                                        if (!premium[showing].getSeat(r, c).getReserved()) {
                                            invalidInput = false;
                                            premium[showing].getSeat(r, c).setReserved(true);
                                            seats[index] = premium[showing].getSeat(r, c);
                                            JOptionPane.showMessageDialog(null, "Seat is added");
                                            exit = true;
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Seat is currently reserved");
                                            exit = true;
                                        }
                                    }
                                }
                            }
                        }
                    }while(invalidInput);
                    break;
                case 3:
                    message = "Please select a showtime:\n";
                    for (int i = 0; i < luxury.length; i++) { // Get showtimes
                        message += (i + 1) + ": " + luxury[i].getShowTime() + "\n";
                    }
                    do { // Select Showtime for theatre
                        invalidInput = false;
                        try {
                            showing = Integer.parseInt(JOptionPane.showInputDialog(message));
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Please enter a number.");
                            invalidInput = true;
                        }
                        if (!invalidInput && (showing < 1 || showing > 7)) {
                            JOptionPane.showMessageDialog(null, "Invalid showtime.");
                            invalidInput = true;
                        }
                    } while (invalidInput);
                    showing--;
                    message = "Please select a seat:\n\n\n";
                    // puts together seat list
                    for (int r = 0; r < Luxury.NUM_ROWS; r++) {
                        for (int c = 0; c < Luxury.NUM_COLS; c++) {
                            if (!luxury[showing].getSeat(r, c).getReserved()) { // If not reserved
                                if (c == 9) {
                                    message += luxury[showing].getSeat(r, c).getSeatID() + "\n";
                                } else {
                                    message += luxury[showing].getSeat(r, c).getSeatID() + " - ";
                                }
                            } else {
                                if (c == 9) {
                                    message += "X\n";
                                } else {
                                    message += "X - ";
                                }
                            }
                        } // Cols
                    } // Rows
                    do {
                        invalidInput = false;
                        chosenSeat = JOptionPane.showInputDialog(message);
                        if (!validateSeatInput(chosenSeat)) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid seat.");
                            invalidInput = true;
                        } else {
                            invalidInput = true;
                            boolean exit = false;
                            for (int r = 0; r < Luxury.NUM_ROWS; r++) {
                                for (int c = 0; c < Luxury.NUM_COLS ; c++) {
                                    if (chosenSeat.equalsIgnoreCase(luxury[showing].getSeat(r, c).getSeatID())) {
                                        if (!luxury[showing].getSeat(r, c).getReserved()) {
                                            invalidInput = false;
                                            luxury[showing].getSeat(r, c).setReserved(true);
                                            seats[index] = luxury[showing].getSeat(r, c);
                                            JOptionPane.showMessageDialog(null, "Seat is added");
                                            exit = true;
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Seat is currently reserved");
                                            exit = true;
                                        }
                                    }
                                }
                            }
                        }
                    }while(invalidInput);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid theatre.");
                    invalidInput = true;
                    break;
            } }while (invalidInput);
    } 
   
   /**
     * Prompts the user for the movie they would like to edit, then the ticket. After a ticket is chosen,
     * they are prompted to chose a new theatre, showtime, and finally a seat. The ticket chosen to be
     * edited is marked as unreserved and replaced with the newley selected seat. that seat is then
     * marked as reserved.
     * @param: Seats [] - seats array containing the reserved seats
     *                Theatre [] - 3 theatre arrays, one for each theatre type
     */
   public static void editTicket(Seats[] seats, Theatre[] standard, Theatre[] premium, Theatre[] luxury) {
      String output = "";
      String input = "";
      Seats currentSeat;
      Seats testSeat;
      Seats[][] showtimeSeats;
      int option;
      int selectedIndex;
      boolean invalidInput = false;
      int count = 0;
      int row = -1;
      int col = -1;
      
      for (int i=0; i < seats.length; i++) { // Get seats that have been selected
         if (seats[i] != null) {
            output += (i+1) + ". Seat: " + seats[i].getSeatID() + "  | Theatre: " + seats[i].getTheatreType() + " | Showtime: " + seats[i].getShowTime() + "\n";
            count++;
         }
      }
      if (output != "") { // Ask for what seat/ticket they would like to edit
         boolean validChoice = false;
         int movieChoice = 0;
         do {
            try {
               movieChoice = Integer.parseInt(JOptionPane.showInputDialog("Select a movie:\n1) Joker (2019)"));
               if (movieChoice != 1) {
                  JOptionPane.showMessageDialog(null, "Please select a valid choice");
                  movieChoice = 0;
               }
               else
                  validChoice = true;
            }
            catch (NumberFormatException e) {
               JOptionPane.showMessageDialog(null, "Please enter a valid value");
            }
         }
         while (!validChoice);
            
         output += "\nPlease enter a seat you would like to change or anything else to return:";
         try {
            option = Integer.parseInt(JOptionPane.showInputDialog(output));
         } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a number.");
            return;
         }
         
         if (option < 1 || option > count) {
            JOptionPane.showMessageDialog(null, "Invalid ticket.");
            return;
         }
         selectedIndex = option-1;
         currentSeat = seats[option-1];
         
         do { // Select which type of theatre
            invalidInput = false;
            output = "Seat: " + seats[option - 1].getSeatID() + " | Theatre: " + seats[option - 1].getTheatreType() + " | Showtime: " + seats[option - 1].getShowTime();
            
            do {
               invalidInput = false;
               try {
                  option = Integer.parseInt(JOptionPane.showInputDialog(output + "\nPlease select a theatre type:\n1) Standard\n2) Premium\n3) Luxury\n"));
               } catch (NumberFormatException e) {
                  JOptionPane.showMessageDialog(null, "Please enter a number.");
                  invalidInput = true;
               }
               
               if (!invalidInput && (option < 1 || option > 3)) {
                  JOptionPane.showMessageDialog(null, "Invalid theatre.");
                  invalidInput = true;
               }
            } while (invalidInput);
            
            switch (option) {
               case 1: //Standard
                  output = "Please select a showtime:\n";
                  for (int i=0; i < standard.length; i++) { // Get showtimes
                     output += (i+1) + ": " + standard[i].getShowTime() + "\n";
                  }
                  do { // Select Showtime for theatre
                     invalidInput = false;
                     try {
                        option = Integer.parseInt(JOptionPane.showInputDialog(output));
                     } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Please enter a number.");
                        invalidInput = true;
                     }
                     
                     if (!invalidInput && (option < 1 || option > 7)) {
                        JOptionPane.showMessageDialog(null, "Invalid showtime.");
                        invalidInput = true;                  
                     }
                  } while(invalidInput);
                  showtimeSeats = standard[option-1].getSeatList(); // Start to get seats for showtime
                  output = "Please select a seat:\n\n";
                  for (int r=0; r < Standard.NUM_ROWS; r++) {
                     for (int c=0; c < Standard.NUM_COLS; c++) {
                        if (!showtimeSeats[r][c].getReserved()) { // If not reserved
                           if (c==9) {
                              output += showtimeSeats[r][c].getSeatID() + "\n";
                           } else {
                              output += showtimeSeats[r][c].getSeatID() + " - ";
                           }
                        } else {
                           if (c==9) {
                              output += "X\n";
                           } else {
                              output += "X - ";
                           }
                        }
                     } // Cols
                  } // Rows
                  output += "\n";
                  do {
                     invalidInput = false;
                     input = JOptionPane.showInputDialog(output);
                     
                     if (!validateSeatInput(input)) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid seat.");
                        invalidInput = true;
                     }
                     invalidInput = true;
                     for (int r=0; r < Standard.NUM_ROWS; r++) {
                        for (int c=0; c < Standard.NUM_COLS; c++) {
                           if (input.equalsIgnoreCase(showtimeSeats[r][c].getSeatID())){
                              if (!showtimeSeats[r][c].getReserved()) {
                                 invalidInput = false;
                                 row = r;
                                 col = c;
                              } else {
                                 JOptionPane.showMessageDialog(null, "Seat is currently reserved");
                              }
                           }
                        }
                     }
                     
                  } while(invalidInput);
                  if (row != -1 && col != -1) {
                     String theatreType = seats[selectedIndex].getTheatreType();
                     switch (theatreType.toUpperCase()) {
                        case "STANDARD":
                           for (int i=0; i < standard.length; i++) {
                              for (int r=0; r < Standard.NUM_ROWS; r++) {
                                 for (int c=0; c < Standard.NUM_COLS; c++) {
                                    if (seats[selectedIndex].isEquals(standard[i].getSeat(r,c))) {
                                       standard[i].getSeat(r,c).setReserved(false);
                                    }
                                 }
                              }
                           }
                           break;
                        case "PREMIUM":
                           for (int i=0; i < premium.length; i++) {
                              for (int r=0; r < Premium.NUM_ROWS; r++) {
                                 for (int c=0; c < Premium.NUM_COLS; c++) {
                                    if (seats[selectedIndex].isEquals(premium[i].getSeat(r,c))) {
                                       premium[i].getSeat(r,c).setReserved(false);
                                    }
                                 }
                              }
                           }
                           break;
                        case "LUXURY":
                           for (int i=0; i < luxury.length; i++) {
                              for (int r=0; r < Luxury.NUM_ROWS; r++) {
                                 for (int c=0; c < Luxury.NUM_COLS; c++) {
                                    if (seats[selectedIndex].isEquals(luxury[i].getSeat(r,c))) {
                                       luxury[i].getSeat(r,c).setReserved(false);
                                    }
                                 }
                              }
                           }
                           break;                       
                        default:
                           break;
                     }
                     seats[selectedIndex] = showtimeSeats[row][col];
                     standard[option - 1].getSeat(row,col).setReserved(true);
                     JOptionPane.showMessageDialog(null, "Seat has been edited.");
                  }
                  break;
               case 2:
                  output = "Please select a showtime:\n";
                  for (int i=0; i < premium.length; i++) { // Get showtimes
                     output += (i+1) + ": " + premium[i].getShowTime() + "\n";
                  }
                  do { // Select Showtime for theatre
                     invalidInput = false;
                     try {
                        option = Integer.parseInt(JOptionPane.showInputDialog(output));
                     } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Please enter a number.");
                        invalidInput = true;
                     }
                     
                     if (!invalidInput && (option < 1 || option > 5)) {
                        JOptionPane.showMessageDialog(null, "Invalid showtime.");
                        invalidInput = true;                  
                     }
                  } while(invalidInput);
                  
                  showtimeSeats = premium[option-1].getSeatList(); // Start to get seats for showtime
                  output = "Please select a seat:\n\n\n";
                  for (int r=0; r < Premium.NUM_ROWS; r++) {
                     for (int c=0; c < Premium.NUM_COLS; c++) {
                        if (!showtimeSeats[r][c].getReserved()) { // If not reserved
                           if (c==9) {
                              output += showtimeSeats[r][c].getSeatID() + "\n";
                           } else {
                              output += showtimeSeats[r][c].getSeatID() + " - ";
                           }
                        } else {
                           if (c==9) {
                              output += "X\n";
                           } else {
                              output += "X - ";
                           }
                        }
                     } // Cols
                  } // Rows
                  
                  do {
                     invalidInput = false;
                     input = JOptionPane.showInputDialog(output);
                     
                     if (!validateSeatInput(input)) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid seat.");
                        invalidInput = true;
                     }
                     invalidInput = true;
                     for (int r=0; r < Premium.NUM_ROWS; r++) { // Look to find selected seat
                        for (int c=0; c < Premium.NUM_COLS; c++) {
                           if (input.equalsIgnoreCase(showtimeSeats[r][c].getSeatID())){
                              if (!showtimeSeats[r][c].getReserved()) {
                                 invalidInput = false;
                                 row = r;
                                 col = c;
                              } else {
                                 JOptionPane.showMessageDialog(null, "Seat is currently reserved");
                              }
                           }
                        }
                     }
                     
                  } while(invalidInput);
                  if (row != -1 && col != -1) {
                     String theatreType = seats[selectedIndex].getTheatreType();
                     switch (theatreType.toUpperCase()) {
                        case "STANDARD":
                           for (int i=0; i < standard.length; i++) {
                              for (int r=0; r < Standard.NUM_ROWS; r++) {
                                 for (int c=0; c < Standard.NUM_COLS; c++) {
                                    if (seats[selectedIndex].isEquals(standard[i].getSeat(r,c))) {
                                       standard[i].getSeat(r,c).setReserved(false);
                                    }
                                 }
                              }
                           }
                           break;
                        case "PREMIUM":
                           for (int i=0; i < premium.length; i++) {
                              for (int r=0; r < Premium.NUM_ROWS; r++) {
                                 for (int c=0; c < Premium.NUM_COLS; c++) {
                                    if (seats[selectedIndex].isEquals(premium[i].getSeat(r,c))) {
                                       premium[i].getSeat(r,c).setReserved(false);
                                    }
                                 }
                              }
                           }
                           break;
                        case "LUXURY":
                           for (int i=0; i < luxury.length; i++) {
                              for (int r=0; r < Luxury.NUM_ROWS; r++) {
                                 for (int c=0; c < Luxury.NUM_COLS; c++) {
                                    if (seats[selectedIndex].isEquals(luxury[i].getSeat(r,c))) {
                                       luxury[i].getSeat(r,c).setReserved(false);
                                    }
                                 }
                              }
                           }
                           break;                       
                        default:
                           break;
                     }
                     seats[selectedIndex] = showtimeSeats[row][col];
                     standard[option - 1].getSeat(row,col).setReserved(true);
                     JOptionPane.showMessageDialog(null, "Seat has been edited.");
                  }
                  break;
               case 3:
                  output = "Please select a showtime:\n";
                  for (int i=0; i < luxury.length; i++) { // Get showtimes
                     output += (i+1) + ": " + luxury[i].getShowTime() + "\n";
                  }
                  do { // Select Showtime for theatre
                     invalidInput = false;
                     try {
                        option = Integer.parseInt(JOptionPane.showInputDialog(output));
                     } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Please enter a number.");
                        invalidInput = true;
                     }
                     
                     if (!invalidInput && (option < 1 || option > 3)) {
                        JOptionPane.showMessageDialog(null, "Invalid showtime.");
                        invalidInput = true;                  
                     }
                  } while(invalidInput);
                  
                  showtimeSeats = luxury[option-1].getSeatList(); // Start to get seats for showtime
                  output = "Please select a seat:\n\n\n";
                  for (int r=0; r < Luxury.NUM_ROWS; r++) {
                     for (int c=0; c < Luxury.NUM_COLS; c++) {
                        if (!showtimeSeats[r][c].getReserved()) { // If not reserved
                           if (c==9) {
                              output += showtimeSeats[r][c].getSeatID() + "\n";
                           } else {
                              output += showtimeSeats[r][c].getSeatID() + " - ";
                           }
                        } else {
                           if (c==9) {
                              output += "X\n";
                           } else {
                              output += "X - ";
                           }
                        }
                     } // Cols
                  } // Rows
                  
                  do {
                     invalidInput = false;
                     input = JOptionPane.showInputDialog(output);
                     
                     if (!validateSeatInput(input)) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid seat.");
                        invalidInput = true;
                     }
                     invalidInput = true;
                     for (int r=0; r < Luxury.NUM_ROWS; r++) {
                        for (int c=0; c < Luxury.NUM_COLS; c++) { // Compare input from user to seat.
                           if (input.equalsIgnoreCase(showtimeSeats[r][c].getSeatID())){
                              if (!showtimeSeats[r][c].getReserved()) {
                                 invalidInput = false;
                                 row = r;
                                 col = c;
                              } else {
                                 JOptionPane.showMessageDialog(null, "Seat is currently reserved");
                              }
                           }
                        }
                     }
                     
                  } while(invalidInput);
                  if (row != -1 && col != -1) {
                     String theatreType = seats[selectedIndex].getTheatreType();
                     switch (theatreType.toUpperCase()) {
                        case "STANDARD":
                           for (int i=0; i < standard.length; i++) {
                              for (int r=0; r < Standard.NUM_ROWS; r++) {
                                 for (int c=0; c < Standard.NUM_COLS; c++) {
                                    if (seats[selectedIndex].isEquals(standard[i].getSeat(r,c))) {
                                       standard[i].getSeat(r,c).setReserved(false);
                                    }
                                 }
                              }
                           }
                           break;
                        case "PREMIUM":
                           for (int i=0; i < premium.length; i++) {
                              for (int r=0; r < Premium.NUM_ROWS; r++) {
                                 for (int c=0; c < Premium.NUM_COLS; c++) {
                                    if (seats[selectedIndex].isEquals(premium[i].getSeat(r,c))) {
                                       premium[i].getSeat(r,c).setReserved(false);
                                    }
                                 }
                              }
                           }
                           break;
                        case "LUXURY":
                           for (int i=0; i < luxury.length; i++) {
                              for (int r=0; r < Luxury.NUM_ROWS; r++) {
                                 for (int c=0; c < Luxury.NUM_COLS; c++) {
                                    if (seats[selectedIndex].isEquals(luxury[i].getSeat(r,c))) {
                                       luxury[i].getSeat(r,c).setReserved(false);
                                    }
                                 }
                              }
                           }
                           break;                       
                        default:
                           break;
                     }
                     seats[selectedIndex] = showtimeSeats[row][col];
                     standard[option - 1].getSeat(row,col).setReserved(true);
                     JOptionPane.showMessageDialog(null, "Seat has been edited.");
                  }
                  break;
               default:
                  JOptionPane.showMessageDialog(null, "Invalid theatre.");
                  invalidInput = true;
                  break;
            }
            
         } while (invalidInput);
      } else {
         JOptionPane.showMessageDialog(null, "You haven't added any tickets yet.");
      }
   }
   
   /**
     * Prompts the user for a ticket number and removes it from the reservedSeats array. It then restructures
     * the array
     * @param: Seats [] - seat array that contains the reserved seats
     *                Theatre [] - 3 theatre arrays, one for each theatre type
     *                int - counter for the number of seats added to the reservedSeats array
     * @return: boolean - true if the ticket was removed, else false
     */
   public static boolean removeTicket (Seats[] seats, Theatre[] standard, Theatre[] premium, Theatre[] luxury, int counter) {
      boolean validSeat = false;
      String seatList = "";
      int choice = -1;
      counter = 0;
      for (int k = 0; k < seats.length; k++) {
         if (seats[k] != null) {
            seatList+= k + 1 + ") Seat: " + seats[k].getSeatID() + " | Theatre: " + seats[k].getTheatreType() + " | ShowTime: " + seats[k].getShowTime() + "\n";
            counter++;  
         }
      }
      
      do {
         if (counter != 0) {
            boolean validChoice = false;
            int movieChoice = 0;
            do {
               try {
                  movieChoice = Integer.parseInt(JOptionPane.showInputDialog("Select a movie:\n1) Joker (2019)"));
                  if (movieChoice != 1) {
                     JOptionPane.showMessageDialog(null, "Please select a valid choice");
                     movieChoice = 0;
                  }
                  else
                     validChoice = true;
               }
               catch (NumberFormatException e) {
                  JOptionPane.showMessageDialog(null, "Please enter a valid value");
               }
            }
            while (!validChoice);
         
            try {
               choice = Integer.parseInt(JOptionPane.showInputDialog("Please select the seat you would like to remove\n" + seatList)) - 1;
               if (choice >= 0 && choice < counter) {
                  for (int i = 0; i < seats.length; i++) {
                     if (seats[choice] != null && seats[i].isEquals(seats[choice])) {
                        validSeat = true;
                        if (seats[choice].getTheatreType().equalsIgnoreCase("Standard")) {
                           for (int j = 0; j < standard.length; j++) {
                              for (int r = 0; r < Standard.NUM_ROWS; r++) {
                                 for (int c = 0; c < Standard.NUM_COLS; c++) {
                                    if (standard[j].getSeat(r,c).isEquals(seats[choice])) {
                                       standard[j].getSeat(r,c).setReserved(false);
                                    } 
                                 } // for c
                              } // for r
                           } // for j
                        } // if standard
                        if (seats[choice].getTheatreType().equalsIgnoreCase("Premium")) {
                           for (int j = 0; j < premium.length; j++) {
                              for (int r = 0; r < Premium.NUM_ROWS; r++) {
                                 for (int c = 0; c < Premium.NUM_COLS; c++) {
                                    if (premium[j].getSeat(r,c).isEquals(seats[choice])) {
                                       premium[j].getSeat(r,c).setReserved(false);
                                    } 
                                 } // for c
                              } // for r
                           } // for j
                        } // if premium
                        if (seats[choice].getTheatreType().equalsIgnoreCase("Luxury")) {
                           for (int j = 0; j < luxury.length; j++) {
                              for (int r = 0; r < Luxury.NUM_ROWS; r++) {
                                 for (int c = 0; c < Luxury.NUM_COLS; c++) {
                                    if (luxury[j].getSeat(r,c).isEquals(seats[choice])) {
                                       luxury[j].getSeat(r,c).setReserved(false);
                                    } 
                                 } // for c
                              } // for r
                           } // for j
                        } // if luxury
                        seats[choice] = null;
                     }
                  }
               }
               else
                  JOptionPane.showMessageDialog(null, "Please select a valid number");
            }
            catch (NumberFormatException e) {
               JOptionPane.showMessageDialog(null, "Please select a valid number");
               choice = -1;
            }
         }
         else {
            JOptionPane.showMessageDialog(null, "No seats have been reserved");
            validSeat = true;
            return false;
         }
      }
      while (!validSeat);
      
      int index = choice;
      Seats temp = null;
      for (int x = choice; x <counter; x++) {
         seats[x] = seats[++index];
      }  
      return true;
   }
   
   /**
     * Prints a receipt with the movie title and each ticket reserved under that movie. The user
     * is then prompted whether they would like to checkout a final time. If yes, the seats in the
     * seats array is saved to their appropriate file by calling the saveToFile method. if no, the user
     * is returned to the main menu
     * @param: Seats [] - seats array containing the reserved seats
     *                Theatre [] - 3 theatre arrays, one for each theatre type
     * @return: boolean - true, if they want to checkout, else false
     */
   public static boolean checkout(Seats[] selectedSeats, Theatre[] standard, Theatre[] premium, Theatre[] luxury) {
      String output = "";
      double totalCost = 0;
      
      output = "Receipt:\n----------------------------------------------------------------------------\n                          Movie: Joker (2019)\n";
      for (int i=0; i < selectedSeats.length; i++) {
         if (selectedSeats[i] != null) {
            output += "Seat: " + selectedSeats[i].getSeatID() + "   | Theatre: " + selectedSeats[i].getTheatreType() + "  | Showtime: " + selectedSeats[i].getShowTime() + "\n";
            totalCost += selectedSeats[i].getTicketCost();
         }
      }
      output += "----------------------------------------------------------------------------\nTotal Cost: " + String.format("$%.2f", totalCost);
            
      int dialogResult = JOptionPane.showConfirmDialog(null, output + "\n\nWould you like to checkout?","Checkout", JOptionPane.YES_NO_OPTION);
      if (dialogResult == JOptionPane.YES_OPTION) {
         saveToFile(selectedSeats, standard, premium, luxury);
         return true;
      } 
      return false;
      
   }
   
   /**
     * Validates that the ticket id entered fits the format
     * @param: String - the seat id entered by the user
     * @return: boolean - returns true if valied, else false
     */
   public static boolean validateSeatInput(String seat) {
      if (seat == null) {
         return false;
      }
      boolean isValid = false;
      for (int r = 0; r < Seats.ROW_LIST.length; r++) {
         for (int c = 0; c < Seats.COL_LIST.length; c++) {
            String index = Seats.ROW_LIST[r] + Seats.COL_LIST[c];
            if (seat.equalsIgnoreCase(index)) 
               isValid = true;
         }
      }
      return isValid;

   }
   
   /**
     * Prepeares the arrays and calls the writeToFile with the appropriate array and seats array
     * @param: Seats [] - seats array that contains the reserved seats
     *                Theatre [] - theatre arrays for each of the theatre types
     */
   public static void saveToFile (Seats [] seats, Theatre [] standard, Theatre [] premium, Theatre [] luxury) {
      Seats [] standardSeats = new Seats [20];
      Seats [] premiumSeats = new Seats [20];
      Seats [] luxurySeats = new Seats [20];
      String [] theatreTypes = {"Standard", "Premium", "Luxury"};
      int stanCount = 0;
      int premCount = 0;
      int luxCount = 0;
      for (int i = 0; i < seats.length; i++) {
         if (seats[i] != null && seats[i].getTheatreType().equalsIgnoreCase(theatreTypes[0]))
            standardSeats[stanCount++] = seats[i];
         if (seats[i] != null && seats[i].getTheatreType().equals(theatreTypes[1]))
            premiumSeats[premCount++] = seats[i];
         if (seats[i] != null && seats[i].getTheatreType().equals(theatreTypes[2]))
            luxurySeats[luxCount++] = seats[i];
      }
      
      String fileName = "";
      for (int x = 0; x < theatreTypes.length; x++) {
         fileName = "TextFiles/" + theatreTypes[x] + ".txt";
         try {
            PrintWriter out = new PrintWriter(new FileOutputStream(new File(fileName)));
            if (x == 0)
               writeToFile(out, standard, standardSeats);
            if (x == 1)
               writeToFile(out, premium, premiumSeats);
            if (x == 2)
               writeToFile(out, luxury, luxurySeats);
         }
         catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Could not find file!");
         }
      } // for x
   }
   
   /**
     * Overwrites the appropriate file with the new seats information stored in the theatre arrays
     * @param: PrintWriter - file that will be written to
     *                Theatre [] - theatre array that contains the seats
                        Seats [] - seats array that contains the reserved seats
     */
   public static void writeToFile (PrintWriter out, Theatre [] theatre, Seats [] seats) {
      String t = "T,";
      String f = "F,";
      for (int i = 0; i < theatre.length; i++) {
         if (theatre[i] != null) {
            out.println(theatre[i].getUnformattedShowTime());
            out.println(theatre[i].getCost());
            String row = "";
            Seats [][] temp;
            
            if (theatre instanceof Standard []) {
               row = "";
               for (int r = 0; r < Standard.NUM_ROWS; r++) {
                  for (int c = 0; c < Standard.NUM_COLS; c++) {
                     temp = theatre[i].getSeatList();
                     if (temp[r][c] == null || !temp[r][c].getReserved()) {
                        if (c < 9)
                           row += f + " ";
                        else
                           row += f;
                     } // if reserved
                     else {
                        if (c < 9)
                           row += t + " ";
                        else
                           row += t;
                     } // if not reserved
                  } // for c
                  if (i < theatre.length - 1) {
                     row += "\n";
                  }
                  else {
                     if (r < Standard.NUM_ROWS - 1) {
                        row+= "\n";
                     }
                  }
               } // for r
               out.print(row);
            } // instanceof Standard
            
            if (theatre instanceof Premium []) {
               row = "";
               for (int r = 0; r < Premium.NUM_ROWS; r++) {
                  for (int c = 0; c < Premium.NUM_COLS; c++) {
                     temp = theatre[i].getSeatList();
                     if (temp[r][c] == null || !temp[r][c].getReserved()) {
                        if (c < 9)
                           row += f + " ";
                        else
                           row += f;
                     } // if reserved
                     else {
                        if (c < 9)
                           row += t + " ";
                        else
                           row += t;
                     } // if not reserved
                  } // for c
                  if (i < theatre.length - 1) {
                     row += "\n";
                  }
                  else {
                     if (r < Premium.NUM_ROWS - 1) {
                        row+= "\n";
                     }
                  }
               } // for r
               out.print(row);
            } // instanceof Premium
   
            if (theatre instanceof Luxury []) {
               row = "";
               for (int r = 0; r < Luxury.NUM_ROWS; r++) {
                  for (int c = 0; c < Luxury.NUM_COLS; c++) {
                     temp = theatre[i].getSeatList();
                     if (temp[r][c] == null || !temp[r][c].getReserved()) {
                        if (c < 9)
                           row += f + " ";
                        else
                           row += f;
                     } // if reserved
                     else {
                        if (c < 9)
                           row += t + " ";
                        else
                           row += t;
                     } // if not reserved
                  } // for c
                  if (i < theatre.length - 1) {
                     row += "\n";
                  }
                  else {
                     if (r < Luxury.NUM_ROWS - 1) {
                        row+= "\n";
                     }
                  }
               } // for r
               out.print(row);
            } // instanceof Luxury
         } // if theatre[i] != null
      } // for i
      out.close();
   }
}
