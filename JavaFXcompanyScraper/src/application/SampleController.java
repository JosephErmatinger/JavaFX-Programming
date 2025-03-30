package application;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class SampleController {
	
	@FXML private TableView<RowData> tableView;
    @FXML private TableColumn<RowData, String> companyColumn;
    @FXML private TableColumn<RowData, String> industryColumn;
    @FXML private TableColumn<RowData, String> marketCapColumn;
    @FXML private TableColumn<RowData, String> largestInvestorColumn;
    @FXML private TableColumn<RowData, String> secondInvestorColumn;
    @FXML private TableColumn<RowData, String> thirdInvestorColumn;
    @FXML private TableColumn<RowData, String> fourthInvestorColumn;
    @FXML private TableColumn<RowData, String> fifthInvestorColumn;
    private Stage webViewStage; 
    private WebView webView;

    private ObservableList<RowData> rowDataList = FXCollections.observableArrayList();

    public static class RowData {
        private final String company;
        private final String industry;
        private final String marketCap;
        private final String largestInvestor;
        private final String secondInvestor;
        private final String thirdInvestor;
        private final String fourthInvestor;
        private final String fifthInvestor;
        private final String website;

        public RowData(String company, String industry, String marketCap, String largestInvestor,
                       String secondInvestor, String thirdInvestor, String fourthInvestor, String fifthInvestor, String website) {
            this.company = company;
            this.industry = industry;
            this.marketCap = marketCap;
            this.largestInvestor = largestInvestor;
            this.secondInvestor = secondInvestor;
            this.thirdInvestor = thirdInvestor;
            this.fourthInvestor = fourthInvestor;
            this.fifthInvestor = fifthInvestor;
            this.website = website;
        }
        public String getCompany() { return company; }
        public String getIndustry() { return industry; }
        public String getMarketCap() { return marketCap; }
        public String getLargestInvestor() { return largestInvestor; }
        public String getSecondInvestor() { return secondInvestor; }
        public String getThirdInvestor() { return thirdInvestor; }
        public String getFourthInvestor() { return fourthInvestor; }
        public String getFifthInvestor() { return fifthInvestor; }
        public String getWebsite() { return website; }
    }
    @FXML
    public void initialize() {
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        industryColumn.setCellValueFactory(new PropertyValueFactory<>("industry"));
        marketCapColumn.setCellValueFactory(new PropertyValueFactory<>("marketCap"));
        largestInvestorColumn.setCellValueFactory(new PropertyValueFactory<>("largestInvestor"));
        secondInvestorColumn.setCellValueFactory(new PropertyValueFactory<>("secondInvestor"));
        thirdInvestorColumn.setCellValueFactory(new PropertyValueFactory<>("thirdInvestor"));
        fourthInvestorColumn.setCellValueFactory(new PropertyValueFactory<>("fourthInvestor"));
        fifthInvestorColumn.setCellValueFactory(new PropertyValueFactory<>("fifthInvestor"));

        try {
            scrapeTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showWebsitePopup(newSelection.getWebsite());
            }
        });
    }
    private void showWebsitePopup(String url) {
        if (url == null || url.isEmpty()) {
            System.out.println("No URL available for this row.");
            return;
        }
        if (webViewStage == null) {
            webViewStage = new Stage();
            webViewStage.setTitle("Website Viewer");
            webView = new WebView();
            Scene scene = new Scene(webView, 800, 600);
            webViewStage.setScene(scene);
        }
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url);
        if (!webViewStage.isShowing()) {
            webViewStage.show();
        } else {
            webViewStage.toFront();
        }
    }
    private void scrapeTable() throws IOException {
        String url = "https://blackrockvanguardwatch.com"; 
        Document doc = Jsoup.connect(url).get();

        Element table = doc.selectFirst("#mainTable");
        if (table == null) return;

        Elements rows = table.select("tbody tr");

        for (Element row : rows) {
            Elements cells = row.select("td");
            if (cells.size() < 8) continue; 
            Element link = cells.get(0).selectFirst("a");
            String company = cells.get(0).text();
            String website = link != null ? link.attr("href") : "";
            String industry = cells.get(1).text();
            String marketCap = cells.get(2).text();
            String largestInvestor = cells.get(3).text();
            String secondInvestor = cells.get(4).text();
            String thirdInvestor = cells.get(5).text();
            String fourthInvestor = cells.get(6).text();
            String fifthInvestor = cells.get(7).text();


            rowDataList.add(new RowData(company, industry, marketCap, largestInvestor, secondInvestor, thirdInvestor, fourthInvestor, fifthInvestor, website));
        }
        tableView.setItems(rowDataList);
    }
}
