package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the applicant database table.
 * 
 */
@Entity
@Table(name="applicant")
@NamedQuery(name="ApplicantWithoutPassword.findAll", query="SELECT a FROM ApplicantWithoutPassword a")
public class ApplicantWithoutPassword extends ApplicantBaseClass implements Serializable {

	
	private static final long serialVersionUID = 1L;

}