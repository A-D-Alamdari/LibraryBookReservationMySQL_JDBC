import java.sql.*;

public class DBConnection {
	final static String url = "jdbc:mysql://localhost:3306/cs202";
	final static String user = "root";
	final static String password = "5050034868Ada*";
	
	public static Connection getConnection() {
		Connection myConn = null;
		
		try {
			myConn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			System.out.println("\n>>> ERROR! The Program Can NOT Connect to the Database. <<<");
		}
		return myConn;
	}


}
