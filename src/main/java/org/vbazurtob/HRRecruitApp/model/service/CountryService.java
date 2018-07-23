package org.vbazurtob.HRRecruitApp.model.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.Country;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class CountryService {

	
	private List<Country> listCountries;
	
	
	public CountryService() {
		
	}
	
	@PostConstruct
	public void init() throws IOException{

		try (
				
				// Replaced code for reading the country files inside a packaged .war (or jar). InpuStream in required to be used
				InputStream in = getClass().getResourceAsStream("/list_countries.csv");
				InputStreamReader reader = new InputStreamReader( in );
				
	     ){
			this.listCountries = new CsvToBeanBuilder<Country>(reader).withType(Country.class).build().parse();
	     }catch (Exception e) {
	    	 e.printStackTrace();
		}
	}

	
	public List<Country> getListCountries() {
		return listCountries;
	}

	public void setListCountries(List<Country> listCountries) {
		this.listCountries = listCountries;
	}
	
	
	
}
