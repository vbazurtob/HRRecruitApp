package org.vbazurtob.HRRecruitApp.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithPassword;


@Repository
public interface ApplicantRepository extends CrudRepository<ApplicantWithPassword, String> {

	public ApplicantWithPassword findOneByUsername(String username);
	
	public long countByUsernameAndPassword( String username, String password );
	
	public long countByUsername( String username );
	
}
