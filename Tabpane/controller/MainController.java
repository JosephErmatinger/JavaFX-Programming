package controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MainController {

    @FXML private ConsoleTabController consoleTabController;
    @FXML private LoggerTabController loggerTabController;
    
    public TextArea getVisualLog() {
        return loggerTabController.getLoggerTxtArea();
    }

    @FXML private void initialize() {
        consoleTabController.injectMainController(this);
    }
}
