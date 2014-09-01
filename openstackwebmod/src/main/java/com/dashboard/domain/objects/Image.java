package com.dashboard.domain.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "image")
public class Image {
	@XmlElement(name = "name")
	public String imageName;
	@XmlElement(name = "id")
	public String id;

	public Image(String imageName, String id) {
		super();
		this.imageName = imageName;
		this.id = id;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Image [imageName=" + imageName + ", id=" + id + "]";
	}
}
