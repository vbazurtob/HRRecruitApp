package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
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

	@Size(min=10, message="Job description length must contain at least 10 characters length")
	@NotEmpty(message="Institution name cannot be empty")
	@NotNull(message="Institution name cannot be null")
	private String description;

	
	@NotNull(message = "Salary cannot be null")
	@Min(0)
	@Digits(integer=6, fraction=2, message="Salary cannot exced 999,999.99")
	private Integer salary;
	
	@Size(min=3, max=150, message="Job title length must be between 3 to 255 characters")
	@NotEmpty(message="Job title name cannot be empty")
	@NotNull(message="Job title name cannot be null")
	private String title;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name="date_posted")
	private Date datePosted;
	
	private String status;

	
	//bi-directional many-to-one association to JobType
	@ManyToOne
	@JoinColumn(name="job_type_id")
	private JobType jobType;

	
	//bi-directional many-to-one association to JobApplicant
	@OneToMany(mappedBy="job", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

	
	
	
	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "Job [id=" + id + ", description=" + description + ", salary=" + salary + ", title=" + title
				+ ", datePosted=" + datePosted + ", status=" + status + ", jobType=" + ((jobType == null) ? null: jobType.getId() ) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datePosted == null) ? 0 : datePosted.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobApplicants == null) ? 0 : jobApplicants.hashCode());
		result = prime * result + ((jobType == null) ? 0 : jobType.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Job other = (Job) obj;
		if (datePosted == null) {
			if (other.datePosted != null)
				return false;
		} else if (!datePosted.equals(other.datePosted))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jobApplicants == null) {
			if (other.jobApplicants != null)
				return false;
		} else if (!jobApplicants.equals(other.jobApplicants))
			return false;
		if (jobType == null) {
			if (other.jobType != null)
				return false;
		} else if (! ( jobType.getId() == other.jobType.getId() ) )
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
	

}