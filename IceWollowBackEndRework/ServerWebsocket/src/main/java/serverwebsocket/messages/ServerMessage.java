package serverwebsocket.messages;

public class ServerMessage extends BaseMessage{

    private String user_id;
    private String server_id;

    public String getUser_id() {
        return user_id;
    }

    public String getServer_id() {
        return server_id;
    }
}
