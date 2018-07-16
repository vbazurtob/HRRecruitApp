package org.vbazurtob.HRRecruitApp.ui.pubweb;


import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.vbazurtob.HRRecruitApp.model.ApplicantChangePasswordForm;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithPassword;
import org.vbazurtob.HRRecruitApp.model.NewApplicantForm;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;
import org.vbazurtob.HRRecruitApp.model.repository.JobTypeRepository;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantService;

@Controller
public class HomeController {

	@Autowired
	private JobTypeRepository jobTypeRepo;
	
	@Autowired
	private ApplicantRepository applicantRepository;
	

	@Autowired
	private ApplicantService applicantService;

	
	private final static String LOGIN_APPLICANT = "/login";
	
	private final static String CREATE_NEW_APPLICANT = "/signup";
	
	private final static String POST_CREATE_NEW_APPLICANT = "/register-new-applicant";
	
	@RequestMapping({"/","/home","/index"})	
	public String HomepageController() {		
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
	
	
	@RequestMapping(CREATE_NEW_APPLICANT) //Create new applicant user
	public String applicantSignUp(
			Model model
			) {
		
		NewApplicantForm newApplicant = new NewApplicantForm();
		 
		model.addAttribute("baseUrl", CREATE_NEW_APPLICANT);
		model.addAttribute("postFormUrl", POST_CREATE_NEW_APPLICANT);
		model.addAttribute("newApplicantObj", newApplicant);
		model.addAttribute("loginUrl", LOGIN_APPLICANT);
		
		return "public/applicant_signup.html";
	}
	
	@PostMapping(POST_CREATE_NEW_APPLICANT)
	public String createNewApplicant(
			@Valid @ModelAttribute("newApplicantObj") NewApplicantForm newApplicantForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model
			) {
		
		// Model to redirect to login page
		model.addAttribute("loginUrl", LOGIN_APPLICANT);
		
		
		if( applicantService.usernameExists(newApplicantForm.getUsername()) ) {
			results.addError(new ObjectError("UsernameAlreadyExists", "The username already exists! Choose another one"));
		}
		
		if(results.hasErrors()) {
			
			model.addAttribute("baseUrl", CREATE_NEW_APPLICANT);
			model.addAttribute("postFormUrl", POST_CREATE_NEW_APPLICANT);
			model.addAttribute("newApplicantObj", newApplicantForm);
			
			return "public/applicant_signup.html";
		}
		
		
		applicantService.createNewApplicantInDB(newApplicantForm.getUsername(), newApplicantForm.getPassword(), newApplicantForm.getEmail() );
		
		
		redirectAttrs.addFlashAttribute("saved",true);
		
		
		
		
		return "redirect:" + CREATE_NEW_APPLICANT;
		
	}
	
	
	
//	@RequestMapping("/logout")
//	public String logoutController() {
//		return "redirect:public/login?logout";
//	}
	
	
	
	
	

}