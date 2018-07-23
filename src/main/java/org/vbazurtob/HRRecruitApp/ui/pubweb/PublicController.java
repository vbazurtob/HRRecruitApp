package org.vbazurtob.HRRecruitApp.ui.pubweb;


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
import org.vbazurtob.HRRecruitApp.conf.ControllerEndpoints;
import org.vbazurtob.HRRecruitApp.model.NewApplicantForm;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantService;
import static org.vbazurtob.HRRecruitApp.conf.ControllerEndpoints.*;

/**
 * @author Voltaire Bazurto Blacio
 * 
 * All rights reserved
 *
 * Controller for all the public pages
 *
 */
@Controller
@RequestMapping(PUBLIC_CNTROLLER)
public class PublicController implements ControllerEndpoints{

	@Autowired
	private ApplicantService applicantService;

	@RequestMapping( { HOME_PAGE })	
	public String HomepageController(Model model) {
		
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		model.addAttribute("applicantLoginUrl", controllerMapping + APPLICANT_LOGIN_PAGE);
		model.addAttribute("hrMemberLoginUrl" , controllerMapping + HR_MEMBER_LOGIN_PAGE);
		return "public/home";
	}

	
	@RequestMapping(APPLICANT_LOGIN_PAGE)	
	public String LoginController(Model model) {
		
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		model.addAttribute("applicantSignupUrl", controllerMapping + CREATE_NEW_APPLICANT_PAGE );
		model.addAttribute("postFormUrl",  APPLICANT_CV_CNTROLLER + APPLICANT_LOGIN_PAGE ); // APPLICANT_LOGIN_PAGE
		
		return "public/login";
	}
	
	
	@RequestMapping( CREATE_NEW_APPLICANT_PAGE ) //Create new applicant user
	public String applicantSignUp(
			Model model
			) {
		
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		NewApplicantForm newApplicant = new NewApplicantForm();
		 
		model.addAttribute("baseUrl", controllerMapping + CREATE_NEW_APPLICANT_PAGE);
		model.addAttribute("postFormUrl", controllerMapping + POST_CREATE_NEW_APPLICANT_PAGE);
		model.addAttribute("loginUrl", controllerMapping + APPLICANT_LOGIN_PAGE);
		
		model.addAttribute("newApplicantObj", newApplicant);
		return "public/applicant_signup.html";
	}
	
	@PostMapping( POST_CREATE_NEW_APPLICANT_PAGE )
	public String createNewApplicant(
			@Valid @ModelAttribute("newApplicantObj") NewApplicantForm newApplicantForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model
			) {
		
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		
		// Model to redirect to login page
		model.addAttribute("loginUrl", controllerMapping + APPLICANT_LOGIN_PAGE);
		
		
		if( applicantService.usernameExists(newApplicantForm.getUsername()) ) {
			results.addError(new ObjectError("UsernameAlreadyExists", "The username already exists! Choose another one"));
		}
		
		if(results.hasErrors()) {
			
			model.addAttribute("baseUrl", controllerMapping + CREATE_NEW_APPLICANT_PAGE);
			model.addAttribute("postFormUrl", controllerMapping + POST_CREATE_NEW_APPLICANT_PAGE);
			model.addAttribute("newApplicantObj", newApplicantForm);
			
			return "public/applicant_signup.html";
		}

		applicantService.createNewApplicantInDB(newApplicantForm.getUsername(), newApplicantForm.getPassword(), newApplicantForm.getEmail() );
		redirectAttrs.addFlashAttribute("saved",true);
		
		return "redirect:" + controllerMapping + CREATE_NEW_APPLICANT_PAGE;
		
	}
	
	
	@RequestMapping( HR_MEMBER_LOGIN_PAGE )	
	public String LoginHrMemberController( Model model ) {
		
		
		model.addAttribute("loginHrUrl", JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_LOGIN_PAGE );
	
		return "public/admin_login";
	}
	

	@RequestMapping( NOT_AUTHORIZED_PAGE )	
	public String NotAuthorizedToViewController( Model model ) {
		
		return "403";
	}
	
	
}