package com.dashboard.domain.objects;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="userList")
public class UserList {
	@XmlElementRef(name="user")
	ArrayList<User> userList = new ArrayList<User>();

	public ArrayList<User> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

}
