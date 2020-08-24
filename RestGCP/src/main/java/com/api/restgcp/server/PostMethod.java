package com.api.restgcp.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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


//localhost:8080/Rest/api/post/
@Path("/post")
public class PostMethod {
	
	private Connection connection=null;
	private Statement stmt=null;
	private ResultSet rs=null;
	private PreparedStatement pStatement=null;
	private JSONObject obj=new JSONObject();
	private JSONArray objArray=new JSONArray();
	
	@POST
	@Path("/createStudent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response studentRecord(Student student){
		String result="Result: "+student;
		return Response.status(201).entity(result).build();
	}
	
	
	@GET()
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomers() {
		DatabaseConnection dbcon=new DatabaseConnection();
		try {
			connection=dbcon.getConnection();
			stmt=connection.createStatement();
			String query="select * from customers";
			rs=stmt.executeQuery(query);
			while(rs.next()) {
				JSONObject child=new JSONObject();
				child.accumulate("customerNumber", rs.getInt("CustomerNumber"));
				child.accumulate("customerName", rs.getString("CustomerName"));
				child.accumulate("contactLastName", rs.getString("contactLastName"));
				child.accumulate("contactrFirstName", rs.getString("contactFirstName"));
				child.accumulate("phone", rs.getString("phone"));
				child.accumulate("addressLine1", rs.getString("addressLine1"));
				child.accumulate("addressLine2", rs.getString("addressLine2"));
				child.accumulate("city", rs.getString("city"));
				child.accumulate("state", rs.getString("state"));
				child.accumulate("postalCode", rs.getString("postalCode"));
				child.accumulate("country", rs.getString("country"));
				child.accumulate("salesRepEmployeeNumber", rs.getString("salesRepEmployeeNumber"));
				child.accumulate("creditLimit", rs.getString("creditLimit"));
				objArray.put(child);
			}
			obj.put("Customers", objArray);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(200).entity(obj.toString()).build();
	}
	
	@POST
	@Path("/createCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCustomer(Customer customer){
		String result="Result :"+customer;
		DatabaseConnection dbConnection=new DatabaseConnection();
		try{connection=dbConnection.getConnection();
		String query="INSERT INTO `classicmodels`.`customers`" + 
				"(`customerNumber`,`customerName`,`contactLastName`,`contactFirstName`,`phone`,`addressLine1`," + 
				"`addressLine2`,`city`,`state`,`postalCode',`country`,`salesRepEmployeeNumber`,`creditLimit`) "+
				"VALUES "+"(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		pStatement=connection.prepareStatement(query);
		pStatement.setInt(1,customer.getCustomerNumber());
		pStatement.setString(2,customer.getCustomerName());
		pStatement.setString(3,customer.getContactLastName());
		pStatement.setString(4,customer.getContactFirstName());
		pStatement.setString(5,customer.getPhone());
		pStatement.setString(6,customer.getAddressLine1());
		pStatement.setString(7,customer.getAddressLine2());
		pStatement.setString(8,customer.getCity());
		pStatement.setString(9,customer.getState());
		pStatement.setString(10,customer.getPostalCode());
		pStatement.setString(11,customer.getCountry());
		pStatement.setString(12,customer.getSalesRepEmployeeNumber());
		pStatement.setString(13,customer.getCreditLimit());
		pStatement.execute();
		}catch(SQLException e) {
			
		}finally{
			try {
				connection.close();
				pStatement.close();
			}catch(Exception e) {
				System.out.println("finally Block Exception :"+e.getMessage());
			}
		}
		return Response.status(201).entity(result).build();
	}
	
	@GET
	@Path("/q0/{cID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeesByID(@PathParam("cID") Integer id){
		System.out.println("request1 found!!");
		DatabaseConnection dbConnection=new DatabaseConnection();
		try{
		connection=dbConnection.getConnection();
		String query="select * from customers where customerNumber="+id;
		stmt=connection.createStatement();
		rs=stmt.executeQuery(query);
		while(rs.next()) {
			obj=new JSONObject();
			obj.accumulate("customerNumber", rs.getInt("CustomerNumber"));
			obj.accumulate("customerName", rs.getString("CustomerName"));
			obj.accumulate("contactLastName", rs.getString("contactLastName"));
			obj.accumulate("contactrFirstName", rs.getString("contactFirstName"));
			obj.accumulate("phone", rs.getString("phone"));
			obj.accumulate("addressLine1", rs.getString("addressLine1"));
			obj.accumulate("addressLine2", rs.getString("addressLine2"));
			obj.accumulate("city", rs.getString("city"));
			obj.accumulate("state", rs.getString("state"));
			obj.accumulate("postalCode", rs.getString("postalCode"));
			obj.accumulate("country", rs.getString("country"));
			obj.accumulate("salesRepEmployeeNumber", rs.getInt("salesRepEmployeeNumber"));
			obj.accumulate("creditLimit", rs.getFloat("creditLimit"));
			objArray.put(obj);
		}
		if(objArray.length()>0) {
			obj=new JSONObject();
			obj.accumulate("Employees",objArray);
		}
		
		if(obj.isEmpty()){
			obj.accumulate("Error", "No value found!");
		}
		
		}catch(SQLException | JSONException e) {
			System.out.println(e.getMessage());
		}finally{
			try {
				connection.close();
				pStatement.close();
			}catch(Exception e) {
				System.out.println("finally Block Exception :"+e.getMessage());
			}
		}
		return Response.status(201).entity(obj.toString()).build();
	}
	
		//here orderDate needs to be in a format yyyy-mm-dd
		//localhost:8080/Rest/api/post/q1/103
		@GET
		@Path("/q1/{orderDate}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getProducts(@PathParam("orderDate")String date){
			System.out.println("request1 found!!");
			DatabaseConnection dbConnection=new DatabaseConnection();
			try{
			connection=dbConnection.getConnection();
			String query="select products.productName,customers.customerName,orderdetails.priceEach," + 
					"orderdetails.quantityOrdered,orders.status,orders.shippedDate from orderdetails " + 
					"join orders on orderdetails.orderNumber=orders.orderNumber " + 
					"join customers on customers.customerNumber=orders.customerNumber " + 
					"join products on orderdetails.productCode=products.productCode" + 
					"where orders.orderDate = '"+date+"'";
			stmt=connection.createStatement();
			rs=stmt.executeQuery(query);
			while(rs.next()) {
				obj=new JSONObject();
				obj.accumulate("productName", rs.getString("productName"));
				obj.accumulate("customerName", rs.getString("customerName"));
				obj.accumulate("priceEach", rs.getString("priceEach"));
				obj.accumulate("quantityOrdered", rs.getString("quantityOrdered"));
				obj.accumulate("status", rs.getString("status"));
				obj.accumulate("shippedDate", rs.getDate("shippedDate"));
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
	//localhost:8080/Rest/api/post/q2/NY
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
	//localhost:8080/Rest/api/post/q3/363
	//localhost:8080/Rest/api/post/q3/'Mini Classics'    as here customer name contains space needs to pass it in single quotes
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
				"where customers.customerName="+customerData;
		}
		stmt=connection.createStatement();
		rs=stmt.executeQuery(query);
		while(rs.next()) {
			obj=new JSONObject();
			obj.accumulate("productName", rs.getString("productName"));
			obj.accumulate("priceEach", rs.getString("priceEach"));
			obj.accumulate("quantityOrdered", rs.getString("quantityOrdered"));
			obj.accumulate("orderDate", rs.getString("orderDate"));
			obj.accumulate("shippedDate", rs.getDate("shippedDate"));
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
