package org.vbazurtob.HRRecruitApp.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vbazurtob.HRRecruitApp.model.ApplicantAcademic;
import org.vbazurtob.HRRecruitApp.model.ApplicantAcademicPK;

@Repository
@Transactional
public interface ApplicantAcademicsRepository extends PagingAndSortingRepository<ApplicantAcademic, ApplicantAcademicPK> {
	
	public Page<ApplicantAcademic> findAll(Pageable page);
	
	
}
