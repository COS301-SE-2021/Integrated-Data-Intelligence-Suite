package com.bezkoder.spring.jpa.h2.model;

import javax.persistence.*;

@Entity
@Table(name = "tutorials")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "permissions")
	private String permissions;

	@Column(name = "description")
	private String description;

	public User() {

	}

	public User(String permissions, String description) {
		this.permissions = permissions;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	@Override
	public String toString() {
		return "Tutorial [id=" + id + ", permissions=" + permissions + ", desc=" + description +  "]";
	}

}
