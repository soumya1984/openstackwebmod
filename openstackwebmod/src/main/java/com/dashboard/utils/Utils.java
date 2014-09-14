/*
 * Purpose : Contains all the methods related to Openstack access
 */
package com.dashboard.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.compute.ComputeService;
import org.openstack4j.model.compute.Image;

import com.dashboard.domain.objects.ServerList;
import com.dashboard.openstack.utils.OpenstackUtils;
import com.dashboard.rest.request.bean.CreateServerRequest;

public class Utils {

	/**
	 * @param username
	 *            - Username for openstack Account
	 * @param password
	 *            - Password for openstack Account
	 * @param provider
	 *            - Cloud Service Provider
	 * @param tenantName
	 *            - tenentname of the openstack account
	 * @returns the connection Object on successful authentication
	 */
	public static ComputeService authenticate(String username, String password,
			String provider, String tenantName) {
		org.openstack4j.api.compute.ComputeService computeService = null;
		if (provider.trim().equalsIgnoreCase("openstack")) {
			computeService = OpenstackUtils.authenticate(username, password,
					tenantName);
		}
		return computeService;
	}

	/**
	 * @param username
	 *            - Username for openstack Account
	 * @param password
	 *            - Password for openstack Account
	 * @param provider
	 *            - Cloud Service Provider
	 * @param tenantName
	 *            - tenentname of the openstack account
	 * @returns the connection Object on successful authentication
	 */
	public static OSClient getOSClient(String username, String password,
			String provider, String tenantName) {
		OSClient osClient = null;
		if (provider.trim().equalsIgnoreCase("openstack")) {
			osClient = OpenstackUtils.getOSClient(username, password,
					tenantName);
		}
		return osClient;
	}

	/**
	 * @param provider
	 *            - Cloud Service Provider
	 * @param compute
	 *            - ComputeService Object
	 * @returns map of Images
	 */
	public static List<Image> getImages(String provider, ComputeService compute) {
		List<Image> imagesList = new ArrayList<Image>();
		if (provider.trim().equalsIgnoreCase("openstack")) {
			imagesList = OpenstackUtils.listImages(compute);
		}
		return imagesList;
	}

	/**
	 * @param provider
	 *            - Cloud Service Provider
	 * @param compute
	 *            - ComputeService Object
	 * @returns map of Images
	 */
	public  ServerList getServerList(String provider,
			ComputeService compute) {
		ServerList serverList = null;
		if (provider.trim().equalsIgnoreCase("openstack")) {
			serverList = OpenstackUtils.listServerDetails(compute);
		}
		return serverList;
	}

	/**
	 * 
	 * @param provider
	 * @param compute
	 * @return
	 */
	public  List<com.dashboard.domain.objects.Server> createServers(List<CreateServerRequest> list,String provider,OSClient osc) {
		List<com.dashboard.domain.objects.Server> serversList=null;
		if (provider.trim().equalsIgnoreCase("openstack")) {
			OpenstackUtils util = new OpenstackUtils();
			serversList = util.buildServers(list,osc);
		}
		return serversList;
	}
	public  String doneActionOnServer(String serverId,OSClient os,String action,String provider) {
		String response=null;
		if (provider.trim().equalsIgnoreCase("openstack")) {
			OpenstackUtils util = new OpenstackUtils();
			response = util.doneActionOnServer(serverId, os, action);
		}
		return response;
	}
}
