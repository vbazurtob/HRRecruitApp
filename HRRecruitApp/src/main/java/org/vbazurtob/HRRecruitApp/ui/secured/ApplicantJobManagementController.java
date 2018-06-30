package org.vbazurtob.HRRecruitApp.ui.secured;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
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
import org.vbazurtob.HRRecruitApp.model.Job;
import org.vbazurtob.HRRecruitApp.model.JobType;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantAcademicsRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantSkillRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantWorkExpRepository;
import org.vbazurtob.HRRecruitApp.model.repository.JobRepository;
import org.vbazurtob.HRRecruitApp.model.repository.JobTypeRepository;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantAcademicsService;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantService;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantSkillService;
import org.vbazurtob.HRRecruitApp.model.service.ApplicantWorkExpService;
import org.vbazurtob.HRRecruitApp.model.service.CountryService;
import org.vbazurtob.HRRecruitApp.model.service.JobService;
import org.vbazurtob.HRRecruitApp.model.service.JobTypeService;
import org.vbazurtob.HRRecruitApp.model.service.ProficiencyService;
import org.vbazurtob.HRRecruitApp.model.service.TypeDegreeService;

import ch.qos.logback.classic.pattern.Util;
import javafx.concurrent.WorkerStateEvent;


@SessionAttributes("filterJobForm")
@Controller
@RequestMapping("/offers")
public class ApplicantJobManagementController {
	
	private final static String  DASHBOARD_JOB_MANAGEMENT_BASE_URL = "/management/";
	
	private final static String FILTER_JOB_LIST = "/filter-jobs/";
	
	private final static String FILTER_JOB_CLEAR = "/clear-filter-jobs/";
	
	private final static String APPLICANTS_JOB = "/applicants-job/";
	
	private final static int RECORDS_PER_PAGE = 10;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private JobTypeRepository jobTypeRepository;
	
	@Autowired
	private JobTypeService jobTypeService;
	
	private String[] arrStatus = new String[]{ "All", "Open", "Closed" };
	private List<String> jobStatusListObj;
	
	
	
	
	public JobAdsManagementController() {
	}


	@PostConstruct
	private void init() {

		jobStatusListObj =  Arrays.asList( arrStatus );

		
	}
	
	
	@RequestMapping(value= { DASHBOARD_JOB_MANAGEMENT_BASE_URL , DASHBOARD_JOB_MANAGEMENT_BASE_URL + "/{page}"  })
	public String listJobs(
			
			@ModelAttribute("filterJobForm") Job jobFilterForm,
			@PathVariable Optional<Integer> page, 
			Model model,
			
			 SessionStatus sessionStatus
		
			) {
		
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "admin";
		
		// Pagination for listing
		Page<Job> jobPageObj = jobService.getPaginatedRecords(jobFilterForm, page, RECORDS_PER_PAGE);
		long previousPageNum = jobService.getPaginationNumbers(jobPageObj)[0];
		long nextPageNum = jobService.getPaginationNumbers(jobPageObj)[1];


		List<JobType> jobTypeList = jobTypeService.getListJobTypeUI();
		
		
		Job emptyFormFilter = new Job();
		

		// View attributes
		model.addAttribute("baseUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL);
		model.addAttribute("filterFormUrl", controllerMapping + FILTER_JOB_LIST);
		model.addAttribute("clearFilterFormUrl", controllerMapping + FILTER_JOB_CLEAR);
		
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", jobPageObj );
		model.addAttribute("jobsList", jobPageObj.getContent());
		model.addAttribute("jobForm", emptyFormFilter);
		model.addAttribute("jobTypeList", jobTypeList );
		model.addAttribute("jobStatusList", jobStatusListObj );
		model.addAttribute("filterJobForm", jobFilterForm);
		
		model.addAttribute("applicantsJobsUrl", controllerMapping + APPLICANTS_JOB);

		
		return "secured/job_management.html";
	}
	
	
	@PostMapping( FILTER_JOB_LIST  )
	public String filterJobs(
			@ModelAttribute("filterJobForm") Job jobFilterForm,
			@Valid @ModelAttribute("jobForm") Job jobForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model ,
			HttpSession session,
			 SessionStatus sessionStatus
		
			) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		
		
		jobFilterForm.setTitle(jobForm.getTitle());
		jobFilterForm.setStatus(jobForm.getStatus());
		jobFilterForm.setJobType(jobForm.getJobType());

		return "redirect:" + controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL;
	}
	
	@PostMapping( FILTER_JOB_CLEAR  )
	public String clearFilters(
			
			@ModelAttribute("filterJobForm") Job jobFilterForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model ,
			HttpSession session,
			SessionStatus sessionStatus
			
			
			) {
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		
		System.out.println("OBJ clear  " + jobFilterForm);;
		
		jobFilterForm.setTitle(null);
		jobFilterForm.setJobType(null);
		jobFilterForm.setStatus(null);
		
		JobType jt = new JobType();
		
		jt.setId(0);
		jt.setDescription("All");
		jobFilterForm.setJobType(jt);
		
		return "redirect:" + controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL;
	}
	
