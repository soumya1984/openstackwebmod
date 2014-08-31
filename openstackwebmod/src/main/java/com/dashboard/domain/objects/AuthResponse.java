package com.dashboard.domain.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "access")
public class AuthResponse {

	@XmlElement(name = "username")
	public String userName;
	@XmlElement(name = "password")
	public String password;
	@XmlElement(name = "authtoken")
	public String authToken;
	@XmlElement(name = "tenentid")
	public String tenentId;

	public AuthResponse(String userName, String password, 
			String authToken, String tenentId) {
		super();
		this.userName = userName;
		this.password = password;
		this.authToken = authToken;
		this.tenentId = tenentId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getTenentId() {
		return tenentId;
	}

	public void setTenentId(String tenentId) {
		this.tenentId = tenentId;
	}

}
