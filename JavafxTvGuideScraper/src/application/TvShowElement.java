package application;
import java.io.Serializable;
import javafx.scene.image.Image;

public class TvShowElement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private  transient Image image;
	private  String title;
    private  String date;
    private  String time;
    private  String network;
    private  String episodeDetails;
    private  String description;
   
    public TvShowElement(Image image, String title, String date, String time, String network, String episodeDetails, String description) {
        this.image = image;
    	this.title =title;
        this.date = date;
        this.time = time;
        this.network = network;
        this.episodeDetails = episodeDetails;
        this.description = description;
    }
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getNetwork() {
        return network;
    }
    public void setNetwork(String network) {
        this.network = network;
    }
    public String getEpisodeDetails() {
        return episodeDetails;
    }
    public void setEpisodeDetails(String episodeDetails) {
        this.episodeDetails = episodeDetails;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "Title: " + title + "\n" +
               "Date: " + date + "\n" +
               "Time: " + time + "\n" +
               "Network: " + network + "\n" +
               "Episode Details: " + episodeDetails + "\n" +
               "Description: " + description;
    }
}
