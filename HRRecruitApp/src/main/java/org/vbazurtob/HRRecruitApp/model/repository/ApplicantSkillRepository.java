package org.vbazurtob.HRRecruitApp.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vbazurtob.HRRecruitApp.model.Applicant;
import org.vbazurtob.HRRecruitApp.model.ApplicantSkill;


@Repository
@Transactional
public interface ApplicantSkillRepository extends CrudRepository<ApplicantSkill, Long>{

	public Page<ApplicantSkill> findAll(Pageable page);
	
	
	public Page<ApplicantSkill> findByApplicant(Applicant applicant, Pageable page);
	
	public Page<ApplicantSkill> findByApplicantUsername(String username, Pageable page);
	
	public long countByApplicantUsernameAndNameAndProficiency(
			String username, 
			String name,
			Integer proficiency
	);
	
	
	
	
}
