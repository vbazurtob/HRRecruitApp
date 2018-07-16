package org.vbazurtob.HRRecruitApp.ui.secured;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.vbazurtob.HRRecruitApp.lib.common.DeleteResponse;
import org.vbazurtob.HRRecruitApp.lib.common.RecordNotFoundException;
import org.vbazurtob.HRRecruitApp.lib.common.Utils;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithPassword;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithoutPassword;
import org.vbazurtob.HRRecruitApp.model.ApplicantAcademic;
import org.vbazurtob.HRRecruitApp.model.ApplicantChangePasswordForm;
import org.vbazurtob.HRRecruitApp.model.ApplicantBaseClass;
import org.vbazurtob.HRRecruitApp.model.ApplicantSkill;
import org.vbazurtob.HRRecruitApp.model.ApplicantWorkExperience;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantAcademicsRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantSkillRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantWorkExpRepository;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantAcademicsService;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantService;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantSkillService;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantWorkExpService;
import org.vbazurtob.HRRecruitApp.model.service.CountryService;
import org.vbazurtob.HRRecruitApp.model.service.ProficiencyService;
import org.vbazurtob.HRRecruitApp.model.service.TypeDegreeService;

import ch.qos.logback.classic.pattern.Util;



@Controller
@RequestMapping("/cv")
public class PersonalProfileController {
	
	private final static int RECORDS_PER_PAGE = 10;
	
	private final static String ACADEMICS_BASE_URL = "/academics/";
	private final static String WORKEXP_BASE_URL = "/workexp/";
	private final static String SKILLS_BASE_URL = "/skills/";

	private final static String PROFILE_BASE_URL = "/profile/";
	
	private final static String VIEW_CV = "/view/";

	@Autowired
	private ApplicantRepository applicantRepository;
	
	@Autowired
	private ApplicantService applicantService;
	
	
	@Autowired
	private ApplicantWorkExpService applicantWorkExpService;
	
	@Autowired
	private ApplicantWorkExpRepository applicantWorkExpRepository;
	
	@Autowired
	private ApplicantSkillService applicantSkillService;
	
	@Autowired
	private ApplicantSkillRepository applicantSkillRepository;
	
	@Autowired
	private ApplicantAcademicsRepository applicantAcademicRepository;
	
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private ProficiencyService proficiencyService;
	
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
	

	@RequestMapping(PROFILE_BASE_URL)
	public String showDetails(Model model) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		
		//TODO
		username = "abc";
		
		ApplicantWithPassword currentApplicant = applicantRepository.findOneByUsername(username);		
		
//		System.out.println(currentApplicant);;
		
		ApplicantChangePasswordForm newChangePasswordObj = new ApplicantChangePasswordForm();
		newChangePasswordObj.setUsernameChangePwdForm(username);
		
		
		model.addAttribute("userProfileOptionSelected",true);
		model.addAttribute("applicant", currentApplicant);
		model.addAttribute("countriesLst", countryService.getListCountries());
		model.addAttribute("baseUrl", controllerMapping + PROFILE_BASE_URL);
		model.addAttribute("changePwdForm", newChangePasswordObj );
		
		return "secured/profile.html";
	}
	
	
	@PostMapping(PROFILE_BASE_URL)
	public String saveProfile(
			@Valid @ModelAttribute("applicant") ApplicantWithoutPassword applicant,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model ){
		
		
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		
		
		// Change pwd form
		ApplicantChangePasswordForm newChangePasswordObj = new ApplicantChangePasswordForm();		
		newChangePasswordObj.setUsernameChangePwdForm(applicant.getUsername());
		
		
		//DEBUG
//		Utils.printFormErrors(results);
		
		
		if(results.hasErrors()) {
			
			model.addAttribute("userProfileOptionSelected",true);
			model.addAttribute("countriesLst", countryService.getListCountries());
			model.addAttribute("baseUrl", controllerMapping + PROFILE_BASE_URL);
			model.addAttribute("changePwdForm", newChangePasswordObj );
			model.addAttribute("applicant", applicant);
			
			return "secured/profile.html";
		}
		

		redirectAttrs.addFlashAttribute("saved", true);
		applicantService.updateApplicantProfile(applicant);
		
		
		return "redirect:" + controllerMapping + PROFILE_BASE_URL;
	}
	
	
	@PostMapping(PROFILE_BASE_URL + "update-password")
	public String updatePassword(
			
			@Valid @ModelAttribute("changePwdForm") ApplicantChangePasswordForm applicantChangePwdForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model) {
		
		//Check current password		
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];

		
		// Current password validation doesn't match
		if( !applicantService.currentApplicantPasswordMatch(applicantChangePwdForm.getUsernameChangePwdForm(), applicantChangePwdForm.getCurrentPassword() ) ) {
			ObjectError errorCurrentPasswordIsNotValid = new ObjectError("CurrentPasswordIsNotValid", "Current password for applicant " + applicantChangePwdForm.getUsernameChangePwdForm() + " doesn't match with records");
			results.addError(errorCurrentPasswordIsNotValid);
		}
		
		
