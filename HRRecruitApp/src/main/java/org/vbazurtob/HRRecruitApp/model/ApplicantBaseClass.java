package org.vbazurtob.HRRecruitApp.model;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@MappedSuperclass
public class ApplicantBaseClass {


	@Id
	//@Size(min = 5, max = 35, message = "Username must be between 10 and 35 characters")
	@NotNull( message ="Username cannot be null")
	private String username;

	@NotEmpty(message = "Address cannot be empty")
	@Size(message = "Address cannot exceed 150 characters", max = 150)
	private String address1;

	@NotNull(message = "Second line of the Address cannot be null")
	@Size(message = "Second line of the Address cannot exceed 150 characters", max= 150)
	private String address2;

	@Size(message = "Country cannot exceed 2 characters", max= 2)
	@NotEmpty(message = "Country cannot be empty")
	private String country;

	@Size(message = "E-mail cannot exceed 45 characters", max= 45)
	@Email(message = "E-mail should be a valid address")
	@NotEmpty(message = "E-mail cannot be empty")
	private String email;

	@Size(message = "Lastname(s) cannot exceed 100 characters", max= 100)
	@NotEmpty(message = "Lastname(s) cannot be empty")
	private String lastname;

	@Size(message = "Name(s) cannot exceed 100 characters", max= 100)
	@NotEmpty(message = "Name(s) cannot be empty")
	private String names;

	@Size(message = "State cannot exceed 100 characters", max= 100)
	@NotEmpty(message = "State cannot be empty")
	private String state;

	@Size(message = "Zipcode cannot exceed 10 characters", max= 10)
	private String zipcode;

	//bi-directional many-to-one association to ApplicantAcademic
	@OneToMany(mappedBy="applicant")
	private List<ApplicantAcademic> applicantAcademics;

	//bi-directional many-to-one association to ApplicantSkill
	@OneToMany(mappedBy="applicant")
	private List<ApplicantSkill> applicantSkills;

	//bi-directional many-to-one association to ApplicantWorkExperience
	@OneToMany(mappedBy="applicant")
	private List<ApplicantWorkExperience> applicantWorkExperiences;

	//bi-directional many-to-one association to JobApplicant
	@OneToMany(mappedBy="applicant")
	private List<JobApplicant> jobApplicants;
	
	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public List<ApplicantAcademic> getApplicantAcademics() {
		return applicantAcademics;
	}

	public void setApplicantAcademics(List<ApplicantAcademic> applicantAcademics) {
		this.applicantAcademics = applicantAcademics;
	}

	public List<ApplicantSkill> getApplicantSkills() {
		return applicantSkills;
	}

	public void setApplicantSkills(List<ApplicantSkill> applicantSkills) {
		this.applicantSkills = applicantSkills;
	}

	public List<ApplicantWorkExperience> getApplicantWorkExperiences() {
		return applicantWorkExperiences;
	}

	public void setApplicantWorkExperiences(List<ApplicantWorkExperience> applicantWorkExperiences) {
		this.applicantWorkExperiences = applicantWorkExperiences;
	}

	public List<JobApplicant> getJobApplicants() {
		return jobApplicants;
	}

	public void setJobApplicants(List<JobApplicant> jobApplicants) {
		this.jobApplicants = jobApplicants;
	}

	public ApplicantBaseClass() {
	}
	

	@Override
	public String toString() {
		return "ApplicantProfileForm [username=" + username + ", address1=" + address1 + ", address2=" + address2
				+ ", country=" + country + ", email=" + email + ", lastname=" + lastname + ", names=" + names
				+ ", state=" + state + ", zipcode=" + zipcode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result + ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((names == null) ? 0 : names.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicantBaseClass other = (ApplicantBaseClass) obj;
		if (address1 == null) {
			if (other.address1 != null)
				return false;
		} else if (!address1.equals(other.address1))
			return false;
		if (address2 == null) {
			if (other.address2 != null)
				return false;
		} else if (!address2.equals(other.address2))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (names == null) {
			if (other.names != null)
				return false;
		} else if (!names.equals(other.names))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (zipcode == null) {
			if (other.zipcode != null)
				return false;
		} else if (!zipcode.equals(other.zipcode))
			return false;
		return true;
	}


	
	
}
