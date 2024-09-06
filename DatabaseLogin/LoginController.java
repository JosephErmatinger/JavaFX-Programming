package application;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LoginController {
	@FXML private ImageView imageview;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void signupButtonClicked() {
        String email = emailField.getText();
        String password = passwordField.getText();
        DatabaseManager.addUser(email, password);
        emailField.clear();
    	passwordField.clear();
    }
    @FXML
    private void loginButtonClicked() {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (DatabaseManager.validateCredentials(email, password)) {
        	System.out.println("Successful Login!");
        	emailField.clear();
        	passwordField.clear();
        } else {
        	System.out.println("Failed Login!");
        }
    }
    
}
