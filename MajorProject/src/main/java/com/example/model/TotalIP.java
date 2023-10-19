package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="iprange")
public class TotalIP {
	
	@Id
	private Long ID;
	@Column(name="whichip")
	private String WhichIP;
	@Column(name="total")
	private int Total;
	
	public TotalIP() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TotalIP(Long iD, String whichIP, int total) {
		super();
		ID = iD;
		WhichIP = whichIP;
		Total = total;
	}
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getWhichIP() {
		return WhichIP;
	}
	public void setWhichIP(String whichIP) {
		WhichIP = whichIP;
	}
	public int getTotal() {
		return Total;
	}
	public void setTotal(int total) {
		Total = total;
	}
	

}
