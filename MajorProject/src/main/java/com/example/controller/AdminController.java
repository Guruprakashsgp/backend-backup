package com.example.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.ExtraClasses.NewIPCreation;
import com.example.Repo.AdministrationRepo;
import com.example.Repo.ExtraDevicesRepo;
import com.example.Repo.ManufacturingRepo;
import com.example.Repo.ProcurementRepo;
import com.example.Repo.SalesRepo;
import com.example.Repo.TotalIPRepo;
import com.example.model.Administration;
import com.example.model.ExtraDevices;
import com.example.model.Manufacturing;
import com.example.model.Procurement;
import com.example.model.Sales;

@RestController
@RequestMapping("/admin")
@Service
@CrossOrigin(origins="*")
public class AdminController {
	
	public AdminController(NewIPCreation newipcreation, AdministrationRepo administrationrepo, ExtraDevicesRepo extradevicesrepo,
			ManufacturingRepo manufacturingrepo, ProcurementRepo procurementrepo, SalesRepo salesrepo) {
		super();
		this.newipcreation = newipcreation;
		this.administrationrepo = administrationrepo;
		this.extradevicesrepo = extradevicesrepo;
		this.manufacturingrepo = manufacturingrepo;
		this.procurementrepo = procurementrepo;
		this.salesrepo = salesrepo;
	}



	
	NewIPCreation newipcreation = new NewIPCreation();
	
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
	
	
	@PutMapping("/ipextend/{whichip}/{value}")
	private String ipextend(@PathVariable("whichip") String whichip,@PathVariable("value") int value)
	{
		int result=totaliprepo.updateiprange(whichip, value);
		if(result!=0)
			return JSONObject.quote("IP extended");
		else
			return JSONObject.quote("IP not extended");

	}
	
	
	//////////////////////////////          Administration
	@PostMapping("/administration/save")
	private String saveadministrationcandidate(@RequestBody Administration candidate)
	{
		if((administrationrepo.lastIPnoadministration()+1)<=totaliprepo.totalIpindepartments())
		{
			String newIp=administrationrepo.lastIPadministration();
		     candidate.setIPAddress(newipcreation.NewIpforAdministration(newIp));
		     candidate.setIPNumber(administrationrepo.lastIPnoadministration()+1);
		     candidate.setIPStatus("Alloted");
		     System.out.println("hello "+candidate.getIPAddress());
		     administrationrepo.save(candidate);
		     return JSONObject.quote("IP Alloted");
		}
		else if((administrationrepo.lastIPnoadministration()+1)>totaliprepo.totalIpindepartments())
		{
			 Administration occupyIP=administrationrepo.getIpafterallslotsfilled();
			 occupyIP.setDepartment("Administration");
			 occupyIP.setName(candidate.getName());
			 occupyIP.setDeviceType(candidate.getDeviceType());
			 occupyIP.setEmpID(candidate.getEmpID());
			 occupyIP.setIPStatus("Alloted");
			 administrationrepo.save(occupyIP);
			 return JSONObject.quote("IP Alloted");
		}
		else
		{
			return ("No IPs Availalbe");
		}
		 
	}
	
	@GetMapping("/administration/allIP")
	private List<Administration> getAlladministrationIPs()
	{
		return administrationrepo.findAll();
	}
	
	@PostMapping("/administration/deleteIP/{ip}")
	private String deleteadministrationIP(@PathVariable("ip")String ip)
	{
		Administration usertodelete=administrationrepo.useripfromadministartionusingip(ip);
		usertodelete.setName(null);
		usertodelete.setDepartment(null);
		usertodelete.setDeviceType(null);
		usertodelete.setIPStatus("UnAlloted");
		usertodelete.setEmpID(null);
		administrationrepo.save(usertodelete);
		
		return "User deleted";
	}
	
	@GetMapping("/administration/getipbyempid/{empid}")
	private List<Administration> getIpByEmpIdadministration(@PathVariable("empid") String empid)
	{
		return administrationrepo.getIPbyEmpID(empid);
	}
	
