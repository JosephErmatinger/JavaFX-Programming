package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UrlService {
    
    private final String path = "/resources/";

    public String getUrlInfo(String category) throws IOException {
        final StringBuilder fileContents = new StringBuilder(2000);
        final InputStream is = this.getClass().getResourceAsStream(path + category);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContents.append(line).append("\n");
            }
        }
        return fileContents.toString();
    }
}
