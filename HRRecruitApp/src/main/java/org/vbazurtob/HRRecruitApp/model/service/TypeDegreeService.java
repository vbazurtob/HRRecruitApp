package org.vbazurtob.HRRecruitApp.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TypeDegreeService {

	private List<String> listDegreeTypes;

	
	
	
	public TypeDegreeService() {
		String[] tmpArr = {
			"Primary",
			"Secondary",
			"Diploma",
			"Bachelors",
			"Bachelors (Incomplete)",
			"Masters degree",
			"Masters degree (Incomplete)",
			"Phd",
			"Phd (Incomplete)",
			"Post doctoral"
		};
		Arrays.sort( tmpArr ); 
		
		this.listDegreeTypes = new ArrayList<>(Arrays.asList( tmpArr )) ;
	}

	public List<String> getListDegreeTypes() {
		return listDegreeTypes;
	}

	public void setListDegreeTypes(List<String> listDegreeTypes) {
		this.listDegreeTypes = listDegreeTypes;
	}

	
	
	
}
