package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the applicant_skills database table.
 * 
 */
@Embeddable
public class ApplicantSkillPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="applicant_id", insertable=false, updatable=false)
	private String applicantId;

	private String name;

	public ApplicantSkillPK() {
	}
	public String getApplicantId() {
		return this.applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ApplicantSkillPK)) {
			return false;
		}
		ApplicantSkillPK castOther = (ApplicantSkillPK)other;
		return 
			this.applicantId.equals(castOther.applicantId)
			&& this.name.equals(castOther.name);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.applicantId.hashCode();
		hash = hash * prime + this.name.hashCode();
		
		return hash;
	}
}