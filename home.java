import java.util.Scanner;
import java.util.Random;

public class home {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to our flight reservation system!\nWhat would you like to do?: \n1.Flight Reservation\t\n2.Check Schedule\n3.Exit\t\t\t\t");
        System.out.print("= ");
        int choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1: {
                        UserInfo(input);
                        FlightDetail(input);
                        ReserveFlight();// 1st choices for flight reservation
                        break;
            }
            case 2: {
                        CheckSchedule();// 2nd choices for checking flight schedule
                        break;
            }
            case 3: {
                        System.out.println("Thank you for using our system. Goodbye!");
                        break;
            }
        }

    }

    public static void UserInfo(Scanner input){

        String name;
        String identificationNumber; //get many number
        String phone;
        String email;

        // This for getting user info        
        System.out.print("Enter Your Name (IC): ");
        name = input.nextLine(); //get user name
                                 //
        System.out.print("Enter identification number: ");
        identificationNumber = input.nextLine();

        System.out.print("Enter phone number: ");
        phone = input.nextLine();//get phone number
                            
        System.out.print("Enter email address: ");
        email = input.nextLine();//get email address
        
        System.out.print("Name: " +name+ " IDno.: " +identificationNumber+ " Phone: " +phone+ " Email: " +email );//test get the right thing or not
        System.out.print("\n");
    }


    // Currently used only for assigning to arrays
    public static void ReserveFlight() {

        char[][] plane1 = new char[4][20];
        char[] rows = {'A', 'B', 'C', 'D'};

        // Assign availability
        for (int i = 0; i < plane1.length; i++) {
            for (int j = 0; j < plane1[i].length; j++) {
                double rand = (Math.random());
                if (rand > 0.5)
                    plane1[i][j] = 'O';
                else
                    plane1[i][j] = 'X';
            }
        }

        System.out.println("\nSeat Map ('O' = Available, 'X' = Booked)\n");

        // Print column numbers
        System.out.print("    ");
        for (int d = 1; d <= plane1[0].length; d++) {
            System.out.printf("%02d ", d);
        }
        System.out.println();

        // Print seat rows
        for (int i = 0; i < plane1.length; i++) {

            if (i == 2) System.out.println(); // aisle space

            System.out.print(rows[i] + "   ");
            for (int j = 0; j < plane1[i].length; j++) {
                System.out.print(plane1[i][j] + "  ");
            }
            System.out.println();
        }
    }


    public static void CheckSchedule() {

    }

    public static void FlightDetail(Scanner input){//will get flight detail from user

        int month;
        int f_type;
        //    String [] local = {"x tahu lagi","dunno ahh"};
        //    String [] inter = {"INter ah"," Japan "}
        String types= "";

        System.out.print("Enter The month you want to Reserve (Enter number only. eg: JAN = 1,): ");
        month = input.nextInt(); // get flight month in number
        while (month <0 || month > 12){// if user put number more than 12 or less than january
            System.out.print("Error, not month more than December or less then January, Enter month again: ");
            month = input.nextInt();
        }
        System.out.println("Flight type:\n1.Local\n2.International");//get flight type from user
        System.out.print("= ");
        f_type = input.nextInt();

        while (f_type < 1 || f_type >2){
            if (f_type == 1){// local flight
                types = "Local";
            }
            else if (f_type ==2){//international flight
                types = "International";
            }
            else{
                System.out.print("Error! Enter again !!");   
            }
        }

        System.out.print("Choosen " + types);

        //    System.out.print("This is the currently available flight for " + types +  " flights.")
        //    System.out.print()

        //    System.out.print("Enter your destination: ");//get destination from user
        //    f_dest = input.nextLine();

    }
}
