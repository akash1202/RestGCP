package com.api.restgcp.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.api.restgcp.client.DatabaseConnection;
import com.google.api.client.util.StringUtils;

//localhost:8080/Rest/api/get/
@Path("/get")
public class GetMethods {
	
	private Connection connection=null;
	private Statement stmt=null;
	private ResultSet rs=null;
	private PreparedStatement pStatement=null;
	private JSONObject obj=new JSONObject();
	private JSONArray objArray=new JSONArray();
	
	
	
	// here orderDate needs to be in a format yyyy-mm-dd
	// localhost:8080/Rest/api/get/q1/2003-01-06
	@GET
	@Path("/q1/{orderDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProducts(@PathParam("orderDate") String date) {
		System.out.println("request1 found!!");
		DatabaseConnection dbConnection = new DatabaseConnection();
		try {
			connection = dbConnection.getConnection();
			String query = "select products.productName,customers.customerName,orderdetails.priceEach,"
					+ "orderdetails.quantityOrdered,orders.status,orders.shippedDate from orderdetails "
					+ "join orders on orderdetails.orderNumber=orders.orderNumber "
					+ "join customers on customers.customerNumber=orders.customerNumber "
					+ "join products on orderdetails.productCode=products.productCode where orders.orderDate = '"
					+ date + "'";
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				obj = new JSONObject();
				obj.accumulate("product Name", rs.getString("productName"));
				obj.accumulate("customer Name", rs.getString("customerName"));
				obj.accumulate("price", rs.getString("priceEach"));
				obj.accumulate("quantity", rs.getString("quantityOrdered"));
				obj.accumulate("status", rs.getString("status"));
				obj.accumulate("shipping Date", rs.getDate("shippedDate"));
				objArray.put(obj);
			}

			if (objArray.isEmpty()) {
				obj.accumulate("Error", "No value found!");
			}

		} catch (SQLException | JSONException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				connection.close();
				pStatement.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("finally Block Exception :" + e.getMessage());
			}
		}
		return Response.status(201).entity(objArray.toString()).build();
	}

	//this method is to retrieve all employee details by statewise
	//localhost:8080/Rest/api/get/q2/NY
	@GET
	@Path("/q2/{state}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeByState(@PathParam("state")String state){
		System.out.println("request2 found!!");
		DatabaseConnection dbConnection=new DatabaseConnection();
		try{
		connection=dbConnection.getConnection();
		String query="select employees.* from employees join offices on employees.officecode=offices.officecode and state='"+state+"'";
		stmt=connection.createStatement();
		rs=stmt.executeQuery(query);
		while(rs.next()) {
			obj=new JSONObject();
			obj.accumulate("employeeNumber", rs.getInt("employeeNumber"));
			obj.accumulate("lastName", rs.getString("lastName"));
			obj.accumulate("firstName", rs.getString("firstName"));
			obj.accumulate("extension", rs.getString("extension"));
			obj.accumulate("email", rs.getString("email"));
			obj.accumulate("officeCode", rs.getString("officeCode"));
			obj.accumulate("reportsTo", rs.getString("reportsTo"));
			obj.accumulate("jobTitle", rs.getString("jobTitle"));
			objArray.put(obj);
		}		
		if(objArray.isEmpty()){
			obj.accumulate("Error", "No value found!");
		}
		
		}catch(SQLException | JSONException e) {
			System.out.println(e.getMessage());
		}finally{
			try {
				connection.close();
				pStatement.close();
				rs.close();
			}catch(Exception e) {
				System.out.println("finally Block Exception :"+e.getMessage());
			}
		}
		return Response.status(201).entity(objArray.toString()).build();
	}
	//this method is to retrieve all employee details by statewise
	//localhost:8080/Rest/api/get/q3/363
	//localhost:8080/Rest/api/get/q3/Mini Classics    here it's okay to pass parameter with space
	@GET
	@Path("/q3/{customerData}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOrderedProducts(@PathParam("customerData")String customerData){
		System.out.println("request3 found!!");
		String query="";
		DatabaseConnection dbConnection=new DatabaseConnection();
		try{
		connection=dbConnection.getConnection();
		if(NumberUtils.isNumber(customerData)) {
		query="select products.productName,orderdetails.priceEach," + 
				"orderdetails.quantityOrdered,orders.orderDate,orders.shippedDate,orders.status from orderdetails " + 
				"join orders on orderdetails.orderNumber=orders.orderNumber " + 
				"join customers on customers.customerNumber=orders.customerNumber " + 
				"join products on orderdetails.productCode=products.productCode " + 
				"where customers.customerNumber="+customerData;
		}else {
		query="select products.productName,orderdetails.priceEach," + 
				"orderdetails.quantityOrdered,orders.orderDate,orders.shippedDate,orders.status from orderdetails " + 
				"join orders on orderdetails.orderNumber=orders.orderNumber " + 
				"join customers on customers.customerNumber=orders.customerNumber " + 
				"join products on orderdetails.productCode=products.productCode " + 
				"where customers.customerName='"+customerData+"'";
		}
		stmt=connection.createStatement();
		rs=stmt.executeQuery(query);
		while(rs.next()) {
			obj=new JSONObject();
			obj.accumulate("productName", rs.getString("productName"));
			obj.accumulate("price", rs.getString("priceEach"));
			obj.accumulate("quantity", rs.getString("quantityOrdered"));
			obj.accumulate("order Date", rs.getString("orderDate"));
			obj.accumulate("shipping Date", rs.getDate("shippedDate"));
			obj.accumulate("status", rs.getString("status"));
			objArray.put(obj);
		}
			
		if(objArray.isEmpty()){
			obj.accumulate("Error", "No value found!");
		}
		
		}catch(SQLException | JSONException e) {
			System.out.println(e.getMessage());
		}finally{
			try {
				connection.close();
				pStatement.close();
				rs.close();
			}catch(Exception e) {
				System.out.println("finally Block Exception :"+e.getMessage());
			}
		}
		return Response.status(201).entity(objArray.toString()).build();
	}
	
	
	
	
	
	// for testing connection purpose
	//localhost:8080/Rest/api/get/aws
	@GET
	@Path("/aws")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDataFromDB() {
		DatabaseConnection dbcon=new DatabaseConnection();
		JSONObject json=new JSONObject();
		try {
			connection=dbcon.getConnection();
			stmt=connection.createStatement();
			String query="select * from customers";
			rs=stmt.executeQuery(query);
			while(rs.next()) {
				System.out.println(rs.getString("customerName"));
			}
			json.accumulate("test", "Testing with aws");
		}catch(Exception e) {
			System.out.println("SQL Exception :"+e.getMessage());
		}

		return Response.status(200).entity(json.toString()).build();
	}
}
