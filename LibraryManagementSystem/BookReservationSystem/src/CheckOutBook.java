import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckOutBook {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void checkOutBook() {
		int checkOutID;
		int lastCheckOutID = 0;
		
		try {
			Statement stmt = DBConnection.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select max(checkout_id) from checkout");
			String userIdOfTable = null;
			while (rs.next()) {
				userIdOfTable = rs.getString(1);
				lastCheckOutID = rs.getInt(1);
			}
			
			if (userIdOfTable == null) {
				checkOutID = 1001;
			} else {
				lastCheckOutID++;
				checkOutID = lastCheckOutID;
			}
			
			System.out.println("\nEnter the Book's information to Check Out:");
			System.out.print("Enter Book ID: ");
			int bookID = Integer.parseInt(br.readLine());
			System.out.print("Enter User ID: ");
			int userID = Integer.parseInt(br.readLine());
			
			
			Statement stmt1 = DBConnection.getConnection().createStatement();
			ResultSet rs1 = stmt1.executeQuery("select book_name from book where book_id = " + bookID);
			String isBookExist = null;
			while (rs1.next()) {
				isBookExist = rs1.getString(1);
			}
			
			Statement stmt2 = DBConnection.getConnection().createStatement();
			ResultSet rs2 = stmt2.executeQuery("select user_name from user where user_id = " + userID);
			String isUserExist = null;
			while (rs2.next()) {
				isUserExist = rs2.getString(1);
			}
			
			if (isBookExist == null) {
				System.out.println("\n>>> ERROR! The Entered Book with \"" + bookID + "\" is NOT Exist in the Library. <<<");
			} else {
				if (isUserExist == null) {
					System.out.println("\n>>> ERROR! The Entered User with \"" + userID + "\" is NOT Exist in the Users. <<<");
				} else {
					Statement stmt3 = DBConnection.getConnection().createStatement();
					ResultSet rs3 = stmt3.executeQuery("select book_amount from book where book_id = " + bookID);
					int amountOfBook = 0;
					while (rs3.next()) {
						amountOfBook = rs3.getInt(1);
					}
					
					if (amountOfBook == 0) {
						System.out.println("\n>>> NOTIFICATION: Currently there is NOT any borrowable amount of the "
								+ "book with Book ID \"" + bookID +"\" in the library");
					} else {
						PreparedStatement ps = DBConnection.getConnection().prepareStatement("insert into checkout"
								+ "(checkout_id, book_id, user_id, checkout_time) values("
								+  checkOutID + ",?,?, NOW())");
							
						ps.setInt(1, bookID);
						ps.setInt(2, userID);
							
						ps.executeUpdate();
							
						PreparedStatement ps2 = DBConnection.getConnection().prepareStatement("update book "
									+ "set book_amount = book_amount-1 where book_id = ?");
						
						ps2.setInt(1, bookID);
						ps2.executeUpdate();
							
						PreparedStatement ps3 = DBConnection.getConnection().prepareStatement("update checkout "
									+ "set status = 0 where book_id = ? and user_id = ?");
						
						ps3.setInt(1, bookID);
						ps3.setInt(2, userID);
						ps3.executeUpdate();
			
						System.out.println("\n>>> NOTIFICATION: The Book with Book ID: \"" + bookID + "\" is Checked Out by"
									+ " the User with User ID: \"" + userID + "\". <<<");
				}
			}
			}	
		} catch(Exception e) {
			System.out.println("\n>>> ERROR! The Entered Book/User ID is NOT Valid. <<<");
		}
	}
	
	
	
	public static void getCurrentlyBorrowedBook() {
		try {
			PreparedStatement ps = DBConnection.getConnection().prepareStatement("select checkout_id, book_id, "
					+ "user_id, checkout_time from checkout where return_date is null");
			
			ResultSet rs = ps.executeQuery();
			
			System.out.println("\nThe list of Currently Borrowed Books are: ");
			System.out.println("Book ID  -  Checkout ID  -  User ID  -  Checkout Date");
			System.out.println("-----------------------------------------------------");
			
			while(rs.next()) {
				System.out.println(rs.getInt(2) + "    -       " + rs.getInt(1) + "       -   " 
						+ rs.getInt(3) + "    -  " + rs.getString(4));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

