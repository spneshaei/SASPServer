package model;

public class Message {
    private String id;
    private String sender;
    private String content;

    public Message(String id, String sender, String content) {
        this.id = id;
        this.sender = sender;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public Account getSender() {
        return DataManager.shared().getAccountWithGivenUsername(sender);
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        Account sender = getSender();
        return sender.getFirstName() + " " + sender.getLastName() + ": " + getContent();
    }
}
