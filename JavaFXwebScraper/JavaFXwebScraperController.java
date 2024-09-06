package application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class JavaFXwebScraperController {
	@FXML private ListView<String> urlListView;
	@FXML private TextField urlField;    
    @FXML private TextArea htmlArea;
    @FXML private WebView webView;
    @FXML private TextField searchField;
    private WebEngine webEngine;
    private ObservableList<String> urlList;
    private FilteredList<String> filteredList; 

    @FXML
    public void initialize() {
        webEngine = webView.getEngine();
        urlList = FXCollections.observableArrayList();
        filteredList = new FilteredList<>(urlList, s -> true); 
        urlListView.setItems(filteredList);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(url -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return url.toLowerCase().contains(lowerCaseFilter);
            });
        });
        urlListView.setOnDragOver(this::handleDragOver);
        urlListView.setOnDragDropped(this::handleDragDropped);
    }
    @FXML
    private void importUrls() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import URLs");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(urlListView.getScene().getWindow());

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    urlList.add(line);
                }
            } catch (IOException e) {
                showAlert("Error", "Failed to import URLs: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
    @FXML
    private void saveConfig() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Settings");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data Files", "*.data"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                List<String> urlsToSave = new ArrayList<>(urlListView.getItems()); // Convert ObservableList to List
                Configuration config = new Configuration(urlsToSave);
                config.saveToFile(file);
            } catch (IOException e) {
                showAlert("Error", "Failed to save settings: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
    @FXML
    private void loadConfig() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Settings");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data Files", "*.data"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                Configuration config = Configuration.loadFromFile(file);
                List<String> urls = config.getUrls();
                urlList.clear();
                urlList.addAll(urls);
            } catch (IOException | ClassNotFoundException e) {
                showAlert("Error", "Failed to load settings: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void loadPage() {
        String url = urlField.getText();
        if (!url.isEmpty()) {
            try {
                Document doc = Jsoup.connect(url).get();
                String htmlContent = doc.outerHtml();
                htmlArea.setText(htmlContent);
                webEngine.loadContent(htmlContent); 
            } catch (IOException e) {
                htmlArea.setText("Error loading page: " + e.getMessage());
            }
        }
    }
    @FXML
    private void loadUrl() {
    	String selectedUrl = urlListView.getSelectionModel().getSelectedItem();
        try {
            Document doc = Jsoup.connect(selectedUrl).get();
            String htmlContent = doc.outerHtml();
            htmlArea.setText(htmlContent);
            webEngine.loadContent(htmlContent);
        } catch (IOException e) {
        	 showAlert("Error", "Failed to load URL: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles() || event.getDragboard().hasUrl()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }
    private void handleDragDropped(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            List<File> files = event.getDragboard().getFiles();
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        urlList.add(line);
                    }
                } catch (IOException e) {
                    showAlert("Error", "Failed to import URLs from dropped file: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        } else if (event.getDragboard().hasUrl()) {
            urlList.add(event.getDragboard().getUrl());
        }
        event.setDropCompleted(true);
        event.consume();
    }
    @FXML
    private void saveHtml() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save HTML File");
        fileChooser.setInitialFileName("page.html");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html"));
        File file = fileChooser.showSaveDialog(htmlArea.getScene().getWindow());
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(htmlArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void saveAllImages() {
        String url = urlField.getText();
        if (url.isEmpty()) {
            showAlert("Error", "URL is empty", Alert.AlertType.ERROR);
            return;
        }
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Directory to Save Images");
        File selectedDirectory = directoryChooser.showDialog(htmlArea.getScene().getWindow());
        if (selectedDirectory == null) {
            return; 
        }
        String folderName = sanitizeFolderName(url);
        File saveFolder = new File(selectedDirectory, folderName);
        if (!saveFolder.exists()) {
            saveFolder.mkdirs();
        }
        try {
            Document doc = Jsoup.connect(url).get();
            Elements imgElements = doc.select("img");

            for (Element imgElement : imgElements) {
                String imgUrl = imgElement.absUrl("src");
                saveImage(imgUrl, saveFolder);
            }
            showAlert("Success", "Images saved successfully in: " + saveFolder.getAbsolutePath(), Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Error", "Failed to save images: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @SuppressWarnings("deprecation")
	private void saveImage(String imageUrl, File saveFolder) throws IOException {
		URL url = new URL(imageUrl);
        String fileName = new File(url.getPath()).getName();
        File outputFile = new File(saveFolder, fileName);

        try (InputStream in = url.openStream()) {
            Files.copy(in, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    private String sanitizeFolderName(String url) {
        return url.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void printPage() {
        webEngine.print(null);
    }
    @FXML
    private void exitApp() {
        System.exit(0);
    }
}
