package application;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.imageio.ImageIO;

public class TvGuideScraperController {
	
	@FXML private MenuItem fetchDataMenuItem;
	@FXML private MenuItem saveMenuItem;
	@FXML private MenuItem printMenuItem;
	@FXML private MenuItem saveImagesMenuItem;
    @FXML private TableView<TvShowElement> tvShowTableView;
    @FXML private TableColumn<TvShowElement, Image> image;
    @FXML private TableColumn<TvShowElement, String> title;
    @FXML private TableColumn<TvShowElement, String> date;
    @FXML private TableColumn<TvShowElement, String> time;
    @FXML private TableColumn<TvShowElement, String> network;
    @FXML private TableColumn<TvShowElement, String> episodeDetails;
    @FXML private TableColumn<TvShowElement, String> description;
    	  private ObservableList<TvShowElement> tvShowList = FXCollections.observableArrayList();
          String url = "https://www.tvguide.com/new-tonight/";
    
    @FXML
    private void handleFetchData() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                fetchAndPopulateTvShowList();
                return null;
            }
        };
        task.setOnRunning(event -> {
        	
        });
        task.setOnSucceeded(event -> {
            tvShowTableView.setItems(tvShowList);
        });
        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            if (exception != null) {
                exception.printStackTrace();
            }
        });
        Thread thread = new Thread(task);
        thread.start();
    }
    private void fetchAndPopulateTvShowList() {
    	tvShowList.clear();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements tvShowElements = doc.select(".c-TvObjectCard");
            Date today = new Date();
            
            for (Element tvShowElement : tvShowElements) {
                String date = tvShowElement.select(".c-TvObjectCard_meta").get(0).text();
                String time = tvShowElement.select(".c-TvObjectCard_meta").get(1).text();
                String network = tvShowElement.select(".c-TvObjectCard_meta").get(2).text();
                String title = tvShowElement.select(".g-text-xlarge").text();
                String episodeDetails = tvShowElement.select(".c-TvObjectCard_metawrapper").text();
                String description = tvShowElement.select(".c-TvObjectCard_summary").text();

                DateTimeFormatter currentDateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d", Locale.ENGLISH);
                String adjustedDateString = currentDateFormatter.format(java.time.LocalDate.now());         
                
                LocalTime parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH));
                LocalTime adjustedTime = parsedTime.minusHours(4);
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
                String adjustedTimeString = adjustedTime.format(timeFormatter);
                
                String imageUrl = tvShowElement.select(".c-cmsImage img").attr("src");
                Image image = null;
                if (!imageUrl.isEmpty()) {
                    try {
                        String imageName = title.replaceAll("[^a-zA-Z0-9]", "_") + "_" + adjustedTimeString.replaceAll("[^a-zA-Z0-9]", "_") + ".jpg";
                        URI uri = new URI(imageUrl);
                        URL urlObj = uri.toURL();
                        image = new Image(urlObj.toExternalForm());
                        TvShowElement tvShow = new TvShowElement(image, title, adjustedDateString, adjustedTimeString, network, episodeDetails, description);
                        tvShowList.add(tvShow);
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("No image for this TV show: " + title);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (DateTimeParseException e) {
        	e.printStackTrace();
        System.out.println("Date or time parsing error.");
        }
    }      
    @FXML
    private void handleSaveTvShows() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save TV Shows");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(tvShowTableView.getScene().getWindow());
        if (file != null) {
            saveTvShowsToFile(file);
        }
    }
    private void saveTvShowsToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (TvShowElement tvShow : tvShowList) {
                writer.write(tvShow.toString()); 
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleSaveImages() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder to Save Images");
        File selectedDirectory = directoryChooser.showDialog(tvShowTableView.getScene().getWindow());
        if (selectedDirectory != null) {
            saveImagesToFolder(selectedDirectory);
        }
    }
    private void saveImagesToFolder(File folder) {
        try {
            for (TvShowElement tvShow : tvShowList) {
                Image image = tvShow.getImage();
                if (image != null) {
                    String imageName = tvShow.getTitle().replaceAll("[^a-zA-Z0-9]", "_") + ".jpg";
                    Path imagePath = Paths.get(folder.getAbsolutePath(), imageName);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", imagePath.toFile());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void saveConfigurationToFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Configuration");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Data Files", "*.dat"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            Configuration configuration = new Configuration(new ArrayList<>(tvShowList));
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(configuration);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void loadConfigurationFromFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Configuration");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data Files", "*.dat"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Configuration configuration = (Configuration) ois.readObject();
                tvShowList.clear();
                tvShowList.addAll(configuration.getTvShowList());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void initialize() {
    	image.setCellValueFactory(new PropertyValueFactory<>("image"));
    	title.setCellValueFactory(new PropertyValueFactory<TvShowElement, String>("title"));
    	date.setCellValueFactory(new PropertyValueFactory<TvShowElement, String>("date"));
    	time.setCellValueFactory(new PropertyValueFactory<TvShowElement, String>("time"));
    	network.setCellValueFactory(new PropertyValueFactory<TvShowElement, String>("network"));
    	episodeDetails.setCellValueFactory(new PropertyValueFactory<TvShowElement, String>("episodeDetails"));
    	episodeDetails.setCellFactory(tc -> {
            TableCell<TvShowElement, String> cell = new TableCell<>();
            Text text = new Text();
            text.wrappingWidthProperty().bind(tc.widthProperty());
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
    	description.setCellValueFactory(new PropertyValueFactory<TvShowElement, String>("description"));
    	description.setCellFactory(tc -> {
            TableCell<TvShowElement, String> cell = new TableCell<>();
            Text text = new Text();
            text.wrappingWidthProperty().bind(tc.widthProperty());
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
    	image.setCellFactory(new Callback<TableColumn<TvShowElement, Image>, TableCell<TvShowElement, Image>>() {
            public TableCell<TvShowElement, Image> call(TableColumn<TvShowElement, Image> param) {
                return new TableCell<TvShowElement, Image>() {
                	private static final int image_size = 200;
                    private final ImageView imageView = new ImageView();
                    @Override
                    protected void updateItem(Image item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                        	imageView.setFitWidth(image_size);
                            imageView.setFitHeight(image_size);
                            imageView.setPreserveRatio(true);
                            imageView.setImage(item);
                            setGraphic(imageView);
                        }
                    }
                };
            }
        });
    }
}
