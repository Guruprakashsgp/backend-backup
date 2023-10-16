package com.example.ExtraClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	@Autowired
	private JavaMailSender mailsender;
	
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
	
	
	public int sendEmailforAdmin(String toEmail) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(toEmail);
		Random rand=new Random();
		int x=111111;
		int y=999999;
		int z=rand.nextInt(y-x+1)+x;
		message.setText(String.valueOf(z));
		message.setSubject("Otp for your Login process");
		message.setFrom("pgparkinglot@gmail.com");
		
		mailsender.send(message);
		
		return z;
		
	}
	
	

}
