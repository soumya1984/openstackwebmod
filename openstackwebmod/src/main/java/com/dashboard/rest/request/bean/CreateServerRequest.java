package com.dashboard.rest.request.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "server")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "servername", "imagename", "flavour" })
public class CreateServerRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String servername;
	public String imageName;
	public String flavour;

	public CreateServerRequest(String servername, String imageName,
			String flavour) {
		super();
		this.servername = servername;
		this.imageName = imageName;
		this.flavour = flavour;
	}
	@XmlElement(name = "servername")
	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}
	@XmlElement(name = "imagename")
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	@XmlElement(name = "flavour")
	public String getFlavour() {
		return flavour;
	}

	public void setFlavour(String flavour) {
		this.flavour = flavour;
	}

	@Override
	public String toString() {
		return "server{" + "servername=" + servername + ", imageName='"
				+ imageName + '\'' + ", flavour='" + flavour + '}';
	}
}
