package com.dashboard.rest.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Past;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.compute.ComputeService;
import org.openstack4j.model.identity.User;

import com.dashboard.domain.objects.ServerList;
import com.dashboard.openstack.utils.Constants;
import com.dashboard.openstack.utils.OpenstackUtils;
import com.dashboard.rest.request.bean.CreateServerRequestList;
import com.dashboard.utils.Connection;
import com.dashboard.utils.Utils;
import com.google.gson.Gson;

@Path("/openstack")
public class OpenStackResource_Impl extends Application {

	private static final String OC_CLIENT_OBJ = "__OS_CLIENT_OBJ";
	private static final String OC_COMPUTE_OBJ = "__OS_COMPUTE_OBJ";
	private static final String USER_NAME = "__USER_NAME__";
	private static final String PASSWORD = "__PASS__";
	private static final String TENANT = "__TENANT__";

	private void log(String message) {
		System.out.println("APPLOG:" + message);
	}

	private Object _getObjectFromSession(String name) {
		HttpSession session = request.getSession();
		if (session != null && session.getAttribute(name) != null) {
			return session.getAttribute(name);
		}
		return null;
	}

	private OSClient _authFromSession() {
		String u = (String) _getObjectFromSession(USER_NAME);
		String p = (String) _getObjectFromSession(PASSWORD);
		String t = (String) _getObjectFromSession(TENANT);
		if (u != null && p != null && t != null) {
			return OpenstackUtils.getOSClient(u, p, t);
		}
		return null;
	}

	// Connection connection = null;
	@Context
	private HttpServletRequest request;
	Connection connection = null;

	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED,
			MediaType.MULTIPART_FORM_DATA })
	public Response getDahBOard(@FormParam("username") String username,
			@FormParam("password") String password) {
		try {
			HttpSession session = request.getSession();
			if (session != null) {
				session.invalidate();
			}
			OSClient osc = OpenstackUtils.getOSClient(username, password,
					"admin");
			ComputeService cs = osc.compute();

			session = request.getSession(true);
			session.setAttribute(OC_CLIENT_OBJ, osc);
			session.setAttribute(OC_COMPUTE_OBJ, osc.compute());
			session.setAttribute(USER_NAME, username);
			session.setAttribute(PASSWORD, password);
			session.setAttribute(TENANT, "admin");

			return Response
					.temporaryRedirect(
							new URI(
									"http://localhost:8080/openstackwebmod/dashboard.html"))
					.build();
		} catch (Exception e) {
			return Response.status(401).build();
		}
	}

	@GET
	@Path("/serverDetails")
	@Produces("application/json")
	public Response getServerDetails(@Context UriInfo uriInfo) {
		String provider = Constants.provider;
		Utils utils = new Utils();
		OSClient osc = _authFromSession();
		if (osc != null) {
			// Get the list of images
			ServerList serverlist = utils
					.getServerList(provider, osc.compute());
			String resonse = new Gson().toJson(serverlist);
			System.out.println(resonse);
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			return Response.created(builder.build()).status(200)
					.entity(resonse).build();
		} else {
			return Response.status(401).build();
		}
	}

	@GET
	@Path("/users")
	@Produces("application/json")
	public Response getUserDetails(@Context UriInfo uriInfo) {
		OSClient osclient = _authFromSession();
		// Find all Users
		List<? extends User> opnstackUsers = osclient.identity().users().list();
		ArrayList<com.dashboard.domain.objects.User> userList = new ArrayList<com.dashboard.domain.objects.User>();
		for (User user : opnstackUsers) {
			com.dashboard.domain.objects.User customUser = new com.dashboard.domain.objects.User(
					user.getName(), user.getId(), "", "");
			userList.add(customUser);
		}
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		return Response.created(builder.build()).status(200)
				.entity(new Gson().toJson(userList)).build();
	}

	@POST
	@Path("/createServers")
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public Response createServer(String x, @Context UriInfo uriInfo) {
		System.out.println(x);
		CreateServerRequestList requestlist = new Gson().fromJson(x,
				CreateServerRequestList.class);
		OSClient osc = _authFromSession();
		if (osc != null) {
			Utils utils = new Utils();
			String provider = Constants.provider;
			List<com.dashboard.domain.objects.Server> servers = utils
					.createServers(requestlist.getRequest(), provider, osc);
			String response = new Gson().toJson(servers);

			System.out.println(requestlist.getRequest().size());
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			return Response.created(builder.build()).status(201)
					.entity(new Gson().toJson(response)).build();
		} else {
			return Response.status(401).build();
		}
	}
	@GET
	@Path("/doaction")
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	public Response dosActionOnServer(@QueryParam("server_id")String serverId,@QueryParam("action")String action, @Context UriInfo uriInfo) {
		System.out.println(serverId);
		OSClient osc = _authFromSession();
		if (osc != null) {
			Utils utils = new Utils();
			String provider = Constants.provider;
             String result = utils.doneActionOnServer(serverId, osc, action, provider);
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			return Response.created(builder.build()).status(200)
					.build();
		} else {
			return Response.status(500).build();
		}
	}

}
