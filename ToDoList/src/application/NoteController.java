package application;
import java.io.IOException;
import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class NoteController {
    
    @FXML private TextArea noteTextArea;
    	  private Consumer<String> onSaveCallback;
    	  private final ObservableList<String> notes = FXCollections.observableArrayList();
    	  
    public void setOnSaveCallback(Consumer<String> callback) {
        this.onSaveCallback = callback;
    }
    @FXML
    private void handleSave() {
        if (onSaveCallback != null) {
            onSaveCallback.accept(noteTextArea.getText());
        }
        Stage stage = (Stage) noteTextArea.getScene().getWindow();
        stage.close();
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