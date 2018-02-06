package org.vbazurtob.HRRecruitApp.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vbazurtob.HRRecruitApp.model.Applicant;
import org.vbazurtob.HRRecruitApp.model.JobType;

@Repository
public interface ApplicantRepository extends CrudRepository<Applicant, String> {

	public Applicant findOneByUsername(String username);
	
}
