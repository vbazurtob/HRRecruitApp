package org.vbazurtob.HRRecruitApp.model.service;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
	

	public void saveJob( Job jobForm ) {
		
		System.out.println( jobForm);;
		
		jobRepository.save( jobForm );
		
	}
	
//	public boolean recordExists(
//			String username ,
//			Date started,
//			Date finished,
//			String degreeName,
//			String degreeType,
//			String institution
//			
//			) {
//		
//		long count = applicantAcademicRepository.countByApplicantUsernameAndStartedAndFinishedAndDegreeNameAndDegreeTypeAndInstitution(
//				username, started, finished, degreeName, degreeType, institution);
//		return  (count > 0) ;
//	}
	
	public Page<Job> getPaginatedRecords(Job criteriaFilter, Optional<Integer> page, int recordsPerPage) {
		
//		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//		
//		CriteriaQuery<Job> query = cb.createQuery(Job.class);
//		
//		Root<Job> r = query.from(Job.class);
		
		//TODO
		
		
		
		PageRequest pageReqObj = PageRequest.of(page.orElse(Integer.valueOf(0)) , recordsPerPage, Direction.DESC, "datePosted", "status", "title" ); 
//		Page<Job> jobPageObj = jobRepository.findAll(pageReqObj);
		
		Page<Job> jobPageObj = jobRepository.findAll(new JobSpecification(criteriaFilter), pageReqObj);
				
//				findAllByTitleContainingOrStatusOrJobTypeId(
//				
//				criteriaFilter.getTitle(),
//				criteriaFilter.getStatus(),
//				criteriaFilter.getJobType() == null ? 0 :  criteriaFilter.getJobType().getId(),
//				
//				pageReqObj
//				);
		
		
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
	

}
