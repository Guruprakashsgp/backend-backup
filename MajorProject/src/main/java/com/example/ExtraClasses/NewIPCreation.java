package com.example.ExtraClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Repo.AdministrationRepo;
import com.example.Repo.ExtraDevicesRepo;
import com.example.Repo.ManufacturingRepo;
import com.example.Repo.ProcurementRepo;
import com.example.Repo.SalesRepo;

@Service
public class NewIPCreation {
	
	@Autowired
	private AdministrationRepo administrationrepo;
	
	@Autowired
	private ExtraDevicesRepo extradevicesrepo;
	@Autowired
	private ManufacturingRepo manufacturingrepo;
	@Autowired
	private ProcurementRepo procurementrepo;
	@Autowired
	private SalesRepo salesrepo;
	
	static int extendValue=200;
	

	
	public String NewIpforAdministration(String nextIp)
	{
		//substring of 11	
		String lastIP=nextIp.substring(11);
		int ipno=Integer.parseInt(lastIP);
		int newipno=ipno+1;
		String newIp=nextIp.substring(0,11)+String.valueOf(newipno);
		System.out.println("one"+newIp);
		return newIp;
		
	}
	
	public String NewIpforSales(String nextIp)
	{
		//substring of 11
		
		String lastIP=nextIp.substring(11);
		int ipno=Integer.parseInt(lastIP);
		int newipno=ipno+1;
		String newIp=nextIp.substring(0,11)+String.valueOf(newipno);
		
		return newIp;
		
	}
	
	
	public String NewIpforManufacturing(String nextIp)
	{
		//substring of 11
		
		String lastIP=nextIp.substring(11);
		int ipno=Integer.parseInt(lastIP);
		int newipno=ipno+1;
		String newIp=nextIp.substring(0,11)+String.valueOf(newipno);
		
		return newIp;
		
	}
	
	
	public String NewIpforProcurement(String nextIp)
	{
		//substring of 11
		
		String lastIP=nextIp.substring(11);
		int ipno=Integer.parseInt(lastIP);
		int newipno=ipno+1;
		String newIp=nextIp.substring(0,11)+String.valueOf(newipno);
		
		return newIp;
		
	}
	
	public String NewIpforExtradevices(String nextIp)
	{
		//substring of 11
		
		String lastIP=nextIp.substring(11);
		int ipno=Integer.parseInt(lastIP);
		int newipno=ipno+1;
		String newIp=nextIp.substring(0,11)+String.valueOf(newipno);
		
		return newIp;
		
	}
	
	public String NewIpforAdministrationafterdeletion(String nextIp,int newipno)
	{
		//substring of 11	
		String lastIP=nextIp.substring(11);
		int ipno=Integer.parseInt(lastIP);
		String newIp=nextIp.substring(0,11)+String.valueOf(newipno);
		System.out.println("one"+newIp);
		return newIp;
		
	}
	
	
	
	
	
	public int getipaftercompletingslots(List<Integer> ipnum)
	{
		List<Integer> l1=new ArrayList<Integer>();
		int diff=ipnum.get(0)-0;
		int size1=ipnum.size();
		Collections.sort(ipnum);
		for(int i=0;i<size1;i++)
		{
			if(ipnum.get(i)-i!= diff)
			{
				while(diff < ipnum.get(i)-i)
				{
					l1.add(diff+i);
					diff++;
				}
			}
		}
		if(l1.isEmpty()==true)
		{
			return 909090;
		}
		else
		{
			return l1.get(0);
		}
		
		
	}
	
	

}
