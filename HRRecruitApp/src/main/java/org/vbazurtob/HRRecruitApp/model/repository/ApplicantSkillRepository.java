package org.vbazurtob.HRRecruitApp.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithPassword;
import org.vbazurtob.HRRecruitApp.model.ApplicantWorkExperience;
import org.vbazurtob.HRRecruitApp.model.ApplicantSkill;


@Repository
@Transactional
public interface ApplicantSkillRepository extends CrudRepository<ApplicantSkill, Long>{

	public Page<ApplicantSkill> findAll(Pageable page);
	
	
	public Page<ApplicantSkill> findByApplicant(ApplicantWithPassword applicant, Pageable page);
	
	public Page<ApplicantSkill> findByApplicantUsername(String username, Pageable page);
	
	
	public List<ApplicantSkill> findByApplicantUsernameOrderByProficiencyDescNameDesc(String username);
	
	public long countByApplicantUsernameAndNameAndProficiency(
			String username, 
			String name,
			Integer proficiency
	);
	
	
	
	
}
