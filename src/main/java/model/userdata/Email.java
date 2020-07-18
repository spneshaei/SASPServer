package model.userdata;

public class Email extends DataString {
    public Email(String data) {
        super(data);
    }

    public static String getPattern() {
        return "([\\w!#$%&'*+\\-/=?^_`{|}~]\\.?)+@[\\w\\-]+\\.\\w+";
    }
}
