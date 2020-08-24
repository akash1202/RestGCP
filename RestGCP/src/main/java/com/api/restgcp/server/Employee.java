package com.api.restgcp.server;

public class Employee {

    String employeeID;
    String employeeName;
    String designation;
    String salary;
    String city;
    
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Employee [EmployeeID=" + employeeID + ", EmployeeName=" + employeeName + ", Designation=" + designation + ", Salary="+salary+" city="
		+ city + "]";
	}
	
	
    
    

}
