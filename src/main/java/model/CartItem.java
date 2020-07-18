package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Seyyed Parsa Neshaei on 6/24/20
 * All Rights Reserved
 */
public class CartItem extends Product {

    private int count;

    public CartItem(String productId, Status status, String name, String brand, int price, int discountPercent, ArrayList<Seller> sellers, int numberAvailable, Category category, String description, LocalDateTime dateCreated, HashMap<String, String> features, int count) {
        super(productId, status, name, brand, price, discountPercent, sellers, numberAvailable, category, description, dateCreated, features);
        this.count = count;
    }

    public CartItem(Product product, int count) {
        super(product.getProductId(), product.getStatus(), product.getName(), product.getBrand(), product.getPrice(), product.getDiscountPercent(), product.getSellers(), product.getNumberAvailable(), product.getCategory(), product.getDescription(), product.getDateCreated(), product.getFeatures());
        this.count = count;
    }

    public int getTotalPrice() {
        return getPrice() * count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
