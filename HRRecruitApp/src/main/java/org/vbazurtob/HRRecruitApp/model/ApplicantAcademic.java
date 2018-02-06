package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the applicant_academics database table.
 * 
 */
@Entity
@Table(name="applicant_academics")
@NamedQuery(name="ApplicantAcademic.findAll", query="SELECT a FROM ApplicantAcademic a")
public class ApplicantAcademic implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ApplicantAcademicPK id;

	//bi-directional many-to-one association to Applicant
	@ManyToOne
	@JoinColumn(name="applicant_id", insertable =false, updatable= false)
	private Applicant applicant;

	public ApplicantAcademic() {
	}

	public ApplicantAcademicPK getId() {
		return this.id;
	}

	public void setId(ApplicantAcademicPK id) {
		this.id = id;
	}

	public Applicant getApplicant() {
		return this.applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

}