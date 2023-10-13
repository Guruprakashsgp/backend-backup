package com.example.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Administration;
import com.example.model.Procurement;

import jakarta.transaction.Transactional;

public interface ProcurementRepo extends JpaRepository<Procurement,Long> {

	
	@Query(value="select ip from procurement where ipnumber in (select max(ipnumber) from procurement)",nativeQuery=true)
	public String lastIPprocurement();
	
	@Query(value="select max(ipnumber) from procurement",nativeQuery=true)
	public int lastIPnoprocurement();
	
		
	@Query(value="select ipnumber from procurement where ipstatus='Alloted'",nativeQuery=true)
	public List<Procurement> getAllAllocatedIps();
	
	@Query(value="select * from procurement where empid= :empid",nativeQuery=true)
	public List<Procurement> getIPbyEmpID(String empid);
	
	
//	@Modifying
//	@Transactional
	@Query(value="select * from procurement where ip= :ipaddress",nativeQuery=true)
	Procurement useripfromprocurementusingIP(String ipaddress);
	
	@Query(value="select * from procurement where ipstatus='UnAlloted' limit 1",nativeQuery=true)
	Procurement getIpafterallslotsfilled();
}
