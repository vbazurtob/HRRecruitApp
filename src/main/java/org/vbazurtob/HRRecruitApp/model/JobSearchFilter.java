package org.vbazurtob.HRRecruitApp.model;

public class JobSearchFilter extends Job {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private SalaryRangeOption salaryRangeQuery;
	
	private int salaryRangeSearchIndex;
	
	private  int jobPostedTimeIndex;
	
	public int getJobPostedTimeIndex() {
		return jobPostedTimeIndex;
	}


	public void setJobPostedTimeIndex(int jobPostedTimeIndex) {
		this.jobPostedTimeIndex = jobPostedTimeIndex;
	}


	public JobSearchFilter() {
		super();
	}

	
//	public SalaryRangeOption getSalaryRangeQuery() {
//		return salaryRangeQuery;
//	}
//
//	public void setSalaryRangeQuery(SalaryRangeOption salaryRangeQuery) {
//		this.salaryRangeQuery = salaryRangeQuery;
//	}

	



	public int getSalaryRangeSearchIndex() {
		return salaryRangeSearchIndex;
	}


	public void setSalaryRangeSearchIndex(int salaryRangeSearchIndex) {
		this.salaryRangeSearchIndex = salaryRangeSearchIndex;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + jobPostedTimeIndex;
		result = prime * result + salaryRangeSearchIndex;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobSearchFilter other = (JobSearchFilter) obj;
		if (jobPostedTimeIndex != other.jobPostedTimeIndex)
			return false;
		if (salaryRangeSearchIndex != other.salaryRangeSearchIndex)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "JobSearchFilter [salaryRangeSearchIndex=" + salaryRangeSearchIndex + ", jobPostedTimeIndex="
				+ jobPostedTimeIndex + ", getId()=" + getId() + ", getDescription()=" + getDescription()
				+ ", getSalary()=" + getSalary() + ", getTitle()=" + getTitle() + ", getJobType()=" + getJobType()
				+ ", getJobApplicants()=" + getJobApplicants() + ", getDatePosted()=" + getDatePosted()
				+ ", getStatus()=" + getStatus() + "]";
	}


	


	


	
	
	
	
}