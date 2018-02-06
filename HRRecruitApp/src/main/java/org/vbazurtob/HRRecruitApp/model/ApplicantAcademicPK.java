package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the applicant_academics database table.
 * 
 */
@Embeddable
public class ApplicantAcademicPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="applicant_id", insertable=false, updatable=false)
	private String applicantId;

	private String institution;

	@Temporal(TemporalType.DATE)
	private java.util.Date started;

	@Temporal(TemporalType.DATE)
	private java.util.Date finished;

	@Column(name="degree_type")
	private String degreeType;

	@Column(name="degree_name")
	private String degreeName;

	public ApplicantAcademicPK() {
	}
	public String getApplicantId() {
		return this.applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public String getInstitution() {
		return this.institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public java.util.Date getStarted() {
		return this.started;
	}
	public void setStarted(java.util.Date started) {
		this.started = started;
	}
	public java.util.Date getFinished() {
		return this.finished;
	}
	public void setFinished(java.util.Date finished) {
		this.finished = finished;
	}
	public String getDegreeType() {
		return this.degreeType;
	}
	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}
	public String getDegreeName() {
		return this.degreeName;
	}
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ApplicantAcademicPK)) {
			return false;
		}
		ApplicantAcademicPK castOther = (ApplicantAcademicPK)other;
		return 
			this.applicantId.equals(castOther.applicantId)
			&& this.institution.equals(castOther.institution)
			&& this.started.equals(castOther.started)
			&& this.finished.equals(castOther.finished)
			&& this.degreeType.equals(castOther.degreeType)
			&& this.degreeName.equals(castOther.degreeName);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.applicantId.hashCode();
		hash = hash * prime + this.institution.hashCode();
		hash = hash * prime + this.started.hashCode();
		hash = hash * prime + this.finished.hashCode();
		hash = hash * prime + this.degreeType.hashCode();
		hash = hash * prime + this.degreeName.hashCode();
		
		return hash;
	}
}