package model;

import java.time.LocalDateTime;

public class IPRecord {
    private String ip;
    private String date;

    public IPRecord(String ip, LocalDateTime date) {
        this.ip = ip;
        this.date = DataManager.stringFromDate(date);
    }

    public String getIp() {
        return ip;
    }

    public LocalDateTime getDate() {
        return DataManager.dateFromString(date);
    }
}
