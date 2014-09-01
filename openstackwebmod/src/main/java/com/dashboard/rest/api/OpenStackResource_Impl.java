package com.dashboard.rest.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.compute.ComputeService;
import org.openstack4j.model.identity.User;

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
			@QueryParam("tenetId") String tenentId,@Context UriInfo uriInfo) {
		// check authentication...
		String username = userId;
		String password = pass;
		String provider = Constants.provider;
		String tenantName = tenentId;
		// Authenticate user =========================================================
		// call rest API ....
		// RestClientUtil util = new RestClientUtil();
		// AuthResponse authRes = util.getToken(username, password);
		// String jsonString = new Gson().toJson(authRes);
		// System.out.println(jsonString);
		//==============================================================================
		ComputeService computeService = Utils.authenticate(username, password, provider,
		 tenantName);
		
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		return Response.created(builder.build()).status(200)
				.build();
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
	@GET
	@Path("/users")
	@Produces("application/json")
	public Response getUserDetails(@QueryParam("userid") String userId,
			@QueryParam("password") String pass,
			@QueryParam("tenetId") String tenentId,@Context UriInfo uriInfo) {
		// check authentication...
		String username = userId;
		String password = pass;
		String provider = Constants.provider;
		String tenantName = tenentId;
		OSClient osclient = Utils.getOSClient(username, password, provider,
		 tenantName);
		// Find all Users
		List<? extends User> opnstackUsers = osclient.identity().users().list();
		ArrayList<com.dashboard.domain.objects.User> userList = new ArrayList<com.dashboard.domain.objects.User>();
		for(User user:opnstackUsers){
			com.dashboard.domain.objects.User customUser = new com.dashboard.domain.objects.User(user.getName(), user.getId(),"", "");
			userList.add(customUser);
		}
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		return Response.created(builder.build()).status(200)
				.entity(new Gson().toJson(userList)).build();
	}
}
