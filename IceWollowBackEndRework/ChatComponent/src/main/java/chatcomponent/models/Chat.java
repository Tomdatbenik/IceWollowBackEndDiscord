package chatcomponent.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import usercomponent.models.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Chat")
public class Chat {
    @GeneratedValue
    @Id
    @Column(name = "id", unique = true)
    @JsonProperty("id")
    private int id;

    @JsonProperty("users")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "chat_users", joinColumns = @JoinColumn(name = "chat_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users;
}
