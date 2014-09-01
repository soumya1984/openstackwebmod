package com.dashboard.domain.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {
	@XmlElement(name = "name")
	public String name;
	@XmlElement(name = "id")
	public String id;
	@XmlElement(name = "role")
	public String role;
	@XmlElement(name = "project")
	public String project;

	public User(String name, String id, String role, String project) {
		super();
		this.name = name;
		this.id = id;
		this.role = role;
		this.project = project;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

}
