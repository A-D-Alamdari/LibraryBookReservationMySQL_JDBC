import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class Books {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void createBook() {
		int bookID = 0;
		int lastBookID = 0;
		
		try {
			Statement stmt = DBConnection.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select max(book_id) from book");
			String bookIdOfTable = null;
			
			while (rs.next()) {
				bookIdOfTable = rs.getString(1);
				lastBookID = rs.getInt(1);
			}
			
			if (bookIdOfTable == null) {
				bookID = 10001;
			} else {
				lastBookID++;
				bookID = lastBookID;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		String book_name = null;
		int book_amount = 0;
		String genre = null;
		
			
		try {
			System.out.println("\n>> Enter the book's information");
			System.out.print("Enter name of the Book: ");
			book_name = br.readLine();
			System.out.print("Enter the amount of the book: ");
			book_amount = Integer.parseInt(br.readLine());
			System.out.print("Enter the Genre of the book: ");
			genre = br.readLine();
				
			if (book_amount < 1) {
				System.out.println("\n>>> ERROR! The Amount of the Book Can NOT be Less than 1. <<<");
			} else {
				Statement stmt0 = DBConnection.getConnection().createStatement();
				ResultSet rs0 = stmt0.executeQuery("select book_id from book where book_name = '" + book_name + "'");
				String bookDuplicateChecker = null;
				while (rs0.next()) {
					bookDuplicateChecker = rs0.getString(1);
				}
					
				if (bookDuplicateChecker != null) {
					System.out.println("\n>>> NOTIFICATION: The \"" + book_name + "\" is already exist in the library. <<<");
				} else {
					Statement stmt1 = DBConnection.getConnection().createStatement();
					ResultSet rs1 = stmt1.executeQuery("select genre_id from genre where genre = '" + genre + "'");
					String genreChecker = null;
					while (rs1.next()) {
						genreChecker = rs1.getString(1);
					}
					if (genreChecker == null) {
						System.out.println("\n>>> ERROR! Your entered genre is NOT valid. <<<");
					} else {
						PreparedStatement ps = DBConnection.getConnection().prepareStatement("insert into book values "
								+ "(?,?,?,?)");
							
						ps.setInt(1, bookID);
						ps.setString(2, book_name);
						ps.setInt(3, book_amount);
						ps.setString(4, genre);
								
						ps.executeUpdate();
							
						System.out.println("\n>>> NOTIFICATION: The \"" + book_name + "\" with "
								+ "Book ID " + bookID + " is added to Library. <<<");
							
						Statement stmt2 = DBConnection.getConnection().createStatement();
						stmt2.executeUpdate("insert into book_backup select * from book where book_id = " + bookID);
					}
				}
			}
		} catch (Exception e) {
				System.out.println("\n>>> ERROR! Please control you inputs. <<<");
		}		
	}
	
	
	
	public static void removeBook() {
		int currentStatusOfBook = -1;
		int bookID = 0;
		
		try {
			System.out.print("\nPlease Enter the Book ID: ");
			bookID = Integer.parseInt(br.readLine());
			
			Statement stmt0 = DBConnection.getConnection().createStatement();
			ResultSet bookTestRS = stmt0.executeQuery("select book_id from book where book_id = " + bookID);
			
			String bookTester = null;
			while (bookTestRS.next()) {
				bookTester = bookTestRS.getString(1);
			}
			

			if (bookTester == null) {
				System.out.println("\n>>> ERROR! There is NOT any book with Book ID as " + bookID + ". <<<");
			} else {
				PreparedStatement ps0 = DBConnection.getConnection().prepareStatement("select status "
						+ "from checkout where book_id =?");
				ps0.setInt(1, bookID);
				ResultSet rs = ps0.executeQuery();
				
				while (rs.next()) {
					currentStatusOfBook = rs.getInt(1);
				}
	
				if (currentStatusOfBook == 0) {
					System.out.println("\n>>> ERROR! The book is borrowd. We send a message to user to return it. <<<");
				} else {
					Statement stmt = DBConnection.getConnection().createStatement();
					stmt.executeUpdate("SET GLOBAL FOREIGN_KEY_CHECKS=0");
					
					PreparedStatement ps2 = DBConnection.getConnection().prepareStatement("delete from book "
							+ "where book_id = ?");
					ps2.setInt(1, bookID);
					ps2.executeUpdate();
					
					PreparedStatement ps3 = DBConnection.getConnection().prepareStatement("delete from book_backup "
							+ "where book_id = ?");
					ps3.setInt(1, bookID);
					ps3.executeUpdate();
					
					Statement stmt1 = DBConnection.getConnection().createStatement();
					stmt1.executeUpdate("SET GLOBAL FOREIGN_KEY_CHECKS=1");
	
					System.out.println("\n>>> NOTIFICATION: The Book with Book ID: \"" + bookID
							+ "\" is Removed from Book Library. <<<");
				}
			}
			
		} catch (Exception e) {
			System.out.println("\n>>> ERROR! <<<");
		}
	}
	
	
	
	public static void listBooks() {
		try {
			PreparedStatement ps = DBConnection.getConnection().prepareStatement("select * from book_backup");
			ResultSet rs = ps.executeQuery();
			
			PreparedStatement ps1 = DBConnection.getConnection().prepareStatement("select book_amount from book");
			ResultSet rs1 = ps1.executeQuery();
			
			System.out.println("\nThe list of Books inside the Library: \n");
			System.out.println("Book ID:  Book Name  -- Stock  --  Genre   --  Number of Currently Checked out");
			System.out.println("------------------------------------------------------------------------------");
			
			while (rs.next() && rs1.next()) {
				int borrowedNumber = rs.getInt(3)- rs1.getInt(1);
				System.out.println(rs.getInt(1) + ":    " + rs.getString(2) + "  --  " 
								+ rs.getInt(3) + "  --  " + rs.getString(4) + "   --  " +
								borrowedNumber);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void stockInfo() {
		try {
			System.out.print("\nPlease Enter the Genre ID: ");
			int genreID = Integer.parseInt(br.readLine());
			
			Statement stmt0 = DBConnection.getConnection().createStatement();
			ResultSet genreTestRS = stmt0.executeQuery("select genre_id from genre where genre_id = " + genreID);
			
			String genreTester = null;
			while (genreTestRS.next()) {
				genreTester = genreTestRS.getString(1);
			}
			
			if (genreTester == null) {
				System.out.println("\n>>> ERROR! There is NOT any Genre with Genre ID as " + genreID + ". <<<");
			} else {
				PreparedStatement ps0 = DBConnection.getConnection().prepareStatement("select genre from "
						+ "genre where genre_id=?");
				
				ps0.setInt(1, genreID);
				
				ResultSet rs0 = ps0.executeQuery();
				
				String genreName = null;
				
				while (rs0.next()) {
					genreName = rs0.getString(1);
				}
				
				PreparedStatement ps = DBConnection.getConnection().prepareStatement("select book_id,"
						+ " book_name, book_amount from book_backup where genre=?");
				
				ps.setString(1, genreName);
				ResultSet rs = ps.executeQuery();
				
				System.out.println("\nThe list of Books with specified ID");
				System.out.println("Book ID:  Book Name         --    Stock");
				System.out.println("--------------------------------------");
				while (rs.next()) {
					System.out.println(rs.getInt(1) + ":    " + rs.getString(2) + "  --  AMOUNT in LIBRARY: " 
									+ rs.getInt(3));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
