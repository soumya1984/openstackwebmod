package com.dashboard.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.dashboard.domain.objects.AuthResponse;
import com.dashboard.openstack.utils.Constants;
import com.dashboard.utils.Connection;
import com.google.gson.Gson;

@Path("/openstack")
public class OpenStackResource_Impl {
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
		RestClientUtil util = new RestClientUtil();
		AuthResponse authRes = util.getToken(username, password);
		String jsonString = new Gson().toJson(authRes);
		System.out.println(jsonString);
		// connection = Utils.authenticate(username, password, provider,
		// tenantName);
		// Get the list of images
		// Map<Integer, Image> imagesMap = Utils.getImages( provider,
		// connection.compute);
		// session.setAttribute(Constants.imagesMapAttrName, imagesMap);

		// Get the list of flavors
		// Map<Integer, Hardware> flavorsMap = Utils.getFlavors( provider,
		// connection.compute);
		// session.setAttribute(Constants.flavorsMapAttrName, flavorsMap);

		// Get the list of instances
		// Map<Integer, Instance> instancesMap = Utils.getInstances(provider,
		// connection.compute, flavorsMap);
		// req.setAttribute(Constants.instancesMapAttrName, instancesMap);

		// System.out.println("Number of Instances : " + instancesMap.size());
		return null;
	}

	@GET
	@Path("/serverdetails")
	@Produces("application/json")
	public Response getServerDetails(@QueryParam("userid") String userId,
			@QueryParam("password") String pass,
			@QueryParam("tenetId") String tenentId) {
		// check authentication...
		String username = userId;
		String password = pass;
		String provider = Constants.provider;
		String tenantName = tenentId;
		// Authenticate user
		// call rest API ....
		RestClientUtil util = new RestClientUtil();
		AuthResponse authRes = util.getToken(username, password);
		String jsonString = new Gson().toJson(authRes);
		System.out.println(jsonString);
		return null;
	}
}
