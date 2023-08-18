package application;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Configuration implements Serializable {
   
		private static final long serialVersionUID = 1L;
	    private ArrayList<String> urlsList; 
	    private String textFlowContent;
	    private Map<String, String> previousHashes;

	    public Configuration(ArrayList<String> urlsList, String textFlowContent, Map<String, String> previousHashes) {
	        this.urlsList = urlsList;
	        this.textFlowContent = textFlowContent;
	        this.previousHashes = previousHashes;
	    }
	    public ArrayList<String> getUrlsList() {
	        return urlsList;
	    }
	    public void setUrlsList(ArrayList<String> urlsList) {
	        this.urlsList = urlsList;
	    }
	    public String getTextFlowContent() {
	        return textFlowContent;
	    }
	    public void setTextFlowContent(String textFlowContent) {
	        this.textFlowContent = textFlowContent;
	    }
	    public Map<String, String> getPreviousHashes() {
	        return previousHashes;
	    }
}
