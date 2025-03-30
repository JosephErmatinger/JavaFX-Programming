package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ToDoListController {
	@FXML
    private ListView<String> noteListView;
    @FXML
    private TextField searchField;
    private final ObservableList<String> notes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        noteListView.setItems(notes);
    }
    @FXML
    private void handleNewNote() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Note.fxml"));
            Stage noteStage = new Stage();
            noteStage.setTitle("New Note");
            noteStage.setScene(new Scene(loader.load()));

            NoteController controller = loader.getController();
            controller.setOnSaveCallback(content -> {
                String newNoteTitle = content.split("\n", 2)[0];
                if (newNoteTitle.isEmpty()) {
                    newNoteTitle = "Untitled Note";
                }
                notes.add(newNoteTitle);
            });

            noteStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
