package usercomponent.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="User")
public class User {
    @GeneratedValue
    @Id
    @Column(name="id", unique = true)
    @JsonProperty("id")
    private int id;

    @JsonProperty("displayName")
    @Column(name="displayName")
    private String displayName;

    @JsonProperty("email")
    @Column(name="email")
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
    }
}
