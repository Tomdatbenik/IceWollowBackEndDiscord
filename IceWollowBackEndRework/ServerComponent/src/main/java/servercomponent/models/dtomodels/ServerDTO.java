package servercomponent.models.dtomodels;

import servercomponent.models.IWServer;
import usercomponent.models.User;

import java.util.ArrayList;
import java.util.List;

public class ServerDTO {

    private int id;
    private String name;
    private List<User> users;
    private User owner;

    private List<ChannelDTO> channels = new ArrayList<>();

    public ServerDTO() {
    }

    public ServerDTO(IWServer server) {
        this.id = server.getId();
        this.name = server.getName();
        this.users = server.getUsers();

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

    public List<User> getUsers() {
        return users;
    }

    public List<ChannelDTO> getChannels() {
        return channels;
    }
}
