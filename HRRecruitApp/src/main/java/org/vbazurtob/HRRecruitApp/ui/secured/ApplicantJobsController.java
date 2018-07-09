package org.vbazurtob.HRRecruitApp.ui.secured;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import org.vbazurtob.HRRecruitApp.model.Job;
import org.vbazurtob.HRRecruitApp.model.JobSearchFilter;
import org.vbazurtob.HRRecruitApp.model.JobType;
import org.vbazurtob.HRRecruitApp.model.SalaryRangeOption;
import org.vbazurtob.HRRecruitApp.model.repository.JobRepository;
import org.vbazurtob.HRRecruitApp.model.repository.JobTypeRepository;
import org.vbazurtob.HRRecruitApp.model.service.JobApplicantService;
import org.vbazurtob.HRRecruitApp.model.service.JobSearchFiltersService;
import org.vbazurtob.HRRecruitApp.model.service.JobService;
import org.vbazurtob.HRRecruitApp.model.service.JobTypeService;


@SessionAttributes("filterJobSearch")
@Controller
@RequestMapping("/jobs")
public class ApplicantJobsController {
	
	
	private final static String FILTER_JOB_LIST = "/filter-search/";
	
	private final static String FILTER_JOB_CLEAR = "/clear-filter-jobs/";
	
	private final static String APPLICANTS_JOB = "/applicants-job/";
	
	private final static String JOB_SEARCH = "/search/";
	
	private final static String VIEW_JOB_DETAIL = "/view/";
	
	private final static int RECORDS_PER_PAGE = 10;
	
	private final static int RECORDS_PER_COLUMN = 4;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private JobTypeRepository jobTypeRepository;
	
	@Autowired
	private JobApplicantService jobApplicantService;
	
	@Autowired
	private JobTypeService jobTypeService;
	
	@Autowired
	private JobSearchFiltersService jobSearchFiltersService;
	
	
	private HashMap<Integer, String> jobDatePostedOptions;
	
	private HashMap<Integer, SalaryRangeOption> salaryRangeListObj;
	
	
	public ApplicantJobsController() {
	}


	@PostConstruct
	private void init() {
		jobDatePostedOptions = jobSearchFiltersService.getDatePostedFilterOptions();
		salaryRangeListObj = jobSearchFiltersService.getSalaryFilterOptions();		
	}
	
	
	@RequestMapping(value= { JOB_SEARCH , JOB_SEARCH + "/{page}"  })
	public String searchJobs(
			
			@ModelAttribute("filterJobSearch") JobSearchFilter filterJobSearch,
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
		Page<Job> jobPageObj = jobService.getPaginatedRecords(filterJobSearch, page, RECORDS_PER_PAGE);
		long previousPageNum = jobService.getPaginationNumbers(jobPageObj)[0];
		long nextPageNum = jobService.getPaginationNumbers(jobPageObj)[1];


		List<JobType> jobTypeList = jobTypeService.getListJobTypeUI();
		
		
		Job emptyFormFilter = new Job();
		

		// View attributes
		model.addAttribute("baseUrl", controllerMapping + JOB_SEARCH);
		
		model.addAttribute("filterFormUrl", controllerMapping + FILTER_JOB_LIST);
		model.addAttribute("clearFilterFormUrl", controllerMapping + FILTER_JOB_CLEAR);
		
		
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", jobPageObj );
		model.addAttribute("jobsList", jobPageObj.getContent());
		
		//model.addAttribute("jobForm", emptyFormFilter);
		
		model.addAttribute("jobTypeList", jobTypeList );
		//model.addAttribute("jobStatusList", jobStatusListObj );
		
		
		model.addAttribute("filterJobSearch", filterJobSearch);
		model.addAttribute("salaryRangeListObj", salaryRangeListObj);
		
		model.addAttribute("jobDatePostedOptions", jobDatePostedOptions);
		
//		jobDatePostedOptions.keySet().iterator().next().intValue()
		
		model.addAttribute("jobDetailUrl", controllerMapping + VIEW_JOB_DETAIL );
		
		
		//model.addAttribute("applicantsJobsUrl", controllerMapping + APPLICANTS_JOB);

		
		model.addAttribute("colPerRow", RECORDS_PER_COLUMN);
		
		return "secured/applicant_job_search.html";
	}
	
	
	@PostMapping( FILTER_JOB_LIST  )
	public String filterJobs(
			@ModelAttribute("filterJobSearch") JobSearchFilter jobFilterForm,
			@Valid @ModelAttribute("jobForm") JobSearchFilter jobForm,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model ,
			HttpSession session,
			 SessionStatus sessionStatus
		
			) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		
		
