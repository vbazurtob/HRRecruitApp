package org.vbazurtob.HRRecruitApp.model.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.Job;
import org.vbazurtob.HRRecruitApp.model.JobSpecification;
import org.vbazurtob.HRRecruitApp.model.repository.JobRepository;

@Service
public class JobService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobSearchFiltersService jobSearchFilterSvc;
	
	public void saveJob( Job jobForm ) {		
		jobRepository.save( jobForm );
		
	}
	

	
	public Page<Job> getPaginatedRecords(Job criteriaFilter, Optional<Integer> page, int recordsPerPage) {
		
		PageRequest pageReqObj = PageRequest.of(page.orElse(Integer.valueOf(0)) , recordsPerPage, Direction.DESC, "datePosted", "status", "title" ); 
		Page<Job> jobPageObj = jobRepository.findAll(
				new JobSpecification( 
						
						criteriaFilter,
						jobSearchFilterSvc
				
						)
		
				, pageReqObj);
						
		return jobPageObj;
		
	}
	

	
	public long[] getPaginationNumbers(Page<Job> jobPageObj) {
		int previousPageNum = jobPageObj.isFirst() ? 0 : jobPageObj.previousPageable().getPageNumber() ;
		int nextPageNum = jobPageObj.isLast() ?   jobPageObj.getTotalPages() - 1 : jobPageObj.nextPageable().getPageNumber() ;
		if(nextPageNum < 0) {
			nextPageNum = 0;
		}
		
		return  new  long[]{ previousPageNum, nextPageNum };
	}
	
//	public void getPaginatedApplicantsJob(long jobId) {
//		
//		Job jobFound = jobRepository.findById(jobId).get();
//		
//		if(jobFound != null) {
//			
//			jobFound.getJobApplicants()
//			
//		}
//		
//	}
	

}
