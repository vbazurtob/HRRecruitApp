package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * The persistent class for the applicant_skills database table.
 * 
 */
@Entity
@Table(name="applicant_skills")
@NamedQuery(name="ApplicantSkill.findAll", query="SELECT a FROM ApplicantSkill a")
public class ApplicantSkill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APPLICANT_SKILLS_ID_GENERATOR", sequenceName="applicant_skills_id_seq", initialValue=1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APPLICANT_SKILLS_ID_GENERATOR")
	private Long id;

	@Size(min=1, max=35, message="Name should be between 3 to 255 characters")
	@NotEmpty(message="Name cannot be empty")
	@NotNull(message="Name cannot be null")
	private String name;

	@Digits(integer=1, fraction=0)
	@Min(0)
	@Max(5)
	@NotNull(message="Proficiency cannot be null")
	private Integer proficiency;// 1 - 5 (Beginner, Novice, Intermediate, Upper Intermediate, Expert)

	//bi-directional many-to-one association to Applicant
	@ManyToOne
	@JoinColumn(name="applicant_id")
	private ApplicantWithPassword applicant;

	public ApplicantSkill() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProficiency() {
		return this.proficiency;
	}

	public void setProficiency(Integer proficiency) {
		this.proficiency = proficiency;
	}

	public ApplicantWithPassword getApplicant() {
		return this.applicant;
	}

	public void setApplicant(ApplicantWithPassword applicant) {
		this.applicant = applicant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicant == null) ? 0 : applicant.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((proficiency == null) ? 0 : proficiency.hashCode());
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
		ApplicantSkill other = (ApplicantSkill) obj;
		if (applicant == null) {
			if (other.applicant != null)
				return false;
		} else if (!applicant.equals(other.applicant))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (proficiency == null) {
			if (other.proficiency != null)
				return false;
		} else if (!proficiency.equals(other.proficiency))
			return false;
		return true;
	}
	
	

}