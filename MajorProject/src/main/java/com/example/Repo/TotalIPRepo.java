package com.example.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.model.TotalIP;

import jakarta.transaction.Transactional;

public interface TotalIPRepo extends JpaRepository<TotalIP,Long> {
	
	@Query(value="Select total from iprange where whichip= :whichip1",nativeQuery=true)
	int totalIpindepartments(String whichip1);
	
	@Query(value="Select total from iprange where whichip='deviceip'",nativeQuery=true)
	int totalIpindevices();
	
	@Modifying
	@Transactional
	@Query(value="update iprange set total =?2 where whichip =?1",nativeQuery=true)
	int updateiprange(String whichip,int total);

}
