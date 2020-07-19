package model;

public class Message {
    private String id;
    private String sender;
    private String content;

    public Message(String id, String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public Customer getSender() {
        return (Customer) DataManager.shared().getAccountWithGivenUsername(sender);
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        Customer sender = getSender();
        return sender.getFirstName() + " " + sender.getLastName() + ": " + getContent();
    }
}
