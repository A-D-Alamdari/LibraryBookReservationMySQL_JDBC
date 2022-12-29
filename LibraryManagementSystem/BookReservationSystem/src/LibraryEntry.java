import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LibraryEntry {
	
	public static void createMenu() throws NumberFormatException, IOException {
		System.out.println("\t+--------------------------------------+");
		System.out.println("\t|              Welcome to              |");
		System.out.println("\t|    Library Book Reservation System   |");
		System.out.println("\t+--------------------------------------+");
		
		
		try {
			while (true) {
				System.out.print("\n>>>>>>>>>>>>>>>>>>>>>>>> MENUE <<<<<<<<<<<<<<<<<<<<<<<<");
				printMenu();
				System.out.print("\nPlease Make a Selection: ");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				int input = 0;
				
				try {
					input = Integer.parseInt(br.readLine());

					if (input == 11) {
						System.out.println("\n>>> Program Terminated Successfully! <<<");
						break;
					} else if (input < 11 && input > 0) {
						switch (input) {
							case 1:
								User.addUser();
								break;
							case 2:
								User.removeUser();
								break;
							case 3:
								Books.createBook();;
								break;
							case 4:
								Books.removeBook();
								break;
							case 5:
								Books.listBooks();
								break;
							case 6:
								Books.stockInfo();
								break;
							case 7:
								CheckOutBook.getCurrentlyBorrowedBook();
								break;
							case 8:
								User.getUserHistory();
								break;
							case 9:
								CheckOutBook.checkOutBook();
								break;
							case 10:
								ReturnBook.returnBook();
								break;
						} 
					}else {
						System.out.println("\n>>> ERROR! Please Enter a Valid Value! <<<");
					}
				 
				} catch (Exception e) {
					System.out.println("\n>>> ERROR! Please Enter a Valid Value! <<<");
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	private static void printMenu() {
		System.out.println("\n\t1)  Add User");
		System.out.println("\t2)  Remove User");
		System.out.println("\t3)  Create Book");
		System.out.println("\t4)  Remove Book");
		System.out.println("\t5)  List all Books with Stock Information");
		System.out.println("\t6)  List all Books with a Specific Genre");
		System.out.println("\t7)  Get Currently Borrowed Books");
		System.out.println("\t8)  Get Currently Borrowed and Borrow History of a User");
		System.out.println("\t9)  Checkout Book");
		System.out.println("\t10) Return Book");
		System.out.println("\t11) Exit");
	}
}
