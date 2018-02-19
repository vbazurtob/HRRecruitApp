package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the applicant_work_experience database table.
 * 
 */
@Embeddable
public class ApplicantWorkExperiencePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="applicant_id", insertable=false, updatable=false)
	private String applicantId;

	private String institution;

	private String position;

	@Temporal(TemporalType.DATE)
	private java.util.Date started;

	@Temporal(TemporalType.DATE)
	private java.util.Date finished;

	public ApplicantWorkExperiencePK() {
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
	public String getPosition() {
		return this.position;
	}
	public void setPosition(String position) {
		this.position = position;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ApplicantWorkExperiencePK)) {
			return false;
		}
		ApplicantWorkExperiencePK castOther = (ApplicantWorkExperiencePK)other;
		return 
			this.applicantId.equals(castOther.applicantId)
			&& this.institution.equals(castOther.institution)
			&& this.position.equals(castOther.position)
			&& this.started.equals(castOther.started)
			&& this.finished.equals(castOther.finished);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.applicantId.hashCode();
		hash = hash * prime + this.institution.hashCode();
		hash = hash * prime + this.position.hashCode();
		hash = hash * prime + this.started.hashCode();
		hash = hash * prime + this.finished.hashCode();
		
		return hash;
	}
}