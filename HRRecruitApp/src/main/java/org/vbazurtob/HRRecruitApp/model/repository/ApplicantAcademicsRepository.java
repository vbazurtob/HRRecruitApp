package org.vbazurtob.HRRecruitApp.model.repository;


import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vbazurtob.HRRecruitApp.model.Applicant;
import org.vbazurtob.HRRecruitApp.model.ApplicantAcademic;


@Repository
@Transactional
public interface ApplicantAcademicsRepository extends PagingAndSortingRepository<ApplicantAcademic, Long> {
	
	public Page<ApplicantAcademic> findAll(Pageable page);
	
	
	public Page<ApplicantAcademic> findByApplicant(Applicant applicant, Pageable page);
	
	public Page<ApplicantAcademic> findByApplicantUsername(String username, Pageable page);
	
	public long countByApplicantUsernameAndStartedAndFinishedAndDegreeNameAndDegreeTypeAndInstitution(
			String username, 
			Date started, 
			Date finished, 
			String degreeName, 
			String degreeType,
			String institution
	);
	
		
}