package org.vbazurtob.HRRecruitApp.ui.pubweb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithPassword;
import org.vbazurtob.HRRecruitApp.model.JobType;
import org.vbazurtob.HRRecruitApp.model.repository.JobTypeRepository;

@Controller
public class HomeController {

	@Autowired
	private JobTypeRepository jobTypeRepo;
	
	@RequestMapping({"/","/home","/index"})	
	public String HomeController() {
		
//		List<JobType> listjobT = (List<JobType>) jobTypeRepo.findAll();
//		
//		for(JobType j :listjobT ) {
//			System.out.println(j.getDescription() + "\n");
//		}
		
		return "public/home";
	}

	
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