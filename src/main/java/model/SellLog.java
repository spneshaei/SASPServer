package model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class SellLog extends Log {
    private String seller;

    public SellLog(LocalDateTime dateTime, HashMap<Product, Integer> products, String id, long price, Seller seller, int discountAmount, DeliveryStatus deliveryStatus) {
        super(id, dateTime, price, discountAmount, products, deliveryStatus);
        this.seller = seller.getUsername();
    }

    public Seller getSeller() {
        return (Seller) DataManager.shared().getAccountWithGivenUsername(seller);
    }
}
