package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the applicant_work_experience database table.
 * 
 */
@Entity
@Table(name="applicant_work_experience")
@NamedQuery(name="ApplicantWorkExperience.findAll", query="SELECT a FROM ApplicantWorkExperience a")
public class ApplicantWorkExperience implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ApplicantWorkExperiencePK id;

	//bi-directional many-to-one association to Applicant
	@ManyToOne
	@JoinColumn(name="applicant_id", insertable = false, updatable =false)
	private Applicant applicant;

	public ApplicantWorkExperience() {
	}

	public ApplicantWorkExperiencePK getId() {
		return this.id;
	}

	public void setId(ApplicantWorkExperiencePK id) {
		this.id = id;
	}

	public Applicant getApplicant() {
		return this.applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

}