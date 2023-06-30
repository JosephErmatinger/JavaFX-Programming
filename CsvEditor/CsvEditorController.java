package application;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.annotation.processing.FilerException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import application.CsvRow;
import application.Main;

public class CsvEditorController implements Initializable {
	@FXML private TableView<CsvRow> tableView;
	@FXML private AnchorPane root;
	      private FileChooser fileChooser;
	      private CSVFormat csvFormat;
	      private Integer numberColumns = 0;
	      private File file;
	      private boolean saved = true;

	    @Override
	    public void initialize(URL url, ResourceBundle rb) {
	        file = new File("");
	        csvFormat = CSVFormat.DEFAULT.withIgnoreEmptyLines(false);

	        ContextMenu contextMenu = new ContextMenu();
	        contextMenu.setAutoHide(true);
	        MenuItem insertLine = new MenuItem("Insert Line");
	        insertLine.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent t) {
	                addNewRow();
	                setNotSaved();
	            }
	        });
	        contextMenu.getItems().add(insertLine);
	        MenuItem removeLine = new MenuItem("Remove Line");
	        removeLine.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent t) {
	                deleteRow();
	                setNotSaved();
	            }
	        });
	        contextMenu.getItems().add(removeLine);
	        contextMenu.getItems().add(new SeparatorMenuItem());
	        MenuItem insertColumn = new MenuItem("Insert Column");
	        insertColumn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                addNewColumn();
	                setNotSaved();
	            }
	        });
	        contextMenu.getItems().add(insertColumn);
	        MenuItem removeColumn = new MenuItem("Remove Column");
	        removeColumn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                deleteColumn();
	                setNotSaved();
	            }
	        });
	        contextMenu.getItems().add(removeColumn);
	        tableView.setContextMenu(contextMenu);
	    }
	    @FXML
	    private void onSaveActionEvent(ActionEvent event) {
	        try (PrintWriter pw = new PrintWriter(file); CSVPrinter print = csvFormat.print(pw)) {
	            for (CsvRow row : tableView.getItems()) {
	                if (row.isEmpty()) {
	                    print.println();
	                } else {
	                    for (SimpleStringProperty column : row.getColumns()) {
	                        print.print(column.getValue());
	                    }
	                    print.println();
	                }
	            }
	            print.flush();
	            setSaved();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            Alert d = new Alert(Alert.AlertType.ERROR);
	            d.setHeaderText("Ooops, could not save the file " + (file != null ? file.getName() : "."));
	            d.setContentText(ex.getMessage());
	            d.setTitle("Error");
	            d.initOwner(root.getScene().getWindow());
	            d.show();
	        }
	    }
	    @FXML
	    private void onOpenActionEvent(ActionEvent event) {
	        File csvFile = null;
	        try {
	            if (!saved) {
	                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
	                a.setHeaderText("Do you want to discard the changes??");
	                a.initOwner(root.getScene().getWindow());
	                Optional<ButtonType> result = a.showAndWait();
	                if (result.get() != ButtonType.YES) {
	                    return;
	                }
	            }
	            csvFile = openFileChooser();
	            if (csvFile == null || !csvFile.exists()) {
	                throw new FileNotFoundException("The selected file does not exist!");
	            }
	            ObservableList<CsvRow> rows = readFile(csvFile);
	            if (rows == null || rows.isEmpty()) {
	                throw new FilerException("The selected file is empty!");
	            }
	            updateTable(rows);
	            this.file = csvFile;
	            setSaved();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            Alert d = new Alert(Alert.AlertType.ERROR);
	            d.setHeaderText("Ooops, could not open the file " + (csvFile != null ? csvFile.getName() : "."));
	            d.setContentText(ex.getMessage());
	            d.setTitle("Erro");
	            d.initOwner(root.getScene().getWindow());
	            d.show();
	        }
	    }
	    private void addNewRow() {
	        Integer current = tableView.getSelectionModel().getSelectedIndex();
	        tableView.getItems().add(current, new CsvRow());
	        tableView.getSelectionModel().select(current);
	    }
	    private void deleteRow() {
	        tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
	    }
	    private void addNewColumn() {
	        List<TablePosition> cells = tableView.getSelectionModel().getSelectedCells();
	        int columnIndex = cells.get(0).getColumn();
	        for (CsvRow row : tableView.getItems()) {
	            row.addColumn(columnIndex);
	        }
	        numberColumns++;
	        tableView.getColumns().add(createColumn(numberColumns - 1));
	        tableView.refresh();
	    }
	    private void deleteColumn() {
	        List<TablePosition> cells = tableView.getSelectionModel().getSelectedCells();
	        int columnIndex = cells.get(0).getColumn();
	        for (CsvRow row : tableView.getItems()) {
	            row.removeColumn(columnIndex);
	        }
	        numberColumns--;
	        tableView.getColumns().remove(tableView.getColumns().size() - 1);
	        tableView.refresh();
	    }
	    private File openFileChooser() {
	        if (fileChooser == null) {
	            fileChooser = new FileChooser();
	            fileChooser.setTitle("Open File");
	            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
	            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
	        }
	        return fileChooser.showOpenDialog(root.getScene().getWindow());
	    }
	    private ObservableList<CsvRow> readFile(File csvFile) throws IOException {
	        ObservableList<CsvRow> rows = FXCollections.observableArrayList();
	        Integer maxColumns = 0;
	        try (Reader in = new InputStreamReader(new FileInputStream(csvFile));) {
	            CSVParser parse = csvFormat.parse(in);
	            for (CSVRecord record : parse.getRecords()) {
	                if (maxColumns < record.size()) {
	                    maxColumns = record.size();
	                }
	                CsvRow row = new CsvRow();
	                for (int i = 0; i < record.size(); i++) {
	                    row.getColumns().add(new SimpleStringProperty(record.get(i)));
	                }
	                rows.add(row);
	            }
	            this.numberColumns = maxColumns;
	        }
	        return rows;
	    }
	    private void updateTable(ObservableList<CsvRow> rows) {
	        tableView.getColumns().clear();
	        for (int i = 0; i < numberColumns; i++) {
	            TableColumn<CsvRow, String> col = createColumn(i);
	            tableView.getColumns().add(col);
	        }
	        tableView.setItems(rows);
	        tableView.setEditable(true);
	        tableView.getSelectionModel().setCellSelectionEnabled(true);
	    }
	    private void setNotSaved() {
	        Stage stage = (Stage) root.getScene().getWindow();
	        stage.setTitle(file.getName() + " (Not saved) ");
	        saved = false;
	    }
	    private void setSaved() {
	        Stage stage = (Stage) root.getScene().getWindow();
	        stage.setTitle(file.getName());
	        saved = true;
	    }
	    @FXML
	    private void onExitActionEvent(ActionEvent event) {
	        if (saved) {
	            exit();
	        } else {
	            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
	            a.setHeaderText("Do you want to exit without saving?");
	            a.initOwner(root.getScene().getWindow());
	            Optional<ButtonType> result = a.showAndWait();
	            if (result.get() == ButtonType.YES) {
	                exit();
	            }
	        }
	    }
	    private void exit() {
	        System.exit(0);
	    }
	    private TableColumn<CsvRow, String> createColumn(int index) {
	        TableColumn<CsvRow, String> col = new TableColumn<>((index + 1) + "");
	        col.setSortable(false);
	        col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CsvRow, String>, ObservableValue<String>>() {
	            @Override
	            public ObservableValue<String> call(TableColumn.CellDataFeatures<CsvRow, String> param) {
	                adjustColumns(param.getValue().getColumns());
	                return param.getValue().getColumns().get(index);
	            }
	        });
	        col.setCellFactory(TextFieldTableCell.forTableColumn());
	        col.setOnEditCommit(new EventHandler<CellEditEvent<CsvRow, String>>() {
	            @Override
	            public void handle(CellEditEvent<CsvRow, String> event) {
	                adjustColumns(event.getRowValue().getColumns());
	                event.getRowValue().getColumns().get(index).set(event.getNewValue());
	                setNotSaved();
	            }
	        });
	        col.setEditable(true);
	        return col;
	    }
	    private void adjustColumns(List<SimpleStringProperty> columns) {
	        int dif = numberColumns - columns.size();
	        for (int i = 0; i < dif; i++) {
	            columns.add(new SimpleStringProperty());
	        }
	    }
}
