package servercomponent.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import usercomponent.models.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Server")
@Getter
@Setter
public class IWServer {
    @GeneratedValue
    @Id
    @Column(name = "id", unique = true)
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonProperty("code")
    @Column(name = "code", unique = true)
    private String code;

    @JsonProperty("users")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users = new ArrayList<>();

    @JsonProperty("voiceChannels")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "server_voice_channels", joinColumns = @JoinColumn(name = "voice_channels"), inverseJoinColumns = @JoinColumn(name = "id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VoiceChannel> voiceChannels = new ArrayList<>();

    @JsonProperty("textChannels")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "server_text_channels", joinColumns = @JoinColumn(name = "text_channels"), inverseJoinColumns = @JoinColumn(name = "id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<TextChannel> textChannels = new ArrayList<>();

    @JsonProperty("staff")
    @JoinColumn(name= "Owner", referencedColumnName = "id")
    @ManyToOne
    private User owner;

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
