package com.example.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.Administration;

import jakarta.transaction.Transactional;

@Repository
public interface AdministrationRepo extends JpaRepository<Administration,Long>{

	@Query(value="select ip from Administration where ipnumber in (select max(ipnumber) from Administration)",nativeQuery=true)
	public String lastIPadministration();

	@Query(value="select max(ipnumber) from Administration",nativeQuery=true)
	public int lastIPnoadministration();
		
	@Query(value="select ipnumber from administration where ipstatus='Alloted'",nativeQuery=true)
	public List<Administration> getAllAllocatedIps();
	
	@Query(value="select * from administration where empid= :empid",nativeQuery=true)
	public List<Administration> getIPbyEmpID(String empid);
	
//	@Modifying
//	@Transactional
	@Query(value="select * from administration where ip= :ipaddress",nativeQuery=true)
	Administration useripfromadministartionusingip(String ipaddress);
	
	@Query(value="select * from administration where ipstatus='UnAlloted' limit 1",nativeQuery=true)
	Administration getIpafterallslotsfilled();
	
	
	
	
}
