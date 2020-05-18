package restmodule.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
@Getter
@Setter
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
}
