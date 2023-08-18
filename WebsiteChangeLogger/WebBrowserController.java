package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebBrowserController {
	@FXML private TextField addressBar;
    @FXML private Button loadButton;
    @FXML private WebView webView;

    @FXML
    void loadURL(ActionEvent event) {
    	String url = addressBar.getText();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url);
    }
}
