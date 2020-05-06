package serverwebsocket.messages;

public abstract class Message {
    private String handler;

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}
