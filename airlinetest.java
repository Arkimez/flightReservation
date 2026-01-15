import java.io.*;
import java.util.*;

public class airlinetest {

  static Scanner sc = new Scanner(System.in);
  static Random rand = new Random();
  static char[] rows = {'A', 'B', 'C', 'D'};
  static char[][] planeSeats = new char[4][20]; // 4 rows x 20 seats
  static String[] flightOrigin = {
    "Kuala Lumpur International Airport (KLIA)", 
    "Kota Kinabalu International Airport",
    "Kuching International Airport"};
  static String[] flightDestination = {
    "UNITED ARAB EMIRATES",
    "SWITZERLAND",
    "JAPAN"};
  static double flightPrice = rand.nextDouble() * 9000 + 1000;
  static String flightNumber = "MY" + (rand.nextInt(900) + 100); // e.g., AK123

  public static void main(String[] args) {

    initSeats(); // initialize seat map

    // ----------------  Main Menu ----------------

    while (true) {
      clearScreen();
      System.out.println("===== AIRLINE RESERVATION SYSTEM =====");
      System.out.println("1. Register User");
      System.out.println("2. Log In");
      System.out.println("0. Exit");
      System.out.print("Choose an option: ");

      int choice = sc.nextInt();
      sc.nextLine();

      switch (choice) {
	case 1: {
		  User information = registerUser();
		  saveUser(information);
		  break;
	}
	case 2: {
		  login();
		  break;
	}
	case 0: {
		  System.out.println("Thank you for using our system. Have a great day!");
		  return;
	}
	default: System.out.println("Invalid option. Try again.");
      }
    }
  }

  // ---------------- User Registration ----------------
  public static User registerUser() {
    System.out.println("\n===== USER REGISTRATION =====");
    System.out.print("Full Name: ");
    String name = sc.nextLine();
    System.out.print("IC Number: ");
    String ic = sc.nextLine();
    System.out.print("Phone Number: ");
    String phone = sc.nextLine();
    System.out.print("Email Address: ");
    String email = sc.nextLine();

    int id = rand.nextInt(900) + 100;
    String userID = Integer.toString(id);
    System.out.println("Registration Complete! Your User ID: " + userID);

    return new User(name, ic, phone, email, userID);
  }

