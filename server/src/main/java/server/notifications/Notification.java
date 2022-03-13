package server.notifications;

public class Notification {
    private String messageContent;

    public Notification(String messageContent) {
        this.messageContent = messageContent;
    }

    public static String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
