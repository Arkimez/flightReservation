import java.util.Scanner;

public class home {
  public static void main(String args[]) {
    Scanner input = new Scanner(System.in);

    System.out.println("Welcome to our flight reservation system!\nWhat would you like to do?: \n1.Flight Reservation\t\t2.Check Schedule\nn3.<>\t\t\t\t4<>");
    int choice = input.nextInt();

    switch (choice) { // This switch will call certain methods depending on the choice
      case 1: {
		ReserveFlight();
		break;
      }
      case 2: {
		CheckSchedule();
		break;
      }
     case 3: {
		System.out.println("3");
		break;
      }
     case 4: {
		System.out.println("4");
		break;
      }
    }

    input.close();
  }

  public static void ReserveFlight() {
  }

  public static void CheckSchedule() {
  }

}

