package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Sale {
    private String offId;
    private ArrayList<String> products;
    private SaleStatus saleStatus;
    private int discountAmount;
    private String startTime;
    private String endTime;
    private String seller;
    private DeliveryStatus deliveryStatus;

    public Sale(String offId, ArrayList<Product> products, SaleStatus saleStatus, int discountAmount, LocalDateTime startTime, LocalDateTime endTime, Seller seller, DeliveryStatus deliveryStatus) {
        this.offId = offId;
        this.products =  products.stream().map(Product::getProductId).collect(Collectors.toCollection(ArrayList::new));
        this.saleStatus = saleStatus;
        this.discountAmount = discountAmount;
        this.startTime = DataManager.stringFromDate(startTime);
        this.endTime = DataManager.stringFromDate(endTime);
        this.seller = seller.getUsername();
        this.deliveryStatus = deliveryStatus;
    }

    public String getOffId() {
        return offId;
    }

    public ArrayList<Product> getProducts() {
        return products.stream().map(id -> DataManager.shared().getProductWithId(id)).collect(Collectors.toCollection(ArrayList::new));
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public LocalDateTime getStartTime() {
        return DataManager.dateFromString(startTime);
    }

    public LocalDateTime getEndTime() {
        return DataManager.dateFromString(endTime);
    }

    public Seller getSeller() {
        return (Seller) DataManager.shared().getAccountWithGivenUsername(seller);
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void addProduct(Product product) {
        products.add(product.getProductId());
        DataManager.saveData();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("حراج " + offId + "\n\n");
        result.append("محصولات:" + "\n");
        for (String product : products) {
            result.append(DataManager.shared().getProductWithId(product).getName()).append("\n");
        }
        result.append("\n" + "میزان تخفیف: ").append(discountAmount).append("\n\n");
        result.append("زمان شروع حراج: ").append(startTime).append("\n\n");
        result.append("زمان پایان حراج: ").append(endTime).append("\n\n");
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), getEndTime());
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), getEndTime());
        result.append("زمان باقی‌مانده: ").append(hours).append(" ساعت و ").append(minutes).append(" دقیقه").append("\n\n");
        result.append("فروشنده: ").append(DataManager.shared().getAccountWithGivenUsername(seller).getFirstName()).append(" ")
                .append(DataManager.shared().getAccountWithGivenUsername(seller).getLastName()).append("\n");
        return result.toString();
    }
}
