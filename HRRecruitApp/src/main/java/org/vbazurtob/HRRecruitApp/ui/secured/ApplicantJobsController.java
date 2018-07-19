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
import org.vbazurtob.HRRecruitApp.model.JobApplicant;
import org.vbazurtob.HRRecruitApp.model.JobSearchFilter;
import org.vbazurtob.HRRecruitApp.model.JobType;
import org.vbazurtob.HRRecruitApp.model.SalaryRangeOption;
import org.vbazurtob.HRRecruitApp.model.repository.JobApplicantRepository;
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
	
	private final static String APPLICANTS_JOBS_APPLIED = "/my-applications/";
	
	private final static String JOB_SEARCH = "/search/";
	
	private final static String VIEW_JOB_DETAIL = "/view/";
	
	private final static int RECORDS_PER_PAGE = 10;
	
	private final static int RECORDS_PER_COLUMN = 4;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private JobApplicantRepository jobApplicantRepository;
	
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
		
		
		// View attributes
		model.addAttribute("baseUrl", controllerMapping + JOB_SEARCH);
		
		model.addAttribute("filterFormUrl", controllerMapping + FILTER_JOB_LIST);
		model.addAttribute("clearFilterFormUrl", controllerMapping + FILTER_JOB_CLEAR);
		
		
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", jobPageObj );
		model.addAttribute("jobsList", jobPageObj.getContent());
		
		
		model.addAttribute("jobTypeList", jobTypeList );
		
		model.addAttribute("filterJobSearch", filterJobSearch);
		model.addAttribute("salaryRangeListObj", salaryRangeListObj);
		model.addAttribute("jobDatePostedOptions", jobDatePostedOptions);
		model.addAttribute("jobDetailUrl", controllerMapping + VIEW_JOB_DETAIL );		
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
		
		//TODO
		username = "abc";
		System.out.println("username " + username);;
		
		boolean alreadyApplied = jobApplicantRepository.countByApplicantUsernameAndJobId(username, id) > 0 ? true : false;
		
		
		System.out.println("applied? " + alreadyApplied);;
		if(alreadyApplied == true) {
			
			JobApplicant jaDetails = jobApplicantRepository.findByApplicantUsernameAndJobId(username, id).get(0);
			model.addAttribute("dateApplied", jaDetails.getDateApplicationSent() );
			
		}
		
				
		Job jobDetailObj =  jobRepository.findById( id ).get() ;
				
		// View

		model.addAttribute("formActionUrl", controllerMapping + VIEW_JOB_DETAIL  + id);
		model.addAttribute("baseUrl", controllerMapping + VIEW_JOB_DETAIL);
		model.addAttribute("formTitle", "View job details");
		model.addAttribute("jobDetails", jobDetailObj);
		model.addAttribute("alreadyApply", alreadyApplied );
		
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
	
	
	
	
	@RequestMapping(value= { APPLICANTS_JOBS_APPLIED , APPLICANTS_JOBS_APPLIED + "/{page}"  })
	public String myAppliedJobs(
			
			@ModelAttribute("filterMyJobsApplied") JobSearchFilter filterJobSearch,
			@PathVariable Optional<Integer> page, 
			Model model,
			
			 SessionStatus sessionStatus
		
			) {
		
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//TODO
		username = "abc";
		
		// Pagination for listing
		Page<JobApplicant> jobAppliedPageObj = jobApplicantService.getPaginatedRecords(username, page, RECORDS_PER_PAGE);
		long previousPageNum = jobApplicantService.getPaginationNumbers(jobAppliedPageObj)[0];
		long nextPageNum = jobApplicantService.getPaginationNumbers(jobAppliedPageObj)[1];


//		List<JobType> jobTypeList = jobTypeService.getListJobTypeUI();
		
		
		// View attributes
		model.addAttribute("baseUrl", controllerMapping + APPLICANTS_JOBS_APPLIED);
		
//		model.addAttribute("filterFormUrl", controllerMapping + FILTER_JOB_LIST);
//		model.addAttribute("clearFilterFormUrl", controllerMapping + FILTER_JOB_CLEAR);
		
		
//		System.out.println(jobAppliedPageObj.getContent().size());;
		
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", jobAppliedPageObj );
		model.addAttribute("jobsAppliedList", jobAppliedPageObj.getContent());
		
		
//		model.addAttribute("jobTypeList", jobTypeList );
		
		model.addAttribute("filterJobSearch", filterJobSearch);
		model.addAttribute("salaryRangeListObj", salaryRangeListObj);
		model.addAttribute("jobDatePostedOptions", jobDatePostedOptions);

		
		return "secured/applicant_applied_jobs.html";
	}
	
	
	
	@RequestMapping("/summary")	
	public String summaryController() {
		
		
		return "secured/summary";
	}

	
	
	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException() {
	        return "error.html";
	}
	
	
	
}
