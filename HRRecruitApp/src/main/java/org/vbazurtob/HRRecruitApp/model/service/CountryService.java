package org.vbazurtob.HRRecruitApp.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.Country;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class CountryService {

	@Autowired
	private ResourceLoader resourceloader;
	
	private List<Country> listCountries;
	
	
	public CountryService() {
		
	}
	
	@PostConstruct
	public void init() throws IOException{
		Resource resource = resourceloader.getResource("classpath:/list_countries.csv");
		

		try (
				
	            Reader reader = Files.newBufferedReader(Paths.get(resource.getURI()));
				
	     ){
			
			this.listCountries = new CsvToBeanBuilder(reader).withType(Country.class).build().parse();
	     }
	}

	
	public List<Country> getListCountries() {
		return listCountries;
	}

	public void setListCountries(List<Country> listCountries) {
		this.listCountries = listCountries;
	}
	
	
	
}
