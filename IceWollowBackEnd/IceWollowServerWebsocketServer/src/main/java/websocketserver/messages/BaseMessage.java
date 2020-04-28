package websocketserver.messages;

public class BaseMessage extends Message {
    private String content;
    private String messageType;

    public String getMessageType() {
        return messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
