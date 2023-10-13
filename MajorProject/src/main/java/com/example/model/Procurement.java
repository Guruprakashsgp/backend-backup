package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Procurement")
public class Procurement {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ID;
	
	@Column(name="name")
	private String Name;
	
	@Column(name="department")
	private String Department;
	
	@Column(name="devicetype")
	private String DeviceType;
	
	@Column(name="ip")
	private String IPAddress;
	
	@Column(name="ipnumber")
	private int IPNumber;
	
	@Column(name="ipstatus")
	private String IPStatus;
	
	@Column(name="empid")
	private String EmpID;
	
	public String getEmpID() {
		return EmpID;
	}
	public void setEmpID(String empID) {
		EmpID = empID;
	}
	
	public String getIPStatus() {
		return IPStatus;
	}
	public void setIPStatus(String iPStatus) {
		IPStatus = iPStatus;
	}
	
	public int getIPNumber() {
		return IPNumber;
	}
	public void setIPNumber(int iPNumber) {
		IPNumber = iPNumber;
	}
	
	
	public Procurement() {
		super();
	}
	public Procurement(String name, String department, String deviceType, String iPAddress,int iPnumber,String iPstatus,String empID) {
	Name = name;
	Department = department;
	DeviceType = deviceType;
	IPAddress = iPAddress;
	IPNumber=iPnumber;
	IPStatus=iPstatus;
	EmpID=empID;
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
	public String getIPAddress() {
		return IPAddress;
	}
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	

}
