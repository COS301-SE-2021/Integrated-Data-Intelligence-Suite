package com.User_Service.User_Service.dataclass;

import com.User_Service.User_Service.rri.Permission;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity(name = "users")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(
            generator = "user_sequence"
    )
    @GenericGenerator(
            name = "user_sequence",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID id;

    String firstName;

    String lastName;

    @Column(unique = true)
    String username;

    @Column(unique = true)
    String email;

    String password;

    Boolean isAdmin;

    @Enumerated(EnumType.STRING)
    Permission permission;

    String verificationCode;

    Boolean isVerified;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    Date dateCreated;

    @ElementCollection
            @CollectionTable(name = "report_ids")
    List<String> reportIDs = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "models",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "selected")
    @Column(name = "model_id")
    private Map<String, Boolean> models = new HashMap<>();


    public User() {

    }

    public User(String firstName, String lastName, String username, String email, String password, Permission permission) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = false;
        this.isVerified = false;
        this.permission = permission;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void addReportID(String reportID) {
        reportIDs.add(reportID);
    }

    public void removeReportID(String reportID) {
        reportIDs.remove(reportID);
    }

    public List<String> getReportIDs() {
        return reportIDs;
    }

    public void setReportIDs(ArrayList<String> reportIDs) {
        this.reportIDs = reportIDs;
    }

    public Map<String, Boolean> getModels() {
        return models;
    }

    public void setModels(Map<String, Boolean> models) {
        this.models = models;
    }

    public void selectModel(String modelID) {
        this.models.replace(modelID, true);
    }

    public void deselectModel(String modelID) {
        this.models.remove(modelID, false);
    }

    public void addModel(String modelID) {
        this.models.put(modelID, false);
    }

    public void removeModel(String modelID) {
        this.models.remove(modelID);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", permission=" + permission +
                '}';
    }
}
