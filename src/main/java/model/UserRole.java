package model;

public enum UserRole {
    CUSTOMER, SELLER, ADMIN;

    @Override
    public String toString() {
        switch (this) {
            case ADMIN:
                return "admin";
            case SELLER:
                return "seller";
            case CUSTOMER:
                return "customer";
            default:
                return "";
        }
    }
}
