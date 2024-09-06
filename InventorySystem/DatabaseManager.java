package application;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
		private static final String DB_URL = "jdbc:derby:" + System.getProperty("user.home") + File.separator + "inventoryDB;create=true";
    
	    public static Connection connect() throws SQLException {
	        return DriverManager.getConnection(DB_URL);
	    }
	    public static void initializeDatabase() {
	        try (Connection conn = connect()) {
	            if (!isTableExists(conn, "Inventory")) {
	                Statement stmt = conn.createStatement();
	                stmt.executeUpdate(
	                    "CREATE TABLE Inventory (" +
	                    "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
	                    "ItemName VARCHAR(100), " +
	                    "Category VARCHAR(50), " +
	                    "Supplier VARCHAR(50), " +
	                    "Cost DOUBLE, " +
	                    "Price DOUBLE, " +
	                    "Quantity INT, " +
	                    "DateAdded DATE, " +
	                    "ExpiryDate DATE, " +
	                    "Description VARCHAR(255), " +
	                    "Location VARCHAR(100), " +
	                    "Barcode BLOB, " +
	                    "ReorderLevel INT, " +
	                    "Discount DOUBLE, " +
	                    "Status VARCHAR(50))");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    private static boolean isTableExists(Connection conn, String tableName) throws SQLException {
	        DatabaseMetaData meta = conn.getMetaData();
	        try (ResultSet res = meta.getTables(null, null, tableName.toUpperCase(), new String[] {"TABLE"})) {
	            return res.next();
	        }
	    }
}

