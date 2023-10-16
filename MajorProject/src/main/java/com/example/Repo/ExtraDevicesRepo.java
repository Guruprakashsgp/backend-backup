package com.example.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Administration;
import com.example.model.ExtraDevices;

import jakarta.transaction.Transactional;

public interface ExtraDevicesRepo extends JpaRepository<ExtraDevices,Long>{

	@Query(value="select ipaddress from extradevices where ipnumber in (select max(ipnumber) from extradevices)",nativeQuery=true)
	public String lastIPextradevices();
	
	@Query(value="select max(ipnumber) from extradevices",nativeQuery=true)
	public int lastIPnoextradevices();

		
	@Query(value="select ipnumber from extradevices where ipstatus='Alloted'",nativeQuery=true)
	public List<ExtraDevices> getAllAllocatedIps();
	
	@Query(value="select count(ipnumber) from extradevices where ipstatus='Alloted'",nativeQuery=true)
	public int getAllAllocatedIpsforpie();
	
	@Query(value="select ipaddress from extradevices where deviceno= :deviceno limit 1",nativeQuery=true)
	public String getnewDeviceNo(int deviceno);
	
	@Query(value="select * from extradevices where department= :department",nativeQuery=true)
	public List<ExtraDevices> getdevicesIpbyDept(String department);
	
	
//	@Modifying
//	@Transactional
	@Query(value="select * from extradevices where ip= :ipaddress",nativeQuery=true)
	ExtraDevices deviceipfromextradevicesusingIP(String ipaddress);
	
	@Query(value="select * from extradevices where ipstatus='UnAlloted' limit 1",nativeQuery=true)
	ExtraDevices getIpafterallslotsfilled();
}
