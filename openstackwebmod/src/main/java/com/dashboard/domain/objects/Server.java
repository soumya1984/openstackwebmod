package com.dashboard.domain.objects;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "server")
public class Server {
	public static final long serialVersionUID = 1L;
	@XmlElement(name = "id")
	public String id;
	@XmlElement(name = "name")
	public String name;
	// public ServerAddresses addresses;
	@XmlElementRef(name = "image")
	public Image image;
	@XmlElementRef(name = "flavor")
	public Flavor flavor;
	@XmlElement(name = "ip4")
	public String accessIPv4;
	@XmlElement(name = "ip6")
	public String accessIPv6;
	@XmlElement(name = "status")
	public String status;
	@XmlElement(name = "progress")
	public Integer progress;
	@XmlElement(name = "tenentis")
	public String tenantId;
	@XmlElement(name = "usrid")
	public String userId;
	@XmlElement(name = "keyname")
	public String keyName;
	@XmlElement(name = "hostid")
	public String hostId;
	@XmlElement(name = "updt")
	public Date updated;
	@XmlElement(name = "credt")
	public Date created;
	@XmlElement(name = "upTime")
	public int upTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Flavor getFlavor() {
		return flavor;
	}

	public void setFlavor(Flavor flavor) {
		this.flavor = flavor;
	}

	public String getAccessIPv4() {
		return accessIPv4;
	}

	public void setAccessIPv4(String accessIPv4) {
		this.accessIPv4 = accessIPv4;
	}

	public String getAccessIPv6() {
		return accessIPv6;
	}

	public void setAccessIPv6(String accessIPv6) {
		this.accessIPv6 = accessIPv6;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public int getUpTime() {
		return upTime;
	}
	
	public void setUpTime(int upTime) {
		this.upTime = upTime;
	}

	@Override
	public String toString() {
		return "Server [id=" + id + ", name=" + name + ", image=" + image
				+ ", flavor=" + flavor + ", accessIPv4=" + accessIPv4
				+ ", accessIPv6=" + accessIPv6 + ", status=" + status
				+ ", progress=" + progress + ", tenantId=" + tenantId
				+ ", userId=" + userId + ", keyName=" + keyName + ", hostId="
				+ hostId + ", updated=" + updated + ", created=" + created + ",upTime=" + upTime 
				+ "]";
	}
}
