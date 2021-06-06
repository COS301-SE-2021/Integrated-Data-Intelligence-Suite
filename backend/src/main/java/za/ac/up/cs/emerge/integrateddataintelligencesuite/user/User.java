package za.ac.up.cs.emerge.integrateddataintelligencesuite.user;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Permissions permission;

    public User(){

    }

    public User(String firstName, String lastName, Permissions permission) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Permissions getPermission() {
        return permission;
    }
}
