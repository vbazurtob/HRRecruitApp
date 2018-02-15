package org.vbazurtob.HRRecruitApp.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.ApplicantAcademic;
import org.vbazurtob.HRRecruitApp.model.ApplicantAcademicPK;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantAcademicsRepository;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;

@Service
public class ApplicantAcademicsService {

	@Autowired
	private ApplicantAcademicsRepository applicantAcademicRepository;
	
	@Autowired
	private ApplicantRepository applicantRepository;
	
	public ApplicantAcademicsService() {
	}
	
	public void saveAcademicDetail(ApplicantAcademicPK pk) {
		
		ApplicantAcademic applicantAcademic =  new ApplicantAcademic();
		applicantAcademic.setId(pk);
		applicantAcademic.setApplicant(applicantRepository.findOneByUsername(pk.getApplicantId()));
		
		applicantAcademicRepository.save(applicantAcademic);
		
	}

}
