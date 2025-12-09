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

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.ApplicantAcademic;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantAcademicsRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;

@Service
public class ApplicantAcademicsService {

	@Autowired
	private ApplicantAcademicsRepository applicantAcademicRepository;
	
	@Autowired
	private ApplicantRepository applicantRepository;
	
	public ApplicantAcademicsService() {
	}
	
	public void saveAcademicDetail( ApplicantAcademic applicantAcademicForm, String applicantUsername ) {
		
		if(applicantAcademicForm.getInProgress() == null) {
			applicantAcademicForm.setInProgress("N");
		}
		
		applicantAcademicForm.setApplicant(applicantRepository.findOneByUsername( applicantUsername ));
		applicantAcademicRepository.save(applicantAcademicForm);
		
	}
	
	public boolean recordExists(
			String username ,
			Date started,
			Date finished,
			String degreeName,
			String degreeType,
			String institution
			
			) {
		
		long count = applicantAcademicRepository.countByApplicantUsernameAndStartedAndFinishedAndDegreeNameAndDegreeTypeAndInstitution(
				username, started, finished, degreeName, degreeType, institution);
		return  (count > 0) ;
	}
	
	public Page<ApplicantAcademic> getPaginatedRecords(String username, Optional<Integer> page, int recordsPerPage) {
		
		PageRequest pageReqObj = PageRequest.of(page.orElse(Integer.valueOf(0)) , recordsPerPage, Direction.DESC, "finished", "started" ); 
		Page<ApplicantAcademic> academicsPageObj = applicantAcademicRepository.findByApplicantUsername(username, pageReqObj);
		
		
		return academicsPageObj;
	}
	
	public long[] getPaginationNumbers(Page<ApplicantAcademic> academicsPageObj) {
		int previousPageNum = academicsPageObj.isFirst() ? 0 : academicsPageObj.previousPageable().getPageNumber() ;
		int nextPageNum = academicsPageObj.isLast() ?   academicsPageObj.getTotalPages() - 1 : academicsPageObj.nextPageable().getPageNumber() ;
		if(nextPageNum < 0) {
			nextPageNum = 0;
		}
		
		return  new  long[]{ previousPageNum, nextPageNum };
	}
	
	public List<ApplicantAcademic> get3MostRecentEducation(String username) {
		PageRequest pageReqObj = PageRequest.of( 0 , 3, Direction.DESC, "finished", "started" ); 
		return applicantAcademicRepository.findByApplicantUsername(username, pageReqObj).getContent();
	}
	

}
