package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class JavafxSqlServerController {
	@FXML private TextArea sqlTextArea;
    @FXML private TableView<ObservableList<String>> tableView;
    	  private Connection connection;

    public void initialize() {
        connectToDatabase();
    }
    private void connectToDatabase() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;instanceName=SQLEXPRESS;databaseName=pubs;integratedSecurity=true;encrypt=false;";
            connection = DriverManager.getConnection(url);
            System.out.println("Connection successful");
        } catch (Exception e) {
            showError("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void executeQuery() {
        String query = sqlTextArea.getText();
        runSelectQuery(query);
    }
    private void runSelectQuery(String query) {
        if (connection == null) {
            System.out.println("Connection is not initialized.");
            return;
        }
        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("Query executed successfully.");
            displayResults(resultSet); // ✅ This is required to update the TableView
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing query.");
        }
    }
    private void displayResults(ResultSet resultSet) throws SQLException {
        tableView.getColumns().clear();
        tableView.getItems().clear();
        
        ObservableList<TableColumn<ObservableList<String>, String>> columns = FXCollections.observableArrayList();
        
        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            final int columnIndex = i - 1;
            String columnName = resultSet.getMetaData().getColumnName(i);
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(columnIndex)));
            columns.add(column);
        }
        tableView.getColumns().addAll(columns);
        
        while (resultSet.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getString(i));
            }
            tableView.getItems().add(row);
        }
    }
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}	