  // ---------------- Flight Reservation ----------------
  public static void flightReservation(User user) {

    // ----- Already booked -----
    try (BufferedReader br = new BufferedReader(new FileReader("bookings.txt"))) {
      String line;
      while((line = br.readLine()) != null) {
	String[] data = line.split("\\|");

	if (data[0].equals(user.userID)) {
	  clearScreen();
	  System.out.println("User already has a booking!\n");
	  return;
	}
      }
    }
    catch (IOException e) {
      clearScreen();
      System.out.println("Error reading booking data.");
      return;
    }

    // ----- Origin Selection -----
    System.out.println("\n===== SELECT ORIGIN =====");
    for (int i = 0; i < flightOrigin.length; i++) {
      System.out.println((i + 1) + ". " + flightOrigin[i]);
    }
    System.out.print("Choose Origin (1-" + flightOrigin.length + "): ");
    int originChoice = sc.nextInt() - 1;
    sc.nextLine();

    String selectedOrigin = flightOrigin[originChoice];

    // ----- Destination Selection -----
    System.out.println("\n===== SELECT DESTINATION =====");
    for (int i = 0; i < flightDestination.length; i++) {
      System.out.println((i + 1) + ". " + flightDestination[i]);
    }
    System.out.print("Choose Destination (1-" + flightDestination.length + "): ");
    int destChoice = sc.nextInt() - 1;
    sc.nextLine();

    String selectedDestination = flightDestination[destChoice];

    // ----- Date Selection (A, B, C) -----
    String[] dateOptions = new String[3];
    int day = 0;
    int year = 2026;
    for (int i = 0; i < 3; i++) {    
      int month = rand.nextInt(12) + 1;

      if(month == 2){
	day = rand.nextInt(27) + 1;
      }else{
	day = rand.nextInt(30) + 1;
      }
      dateOptions[i] = String.format("%04d-%02d-%02d", year, month, day);
    }

    System.out.println("\n===== SELECT FLIGHT DATE =====");
    System.out.println("A. " + dateOptions[0]);
    System.out.println("B. " + dateOptions[1]);
    System.out.println("C. " + dateOptions[2]);
    System.out.print("Choose Date (A/B/C): ");

    char dateChoice = sc.next().toUpperCase().charAt(0);
    sc.nextLine();

    String selectedDate;
    switch (dateChoice) {
      case 'A': selectedDate = dateOptions[0]; break;
      case 'B': selectedDate = dateOptions[1]; break;
      case 'C': selectedDate = dateOptions[2]; break;
      default:
		System.out.println("Invalid choice. Returning to menu.");
		return;
    }

    // ----- Determine Weather -----
    int weatherLevel = rand.nextInt(3) + 1;
    String weatherInfo;
    if (weatherLevel == 1)
      weatherInfo = "Sunny - Good to go";
    else if (weatherLevel == 2)
      weatherInfo = "Rainy/Cloudy - Waiting for pilot confirmation";
    else
      weatherInfo = "Heavy Storm - Flight may be delayed";

    // ----- Display Flight Details -----
    System.out.println("\n===== FLIGHT DETAILS =====");
    System.out.println("Flight Number : " + flightNumber);
    System.out.println("Origin        : " + selectedOrigin);
    System.out.println("Destination   : " + selectedDestination);
    System.out.println("Date          : " + selectedDate);
    System.out.printf("Price (RM)    : %.2f%n", flightPrice);
    System.out.println("Weather       : " + weatherInfo);

    // ----- Seat Display -----
    displaySeats();

    // ----- Passengers -----
    System.out.print("\nNumber of Passengers: ");
    int passengerCount = sc.nextInt();
    sc.nextLine();

    String[] bookedSeats = new String[passengerCount];

    for (int p = 0; p < passengerCount; p++) {
      while (true) {
	System.out.println("\nSelect seat for passenger " + (p + 1));
	System.out.print("Row (A-D): ");
	char chosenRow = sc.next().toUpperCase().charAt(0);
	System.out.print("Seat Number (1-20): ");
	int chosenSeat = sc.nextInt();
	sc.nextLine();

	int rowIndex = chosenRow - 'A';
	int colIndex = chosenSeat - 1;

	if (rowIndex < 0 || rowIndex >= 4 || colIndex < 0 || colIndex >= 20) {
	  System.out.println("Invalid seat.");
	} else if (planeSeats[rowIndex][colIndex] == 'X') {
	  System.out.println("Seat already booked.");
	} else {
	  planeSeats[rowIndex][colIndex] = 'X';
	  bookedSeats[p] = chosenRow + String.valueOf(chosenSeat);
	  System.out.println("Seat booked: " + bookedSeats[p]);
	  break;
	}
      }
    }

    // ----- Weather Confirmaation -----
    if (weatherLevel == 3) {
      System.out.print("\nWeather is severe. Proceed? (Y/N): ");
      char confirm = sc.next().toUpperCase().charAt(0);
      sc.nextLine();
      if (confirm != 'Y') {
	flightReservation(user);
	return;
      }
    }

    // ----- Payment -----
    double totalPrice = passengerCount * flightPrice;
    System.out.printf("Price (RM)    : %.2f%n", flightPrice);
    System.out.printf("Total Price (RM): %.2f%n", totalPrice);
    System.out.print("Proceed to payment? (Y/N): ");
    char pay = sc.next().toUpperCase().charAt(0);
    sc.nextLine();

    if (pay != 'Y') return;

    System.out.println("Payment successful!");

    saveBooking(user, bookedSeats, selectedDate, totalPrice, selectedOrigin, selectedDestination);
    System.out.println("\nFlight Reserved! Thank you, " + user.name + "!");
  }

  // ---------------- Cancel Reservation -----------
  public static void cancelReservation(User user) {
    boolean found = false;

    try (BufferedReader br = new BufferedReader(new FileReader("bookings.txt"));
	BufferedWriter bw = new BufferedWriter(new FileWriter("bookings.txt"))) {

      String line;
      while ((line = br.readLine()) != null) {
	String[] data = line.split("\\|");

	if (data[0].equals(user.userID)) {
	  found = true;
	  continue;
	}

	bw.write(line);
	bw.newLine();
      }
    }
    catch (IOException e) {
      clearScreen();
      System.out.println("Error processing bookings data.");
      return;
    }

    if (found) {
      clearScreen();
      System.out.println("Reservation for User ID " + user.userID + " has been canceled.");
    }
    else {
      clearScreen();
      System.out.println("No reservation found for User ID " + user.userID + ".");
    }
  }

  // ---------------- Display Seats ----------------
  public static void displaySeats() {
    System.out.println("\nSeat Map ('O'=Available, 'X'=Booked)");
    System.out.print("    ");
    for (int d = 1; d <= planeSeats[0].length; d++) System.out.printf("%02d ", d);
    System.out.println();
    for (int i = 0; i < planeSeats.length; i++) {
      System.out.print(rows[i] + "   ");
      for (int j = 0; j < planeSeats[i].length; j++) System.out.print(planeSeats[i][j] + "  ");
      System.out.println();
    }
  }

