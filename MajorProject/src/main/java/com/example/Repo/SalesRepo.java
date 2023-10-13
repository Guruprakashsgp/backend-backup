package com.example.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Administration;
import com.example.model.Sales;

import jakarta.transaction.Transactional;

public interface SalesRepo extends JpaRepository<Sales,Long> {

	
	@Query(value="select ip from sales where ipnumber in (select max(ipnumber) from sales)",nativeQuery=true)
	public String lastIPsales();
	
	@Query(value="select max(ipnumber) from sales",nativeQuery=true)
	public int lastIPnosales();
		
	@Query(value="select ipnumber from sales where ipstatus='Alloted'",nativeQuery=true)
	public List<Sales> getAllAllocatedIps();
	
	
	@Query(value="select * from sales where empid= :empid",nativeQuery=true)
	public List<Sales> getIPbyEmpID(String empid);
	
	
//	@Modifying
//	@Transactional
	@Query(value="select * from sales where ip= :ipaddress",nativeQuery=true)
	Sales useripfromsalesusingIP(String ipaddress);
	
	@Query(value="select * from sales where ipstatus='UnAlloted' limit 1",nativeQuery=true)
	Sales getIpafterallslotsfilled();
}
