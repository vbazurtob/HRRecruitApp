package org.vbazurtob.HRRecruitApp.model.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.ApplicantWorkExperience;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantWorkExpRepository;

@Service
public class ApplicantWorkExpService {
	
	@Autowired
	private ApplicantWorkExpRepository appWorkExpRepository;
	
	@Autowired
	private ApplicantRepository applicantRepository;
	
	
	public ApplicantWorkExpService() {
	}
	
	public void saveWorkExpDetail( ApplicantWorkExperience applicantWorkExperienceForm, String applicantUsername ) {
			
		applicantWorkExperienceForm.setApplicant(applicantRepository.findOneByUsername( applicantUsername ));
		appWorkExpRepository.save(applicantWorkExperienceForm);
		
	}
	
	public boolean recordExists(
			String username ,
			Date started,
			Date finished,
			String position,
			String institution
			
			) {
		
		long count =  appWorkExpRepository.countByApplicantUsernameAndStartedAndFinishedAndPositionAndInstitution(
				username, started, finished, position, institution);
		return  (count > 0) ;
	}
	
	public Page<ApplicantWorkExperience> getPaginatedRecords(String username, Optional<Integer> page, int recordsPerPage) {
		
		PageRequest pageReqObj = PageRequest.of(page.orElse(Integer.valueOf(0)) , recordsPerPage, Direction.DESC, "finished", "started" ); 
		Page<ApplicantWorkExperience> academicsPageObj = appWorkExpRepository.findByApplicantUsername(username, pageReqObj);
		
		
		
		return academicsPageObj;
	}
	
	public long[] getPaginationNumbers(Page<ApplicantWorkExperience> academicsPageObj) {
		int previousPageNum = academicsPageObj.isFirst() ? 0 : academicsPageObj.previousPageable().getPageNumber() ;
		int nextPageNum = academicsPageObj.isLast() ? academicsPageObj.getTotalPages() - 1 : academicsPageObj.nextPageable().getPageNumber() ;
		
		if(nextPageNum < 0) {
			nextPageNum = 0;
		}
		
		
		return  new  long[]{ previousPageNum, nextPageNum };
	}
	
	public List<ApplicantWorkExperience> get3MostRecentExperience(String username) {
		PageRequest pageReqObj = PageRequest.of( 0 , 3, Direction.DESC, "finished", "started" ); 
		return appWorkExpRepository.findByApplicantUsername(username, pageReqObj).getContent();
	}
	
}
