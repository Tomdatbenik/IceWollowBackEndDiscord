package server.models.dtomodels;

import server.models.Channel;
import server.models.IWServer;
import user.models.User;

import java.util.ArrayList;
import java.util.List;

public class ServerDTO {

    private int id;
    private String name;
    private User owner;

    private List<ChannelDTO> channels = new ArrayList<>();

    public ServerDTO() {
    }

    public ServerDTO(IWServer server) {
        this.id = server.getId();
        this.name = server.getName();

        server.getChannels().stream().forEach(c-> {
            this.channels.add(new ChannelDTO(c));
        });

        this.owner = server.getOwner();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public List<ChannelDTO> getChannels() {
        return channels;
    }
}
