package servercomponent.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import usercomponent.models.User;

import javax.persistence.*;
import java.util.ArrayList;
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
    @Column(name = "name")
    private String name;

    @JsonProperty("users")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users = new ArrayList<>();

    @JsonProperty("channels")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "server_voice_channels", joinColumns = @JoinColumn(name = "voice_channels"), inverseJoinColumns = @JoinColumn(name = "id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VoiceChannel> voiceChannels = new ArrayList<>();

    @JsonProperty("channels")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "server_text_channels", joinColumns = @JoinColumn(name = "text_channels"), inverseJoinColumns = @JoinColumn(name = "id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<TextChannel> textChannels = new ArrayList<>();

    @JsonProperty("staff")
    @JoinColumn(name= "Owner", referencedColumnName = "id")
    @ManyToOne
    private User owner;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Channel> getChannels() {

        List<Channel> channels = new ArrayList<>();

        textChannels.stream().forEach(c -> channels.add(c));
        voiceChannels.stream().forEach(c -> channels.add(c));

        return channels;
    }

    public List<VoiceChannel> getVoiceChannels() {
        return voiceChannels;
    }

    public void setVoiceChannels(List<VoiceChannel> voiceChannels) {
        this.voiceChannels = voiceChannels;
    }

    public List<TextChannel> getTextChannels() {
        return textChannels;
    }

    public void setTextChannels(List<TextChannel> textChannels) {
        this.textChannels = textChannels;
    }
}
