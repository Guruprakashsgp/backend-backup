package com.example.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Repo.AdministrationRepo;
import com.example.Repo.ExtraDevicesRepo;
import com.example.Repo.ManufacturingRepo;
import com.example.Repo.ProcurementRepo;
import com.example.Repo.RequestRepo;
import com.example.Repo.SalesRepo;
import com.example.Repo.UsersRepo;
import com.example.model.Requests;
import com.example.model.Users;

@RestController
@Service
@RequestMapping("/user/")
@CrossOrigin(origins="*")
public class UserController {
	
	
	@Autowired
	private JavaMailSender mailsender;
	
	public String name="";
	public String empid="";
	public String department="";
	
	
	@Autowired
	private UsersRepo usersrepo;
	@Autowired
	private RequestRepo requestrepo;
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

	 int flag =0;
	
	@GetMapping("login/{username}/{password}")
	public String userlogin(@PathVariable("username") String username1, @PathVariable("password")String password1) throws SQLException
	{
		Users user=usersrepo.findByUsernameAndPassword(username1,password1);
		
		if(user!= null)
		{
			name=user.getName();
			empid=user.getEmpid();
			department=user.getDepartment();
//			if(administrationrepo.getIPbyEmpID(empid).size()>0)
//			{
//				department="administration";
//			}
//			else if(salesrepo.getIPbyEmpID(empid).size()>0)
//			{
//				department="sales";
//			}
//			else if(procurementrepo.getIPbyEmpID(empid).size()>0)
//			{
//				department="procurement";
//			}
//			else 
//			{
//				department="manufacturing";
//			}
			return JSONObject.quote("user present"+empid+name+'/'+department);
		}
		else
		{
			
			return JSONObject.quote("no user");
		}
	}
	 
	
	 @GetMapping("otp/{email}")
	 public String otpverification(@PathVariable("email") String toEmail) throws SQLException
	 {
		 int otp=0;
		 Users user=usersrepo.findByEmail(toEmail);
		 if(user!=null)
		 {
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
		     otp=z;
		 }
		 return JSONObject.quote(String.valueOf(otp));
	 }
	 
	 @PostMapping("signup/{name}/{username}/{password}/{email}/{empid}/{department}")
	 public String candidateaddition(@PathVariable("name") String name,@PathVariable("username") String username,@PathVariable("password") String password,@PathVariable("email") String email,@PathVariable("empid") String empid,@PathVariable("department") String department) throws SQLException {
		 	Users usercandidate1=usersrepo.findByEmailAndUsername(email,username);
		 	Users usercandidate2=usersrepo.findByEmail(email);
		 	Users usercandidate3=usersrepo.findByUsername(username);
			
			if(usercandidate1!=null)
			{
				return JSONObject.quote("email and username already exists");
			}
			else if(usercandidate2!=null)
			{
				return JSONObject.quote("email");
			}
			else if(usercandidate3!=null)
			{
				return JSONObject.quote("username");
			}
			else
			{
				Users usercandidate= new Users();
				usercandidate.setName(name);
				usercandidate.setEmail(email);
				usercandidate.setUsername(username);
				usercandidate.setEmpid(empid);
				usercandidate.setPassword(password);
				usercandidate.setDepartment(department);
				usersrepo.save(usercandidate);
				
				 SimpleMailMessage message=new SimpleMailMessage();
				 message.setTo(email);
				 message.setText("Your Username and Password for further login purpose will be: "+"\nUsername :"+ username +"\nPasswod:"+  password  );
				 message.setSubject("Login Credentials");
				 message.setFrom("pgparkinglot@gmail.com");
				 mailsender.send(message);
				
				return JSONObject.quote("Registered");
			}
	 }
	 
	 
	 
		@PutMapping("updateP/{password}/{email}")
		public String updatepass(@PathVariable("password") String password, @PathVariable("email")String email) throws SQLException
		{
			
			Users user=usersrepo.findByEmail(email);
			user.setPassword(password);
			usersrepo.save(user);
		return JSONObject.quote("Updated");
		}
		
		
		////////////////////////////////////requests
		@PostMapping("requests/save")
		private String saveRequests(@RequestBody Requests request)
		{
			
			if(requestrepo.getRequesteduser(request.getEmpID(), request.getDeviceType())!=null)
			{
				return JSONObject.quote("Already");
			}
			else
			{
				request.setRequestStatus("Requested");	
				requestrepo.save(request);
				return JSONObject.quote("request saved");
			}
//			String empid=request.getEmpID();
//			Users user=usersrepo.getByEmpid(empid);
//			request.setName(user.getName());
//			System.out.println(request.getEmp());
			
		}
		
		
		@GetMapping("requests/isaccepted/{empID}")
		private List<Requests> isaccepted(@PathVariable("empID") String empID)
		{
			return requestrepo.isaccepted(empID);
		}
		
		@GetMapping("requests/allrequests")
		private List<Requests> requests()
		{
			return requestrepo.findAll();
		}
		
		@DeleteMapping("requests/delete/{empID}")
		private String afteruserrequestcompleted(@PathVariable("empID") String empID)
		{
			requestrepo.deletingrequestaftercompletion(empID);
			return JSONObject.quote("User Deleted");
		}
 
	 



	

}
