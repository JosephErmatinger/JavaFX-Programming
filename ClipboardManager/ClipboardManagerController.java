package application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.List;

public class ClipboardManagerController {

    @FXML private Button copyButton;
    @FXML private Button deleteButton;
    @FXML private Button saveButton;
    @FXML private ListView<String> clipboardListView;
    private ObservableList<String> clipboardList = FXCollections.observableArrayList();
    private java.awt.datatransfer.Clipboard systemClipboard;

    @FXML
    public void initialize() {
        clipboardList = FXCollections.observableArrayList();
        clipboardListView.setItems(clipboardList);
        System.out.println("ListView initialized");
        // Initialize the system clipboard
        systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // Start clipboard monitoring
        ClipboardMonitorTask clipboardMonitorTask = new ClipboardMonitorTask();
        Thread monitorThread = new Thread(clipboardMonitorTask);
        monitorThread.setDaemon(true); // Ensures the thread stops when the application exits
        monitorThread.start();
        System.out.println("Clipboard monitor thread started");
    }
    @FXML
    private void handleCopy(ActionEvent event) {
        String selected = clipboardListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Transferable content = new StringSelection(selected);
            systemClipboard.setContents(content, null);
            //System.out.println("Copied to clipboard: " + selected); // Debugging line
        }
    }
    @FXML
    private void handleSave(ActionEvent event) {
        String selected = clipboardListView.getSelectionModel().getSelectedItem();
        // Add logic to save the selected item
        //System.out.println("Save selected: " + selected); // Debugging line
    }
    @FXML
    private void handleDelete(ActionEvent event) {
        String selected = clipboardListView.getSelectionModel().getSelectedItem();
        clipboardList.remove(selected);
        //System.out.println("Deleted from list: " + selected); // Debugging line
    }
    public void addClipboardItem(String item) {
        if (item != null && !item.isEmpty()) {
            clipboardList.add(0, item); // Adds the item to the top of the list
            //System.out.println("Added to ListView: " + item); // Debugging line
        }
    }
    private class ClipboardMonitorTask extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            String lastContent = "";
            while (true) {
                String newContent = "";
                boolean contentChanged = false;
                Transferable transferable = systemClipboard.getContents(null);
                if (transferable != null) {
                    try {
                        if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                            newContent = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                            contentChanged = true;
                            //System.out.println("String content detected: " + newContent);
                        } else if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                            List<?> files = (List<?>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                            newContent = files.toString();
                            contentChanged = true;
                            //System.out.println("Files content detected: " + newContent);
                        } else if (transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                            newContent = "Image copied to clipboard";
                            contentChanged = true;
                            //System.out.println("Image content detected");
                        }
                    } catch (UnsupportedFlavorException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Clipboard content is not available");
                }
                if (contentChanged && !newContent.equals(lastContent)) {
                    final String contentToAdd = newContent;
                    lastContent = newContent;
                    Platform.runLater(() -> {
                        //System.out.println("Adding to ListView: " + contentToAdd);
                        addClipboardItem(contentToAdd);
                    });
                }
                Thread.sleep(500); // Polling interval
            }
        }
    }
}

