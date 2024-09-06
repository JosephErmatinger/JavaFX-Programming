package application;
import java.io.*;
import java.util.List;

public class Configuration implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<String> urls;

    public Configuration(List<String> urls) {
        this.urls = urls;
    }
    public List<String> getUrls() {
        return urls;
    }
    public void saveToFile(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        }
    }
    public static Configuration loadFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Configuration) ois.readObject();
        }
    }
}


