package com.example.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.ExtraClasses.NewIPCreation;
import com.example.Repo.AdminRepo;
import com.example.Repo.AdministrationRepo;
import com.example.Repo.ExtraDevicesRepo;
import com.example.Repo.ManufacturingRepo;
import com.example.Repo.ProcurementRepo;
import com.example.Repo.RequestRepo;
import com.example.Repo.SalesRepo;
import com.example.Repo.TotalIPRepo;
import com.example.Repo.UsersRepo;
import com.example.model.Admin;
import com.example.model.Administration;
import com.example.model.ExtraDevices;
import com.example.model.Manufacturing;
import com.example.model.Procurement;
import com.example.model.Requests;
import com.example.model.Sales;
import com.example.model.Users;

@RestController
@RequestMapping("/admin")
@Service
@CrossOrigin(origins="*")
public class AdminController {
	
	public AdminController(NewIPCreation newipcreation, AdminRepo adminrepo, AdministrationRepo administrationrepo,
			ExtraDevicesRepo extradevicesrepo, ManufacturingRepo manufacturingrepo, ProcurementRepo procurementrepo,
			SalesRepo salesrepo, TotalIPRepo totaliprepo, RequestRepo requestrepo) {
		super();
		this.newipcreation = newipcreation;
		this.adminrepo = adminrepo;
		this.administrationrepo = administrationrepo;
		this.extradevicesrepo = extradevicesrepo;
		this.manufacturingrepo = manufacturingrepo;
		this.procurementrepo = procurementrepo;
		this.salesrepo = salesrepo;
		this.totaliprepo = totaliprepo;
		this.requestrepo = requestrepo;
	}
	NewIPCreation newipcreation = new NewIPCreation();
	
	
	@Autowired
	private AdminRepo adminrepo;
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
	private TotalIPRepo totaliprepo;
	@Autowired
	private RequestRepo requestrepo;
	@Autowired
	private UsersRepo userrepo;
	@Autowired
	private JavaMailSender mailsender;
	
	////////////////////////////////////////////////////Admin
	
	@PostMapping("/addadmin/save")
	private String adminaddition(@RequestBody Admin adminCandidate)
	{	
		String email1=adminCandidate.getEmail();
		String username1=adminCandidate.getUserName();
		System.out.println(adminCandidate.getEmail());
		System.out.println(adminCandidate.getUserName());
		List<Admin> tocheckemail=adminrepo.findByEmail(adminCandidate.getEmail());
	
		List<Admin> tocheckusername=adminrepo.findByUserName(adminCandidate.getUserName());
		
		List<Admin> tocheckboth=adminrepo.findByEmailAndUserName(email1,username1);
		
		if(tocheckboth.size()>0)
		{
			return JSONObject.quote("email and username already exists");
		}
		else if(tocheckusername.size()>0)
		{
			return JSONObject.quote("username exists");
		}
		else if(tocheckemail.size()>0)
		{
			return JSONObject.quote("email already exists");
		}
		else
		{
			adminrepo.save(adminCandidate);
			return JSONObject.quote("admin saved");
		}
	}
	
	@GetMapping("/emailavailable/{email}")
	private String isemailavailable(@PathVariable("email") String email)
	{
		List<Admin> isemailavaliable=adminrepo.findByEmail(email);
		if(isemailavaliable.size()==0)
		{
			return JSONObject.quote("no email is present");
		}
		else
		{
			int otp=newipcreation.sendEmailforAdmin(email);
			return JSONObject.quote(String.valueOf(otp));
		}
	}
	
	@GetMapping("/alladmins")
	private List<Admin> getalladmins()
	{
		return adminrepo.findAll();
	}
	
	@PutMapping("/updateadmin")
	private String updateadmin(@RequestBody Admin adminguy)
	{
		String guyemail=adminguy.getEmail();
		List<Admin> guy=adminrepo.findByEmail(guyemail);
		guy.get(0).setPassword(adminguy.getPassword());
		adminrepo.save(guy.get(0));
		return JSONObject.quote("password updated");
	}
	
	@GetMapping("/login/{userName}/{password}")
	private String loginverification(@PathVariable("userName") String userName,@PathVariable("password") String password )
	{
		Admin loginverify=adminrepo.findByUserNameAndPassword(userName,password);
		if(loginverify==null)
		{
			System.out.println("no admin");
			return JSONObject.quote("no such admin");
		}
		else
		{
			System.out.println("admin");
			return JSONObject.quote("login successfull");
		}
	}
	
	
	
