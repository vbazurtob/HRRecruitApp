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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

/** The persistent class for the job_applicant database table. */
@Entity
@Table(name = "job_applicant")
@NamedQuery(name = "JobApplicant.findAll", query = "SELECT j FROM JobApplicant j")
public class JobApplicant implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(
      name = "JOB_APPLICANT_ID_GENERATOR",
      sequenceName = "job_applicant_id_seq",
      initialValue = 1,
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOB_APPLICANT_ID_GENERATOR")
  private Long id;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_application_sent")
  private Date dateApplicationSent;

  // bi-directional many-to-one association to Applicant
  @ManyToOne
  @JoinColumn(name = "applicant_id")
  private ApplicantWithPassword applicant;

  // bi-directional many-to-one association to Job
  @ManyToOne private Job job;

  public JobApplicant() {}

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
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    JobApplicant other = (JobApplicant) obj;
    if (applicant == null) {
      if (other.applicant != null) return false;
    } else if (!applicant.equals(other.applicant)) return false;
    if (dateApplicationSent == null) {
      if (other.dateApplicationSent != null) return false;
    } else if (!dateApplicationSent.equals(other.dateApplicationSent)) return false;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (job == null) {
        return other.job == null;
    } else return job.equals(other.job);
  }

  @Override
  public String toString() {
    return "JobApplicant [id="
        + id
        + ", dateApplicationSent="
        + dateApplicationSent
        + ", applicant="
        + applicant
        + ", job="
        + job
        + "]";
  }
}