//		DEBUG
//		Utils.printFormErrors(results);
		
		if(results.hasErrors()) {
			
			
			//In case of error set the data for the applicant profile data
			ApplicantWithPassword currentApplicant = applicantRepository.findOneByUsername( applicantChangePwdForm.getUsernameChangePwdForm() );
			
			
			
			model.addAttribute("userProfileOptionSelected",true);
			model.addAttribute("countriesLst", countryService.getListCountries());
			model.addAttribute("baseUrl", controllerMapping + PROFILE_BASE_URL);
			model.addAttribute("applicant", currentApplicant);
			
			return "secured/profile.html";
		}
		

		
		boolean isPwdChanged = applicantService.updatePassword(applicantChangePwdForm.getUsernameChangePwdForm(), applicantChangePwdForm.getPassword(), applicantChangePwdForm.getPasswordConfirmation());
		if( isPwdChanged ) {
			redirectAttrs.addFlashAttribute("pwdchanged",true);
		}else {	
			redirectAttrs.addFlashAttribute("pwdchangedError",true);

		}
		
		
		return "redirect:" + controllerMapping + PROFILE_BASE_URL;
		
		
	}
	
	

	@RequestMapping(value = {  ACADEMICS_BASE_URL , ACADEMICS_BASE_URL + "{page}" } )
	public String showAcademics(
			Model model,
			@PathVariable Optional<Integer> page
			) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		// New blank record for adding new record
		ApplicantAcademic newAppAcademic = new ApplicantAcademic();
		newAppAcademic.setInProgress("N");
		
		// Set Applicant nested object for proper validation, we need to pass in the post at least the applicant username	
		ApplicantWithPassword newApplicantQuery = new ApplicantWithPassword();
		newApplicantQuery = applicantRepository.findOneByUsername(username);
		newAppAcademic.setApplicant(newApplicantQuery);
		
		// Pagination for listing
		Page<ApplicantAcademic> academicsPageObj = applicantAcademicsService.getPaginatedRecords(username, page, RECORDS_PER_PAGE);
		long previousPageNum = applicantAcademicsService.getPaginationNumbers(academicsPageObj)[0];
		long nextPageNum = applicantAcademicsService.getPaginationNumbers(academicsPageObj)[1];

		
		// View attributes
		model.addAttribute("baseUrl", controllerMapping + ACADEMICS_BASE_URL);
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", academicsPageObj );
		model.addAttribute("academics", academicsPageObj.getContent());
		model.addAttribute("academicsForm", newAppAcademic);
		model.addAttribute("degreeTypeLst", degreeTypeService.getListDegreeTypes());
		model.addAttribute("academicsOptionSelected",true);
				
		return "secured/academics_form.html";
	}
	
	@PostMapping(value= ACADEMICS_BASE_URL)
	public String showAcademics( @Valid @ModelAttribute("academicsForm") ApplicantAcademic academicsForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model ) {
		
//		Check if record already exists (DB validation)
		if ( applicantAcademicsService.recordExists(academicsForm.getApplicant().getUsername(), 
				academicsForm.getStarted(), 
				academicsForm.getFinished(), 
				academicsForm.getDegreeName(), 
				academicsForm.getDegreeType(),
				academicsForm.getInstitution())  ){
			ObjectError errorDuplicateRecord = new ObjectError("AcademicsRecordDuplicate", "Records already exists in db!");
			results.addError(errorDuplicateRecord);
		}
		
		//Get Controller Name for url construction
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		// Set Applicant nested object for proper validation	
		ApplicantWithPassword newApplicantQuery = new ApplicantWithPassword();
		newApplicantQuery = applicantRepository.findOneByUsername(username);
		academicsForm.setApplicant(newApplicantQuery);
		
		// Pagination for listing
		Page<ApplicantAcademic> academicsPageObj = applicantAcademicsService.getPaginatedRecords(username, Optional.of( Integer.valueOf(0) )
				, RECORDS_PER_PAGE);
		long previousPageNum = applicantAcademicsService.getPaginationNumbers(academicsPageObj)[0];
		long nextPageNum = applicantAcademicsService.getPaginationNumbers(academicsPageObj)[1];
		
		// View attributes
		model.addAttribute("baseUrl", controllerMapping + ACADEMICS_BASE_URL);
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", academicsPageObj );
		model.addAttribute("academics", academicsPageObj.getContent() );	
		model.addAttribute("academicsForm", academicsForm); // Pass the updated academicsForm to the UI
		model.addAttribute("academicsOptionSelected",true);
		
		
		if(results.hasErrors()) { // Reload the form with errors			
			model.addAttribute("degreeTypeLst", degreeTypeService.getListDegreeTypes());
			return "secured/academics_form.html";
		}
		
		// Save the form data
		applicantAcademicsService.saveAcademicDetail(academicsForm, username);
		redirectAttrs.addFlashAttribute("saved",true);
		return "redirect:" + controllerMapping + ACADEMICS_BASE_URL;
	}
	
	
	@RequestMapping( ACADEMICS_BASE_URL + "edit/{id}" )
	public String editAcademics(
			Model model,
			@PathVariable Long id
			) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		
		Optional<ApplicantAcademic> academicsOpt = appAcademicsRepository.findById(id);
		
		if(academicsOpt.isPresent()) {
			ApplicantAcademic appAcademicsFormObj = academicsOpt.get();
			
			// View attributes
			model.addAttribute("baseUrl", controllerMapping + ACADEMICS_BASE_URL + "edit/" + id);
			model.addAttribute("academicsForm", appAcademicsFormObj);
			model.addAttribute("degreeTypeLst", degreeTypeService.getListDegreeTypes());
			model.addAttribute("academicsOptionSelected",true);
					
			return "secured/academics_form_edit.html";
			
			
		} else {
			throw new RecordNotFoundException();
		}
	
	}
	
	
	@PostMapping( ACADEMICS_BASE_URL + "edit/{id}" )
	public String editAcademics( @Valid @ModelAttribute("academicsForm") ApplicantAcademic academicsForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model,
			@PathVariable Long id) {
		
		System.out.println("EDIT ");;
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		
		if(results.hasErrors()) { // Reload the form with errors			
		
			// View attributes
			model.addAttribute("baseUrl", controllerMapping + ACADEMICS_BASE_URL + "edit/" + id);
			model.addAttribute("academicsForm", academicsForm);
			model.addAttribute("degreeTypeLst", degreeTypeService.getListDegreeTypes());
			model.addAttribute("academicsOptionSelected",true);
						
			return "secured/academics_form_edit.html";
			
		}
		
					
		// Save the form data
		applicantAcademicsService.saveAcademicDetail(academicsForm, username);
		redirectAttrs.addFlashAttribute("updated",true);
		return "redirect:/cv/academics/";
	
	}
	
	@DeleteMapping( value= ACADEMICS_BASE_URL + "delete/{id}", produces= { MediaType.APPLICATION_JSON_VALUE } )
	@ResponseBody
	public DeleteResponse deleteAcademics( @PathVariable Long id ) {
		
		//System.out.println( " DELETE " + id);
		String response="OK";
		try {
			appAcademicsRepository.deleteById(id);
		}catch(Exception e) {
			e.printStackTrace();
			response="ERROR";
		}
		
		return new DeleteResponse(response);
	}
	

	@RequestMapping(value = {  WORKEXP_BASE_URL , WORKEXP_BASE_URL + "{page}" } )
	public String showWorkExperience(
		Model model,
		@PathVariable Optional<Integer> page
	) {
	
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		// New blank record for adding new record
		ApplicantWorkExperience newAppWorkexp = new ApplicantWorkExperience();
		
		// Set Applicant nested object for proper validation, we need to pass in the post at least the applicant username	
		ApplicantWithPassword newApplicantQuery = new ApplicantWithPassword();
		newApplicantQuery = applicantRepository.findOneByUsername(username);
		newAppWorkexp.setApplicant(newApplicantQuery);
		
		// Pagination for listing
		Page<ApplicantWorkExperience> workExpPageObj = applicantWorkExpService.getPaginatedRecords(username, page, RECORDS_PER_PAGE);
		long previousPageNum = applicantWorkExpService.getPaginationNumbers(workExpPageObj)[0];
		long nextPageNum = applicantWorkExpService.getPaginationNumbers(workExpPageObj)[1];
	
		
		// View attributes
		model.addAttribute("baseUrl", controllerMapping + WORKEXP_BASE_URL);
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", workExpPageObj );
		model.addAttribute("workexp", workExpPageObj.getContent());
		model.addAttribute("workexpForm", newAppWorkexp);
		model.addAttribute("degreeTypeLst", degreeTypeService.getListDegreeTypes());
		model.addAttribute("workExperienceOptionSelected",true);
				
			
		return "secured/workexp.html";
	}
	
	
	@PostMapping(value = WORKEXP_BASE_URL  )
	public String editWorkExp( @Valid @ModelAttribute("workexpForm") ApplicantWorkExperience workexpForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model) {
		
		
		
		
		//Check if record already exists (DB validation)
		if ( applicantWorkExpService.recordExists(
				workexpForm.getApplicant().getUsername(), 
				workexpForm.getStarted(), 
				workexpForm.getFinished(), 
				workexpForm.getPosition(),
				workexpForm.getInstitution())  ){
			
			ObjectError errorDuplicateRecord = new ObjectError("WorkExpRecordDuplicate", "Records already exists in db!");
			results.addError(errorDuplicateRecord);
		}
		
		//Get Controller Name for url construction
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		// Set Applicant nested object for proper validation	
		ApplicantWithPassword newApplicantQuery = new ApplicantWithPassword();
		newApplicantQuery = applicantRepository.findOneByUsername(username);
		workexpForm.setApplicant(newApplicantQuery);
		
		// Pagination for listing
		Page<ApplicantWorkExperience> workexpPageObj = applicantWorkExpService.getPaginatedRecords(username, Optional.of( Integer.valueOf(0) )
				, RECORDS_PER_PAGE);
		long previousPageNum = applicantWorkExpService.getPaginationNumbers(workexpPageObj)[0];
		long nextPageNum = applicantWorkExpService.getPaginationNumbers(workexpPageObj)[1];
		
		// View attributes
		model.addAttribute("baseUrl", controllerMapping + WORKEXP_BASE_URL);
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", workexpPageObj );
		model.addAttribute("workexp", workexpPageObj.getContent() );	
		model.addAttribute("workexpForm", workexpForm); // Pass the updated academicsForm to the UI
		model.addAttribute("workExperienceOptionSelected",true);

		// DEBUG form Validations
//		Utils.printFormErrors(results);
		
		
		if(results.hasErrors()) { // Reload the form with errors			
			model.addAttribute("degreeTypeLst", degreeTypeService.getListDegreeTypes());
			return "secured/workexp.html";
		}
		
		// Save the form data
		applicantWorkExpService.saveWorkExpDetail(workexpForm, username);
		redirectAttrs.addFlashAttribute("saved",true);
		return "redirect:" + controllerMapping + WORKEXP_BASE_URL;
		
		
		
	
	}
	
	
	@RequestMapping( WORKEXP_BASE_URL + "edit/{id}" )
	public String editWorkExp(
			Model model,
			@PathVariable Long id
			) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		
		Optional<ApplicantWorkExperience> workexpOpt = applicantWorkExpRepository.findById(id);
		
		if(workexpOpt.isPresent()) {
			ApplicantWorkExperience appWorkExpFormObj = workexpOpt.get();
			
			// View attributes
			model.addAttribute("baseUrl", controllerMapping + WORKEXP_BASE_URL + "edit/" + id);
			model.addAttribute("workexpForm", appWorkExpFormObj);
			model.addAttribute("degreeTypeLst", degreeTypeService.getListDegreeTypes());
			model.addAttribute("workExperienceOptionSelected",true);
					
			return "secured/workexp_form_edit.html";
			
			
		} else {
			throw new RecordNotFoundException();
		}
	
	}
	
	
	@PostMapping( WORKEXP_BASE_URL + "edit/{id}" )
	public String editWorkExp( @Valid @ModelAttribute("workexpForm") ApplicantWorkExperience workexpForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model,
			@PathVariable Long id) {
		
				
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
				
		//TODO
		username = "abc";
							
		
		
		if(results.hasErrors()) { // Reload the form with errors			
			
			// View attributes
			model.addAttribute("baseUrl", controllerMapping + WORKEXP_BASE_URL + "edit/" + id);
			model.addAttribute("workexpForm", workexpForm);
			model.addAttribute("degreeTypeLst", degreeTypeService.getListDegreeTypes());
			model.addAttribute("workExperienceOptionSelected",true);
					
			return "secured/workexp_form_edit.html";
			
		}
		
					
		// Save the form data
		applicantWorkExpService.saveWorkExpDetail(workexpForm, username);
		redirectAttrs.addFlashAttribute("updated",true);
		return "redirect:" + controllerMapping + WORKEXP_BASE_URL;
		
		
		
	}
	
	
	@DeleteMapping( value= WORKEXP_BASE_URL + "delete/{id}", produces= { MediaType.APPLICATION_JSON_VALUE } )
	@ResponseBody
	public DeleteResponse deleteWorkExp( @PathVariable Long id ) {
		
		//System.out.println( " DELETE " + id);
		String response="OK";
		try {
			applicantWorkExpRepository.deleteById(id);
		}catch(Exception e) {
			e.printStackTrace();
			response="ERROR";
		}
		
		return new DeleteResponse(response);
	}
	
	
	
	@RequestMapping(value = {  SKILLS_BASE_URL , SKILLS_BASE_URL + "{page}" } )
	public String showSkills(
			Model model,
			@PathVariable Optional<Integer> page
			) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		// New blank record for adding new record
		ApplicantSkill newAppSkill = new ApplicantSkill();
		
		// Set Applicant nested object for proper validation, we need to pass in the post at least the applicant username	
		ApplicantWithPassword newApplicantQuery = new ApplicantWithPassword();
		newApplicantQuery = applicantRepository.findOneByUsername(username);
		newAppSkill.setApplicant(newApplicantQuery);
		
		// Pagination for listing
		Page<ApplicantSkill> skillPageObj = applicantSkillService.getPaginatedRecords(username, page, RECORDS_PER_PAGE);
		long previousPageNum = applicantSkillService.getPaginationNumbers(skillPageObj)[0];
		long nextPageNum = applicantSkillService.getPaginationNumbers(skillPageObj)[1];

		
		// View attributes
		model.addAttribute("baseUrl", controllerMapping + SKILLS_BASE_URL);
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", skillPageObj );
		model.addAttribute("skills", skillPageObj.getContent());
		model.addAttribute("skillsForm", newAppSkill);
		model.addAttribute("skillsOptionSelected",true);
		model.addAttribute("proficiencyLst", proficiencyService.getListProficiencies());
		
		model.addAttribute("proficiencyService", proficiencyService);
				
		return "secured/skills.html";
	}
	
	
	@PostMapping(value= SKILLS_BASE_URL)
	public String showSkills( @Valid @ModelAttribute("skillsForm") ApplicantSkill skillForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model ) {
		
		//Check if record already exists (DB validation)
		if ( applicantSkillService.recordExists(skillForm.getApplicant().getUsername(), 
				skillForm.getName(), 
				skillForm.getProficiency())  ){
			ObjectError errorDuplicateRecord = new ObjectError("SkillsRecordDuplicate", "Records already exists in db!");
			results.addError(errorDuplicateRecord);
		}
		
		//Get Controller Name for url construction
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		// Set Applicant nested object for proper validation	
		ApplicantWithPassword newApplicantQuery = new ApplicantWithPassword();
		newApplicantQuery = applicantRepository.findOneByUsername(username);
		skillForm.setApplicant(newApplicantQuery);
		
		// Pagination for listing
		Page<ApplicantSkill> skillPageObj = applicantSkillService.getPaginatedRecords(username, Optional.of( Integer.valueOf(0) )
				, RECORDS_PER_PAGE);
		long previousPageNum = applicantSkillService.getPaginationNumbers(skillPageObj)[0];
		long nextPageNum = applicantSkillService.getPaginationNumbers(skillPageObj)[1];
		
		// View attributes
		model.addAttribute("baseUrl", controllerMapping + SKILLS_BASE_URL);
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", skillPageObj );
		model.addAttribute("skills", skillPageObj.getContent() );	
		model.addAttribute("skillsForm", skillForm); // Pass the updated academicsForm to the UI
		model.addAttribute("skillsOptionSelected",true);
		model.addAttribute("proficiencyLst", proficiencyService.getListProficiencies());
		model.addAttribute("proficiencyService", proficiencyService);
		// DEBUG form Validations
//		Utils.printFormErrors(results);
		
		
		if(results.hasErrors()) { // Reload the form with errors			
			return "secured/skills.html";
		}
		
		// Save the form data
		applicantSkillService.saveSkill(skillForm, username);
		redirectAttrs.addFlashAttribute("saved",true);
		return "redirect:" + controllerMapping + SKILLS_BASE_URL;
	}
	
	
	@RequestMapping( SKILLS_BASE_URL + "edit/{id}" )
	public String editSkills(
			Model model,
			@PathVariable Long id
			) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		
		Optional<ApplicantSkill> applicantOpt = applicantSkillRepository.findById(id);
		
		if(applicantOpt.isPresent()) {
			ApplicantSkill appSkillsFormObj = applicantOpt.get();
			
			
			// View attributes
			model.addAttribute("baseUrl", controllerMapping + SKILLS_BASE_URL + "edit/" + id);
			model.addAttribute("skillsForm",  appSkillsFormObj);
			model.addAttribute("skillsOptionSelected",true);
			model.addAttribute("proficiencyLst", proficiencyService.getListProficiencies());
			
			model.addAttribute("proficiencyService", proficiencyService);
					
			return "secured/skills_form_edit.html";
			
			
		} else {
			throw new RecordNotFoundException();
		}
	
	}
	
	
	
	
	@PostMapping( SKILLS_BASE_URL + "edit/{id}" )
	public String editSkills( @Valid @ModelAttribute("skillsForm") ApplicantSkill skillsForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model,
			@PathVariable Long id) {
		
				
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
				
		//TODO
		username = "abc";
							
		
		
		if(results.hasErrors()) { // Reload the form with errors			
			
			
			
			model.addAttribute("baseUrl", controllerMapping + SKILLS_BASE_URL + "edit/" + id);
			model.addAttribute("skillsForm",  skillsForm);
			model.addAttribute("skillsOptionSelected",true);
			model.addAttribute("proficiencyLst", proficiencyService.getListProficiencies());
			model.addAttribute("proficiencyService", proficiencyService);
			
					
			return "secured/skills_form_edit.html";
			
		}
		
					
		// Save the form data
		applicantSkillService.saveSkill(skillsForm, username);
		redirectAttrs.addFlashAttribute("updated",true);
		return "redirect:" + controllerMapping + SKILLS_BASE_URL;
		
		
		
	}
	
	
	@DeleteMapping( value= SKILLS_BASE_URL + "delete/{id}", produces= { MediaType.APPLICATION_JSON_VALUE } )
	@ResponseBody
	public DeleteResponse deleteSkills( @PathVariable Long id ) {

		String response="OK";
		try {
			applicantSkillRepository.deleteById(id);
		}catch(Exception e) {
			e.printStackTrace();
			response="ERROR";
		}
		
		return new DeleteResponse(response);
	}
	
	
	@RequestMapping( VIEW_CV  )
	public String viewMyCV(
			
			Model model
			
			) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
						
		//TODO
		username = "abc";
		
		ApplicantWithPassword applicant = applicantRepository.findOneByUsername(username);
		
		model.addAttribute("applicant", applicant);
		
		model.addAttribute("workexpLst", applicantWorkExpRepository.findByApplicantUsernameOrderByStartedDescFinishedDesc(username) );
		
		model.addAttribute("educationLst", applicantAcademicRepository.findByApplicantUsernameOrderByStartedDescFinishedDesc(username) );

		model.addAttribute("skillsLst", applicantSkillRepository.findByApplicantUsernameOrderByProficiencyDescNameDesc(username) );
		
		return "secured/view_my_cv.html";
		
	}
	
	
	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException() {
	        return "error.html";
	}
	
	
	
}