	///////////////////////////////////////////////////IP Extend
	@PutMapping("/ipextend/{whichip}/{value}")
	private String ipextend(@PathVariable("whichip") String whichip,@PathVariable("value") int value)
	{
		int oldIPno=totaliprepo.totalIpindepartments(whichip);
		int newIPno=oldIPno+value;
		int result=totaliprepo.updateiprange(whichip,newIPno);
		if(result!=0)
			return JSONObject.quote("IP extended");
		else
			return JSONObject.quote("IP not extended");

	}
	
	
	//////////////////////////////          Administration
	@PostMapping("/administration/save")
	private String saveadministrationcandidate(@RequestBody Administration candidate)
	{
		if((administrationrepo.lastIPnoadministration()+1)<=totaliprepo.totalIpindepartments("administration"))
		{
			String newIp=administrationrepo.lastIPadministration();
		     candidate.setIPAddress(newipcreation.NewIpforAdministration(newIp));
		     candidate.setIPNumber(administrationrepo.lastIPnoadministration()+1);
		     candidate.setIPStatus("Alloted");
		     System.out.println("hello "+candidate.getIPAddress());
		     administrationrepo.save(candidate);
		     return JSONObject.quote("IP Alloted");
		}
		else if((administrationrepo.lastIPnoadministration()+1)>totaliprepo.totalIpindepartments("administration"))
		{
			 Administration occupyIP=administrationrepo.getIpafterallslotsfilled();
			 if(occupyIP==null)
			 {
				 return JSONObject.quote("No IPs Availalbe");
			 }
			 else
			 {
				 occupyIP.setDepartment("Administration");
				 occupyIP.setName(candidate.getName());
				 occupyIP.setDeviceType(candidate.getDeviceType());
				 occupyIP.setEmpID(candidate.getEmpID());
				 occupyIP.setIPStatus("Alloted");
				 administrationrepo.save(occupyIP);
				 return JSONObject.quote("IP Alloted");
			 }
			 
		}
		else
		{
			return JSONObject.quote("No IPs Availalbe");
		}
		
		 
	}
	
	@GetMapping("/administration/allIP")
	private List<Administration> getAlladministrationIPs()
	{
		return administrationrepo.findByIpstatus();
	}
	
	@PostMapping("/administration/deleteIP/{ip}")
	private String deleteadministrationIP(@PathVariable("ip")String ip)
	{
		Administration usertodelete=administrationrepo.useripfromadministartionusingip(ip);
		Users user=userrepo.getByEmpid(usertodelete.getEmpID());
		
		String email=user.getEmail();
		 SimpleMailMessage message=new SimpleMailMessage();
		 message.setTo(email);
		 message.setText("We are Sorry to inform you that your IP access has been revoked and you cannot use that IP henceforth... ");
		 message.setSubject("IP Revoked");
		 message.setFrom("pgparkinglot@gmail.com");
		 mailsender.send(message);
		
		usertodelete.setName(null);
		usertodelete.setDepartment(null);
		usertodelete.setDeviceType(null);
		usertodelete.setIPStatus("UnAlloted");
		usertodelete.setEmpID(null);
		administrationrepo.save(usertodelete);
		
		return JSONObject.quote("User deleted");
	}
	
	@GetMapping("/administration/getipbyempid/{empid}")
	private List<Administration> getIpByEmpIdadministration(@PathVariable("empid") String empid)
	{
		return administrationrepo.getIPbyEmpID(empid);
	}
	
	@GetMapping("/administration/getallocatedIPno")
	private String getallallocatedIPno()
	{
		int result=administrationrepo.getAllAllocatedIpsforpie();
		return JSONObject.quote(String.valueOf(result));
	}
	
	/////////////////////////////////////    Sales
	
	@PostMapping("/sales/save")
	private String savesalescandidate(@RequestBody Sales candidate)
	{
		if((salesrepo.lastIPnosales()+1)<=totaliprepo.totalIpindepartments("sales"))
		{
			String newIp=salesrepo.lastIPsales();
		     candidate.setIPAddress(newipcreation.NewIpforSales(newIp));
		     candidate.setIPNumber(salesrepo.lastIPnosales()+1);
		     candidate.setIPStatus("Alloted");
		     System.out.println("hello "+candidate.getIPAddress());
		     salesrepo.save(candidate);
		     return JSONObject.quote("IP Alloted");
		}
		else if((salesrepo.lastIPnosales()+1)>totaliprepo.totalIpindepartments("sales"))
		{
			
			 Sales occupyIP=salesrepo.getIpafterallslotsfilled();
			 if(occupyIP==null)
			 {
				 return JSONObject.quote("No IPs Availalbe");
			 }
			 else
			 {
				 occupyIP.setDepartment(candidate.getDepartment());
				 occupyIP.setName(candidate.getName());
				 occupyIP.setDeviceType(candidate.getDeviceType());
				 occupyIP.setEmpID(candidate.getEmpID());
				 occupyIP.setIPStatus("Alloted");
				 salesrepo.save(occupyIP);
				 return JSONObject.quote("IP Alloted");
			 }
		}
		else
		{
			return JSONObject.quote("No IPs Availalbe");
		}
		 
	}
	
