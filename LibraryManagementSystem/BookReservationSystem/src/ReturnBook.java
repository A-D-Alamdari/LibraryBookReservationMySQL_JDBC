import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReturnBook {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void returnBook() {
		try {
			System.out.print("\nEnter the Book's Checkout ID: ");
			int checkoutID = Integer.parseInt(br.readLine());
			
			Statement stmt0 = DBConnection.getConnection().createStatement();
			ResultSet checkoutTestRS = stmt0.executeQuery("select checkout_id from checkout"
					+ " where checkout_id = " + checkoutID);
			
			String checkoutTester = null;
			while (checkoutTestRS.next()) {
				checkoutTester = checkoutTestRS.getString(1);
			}
			
			if (checkoutTester == null) {
				System.out.println("\n>>> ERROR! There is NOT any checkout with Checkout ID as " + checkoutID + ". <<<");
			} else {
				PreparedStatement ps0 = DBConnection.getConnection().prepareStatement("select return_date "
						+ "from checkout where checkout_id=?");
				ps0.setInt(1, checkoutID);
				ResultSet rs0 = ps0.executeQuery();
				
				String returnStatus = null;
				while (rs0.next()) {
					returnStatus = rs0.getString(1);
				}
				
				if (returnStatus == null) {
					PreparedStatement ps = DBConnection.getConnection().prepareStatement("update checkout "
							+ "set return_date = now() where checkout_id = ?");
					
					PreparedStatement ps1 = DBConnection.getConnection().prepareStatement("select book_id "
							+ "from checkout where checkout_id=?");
					ps.setInt(1, checkoutID);
					ps1.setInt(1, checkoutID);
					
					ResultSet rs = ps1.executeQuery();
					int bookID = 0;
					
					while (rs.next()) {
						bookID = rs.getInt(1);
					}
					ps.executeUpdate();
					
					PreparedStatement ps2 = DBConnection.getConnection().prepareStatement("update book "
							+ "set book_amount = book_amount+1 where book_id = ?");
					ps2.setInt(1, bookID);
					ps2.executeUpdate();
					
					PreparedStatement ps3 = DBConnection.getConnection().prepareStatement("update checkout "
							+ "set status = 1 where checkout_id = ?");
					ps3.setInt(1, checkoutID);
					ps3.executeUpdate();
					
					System.out.println("\n>>> NOTIFICATION: The Book with Book ID: \"" + bookID + "\" is Returned. <<<");
				} else {
					System.out.println("\n>>> ERROR: This Book is already Returned to the Library. <<<");
				}
			}
			
		} catch (Exception e) {
			System.out.println("\n>>> ERROR! <<<");
		}
		
	}
}
