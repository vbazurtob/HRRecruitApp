/*
 * Personal License Agreement
 * Copyright Notice
 *
 * © 2025 Voltaire Bazurto Blacio. All rights reserved.
 * License Terms
 *
 *     Ownership: All code contained in this portfolio is the sole property of Voltaire Bazurto Blacio and is hereby copyrighted by me.
 *
 *     Permitted Use: Others are welcome to view and study the code for personal, educational, or non-commercial purposes. You may share insights or information about the code, but you cannot use it for any commercial products, either as-is or in a derivative form.
 *
 *     Restrictions: The code may not be used, reproduced, or distributed for commercial purposes under any circumstances without my explicit written permission.
 *
 *     Rights Reserved: I reserve the right to use the code, or any future versions thereof, for my own purposes in any way I choose, including but not limited to the development of future commercial derivative works under my name or personal brand.
 *
 *     Disclaimer: The code is provided "as is" without warranty of any kind, either express or implied. I am not responsible for any damages resulting from the use of this code.
 *
 * By accessing this portfolio, you agree to abide by these terms.
 */

package org.vbazurtob.HRRecruitApp.model;

public class JobSearchFilter extends Job {

  /** */
  private static final long serialVersionUID = 1L;

  //	private SalaryRangeOption salaryRangeQuery;

  private int salaryRangeSearchIndex;

  private int jobPostedTimeIndex;

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
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (getClass() != obj.getClass()) return false;
    JobSearchFilter other = (JobSearchFilter) obj;
    if (jobPostedTimeIndex != other.jobPostedTimeIndex) return false;
      return salaryRangeSearchIndex == other.salaryRangeSearchIndex;
  }

  @Override
  public String toString() {
    return "JobSearchFilter [salaryRangeSearchIndex="
        + salaryRangeSearchIndex
        + ", jobPostedTimeIndex="
        + jobPostedTimeIndex
        + ", getId()="
        + getId()
        + ", getDescription()="
        + getDescription()
        + ", getSalary()="
        + getSalary()
        + ", getTitle()="
        + getTitle()
        + ", getJobType()="
        + getJobType()
        + ", getJobApplicants()="
        + getJobApplicants()
        + ", getDatePosted()="
        + getDatePosted()
        + ", getStatus()="
        + getStatus()
        + "]";
  }
}
