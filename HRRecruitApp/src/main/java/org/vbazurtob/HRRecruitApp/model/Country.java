package org.vbazurtob.HRRecruitApp.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByName;

public class Country {

	@CsvBindByName
	private String code;
	@CsvBindByName
	private String name;
	
	//Empty constructor is required by OpenCSV
	public Country() {
		
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Country(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	

}
