package model;

public enum CommentStatus {
    WAITING_FOR_REVIEW, DISALLOWED, CONFIRMED;

    @Override
    public String toString() {
        if (this.equals(WAITING_FOR_REVIEW)) {
            return "Waiting for review";
        } else if (this.equals(DISALLOWED)) {
            return "Disallowed";
        } else if (this.equals(CONFIRMED)) {
            return "Confirmed";
        } else {
            return "";
        }
    }
}
