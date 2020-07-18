package model.userdata;

public class PhoneNumber extends DataString {

    public PhoneNumber(String number) {
        super(number);
    }

    public static String getPattern() {
        return "[\\d\\-()]+";
    }
}
