package model.userdata;

public abstract class DataString {
    private String data;

    public DataString(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static String getPattern() {
        return ".*";
    }

    @Override
    public String toString() {
        return data;
    }
}
