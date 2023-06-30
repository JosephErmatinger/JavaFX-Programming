package application;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WebsiteWatcherController {

    @FXML private Button openFileButton;
    @FXML private ListView<String> listView;
    @FXML private TableColumn<Website, Integer> ID;
	@FXML private TableColumn<Website, String> URL;
	@FXML private TableColumn<Website, String> HTML;
	@FXML private TableView<Website> Tableview;
	@FXML private ProgressBar progressbar;
	@FXML private WebView webview;
          private WebEngine engine;

    @FXML
    void openFile(ActionEvent event) { 	
    	Stage stage = (Stage) listView.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                List<String> lines = Files.readAllLines(selectedFile.toPath());
                ObservableList<String> urls = FXCollections.observableArrayList(lines);
                listView.setItems(urls);
                            	
                for (String url : urls) {
                    // Retrieve HTML for each website using JSoup
                    Document doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
                    String html = doc.html();
                    // Store HTML in the Derby database
                    try (Connection connection = DatabaseManager.getConnection()) {
                        String insertQuery = "INSERT INTO WebsiteWatcher (URL, HTML) VALUES (?, ?)";
                        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                            statement.setString(1, url);
                            statement.setString(2, html);
                            statement.executeUpdate();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }    
        }
        ObservableList<Website> data = DatabaseManager.retrieveDataForTableview();
        populateTableView(data);
    }
    @FXML
    void selectWebsite(MouseEvent event) {
    	 String url = listView.getSelectionModel().getSelectedItem();
    	 engine = webview.getEngine();
    	 engine.load(url);
    }  
    public void populateTableView(ObservableList<Website> data) {
        Tableview.setItems(data);
    }
    @FXML
    void initialize() { 
    	ID.setCellValueFactory(new PropertyValueFactory<Website, Integer>("ID"));
        URL.setCellValueFactory(new PropertyValueFactory<Website, String>("Url"));
        HTML.setCellValueFactory(new PropertyValueFactory<Website, String>("HTML"));
    }
}
