package com.api.restgcp.client;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class DatabaseConnection {

	private String dbName="classicmodels";
	private String userName="admin";
	private String userPass="admin123";
	private String hostName="programdebug.cbhqhvwkvabh.us-east-2.rds.amazonaws.com";
	private String port="3306";
	
	private String jdbcURL="jdbc:mysql://"+hostName+":"+port+"/"+dbName+"?user="+userName+"&password="+userPass;
	private Connection con=null;
	
	@Path("/aws")
	@Produces(MediaType.APPLICATION_JSON)
	public Connection getConnection() {
		try {
			System.out.println("Load Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Make Connection with MySql");
			con=DriverManager.getConnection(jdbcURL);
			
		}catch(ClassNotFoundException e) {
			System.out.println("Class Not Found Error:"+e.getMessage());
		}catch(SQLException e) {
			System.out.println("");
		}
		return con;
	}

}