		jobFilterForm.setTitle(jobForm.getTitle());
		jobFilterForm.setStatus("Open");
		jobFilterForm.setJobType(jobForm.getJobType());
		
		System.out.println("ON FILTER object created " + jobForm.getSalaryRangeSearchIndex());;
		
	
		
		System.out.println("OBJ filter  " + jobFilterForm);;
		
		return "redirect:" + controllerMapping + JOB_SEARCH;
	}
	
	@PostMapping( FILTER_JOB_CLEAR  )
	public String clearFilters(
			
			@ModelAttribute("filterJobSearch") JobSearchFilter jobFilterForm,
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
		
		jobFilterForm.setSalaryRangeSearchIndex( 0 ); // <- BUG find the elem in arraylist with 0 index
		
		System.out.println("======= " + salaryRangeListObj.get(0));;
		
		
		jobFilterForm.setJobPostedTimeIndex(0);
		
		JobType jt = new JobType();
		
		jt.setId(0);
		jt.setDescription("All");
		jobFilterForm.setJobType(jt);
		
		System.out.println("AFTER OBJ clear  " + jobFilterForm );;
		
		return "redirect:" + controllerMapping + JOB_SEARCH;
	}
	
	@ModelAttribute("filterJobSearch")
	public Job getFilterForm() {	
		return new JobSearchFilter();
	}
	
	
	
	@RequestMapping( VIEW_JOB_DETAIL + "{id}" )
	public String viewJobDetails(
			Model model,
			@PathVariable Long id
			) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
				
		
				
		Job jobDetailObj =  jobRepository.findById( id ).get() ;
				
		// View

		model.addAttribute("formActionUrl", controllerMapping + VIEW_JOB_DETAIL  + id);
		model.addAttribute("baseUrl", controllerMapping + VIEW_JOB_DETAIL);
		model.addAttribute("formTitle", "View job details");
		model.addAttribute("jobDetails", jobDetailObj);
//		model.addAttribute("jobTypeList", jobTypeList );
		
		return "secured/job_detail_view_apply.html";
	
	}
	
	
	@PostMapping( VIEW_JOB_DETAIL + "{id}" )
	public String applyJob(
			@Valid @ModelAttribute("jobDetails") Job jobDetails,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model ){
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
				

		//TODO
		username="abc";
		
		jobApplicantService.saveApplicantForJob(jobDetails, username);
		
		
		
//		redirectAttrs.addFlashAttribute("applied", true);
		
		// In template add check if exist in db of applied
		
		
		return "redirect:" + controllerMapping + VIEW_JOB_DETAIL + jobDetails.getId();
	}
	
	
	
