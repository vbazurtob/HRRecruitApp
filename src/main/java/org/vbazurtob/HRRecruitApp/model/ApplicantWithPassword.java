package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;



import javax.persistence.Entity;

import javax.persistence.NamedQuery;

import javax.persistence.Table;
import javax.validation.Valid;



/**
 * The persistent class for the applicant database table.
 * 
 */
@Entity
@Table(name="applicant")
@NamedQuery(name="ApplicantWithPassword.findAll", query="SELECT a FROM ApplicantWithPassword a")
public class ApplicantWithPassword extends ApplicantBaseClass implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Valid
	private String password;


	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	

}