package com.dashboard.rest.request.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "serverslist")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateServerRequestList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CreateServerRequest> server = new ArrayList<CreateServerRequest>();

	@XmlElementRef
	public List<CreateServerRequest> getRequest() {
		return server;
	}
	
	public void setRequest(List<CreateServerRequest> list) {
		this.server = list;

	}

}
