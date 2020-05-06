package websocketserver.models;

import com.google.gson.Gson;
import websocketserver.messages.BaseMessage;

public class ServerObserver {
    private Gson gson = new Gson();

    private IWServer server;
    private VoiceChannel channel;
    private Client client;

    public Client getClient() {
        return client;
    }

    public VoiceChannel getChannel() {
        return channel;
    }

    public void setChannel(VoiceChannel channel) {
        this.channel = channel;
    }

    public void setServer(IWServer server) {
        this.server = server;
    }

    public IWServer getServer() {
        return server;
    }

    public ServerObserver(Client client) {
        this.client = client;
    }

    public void update()
    {
        BaseMessage message = new BaseMessage();
        message.setContent(gson.toJson(server));
        message.setHandler("ServerUpdateHandler");

        client.sendMessage(message);
    }

}
