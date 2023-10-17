package com.example.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Procurement;
import com.example.model.Requests;


@Repository
public interface RequestRepo extends JpaRepository<Requests,Long>{

	@Query(value="select * from requests where empid= :empid and devicetype= :deviceType",nativeQuery=true)
	Requests getRequesteduser(String empid,String deviceType);

//	Requests getByempID(String empId);
	@Query(value="select * from requests where empID= :empID and requeststatus='Accepted'",nativeQuery=true)
	List<Requests> isaccepted(String empID);
	
	@Query(value="select * from requests where requeststatus <> 'Accepted'",nativeQuery=true)
	List<Requests> allunacceptedrequests();
	
	@Modifying
	@Transactional
	@Query(value="delete from requests where empid= :empid",nativeQuery=true)
	void deletingrequestaftercompletion(String empid);

//	List<Requests> findByRequestStatusNot(String string);
}
