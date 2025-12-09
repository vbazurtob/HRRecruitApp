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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.vbazurtob.HRRecruitApp.lib.validators.FormFieldsDateToPresentNotNullConstraint;
import org.vbazurtob.HRRecruitApp.lib.validators.FormFieldsDatesConstraint;

/**
 * The persistent class for the applicant_work_experience database table.
 * 
 */
@Entity
@Table(name="applicant_work_experience")
@NamedQuery(name="ApplicantWorkExperience.findAll", query="SELECT a FROM ApplicantWorkExperience a")
@FormFieldsDatesConstraint(from= "started", to="finished", message="Dates contain errors, they have to follow these rules: Initial date cannot be blank. Initial date must be before the Final date")
@FormFieldsDateToPresentNotNullConstraint(inProgress="inProgress", toDate="finished", message = "If the ''In Progress'' field hasn't been checked, a ''finished'' date must be specified " )
public class ApplicantWorkExperience implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APPLICANT_WORK_EXPERIENCE_ID_GENERATOR", sequenceName="applicant_work_experience_id_seq", initialValue=1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APPLICANT_WORK_EXPERIENCE_ID_GENERATOR")
	private Long id;

	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date finished;

	@Size(min=3, max=255, message="Institution name length must be between 3 to 255 characters")
	@NotEmpty(message="Institution name cannot be empty")
	@NotNull(message="Institution name cannot be null")
	private String institution;

	@Size(min=3, max=255, message="Position name length must be between 3 to 255 characters")
	@NotEmpty(message="Institution name cannot be empty")
	@NotNull(message="Institution name cannot be null")
	private String position;

	
	@NotNull(message="You must select a ''Started'' date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date started;

	@Column(name="in_progress")
	private String inProgress;
	
	//bi-directional many-to-one association to Applicant
	@ManyToOne
	@JoinColumn(name="applicant_id")
	private ApplicantWithPassword applicant;

	public ApplicantWorkExperience() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFinished() {
		return this.finished;
	}

	public void setFinished(Date finished) {
		this.finished = finished;
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

	public Date getStarted() {
		return this.started;
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	public ApplicantWithPassword getApplicant() {
		return this.applicant;
	}

	public void setApplicant(ApplicantWithPassword applicant) {
		this.applicant = applicant;
	}
	
	
	public String getInProgress() {
		return inProgress;
	}

	public void setInProgress(String inProgress) {
		this.inProgress = inProgress;
	}

	
	
	@Override
	public String toString() {
		return "ApplicantWorkExperience [id=" + id + ", finished=" + finished + ", institution=" + institution
				+ ", position=" + position + ", started=" + started + ", inProgress=" + inProgress + ", applicant="
			    + (( applicant == null ) ? "null" : applicant.toString() )  + "]";
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ApplicantWorkExperience)) {
			return false;
		}
		ApplicantWorkExperience castOther = (ApplicantWorkExperience)other;
		return 
			this.id == castOther.id &&
			this.applicant.equals(castOther.applicant)
			&& this.institution.equals(castOther.institution)
			&& this.position.equals(castOther.position)
			&& this.started.equals(castOther.started)
			&& this.finished.equals(castOther.finished)
			&& this.inProgress.equals(castOther.inProgress);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.intValue(); // Long to int
		hash = hash * prime + this.institution.hashCode();
		hash = hash * prime + this.position.hashCode();
		hash = hash * prime + this.started.hashCode();
		hash = hash * prime + this.finished.hashCode();
		
		return hash;
	}

}