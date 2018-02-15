package org.vbazurtob.HRRecruitApp.ui.secured;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.vbazurtob.HRRecruitApp.model.Applicant;
import org.vbazurtob.HRRecruitApp.model.ApplicantAcademic;
import org.vbazurtob.HRRecruitApp.model.ApplicantAcademicPK;
import org.vbazurtob.HRRecruitApp.model.ApplicantProfileForm;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantAcademicsRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantAcademicsService;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantService;
import org.vbazurtob.HRRecruitApp.model.service.CountryService;
import org.vbazurtob.HRRecruitApp.model.service.TypeDegreeService;

@Controller
@RequestMapping("/cv")
public class PersonalProfileController {

	@Autowired
	private ApplicantRepository applicantRepository;
	
	@Autowired
	private ApplicantService applicantService;
	
	
	@Autowired
	private CountryService countryService;
	
	
	@Autowired
	private ApplicantAcademicsService applicantAcademicsService;
	
	@Autowired
	private ApplicantAcademicsRepository appAcademicsRepository;
	
	@Autowired
	private TypeDegreeService degreeTypeService;
	
	public PersonalProfileController() {
	}
	
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor( Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));   
	}
	

	@RequestMapping("/profile")
	public String showDetails(Model model) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		Applicant currentApplicant = applicantRepository.findOneByUsername(username);
	
		ApplicantProfileForm applicantProfileForm = new ApplicantProfileForm(currentApplicant);
		
		
		model.addAttribute("applicant", applicantProfileForm);
		model.addAttribute("countriesLst", countryService.getListCountries());
		
		return "secured/profile.html";
	}
	
	
	@PostMapping("/profile")
	public String saveProfile(
			
			@Valid @ModelAttribute("applicant") ApplicantProfileForm applicant,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model) {
		
		//System.out.println("Count errors: " + results.getErrorCount());
		
		if(results.hasErrors()) {
			
			
			//System.out.println("Count errors: " + results.getErrorCount());
			
			
			
//			for(FieldError eee : (List<FieldError>) results.getFieldErrors() ) {
//			
//				//System.out.println("---------------ERR" + eee);
//				
//				
//			}
//			
//			for(ObjectError eee : (List<ObjectError>) results.getAllErrors() ) {
//				
//				//System.out.println("---ERRores generales---" + eee);
//				
//				
//			}

			model.addAttribute("countriesLst", countryService.getListCountries());
			return "secured/profile.html";
		}
		
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
		}else {
			//results.addError(new ObjectError("passwordsNotEqual", "Passwords  don't match. No changes were made"));
			System.out.println("No changed");
			//annotation no error;
		}
		
		
		
		return "redirect:/cv/profile";
	}
	
	
	@RequestMapping("/academics")
	public String showAcademics(Model model /*, Pageable page*/) {
		
		PageRequest page = new PageRequest(0, 1);
		
		
		System.out.println("offset " + page.getOffset());
		
		Page<ApplicantAcademic> academics =  appAcademicsRepository.findAll(page);
		
		System.out.println("==================== " + academics.getNumberOfElements());
		
		ApplicantAcademicPK  newApplicantPK = new ApplicantAcademicPK();
		
		//TODO
		newApplicantPK.setApplicantId("abc");
		//newApplicantPK.setStarted(new Date());
		//newApplicantPK.setFinished(new Date());
		
		ApplicantAcademic newApplicant = new ApplicantAcademic();
		newApplicant.setId(newApplicantPK);
		
		model.addAttribute("academics", academics.stream().sorted().collect( Collectors.toList()) );
		
		model.addAttribute("academicsForm", newApplicantPK);
		
		model.addAttribute("degreeTypeLst", degreeTypeService.getListDegreeTypes());
		
		//newApplicant.getId().get
		
		return "secured/academics_form.html";
	}
	
	@PostMapping("/academics")
	public String showAcademics( @Valid @ModelAttribute("academicsForm") ApplicantAcademicPK academicsForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model ) {
		
		
		//System.out.println("Error count: " + results.getErrorCount());
		
		//System.out.println("Institution" + academicsForm.getInstitution() );
		
		if(results.hasErrors()) {
			
//			System.out.println("ERROR RRRRR");
//			
//			
//			
//			
//			for(FieldError eee : (List<FieldError>) results.getFieldErrors() ) {
//			
//				System.out.println("---------------ERR" + eee);
//				
//				
//			}
//			
//			for(ObjectError eee : (List<ObjectError>) results.getAllErrors() ) {
//				
//				System.out.println("---ERRores generales---" + eee);
//				
//				
//			}
			
			
			model.addAttribute("degreeTypeLst", degreeTypeService.getListDegreeTypes());
			return "secured/academics_form.html";
		}
		
		
		applicantAcademicsService.saveAcademicDetail(academicsForm);
		
		redirectAttrs.addFlashAttribute("saved",true);
		return "redirect:/cv/academics";
	}
	
	@RequestMapping("/workexp")
	public String showWorkExperience() {
		
		return "secured/workexp.html";
	}
	
	
}
