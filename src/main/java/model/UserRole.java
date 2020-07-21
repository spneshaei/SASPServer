package model;

public enum UserRole {
    CUSTOMER, SELLER, ADMIN, ASSISTANT;

    @Override
    public String toString() {
        switch (this) {
            case ADMIN:
                return "admin";
            case SELLER:
                return "seller";
            case CUSTOMER:
                return "customer";
            case ASSISTANT:
                return "assistant";
            default:
                return "";
        }
    }
}
