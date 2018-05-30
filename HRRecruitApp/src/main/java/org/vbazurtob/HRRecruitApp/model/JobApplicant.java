package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;
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

	@EmbeddedId
	private JobApplicantPK id;

	@Column(name="date_application_sent")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateApplicationSent;

	//bi-directional many-to-one association to Applicant
	@ManyToOne
	@JoinColumn(name="applicant_id" , insertable= false, updatable = false)
	private Applicant applicant;

	//bi-directional many-to-one association to Job
	@ManyToOne
	@JoinColumn( insertable=false, updatable=false)
	private Job job;

	public JobApplicant() {
	}

	public JobApplicantPK getId() {
		return this.id;
	}

	public void setId(JobApplicantPK id) {
		this.id = id;
	}

	public Date getDateApplicationSent() {
		return dateApplicationSent;
	}

	public void setDateApplicationSent(Date dateApplicationSent) {
		this.dateApplicationSent = dateApplicationSent;
	}

	public Applicant getApplicant() {
		return this.applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@PrePersist
	protected void onCreate() {
		this.dateApplicationSent = new Date();
	}
	
}