	@GetMapping("/sales/allsalesIP")
	private List<Sales> getAllIPs()
	{
		return salesrepo.findByIpstatus();
	}
	
	@PostMapping("/sales/deleteIP/{ip}")
	private String deletesalesIP(@PathVariable("ip")String ip)
	{
		Sales usertodelete=salesrepo.useripfromsalesusingIP(ip);
		
		Users user=userrepo.getByEmpid(usertodelete.getEmpID());
		String email=user.getEmail();
		 SimpleMailMessage message=new SimpleMailMessage();
		 message.setTo(email);
		 message.setText("We are Sorry to inform you that your IP access has been revoked and you cannot use that IP henceforth... ");
		 message.setSubject("IP Revoked");
		 message.setFrom("pgparkinglot@gmail.com");
		 mailsender.send(message);
		 
		usertodelete.setName(null);
		usertodelete.setDepartment(null);
		usertodelete.setDeviceType(null);
		usertodelete.setIPStatus("UnAlloted");
		usertodelete.setEmpID(null);
		salesrepo.save(usertodelete);		
		return JSONObject.quote("User deleted");
	}
	
	@GetMapping("/sales/getipbyempid/{empid}")
	private List<Sales> getIpByEmpIdsales(@PathVariable("empid") String empid)
	{
		return salesrepo.getIPbyEmpID(empid);
	}
	
	

	@GetMapping("/sales/getallocatedIPno")
	private String getallallocatedIPnoSales()
	{
		int result=salesrepo.getAllAllocatedIpsforpie();
		return JSONObject.quote(String.valueOf(result));
	}
	
	/////////////////////////////////////  Manufacturing
	
	
	@PostMapping("/manufacturing/save")
	private String savemanufacturingcandidate(@RequestBody Manufacturing candidate)
	{
		if((manufacturingrepo.lastIPnomanufacturing()+1)<=totaliprepo.totalIpindepartments("manufacturing"))
		{
			String newIp=manufacturingrepo.lastIPmanufacturing();
		     candidate.setIPAddress(newipcreation.NewIpforManufacturing(newIp));
		     candidate.setIPNumber(manufacturingrepo.lastIPnomanufacturing()+1);
		     candidate.setIPStatus("Alloted");
		     System.out.println("hello "+candidate.getIPAddress());
		     manufacturingrepo.save(candidate);
		     return JSONObject.quote("IP Alloted");
		}
		else if((manufacturingrepo.lastIPnomanufacturing()+1)>totaliprepo.totalIpindepartments("manufacturing"))
		{
			 Manufacturing occupyIP=manufacturingrepo.getIpafterallslotsfilled();
			 if(occupyIP==null)
			 {
				 return JSONObject.quote("No IPs Availalbe");
			 }
			 else
			 {
			 occupyIP.setDepartment(candidate.getDepartment());
			 occupyIP.setName(candidate.getName());
			 occupyIP.setDeviceType(candidate.getDeviceType());
			 occupyIP.setEmpID(candidate.getEmpID());
			 occupyIP.setIPStatus("Alloted");
			 manufacturingrepo.save(occupyIP);
			 return JSONObject.quote("IP Alloted");
			 }
		}
		else
		{
			return JSONObject.quote("No IPs Availalbe");
		}
		 
	}
	
	@GetMapping("/manufacturing/allIP")
	private List<Manufacturing> getAllmanufacturingIPs()
	{
		return manufacturingrepo.findByIpstatus();
	}
	
	@PostMapping("/manufacturing/deleteIP/{ip}")
	private String deletemanufacturingIP(@PathVariable("ip")String ip)
	{
		Manufacturing usertodelete=manufacturingrepo.useripfrommanufacturingusingIP(ip);
		
		Users user=userrepo.getByEmpid(usertodelete.getEmpID());
		String email=user.getEmail();
		 SimpleMailMessage message=new SimpleMailMessage();
		 message.setTo(email);
		 message.setText("We are Sorry to inform you that your IP access has been revoked and you cannot use that IP henceforth... ");
		 message.setSubject("IP Revoked");
		 message.setFrom("pgparkinglot@gmail.com");
		 mailsender.send(message);
		 
		 
		usertodelete.setName(null);
		usertodelete.setDepartment(null);
		usertodelete.setDeviceType(null);
		usertodelete.setIPStatus("UnAlloted");
		usertodelete.setEmpID(null);
		manufacturingrepo.save(usertodelete);		
		return JSONObject.quote("User deleted");
	}
	
