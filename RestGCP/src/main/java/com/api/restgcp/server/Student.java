package com.api.restgcp.server;

public class Student {
	String id;
	String studentFName;
	String studentLName;
	String city;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudentFName() {
		return studentFName;
	}
	public void setStudentFName(String studentFName) {
		this.studentFName = studentFName;
	}
	public String getStudentLName() {
		return studentLName;
	}
	public void setStudentLName(String studentLName) {
		this.studentLName = studentLName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", studentFName=" + studentFName + ", studentLName=" + studentLName + ", city="
				+ city + "]";
	}
	
}
