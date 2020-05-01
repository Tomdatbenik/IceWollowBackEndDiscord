package serverlogic.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import serverlogic.models.dtomodels.ChannelDTO;

import javax.persistence.*;


@MappedSuperclass
public  abstract class Channel {
    @GeneratedValue
    @Id
    @Column(name = "id", unique = true)
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    @Column(name="name")
    private String name;

    public Channel() {
    }

    public Channel(ChannelDTO channelDTO) {
        this.name = channelDTO.getName();
    }

    public Channel(String name ) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
