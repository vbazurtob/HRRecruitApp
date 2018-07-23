package org.vbazurtob.HRRecruitApp.ui.secured;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.vbazurtob.HRRecruitApp.lib.common.RecordNotFoundException;
import org.vbazurtob.HRRecruitApp.model.Job;
import org.vbazurtob.HRRecruitApp.model.JobApplicant;
import org.vbazurtob.HRRecruitApp.model.JobSearchFilter;
import org.vbazurtob.HRRecruitApp.model.JobType;
import org.vbazurtob.HRRecruitApp.model.SalaryRangeOption;
import org.vbazurtob.HRRecruitApp.model.repository.JobApplicantRepository;
import org.vbazurtob.HRRecruitApp.model.repository.JobRepository;
import org.vbazurtob.HRRecruitApp.model.service.JobApplicantService;
import org.vbazurtob.HRRecruitApp.model.service.JobSearchFiltersService;
import org.vbazurtob.HRRecruitApp.model.service.JobService;
import org.vbazurtob.HRRecruitApp.model.service.JobTypeService;

import static org.vbazurtob.HRRecruitApp.conf.ControllerEndpoints.*;

/**
 * @author Voltaire Bazurto Blacio
 * 
 * All rights reserved
 *
 * Controller for applicant job related operations
 *
 */
@SessionAttributes("filterJobSearch")
@Controller
@RequestMapping(JOBS_CNTROLLER)
public class ApplicantJobsController {
		
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
	
	
	@RequestMapping(value= { APPLICANT_JOB_SEARCH_PAGE , APPLICANT_JOB_SEARCH_PAGE  + "/{page}"  })
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
				
		// Pagination for listing
		Page<Job> jobPageObj = jobService.getPaginatedRecords(filterJobSearch, page, RECORDS_PER_PAGE);
		long previousPageNum = jobService.getPaginationNumbers(jobPageObj)[0];
		long nextPageNum = jobService.getPaginationNumbers(jobPageObj)[1];


		List<JobType> jobTypeList = jobTypeService.getListJobTypeUI();
		
		
		// View attributes
		model.addAttribute("baseUrl", controllerMapping + APPLICANT_JOB_SEARCH_PAGE);
		
		model.addAttribute("filterFormUrl", controllerMapping + APPLICANT_JOB_POST_FILTER_JOB_LIST);
		model.addAttribute("clearFilterFormUrl", controllerMapping + APPLICANT_JOB_POST_FILTER_JOB_CLEAR);
		
		
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", jobPageObj );
		model.addAttribute("jobsList", jobPageObj.getContent());
		
		
		model.addAttribute("jobTypeList", jobTypeList );
		
		model.addAttribute("filterJobSearch", filterJobSearch);
		model.addAttribute("salaryRangeListObj", salaryRangeListObj);
		model.addAttribute("jobDatePostedOptions", jobDatePostedOptions);
		model.addAttribute("jobDetailUrl", controllerMapping + APPLICANT_JOB_VIEW_JOB_DETAIL );		
		model.addAttribute("colPerRow", RECORDS_PER_COLUMN);
		
		return "secured/applicant_job_search.html";
	}
	
	
	@PostMapping( APPLICANT_JOB_POST_FILTER_JOB_LIST  )
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
		
		return "redirect:" + controllerMapping + APPLICANT_JOB_SEARCH_PAGE;
	}
	
	@PostMapping( APPLICANT_JOB_POST_FILTER_JOB_CLEAR  )
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
		
		jobFilterForm.setTitle(null);
		jobFilterForm.setJobType(null);
		jobFilterForm.setStatus(null);
		
		jobFilterForm.setSalaryRangeSearchIndex( 0 ); // <- BUG find the elem in arraylist with 0 index		
		jobFilterForm.setJobPostedTimeIndex(0);
		
		JobType jt = new JobType();
		
		jt.setId(0);
		jt.setDescription("All");
		jobFilterForm.setJobType(jt);
		
		return "redirect:" + controllerMapping + APPLICANT_JOB_SEARCH_PAGE;
	}
	
	@ModelAttribute("filterJobSearch")
	public Job getFilterForm() {	
		return new JobSearchFilter();
	}
	
	
	
	@RequestMapping( APPLICANT_JOB_VIEW_JOB_DETAIL + "{id}" )
	public String viewJobDetails(
			Model model,
			@PathVariable Long id
			) {
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		boolean alreadyApplied = jobApplicantRepository.countByApplicantUsernameAndJobId(username, id) > 0 ? true : false;
		
		
		if(alreadyApplied == true) {
			JobApplicant jaDetails = jobApplicantRepository.findByApplicantUsernameAndJobId(username, id).get(0);
			model.addAttribute("dateApplied", jaDetails.getDateApplicationSent() );
		}
		
				
		Job jobDetailObj =  jobRepository.findById( id ).get() ;
				
		// View

		model.addAttribute("formActionUrl", controllerMapping + APPLICANT_JOB_VIEW_JOB_DETAIL  + id);
		model.addAttribute("baseUrl", controllerMapping + APPLICANT_JOB_VIEW_JOB_DETAIL);
		model.addAttribute("formTitle", "View job details");
		model.addAttribute("jobDetails", jobDetailObj);
		model.addAttribute("alreadyApply", alreadyApplied );
		
		return "secured/job_detail_view_apply.html";
	
	}
	
	
	@PostMapping( APPLICANT_JOB_VIEW_JOB_DETAIL + "{id}" )
	public String applyJob(
			@Valid @ModelAttribute("jobDetails") Job jobDetails,
			BindingResult results, 
			RedirectAttributes redirectAttrs,
			Model model ){
		
		//Get Controller Name
		String controllerMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
		// Get logged username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		jobApplicantService.saveApplicantForJob(jobDetails, username);
		
		
		// In template add check if exist in db of applied
		return "redirect:" + controllerMapping + APPLICANT_JOB_VIEW_JOB_DETAIL + jobDetails.getId();
	}
	
	
	
	
	@RequestMapping(value= { APPLICANT_JOB_APPLICANTS_JOBS_APPLIED , APPLICANT_JOB_APPLICANTS_JOBS_APPLIED + "/{page}"  })
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
		
		
		// Pagination for listing
		Page<JobApplicant> jobAppliedPageObj = jobApplicantService.getPaginatedRecords(username, page, RECORDS_PER_PAGE);
		long previousPageNum = jobApplicantService.getPaginationNumbers(jobAppliedPageObj)[0];
		long nextPageNum = jobApplicantService.getPaginationNumbers(jobAppliedPageObj)[1];

		
		// View attributes
		model.addAttribute("baseUrl", controllerMapping + APPLICANT_JOB_APPLICANTS_JOBS_APPLIED);
		
		model.addAttribute("prevPage", previousPageNum);
		model.addAttribute("nextPage", nextPageNum);
		model.addAttribute("pageObj", jobAppliedPageObj );
		model.addAttribute("jobsAppliedList", jobAppliedPageObj.getContent() );
		
		model.addAttribute("filterJobSearch", filterJobSearch);
		model.addAttribute("salaryRangeListObj", salaryRangeListObj);
		model.addAttribute("jobDatePostedOptions", jobDatePostedOptions);

		
		return "secured/applicant_applied_jobs.html";
	}
	
	
	

	
	
	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException() {
	        return "error.html";
	}
	
	
	
}
