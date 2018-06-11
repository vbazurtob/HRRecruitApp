package org.vbazurtob.HRRecruitApp.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.Proficiency;


@Service
public class ProficiencyService {

	
	private List<Proficiency> listProficiencies;
	
	
	public ProficiencyService() {
		
	}
	
	@PostConstruct
	public void init() throws IOException{

		String[] text = {
				"Beginner",
				"Novice",
				"Intermediate",
				"Upper-Intermediate",
				"Expert"
		};
		
		ArrayList<Proficiency> listP = new ArrayList<>();
		
		int c = 1;
		for( String t : text) {
			Proficiency p = new Proficiency();
			p.setText(t);
			p.setScore(c);
			c++;
			listP.add(p);
		}
		this.setListProficiencies(listP);
		
	}

	public List<Proficiency> getListProficiencies() {
		return listProficiencies;
	}

	public void setListProficiencies(List<Proficiency> listProficiencies) {
		this.listProficiencies = listProficiencies;
	}

	
	public String getTextFromScore(int score) {
		return this.getListProficiencies().get(score-1).getText();
	}

	
	
}
