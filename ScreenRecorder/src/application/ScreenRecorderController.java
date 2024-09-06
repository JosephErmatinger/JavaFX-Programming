package application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ScreenRecorderController {

    @FXML private ChoiceBox<String> formatChoiceBox;
    @FXML private ImageView imageView;
    @FXML private Label directoryLabel;
    @FXML private Button selectDirectoryButton;
    @FXML private TextField intervalTextField; // For specifying capture interval
    @FXML private ToggleButton recordToggleButton;
    private boolean isRecording = false;
    private Robot robot;
    private Rectangle screenRect;
    private File selectedDirectory;
    private int captureInterval = 1000; // Default capture interval in milliseconds

    public ScreenRecorderController() {
        try {
            robot = new Robot();
            screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        formatChoiceBox.getItems().addAll("png", "jpg", "gif");
        formatChoiceBox.setValue("png"); // Default value
    }

    @FXML
    public void selectDirectory(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        Stage stage = (Stage) selectDirectoryButton.getScene().getWindow();
        File directory = directoryChooser.showDialog(stage);
        if (directory != null) {
            selectedDirectory = directory;
            directoryLabel.setText("Selected Directory: " + directory.getAbsolutePath());
        }
    }
    @FXML
    public void toggleRecording(ActionEvent event) {
        if (recordToggleButton.isSelected()) {
            recordToggleButton.setText("Stop Recording");
            startRecording(event);
        } else {
            recordToggleButton.setText("Start Recording");
            stopRecording(event);
        }
    }
    @FXML
    public void startRecording(ActionEvent event) {
        if (selectedDirectory == null) {
            showAlert("Error", "Please select a directory first.");
            recordToggleButton.setSelected(false);
            recordToggleButton.setText("Start Recording");
            return;
        }
        if (!validateInterval()) {
            return; // If interval is invalid, exit method
        }
        String format = formatChoiceBox.getValue();
        if ("gif".equals(format)) {
            startGifRecording();
        } else {
            startImageRecording(format);
        }
    }
    private void startImageRecording(String format) {
        isRecording = true;
        new Thread(() -> {
            int frameNumber = 0;
            while (isRecording) {
                BufferedImage screenCapture = robot.createScreenCapture(screenRect);
                File file = new File(selectedDirectory, "frame" + frameNumber++ + "." + format);
                try {
                    ImageIO.write(screenCapture, format, file);
                    Platform.runLater(() -> updateImageView(screenCapture));
                    Thread.sleep(captureInterval);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void startGifRecording() {
        isRecording = true;
        new Thread(() -> {
            AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
            gifEncoder.start(selectedDirectory + "/recording.gif");
            gifEncoder.setDelay(captureInterval); // Set delay between frames
            gifEncoder.setRepeat(0); // Repeat indefinitely

            while (isRecording) {
                BufferedImage screenCapture = robot.createScreenCapture(screenRect);
                gifEncoder.addFrame(screenCapture);
                Platform.runLater(() -> updateImageView(screenCapture));
                try {
                    Thread.sleep(captureInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            gifEncoder.finish();
        }).start();
    }
    @FXML
    public void stopRecording(ActionEvent event) {
        isRecording = false;
    }
    @FXML
    public void takeScreenshot(ActionEvent event) {
        if (selectedDirectory == null) {
            showAlert("Error", "Please select a directory first.");
            return;
        }
        BufferedImage screenCapture = robot.createScreenCapture(screenRect);
        String format = formatChoiceBox.getValue();
        File file = new File(selectedDirectory, "screenshot." + format);
        try {
            ImageIO.write(screenCapture, format, file);
            showAlert("Screenshot taken", "Saved as screenshot." + format);
            updateImageView(screenCapture);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void updateImageView(BufferedImage image) {
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        imageView.setImage(fxImage);
    }
    private boolean validateInterval() {
        try {
            int interval = Integer.parseInt(intervalTextField.getText().trim());
            if (interval <= 0) {
                showAlert("Error", "Interval must be greater than zero.");
                return false;
            }
            captureInterval = interval;
            return true;
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid interval format. Please enter a valid number.");
            return false;
        }
    }
}
