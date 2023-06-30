package controller;
import application.UrlService;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class ConsoleTabController {
    @FXML private TextArea urlOverviewText;
    @FXML private ListView<String> urlList;
    private final UrlService urlService = new UrlService();
    private MainController mainController; 
    public void injectMainController(MainController mainController){
        this.mainController = mainController;
    }    
    public void initialize() {
        ObservableList<String> categories = FXCollections.observableArrayList("Addenda Watchlist No. 1", "Architect, Engineer, CM and GC No. 1", "Bid4Michigan No. 1",
        "BidNet No. 1", "Cities No. 1", "Colleges No. 1", "Counties No. 1", "Housing Commissions No. 1", "MDOT Request for Proposals No. 1", "Miscellaneous No. 1",
        "Out of State No. 1", "Planning Commission Cities and Counties No. 1", "Planning Commission Colleges and Universities No. 1", "Planning Commission Priority Municipalities No. 1",
        "Planning Commission Townships No. 1", "Planning Commission Villages No. 1", "Road Commissions No. 1", "Schools No. 1", "Townships No. 1", "Villages No. 1");
        urlList.setItems(categories);
    }
    @FXML
    private void onMouseClicked(MouseEvent event) {
        urlOverviewText.clear();
        final String selectedItem = urlList.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        urlOverviewText.positionCaret(0);
        urlOverviewText.appendText(getInfo(selectedItem));
    }
    public String getInfo(String selectedItem) {
        PrintWriter stackTraceWriter = new PrintWriter(new StringWriter());
        String urlInfo = null ;            
        try {
            urlInfo = urlService.getUrlInfo(selectedItem); 
            getLog().appendText("Sucessfully retrieved urls for " + selectedItem + "\n");
        } catch (IOException exception) {
            exception.printStackTrace (stackTraceWriter);
            getLog().appendText(stackTraceWriter.toString() + "\n");
        }       
        return urlInfo;
    }
    public TextArea getUrlOverviewText() {
        return urlOverviewText;
    }
    public ListView<String> getUrlList() {
        return urlList;
    }    
    private TextArea getLog(){
        return mainController.getVisualLog(); 
    }
}
