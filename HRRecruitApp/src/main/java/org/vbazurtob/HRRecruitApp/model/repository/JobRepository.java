package org.vbazurtob.HRRecruitApp.model.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.vbazurtob.HRRecruitApp.model.Job;

public interface JobRepository extends CrudRepository<Job, Long> {

	public List<Job> findByTitle(String title);
	
	

	public Page<Job> findAll(Pageable page);
	
	
}
