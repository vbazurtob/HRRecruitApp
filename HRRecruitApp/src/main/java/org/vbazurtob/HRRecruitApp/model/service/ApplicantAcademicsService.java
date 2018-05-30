package org.vbazurtob.HRRecruitApp.model.service;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.Applicant;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
>>>>>>> b5256d02c41f6f6ae9c92ffeb8fec7c47c4e487b
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
<<<<<<< HEAD
	
	public List<ApplicantAcademic> getPagedApplicantAcademics(Applicant applicant, Pageable p) {
		
//		applicantAcademicRepository.findByApplicantId(applicant, p);
		
		
		return new ArrayList<>();
	}
=======
>>>>>>> b5256d02c41f6f6ae9c92ffeb8fec7c47c4e487b

}
