/*
 * Purpose : Contains all the methods related to Openstack access
 */
package com.dashboard.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jclouds.domain.Location;

import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.openstack.nova.v2_0.domain.Volume;

import com.dashboard.openstack.utils.OpenstackUtils;
import com.dashboard.resources.Instance;

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
	public static Connection authenticate(String username, String password,
			String provider, String tenantName) {
		Connection connection = null;
		if (provider.trim().equalsIgnoreCase("openstack")) {
			connection = OpenstackUtils.authenticate(username, password,
					tenantName);
		}
		return connection;
	}

	/**
	 * @param provider
	 *            - Cloud Service Provider
	 * @param compute
	 *            - ComputeService Object
	 * @returns list of nodes or servers
	 */
	public static List<NodeMetadata> listServers(String provider,
			ComputeService compute) {
		List<NodeMetadata> nodesList = new ArrayList<NodeMetadata>();
		if (provider.trim().equalsIgnoreCase("openstack")) {
			nodesList = OpenstackUtils.listServers(compute);
		}
		return nodesList;
	}

	/**
	 * @param provider
	 *            - Cloud Service Provider
	 * @param compute
	 *            - ComputeService Object
	 * @param flavorsMap
	 *            - Map of the Hardware Flavours
	 * @returns List of Instances
	 */
	public static List<Instance> listInstances(String provider,
			ComputeService compute, Map<Integer, Hardware> flavorsMap) {
		List<Instance> instancesList = new ArrayList<Instance>();
		List<NodeMetadata> nodesList = listServers(provider, compute);

		for (NodeMetadata node : nodesList) {
			int flavorId = 0;
			for (int i : flavorsMap.keySet()) {
				if (flavorsMap.get(i).equals(node.getHardware())) {
					flavorId = i;
					break;
				}
			}
			String[] idParts = node.getId().split("/");
			String id = idParts[1];
			String zoneId = idParts[0];
			System.out.println(id + " " + zoneId);
			Instance instance = new Instance(zoneId, id, node.getName(),
					node.getPrivateAddresses(), flavorId, node.getStatus());
			instancesList.add(instance);
		}
		return instancesList;
	}

	/**
	 * @param provider
	 *            - Cloud Service Provider
	 * @param compute
	 *            - ComputeService Object
	 * @param flavorsMap
	 *            -Map of the Hardware Flavours
	 * @returns Map of Instances
	 */
	public static Map<String, Instance> getInstances(String provider,
			ComputeService compute, Map<Integer, Hardware> flavorsMap) {
		List<Instance> instancesList;
		Map<String, Instance> instancesMap = new HashMap<String, Instance>();
		instancesList = Utils.listInstances(provider, compute, flavorsMap);
		int i = 1;
		for (Instance instance : instancesList) {
			String id = instance.getId();
			System.out.println("ZONE " + instance.getZoneId() + " id "
					+ instance.getId());
			// id = id.replace(instance.getZoneId()+"/", "");
			instancesMap.put(id, instance);
		}
		System.out.println("INSTNACES size " + instancesMap.size());
		return instancesMap;
	}

	/**
	 * @param provider
	 *            - Cloud Service Provider
	 * @param compute
	 *            - ComputeService Object
	 * @returns list of Images
	 */
	public static List<Image> listImages(String provider, ComputeService compute) {
		if (provider.trim().equalsIgnoreCase("openstack")) {
			return OpenstackUtils.listImages(compute);
		}
		return new ArrayList<Image>();
	}

	/**
	 * @param provider
	 *            - Cloud Service Provider
	 * @param compute
	 *            - ComputeService Object
	 * @returns map of Images
	 */
	public static Map<Integer, Image> getImages(String provider,
			ComputeService compute) {
		List<Image> imagesList = new ArrayList<Image>();
		Map<Integer, Image> imagesMap = new HashMap<Integer, Image>();

		if (provider.trim().equalsIgnoreCase("openstack")) {
			imagesList = OpenstackUtils.listImages(compute);
		}
		int i = 1;
		for (Image img : imagesList) {
			imagesMap.put(i++, img);
		}
		return imagesMap;
	}

	/**
	 * @param provider
	 *            - Cloud Service Provider
	 * @param compute
	 *            - ComputeService Object
	 * @returns List of Hardware Flavours
	 */
	public static List<Hardware> listFlavors(String provider,
			ComputeService compute) {
		if (provider.trim().equalsIgnoreCase("openstack")) {
			return OpenstackUtils.listHardware(compute);
		}
		return new ArrayList<Hardware>();
	}

	/**
	 * @param provider
	 *            - Cloud Service Provider
	 * @param compute
	 *            -computeService Object
	 * @returns map of Hardware Flavours
	 */
	public static Map<Integer, Hardware> getFlavors(String provider,
			ComputeService compute) {
		List<Hardware> flavorsList = new ArrayList<Hardware>();
		Map<Integer, Hardware> flavorsMap = new HashMap<Integer, Hardware>();

		if (provider.trim().equalsIgnoreCase("openstack")) {
			flavorsList = OpenstackUtils.listHardware(compute);
		}
		int i = 1;
		for (Hardware h : flavorsList) {
			flavorsMap.put(i++, h);
		}
		return flavorsMap;
	}

	/**
	 * @param compute
	 *            - ComputeServices Object
	 * @param imageId
	 *            - Id of the Image
	 * @param hardwareId
	 *            - Id of the Hardware Flavour
	 * @param provider
	 *            - Cloud Service Provider
	 * @returns true on successful creation of instance
	 */
	public static boolean createInstance(ComputeService compute,
			String imageId, String hardwareId, String provider) {
		if (provider.trim().equalsIgnoreCase("openstack")) {
			return OpenstackUtils.createInstance(compute, imageId, hardwareId);
		}
		return false;
	}

}
