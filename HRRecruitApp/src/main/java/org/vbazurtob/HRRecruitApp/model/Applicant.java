package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the applicant database table.
 * 
 */
@Entity
@Table(name="applicant")
@NamedQuery(name="Applicant.findAll", query="SELECT a FROM Applicant a")
public class Applicant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	private String address1;

	private String address2;

	private String country;

	private String email;

	private String lastname;

	private String names;

	private String password;

	private String state;

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

	public Applicant() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getNames() {
		return this.names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public List<ApplicantAcademic> getApplicantAcademics() {
		return this.applicantAcademics;
	}

	public void setApplicantAcademics(List<ApplicantAcademic> applicantAcademics) {
		this.applicantAcademics = applicantAcademics;
	}

	public ApplicantAcademic addApplicantAcademic(ApplicantAcademic applicantAcademic) {
		getApplicantAcademics().add(applicantAcademic);
		applicantAcademic.setApplicant(this);

		return applicantAcademic;
	}

	public ApplicantAcademic removeApplicantAcademic(ApplicantAcademic applicantAcademic) {
		getApplicantAcademics().remove(applicantAcademic);
		applicantAcademic.setApplicant(null);

		return applicantAcademic;
	}

	public List<ApplicantSkill> getApplicantSkills() {
		return this.applicantSkills;
	}

	public void setApplicantSkills(List<ApplicantSkill> applicantSkills) {
		this.applicantSkills = applicantSkills;
	}

	public ApplicantSkill addApplicantSkill(ApplicantSkill applicantSkill) {
		getApplicantSkills().add(applicantSkill);
		applicantSkill.setApplicant(this);

		return applicantSkill;
	}

	public ApplicantSkill removeApplicantSkill(ApplicantSkill applicantSkill) {
		getApplicantSkills().remove(applicantSkill);
		applicantSkill.setApplicant(null);

		return applicantSkill;
	}

	public List<ApplicantWorkExperience> getApplicantWorkExperiences() {
		return this.applicantWorkExperiences;
	}

	public void setApplicantWorkExperiences(List<ApplicantWorkExperience> applicantWorkExperiences) {
		this.applicantWorkExperiences = applicantWorkExperiences;
	}

	public ApplicantWorkExperience addApplicantWorkExperience(ApplicantWorkExperience applicantWorkExperience) {
		getApplicantWorkExperiences().add(applicantWorkExperience);
		applicantWorkExperience.setApplicant(this);

		return applicantWorkExperience;
	}

	public ApplicantWorkExperience removeApplicantWorkExperience(ApplicantWorkExperience applicantWorkExperience) {
		getApplicantWorkExperiences().remove(applicantWorkExperience);
		applicantWorkExperience.setApplicant(null);

		return applicantWorkExperience;
	}

	public List<JobApplicant> getJobApplicants() {
		return this.jobApplicants;
	}

	public void setJobApplicants(List<JobApplicant> jobApplicants) {
		this.jobApplicants = jobApplicants;
	}

	public JobApplicant addJobApplicant(JobApplicant jobApplicant) {
		getJobApplicants().add(jobApplicant);
		jobApplicant.setApplicant(this);

		return jobApplicant;
	}

	public JobApplicant removeJobApplicant(JobApplicant jobApplicant) {
		getJobApplicants().remove(jobApplicant);
		jobApplicant.setApplicant(null);

		return jobApplicant;
	}

}