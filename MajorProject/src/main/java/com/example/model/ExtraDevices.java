package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Extradevices")

public class ExtraDevices {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ID;
	
	@Column(name="deviceno")
	private int Deviceno;
	
	@Column(name="department")
	private String Department;
	
	@Column(name="devicetype")
	private String DeviceType;
	
	@Column(name="ipaddress")
	private String IPAddress;
	
	@Column(name="ipnumber")
	private int IPNumber;
	
	@Column(name="ipstatus")
	private String IPStatus;
	
	
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
	
	
	public ExtraDevices() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExtraDevices(int deviceNo,String department, String deviceType, String iPAddress,int iPNumber,String iPstatus) {
		super();
		Deviceno=deviceNo;
		Department = department;
		DeviceType = deviceType;
		IPAddress = iPAddress;
		IPNumber=iPNumber;
		IPStatus=iPstatus;
		
	}
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public int getDeviceno() {
		return Deviceno;
	}
	public void setDeviceno(int deviceno) {
		Deviceno = deviceno;
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
