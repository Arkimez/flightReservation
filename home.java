import java.util.Scanner;

public class home {
  public static void main(String args[]) {
    Scanner input = new Scanner(System.in);

    System.out.println("Welcome to our flight reservation system!\nWhat would you like to do?: \n1.<>\t\t2.<>\n3.<>\t\t4<>");
    int choice = input.nextInt();

    switch (choice) { // This switch will call certain methods depending on the choice
      case 1: {
		System.out.println("1");
		break;
      }
      case 2: {
		System.out.println("2");
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
}