	@GetMapping("/manfacturing/getipbyempid/{empid}")
	private List<Manufacturing> getIpByEmpIdmanufacturing(@PathVariable("empid") String empid)
	{
		return manufacturingrepo.getIPbyEmpID(empid);
	}
	

	@GetMapping("/manufacturing/getallocatedIPno")
	private String getallallocatedIPnoManufacturing()
	{
		int result=manufacturingrepo.getAllAllocatedIpsforpie();
		return JSONObject.quote(String.valueOf(result));
	}
	
	/////////////////////////////////// Procurement
	
	@PostMapping("/procurement/save")
	private String saveprocurementcandidate(@RequestBody Procurement candidate)
	{
		if((procurementrepo.lastIPnoprocurement()+1)<=totaliprepo.totalIpindepartments("procurement"))
		{
			String newIp=procurementrepo.lastIPprocurement();
		     candidate.setIPAddress(newipcreation.NewIpforProcurement(newIp));
		     candidate.setIPNumber(procurementrepo.lastIPnoprocurement()+1);
		     candidate.setIPStatus("Alloted");
		     System.out.println("hello "+candidate.getIPAddress());
		     procurementrepo.save(candidate);
		     return JSONObject.quote("IP Alloted");
		}
		else if((procurementrepo.lastIPnoprocurement()+1)>totaliprepo.totalIpindepartments("procurement"))
		{
			 Procurement occupyIP=procurementrepo.getIpafterallslotsfilled();
			 if(occupyIP==null)
			 {
				 return JSONObject.quote("No IPs Availalbe");
			 }
			 else
			 {
			 occupyIP.setDepartment(candidate.getDepartment());
			 occupyIP.setName(candidate.getName());
			 occupyIP.setDeviceType(candidate.getDeviceType());
			 occupyIP.setEmpID(candidate.getEmpID());
			 occupyIP.setIPStatus("Alloted");
			 procurementrepo.save(occupyIP);
			 return JSONObject.quote("IP Alloted");
			 }
		}
		else
		{
			return JSONObject.quote("No IPs Availalbe");
		}
		
		 
	}
	
	@GetMapping("/procurement/allIP")
	private List<Procurement> getAllprocurementIPs()
	{
		return procurementrepo.findByIpstatus();
	}
	
	@PostMapping("/procurement/deleteIP/{ip}")
	private String deleteprocurementIP(@PathVariable("ip")String ip)
	{
		Procurement usertodelete=procurementrepo.useripfromprocurementusingIP(ip);
		
		Users user=userrepo.getByEmpid(usertodelete.getEmpID());
		String email=user.getEmail();
		 SimpleMailMessage message=new SimpleMailMessage();
		 message.setTo(email);
		 message.setText("We are Sorry to inform you that your IP access has been revoked and you cannot use that IP henceforth... ");
		 message.setSubject("IP Revoked");
		 message.setFrom("pgparkinglot@gmail.com");
		 mailsender.send(message);
		 
		usertodelete.setName(null);
		usertodelete.setDepartment(null);
		usertodelete.setDeviceType(null);
		usertodelete.setIPStatus("UnAlloted");
		usertodelete.setEmpID(null);
		procurementrepo.save(usertodelete);
		return JSONObject.quote("User deleted");
	}
	
	@GetMapping("/procurement/getipbyempid/{empid}")
	private List<Procurement> getIpByEmpIdprocurement(@PathVariable("empid") String empid)
	{
		return procurementrepo.getIPbyEmpID(empid);
	}
	

	@GetMapping("/procurement/getallocatedIPno")
	private String getallallocatedIPnoProcurement()
	{
		int result=procurementrepo.getAllAllocatedIpsforpie();
		return JSONObject.quote(String.valueOf(result));
	}
	
	//////////////////////////////////// ExtraDevices
	
