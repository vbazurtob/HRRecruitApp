package org.vbazurtob.HRRecruitApp.model.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
import org.vbazurtob.HRRecruitApp.model.JobApplicant;
import org.vbazurtob.HRRecruitApp.model.JobSpecification;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;
import org.vbazurtob.HRRecruitApp.model.repository.JobApplicantRepository;
import org.vbazurtob.HRRecruitApp.model.repository.JobRepository;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@Service
public class JobApplicantService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JobApplicantRepository jobApplicantRepository;
	
	@Autowired
	private ApplicantRepository applicantRepository;
	
	
	public boolean saveApplicantForJob( Job jobForm, String username ) {
		
		if( jobApplicantRepository.countByApplicantUsernameAndJobId(username, jobForm.getId()) <=0 ) {
			JobApplicant jA = new JobApplicant();
			jA.setApplicant(applicantRepository.findOneByUsername(username));
			jA.setDateApplicationSent(new Date());
			jA.setJob(jobForm);
			jobApplicantRepository.save(jA);
			return true;
		}else {
			System.out.println( "Already applied" ); // TODO check thro exception
			return false;
		}
		
				
	}
	
	public Page<JobApplicant> getPaginatedRecords(String username, Optional<Integer> page, int recordsPerPage) {
		
		PageRequest pageReqObj = PageRequest.of(page.orElse(Integer.valueOf(0)) , recordsPerPage, Direction.DESC, "dateApplicationSent", "job.datePosted" ); 
		Page<JobApplicant> jobApplicantPageObj = jobApplicantRepository.findByApplicantUsername(username, pageReqObj);
						
		return jobApplicantPageObj;
		
	}
	

	
	public long[] getPaginationNumbers(Page<JobApplicant> jobPageObj) {
		int previousPageNum = jobPageObj.isFirst() ? 0 : jobPageObj.previousPageable().getPageNumber() ;
		int nextPageNum = jobPageObj.isLast() ?   jobPageObj.getTotalPages() - 1 : jobPageObj.nextPageable().getPageNumber() ;
		if(nextPageNum < 0) {
			nextPageNum = 0;
		}
		
		return  new  long[]{ previousPageNum, nextPageNum };
	}
	
	public Page<JobApplicant> getPaginatedApplicantsFromJob(long jobId, Optional<Integer> page, int recordsPerPage) {
		
		
		PageRequest pageReqObj = PageRequest.of(page.orElse(Integer.valueOf(0)) , recordsPerPage, Direction.DESC, "dateApplicationSent", "job.datePosted" ); 
		Page<JobApplicant> jobApplicantPageObj = jobApplicantRepository.findByJobId( jobId, pageReqObj );
						
		return jobApplicantPageObj;
		
	}
		

}
