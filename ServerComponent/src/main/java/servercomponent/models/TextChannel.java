package servercomponent.models;

import javax.persistence.Entity;
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
