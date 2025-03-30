package application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CompanyData {
	
	    private final SimpleIntegerProperty rank;
	    private final SimpleStringProperty title;
	    private final SimpleStringProperty website;
	    private final SimpleIntegerProperty employees;
	    private final SimpleStringProperty sector;
	    private final SimpleStringProperty industry;
	    private final SimpleStringProperty hqlocation;
	    private final SimpleStringProperty hqaddr;
	    private final SimpleStringProperty hqcity;
	    private final SimpleStringProperty hqstate;
	    private final SimpleStringProperty hqzip;
	    private final SimpleStringProperty hqtel;
	    private final SimpleStringProperty ceo;
	    private final SimpleStringProperty ceoTitle;
	    private final SimpleStringProperty address;
	    private final SimpleStringProperty ticker;
	    private final SimpleStringProperty fullname;
	    private final SimpleStringProperty revenues;
	    private final SimpleStringProperty revchange;
	    private final SimpleStringProperty profits;
	    private final SimpleStringProperty prftchange;
	    private final SimpleStringProperty assets;
	    private final SimpleStringProperty totshequity;
                                                               
	    public CompanyData(int rank, String title, String website, int employees, String sector, String industry,
	                      String hqlocation, String hqaddr, String hqcity, String hqstate, String hqzip,
	                      String hqtel, String ceo, String ceoTitle, String address, String ticker,
	                      String fullname, String revenues, String revchange, String profits, String prftchange,
	                      String assets, String totshequity) {
	        this.rank = new SimpleIntegerProperty(rank);
	        this.title = new SimpleStringProperty(title);
	        this.website = new SimpleStringProperty(website);
	        this.employees = new SimpleIntegerProperty(employees);
	        this.sector = new SimpleStringProperty(sector);
	        this.industry = new SimpleStringProperty(industry);
	        this.hqlocation = new SimpleStringProperty(hqlocation);
	        this.hqaddr = new SimpleStringProperty(hqaddr);
	        this.hqcity = new SimpleStringProperty(hqcity);
	        this.hqstate = new SimpleStringProperty(hqstate);
	        this.hqzip = new SimpleStringProperty(hqzip);
	        this.hqtel = new SimpleStringProperty(hqtel);
	        this.ceo = new SimpleStringProperty(ceo);
	        this.ceoTitle = new SimpleStringProperty(ceoTitle);
	        this.address = new SimpleStringProperty(address);
	        this.ticker = new SimpleStringProperty(ticker);
	        this.fullname = new SimpleStringProperty(fullname);
	        this.revenues = new SimpleStringProperty(revenues);
	        this.revchange = new SimpleStringProperty(revchange);
	        this.profits = new SimpleStringProperty(profits);
	        this.prftchange = new SimpleStringProperty(prftchange);
	        this.assets = new SimpleStringProperty(assets);
	        this.totshequity = new SimpleStringProperty(totshequity);
	    }
	    public int getRank() {
	        return rank.get();
	    }
	    public String getTitle() {
	        return title.get();
	    }
	    public String getWebsite() {
	        return website.get();
	    }
	    public int getEmployees() {
	        return employees.get();
	    }
	    public String getSector() {
	        return sector.get();
	    }
	    public String getIndustry() {
	        return industry.get();
	    }
	    public String getHqlocation() {
	        return hqlocation.get();
	    }
	    public String getHqaddr() {
	        return hqaddr.get();
	    }
	    public String getHqcity() {
	        return hqcity.get();
	    }
	    public String getHqstate() {
	        return hqstate.get();
	    }
	    public String getHqzip() {
	        return hqzip.get();
	    }
	    public String getHqtel() {
	        return hqtel.get();
	    }
	    public String getCeo() {
	        return ceo.get();
	    }
	    public String getCeoTitle() {
	        return ceoTitle.get();
	    }
	    public String getAddress() {
	        return address.get();
	    }
	    public String getTicker() {
	        return ticker.get();
	    }
	    public String getFullname() {
	        return fullname.get();
	    }
	    public String getRevenues() {
	        return revenues.get();
	    }
	    public String getRevchange() {
	        return revchange.get();
	    }
	    public String getProfits() {
	        return profits.get();
	    }
	    public String getPrftchange() {
	        return prftchange.get();
	    }
	    public String getAssets() {
	        return assets.get();
	    }
	    public String getTotshequity() {
	        return totshequity.get();
	    }
	    public void setRank(int rank) {
	        this.rank.set(rank);
	    }
	    public void setTitle(String title) {
	        this.title.set(title);
	    }
	    public void setWebsite(String website) {
	        this.website.set(website);
	    }
	    public void setEmployees(int employees) {
	        this.employees.set(employees);
	    }
	    public void setSector(String sector) {
	        this.sector.set(sector);
	    }
	    public void setIndustry(String industry) {
	        this.industry.set(industry);
	    }
	    public void setHqlocation(String hqlocation) {
	        this.hqlocation.set(hqlocation);
	    }
	    public void setHqaddr(String hqaddr) {
	        this.hqaddr.set(hqaddr);
	    }
	    public void setHqcity(String hqcity) {
	        this.hqcity.set(hqcity);
	    }
	    public void setHqstate(String hqstate) {
	        this.hqstate.set(hqstate);
	    }
	    public void setHqzip(String hqzip) {
	        this.hqzip.set(hqzip);
	    }
	    public void setHqtel(String hqtel) {
	        this.hqtel.set(hqtel);
	    }
	    public void setCeo(String ceo) {
	        this.ceo.set(ceo);
	    }
	    public void setCeoTitle(String ceoTitle) {
	        this.ceoTitle.set(ceoTitle);
	    }
	    public void setAddress(String address) {
	        this.address.set(address);
	    }
	    public void setTicker(String ticker) {
	        this.ticker.set(ticker);
	    }
	    public void setFullname(String fullname) {
	        this.fullname.set(fullname);
	    }
	    public void setRevenues(String revenues) {
	        this.revenues.set(revenues);
	    }
	    public void setRevchange(String revchange) {
	        this.revchange.set(revchange);
	    }
	    public void setProfits(String profits) {
	        this.profits.set(profits);
	    }
	    public void setPrftchange(String prftchange) {
	        this.prftchange.set(prftchange);
	    }
	    public void setAssets(String assets) {
	        this.assets.set(assets);
	    }
	    public void setTotshequity(String totshequity) {
	        this.totshequity.set(totshequity);
	    }
}


