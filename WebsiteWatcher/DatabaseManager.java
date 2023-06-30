package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseManager {
	
	private static final String DB_URL = "jdbc:derby:WebsiteWatcher;create=true";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    
    public static void createTable() {
        try (Connection connection = getConnection()) {
            String createTableQuery = "CREATE TABLE WebsiteWatcher ("
                    + "ID INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "URL VARCHAR(255) NOT NULL,"
                    + "HTML CLOB)";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableQuery);
                System.out.println("Table 'WebsiteWatcher' created successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    static ObservableList<Website> retrieveDataForTableview() {
        ObservableList<Website> data = FXCollections.observableArrayList();

        try (Connection connection = DatabaseManager.getConnection()) {
            String selectQuery = "SELECT * FROM WebsiteWatcher";
            try (PreparedStatement statement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String URL = resultSet.getString("URL");
                    String HTML = resultSet.getString("HTML");

                    data.add(new Website(ID, URL, HTML));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
