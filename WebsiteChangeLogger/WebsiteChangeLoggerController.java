package application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WebsiteChangeLoggerController {
	@FXML private WebView Webview;
	@FXML private TextFlow consoleTextFlow;
	@FXML private ListView<String> listView;
	@FXML private MenuBar menu;
	@FXML private MenuItem menuLoadUrls;
    @FXML private MenuItem menuStart;
    @FXML private MenuItem menuStop;
    @FXML private MenuItem menuReset;
    @FXML private MenuItem menuSaveConfig;
    @FXML private MenuItem menuLoadConfig;
    @FXML private MenuItem menuSettings;
    @FXML private MenuItem menuSave;
    @FXML private MenuItem menuPrint;
    @FXML private MenuItem menuBrowser;
    @FXML private MenuItem menuWebview;
    @FXML private TextField searchBar;
          private Thread websiteWatcherThread;
          private volatile boolean isWatcherRunning;
          private volatile long websiteWatcherInterval;
          private Map<String, String> previousHashes;
          private ObservableList<String> urlsList;
          
          
    @FXML
    private void initialize() {
        previousHashes = new HashMap<>();                 								
        urlsList = FXCollections.observableArrayList();   								
        listView.setItems(urlsList);                      								
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<String>() { 
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setStyle("-fx-background-color: lightseagreen;");
                        } else {
                            setText(item);
                            setStyle("-fx-background-color: lightseagreen;");
                        }
                    }
                };
            }
        });
    }
    private String getCurrentFormattedDate() {
        Date today = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(today);
    }
    public void setIntervalValue(long intervalValue) {
        websiteWatcherInterval = intervalValue;
    }
    private void printIntervalValue(long interval) {
        long minutes = interval / 60000;
        long seconds = (interval % 60000) / 1000;
        System.out.println("Stored Value for Thread.sleep(): " + minutes + " minutes " + seconds + " seconds");
    }
    @FXML
    private void startWebsiteWatcher() {
        if (!isWatcherRunning) {
            isWatcherRunning = true;												 
            if (urlsList.isEmpty()) {												   
                appendMessageToTextFlow("Please select a text file with urls, or drag a file/URLs onto the list.");
                return;
            }
        } else if (previousHashes.isEmpty()) {										  
                appendMessageToTextFlow("Please load URLs before starting the website watcher.");
                isWatcherRunning = false;
                return;
            }
            previousHashes = initializePreviousHashes(urlsList);                       
            websiteWatcherThread = new Thread(() -> {								    
                while (isWatcherRunning) {											    
                    for (String url : urlsList) {									  
                      System.out.println("Checking:" + url);
                        String html = fetchHtml(url);                                  
                        if (html != null) {											    
                            String currentHash = getHash(html);						    
                            if (!previousHashes.get(url).equals(currentHash)) { 	
                            	String storedHtml = DatabaseManager.getStoredHtmlFromDatabase(url);
                            	String diffHtml = Diff.performDiffAndHighlight(storedHtml, html);
                            	Platform.runLater(() -> appendMessageToTextFlow(url));
                            	Platform.runLater(() -> Webview.getEngine().loadContent(diffHtml));
                                previousHashes.put(url, currentHash);                               
                                String formattedDate = getCurrentFormattedDate();                             
                                DatabaseManager.updateWebsiteWatcherTable(url, html, currentHash, formattedDate);
                                EmailSender.sendEmail("raiders_1122@hotmail.com", url, formattedDate);
                            }
                        }
                    }
                    if (websiteWatcherInterval <= 0) {
                    	websiteWatcherInterval = 60000; 
                    }
                    try {
                    	printIntervalValue(websiteWatcherInterval);						 
                        Thread.sleep(websiteWatcherInterval);                            
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            websiteWatcherThread.start();												
        }
    @FXML
    private void stopWebsiteWatcher() {
        if (isWatcherRunning) {
            isWatcherRunning = false;
            if (websiteWatcherThread != null) {
                websiteWatcherThread.interrupt();
            }
        }
    }
    @FXML
    private void loadUrls() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select URL File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Task<Void> loadUrlsTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                	loadUrlsFromFile(file);                								
                    return null;
                }
            };
            loadUrlsTask.setOnFailed(event -> {
                loadUrlsTask.getException().printStackTrace();
            });
            new Thread(loadUrlsTask).start();             							 
        }
    }
    private void appendMessageToTextFlow(String url) {
        Hyperlink hyperlink = new Hyperlink(url);                                       
        hyperlink.setOnAction(e -> {                                  				     
        	try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException exception) {
                exception.printStackTrace();
            }
        });
        String formattedDate = getCurrentFormattedDate();
        Text dateText = new Text(formattedDate + "\n");									 
        consoleTextFlow.getChildren().addAll(hyperlink, dateText); 
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Website Change");
        alert.setHeaderText(url);
        alert.setContentText(formattedDate);
        alert.showAndWait();
    }
    private Map<String, String> initializePreviousHashes(ObservableList<String> urls) {
        Map<String, String> previousHashes = new HashMap<>();                    		
        for (String url : urls) {
            String html = fetchHtml(url);                                        		
            if (html != null) {                                                  		
                String currentHash = getHash(html);                              		
                previousHashes.put(url, currentHash);               
                String formattedDate = getCurrentFormattedDate();
                DatabaseManager.insertWebsiteWatcherRecords(url, html, currentHash, formattedDate);
            }   
        }
        return previousHashes;                                                   		
    }
    private String fetchHtml(String url) {
        try {
            Document doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
            return doc.html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getHash(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(content.getBytes());
            byte[] digest = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();                                             	
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    @FXML
    void dragOver(DragEvent event) {
    	Dragboard db = event.getDragboard();
        if (db.hasFiles() || db.hasUrl()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }
    @FXML
    void dragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {															
            File file = db.getFiles().get(0);                                           
            if (file.isFile()) {
                Task<Void> loadUrlsTask = new Task<Void>() {                            
                    @Override
                    protected Void call() throws Exception {
                        loadUrlsFromFile(file);                     					
                        return null;
                    }
                };
                loadUrlsTask.setOnFailed(taskEvent -> {
                    loadUrlsTask.getException().printStackTrace();  					
                });
                new Thread(loadUrlsTask).start();                   				
                success = true;
            }
        } else if (db.hasUrl()) {														
            String url = db.getUrl();                                                   
            Task<Void> fetchUrlTask = new Task<Void>() {								
                @Override
                protected Void call() throws Exception {
                    loadUrlsFromUrl(url);                           					
                    return null;
                }
            };
            fetchUrlTask.setOnFailed(taskEvent -> {
                fetchUrlTask.getException().printStackTrace();      					
            });
            new Thread(fetchUrlTask).start();                       				
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }
    private void loadUrlsFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;																
            while ((line = reader.readLine()) != null) {								
                String trimmedLine = line.trim();                                       
                if (!trimmedLine.isEmpty()) {											
                    Platform.runLater(() -> {											
                        urlsList.add(trimmedLine);										
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadUrlsFromUrl(String url) {
        Platform.runLater(() -> urlsList.add(url));   									
    }
    @FXML
    private void resetWebsiteWatcher(ActionEvent event) {
        stopWebsiteWatcher(); 						  									
        listView.getItems().clear();                  								
        consoleTextFlow.getChildren().clear();        									
        previousHashes.clear();
        Webview.getEngine().loadContent("");
    }
    private String extractTextFromTextFlow() {
        StringBuilder contentBuilder = new StringBuilder();
        for (Node node : consoleTextFlow.getChildren()) {
            if (node instanceof Hyperlink) {
                Hyperlink hyperlink = (Hyperlink) node;
                contentBuilder.append(hyperlink.getText()).append(" ");
            } else if (node instanceof Text) {
                Text textNode = (Text) node;
                contentBuilder.append(textNode.getText());
            }
        }
        return contentBuilder.toString();
    }
    @FXML
    private void saveToFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Website Changes to file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                String content = extractTextFromTextFlow();
                writer.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void printTextFlow(ActionEvent event) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(null)) {
            String content = extractTextFromTextFlow();
            Text textNode = new Text(content);
            boolean printed = printerJob.printPage(textNode);
            if (printed) {
                printerJob.endJob();
            } else {
                showPrintErrorAlert();
            }
        }
    }
    private void showPrintErrorAlert() {
        Alert alert = new Alert(AlertType.ERROR, "Error occurred while printing.", ButtonType.OK);
        alert.showAndWait();
    }
    @FXML
    private void openInBrowser(ActionEvent event) {
        String selectedUrl = listView.getSelectionModel().getSelectedItem();
        if (selectedUrl != null && !selectedUrl.isEmpty()) {
            try {
                Desktop.getDesktop().browse(new URI(selectedUrl));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void openInWebView(ActionEvent event) {
        String selectedUrl = listView.getSelectionModel().getSelectedItem();
        if (selectedUrl != null && !selectedUrl.isEmpty()) {
            WebView webView = new WebView();
            webView.getEngine().load(selectedUrl);
            Stage stage = new Stage();
            stage.setTitle("Web View");
            stage.setScene(new Scene(webView, 800, 600));
            stage.show();
        }
    }
    @FXML
    private void filterUrls(KeyEvent event) {
        String searchText = searchBar.getText().toLowerCase();                          
        if (searchText.isEmpty()) {                                                     
            listView.setItems(urlsList);                                               
        } else {
            FilteredList<String> filteredUrls = urlsList.filtered(url -> url.toLowerCase().contains(searchText)); 
            listView.setItems(filteredUrls);                                           
        }																				
    }
    @FXML
    private void saveConfigurationToFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Configuration");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Data Files", "*.dat"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            ArrayList<String> urlsArrayList = new ArrayList<>(urlsList);
            String textFlowContent = extractTextFromTextFlow();
            Configuration configuration = new Configuration(urlsArrayList, textFlowContent, previousHashes);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(configuration);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void loadConfigurationFromFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Configuration");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data Files", "*.dat"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Configuration configuration = (Configuration) ois.readObject();              
                urlsList = FXCollections.observableArrayList(configuration.getUrlsList());
                listView.setItems(urlsList);
                consoleTextFlow.getChildren().clear();
                updateTextFlowWithContent(configuration.getTextFlowContent());
                previousHashes = configuration.getPreviousHashes();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private void updateTextFlowWithContent(String content) {
        String[] lines = content.split("\n");											
        for (String line : lines) {
            int firstSpaceIndex = line.indexOf(" ");
            int lastSpaceIndex = line.lastIndexOf(" ");
            if (firstSpaceIndex >= 0 && firstSpaceIndex < lastSpaceIndex && lastSpaceIndex < line.length() - 1) {
                String url = line.substring(0, firstSpaceIndex);						
                String date = line.substring(firstSpaceIndex + 1);						
                Hyperlink hyperlink = new Hyperlink(url);                               
                hyperlink.setOnAction(e -> {                                 			
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (IOException | URISyntaxException exception) {
                        exception.printStackTrace();
                    }
                });
                Text dateText = new Text(date + "\n");									
                consoleTextFlow.getChildren().addAll(hyperlink, dateText);             
            }
        }
    }
    @FXML
    private void openSettingsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsWindow.fxml"));
            Parent root = loader.load();
            SettingsWindowController settingsController = loader.getController();
            settingsController.setWebsiteChangeLoggerController(this); 
            Stage settingsStage = new Stage();
            settingsStage.setTitle("Settings");
            settingsStage.setScene(new Scene(root));
            settingsStage.initModality(Modality.APPLICATION_MODAL);
            settingsController.initialize();
            settingsStage.setOnHiding(event -> {
                int intervalValue = settingsController.getIntervalTextFieldValue();
                System.out.println("Stored Value Change Logger: " + intervalValue);
                this.setIntervalValue(intervalValue);
            });
            settingsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}