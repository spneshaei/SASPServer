package model;

public enum SaleStatus {
    UNDER_REVIEW_TO_MAKE, UNDER_REVIEW_TO_MODIFY, CONFIRMED;

    @Override
    public String toString() {
        if (this.equals(UNDER_REVIEW_TO_MAKE)) {
            return "Under review to make";
        } else if (this.equals(UNDER_REVIEW_TO_MODIFY)) {
            return "Under review to modify";
        } else {
            return "Confirmed";
        }
    }
}
