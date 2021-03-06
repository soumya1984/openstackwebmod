package com.dashboard.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.dashboard.domain.objects.AuthResponse;
import com.dashboard.domain.objects.Server;
import com.dashboard.rest.request.bean.CreateServerRequest;
import com.dashboard.rest.request.bean.CreateServerRequestList;
import com.google.gson.Gson;

public class HttpUtill {

	private HttpPost postRequest = null;
	private HttpResponse response = null;

	public HttpResponse getHttpPostClientResponse(String url,
			String contentType, String requestBody) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			postRequest = new HttpPost(url);
			StringEntity input = new StringEntity(requestBody);
			input.setContentType(contentType);
			postRequest.setEntity(input);
			response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	public String getHttpGetClientResponse(String url, AuthResponse auth) {
		String inputLine;
		StringBuffer response = new StringBuffer();
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			// optional default is GET
			con.setRequestMethod("GET");
			// add request header
			con.setRequestProperty("X-Auth-Token", auth.getAuthToken());
			con.setRequestProperty("content-Type", "application/json");
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response.toString();

	}

	public String extractResponse(HttpResponse httpResonse) {
		BufferedReader br;
		StringBuilder sb = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					(httpResonse.getEntity().getContent())));
			sb = new StringBuilder();

			String output = "";
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			System.out.println(sb.toString());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
public static void main(String[] args){
	HttpUtill httpUtil = new HttpUtill();
	CreateServerRequestList serverList = new CreateServerRequestList();
	CreateServerRequest server1 = new CreateServerRequest("myserver1","CMPE-MINI","m1.tiny");
	CreateServerRequest server2 = new CreateServerRequest("myserver2","CMPE-MINI","m1.tiny");
	//server1.setFlavour();
	List<CreateServerRequest> list = new ArrayList<CreateServerRequest>();
	list.add(server1);
	list.add(server2);
	serverList.setRequest(list);
	String request =new Gson().toJson(serverList);
	System.out.println(request);
	httpUtil.getHttpPostClientResponse("http://localhost:8080//openstackwebmod/rest/openstack/createServers", "application/json", request);
}	
	
	
	
}