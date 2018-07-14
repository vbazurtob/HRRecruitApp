package org.vbazurtob.HRRecruitApp.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithPassword;
import org.vbazurtob.HRRecruitApp.model.JobApplicant;


@Repository
@Transactional
public interface JobApplicantRepository extends CrudRepository<JobApplicant, Long>{

	public Page<JobApplicant> findAll(Pageable page);
	
	
	public Page<JobApplicant> findByApplicant(ApplicantWithPassword applicant, Pageable page);
	
	public Page<JobApplicant> findByApplicantUsername(String username, Pageable page);
	
	public List<JobApplicant> findByApplicantUsernameAndJobId(String username, Long id);
	
	public Page<JobApplicant> findByJobId(long jobId, Pageable page);

	
	public long countByApplicantUsernameAndJobId(
			String username, 
			Long id
	);
	
	
	
	
}
