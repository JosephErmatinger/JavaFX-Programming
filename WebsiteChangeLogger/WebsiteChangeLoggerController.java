package application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WebsiteChangeLoggerController {
	@FXML private TextArea consoleTextArea;
          private static final String URL_FILE_PATH = "C:/Users/Joe/Downloads/urls.txt";
          private Thread websiteWatcherThread;
          private volatile boolean isWatcherRunning;

    @FXML
    private void initialize() {
        consoleTextArea.setStyle("-fx-font-family: monospace;");
    }
    @FXML
    private void startWebsiteWatcher() {
        if (!isWatcherRunning) {
            isWatcherRunning = true;
            consoleTextArea.clear();
            try {
                String[] urls = readUrlsFromFile(URL_FILE_PATH);
                Map<String, String> previousHashes = initializePreviousHashes(urls); // Initialize with initial hash values
                websiteWatcherThread = new Thread(() -> {
                    while (isWatcherRunning) {
                        for (String url : urls) {
                            String html = fetchHtml(url);
                            if (html != null) {
                                String currentHash = getHash(html);
                                if (!previousHashes.get(url).equals(currentHash)) { // Compare with the initial hash
                                    String message = "Website changed: " + url;
                                    appendMessageToConsole(message);
                                    previousHashes.put(url, currentHash);
                                }
                            }
                        }
                        try {
                            Thread.sleep(60000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                websiteWatcherThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    private void appendMessageToConsole(String message) {
    	Date today = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(today);
        Platform.runLater(() -> consoleTextArea.appendText(message + "\n" + formattedDate + "\n"));
    }
    private String[] readUrlsFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        StringBuilder urlsBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            urlsBuilder.append(line).append("\n");
        }
        reader.close();
        return urlsBuilder.toString().split("\n");
    }
    private String fetchHtml(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
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
    private Map<String, String> initializePreviousHashes(String[] urls) {
        Map<String, String> previousHashes = new HashMap<>();

        for (String url : urls) {
            String html = fetchHtml(url);
            if (html != null) {
                String currentHash = getHash(html);
                previousHashes.put(url, currentHash);
            }
        }

        return previousHashes;
    }
}

