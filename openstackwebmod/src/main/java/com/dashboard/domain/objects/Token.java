package com.dashboard.domain.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="token")
public class Token {

	public String id;
	
	public String issuedat;
	public String expireat;
	@XmlElement(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@XmlElement(name="issued_at")
	public String getIssuedat() {
		return issuedat;
	}

	public void setIssuedat(String issuedat) {
		this.issuedat = issuedat;
	}
	@XmlElement(name="expires")
	public String getExpireat() {
		return expireat;
	}

	public void setExpireat(String expireat) {
		this.expireat = expireat;
	}

	public Token(String id, String issuedat, String expireat) {
		super();
		this.id = id;
		this.issuedat = issuedat;
		this.expireat = expireat;
	}

}
