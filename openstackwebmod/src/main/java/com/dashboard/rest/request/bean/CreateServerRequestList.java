package com.dashboard.rest.request.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateServerRequestList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CreateServerRequest> request = new ArrayList<CreateServerRequest>();

	@XmlElementRef
	public List<CreateServerRequest> getRequest() {
		return request;
	}

	public void setRequest(List<CreateServerRequest> list) {
		this.request = list;

	}

}
