package org.vbazurtob.HRRecruitApp.model;

public class ApplicantProfileForm extends Applicant{

	private String passwordConfirmation;

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public ApplicantProfileForm() {
		super();
	}

	
	
	
//	
//	public ApplicantProfileForm() {
//		super();
//	}
//
//	public ApplicantProfileForm(Applicant applicant, String passwordConfirmation) {
//		super();
//		this.setAddress1(applicant.getAddress1());
//		this.setAddress2(applicant.getAddress2());
//		this.setCountry(applicant.getCountry());
//		this.setEmail(applicant.getEmail());
//		this.setLastname(applicant.getLastname());
//		this.setNames(applicant.getNames());
//		this.setPassword(applicant.getPassword());
//		this.setState(applicant.getState());
//		this.setUsername(applicant.getUsername());
//		this.setZipcode(applicant.getZipcode());		
//		this.passwordConfirmation = passwordConfirmation;
//	}
	
	
	
}
