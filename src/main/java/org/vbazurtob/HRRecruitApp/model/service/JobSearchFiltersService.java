package org.vbazurtob.HRRecruitApp.model.service;

import java.util.HashMap;


import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.SalaryRangeOption;

@Service
public class JobSearchFiltersService {
	
	

	public HashMap<Integer, SalaryRangeOption> getSalaryFilterOptions(){
		
		HashMap<Integer, SalaryRangeOption> salaryRangeListObj = new HashMap<>(); 
		salaryRangeListObj.put(0, new SalaryRangeOption(0, "Any", 0, 99999999) );
		salaryRangeListObj.put(1, new SalaryRangeOption(1, "<= $100", 0, 100) );
		salaryRangeListObj.put(2, new SalaryRangeOption(2, "$101 - $500", 101, 500) );
		salaryRangeListObj.put(3, new SalaryRangeOption(3, "$501 - $1000", 501, 1000) );
		salaryRangeListObj.put(4, new SalaryRangeOption(4, "> $1000", 1000, 99999999) );
		
		return salaryRangeListObj;
	}
	
	public HashMap<Integer, String> getDatePostedFilterOptions(){
		
		HashMap<Integer, String> jobDatePostedOptions = new HashMap<>();
		jobDatePostedOptions.put(0, "All");
		jobDatePostedOptions.put(1, "Today");
		jobDatePostedOptions.put(2, "Yesterday");
		jobDatePostedOptions.put(3, "Less than 1 week ago");
		jobDatePostedOptions.put(4, "Less than 15 days ago");
		jobDatePostedOptions.put(5, "Less than 1 month ago");
		
		return jobDatePostedOptions;
	}
	
	public String[] getStatusFilterOptions() {
		return new String[]{ "All", "Open", "Closed" };
	}


}
