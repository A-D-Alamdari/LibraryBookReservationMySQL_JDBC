import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class User {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void addUser() {
		int userID;
		int lastUserID = 0;
		
		try {
			Statement stmt = DBConnection.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select max(user_id) from user");
			String userIdOfTable = null;
			while (rs.next()) {
				userIdOfTable = rs.getString(1);
				lastUserID = rs.getInt(1);
			}
			
			if (userIdOfTable == null) {
				userID = 1001;
			} else {
				lastUserID++;
				userID = lastUserID;
			}
			
			
			PreparedStatement ps = DBConnection.getConnection().prepareStatement("insert into user"
					+ " values(" + userID + ",?,?,?)");
			
			System.out.println("\nEnter the User's Information");
			System.out.print("Enter the Name of the User: ");
			String user_name = br.readLine();
			System.out.print("Enter the Email of User: ");
			String user_email = br.readLine();
			System.out.print("Enter the Address of the User: ");
			String user_address = br.readLine();
				
			Statement stmt1 = DBConnection.getConnection().createStatement();
			ResultSet rs1 = stmt1.executeQuery("select user_id from user where user_name = '" + user_name + "'");
			String userDuplicateChecker = null;
			while (rs1.next()) {
				userDuplicateChecker = rs1.getString(1);
			}
			
			if (userDuplicateChecker == null) {
				ps.setString(1, user_name);
				ps.setString(2, user_email);
				ps.setString(3, user_address);
					
				ps.executeUpdate();
				
				System.out.println("\n>>> NOTIFICATION: \"" + user_name + "\" with"
							+ " User ID " + userID + " is added to the Users. <<<" );
			} else {
				System.out.println("\n>>> This user already exists in the library members. <<<");
			}
		} catch(Exception e) {
			
		}
	}
	
	
	
	public static void removeUser() {
		try {
			System.out.print("\nPlease Enter the User ID to Remove: ");
			int userID = Integer.parseInt(br.readLine());
			
			Statement stmt = DBConnection.getConnection().createStatement();
			ResultSet userTestRS = stmt.executeQuery("select user_id from user where user_id = " + userID);
			
			String userTester = null;
			while (userTestRS.next()) {
				userTester = userTestRS.getString(1);
			}
			

			if (userTester == null) {
				System.out.println("\n>>> ERROR! There is NOT any user with User ID as " + userID + ". <<<");
			} else {
				PreparedStatement ps0 = DBConnection.getConnection().prepareStatement("select status "
						+ "from checkout where user_id =?");
				ps0.setInt(1, userID);
				ResultSet rs = ps0.executeQuery();
				int currentStatusOfUser = -1;
				while (rs.next()) {
					currentStatusOfUser = rs.getInt(1);
				}
			
				if (currentStatusOfUser == 0) {
					System.out.println("\n\t>>> ERROR! The User can NOT be REMOOVED. <<<");
					System.out.println(">>> The user " + userID + " has checkout book(s) from the Library. <<<");
					
				} else { 
					Statement stmt1 = DBConnection.getConnection().createStatement();
					stmt1.executeUpdate("SET GLOBAL FOREIGN_KEY_CHECKS=0");
					
					PreparedStatement ps = DBConnection.getConnection().prepareStatement("delete from user "
							+ "where user_id = ?");
					
					ps.setInt(1, userID);
					
					ps.executeUpdate();
					
					Statement stmt2 = DBConnection.getConnection().createStatement();
					stmt2.executeUpdate("SET GLOBAL FOREIGN_KEY_CHECKS=1");
					
					System.out.println("\n>>> NOTIFICATION: The User with User ID \"" + userID +"\" is Removed. <<<\n");
				}
			}
			
		} catch (Exception e) {
			System.out.println("\n>>> ERROR! <<<");
		}
	}
	
	
	
	public static void getUserHistory() {
		try {
			System.out.print("Please Enter the User ID: ");
			int userID = Integer.parseInt(br.readLine());
			
			Statement stmt = DBConnection.getConnection().createStatement();
			ResultSet userTestRS = stmt.executeQuery("select user_id from user where user_id = " + userID);
			
			String userTester = null;
			while (userTestRS.next()) {
				userTester = userTestRS.getString(1);
			}
			

			if (userTester == null) {
				System.out.println("\n>>> ERROR! There is NOT any user with specified USER ID. <<<");
			} else {
				PreparedStatement ps = DBConnection.getConnection().prepareStatement("select * from "
						+ "checkout where user_id=?");
				ps.setInt(1, userID);
				ResultSet rs = ps.executeQuery();
				
				System.out.println("\nCheckout ID // Book ID // Checkout Date // Return Date");
				System.out.println("------------------------------------------------------");
				
				while (rs.next()) {
					System.out.println(rs.getInt(1) + " // " + rs.getInt(2) 
							 + " // " + rs.getString(4) + " // " + rs.getString(5));
				}
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
