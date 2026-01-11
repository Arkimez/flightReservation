import java.io.*;
import java.util.*;

public class airline {

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

        // ---------------- PART 1: Main Menu ----------------

        while (true) {
            System.out.println("\n===== AIRLINE RESERVATION SYSTEM =====");
            System.out.println("1. Register & Flight Reservation");
            System.out.println("2. Check Flight Schedule");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    User Information = registerUser();
                    flightReservation(Information);
                    break;
                case 2:
                    checkSchedule();
                    break;
                case 3:
                    System.out.println("Thank you for using our system. Have a great day!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ---------------- PART 2: User Registration ----------------
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

        int userID = rand.nextInt(900) + 100;
        System.out.println("Registration Complete! Your User ID: " + userID);

        return new User(name, ic, phone, email, userID);
    }

    // ---------------- PART 3: Flight Reservation ----------------
    public static void flightReservation(User user) {

    // ----- ORIGIN SELECTION -----
    System.out.println("\n===== SELECT ORIGIN =====");
    for (int i = 0; i < flightOrigin.length; i++) {
        System.out.println((i + 1) + ". " + flightOrigin[i]);
    }
    System.out.print("Choose Origin (1-" + flightOrigin.length + "): ");
    int originChoice = sc.nextInt() - 1;
    sc.nextLine();

    String selectedOrigin = flightOrigin[originChoice];

    // ----- DESTINATION SELECTION -----
    System.out.println("\n===== SELECT DESTINATION =====");
    for (int i = 0; i < flightDestination.length; i++) {
        System.out.println((i + 1) + ". " + flightDestination[i]);
    }
    System.out.print("Choose Destination (1-" + flightDestination.length + "): ");
    int destChoice = sc.nextInt() - 1;
    sc.nextLine();

    String selectedDestination = flightDestination[destChoice];

    // ----- DATE SELECTION (A, B, C) -----
    String[] dateOptions = new String[3];

    for (int i = 0; i < 3; i++) {
        int year = rand.nextInt(10) + 2026;
        int month = rand.nextInt(12) + 1;
        int day = rand.nextInt(28) + 1;
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
        case 'D': selectedDate = dateOptions[3]; break;
        default:
            System.out.println("Invalid choice. Returning to menu.");
            return;
    }

    // ----- WEATHER -----
    int weatherLevel = rand.nextInt(3) + 1;
    String weatherInfo;
    if (weatherLevel == 1)
        weatherInfo = "Sunny - Good to go";
    else if (weatherLevel == 2)
        weatherInfo = "Rainy/Cloudy - Waiting for pilot confirmation";
    else
        weatherInfo = "Heavy Storm - Flight may be delayed";

    // ----- DISPLAY FLIGHT DETAILS -----
    System.out.println("\n===== FLIGHT DETAILS =====");
    System.out.println("Flight Number : " + flightNumber);
    System.out.println("Origin        : " + selectedOrigin);
    System.out.println("Destination   : " + selectedDestination);
    System.out.println("Date          : " + selectedDate);
    System.out.println("Price (RM)    : " + flightPrice);
    System.out.println("Weather       : " + weatherInfo);

    // ----- SEAT DISPLAY -----
    displaySeats();

    // ----- PASSENGERS -----
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

    // ----- PART 4 WEATHER CONFIRMATION -----
    if (weatherLevel == 3) {
        System.out.print("\nWeather is severe. Proceed? (Y/N): ");
        char confirm = sc.next().toUpperCase().charAt(0);
        sc.nextLine();
        if (confirm != 'Y') {
            flightReservation(user);
            return;
        }
    }

    // ----- PART 5 PAYMENT -----
    double totalPrice = passengerCount * flightPrice;
    System.out.println("\nTotal Price (RM): " + totalPrice);
    System.out.print("Proceed to payment? (Y/N): ");
    char pay = sc.next().toUpperCase().charAt(0);
    sc.nextLine();

    if (pay != 'Y') return;

    System.out.println("Payment successful!");

    // ----- PART 6 SAVE -----
    saveBooking(user, bookedSeats, selectedDate, totalPrice);

    System.out.println("\nBooking Complete! Thank you, " + user.name + "!");
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

    // ---------------- Save Booking to File ----------------
    public static void saveBooking(User user, String[] seatsBooked, String flightDate, double totalPrice) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("bookings.txt", true))) {
            bw.write("UserID:" + user.userID);
            bw.write("Name:" + user.name);
            bw.write("FlightNumber:" + flightNumber);
            bw.write("Origin:" + flightOrigin);
            bw.write("Destination:" + flightDestination);
            bw.write("Date:" + flightDate);
            bw.write("Seats:" + String.join("-", seatsBooked));
            bw.write("TotalPrice: RM " + String.format("%.2f", totalPrice));
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving booking.");
        }
    }

    // ---------------- PART 7: Check Schedule ----------------
    public static void checkSchedule() {
        System.out.print("\nEnter your User ID to check bookings: ");
        int userID = sc.nextInt();
        sc.nextLine();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader("bookings.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("UserID:" + userID)) {
                    System.out.println("\n" + line);
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("No bookings found.");
        }

        if (!found) System.out.println("No bookings for this User ID.");
    }
}

// ---------------- User Class ----------------
class User {
    String name;
    String ic;
    String phone;
    String email;
    int userID;

    public User(String name, String ic, String phone, String email, int userID) {
        this.name = name;
        this.ic = ic;
        this.phone = phone;
        this.email = email;
        this.userID = userID;
    }
}
