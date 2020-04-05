package server.models.dtomodels;

import server.models.Channel;
import server.models.IWServer;

import java.util.List;

public class ServerDTO {

    private int id;
    private String name;

    private List<Channel> channels;

    public ServerDTO() {
    }

    public ServerDTO(IWServer server) {
        this.id = server.getId();
        this.name = server.getName();
        this.channels = server.getChannels();
    }
}
