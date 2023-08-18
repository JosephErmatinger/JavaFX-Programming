package application;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SettingsWindowController {
    @FXML private Label label;
    @FXML private TextField intervalTextField;
    	  private WebsiteChangeLoggerController websiteController;
    	  private int previousIntervalValue = 1;
    	  private static final String PREFERENCE_KEY = "intervalValue";

    	  public void initialize() {
    		  Preferences preferences = Preferences.userNodeForPackage(SettingsWindowController.class);
    	      previousIntervalValue = preferences.getInt(PREFERENCE_KEY, 1);
    	      intervalTextField.setText(String.valueOf(previousIntervalValue));
    	  }
    	  public void setWebsiteChangeLoggerController(WebsiteChangeLoggerController websiteController) {
    	        this.websiteController = websiteController;
    	  }
    	  public int getIntervalTextFieldValue() {
    	        String intervalText = intervalTextField.getText();
    	        int intervalValue = 1;
    	        try {
    	            intervalValue = Integer.parseInt(intervalText);
    	        } catch (NumberFormatException e) {
    	        }
    	        Preferences preferences = Preferences.userNodeForPackage(SettingsWindowController.class);
    	        preferences.putInt(PREFERENCE_KEY, intervalValue);
    	        System.out.println("Stored Value Settings Window: " + preferences.getInt(PREFERENCE_KEY, 1));
    	        return intervalValue * 60000;
    	 }
}
