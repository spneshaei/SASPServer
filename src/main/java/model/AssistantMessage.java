package model;

public class AssistantMessage extends Message {

    private String receiver;

    public AssistantMessage(String id, String sender, String receiver, String content) {
        super(id, sender, content);
        this.receiver = receiver;
    }

    public Account getReceiver() {
        return DataManager.shared().getAccountWithGivenUsername(receiver);
    }

    @Override
    public String toString() {
        return "پیام از " + getSender().getUsername() + " به " + getReceiver().getUsername() + ":" + "\n" + getContent();
    }
}
