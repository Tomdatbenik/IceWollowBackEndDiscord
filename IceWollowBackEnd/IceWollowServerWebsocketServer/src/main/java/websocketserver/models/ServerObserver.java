package websocketserver.models;

import com.google.gson.Gson;
import server.models.IWServer;
import websocketserver.messages.BaseMessage;

public class ServerObserver {
    private Gson gson = new Gson();

    private IWServer server;
    private Client client;

    public Client getClient() {
        return client;
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

    public void detectChange(){

    }

    public void update()
    {
        BaseMessage message = new BaseMessage();
        message.setContent(gson.toJson(server));
        message.setHandler("ServerUpdateHandler");

        client.SendMessage(message);
    }

}
