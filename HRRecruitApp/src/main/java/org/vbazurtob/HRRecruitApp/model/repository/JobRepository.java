package org.vbazurtob.HRRecruitApp.model.repository;


import java.util.List;

import org.eclipse.persistence.jpa.jpql.parser.TrimExpression.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.vbazurtob.HRRecruitApp.model.Job;

public interface JobRepository extends CrudRepository<Job, Long>, JpaSpecificationExecutor<Job> {

	public List<Job> findByTitle(String title);
	
	

	public Page<Job> findAll(Pageable page);
	
	public Page<Job> findAll(Specification spec, Pageable page);
	
	public Page<Job> findAllByTitleContainingOrStatusOrJobTypeId(String title, String status,  int jobTypeId, Pageable page );
}
