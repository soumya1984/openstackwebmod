package com.dashboard.domain.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "serverlist")
public class ServerList {
    @XmlElementRef(name ="server")
	public ArrayList<Server> getServerList() {
		return serverList;
	}

	public void setServerList(List<Server> servers) {
		this.serverList = (ArrayList<Server>) servers;
	}

	public ArrayList<Server> serverList = new ArrayList<Server>();
}
