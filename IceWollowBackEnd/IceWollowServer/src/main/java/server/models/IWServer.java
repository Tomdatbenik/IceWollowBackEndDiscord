package server.models;

import channel.models.Channel;
import com.fasterxml.jackson.annotation.JsonProperty;
import user.models.User;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "Server")
public class IWServer {
    @GeneratedValue
    @Id
    @Column(name = "id", unique = true)
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    @Column(name="name")
    private String name;

    @JsonProperty("users")
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @ManyToMany
    private List<User> users;

    @JsonProperty("channels")
    @JoinColumn(name = "channel_id",referencedColumnName = "id")
    @OneToMany
    private List<Channel> channels;

}