//	@RequestMapping( DASHBOARD_JOB_MANAGEMENT_BASE_URL + "new" )
//	public String newJob(
//			Model model
//			) {
//		
//		//Get Controller Name
//		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
//		// Get logged username
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		
//		//TODO
//		username = "admin";
//		
//		Job newJobObj = new Job();
//
//		ArrayList<JobType> jobTypeList = (ArrayList<JobType>) jobTypeRepository.findAllByOrderByDescriptionAsc();
//		
//		
//		// View attributes
//		model.addAttribute("baseUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL);
//		model.addAttribute("formActionUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL + "new");
//		
//		model.addAttribute("formTitle", "Create new job");
//		model.addAttribute("jobForm", newJobObj);
//		model.addAttribute("jobTypeList", jobTypeList );
//		
//		return "secured/job_create_edit.html";
//	}
//	
//	
//	
//	@PostMapping( DASHBOARD_JOB_MANAGEMENT_BASE_URL + "new"  )
//	public String saveProfile(
//			@Valid @ModelAttribute("jobForm") Job jobForm,
//			BindingResult results, 
//			RedirectAttributes redirectAttrs,
//			Model model ){
//		
//		//Get Controller Name
//		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
//		// Get logged username
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//				
//		//TODO
//		username = "admin";
//		
////		System.out.println("description" +  jobForm.getJobType().getDescription() + " ID " + jobForm.getJobType().getId());
//		
//		if(results.hasErrors()) {
//			
//			ArrayList<JobType> jobTypeList = (ArrayList<JobType>) jobTypeRepository.findAllByOrderByDescriptionAsc();
//			model.addAttribute("formActionUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL + "new");
//			model.addAttribute("baseUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL);
//			model.addAttribute("formTitle", "Create new job");
//			model.addAttribute("jobForm", jobForm);
//			model.addAttribute("jobTypeList", jobTypeList );
//			
//			return "secured/job_create_edit.html";
//		}
//		
//
//		redirectAttrs.addFlashAttribute("saved", true);
//		
//
//		
//		Optional<JobType> jt = jobTypeRepository.findById( jobForm.getJobType().getId() );
//		
//
//		jobForm.setDatePosted(new Date());
//		jobForm.setStatus("O");
//		jobForm.setJobType( jt.get()   );
//		
//		jobService.saveJob(jobForm);
//		
//		
//		return "redirect:" + controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL;
//	}
//	
//	
//	
//	
//	@RequestMapping( DASHBOARD_JOB_MANAGEMENT_BASE_URL + "edit/{id}" )
//	public String editJob(
//			Model model,
//			@PathVariable Long id
//			) {
//		
//		//Get Controller Name
//		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
//		// Get logged username
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//				
//		//TODO
//		username = "admin";
//				
//		Job jobFormObj =  jobRepository.findById( id ).get() ;
//				
//		// View
//		ArrayList<JobType> jobTypeList = (ArrayList<JobType>) jobTypeRepository.findAllByOrderByDescriptionAsc();
//		model.addAttribute("formActionUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL + "edit/" + id);
//		model.addAttribute("baseUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL);
//		model.addAttribute("formTitle", "Edit job");
//		model.addAttribute("jobForm", jobFormObj);
//		model.addAttribute("jobTypeList", jobTypeList );
//		
//		return "secured/job_create_edit.html";
//	
//	}
//	
//	
//	@PostMapping( DASHBOARD_JOB_MANAGEMENT_BASE_URL + "edit/{id}"   )
//	public String editJob(
//			@Valid @ModelAttribute("jobForm") Job jobForm,
//			BindingResult results, 
//			RedirectAttributes redirectAttrs,
//			Model model ){
//		
//		//Get Controller Name
//		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
//		// Get logged username
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//				
//		//TODO
//		username = "admin";
//		
////		System.out.println("description" +  jobForm.getJobType().getDescription() + " ID " + jobForm.getJobType().getId());
//		
//		
//		// DEBUG
//		Utils.printFormErrors(results);
//		
//		
//		if(results.hasErrors()) {
//			
//			ArrayList<JobType> jobTypeList = (ArrayList<JobType>) jobTypeRepository.findAllByOrderByDescriptionAsc();
//			model.addAttribute("formActionUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL + "edit/" + jobForm.getId());
//			model.addAttribute("baseUrl", controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL);
//			model.addAttribute("formTitle", "Edit new job");
//			model.addAttribute("jobForm", jobForm);
//			model.addAttribute("jobTypeList", jobTypeList );
//			
//			return "secured/job_create_edit.html";
//		}
//		
//
//	
//
//		
//		Optional<Job> jobDbObj = jobRepository.findById(jobForm.getId());
//		jobForm.setStatus( jobDbObj.get().getStatus() );
//		jobForm.setDatePosted( jobDbObj.get().getDatePosted()  );
//		
//		
//		
//		
//		Optional<JobType> jt = jobTypeRepository.findById( jobForm.getJobType().getId() );
//		jobForm.setJobType( jt.get()   );
//		
//		
//		jobService.saveJob(jobForm);
//		
//		redirectAttrs.addFlashAttribute("saved", true);
//		return "redirect:" + controllerMapping + DASHBOARD_JOB_MANAGEMENT_BASE_URL;
//	}
//	
//	@DeleteMapping( value= DASHBOARD_JOB_MANAGEMENT_BASE_URL + "delete/{id}", produces= { MediaType.APPLICATION_JSON_VALUE } )
//	@ResponseBody
//	public DeleteResponse deleteSkills( @PathVariable Long id ) {
//
//		String response="OK";
//		try {
//
//		 Job jobDb = jobRepository.findById(id).get();
//		 
//		 
//		 jobDb.setStatus("D");// Deleted. In this version, no validation is made regarding if there might be or not already applicants or this job.
//		 jobRepository.save(jobDb);
//		 
//		}catch(Exception e) {
//			e.printStackTrace();
//			response="ERROR";
//		}
//		
//		return new DeleteResponse(response);
//	}
	
	
	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException() {
	        return "error.html";
	}
	
	
	
}
