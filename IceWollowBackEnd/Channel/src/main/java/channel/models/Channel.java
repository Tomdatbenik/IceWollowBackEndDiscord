package channel.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import user.models.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Channel")
public class Channel {
    @GeneratedValue
    @Id
    @Column(name = "id", unique = true)
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    @Column(name="name")
    private String name;

    private List<User> users;
}
