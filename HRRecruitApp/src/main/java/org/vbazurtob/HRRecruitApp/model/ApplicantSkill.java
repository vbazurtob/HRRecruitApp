package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the applicant_skills database table.
 * 
 */
@Entity
@Table(name="applicant_skills")
@NamedQuery(name="ApplicantSkill.findAll", query="SELECT a FROM ApplicantSkill a")
public class ApplicantSkill implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ApplicantSkillPK id;

	private Integer proficiency;

	//bi-directional many-to-one association to Applicant
	@ManyToOne
	@JoinColumn(name="applicant_id", insertable=false, updatable =false)
	private Applicant applicant;

	public ApplicantSkill() {
	}

	public ApplicantSkillPK getId() {
		return this.id;
	}

	public void setId(ApplicantSkillPK id) {
		this.id = id;
	}

	public Integer getProficiency() {
		return this.proficiency;
	}

	public void setProficiency(Integer proficiency) {
		this.proficiency = proficiency;
	}

	public Applicant getApplicant() {
		return this.applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

}