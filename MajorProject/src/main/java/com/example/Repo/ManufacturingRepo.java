package com.example.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Administration;
import com.example.model.Manufacturing;

import jakarta.transaction.Transactional;

public interface ManufacturingRepo extends JpaRepository<Manufacturing,Long> {

	
	@Query(value="select ip from manufacturing where ipnumber in (select max(ipnumber) from manufacturing)",nativeQuery=true)
	public String lastIPmanufacturing();
	
	@Query(value="select max(ipnumber) from manufacturing",nativeQuery=true)
	public int lastIPnomanufacturing();
	
		
	@Query(value="select ipnumber from manufacturing where ipstatus='Alloted'",nativeQuery=true)
	public List<Manufacturing> getAllAllocatedIps();
	
	@Query(value="select count(ipnumber) from manufacturing where ipstatus='Alloted'",nativeQuery=true)
	public int getAllAllocatedIpsforpie();
	
	@Query(value="select * from manufacturing where empid= :empid",nativeQuery=true)
	public List<Manufacturing> getIPbyEmpID(String empid);
	
	
//	@Modifying
//	@Transactional
	@Query(value="select * from manufacturing where ip= :ipaddress",nativeQuery=true)
	Manufacturing useripfrommanufacturingusingIP(String ipaddress);
	
	@Query(value="select * from manufacturing where ipstatus='UnAlloted' limit 1",nativeQuery=true)
	Manufacturing getIpafterallslotsfilled();
}
