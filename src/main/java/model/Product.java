package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Product {
    private String productId;
    // TODO: enum in Gson?
    private Status status;
    private String name;
    private String brand;
    private int price;
    private int discountPercent;
    private int visitCount = 0;
    private ArrayList<String> sellers;
    private int numberAvailable;
    private String category;
    private String description;
    private String imageURL;
    private int[] slides;
    private ArrayList<Comment> comments;
    private ArrayList<Score> scores;
    private String dateCreated;
    private String currentSeller = "";
    private boolean isSelected = false;
    private HashMap<String, String> features;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public HashMap<String, String> getFeatures() {
        return features;
    }

    public void setFeatures(HashMap<String, String> features) {
        this.features = features;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Product(String productId, Status status, String name, String brand, int price, int discountPercent, ArrayList<Seller> sellers, int numberAvailable, Category category, String description, LocalDateTime dateCreated, HashMap<String, String> features) {
        this.productId = productId;
        this.status = status;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.discountPercent = discountPercent;
        this.sellers = sellers.stream().map(Account::getUsername).collect(Collectors.toCollection(ArrayList::new));
        this.numberAvailable = numberAvailable;
        this.category = category.getId();
        this.description = description;
        this.comments = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.dateCreated = DataManager.stringFromDate(dateCreated);
        this.features = features;
//        this.slides = new int[]{R.drawable.image_asset1, R.raw.ligntness};
    }

    public Seller getCurrentSeller() {
        if (currentSeller.equals("") && sellers.size() > 0) return (Seller) DataManager.shared().getAccountWithGivenUsername(sellers.get(0));
        return (Seller) DataManager.shared().getAccountWithGivenUsername(currentSeller);
    }

    public void setCurrentSeller(Seller currentSeller) {
        this.currentSeller = currentSeller.getUsername();
    }

    public LocalDateTime getDateCreated() {
        return DataManager.dateFromString(dateCreated);
    }

    public Status getStatus() {
        return status;
    }

    public String getBrand() {
        return brand;
    }

    public int getNumberAvailable() {
        return numberAvailable;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public ArrayList<Seller> getSellers() {
        return sellers.stream().map(id -> (Seller) DataManager.shared().getAccountWithGivenUsername(id)).collect(Collectors.toCollection(ArrayList::new));
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
        DataManager.saveData();
    }

    public int getVisitCount() {
        return visitCount;
    }

    public Category getCategory() {
        return DataManager.shared().getCategoryWithId(category);
    }

    public void incrementVisitCount() {
        visitCount += 1;
        DataManager.saveData();
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageScore() {
        if (scores.size() == 0) return 0;
        int total = 0;
        for (Score score : scores) {
            total += score.getScore();
        }
        return (double) (total) / scores.size();
    }

    public void addSeller(Seller seller) {

        DataManager.saveData();
    }

    public void addCustomer(Customer customer) {

        DataManager.saveData();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        DataManager.saveData();
    }

    public void addScore(int rating, Customer customer) {
        scores.add(new Score(DataManager.getNewId(), customer, rating));
        DataManager.saveData();
    }

    public void decrementNumberAvailable() {
        if (numberAvailable > 0) numberAvailable -= 1;
        else numberAvailable = 0;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product))
            return false;
        return this.productId.equals(((Product) obj).productId);
    }
}