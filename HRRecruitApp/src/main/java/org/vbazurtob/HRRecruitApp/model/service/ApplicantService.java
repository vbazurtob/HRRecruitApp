package org.vbazurtob.HRRecruitApp.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.Applicant;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;

@Service
public class ApplicantService {

	@Autowired
	private ApplicantRepository applicantRepository;

	@Autowired
	protected BCryptPasswordEncoder bcryptEncoder;
	

	public void updateApplicantProfile(Applicant applicantFormData) {
		
		Applicant applicantDB = applicantRepository.findOneByUsername(applicantFormData.getUsername());
		
		//Update only selected fields. Password is not updated
		
		applicantDB.setNames(applicantFormData.getNames());
		applicantDB.setLastname(applicantFormData.getLastname());
		applicantDB.setAddress1(applicantFormData.getAddress1());
		applicantDB.setAddress2(applicantFormData.getAddress2());
		
		applicantDB.setCountry(applicantFormData.getCountry());
		applicantDB.setState(applicantFormData.getState());
		applicantDB.setZipcode(applicantFormData.getZipcode());
		applicantDB.setEmail(applicantFormData.getEmail());
		
		applicantRepository.save(applicantDB);
		
	}
	
	
	public boolean updatePassword(String applicantUsername, String password, String passwordConfirmation) {
		
		if(!password.equals(passwordConfirmation) || password.trim().isEmpty() || passwordConfirmation.trim().isEmpty()) {
			return false;
		}else {

			//Encrypt the password
			Applicant applicantDB = applicantRepository.findOneByUsername(applicantUsername);
			
			applicantDB.setPassword(bcryptEncoder.encode(password) );
			applicantRepository.save(applicantDB);
			
			return true;
		}
		
	}
	

}