  // ---------------- Initialize Seats ----------------
  public static void initSeats() {
    for (int i = 0; i < planeSeats.length; i++) {
      for (int j = 0; j < planeSeats[i].length; j++) {
	planeSeats[i][j] = 'O';
      }
    }
  }

  // ---------------- Save booking data to bookings.txt ----------------
  public static void saveBooking(User user, String[] seatsBooked, String flightDate, 
      double totalPrice, String origin, String destination) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("bookings.txt", true))) {
      // Save everything in one line, separated by "|"
      String bookingLine = user.userID + "|" + 
	flightNumber + "|" + 
	origin + "|" + 
	destination + "|" + 
	flightDate + "|" + 
	String.join(", ", seatsBooked) + "|" + 
	String.format("%.2f", totalPrice);
      bw.write(bookingLine);
      bw.newLine();
    } 
    catch (IOException e) {
      clearScreen();
      System.out.println("Error reserving flight.");
      return;
    }
  }

  // ---------------- Save User data to users.txt ----------------
  public static void saveUser(User user) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
      // Save everything in one line, separated by "|"
      String userLine = user.userID + "|" + 
	user.name + "|" + 
	user.ic + "|" + 
	user.phone + "|" + 
	user.email;
      bw.write(userLine);
      bw.newLine();
    } 
    catch (IOException e) {
      clearScreen();
      System.out.println("Error saving booking.");
      return;
    }
  }

  // ---------------- Check Schedule ----------------
  public static void checkSchedule(User user) {
    String[] userData = new String[5];
    String[] bookingData = new String[7];

    bookingData[0] = "n/a";
    userData[0] = "n/a";
    String line;

    try (BufferedReader br = new BufferedReader(new FileReader("bookings.txt"))) {
      while ((line = br.readLine()) != null) {
	String[] data = line.split("\\|"); // split by |

	if (data[0].equals(user.userID))
	  bookingData = data;
      }
    }
    catch (IOException e) {
      clearScreen();
      System.out.println("Error reading booking data.");
    }

    try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
      while ((line = br.readLine()) != null) {
	String[] data = line.split("\\|"); // split by |

	if (data[0].equals(user.userID))
	  userData = data;
      }
    }
    catch (IOException e) {
      clearScreen();
      System.out.println("Error reading user data.");
    }

    if (userData[0].equals(user.userID) && bookingData[0].equals(user.userID)) {
      clearScreen();
      System.out.println("===== BOOKING FOUND =====");
      System.out.println("User ID      : " + userData[0]);
      System.out.println("Name         : " + userData[1]);
      System.out.println("Flight No    : " + bookingData[1]);
      System.out.println("Origin       : " + bookingData[2]);
      System.out.println("Destination  : " + bookingData[3]);
      System.out.println("Date         : " + bookingData[4]);
      System.out.println("Seats        : " + bookingData[5]);
      System.out.println("Total (RM)   : " + bookingData[6]);
      System.out.println();
    }
  }

  // ------------------ User menu -------------------
  public static void menu (User user) {
    while (true) {
      System.out.println("1. Flight Reservation");
      System.out.println("2. Cancel Reservation");
      System.out.println("3. Check Flight Schedule");
      System.out.println("0. Return To Login.");
      System.out.print("Choose an option: ");

      int choice = sc.nextInt();
      sc.nextLine();

      switch (choice) {
	case 1: flightReservation(user); break;
	case 2: cancelReservation(user); break;
	case 3: checkSchedule(user); break;
	case 0: return;
	default: {
		   System.out.println("Invalid choice.\n");
	}
      }
    }
  }

  // ---------------- User Login -----------------
  public static void login () {
    System.out.print("Enter your User ID: ");
    String userID = sc.nextLine();

    try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
      String line;
      while ((line = br.readLine()) != null) {
	String[] data = line.split("\\|");

	if (data[0].equals(userID)) {
	  User user = new User(data[1], data[0]);
	  clearScreen();
	  System.out.println("==== WELCOME " + user.name + " ====");
	  menu(user);
	  return;
	}
      }
    }
    catch (IOException e) {
      clearScreen();
      System.out.println("Error reading the user file.");
    }
  }

  // Clears screen; will be used frequently for clean UI
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}



// ---------------- User Class ----------------
class User {
  String name;
  String ic;
  String phone;
  String email;
  String userID;

  public User(String name, String ic, String phone, String email, String userID) {
    this.name = name;
    this.ic = ic;
    this.phone = phone;
    this.email = email;
    this.userID = userID;
  }

  public User(String name, String userID) { // overloaded method used for only getting user id.
    this.name = name;
    this.ic = "n/a";
    this.phone = "n/a";
    this.email = "n/a";
    this.userID = userID;
  }
}
