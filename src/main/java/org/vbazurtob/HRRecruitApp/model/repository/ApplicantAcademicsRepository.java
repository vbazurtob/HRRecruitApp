package org.vbazurtob.HRRecruitApp.model.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithPassword;
import org.vbazurtob.HRRecruitApp.model.ApplicantWorkExperience;
import org.vbazurtob.HRRecruitApp.model.ApplicantAcademic;


@Repository
@Transactional
public interface ApplicantAcademicsRepository extends PagingAndSortingRepository<ApplicantAcademic, Long> {
	
	public Page<ApplicantAcademic> findAll(Pageable page);
	
	
	public Page<ApplicantAcademic> findByApplicant(ApplicantWithPassword applicant, Pageable page);
	
	public Page<ApplicantAcademic> findByApplicantUsername(String username, Pageable page);
	
	
	public List<ApplicantAcademic> findByApplicantUsernameOrderByStartedDescFinishedDesc(String username);
	
	public long countByApplicantUsernameAndStartedAndFinishedAndDegreeNameAndDegreeTypeAndInstitution(
			String username, 
			Date started, 
			Date finished, 
			String degreeName, 
			String degreeType,
			String institution
	);
	

	
		
}
