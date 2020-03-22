package user.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="User")
public class User {

    @Id
    @Column(name="id", unique = true)
    private int id;

    @Column(name="displayName")
    private String displayName;

    @Column(name="email", unique = true)
    private String email;
}
