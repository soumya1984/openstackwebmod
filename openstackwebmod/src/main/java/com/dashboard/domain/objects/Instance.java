package com.dashboard.domain.objects;

import java.util.Set;

import org.jclouds.compute.domain.NodeMetadata.Status;

public class Instance {

	String id;
	String name;
	Set<String> ips;
	int flavorId;
	Status state;
	String zoneId;
	

	/**
	 * @returns Id of the Zone
	 */
	public String getZoneId() {
		return zoneId;
	}

	/**
	 * @param zoneId - Id of the Zone
	 */
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}


	/**
	 * @param zoneId - Id of the Zone
	 * @param id -Id of the Instance
	 * @param name - name of the Instance
	 * @param ips - set of Ip addresses
	 * @param flavorId - Id of the Hardware flavour
	 * @param status - status of the Instance
	 */
	public Instance(String zoneId, String id, String name, Set<String> ips, int flavorId, Status status) {
		super();
		this.zoneId = zoneId;
		this.id = id;
		this.name = name;
		this.ips = ips;
		this.flavorId = flavorId;
		this.state = status;
	}

	/**
	 * @returns Id of the Instance
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id - Id of the Instance
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @returns name of the Instance
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name - name of the Instance
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @returns set of Ip addresses
	 */
	public Set<String> getIps() {
		return ips;
	}

	/**
	 * @param ips - set of Ip addresses
	 */
	public void setIps(Set<String> ips) {
		this.ips = ips;
	}

	/**
	 * @returns Id of the Hardware Flavour
	 */
	public int getFlavorId() {
		return flavorId;
	}

	/**
	 * @param flavorId - Id of the Hardware Flavour
	 */
	public void setFlavorId(int flavorId) {
		this.flavorId = flavorId;
	}

	/**
	 * @returns status of the Instance
	 */
	public Status getState() {
		return state;
	}

	/**
	 * @param state - Status of the Instance
	 */
	public void setState(Status state) {
		this.state = state;
	}
	
	/**
	 * @returns Ipaddress of the Instance
	 */
	public String getIp() {
		String ip = "No IP";
		for(String i : getIps()){
			ip = i;
		}
		return ip;
	}
	
}
