package model;

public enum DeliveryStatus {
    WAITING, SENT;

    @Override
    public String toString() {
        if (this.equals(WAITING)) {
            return "در انتظار ارسال";
        } else if (this.equals(SENT)) {
            return "ارسال شده";
        } else {
            return "";
        }
    }
}
