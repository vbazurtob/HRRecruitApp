package org.vbazurtob.HRRecruitApp.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.ApplicantSkill;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantSkillRepository;

@Service
public class ApplicantSkillService {
	
	@Autowired
	private ApplicantSkillRepository appSkillRepository;
	
	@Autowired
	private ApplicantRepository applicantRepository;
	
	
	public ApplicantSkillService() {
	
	}
	
	public void saveSkill( ApplicantSkill applicantSkillForm, String applicantUsername ) {
			
		applicantSkillForm.setApplicant(applicantRepository.findOneByUsername( applicantUsername ));
		appSkillRepository.save(applicantSkillForm);
		
	}
	
	public boolean recordExists(
			String username ,
			String name,
			Integer proficiency
			
			) {
		
		long count =  appSkillRepository.countByApplicantUsernameAndNameAndProficiency(
				username, name, proficiency);
		return  (count > 0) ;
	}
	
	public Page<ApplicantSkill> getPaginatedRecords(String username, Optional<Integer> page, int recordsPerPage) {
		
		PageRequest pageReqObj = PageRequest.of(page.orElse(Integer.valueOf(0)) , recordsPerPage, Direction.DESC, "proficiency", "name" ); 
		Page<ApplicantSkill> skillPageObj = appSkillRepository.findByApplicantUsername(username, pageReqObj);
		
		return skillPageObj;
	}
	
	public long[] getPaginationNumbers(Page<ApplicantSkill> skillPageObj) {
		int previousPageNum = skillPageObj.isFirst() ? 0 : skillPageObj.previousPageable().getPageNumber() ;
		int nextPageNum = skillPageObj.isLast() ? skillPageObj.getTotalPages() - 1 : skillPageObj.nextPageable().getPageNumber() ;
		
		if(nextPageNum < 0) {
			nextPageNum = 0;
		}
		
		
		return  new  long[]{ previousPageNum, nextPageNum };
	}
	
}
