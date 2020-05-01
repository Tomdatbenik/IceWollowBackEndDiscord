package serverlogic.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import chatlogic.models.Chat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TextChannel")
public class TextChannel extends Channel {
    @JsonProperty("Chat")
    @JoinColumn(name = "Chat",referencedColumnName = "id")
    @OneToOne
    public Chat chat;

    public TextChannel() {
    }

    public TextChannel(String name) {
        super(name);
    }
}
