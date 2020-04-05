package server.models;

import javax.persistence.*;

@Entity
@Table(name = "VoiceChannel")
public class VoiceChannel extends Channel{
    public VoiceChannel() {
    }

    public VoiceChannel(String name) {
        super(name);
    }
}
