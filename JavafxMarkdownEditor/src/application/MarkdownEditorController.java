package application; // Make sure to replace with your actual package
import javafx.fxml.FXML;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.html.HtmlRenderer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MarkdownEditorController {

    @FXML private HTMLEditor htmlEditor;
    @FXML private WebView markdownPreview;

    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownEditorController() {
        MutableDataSet options = new MutableDataSet();
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
    }

    @FXML
    private void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Markdown Files", "*.md"));
        Window stage = htmlEditor.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                htmlEditor.setHtmlText(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void renderMarkdown(ActionEvent event) {
        String htmlText = htmlEditor.getHtmlText();
        String markdownText = htmlText.replaceAll("(?s)<pre>(.*)</pre>", "$1").replaceAll("<[^>]+>", "");

        // Use the parser and renderer instances
        Node document = parser.parse(markdownText);
        String renderedHtml = renderer.render(document);

        markdownPreview.getEngine().loadContent(renderedHtml);
    }
}
