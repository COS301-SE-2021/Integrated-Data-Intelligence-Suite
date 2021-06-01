package za.ac.up.cs.emerge.integrateddataintelligencesuite.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class user {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String permission;

    public user(){

    }

    public user(String firstName, String lastName, String permission) {
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

    public String getPermission() {
        return permission;
    }
}
