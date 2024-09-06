package application;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClipboardManager {

    private static String lastClipboardContent = "";

    public static void main(String[] args) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        clipboard.addFlavorListener(new FlavorListener() {
            @Override
            public void flavorsChanged(FlavorEvent e) {
                processClipboardContent(clipboard);
            }
        });
        System.out.println("Clipboard Manager started. Waiting for clipboard changes...");
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            System.err.println("Clipboard Manager interrupted: " + e.getMessage());
        }
    }
    private static void processClipboardContent(Clipboard clipboard) {
        Transferable content = clipboard.getContents(null);
        if (content != null) {
            if (content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                handleStringFlavor(content);
            } else if (content.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                handleFileListFlavor(content);
            } else {
                System.out.println("Unsupported content type copied to clipboard.");
            }
        }
    }
    private static void handleStringFlavor(Transferable content) {
        try {
            String clipboardText = (String) content.getTransferData(DataFlavor.stringFlavor);
            if (!clipboardText.equals(lastClipboardContent) && !clipboardText.isEmpty()) {
                lastClipboardContent = clipboardText;
                System.out.println("Text copied: " + clipboardText);
                clearClipboard(Toolkit.getDefaultToolkit().getSystemClipboard());
            }
        } catch (UnsupportedFlavorException | IOException e) {
            System.err.println("Error retrieving clipboard content: " + e.getMessage());
        }
    }
    private static void handleFileListFlavor(Transferable content) {
        try {
            @SuppressWarnings("unchecked")
            List<File> files = (List<File>) content.getTransferData(DataFlavor.javaFileListFlavor);
            for (File file : files) {
                System.out.println("Files copied: " + file.getAbsolutePath());
            }
            clearClipboard(Toolkit.getDefaultToolkit().getSystemClipboard());
        } catch (UnsupportedFlavorException | IOException e) {
            System.err.println("Error retrieving clipboard content: " + e.getMessage());
        }
    }
    private static void clearClipboard(Clipboard clipboard) {
        try {
            clipboard.setContents(new StringSelection(""), null);
        } catch (IllegalStateException e) {
            System.err.println("Error clearing clipboard: " + e.getMessage());
        }
    }
}
