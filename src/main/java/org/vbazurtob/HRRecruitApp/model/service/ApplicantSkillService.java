/*
 * Personal License Agreement
 * Copyright Notice
 *
 * © 2025 Voltaire Bazurto Blacio. All rights reserved.
 * License Terms
 *
 *     Ownership: All code contained in this portfolio is the sole property of Voltaire Bazurto Blacio and is hereby copyrighted by me.
 *
 *     Permitted Use: Others are welcome to view and study the code for personal, educational, or non-commercial purposes. You may share insights or information about the code, but you cannot use it for any commercial products, either as-is or in a derivative form.
 *
 *     Restrictions: The code may not be used, reproduced, or distributed for commercial purposes under any circumstances without my explicit written permission.
 *
 *     Rights Reserved: I reserve the right to use the code, or any future versions thereof, for my own purposes in any way I choose, including but not limited to the development of future commercial derivative works under my name or personal brand.
 *
 *     Disclaimer: The code is provided "as is" without warranty of any kind, either express or implied. I am not responsible for any damages resulting from the use of this code.
 *
 * By accessing this portfolio, you agree to abide by these terms.
 */

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
