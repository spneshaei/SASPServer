package model;

public enum DeliveryStatus {
    ORDERED, PROCESSED, QUEUED, DELIVERED;

    @Override
    public String toString() {
        if (this.equals(ORDERED)) {
            return "Ordered";
        } else if (this.equals(PROCESSED)) {
            return "Processed";
        } else if (this.equals(QUEUED)) {
            return "Queued";
        } else if (this.equals(DELIVERED)) {
            return "Delivered";
        } else {
            return "";
        }
    }
}
