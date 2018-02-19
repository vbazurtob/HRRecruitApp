package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.vbazurtob.HRRecruitApp.lib.FormFieldsDatesConstraint;

/**
 * The primary key class for the applicant_academics database table.
 * 
 */
@Embeddable
@FormFieldsDatesConstraint(from= "started", to="finished", message="Dates contain errors, they have to follow these rules: Initial date cannot be blank. Initial date must be before the Final date")
public class ApplicantAcademicPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@NotNull(message="ApplicantId cannot be null")
	@Column(name="applicant_id", insertable=false, updatable=false)
	private String applicantId;

	@Size(min=3, max=255, message="Institution name length must be between 3 to 255 characters")
	@NotEmpty(message="Institution name cannot be empty")
	@NotNull(message="Institution name cannot be null")
	private String institution;

	@NotNull(message="You must select a ''Started'' date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private java.util.Date started;

	//@NotNull(message="You must select a ''finished'' date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private java.util.Date finished;

	@Size(max=80, message="Degree type cannot exceed 80 characters")
	@NotEmpty(message="Degree type cannot be empty")
	@NotNull(message="Degree type cannot be null")
	@Column(name="degree_type")
	private String degreeType;

	@Size(max=150, message="Degree name cannot exceed 150 characters")
	@NotEmpty(message="Degree name cannot be empty")
	@NotNull(message="Degree name cannot be null")
	@Column(name="degree_name")
	private String degreeName;

	public ApplicantAcademicPK() {
	}
	public String getApplicantId() {
		return this.applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public String getInstitution() {
		return this.institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public java.util.Date getStarted() {
		return this.started;
	}
	public void setStarted(java.util.Date started) {
		this.started = started;
	}
	public java.util.Date getFinished() {
		return this.finished;
	}
	public void setFinished(java.util.Date finished) {
		this.finished = finished;
	}
	public String getDegreeType() {
		return this.degreeType;
	}
	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}
	public String getDegreeName() {
		return this.degreeName;
	}
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ApplicantAcademicPK)) {
			return false;
		}
		ApplicantAcademicPK castOther = (ApplicantAcademicPK)other;
		return 
			this.applicantId.equals(castOther.applicantId)
			&& this.institution.equals(castOther.institution)
			&& this.started.equals(castOther.started)
			&& this.finished.equals(castOther.finished)
			&& this.degreeType.equals(castOther.degreeType)
			&& this.degreeName.equals(castOther.degreeName);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.applicantId.hashCode();
		hash = hash * prime + this.institution.hashCode();
		hash = hash * prime + this.started.hashCode();
		hash = hash * prime + this.finished.hashCode();
		hash = hash * prime + this.degreeType.hashCode();
		hash = hash * prime + this.degreeName.hashCode();
		
		return hash;
	}
}