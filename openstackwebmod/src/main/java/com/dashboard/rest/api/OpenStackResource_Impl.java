package com.dashboard.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.openstack4j.api.compute.ComputeService;
import com.dashboard.domain.objects.ServerList;
import com.dashboard.openstack.utils.Constants;
import com.dashboard.utils.Connection;
import com.dashboard.utils.Utils;
import com.google.gson.Gson;


@Path("/openstack")
public class OpenStackResource_Impl extends Application{
	Connection connection = null;

	@GET
	@Path("/dashboard")
	@Produces("application/json")
	public Response getDahBOard(@QueryParam("userid") String userId,
			@QueryParam("password") String pass,
			@QueryParam("tenetId") String tenentId) {
		// check authentication...
		String username = userId;
		String password = pass;
		String provider = Constants.provider;
		String tenantName = tenentId;
		// Authenticate user
		// call rest API ....
		// RestClientUtil util = new RestClientUtil();
		// AuthResponse authRes = util.getToken(username, password);
		// String jsonString = new Gson().toJson(authRes);
		// System.out.println(jsonString);
		ComputeService computeService = Utils.authenticate(username, password, provider,
		 tenantName);
		// Get the list of images
		//List<Image> image = Utils.getImages( provider,
		//		computeService);
		// session.setAttribute(Constants.imagesMapAttrName, imagesMap);

		// Get the list of flavors
		// Map<Integer, Hardware> flavorsMap = Utils.getFlavors( provider,
		// connection.compute);
		// session.setAttribute(Constants.flavorsMapAttrName, flavorsMap);

		// Get the list of instances
		//   Map<String, com.dashboard.resources.Instance> instancesMap = Utils.getInstances(provider,
		//    connection.compute, flavorsMap);
		// req.setAttribute(Constants.instancesMapAttrName, instancesMap);

		 //System.out.println("Number of Instances : " + instancesMap.size());
		return null;
	}
	@GET
	@Path("/serverDetails")
	@Produces("application/json")
	public Response getServerDetails(@QueryParam("userid") String userId,
			@QueryParam("password") String pass,
			@QueryParam("tenetId") String tenentId, @Context UriInfo uriInfo) {
		// check authentication...
		String username = userId;
		String password = pass;
		String provider = Constants.provider;
		String tenantName = tenentId;
		ComputeService computeService = Utils.authenticate(username, password, provider,
		 tenantName);
		// Get the list of images
		ServerList serverlist = Utils.getServerList( provider,
				computeService);		
		String resonse=new Gson().toJson(serverlist);
		System.out.println(resonse);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		return Response.created(builder.build()).status(200)
				.entity(resonse).build();
	}
}
