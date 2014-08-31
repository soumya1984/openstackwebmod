package com.dashboard.rest.api;

import org.apache.http.HttpResponse;

import com.dashboard.domain.objects.AuthResponse;
import com.dashboard.utils.HttpUtill;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//######################################################
//# 
//# Sample code to illustrate the use of the OpenStack
//# REST API
//# 
//# TKH 8/23/2014
//#
//######################################################

public class RestClientUtil {

	// ############### Configuration Section ################
	// ############### Enter your own values ################
	// # Obtain your OpenStack password from file
	// # /root/keystonerc_admin
	String OSpassword = "01ff16bf4cbb4020";

	// # Obtain your own tenant id for tenant "admin" by running command
	// # keystone tenant-list
	// # on the OpenStack server
	String tenantid = "034370b2e765495a8d779ad76ac919b3";

	// # OpenStack server address
	// # Can be IP address or DNS name
	final static String hostIP = "192.168.56.1";

	// # We are user admin and tenant admin.
	String tenantname = "admin";
	String username = "admin";

	// ############### OpenStack API ports ########
	// # Make sure that these ports are open in the OpenStack server
	// # and that VirtualBox Port Forwarding (if used) is properly set
	final static String KEYSTONEPORT = "5000";
	final static String NOVAPORT = "8774";
	final static String NOVA_EC2PORT = "8773";
	final static String CINDERPORT = "8776";
	final static String CEILOMETERPORT = "8777";
	final static String GLANCEPORT = "9292";
	final static String NEUTRONPORT = "9696";
	final static String KEYSTONE_ADMport = "35357";
	final static String CMDpath = "/v2.0/tokens";
	final static String NOVA_VER = "/v2/";
	final static String NOVA_CMDPATH = "/servers/detail";
	String token;
	String tenentId;

	private HttpResponse httpPostClientResponse;

	public AuthResponse getToken(String UserName, String Passwors) {
		String url = "http://" + RestClientUtil.hostIP + ":"
				+ RestClientUtil.KEYSTONEPORT + RestClientUtil.CMDpath;
		String body = "{" + "\"auth\":" + " { " + "\"tenantName\": "
				+ "\"admin\"," + "\"passwordCredentials\":" + "{"
				+ "\"username\": " + "\"admin\"," + "\"password\": \""
				+ "01ff16bf4cbb4020" + "\"}" + "} " + "} ";
		HttpUtill util = new HttpUtill();
		httpPostClientResponse = util.getHttpPostClientResponse(url,
				"application/json", body);
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(
				util.extractResponse(httpPostClientResponse)).getAsJsonObject();
		JsonElement element = obj.get("access");
		JsonElement tokenDetails = element.getAsJsonObject().get("token");
		JsonElement tenantDetails = tokenDetails.getAsJsonObject()
				.get("tenant");
		tenentId = tenantDetails.getAsJsonObject().get("id").getAsString();
		token = tokenDetails.getAsJsonObject().get("id").getAsString();
		AuthResponse authRes = new AuthResponse("admin", "admin", token,
				tenentId);
		getServerDetails(authRes);
		return authRes;

	}

	public void getServerDetails(AuthResponse authRes) {
		String url = "http://" + RestClientUtil.hostIP + ":"
				+ RestClientUtil.NOVAPORT + RestClientUtil.NOVA_VER
				+ authRes.getTenentId() + NOVA_CMDPATH;
		HttpUtill util = new HttpUtill();
		util.getHttpGetClientResponse(url, authRes);
	}
	public static void main(String args[]) {
		RestClientUtil util = new RestClientUtil();
		util.getToken("", "");
	}
}
