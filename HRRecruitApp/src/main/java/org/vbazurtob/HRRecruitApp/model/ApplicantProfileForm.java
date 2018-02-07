package org.vbazurtob.HRRecruitApp.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import org.vbazurtob.HRRecruitApp.lib.FormFieldsMatchConstraint;

@FormFieldsMatchConstraint.List({
		@FormFieldsMatchConstraint(field="password", fieldMatch="passwordConfirmation", message="Passwords don''t match! No changes were made.")
})
public class ApplicantProfileForm extends Applicant{


	
	@Size(max = 128, message = "Password confirmation cannot exceed 128 characters")
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
	
	public ApplicantProfileForm(Applicant applicant) {
	
		this.setUsername(applicant.getUsername());
		this.setNames(applicant.getNames());
		this.setLastname(applicant.getLastname());
		this.setAddress1(applicant.getAddress1());
		this.setAddress2(applicant.getAddress2());
		this.setCountry(applicant.getCountry());
		this.setState(applicant.getState());
		this.setZipcode(applicant.getZipcode());
		this.setEmail(applicant.getEmail());
		this.setPassword("");
		this.setPasswordConfirmation("");
		
	}
	
}
