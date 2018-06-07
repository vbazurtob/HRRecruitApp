package org.vbazurtob.HRRecruitApp.ui.secured;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.json.JsonObject;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.eclipse.persistence.annotations.DeleteAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.vbazurtob.HRRecruitApp.lib.common.DeleteResponse;
import org.vbazurtob.HRRecruitApp.lib.common.RecordNotFoundException;
import org.vbazurtob.HRRecruitApp.model.Applicant;
import org.vbazurtob.HRRecruitApp.model.ApplicantAcademic;
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
	
	private final static int RECORDS_PER_PAGE = 10;
	
	private final static String ACADEMICS_BASE_URL = "/academics/";
	

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
		
		model.addAttribute("userProfileOptionSelected",true);
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

			model.addAttribute("userProfileOptionSelected",true);
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
		Applicant newApplicantQuery = new Applicant();
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
		
		//Check if record already exists (DB validation)
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
		Applicant newApplicantQuery = new Applicant();
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
		// DEBUG form Validations
		//		System.out.println("Errors? " + results.hasErrors() );
		//		
		//		List<ObjectError> oes = results.getAllErrors();
		//		for( ObjectError oe: oes ) {
		//			System.out.println(oe.toString());
		//		}
		// System.out.println( "Record exists?  " + applicantAcademicsService.recordExists(username, academicsForm) );
		
		
		
		if(results.hasErrors()) { // Reload the form with errors			
			model.addAttribute("degreeTypeLst", degreeTypeService.getListDegreeTypes());
			return "secured/academics_form.html";
		}
		
		// Save the form data
		applicantAcademicsService.saveAcademicDetail(academicsForm, username);
		redirectAttrs.addFlashAttribute("saved",true);
		return "redirect:/cv/academics/";
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
	
	
	@RequestMapping("/workexp")
	public String showWorkExperience() {
		
		return "secured/workexp.html";
	}
	
	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException() {
	        return "error.html";
	}
	
	
	
}
