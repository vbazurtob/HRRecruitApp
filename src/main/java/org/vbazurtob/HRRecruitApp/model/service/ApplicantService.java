package org.vbazurtob.HRRecruitApp.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithPassword;
import org.vbazurtob.HRRecruitApp.model.ApplicantBaseClass;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;

@Service
public class ApplicantService {

	@Autowired
	private ApplicantRepository applicantRepository;

	@Autowired
	protected BCryptPasswordEncoder bcryptEncoder;
	

	public void updateApplicantProfile(ApplicantBaseClass applicantFormData) {
		
		ApplicantWithPassword applicantDB = applicantRepository.findOneByUsername(applicantFormData.getUsername());
		
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
	
	public void createNewApplicantInDB( String username, String password, String email ) {
		
		ApplicantWithPassword newApplicantToSave = new ApplicantWithPassword();
		newApplicantToSave.setUsername( username );
		newApplicantToSave.setPassword(bcryptEncoder.encode(password) );
		newApplicantToSave.setNames(username);
		newApplicantToSave.setLastname(".");
		newApplicantToSave.setAddress1(".");
		newApplicantToSave.setAddress2(".");
		newApplicantToSave.setCountry(".");
		newApplicantToSave.setState(".");
		newApplicantToSave.setEmail(email);
		newApplicantToSave.setZipcode(".");
		
		applicantRepository.save(newApplicantToSave);
	}
	
	
	public boolean updatePassword(String applicantUsername, String password, String passwordConfirmation) {
		
		if(!password.equals(passwordConfirmation) || password.trim().isEmpty() || passwordConfirmation.trim().isEmpty()) {
			return false;
		}else {

			//Encrypt the password
			ApplicantWithPassword applicantDB = applicantRepository.findOneByUsername(applicantUsername);
			
			applicantDB.setPassword(bcryptEncoder.encode(password) );
			applicantRepository.save(applicantDB);
			
			return true;
		}
		
	}
	
	public boolean currentApplicantPasswordMatch(String username, String rawPassword ) {
		
		ApplicantWithPassword applicantWithPassword = applicantRepository.findOneByUsername(username);
		return bcryptEncoder.matches(rawPassword, applicantWithPassword.getPassword());
	}
	
	public boolean usernameExists(String username) {
		return applicantRepository.countByUsername(username) > 0;
	}
	

}
