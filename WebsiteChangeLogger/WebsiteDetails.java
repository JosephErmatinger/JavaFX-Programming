package application;

public class WebsiteDetails {
    
	private String loginUrl;
    private String targetUrl;
    private String username;
    private String password;

    public WebsiteDetails(String loginUrl, String targetUrl, String username, String password) {
        this.loginUrl = loginUrl;
        this.targetUrl = targetUrl;
        this.username = username;
        this.password = password;
    }
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
