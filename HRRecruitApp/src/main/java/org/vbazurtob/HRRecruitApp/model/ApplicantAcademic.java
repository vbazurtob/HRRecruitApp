package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.vbazurtob.HRRecruitApp.lib.validators.FormFieldsDateToPresentNotNullConstraint;
import org.vbazurtob.HRRecruitApp.lib.validators.FormFieldsDatesConstraint;

import java.util.Date;


/**
 * The persistent class for the applicant_academics database table.
 * 
 */
@Entity
@Table(name="applicant_academics")
@NamedQuery(name="ApplicantAcademic.findAll", query="SELECT a FROM ApplicantAcademic a")
@FormFieldsDatesConstraint(from= "started", to="finished", message="Dates contain errors, they have to follow these rules: Initial date cannot be blank. Initial date must be before the Final date")
@FormFieldsDateToPresentNotNullConstraint(inProgress="inProgress", toDate="finished", message = "If the ''In Progress'' field hasn't been checked, a ''finished'' date must be specified " )
public class ApplicantAcademic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="applicant_academics_id_seq", sequenceName="applicant_academics_id_seq", initialValue=1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="applicant_academics_id_seq")
	private long id;

	@Size(max=150, message="Degree name cannot exceed 150 characters")
	@NotEmpty(message="Degree name cannot be empty")
	@NotNull(message="Degree name cannot be null")
	@Column(name="degree_name")
	private String degreeName;

	@Size(max=80, message="Degree type cannot exceed 80 characters")
	@NotEmpty(message="Degree type cannot be empty")
	@NotNull(message="Degree type cannot be null")
	@Column(name="degree_type")
	private String degreeType;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date finished;

	@Column(name="in_progress")
	private String inProgress;

	
	@Size(min=3, max=255, message="Institution name length must be between 3 to 255 characters")
	@NotEmpty(message="Institution name cannot be empty")
	@NotNull(message="Institution name cannot be null")
	private String institution;

	
	@NotNull(message="You must select a ''Started'' date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date started;

	//bi-directional many-to-one association to Applicant
	@ManyToOne
	@JoinColumn(name="applicant_id")
	private ApplicantWithPassword applicant;

	public ApplicantAcademic() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDegreeName() {
		return this.degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getDegreeType() {
		return this.degreeType;
	}

	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}

	public Date getFinished() {
		return this.finished;
	}

	public void setFinished(Date finished) {
		this.finished = finished;
	}

	public String getInProgress() {
		return this.inProgress;
	}

	public void setInProgress(String inProgress) {
		this.inProgress = inProgress;
	}

	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
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

	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ApplicantAcademic)) {
			return false;
		}
		ApplicantAcademic castOther = (ApplicantAcademic)other;
		return 
			this.id == (castOther.id)
			&& this.institution.equals(castOther.institution)
			&& this.started.equals(castOther.started)
			&& this.finished.equals(castOther.finished)
			&& this.degreeType.equals(castOther.degreeType)
			&& this.degreeName.equals(castOther.degreeName)
		    && this.inProgress.equals(castOther.inProgress)
					;
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		// hash = hash * prime + this.id;
		hash = hash * prime + this.institution.hashCode();
		hash = hash * prime + this.started.hashCode();
		hash = hash * prime + this.finished.hashCode();
		hash = hash * prime + this.degreeType.hashCode();
		hash = hash * prime + this.degreeName.hashCode();
		hash = hash * prime + this.inProgress.hashCode();
		return hash;
	}

	@Override
	public String toString() {
		return "ApplicantAcademic [id=" + id + ", degreeName=" + degreeName + ", degreeType=" + degreeType
				+ ", finished=" + finished + ", inProgress=" + inProgress + ", institution=" + institution
				+ ", started=" + started + ", applicant=" + ((applicant!=null)? applicant.toString(): null) + "]";
	}
	
	
	
}