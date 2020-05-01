package servercomponent.models;

import usercomponent.models.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "VoiceChannel")
public class VoiceChannel extends Channel{
    public VoiceChannel() {
    }

    @Transient
    private List<User> users = new ArrayList<User>();

    public List<User> getUsers() {
        return users;
    }

    public VoiceChannel(String name) {
        super(name);
    }
}
