package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
                    + "HTML CLOB,"
                    + "HASH VARCHAR(64) NOT NULL,"
                    + "DATE TIMESTAMP)";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableQuery);
                System.out.println("Table 'WebsiteWatcher' created successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertWebsiteWatcherRecords(String url, String html, String currentHash, String formattedDate) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String insertQuery = "INSERT INTO WebsiteWatcher (URL, HTML, HASH, DATE) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, url);
                statement.setString(2, html);
                statement.setString(3, currentHash);
                statement.setString(4, formattedDate);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateWebsiteWatcherTable(String url, String html, String currentHash, String formattedDate) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String updateQuery = "UPDATE WebsiteWatcher SET HTML = ?, HASH = ?, DATE = ? WHERE URL = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, html);
                statement.setString(2, currentHash);
                statement.setString(3, formattedDate);
                statement.setString(4, url);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getStoredHtmlFromDatabase(String url) {
        String storedHtml = null;
        try (Connection connection = DatabaseManager.getConnection()) {
            String selectQuery = "SELECT HTML FROM WebsiteWatcher WHERE URL = ?";
            try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
                statement.setString(1, url);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        storedHtml = resultSet.getString("HTML");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storedHtml;
    }
}
