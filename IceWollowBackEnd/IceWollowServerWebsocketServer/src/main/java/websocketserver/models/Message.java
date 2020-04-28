package websocketserver.models;

import websocketserver.interfaces.IHandler;

public class Message {
    private String content;
    private String handler;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}
