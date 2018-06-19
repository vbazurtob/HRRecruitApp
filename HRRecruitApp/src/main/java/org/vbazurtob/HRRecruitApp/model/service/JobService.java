package org.vbazurtob.HRRecruitApp.model.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.Job;
import org.vbazurtob.HRRecruitApp.model.repository.JobRepository;

@Service
public class JobService {

	@Autowired
	private JobRepository jobRepository;
	

	public void saveJob( Job jobForm ) {
		
//		applicantAcademicRepository.save(applicantAcademicForm);
		
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
	
	public Page<Job> getPaginatedRecords( Optional<Integer> page, int recordsPerPage) {
		
		PageRequest pageReqObj = PageRequest.of(page.orElse(Integer.valueOf(0)) , recordsPerPage, Direction.DESC, "salary", "title" ); 
		Page<Job> jobPageObj = jobRepository.findAll(pageReqObj);
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
