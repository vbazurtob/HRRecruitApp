package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the job_applicant database table.
 * 
 */
@Entity
@Table(name="job_applicant")
@NamedQuery(name="JobApplicant.findAll", query="SELECT j FROM JobApplicant j")
public class JobApplicant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="JOB_APPLICANT_ID_GENERATOR", sequenceName="job_applicant_id_seq",initialValue=1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="JOB_APPLICANT_ID_GENERATOR")
	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_application_sent")
	private Date dateApplicationSent;

	//bi-directional many-to-one association to Applicant
	@ManyToOne
	@JoinColumn(name="applicant_id")
	private ApplicantWithPassword applicant; 

	//bi-directional many-to-one association to Job
	@ManyToOne
	private Job job;

	public JobApplicant() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateApplicationSent() {
		return this.dateApplicationSent;
	}

	public void setDateApplicationSent(Date dateApplicationSent) {
		this.dateApplicationSent = dateApplicationSent;
	}

	

	public ApplicantWithPassword getApplicant() {
		return applicant;
	}

	public void setApplicant(ApplicantWithPassword applicant) {
		this.applicant = applicant;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicant == null) ? 0 : applicant.hashCode());
		result = prime * result + ((dateApplicationSent == null) ? 0 : dateApplicationSent.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((job == null) ? 0 : job.hashCode());
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
		JobApplicant other = (JobApplicant) obj;
		if (applicant == null) {
			if (other.applicant != null)
				return false;
		} else if (!applicant.equals(other.applicant))
			return false;
		if (dateApplicationSent == null) {
			if (other.dateApplicationSent != null)
				return false;
		} else if (!dateApplicationSent.equals(other.dateApplicationSent))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JobApplicant [id=" + id + ", dateApplicationSent=" + dateApplicationSent + ", applicant=" + applicant
				+ ", job=" + job + "]";
	}
	
	
	

}