package org.vbazurtob.HRRecruitApp.ui.secured;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.vbazurtob.HRRecruitApp.model.Applicant;
import org.vbazurtob.HRRecruitApp.model.ApplicantProfileForm;
import org.vbazurtob.HRRecruitApp.model.Country;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantService;
import org.vbazurtob.HRRecruitApp.model.service.CountryService;

@Controller
@RequestMapping("/cv")
public class PersonalProfileController {

	@Autowired
	private ApplicantRepository applicantRepository;
	
	@Autowired
	private ApplicantService applicantService;
	
	
	@Autowired
	private CountryService countryService;
	
	
	public PersonalProfileController() {
	}

	@RequestMapping("/profile")
	public String showDetails(Model model) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		Applicant currentApplicant = applicantRepository.findOneByUsername(username);
	
		model.addAttribute("applicant", currentApplicant);
		model.addAttribute("countriesLst", countryService.getListCountries());
		
		return "secured/profile.html";
	}
	
	
	@RequestMapping(value = "/save-profile", method = RequestMethod.POST  )
	public String saveProfile(
			@ModelAttribute ApplicantProfileForm applicant,
			BindingResult results, RedirectAttributes redirectAttrs) {
				
		
//		String repassword = Arrays.toString( request.getParameterNames() );
		//String repassword = applicant.getPasswordConfirmation();
		
		
		//System.out.println(applicant.getAddress1());
		
		//System.out.println(applicant.getPassword() + " ========== " + repassword);
		
		//String flags = "saved=true";
		redirectAttrs.addFlashAttribute("saved", true);
		applicantService.updateApplicantProfile(applicant);
		boolean isPwdChanged = applicantService.updatePassword(applicant.getUsername(), applicant.getPassword(), applicant.getPasswordConfirmation());
		if( isPwdChanged ) {
			//flags+="&pwdchanged=true";
			redirectAttrs.addFlashAttribute("pwdchanged",true);
		}
		
		
		
		return "redirect:/cv/profile";
	}
	
	
	@RequestMapping("/academics")
	public String showAcademics() {
		
		return "secured/academics_form.html";
	}
	
	@RequestMapping("/workexp")
	public String showWorkExperience() {
		
		return "secured/workexp.html";
	}
	
	
}
