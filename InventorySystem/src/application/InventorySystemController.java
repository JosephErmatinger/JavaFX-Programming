package application;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class InventorySystemController {
		
	@FXML private Menu menu;
	@FXML private MenuBar menubar;
	@FXML private MenuItem exitMenuItem;
	@FXML private MenuItem printMenuItem;
	@FXML private MenuItem saveMenuItem;
	@FXML private MenuItem importCsvMenuItem;
 	@FXML private TextField barcodeTextField;
    @FXML private TextField categoryTextField;
    @FXML private TextField costTextField;
    @FXML private TextField dateAddedTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField discountTextField;
    @FXML private TextField expiryDateTextField;
    @FXML private TextField idTextField;
    @FXML private TextField locationTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField quantityTextField;
    @FXML private TextField reorderTextField;
    @FXML private TextField statusTextField;
    @FXML private TextField supplierTextField;
    @FXML private TableView<InventoryItem> inventoryTableview;
    @FXML private TableColumn<InventoryItem, Integer> idColumn;
    @FXML private TableColumn<InventoryItem, String> nameColumn;
    @FXML private TableColumn<InventoryItem, String> categoryColumn;
    @FXML private TableColumn<InventoryItem, String> supplierColumn;
    @FXML private TableColumn<InventoryItem, Double> costColumn;
    @FXML private TableColumn<InventoryItem, Double> priceColumn;
    @FXML private TableColumn<InventoryItem, Integer> quantityColumn;
    @FXML private TableColumn<InventoryItem, Date> dateAddedColumn;
    @FXML private TableColumn<InventoryItem, Date> expiryDateColumn;
    @FXML private TableColumn<InventoryItem, String> descriptionColumn;
    @FXML private TableColumn<InventoryItem, String> locationColumn;
    @FXML private TableColumn<InventoryItem, Image> barcodeColumn;
    @FXML private TableColumn<InventoryItem, Integer> reorderLevelColumn;
    @FXML private TableColumn<InventoryItem, Double> discountColumn;
    @FXML private TableColumn<InventoryItem, String> statusColumn;
    @FXML private ImageView imageview;
          private File selectedBarcodeFile;
          private boolean isEditing = false;	         
          private static final Logger LOGGER = Logger.getLogger(InventorySystemController.class.getName());
    	  private ObservableList<InventoryItem> inventoryList = FXCollections.observableArrayList();
    @FXML
    private void initialize() {
    	idColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("id"));
    	nameColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("name"));
    	categoryColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("category"));
    	supplierColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("supplier"));
    	costColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, Double>("cost"));
    	priceColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, Double>("price"));
    	quantityColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("quantity"));
    	dateAddedColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, Date>("dateAdded"));
    	expiryDateColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, Date>("expiryDate"));
    	descriptionColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("description"));
    	locationColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("location"));
    	barcodeColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, Image>("barcode"));
    	reorderLevelColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("reorderLevel"));
    	discountColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, Double>("discount"));
    	statusColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("status"));
    	barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        barcodeColumn.setCellFactory(column -> new TableCell<InventoryItem, Image>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);
                }
            }
        });
    	loadData();
        inventoryTableview.setItems(inventoryList);
    }
    @FXML
    private void handleSubmit() {
        if (isEditing) {
            updateRecord();
        } else {
            addRecord();
        }
    }
    @FXML
    private void addRecord() {
        try (Connection conn = DatabaseManager.connect()) {
            String insertSQL = "INSERT INTO Inventory (ItemName, Category, Supplier, Cost, Price, Quantity, DateAdded, ExpiryDate, "
                             + "Description, Location, Barcode, ReorderLevel, Discount, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, nameTextField.getText());
                pstmt.setString(2, categoryTextField.getText());
                pstmt.setString(3, supplierTextField.getText());
                pstmt.setDouble(4, Double.parseDouble(costTextField.getText()));
                pstmt.setDouble(5, Double.parseDouble(priceTextField.getText()));
                pstmt.setInt(6, Integer.parseInt(quantityTextField.getText()));
                pstmt.setDate(7, Date.valueOf(dateAddedTextField.getText()));
                pstmt.setDate(8, Date.valueOf(expiryDateTextField.getText()));
                pstmt.setString(9, descriptionTextField.getText());
                pstmt.setString(10, locationTextField.getText());
                byte[] barcodeData = null;
                if (selectedBarcodeFile != null) {
                    barcodeData = readFileToByteArray(selectedBarcodeFile);
                }
                pstmt.setBytes(11, barcodeData);
                pstmt.setInt(12, Integer.parseInt(reorderTextField.getText()));
                pstmt.setDouble(13, Double.parseDouble(discountTextField.getText()));
                pstmt.setString(14, statusTextField.getText());
                pstmt.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        loadData();
    }
    @FXML
	private void handleEdit() {
	    InventoryItem selectedItem = inventoryTableview.getSelectionModel().getSelectedItem();
	    if (selectedItem != null) {
	    	isEditing = true;            
	    	idTextField.setText(String.valueOf(selectedItem.getId())); 
	        nameTextField.setText(selectedItem.getName());
	        categoryTextField.setText(selectedItem.getCategory());
	        supplierTextField.setText(selectedItem.getSupplier());
	        costTextField.setText(Double.toString(selectedItem.getCost()));
	        priceTextField.setText(Double.toString(selectedItem.getPrice()));
	        quantityTextField.setText(Integer.toString(selectedItem.getQuantity()));
	        dateAddedTextField.setText(selectedItem.getDateAdded().toString());
	        expiryDateTextField.setText(selectedItem.getExpiryDate().toString());
	        descriptionTextField.setText(selectedItem.getDescription());
	        locationTextField.setText(selectedItem.getLocation());
	        reorderTextField.setText(Integer.toString(selectedItem.getReorderLevel()));
	        discountTextField.setText(Double.toString(selectedItem.getDiscount()));
	        statusTextField.setText(selectedItem.getStatus());
	        if (selectedItem.getBarcode() != null) {
	            imageview.setImage(selectedItem.getBarcode());
	        } else {
	            imageview.setImage(null);
	        }
	    } else {
	        System.out.println("No item selected for editing.");
	    }
	}
    @FXML
	private void updateRecord() {
	    InventoryItem selectedItem = inventoryTableview.getSelectionModel().getSelectedItem();
	    if (selectedItem != null) {
	        try (Connection conn = DatabaseManager.connect()) {
	            String updateSQL = "UPDATE Inventory SET ItemName=?, Category=?, Supplier=?, Cost=?, Price=?, Quantity=?, "
	                             + "DateAdded=?, ExpiryDate=?, Description=?, Location=?, Barcode=?, ReorderLevel=?, Discount=?, "
	                             + "Status=? WHERE ID=?";
	            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
	                pstmt.setString(1, nameTextField.getText());
	                pstmt.setString(2, categoryTextField.getText());
	                pstmt.setString(3, supplierTextField.getText());
	                pstmt.setDouble(4, Double.parseDouble(costTextField.getText()));
	                pstmt.setDouble(5, Double.parseDouble(priceTextField.getText()));
	                pstmt.setInt(6, Integer.parseInt(quantityTextField.getText()));
	                pstmt.setDate(7, Date.valueOf(dateAddedTextField.getText()));
	                pstmt.setDate(8, Date.valueOf(expiryDateTextField.getText()));
	                pstmt.setString(9, descriptionTextField.getText());
	                pstmt.setString(10, locationTextField.getText());                
	                byte[] barcodeData;
	                if (selectedBarcodeFile != null) {
	                    barcodeData = readFileToByteArray(selectedBarcodeFile);
	                } else {
	                    barcodeData = imageToByteArray(selectedItem.getBarcode());
	                }
	                pstmt.setBytes(11, barcodeData);
	                pstmt.setInt(12, Integer.parseInt(reorderTextField.getText()));
	                pstmt.setDouble(13, Double.parseDouble(discountTextField.getText()));
	                pstmt.setString(14, statusTextField.getText());
	                pstmt.setInt(15, selectedItem.getId());
	                pstmt.executeUpdate();
	            }
	        } catch (SQLException | IOException e) {
	            e.printStackTrace();
	        }
	        loadData();
	        isEditing = false;
	    } else {
	        System.out.println("No item selected for editing.");
	    }
	}
	@FXML
    private void handleDelete() {
		InventoryItem selectedItem = inventoryTableview.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
            try (Connection conn = DatabaseManager.connect()) {
                String deleteSQL = "DELETE FROM Inventory WHERE ID=?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                    pstmt.setInt(1, selectedItem.getId());
                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            loadData(); 
        } else {
            
        }
    }
	@FXML
	private void handleImportCSV() {
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Select CSV File");
	    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
	    File selectedFile = fileChooser.showOpenDialog(new Stage());
	    if (selectedFile != null) {
	        try (Connection conn = DatabaseManager.connect();
	             FileReader fileReader = new FileReader(selectedFile);
	             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader())) {	        			
	            LOGGER.info("CSV Headers: " + csvParser.getHeaderMap().keySet());
	            String insertSQL = "INSERT INTO Inventory (ItemName, Category, Supplier, Cost, Price, Quantity, DateAdded, ExpiryDate, "
	                             + "Description, Location, Barcode, ReorderLevel, Discount, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	            List<CSVRecord> records = csvParser.getRecords();	          
	            conn.setAutoCommit(false);
	            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
	                for (CSVRecord record : records) {
	                    LOGGER.info("CSV Record: " + record.toString());
	                    pstmt.setString(1, record.get("ItemName"));
	                    pstmt.setString(2, record.get("Category"));
	                    pstmt.setString(3, record.get("Supplier"));
	                    pstmt.setDouble(4, Double.parseDouble(record.get("Cost")));
	                    pstmt.setDouble(5, Double.parseDouble(record.get("Price")));
	                    pstmt.setInt(6, Integer.parseInt(record.get("Quantity")));
	                    pstmt.setDate(7, Date.valueOf(record.get("DateAdded")));
	                    pstmt.setDate(8, Date.valueOf(record.get("ExpiryDate")));
	                    pstmt.setString(9, record.get("Description"));
	                    pstmt.setString(10, record.get("Location"));
	                    File barcodeFile = new File(record.get("Barcode"));
	                    byte[] barcodeData = readFileToByteArray(barcodeFile);
	                    pstmt.setBytes(11, barcodeData);
	                    pstmt.setInt(12, Integer.parseInt(record.get("ReorderLevel")));
	                    pstmt.setDouble(13, Double.parseDouble(record.get("Discount")));
	                    pstmt.setString(14, record.get("Status"));
	                    pstmt.executeUpdate(); 
	                }	                
	                conn.commit(); 
	            } catch (SQLException | IOException e) {
	                conn.rollback(); 
	                e.printStackTrace();
	            } finally {
	                conn.setAutoCommit(true); 
	            }
	        } catch (IOException | SQLException e) {
	            e.printStackTrace();
	        }    
	        loadData(); 
	    }
	}
	private static byte[] readFileToByteArray(File file) throws IOException {
	    try (FileInputStream fis = new FileInputStream(file)) {
	        byte[] data = new byte[(int) file.length()];
	        int bytesRead = fis.read(data);
	        if (bytesRead != file.length()) {
	            throw new IOException("Could not read the entire file " + file.getName());
	        }
	        return data;
	    }
	}
	private byte[] imageToByteArray(Image image) {
	    if (image == null) {
	        return null;
	    }
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    try {
	        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
	        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
	        return byteArrayOutputStream.toByteArray();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	@FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Barcode Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        selectedBarcodeFile = fileChooser.showOpenDialog(new Stage());
        if (selectedBarcodeFile != null) {
            imageview.setImage(new Image(selectedBarcodeFile.toURI().toString()));
            barcodeTextField.setText(selectedBarcodeFile.getAbsolutePath());
        }
    }
	private void loadData() {
	    inventoryList.clear();
	    try (Connection conn = DatabaseManager.connect()) {
	        String querySQL = "SELECT * FROM Inventory";
	        try (Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(querySQL)) {
	            while (rs.next()) {
	                Image barcodeImage = null;
	                byte[] barcodeBytes = rs.getBytes("Barcode");
	                if (barcodeBytes != null) {
	                    barcodeImage = new Image(new ByteArrayInputStream(barcodeBytes));
	                }
	                InventoryItem item = new InventoryItem(
	                        rs.getInt("ID"),
	                        rs.getString("ItemName"),
	                        rs.getString("Category"),
	                        rs.getString("Supplier"),
	                        rs.getDouble("Cost"),
	                        rs.getDouble("Price"),
	                        rs.getInt("Quantity"),
	                        rs.getDate("DateAdded"),
	                        rs.getDate("ExpiryDate"),
	                        rs.getString("Description"),
	                        rs.getString("Location"),
	                        barcodeImage,
	                        rs.getInt("ReorderLevel"),
	                        rs.getDouble("Discount"),
	                        rs.getString("Status")
	                );
	                inventoryList.add(item);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    inventoryTableview.setItems(inventoryList);
	}
	@FXML
	private void clear() {
	    idTextField.clear();
	    nameTextField.clear();
	    categoryTextField.clear();
	    supplierTextField.clear();
	    costTextField.clear();
	    priceTextField.clear();
	    quantityTextField.clear();
	    dateAddedTextField.clear();
	    expiryDateTextField.clear();
	    descriptionTextField.clear();
	    locationTextField.clear();
	    reorderTextField.clear();
	    discountTextField.clear();
	    statusTextField.clear();
	    barcodeTextField.clear();
	    imageview.setImage(null);
	    selectedBarcodeFile = null; 
	}
	@FXML
	private void handleExit() {
	    Platform.exit();
	}
}