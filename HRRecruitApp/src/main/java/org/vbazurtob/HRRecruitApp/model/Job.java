package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the job database table.
 * 
 */
@Entity
@Table(name="job")
@NamedQuery(name="Job.findAll", query="SELECT j FROM Job j")
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="job_id_seq", sequenceName="job_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="job_id_seq")
	private Long id;

	private String description;

	private Integer salary;

	private String title;

	//bi-directional many-to-one association to JobType
	@ManyToOne
	@JoinColumn(name="job_type_id")
	private JobType jobType;

	//bi-directional many-to-one association to JobApplicant
	@OneToMany(mappedBy="job")
	private List<JobApplicant> jobApplicants;

	public Job() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSalary() {
		return this.salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JobType getJobType() {
		return this.jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public List<JobApplicant> getJobApplicants() {
		return this.jobApplicants;
	}

	public void setJobApplicants(List<JobApplicant> jobApplicants) {
		this.jobApplicants = jobApplicants;
	}

	public JobApplicant addJobApplicant(JobApplicant jobApplicant) {
		getJobApplicants().add(jobApplicant);
		jobApplicant.setJob(this);

		return jobApplicant;
	}

	public JobApplicant removeJobApplicant(JobApplicant jobApplicant) {
		getJobApplicants().remove(jobApplicant);
		jobApplicant.setJob(null);

		return jobApplicant;
	}

}