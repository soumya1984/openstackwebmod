/**
 * Purpose : Contains all the methods related to Openstack access.
 */
package com.dashboard.openstack.utils;

import java.util.ArrayList;
import java.util.List;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.compute.ComputeService;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.RebootType;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.model.network.Network;
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
				//customServ.setAccessIPv4(nova.getAccessIPv4());
				//customServ.setAccessIPv6(nova.getAccessIPv6());
				//customServ.setCreated(nova.getCreated());
				//customServ.setHostId(nova.getHostId());
				customServ.setId(nova.getId());
				//customServ.setKeyName(nova.getKeyName());
				customServ.setName(nova.getName());
				customServ.setProgress(nova.getProgress());
				customServ.setStatus(nova.getStatus().name());
				//customServ.setTenantId(nova.getTenantId());
				//customServ.setUpdated(nova.getUpdated());
				//customServ.setUserId(nova.getUserId());
				// custom
				// Flavor..===================================================
				com.dashboard.domain.objects.Flavor flavor = new com.dashboard.domain.objects.Flavor(
						nova.getFlavor().getId(), nova.getFlavor().getName());
				customServ.setFlavor(flavor);
				// /=================================================================
				//customServ.setAccessIPv4(nova.getAccessIPv6());
				//customServ.setAccessIPv4(nova.getAccessIPv6());
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
			List<CreateServerRequest> list, OSClient osc) {
		//OSClient os = getOSClient(username, password, tenantName);
		//get the list of the N/W......and use it in Round Robin Fashion 
		List<? extends Network> networks = osc.networking().network().list();
		List<String> networkIdList = getNetworkIdList(networks);
		//int count=0;
		
		
		List<com.dashboard.domain.objects.Server> servers = new ArrayList<com.dashboard.domain.objects.Server>();
		for (CreateServerRequest request : list) {
			// Create a Server Model Object
			ServerCreate sc = Builders
					.server()
					.name(request.getServername().toString())
					.flavor(request.getFlavour().toString())
					.image(request.getImageName().toString())
					.networks(networkIdList)
					.build();
			// Boot the Server
			Server server = osc.compute().servers().boot(sc);
			// mapping between customresponse & server response
			com.dashboard.domain.objects.Server customServerRes = getCustomServer(server);
			servers.add(customServerRes);
		}
		return servers;

	}
	
	public String doneActionOnServer(String serverId,OSClient os,String action){
		if(action.equalsIgnoreCase("start")){
			os.compute().servers().action(serverId, Action.START);
			return "success";
		}
		if(action.equalsIgnoreCase("stop")){
		os.compute().servers().action(serverId, Action.STOP);
		return "success";
		}
		return "failure";
	}

	private List<String> getNetworkIdList(List<? extends Network> networks) {
		List<String> networkList = new ArrayList<String>();
		for (Network nwtwrk : networks) {
			networkList.add(nwtwrk.getId());
		}
		return networkList;
	}

	public com.dashboard.domain.objects.Server getCustomServer(Server server) {
		com.dashboard.domain.objects.Server customServ = new com.dashboard.domain.objects.Server();
		customServ.setId(server.getId());
		customServ.setName(server.getName());
		customServ.setProgress(server.getProgress());
		return customServ;
	}
}
