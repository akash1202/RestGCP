package com.api.restgcp.server;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/request")
public class GetPostApi {

	//url= http://localhost:8080/Rest/api/request/student
	@GET
	@Path("/student")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudent() { //first GET method which returns 1 student information
	JSONObject student=new JSONObject();
	try {
	student.accumulate("first name", "Akash");
	student.accumulate("last name", "Anaghan");
	student.accumulate("student id",1994353);  //here added key-value pairs in JSONObject as sequence is given in question
	student.accumulate("city", "Montreal");
	}catch(JSONException exception) {
		System.out.println("JSON Exception"+exception.getMessage());
	}
	return Response.status(201).entity(student.toString()).build();
  }
	//url= http://localhost:8080/Rest/api/request/students
	@GET
	@Path("/students")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudents() { //second GET method which returns 2 students' information
	JSONArray students=new JSONArray();
	JSONObject student=new JSONObject();
	try {
	student.accumulate("id", 1994353);
	student.accumulate("firstname", "Akash");
	student.accumulate("lastname","Anaghan");
	student.accumulate("city", "Montreal");
	students.put(student);
	student=new JSONObject();
	student.accumulate("id", 1995849);
	student.accumulate("firstname", "Rajeshwari");
	student.accumulate("lastname","brahmbhatt");
	student.accumulate("city", "Montreal");
	students.put(student);
	}catch(JSONException exception) { //in case of JSON Parsing exception 
		System.out.println("JSON Exception"+exception.getMessage());
	}
	return Response.status(201).entity(students.toString()).build();
  }
	//url= http://localhost:8080/Rest/api/request/employee with POST method
	@POST
	@Path("/employee")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployee(Employee employee) { //POST method which returns 1 student information
	String result="Response : "+employee;
	return Response.status(201).entity(result).build();
  }
	
	
}
