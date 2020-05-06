package serverwebsocket.messages;

public class ChannelMessage extends BaseMessage{

    private String user_id;
    private String channel_id;

    public String getUser_id() {
        return user_id;
    }

    public String getChannel_id() {
        return channel_id;
    }
}
