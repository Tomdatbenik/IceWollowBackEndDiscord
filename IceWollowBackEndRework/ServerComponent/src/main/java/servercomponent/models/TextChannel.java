package servercomponent.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TextChannel")
public class TextChannel extends Channel {

    public TextChannel() {
    }

    public TextChannel(String name) {
        super(name);
    }
}
