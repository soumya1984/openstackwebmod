/**
 * Purpose : Contains all the methods related to Openstack access.
 */
package com.dashboard.openstack.utils;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;

import com.dashboard.resources.Instance;
import com.dashboard.utils.Connection;
import com.dashboard.utils.Utils;
import com.google.inject.Module;

public class OpenstackUtils {

	/**
	 * @param username
	 *            - username for Openstack account
	 * @param password
	 *            - password for openstack account
	 * @param tenantName
	 *            - tenantName for openstack account
	 * @returns the connection object on successful authentication
	 */
	public static Connection authenticate(String username, String password,
			String tenantName) {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				e.printStackTrace();
				System.exit(1);
			}
		});
		Connection conn;
		String provider = "openstack-nova";
		String identity = tenantName + ":" + username;
		String apiKey = password;
		ContextBuilder cb = ContextBuilder.newBuilder(provider);
		cb.endpoint("http://192.168.56.1/:5000/v2.0");
		ComputeServiceContext context = cb.credentials(identity, apiKey)
				.buildView(ComputeServiceContext.class);
		ComputeService compute = context.getComputeService();
		conn = new Connection();
		conn.compute = compute;
		conn.context = context;
		System.out.println("Connection " + conn);
		// flagAuth = 0;
		return conn;
	}

	/**
	 * @param compute
	 *            - ComputeService Object
	 * @returns list of instances in Openstack
	 */

	public static List<NodeMetadata> listServers(ComputeService compute) {
		Set<? extends ComputeMetadata> nodes = compute.listNodes();
		List<NodeMetadata> instancesList = new ArrayList<NodeMetadata>();
		if (nodes != null) {
			for (ComputeMetadata node : nodes) {
				NodeMetadata metadata = compute.getNodeMetadata(node.getId());
				instancesList.add(metadata);
			}
		}
		return instancesList;
	}

	/**
	 * @param compute
	 *            - ComputeService Object
	 * @returns List of Images available in Openstack
	 */
	public static List<Image> listImages(ComputeService compute) {
		Set<? extends Image> images = compute.listImages();
		if (images != null) {
			return new ArrayList<Image>(images);
		}
		return new ArrayList<Image>();
	}

	/**
	 * @param compute
	 *            - ComputeService Object
	 * @returns List of Hardware Flavours available in Openstack
	 */
	public static List<Hardware> listHardware(ComputeService compute) {
		Set<? extends Hardware> flavors = compute.listHardwareProfiles();
		if (flavors != null) {
			return new ArrayList<Hardware>(flavors);
		}
		return new ArrayList<Hardware>();

	}

	/**
	 * @param compute
	 *            - ComputeService Object
	 * @param imageId
	 *            - Id of the Image
	 * @param hardwareId
	 *            - Id of the Hardware Flavour
	 * @returns true on successful creation of Instance
	 */
	public static boolean createInstance(ComputeService compute,
			String imageId, String hardwareId) {
		try {
			String groupName = "graphical";
			TemplateBuilder templateBuilder = compute.templateBuilder();
			templateBuilder.hardwareId(hardwareId); // provider id or id in
													// Hardware Profile
			templateBuilder.imageId(imageId); // Image id in Image
			Template t = templateBuilder.build();
			System.out.println(">> adding node to group " + groupName);

			Set<? extends NodeMetadata> set = compute.createNodesInGroup(
					groupName, 1, templateBuilder.build());
			if (set.size() > 0) {
				return true;
			}

		} catch (org.jclouds.compute.RunNodesException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param context
	 *            - ComputeServiceContext Object
	 * @returns all volumes in the Openstack
	 */
	/*
	 * public static Map<Integer, Volume> getVolumes (ComputeServiceContext
	 * context) { Map<Integer, Volume> volumesMap = new HashMap<Integer,
	 * Volume>(); Optional<? extends VolumeApi> volumeTypeOption; NovaApi
	 * novaApi =
	 * NovaApi.class.cast(context.getProviderSpecificContext().getApi());
	 * VolumeApi volumesApi = novaApi.getVolumeExtensionForZone("siel").get();
	 * Set<? extends Volume> volumes = volumesApi.list().toImmutableSet(); int i
	 * = 1; for (Volume vol : volumes) { volumesMap.put(i++, vol); } return
	 * volumesMap; }
	 */

	/**
	 * @param compute
	 *            - ComputeService Object
	 * @param nodeId
	 *            - Id of the Node
	 */
	public static void resumeNode(ComputeService compute, String nodeId) {
		try {
			System.out
					.println("---------------------b4 returned---------------------");
			compute.resumeNode(nodeId);

		} catch (Exception e) {
			System.out.println("Exception Suppressed");
			System.out
					.println("---------------------returned---------------------");
			Map<Integer, Hardware> flvrsMap = Utils.getFlavors("openstack",
					compute);

			// System.out.println("size  "+instancesMap.size());
			boolean flag = true;
			while (flag) {
				Map<String, Instance> instancesMap = Utils.getInstances(
						"openstack", compute, flvrsMap);
				for (String id : instancesMap.keySet()) {
					Instance instance = instancesMap.get(id);
					// System.out.println("ud----- "+ id+" "+nodeId);
					id = "siel/" + id;
					if (id.equals(nodeId)) {
						// System.out.println(instance.getState().toString());
						if ((instance.getState().toString()).equals("RUNNING")) {
							flag = false;
						} else {
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}

				}
			}
		}
	}

	/**
	 * @param compute
	 *            - ComputeService Object
	 * @param nodeId
	 *            - Id of the node
	 */
	public static void suspendNode(ComputeService compute, String nodeId) {
		try {
			compute.suspendNode(nodeId);
			Map<Integer, Hardware> flvrsMap = Utils.getFlavors("openstack",
					compute);
			Map<String, Instance> instancesMap = Utils.getInstances(
					"openstack", compute, flvrsMap);
			boolean flag = true;
			while (flag) {
				for (String id : instancesMap.keySet()) {
					Instance instance = instancesMap.get(id);
					if (id.equals(nodeId)) {
						if ((instance.getState().toString()).equals("RUNNING")) {

							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							flag = false;
						}
					}

				}
			}
		} catch (Exception e) {
			System.out.println("Exception Suppressed");

			System.out
					.println("---------------------returned---------------------");
			Map<Integer, Hardware> flvrsMap = Utils.getFlavors("openstack",
					compute);

			// System.out.println("size  "+instancesMap.size());
			boolean flag = true;
			while (flag) {
				Map<String, Instance> instancesMap = Utils.getInstances(
						"openstack", compute, flvrsMap);
				for (String id : instancesMap.keySet()) {
					Instance instance = instancesMap.get(id);
					// System.out.println("ud----- "+ id+" "+nodeId);
					id = "siel/" + id;
					if (id.equals(nodeId)) {
						// System.out.println(instance.getState().toString());
						if (!(instance.getState().toString()).equals("RUNNING")) {
							flag = false;
						} else {
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}

				}
			}

		}
	}

	/**
	 * @param compute
	 *            - ComputeService Object
	 * @param nodeId
	 *            - Id of the node
	 */
	public static void destroyNode(ComputeService compute, String nodeId) {
		compute.destroyNode(nodeId);
	}

	/**
	 * @param context
	 *            - ComputeServiceContext Object
	 * @param volumeId
	 *            - Id of the volume
	 * @param nodeId
	 *            - Id of the node
	 */
	/*
	 * public static void attachVolume (ComputeServiceContext context, String
	 * volumeId, String nodeId) {
	 * 
	 * boolean flag=true; System.out.println("attaching  " + volumeId);
	 * Map<Integer, Volume> volumesMap = new HashMap<Integer, Volume>();
	 * Optional<? extends VolumeApi> volumeTypeOption; NovaApi novaApi =
	 * NovaApi.class.cast(context.getProviderSpecificContext().getApi());
	 * System.out.println("Check 1 "); VolumeApi volumesApi =
	 * novaApi.getVolumeExtensionForZone("siel").get();
	 * System.out.println("check 2"); VolumeAttachment result =
	 * volumesApi.attachVolumeToServerAsDevice(volumeId, nodeId, "/dev/vdc");
	 * System.out.println("check 3");
	 * 
	 * while(flag) { volumesApi=novaApi.getVolumeExtensionForZone("siel").get();
	 * Set<? extends Volume> volumes = volumesApi.list().toImmutableSet();
	 * 
	 * for(Volume vol : volumes) { if(vol.getId().equals(volumeId)) {
	 * if((vol.getStatus()).equals(Volume.Status.IN_USE)) flag=false; else try {
	 * Thread.sleep(5000); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * } } } }
	 */
	/**
	 * @param context
	 *            - ComputeServiceContext Object
	 * @param name
	 *            - name of the Object
	 * @param size
	 *            - size of the Object
	 * @returns true on successful creation of volume
	 */
	/*
	 * public static boolean createVolume (ComputeServiceContext context, String
	 * name, String size) { Optional<? extends VolumeApi> volumeTypeOption;
	 * System.out.println("name " + name + " size " + size);
	 * 
	 * NovaApi novaApi =
	 * NovaApi.class.cast(context.getProviderSpecificContext().getApi());
	 * 
	 * VolumeApi volumesApi = novaApi.getVolumeExtensionForZone("siel").get();
	 * System.out.println("check 1"); System.out.println("check 2"); Volume
	 * volume = volumesApi.create(Integer.parseInt(size),
	 * CreateVolumeOptions.Builder
	 * .name(name).description("description of test volume"));
	 * System.out.println("check 3"); if(volume!=null) return true; return
	 * false; }
	 */
	/**
	 * @param context
	 *            - ComputeServiceContext Object
	 * @param nodeId
	 *            - Id of the node
	 */
	/*
	 * public static void deleteVolume (ComputeServiceContext context,String
	 * nodeId) { NovaApi novaApi =
	 * NovaApi.class.cast(context.getProviderSpecificContext() .getApi());
	 * VolumeApi volumesApi= novaApi.getVolumeExtensionForZone("siel").get();
	 * volumesApi.delete(nodeId); boolean flag=true; int size=0; int i=0;
	 * while(flag) { volumesApi=novaApi.getVolumeExtensionForZone("siel").get();
	 * Set<? extends Volume> volumes = volumesApi.list().toImmutableSet(); i=0;
	 * for(Volume vol : volumes) { i++; if(vol.getId().equals(nodeId)) {
	 * flag=true; break; } else flag=false; } if(flag==false) break; else { try
	 * { Thread.sleep(5000); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } } } }
	 */

	/**
	 * @param context
	 *            - ComputeServiceContext Object
	 * @param volumeId
	 *            - Id of the volume
	 * @param nodeId
	 *            - Id of the node
	 */
	/*
	 * public static void detachVolume (ComputeServiceContext context, String
	 * volumeId, String nodeId) {
	 * 
	 * System.out.println("dettaching  " + volumeId); boolean flag=true;
	 * Map<Integer, Volume> volumesMap = new HashMap<Integer, Volume>();
	 * Optional<? extends VolumeApi> volumeTypeOption; NovaApi novaApi =
	 * NovaApi.class.cast(context.getProviderSpecificContext().getApi());
	 * VolumeApi volumesApi = novaApi.getVolumeExtensionForZone("siel").get();
	 * volumesApi.detachVolumeFromServer(volumeId, nodeId);
	 * 
	 * while(flag) { volumesApi=novaApi.getVolumeExtensionForZone("siel").get();
	 * Set<? extends Volume> volumes = volumesApi.list().toImmutableSet();
	 * 
	 * for(Volume vol : volumes) { if(vol.getId().equals(volumeId)) {
	 * if((vol.getStatus()).equals(Volume.Status.AVAILABLE)) flag=false; else
	 * try { Thread.sleep(5000); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * } } } }
	 */
}
