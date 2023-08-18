package application;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class ProtectedWebsitesController {

    @FXML private TextField loginUrlField;
    @FXML private PasswordField passwordField;
    @FXML private TextField targetUrlField;
    @FXML private TextField usernameField;
    @FXML private ListView<WebsiteDetails> websiteListView;
    @FXML private Button scrapeButton;
    @FXML private Button addButton;

    private Map<String, String> loginCookies = new HashMap<>();
    private ObservableList<WebsiteDetails> websiteDetailsList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
    	websiteDetailsList = FXCollections.observableArrayList();   								
        websiteListView.setItems(websiteDetailsList);
        websiteListView.setCellFactory(new Callback<ListView<WebsiteDetails>, ListCell<WebsiteDetails>>() {
            public ListCell<WebsiteDetails> call(ListView<WebsiteDetails> param) {
                return new ListCell<WebsiteDetails>() {
                    @Override
                    protected void updateItem(WebsiteDetails item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            setText("Login URL: " + item.getLoginUrl() + "\nTarget URL: " + item.getTargetUrl() + "\nUsername: " + item.getUsername() + "\nPassword: " + item.getPassword());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }
    @FXML
    void addButtonClicked(ActionEvent event) {
    	 String loginUrl = loginUrlField.getText();
         String targetUrl = targetUrlField.getText();
         String username = usernameField.getText();
         String password = passwordField.getText();

         WebsiteDetails WebsiteDetails = new WebsiteDetails(loginUrl, targetUrl, username, password);
         websiteDetailsList.add(WebsiteDetails);

         loginUrlField.clear();
         targetUrlField.clear();
         usernameField.clear();
         passwordField.clear();
    }
    @FXML
    void scrapeButtonClicked(ActionEvent event) {
    	for (WebsiteDetails details : websiteDetailsList) {
            scrapeWebsite(details);
        }
    }
    private void scrapeWebsite(WebsiteDetails details) {
        String loginUrl = details.getLoginUrl();
        String targetUrl = details.getTargetUrl();
        String username = details.getUsername();
        String password = details.getPassword();
        try {
            Document loginPage = Jsoup.connect(loginUrl).get();          
                Connection.Response loginResponse = Jsoup.connect(loginUrl)
                        .data("email", username)  
                        .data("pass", password)
                        .method(Connection.Method.POST)
                        .execute();
                
                Map<String, String> loginCookies = loginResponse.cookies();               
                Document targetPageDoc = Jsoup.connect(targetUrl)
                        .cookies(loginCookies)
                        .get();               
                System.out.println(targetPageDoc.html());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