	@ModelAttribute("filterJobForm")
	public Job getFilterForm() {	
		return new Job();
	}
	
	
	@RequestMapping( DASHBOARD_JOB_MANAGEMENT_BASE_URL + "new" )
	public String newJob(
			Model model
			) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "admin";
		
		Job newJobObj = new Job();

		ArrayList<JobType> jobTypeList = (ArrayList<JobType>) jobTypeRepository.findAllByOrderByDescriptionAsc();
		
		
		// View attributes
		model.addAttribute("baseUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL);
		model.addAttribute("formActionUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL + "new");
		
		model.addAttribute("formTitle", "Create new job");
		model.addAttribute("jobForm", newJobObj);
		model.addAttribute("jobTypeList", jobTypeList );
		
		return "secured/job_create_edit.html";
	}
	
	
	
	@PostMapping( DASHBOARD_JOB_MANAGEMENT_BASE_URL + "new"  )
	public String saveProfile(
			@Valid @ModelAttribute("jobForm") Job jobForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model ){
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
				
		//TODO
		username = "admin";
		
//		System.out.println("description" +  jobForm.getJobType().getDescription() + " ID " + jobForm.getJobType().getId());
		
		if(results.hasErrors()) {
			
			ArrayList<JobType> jobTypeList = (ArrayList<JobType>) jobTypeRepository.findAllByOrderByDescriptionAsc();
			model.addAttribute("formActionUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL + "new");
			model.addAttribute("baseUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL);
			model.addAttribute("formTitle", "Create new job");
			model.addAttribute("jobForm", jobForm);
			model.addAttribute("jobTypeList", jobTypeList );
			
			return "secured/job_create_edit.html";
		}
		

		redirectAttrs.addFlashAttribute("saved", true);
		

		
		Optional<JobType> jt = jobTypeRepository.findById( jobForm.getJobType().getId() );
		

		jobForm.setDatePosted(new Date());
		jobForm.setStatus("O");
		jobForm.setJobType( jt.get()   );
		
		jobService.saveJob(jobForm);
		
		
		return "redirect:" + controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL;
	}
	
	
	
	
	@RequestMapping( DASHBOARD_JOB_MANAGEMENT_BASE_URL + "edit/{id}" )
	public String editJob(
			Model model,
			@PathVariable Long id
			) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
				
		//TODO
		username = "admin";
				
		Job jobFormObj =  jobRepository.findById( id ).get() ;
				
		// View
		ArrayList<JobType> jobTypeList = (ArrayList<JobType>) jobTypeRepository.findAllByOrderByDescriptionAsc();
		model.addAttribute("formActionUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL + "edit/" + id);
		model.addAttribute("baseUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL);
		model.addAttribute("formTitle", "Edit job");
		model.addAttribute("jobForm", jobFormObj);
		model.addAttribute("jobTypeList", jobTypeList );
		
		return "secured/job_create_edit.html";
	
	}
	
	
	@PostMapping( DASHBOARD_JOB_MANAGEMENT_BASE_URL + "edit/{id}"   )
	public String editJob(
			@Valid @ModelAttribute("jobForm") Job jobForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model ){
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
				
		//TODO
		username = "admin";
		
//		System.out.println("description" +  jobForm.getJobType().getDescription() + " ID " + jobForm.getJobType().getId());
		
		
		// DEBUG
		Utils.printFormErrors(results);
		
		
		if(results.hasErrors()) {
			
			ArrayList<JobType> jobTypeList = (ArrayList<JobType>) jobTypeRepository.findAllByOrderByDescriptionAsc();
			model.addAttribute("formActionUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL + "edit/" + jobForm.getId());
			model.addAttribute("baseUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL);
			model.addAttribute("formTitle", "Edit new job");
			model.addAttribute("jobForm", jobForm);
			model.addAttribute("jobTypeList", jobTypeList );
			
			return "secured/job_create_edit.html";
		}
		

	

		
		Optional<Job> jobDbObj = jobRepository.findById(jobForm.getId());
		jobForm.setStatus( jobDbObj.get().getStatus() );
		jobForm.setDatePosted( jobDbObj.get().getDatePosted()  );
		
		
		
		
		Optional<JobType> jt = jobTypeRepository.findById( jobForm.getJobType().getId() );
		jobForm.setJobType( jt.get()   );
		
		
		jobService.saveJob(jobForm);
		
		redirectAttrs.addFlashAttribute("saved", true);
		return "redirect:" + controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL;
	}
	
	@DeleteMapping( value= DASHBOARD_JOB_MANAGEMENT_BASE_URL + "delete/{id}", produces= { MediaType.APPLICATION_JSON_VALUE } )
	@ResponseBody
	public DeleteResponse deleteSkills( @PathVariable Long id ) {

		String response="OK";
		try {

		 Job jobDb = jobRepository.findById(id).get();
		 
		 
		 jobDb.setStatus("D");// Deleted. In this version, no validation is made regarding if there might be or not already applicants or this job.
		 jobRepository.save(jobDb);
		 
		}catch(Exception e) {
			e.printStackTrace();
			response="ERROR";
		}
		
		return new DeleteResponse(response);
	}
	
	
	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException() {
	        return "error.html";
	}
	
	
	
}
