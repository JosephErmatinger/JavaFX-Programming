package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class Fortune500csvEditorController {
    
    @FXML private MenuItem openCsvFile;
	@FXML private TableView<CompanyData> csvTable;    
	@FXML private TableColumn<CompanyData, Integer> rankColumn;
	@FXML private TableColumn<CompanyData, String> titleColumn;
	@FXML private TableColumn<CompanyData, String> websiteColumn;
	@FXML private TableColumn<CompanyData, Integer> employeesColumn;
	@FXML private TableColumn<CompanyData, String> sectorColumn;
	@FXML private TableColumn<CompanyData, String> industryColumn;
	@FXML private TableColumn<CompanyData, String> hqlocationColumn;
	@FXML private TableColumn<CompanyData, String> hqaddrColumn;
	@FXML private TableColumn<CompanyData, String> hqcityColumn;
	@FXML private TableColumn<CompanyData, String> hqstateColumn;
	@FXML private TableColumn<CompanyData, String> hqzipColumn;
	@FXML private TableColumn<CompanyData, String> hqtelColumn;
	@FXML private TableColumn<CompanyData, String> ceoColumn;
	@FXML private TableColumn<CompanyData, String> ceoTitleColumn;
	@FXML private TableColumn<CompanyData, String> addressColumn;
	@FXML private TableColumn<CompanyData, String> tickerColumn;
	@FXML private TableColumn<CompanyData, String> fullnameColumn;
	@FXML private TableColumn<CompanyData, String> revenuesColumn;
	@FXML private TableColumn<CompanyData, String> revchangeColumn;
	@FXML private TableColumn<CompanyData, String> profitsColumn;
	@FXML private TableColumn<CompanyData, String> prftchangeColumn;
	@FXML private TableColumn<CompanyData, String> assetsColumn;
	@FXML private TableColumn<CompanyData, String> totshequityColumn;
		  private ObservableList<CompanyData> companyList = FXCollections.observableArrayList();

		  @FXML
		  public void initialize() {
			  csvTable.setEditable(true);
			  
			  rankColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, Integer>("rank"));
			    rankColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			    rankColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setRank(event.getNewValue());  
			    });
			    titleColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("title"));
			    titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    titleColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setTitle(event.getNewValue());
			    });
			    websiteColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("website"));
			    websiteColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    websiteColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setWebsite(event.getNewValue());
			    });			    
			    employeesColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, Integer>("employees"));
			    employeesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			    employeesColumn.setOnEditCommit(event -> {
			    	CompanyData record = event.getRowValue();
			        record.setEmployees(event.getNewValue());
			    });
			    sectorColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("sector"));
			    sectorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    sectorColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setSector(event.getNewValue());
			    });
			    industryColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("industry"));
			    industryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    industryColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setIndustry(event.getNewValue());
			    });	
			    hqlocationColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("hqlocation"));
			    hqlocationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    hqlocationColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setHqlocation(event.getNewValue());
			    });	
			    hqaddrColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("hqaddr"));
			    hqaddrColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    hqaddrColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setHqaddr(event.getNewValue());
			    });	
			    hqcityColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("hqcity"));
			    hqcityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    hqcityColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setHqcity(event.getNewValue());
			    });
			    hqstateColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("hqstate"));
			    hqstateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    hqstateColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setHqstate(event.getNewValue());
			    });
			    hqzipColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("hqzip"));
			    hqzipColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    hqzipColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setHqzip(event.getNewValue());
			    });
			    hqtelColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("hqtel"));
			    hqtelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    hqtelColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setHqtel(event.getNewValue());
			    });
			    ceoColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("ceo"));
			    ceoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    ceoColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setCeo(event.getNewValue());
			    });
			    ceoTitleColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("ceoTitle"));
			    ceoTitleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    ceoTitleColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setCeoTitle(event.getNewValue());
			    });
			    addressColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("address"));
			    addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    addressColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setAddress(event.getNewValue());
			    });
			    tickerColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("ticker"));
			    tickerColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    tickerColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setTicker(event.getNewValue());
			    });
			    fullnameColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("fullname"));
			    fullnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    fullnameColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setFullname(event.getNewValue());
			    });
			    revenuesColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("revenues"));
			    revenuesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    revenuesColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setRevenues(event.getNewValue());
			    });
			    revchangeColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("revchange"));
			    revchangeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    revchangeColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setRevchange(event.getNewValue());
			    });
			    profitsColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("profits"));
			    profitsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    profitsColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setProfits(event.getNewValue());
			    });
			    prftchangeColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("prftchange"));
			    prftchangeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    prftchangeColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setPrftchange(event.getNewValue());
			    });
			    assetsColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("assets"));
			    assetsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    assetsColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setAssets(event.getNewValue());
			    });
			    totshequityColumn.setCellValueFactory(new PropertyValueFactory<CompanyData, String>("totshequity"));
			    totshequityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			    totshequityColumn.setOnEditCommit(event -> {
			        CompanyData record = event.getRowValue();
			        record.setTotshequity(event.getNewValue());
			    });
		      if (csvTable == null) {
		          System.out.println("csvTable is not initialized");
		      } else {
		          System.out.println("csvTable is initialized");
		      }
		    }
		  	@FXML
		    public void openCSV() {
		    	if (csvTable == null) {
		            System.out.println("csvTable is null");
		            return;
		        }
		        FileChooser fileChooser = new FileChooser();
		        File file = fileChooser.showOpenDialog(csvTable.getScene().getWindow());
		        if (file != null) {
		            loadCSVData(file);
		        }
		    }
		  	private void loadCSVData(File file) {
		  	    try (BufferedReader reader = new BufferedReader(new FileReader(file));
		  	         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

		            for (CSVRecord record : csvParser) {
		                CompanyData company = new CompanyData(
		                    Integer.parseInt(record.get("Rank")),   
		                    record.get("Title"),                   
		                    record.get("Website"),                 
		                    Integer.parseInt(record.get("Employees")),  
		                    record.get("Sector"),                  
		                    record.get("Industry"),                
		                    record.get("Hqlocation"),              
		                    record.get("Hqaddr"),                 
		                    record.get("Hqcity"),                  
		                    record.get("Hqstate"),                
		                    record.get("Hqzip"),                  
		                    record.get("Hqtel"),                   
		                    record.get("Ceo"),                    
		                    record.get("Ceo-title"),              
		                    record.get("Address"),                 
		                    record.get("Ticker"),                  
		                    record.get("Fullname"),                
		                    record.get("Revenues"),                
		                    record.get("Revchange"),               
		                    record.get("Profits"),                 
		                    record.get("Prftchange"),             
		                    record.get("Assets"),                 
		                    record.get("Totshequity")   
		                );
		                companyList.add(company);
		                System.out.println(record);
		            }
		            csvTable.setItems(companyList);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
	  	    public void saveCSV() {
	  	        Stage stage = (Stage) csvTable.getScene().getWindow();  
	  	        chooseFileAndSave(stage);
	  	    }
	  	    private void chooseFileAndSave(Stage stage) {
	  	        FileChooser fileChooser = new FileChooser();
	  	        fileChooser.setTitle("Save CSV File");
	  	        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

	  	        File file = fileChooser.showSaveDialog(stage);
	  	        if (file != null) {
	  	            saveCSVData(file);
	  	        }
	  	    }
	  	    private void saveCSVData(File file) {
	  	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	  	             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(
	  	                 "Rank", "Title", "Website", "Employees", "Sector", "Industry",
	  	                 "Hqlocation", "Hqaddr", "Hqcity", "Hqstate", "Hqzip", "Hqtel",
	  	                 "Ceo", "Ceo-title", "Address", "Ticker", "Fullname", "Revenues",
	  	                 "Revchange", "Profits", "Prftchange", "Assets", "Totshequity"))) {

	  	            for (CompanyData company : companyList) {
	  	                csvPrinter.printRecord(
	  	                    company.getRank(),
	  	                    company.getTitle(),
	  	                    company.getWebsite(),
	  	                    company.getEmployees(),
	  	                    company.getSector(),
	  	                    company.getIndustry(),
	  	                    company.getHqlocation(),
	  	                    company.getHqaddr(),
	  	                    company.getHqcity(),
	  	                    company.getHqstate(),
	  	                    company.getHqzip(),
	  	                    company.getHqtel(),
	  	                    company.getCeo(),
	  	                    company.getCeoTitle(),
	  	                    company.getAddress(),
	  	                    company.getTicker(),
	  	                    company.getFullname(),
	  	                    company.getRevenues(),
	  	                    company.getRevchange(),
	  	                    company.getProfits(),
	  	                    company.getPrftchange(),
	  	                    company.getAssets(),
	  	                    company.getTotshequity()
	  	                );
	  	            }
	  	            csvPrinter.flush();  
	  	        } catch (IOException e) {
	  	            e.printStackTrace();
	  	        }
	  	    }
}	  	    


