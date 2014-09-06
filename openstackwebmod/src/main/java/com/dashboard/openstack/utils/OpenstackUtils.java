/**
 * Purpose : Contains all the methods related to Openstack access.
 */
package com.dashboard.openstack.utils;

import java.util.ArrayList;
import java.util.List;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.compute.ComputeService;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.openstack.OSFactory;

import com.dashboard.domain.objects.ServerList;
import com.dashboard.rest.request.bean.CreateServerRequest;

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
	public static ComputeService authenticate(String username, String password,
			String tenantName) {
		OSClient os = OSFactory.builder()
				.endpoint("http://localhost:5000/v2.0")
				.credentials("admin", password).tenantName("admin")
				.authenticate();
		// Find all running Servers
		ComputeService compute = os.compute();

		return compute;
	}

	/**
	 * @param username
	 *            - username for Openstack account
	 * @param password
	 *            - password for openstack account
	 * @param tenantName
	 *            - tenantName for openstack account
	 * @returns the OSClient object on successful authentication
	 */
	public static OSClient getOSClient(String username, String password,
			String tenantName) {
		OSClient os = OSFactory.builder()
				.endpoint("http://localhost:5000/v2.0")
				.credentials(username, password).tenantName(tenantName)
				.authenticate();
		return os;
	}

	public static List<Image> listImages(ComputeService compute) {
		List<? extends Image> images = compute.images().list();
		if (images != null) {
			return new ArrayList<Image>(images);
		}
		return new ArrayList<Image>();
	}

	/**
	 * map novaserver object to customserver object...
	 * 
	 * @param compute
	 * @return ServerList
	 */
	public static ServerList listServerDetails(ComputeService compute) {
		// open Stack Server

		List<? extends Server> novaServers = compute.servers().list();
		List<com.dashboard.domain.objects.Server> servers = new ArrayList<com.dashboard.domain.objects.Server>();
		ServerList svrList = new ServerList();
		if (servers != null) {
			// return new ArrayList<Image>(images);
			for (Server nova : novaServers) {
				com.dashboard.domain.objects.Server customServ = new com.dashboard.domain.objects.Server();
				customServ.setAccessIPv4(nova.getAccessIPv4());
				customServ.setAccessIPv6(nova.getAccessIPv6());
				customServ.setCreated(nova.getCreated());
				customServ.setHostId(nova.getHostId());
				customServ.setId(nova.getId());
				customServ.setKeyName(nova.getKeyName());
				customServ.setName(nova.getName());
				customServ.setProgress(nova.getProgress());
				customServ.setStatus(nova.getStatus().name());
				customServ.setTenantId(nova.getTenantId());
				customServ.setUpdated(nova.getUpdated());
				customServ.setUserId(nova.getUserId());
				// custom
				// Flavor..===================================================
				com.dashboard.domain.objects.Flavor flavor = new com.dashboard.domain.objects.Flavor(
						nova.getFlavor().getId(), nova.getFlavor().getName());
				customServ.setFlavor(flavor);
				// /=================================================================
				customServ.setAccessIPv4(nova.getAccessIPv6());
				customServ.setAccessIPv4(nova.getAccessIPv6());
				// Image
				// .........====================================================
				Image novaImage = nova.getImage();
				com.dashboard.domain.objects.Image image = new com.dashboard.domain.objects.Image(
						novaImage.getName(), novaImage.getId());
				customServ.setImage(image);
				servers.add(customServ);
			}
		}
		if (servers.size() > 0) {
			svrList.setServerList(servers);
		}
		return svrList;

	}

	public  List<com.dashboard.domain.objects.Server> buildServers(
			List<CreateServerRequest> list, String username, String password,
			String tenantName) {
		List<com.dashboard.domain.objects.Server> servers = new ArrayList<com.dashboard.domain.objects.Server>();
		for (CreateServerRequest request : list) {
			// Create a Server Model Object
			List<String> network = new ArrayList<String>();
			network.add("3d6eaa35-56d1-46d7-9060-1f2736d0b670");
			OSClient os = getOSClient(username, password, tenantName);
			ServerCreate sc = Builders
					.server()
					.name(request.getServername())
					.flavor(request.getFlavour())
					.image(request.getImageName())
					.networks(network)
					.build();
			// Boot the Server
			Server server = os.compute().servers().boot(sc);
			// mapping between customresponse & server response
			com.dashboard.domain.objects.Server customServerRes = getCustomServer(server);
			servers.add(customServerRes);
		}
		return servers;

	}

	public com.dashboard.domain.objects.Server getCustomServer(Server server) {
		com.dashboard.domain.objects.Server customServ = new com.dashboard.domain.objects.Server();
		customServ.setAccessIPv4(server.getAccessIPv4());
		customServ.setAccessIPv6(server.getAccessIPv6());
		customServ.setCreated(server.getCreated());
		customServ.setHostId(server.getHostId());
		customServ.setId(server.getId());
		customServ.setKeyName(server.getKeyName());
		customServ.setName(server.getName());
		customServ.setProgress(server.getProgress());
		//customServ.setStatus(server.getStatus().name());
		customServ.setTenantId(server.getTenantId());
		customServ.setUpdated(server.getUpdated());
		customServ.setUserId(server.getUserId());
		// custom
		// Flavor..===================================================
		com.dashboard.domain.objects.Flavor flavor = new com.dashboard.domain.objects.Flavor(
				server.getFlavor().getId(), server.getFlavor().getName());
		customServ.setFlavor(flavor);
		// /=================================================================
		customServ.setAccessIPv4(server.getAccessIPv6());
		customServ.setAccessIPv4(server.getAccessIPv6());
		// Image
		// .........====================================================
		Image novaImage = server.getImage();
		com.dashboard.domain.objects.Image image = new com.dashboard.domain.objects.Image(
				novaImage.getName(), novaImage.getId());
		customServ.setImage(image);
		return customServ;
	}
}
