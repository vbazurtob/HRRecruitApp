package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the job_applicant database table.
 * 
 */
@Embeddable
public class JobApplicantPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="applicant_id", insertable=false, updatable=false)
	private String applicantId;

	@Column(name="job_id", insertable=false, updatable=false)
	private Long jobId;

	public JobApplicantPK() {
	}
	public String getApplicantId() {
		return this.applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public Long getJobId() {
		return this.jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JobApplicantPK)) {
			return false;
		}
		JobApplicantPK castOther = (JobApplicantPK)other;
		return 
			this.applicantId.equals(castOther.applicantId)
			&& this.jobId.equals(castOther.jobId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.applicantId.hashCode();
		hash = hash * prime + this.jobId.hashCode();
		
		return hash;
	}
}