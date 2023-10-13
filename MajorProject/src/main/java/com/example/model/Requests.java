package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="requests")
public class Requests {
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ID;
	
	@Column(name="name")
	private String Name;
	@Column(name="empid")
	private String EmpID;
	@Column(name="department")
	private String Department;
	@Column(name="devicetype")
	private String DeviceType;
	@Column(name="requestedtime")
	private String RequestedTime;
	
	public Requests() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Requests(Long iD, String name, String empID, String department, String deviceType, String requestedTime) {
		super();
		ID = iD;
		Name = name;
		EmpID = empID;
		Department = department;
		DeviceType = deviceType;
		RequestedTime = requestedTime;
	}
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getEmpID() {
		return EmpID;
	}
	public void setEmpID(String empID) {
		EmpID = empID;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public String getDeviceType() {
		return DeviceType;
	}
	public void setDeviceType(String deviceType) {
		DeviceType = deviceType;
	}
	public String getRequestedTime() {
		return RequestedTime;
	}
	public void setRequestedTime(String requestedTime) {
		RequestedTime = requestedTime;
	}
	
	
	

}
