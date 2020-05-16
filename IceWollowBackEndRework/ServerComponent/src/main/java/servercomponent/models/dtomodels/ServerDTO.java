package servercomponent.models.dtomodels;

import lombok.Getter;
import lombok.Setter;
import servercomponent.models.IWServer;
import usercomponent.models.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ServerDTO {

    private int id;
    private String name;
    private List<User> users;
    private User owner;
    private String code;

    private List<ChannelDTO> channels = new ArrayList<>();

    public ServerDTO() {
    }

    public ServerDTO(IWServer server) {
        this.id = server.getId();
        this.name = server.getName();
        this.users = server.getUsers();
        this.code = server.getCode();
        server.getChannels().stream().forEach(c-> {
            this.channels.add(new ChannelDTO(c));
        });

        this.owner = server.getOwner();
    }
}
