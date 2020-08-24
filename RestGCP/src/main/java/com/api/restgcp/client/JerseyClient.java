package com.api.restgcp.client;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JerseyClient {

	Client client=Client.create();
	String url="http://localhost:8080/Rest/api/request/employee";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JerseyClient jerseyClient =new JerseyClient();
		jerseyClient.postRequest(); 
	}
	
	public void postRequest() { //for POST type request
		WebResource webResource=client.resource(url);
		JSONObject employee=new JSONObject();
		try {
		employee.accumulate("employeeID",1994353);
		employee.accumulate("employeeName","Akash Anaghan");
		employee.accumulate("designation","Manager");
		employee.accumulate("salary",148000);
		employee.accumulate("city","Montreal");
		}catch(JSONException exception) {
			System.out.println("JSON Error:"+exception.getMessage());
		}
		ClientResponse response=webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,employee.toString());
		if(response.getStatus()!=201) {
			System.out.println("Http Error:"+response.getStatus());
		}
		String result=response.getEntity(String.class);
		System.out.println("Response from the Server:\n"+result);
	}
	
}
