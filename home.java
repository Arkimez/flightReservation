import java.util.Scanner;

public class home {
  public static void main(String args[]) {
    Scanner input = new Scanner(System.in);

    System.out.println("Welcome to our flight reservation system!\nWhat would you like to do?: \n1.Flight Reservation\t\t2.Check Schedule\nn3.<>\t\t\t\t4<>");
    int choice = input.nextInt();

    switch (choice) {
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

  // Currently used only for assigning to arrays
  public static void ReserveFlight() {
    char[][] plane1 = new char[4][20];

    // To assign availability of seats
    for (int i = 0; i < plane1.length; i++) {
      for (int j = 0; j < plane1[0].length; j++) {
	double rand = (Math.random());
	if (rand > 0.5)
	  plane1[i][j] = 'O';
	else
	  plane1[i][j] = 'X';
      }
    }

    for (int i = 0; i < plane1.length; i++) {
      for (int j = 0; j < plane1[0].length; j++)
	System.out.print(plane1[i][j] + "\s");

      // To display seats according to rows 
      System.out.println();
      if (i == 1)
	System.out.println();
    }

  }

  public static void CheckSchedule() {
  }

}

