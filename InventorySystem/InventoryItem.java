package application;
import java.sql.Date;
import javafx.scene.image.Image;

public class InventoryItem {
    private int id;
    private String name;
    private String category;
    private String supplier;
    private double cost;
    private double price;
    private Integer quantity;
    private Date dateAdded;
    private Date expiryDate;
    private String description;
    private String location;
    private Image barcode;
    private int reorderLevel;
    private double discount;
    private String status;
   
    public InventoryItem(int id, String name, String category, String supplier, double cost, double price, int quantity, Date dateAdded, Date expiryDate, String description, String location, Image barcode, int reorderLevel, double discount, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.supplier = supplier;
        this.cost = cost;
        this.price = price;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
        this.expiryDate = expiryDate;
        this.description = description;
        this.location = location;
        this.barcode = barcode;
        this.reorderLevel = reorderLevel;
        this.discount = discount;
        this.status = status;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Date getDateAdded() {
        return dateAdded;
    }
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
    public Date getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Image getBarcode() {
        return barcode;
    }
    public void setBarcode(Image barcode) {
        this.barcode = barcode;
    }
    public int getReorderLevel() {
        return reorderLevel;
    }
    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "ID: " + id + "\n" +
               "Name: " + name + "\n" +
               "Category: " + category + "\n" +
               "Supplier: " + supplier + "\n" +
               "Cost: " + cost + "\n" +
               "Price: " + price + "\n" +
               "Quantity: " + quantity + "\n" +
               "Date Added: " + dateAdded + "\n" +
               "Expiry Date: " + expiryDate + "\n" +
               "Description: " + description + "\n" +
               "Location: " + location + "\n" +
               "Barcode: " + barcode + "\n" +
               "Reorder Level: " + reorderLevel + "\n" +
               "Discount: " + discount + "\n" +
               "Status: " + status;
    }
}