	/////////////////////////////////////    Sales
	
	@PostMapping("/sales/save")
	private String savesalescandidate(@RequestBody Sales candidate)
	{
		if((salesrepo.lastIPnosales()+1)<=totaliprepo.totalIpindepartments())
		{
			String newIp=salesrepo.lastIPsales();
		     candidate.setIPAddress(newipcreation.NewIpforSales(newIp));
		     candidate.setIPNumber(salesrepo.lastIPnosales()+1);
		     candidate.setIPStatus("Alloted");
		     System.out.println("hello "+candidate.getIPAddress());
		     salesrepo.save(candidate);
		     return JSONObject.quote("IP Alloted");
		}
		else if((salesrepo.lastIPnosales()+1)>totaliprepo.totalIpindepartments())
		{
			 Sales occupyIP=salesrepo.getIpafterallslotsfilled();
			 occupyIP.setDepartment(candidate.getDepartment());
			 occupyIP.setName(candidate.getName());
			 occupyIP.setDeviceType(candidate.getDeviceType());
			 occupyIP.setEmpID(candidate.getEmpID());
			 occupyIP.setIPStatus("Alloted");
			 salesrepo.save(occupyIP);
			 return JSONObject.quote("IP Alloted");
		}
		else
		{
			return JSONObject.quote("No IPs Availalbe");
		}
		 
	}
	
	@GetMapping("/sales/allsalesIP")
	private List<Sales> getAllIPs()
	{
		return salesrepo.findAll();
	}
	
	@PostMapping("/sales/deleteIP/{ip}")
	private String deletesalesIP(@PathVariable("ip")String ip)
	{
		Sales usertodelete=salesrepo.useripfromsalesusingIP(ip);
		usertodelete.setName(null);
		usertodelete.setDepartment(null);
		usertodelete.setDeviceType(null);
		usertodelete.setIPStatus("UnAlloted");
		usertodelete.setEmpID(null);
		salesrepo.save(usertodelete);		
		return "IP deleted";
	}
	
	@GetMapping("/sales/getipbyempid/{empid}")
	private List<Sales> getIpByEmpIdsales(@PathVariable("empid") String empid)
	{
		return salesrepo.getIPbyEmpID(empid);
	}
	
	/////////////////////////////////////  Manufacturing
	
	
	@PostMapping("/manufacturing/save")
	private String savemanufacturingcandidate(@RequestBody Manufacturing candidate)
	{
		if((manufacturingrepo.lastIPnomanufacturing()+1)<=totaliprepo.totalIpindepartments())
		{
			String newIp=manufacturingrepo.lastIPmanufacturing();
		     candidate.setIPAddress(newipcreation.NewIpforManufacturing(newIp));
		     candidate.setIPNumber(manufacturingrepo.lastIPnomanufacturing()+1);
		     candidate.setIPStatus("Alloted");
		     System.out.println("hello "+candidate.getIPAddress());
		     manufacturingrepo.save(candidate);
		     return JSONObject.quote("IP Alloted");
		}
		else if((manufacturingrepo.lastIPnomanufacturing()+1)>totaliprepo.totalIpindepartments())
		{
			 Manufacturing occupyIP=manufacturingrepo.getIpafterallslotsfilled();
			 occupyIP.setDepartment(candidate.getDepartment());
			 occupyIP.setName(candidate.getName());
			 occupyIP.setDeviceType(candidate.getDeviceType());
			 occupyIP.setEmpID(candidate.getEmpID());
			 occupyIP.setIPStatus("Alloted");
			 manufacturingrepo.save(occupyIP);
			 return JSONObject.quote("IP Alloted");
		}
		else
		{
			return JSONObject.quote("No IPs Availalbe");
		}
		 
	}
	
	@GetMapping("/manufacturing/allIP")
	private List<Manufacturing> getAllmanufacturingIPs()
	{
		return manufacturingrepo.findAll();
	}
	