	@PostMapping("/extradevice/save")
	private String saveextradevicecandidate(@RequestBody ExtraDevices device)
	{
		if((extradevicesrepo.getnewDeviceNo(device.getDeviceno()))==null)
		{
			if((extradevicesrepo.lastIPnoextradevices()+1)<=totaliprepo.totalIpindevices())
			{
				String newIp=extradevicesrepo.lastIPextradevices();
			     device.setIPAddress(newipcreation.NewIpforExtradevices(newIp));
			     device.setIPNumber(extradevicesrepo.lastIPnoextradevices()+1);
			     device.setIPStatus("Alloted");
			    // System.out.println("hello "+candidate.getIPAddress());
			     extradevicesrepo.save(device);
			     return JSONObject.quote("IP Alloted");
			}
			else if((extradevicesrepo.lastIPnoextradevices()+1)>totaliprepo.totalIpindevices())
			{
				 ExtraDevices occupyIP=extradevicesrepo.getIpafterallslotsfilled();
				 if(occupyIP==null)
				 {
					 return JSONObject.quote("No IPs Availalbe");
				 }
				 else
				 {
				 occupyIP.setDepartment(device.getDepartment());
				 occupyIP.setDeviceType(device.getDeviceType());
				 occupyIP.setDeviceno(device.getDeviceno());
				 occupyIP.setIPStatus("Alloted");
				 extradevicesrepo.save(occupyIP);
				 return JSONObject.quote("IP Alloted");
				 }
			}
			else
			{
				return JSONObject.quote("No IPs Availalbe");
			}
		}
		else
		{
			return JSONObject.quote("Device Already Exists");
		}
		
		 
	}
	
	@GetMapping("/extradevice/allIP")
	private List<ExtraDevices> getAllextradevicesIPs()
	{
		return extradevicesrepo.findByIpstatus();
	}
	
	@PostMapping("/extradevices/deleteIP/{ip}")
	private String deleteextrdevicesIP(@PathVariable("ip")String ip)
	{
		ExtraDevices devicetodelete=extradevicesrepo.deviceipfromextradevicesusingIP(ip);
		devicetodelete.setDepartment(null);
		devicetodelete.setDeviceType(null);
		devicetodelete.setIPStatus("UnAlloted");
		devicetodelete.setDeviceno(0);
		extradevicesrepo.save(devicetodelete);
		return JSONObject.quote("User deleted");
	}
	
	@GetMapping("/extradevices/getipbydepartment/{department}")
	private List<ExtraDevices> getIpBydepartmentextradevices(@PathVariable("department") String department)
	{
		return extradevicesrepo.getdevicesIpbyDept(department);
	}
	

	@GetMapping("/extradevices/getallocatedIPno")
	private String getallallocatedIPnoExtraDevices()
	{
		int result=extradevicesrepo.getAllAllocatedIpsforpie();
		return JSONObject.quote(String.valueOf(result));
	}
	
	
	////////////////////////////////////////////Requests
	
	@PostMapping("/requests/save")
	private String saveRequests(Requests request)
	{
		requestrepo.save(request);
		return JSONObject.quote("request saved");
	}
	
	@GetMapping("/requests/allrequests")
	private List<Requests> allrequest()
	{
		return requestrepo.allunacceptedrequests();
	}
	
	@PostMapping("/requests/accepted/{empId}/{deviceType}")
	private String requestaccepted(@PathVariable("empId") String empId,@PathVariable("deviceType") String deviceType)
	{
		Requests user=requestrepo.getRequesteduser(empId,deviceType);
//		Requests user1=requestrepo.getByempID(empId);
		user.setRequestStatus("Accepted");
		requestrepo.save(user);
		Users user1=userrepo.getByEmpid(empId);
		String email=user1.getEmail();
		 SimpleMailMessage message=new SimpleMailMessage();
		 message.setTo(email);
		 message.setText("Your Request has be accepted and an IP Address is allocated for the corresponding Device. For further info visit IP list section in your Dashboard.");
		 message.setSubject("Request Completion");
		 message.setFrom("pgparkinglot@gmail.com");
		 mailsender.send(message);
		return JSONObject.quote("request accepted");	
	}
	
	@GetMapping("/requests/isaccepted/{empID}")
	private List<Requests> isaccepted(@PathVariable("empID") String empID)
	{
		return requestrepo.isaccepted(empID);
	}
	
	@GetMapping("/auditlog/{empId}/{deviceType}")
	private Requests getuserinauditlog(@PathVariable("empId") String empId,@PathVariable("deviceType") String deviceType)
	{
		Requests user=requestrepo.getRequesteduser(empId,deviceType);
		return user;
	}
	
	@GetMapping("/requests/acceptedrequestsforauditlog")
	private List<Requests> getrequestsforaudit()
	{
		List<Requests> result=requestrepo.findByRequestStatus();
		return result;
	}

	
	
	
	

	
	
	
	
}
