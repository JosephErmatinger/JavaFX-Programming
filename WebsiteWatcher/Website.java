package application;

public class Website {
	
	int ID;
	String Url, HTML;
	
	public Website(int ID, String Url, String HTML) {
		this.ID = ID;
		this.Url = Url;
		this.HTML = HTML;
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public String getUrl() {
		return Url;
	}
	public void setPageUrl(String Url) {
		this.Url = Url;
	}
	public String getHTML() {
		return HTML;
	}
	public void setHTML(String HTML) {
		this.HTML = HTML;
	}
}