	@PostMapping("/manufacturing/deleteIP/{ip}")
	private String deletemanufacturingIP(@PathVariable("ip")String ip)
	{
		Manufacturing usertodelete=manufacturingrepo.useripfrommanufacturingusingIP(ip);
		usertodelete.setName(null);
		usertodelete.setDepartment(null);
		usertodelete.setDeviceType(null);
		usertodelete.setIPStatus("UnAlloted");
		usertodelete.setEmpID(null);
		manufacturingrepo.save(usertodelete);		
		return "IP deleted";
	}
	
	@GetMapping("/manfacturing/getipbyempid/{empid}")
	private List<Manufacturing> getIpByEmpIdmanufacturing(@PathVariable("empid") String empid)
	{
		return manufacturingrepo.getIPbyEmpID(empid);
	}
	
	/////////////////////////////////// Procurement
	
	@PostMapping("/procurement/save")
	private String saveprocurementcandidate(@RequestBody Procurement candidate)
	{
		if((procurementrepo.lastIPnoprocurement()+1)<=totaliprepo.totalIpindepartments())
		{
			String newIp=procurementrepo.lastIPprocurement();
		     candidate.setIPAddress(newipcreation.NewIpforProcurement(newIp));
		     candidate.setIPNumber(procurementrepo.lastIPnoprocurement()+1);
		     candidate.setIPStatus("Alloted");
		     System.out.println("hello "+candidate.getIPAddress());
		     procurementrepo.save(candidate);
		     return JSONObject.quote("IP Alloted");
		}
		else if((procurementrepo.lastIPnoprocurement()+1)>totaliprepo.totalIpindepartments())
		{
			 Procurement occupyIP=procurementrepo.getIpafterallslotsfilled();
			 occupyIP.setDepartment(candidate.getDepartment());
			 occupyIP.setName(candidate.getName());
			 occupyIP.setDeviceType(candidate.getDeviceType());
			 occupyIP.setEmpID(candidate.getEmpID());
			 occupyIP.setIPStatus("Alloted");
			 procurementrepo.save(occupyIP);
			 return JSONObject.quote("IP Alloted");
		}
		else
		{
			return JSONObject.quote("No IPs Availalbe");
		}
		 
	}
	
	@GetMapping("/procurement/allIP")
	private List<Procurement> getAllprocurementIPs()
	{
		return procurementrepo.findAll();
	}
	
	@DeleteMapping("/procurement/deleteIP/{ip}")
	private String deleteprocurementIP(@PathVariable("ip")String ip)
	{
		Procurement usertodelete=procurementrepo.useripfromprocurementusingIP(ip);
		usertodelete.setName(null);
		usertodelete.setDepartment(null);
		usertodelete.setDeviceType(null);
		usertodelete.setIPStatus("UnAlloted");
		usertodelete.setEmpID(null);
		procurementrepo.save(usertodelete);
		return "IP deleted";
	}
	
	@GetMapping("/procurement/getipbyempid/{empid}")
	private List<Procurement> getIpByEmpIdprocurement(@PathVariable("empid") String empid)
	{
		return procurementrepo.getIPbyEmpID(empid);
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
				 occupyIP.setDepartment(device.getDepartment());
				 occupyIP.setDeviceType(device.getDeviceType());
				 occupyIP.setDeviceno(device.getDeviceno());
				 occupyIP.setIPStatus("Alloted");
				 extradevicesrepo.save(occupyIP);
				 return JSONObject.quote("IP Alloted");
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
		return extradevicesrepo.findAll();
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
		return "Device deleted";
	}
	
	@GetMapping("/extradevices/getipbydepartment/{department}")
	private List<ExtraDevices> getIpBydepartmentextradevices(@PathVariable("department") String department)
	{
		return extradevicesrepo.getdevicesIpbyDept(department);
	}
	
	
	

	
	
	
	
}
