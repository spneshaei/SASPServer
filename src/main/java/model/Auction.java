package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Auction {
    private String id;
    private String product;
    private int priceUpToNow = 0;
    private String lastCustomer = "";
    private String endTime = "";
    private ArrayList<Message> messages = new ArrayList<>();

    public String getId() {
        return id;
    }

    public LocalDateTime getEndTime() {
        return DataManager.dateFromString(endTime);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = DataManager.stringFromDate(endTime);
    }

    public Product getProduct() {
        return DataManager.shared().getProductWithId(product);
    }

    public int getPriceUpToNow() {
        return priceUpToNow;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Customer getLastCustomer() {
        return (Customer) DataManager.shared().getAccountWithGivenUsername(lastCustomer);
    }

    public void setLastCustomer(Customer lastCustomer) {
        this.lastCustomer = lastCustomer.getUsername();
    }

    public void setPriceUpToNow(int newPrice) {
        if (newPrice > this.priceUpToNow) this.priceUpToNow = newPrice;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public Auction(String id, Product product) {
        this.id = id;
        this.product = product.getProductId();
    }

    @Override
    public String toString() {
        return id;
    }
}
