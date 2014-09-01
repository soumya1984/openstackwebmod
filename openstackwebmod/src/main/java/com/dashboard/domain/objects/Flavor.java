package com.dashboard.domain.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="flavour")
public class Flavor {
    @XmlElement(name ="id")
	public String flavorId;
    @XmlElement(name = "name")
	public String flavourName;

	public Flavor(String flavorId, String flavourName) {
		super();
		this.flavorId = flavorId;
		this.flavourName = flavourName;
	}

	public String getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(String flavorId) {
		this.flavorId = flavorId;
	}

	public String getFlavourName() {
		return flavourName;
	}

	public void setFlavourName(String flavourName) {
		this.flavourName = flavourName;
	}

	@Override
	public String toString() {
		return "Flavor [flavorId=" + flavorId + ", flavourName=" + flavourName
				+ "]";
	}

}
