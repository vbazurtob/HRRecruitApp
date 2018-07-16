package org.vbazurtob.HRRecruitApp.ui.pubweb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vbazurtob.HRRecruitApp.model.repository.JobTypeRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	

	
	@RequestMapping("/login")	
	public String LoginController() {
		
		
		return "public/login";
	}
	
	
	@RequestMapping("/summary")	
	public String summaryController() {
		
		
		return "secured/summary";
	}
	
	
//	@RequestMapping("/logout")
//	public String logoutController() {
//		return "redirect:public/login?logout";
//	}
	
	
	
	
	

}