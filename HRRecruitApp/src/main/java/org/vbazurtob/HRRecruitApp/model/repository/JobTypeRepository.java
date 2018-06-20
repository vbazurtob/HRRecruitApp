package org.vbazurtob.HRRecruitApp.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vbazurtob.HRRecruitApp.model.JobType;

@Repository
public interface JobTypeRepository extends CrudRepository<JobType, Integer> {

	public List<JobType> findByDescription(String description);
	
	public List<JobType> findAllByOrderByDescriptionAsc();
}
