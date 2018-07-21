package org.vbazurtob.HRRecruitApp.model;



import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.vbazurtob.HRRecruitApp.model.service.JobSearchFiltersService;



public class JobSpecification implements Specification<Job> {

	
	private JobSearchFiltersService jobSearchFiltersService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Job criteriaFilterObj;
	
	
	
	public JobSpecification(Job c, JobSearchFiltersService jobSearchFilterSvc) {
		criteriaFilterObj = c;
		jobSearchFiltersService = jobSearchFilterSvc;
	}

	@Override
	public Predicate toPredicate(Root<Job> root, CriteriaQuery<?> query, CriteriaBuilder cb) {



		Predicate predicateReturn = cb.conjunction();
		
		if(criteriaFilterObj == null) {
			return predicateReturn;
		}

		//Avoid deleted records
		Predicate avoidDeleted = cb.notEqual(root.get(Job_.status), "D");
		predicateReturn = cb.and(predicateReturn, avoidDeleted );
		
		
		Predicate titleP, statusP, jobTypeP;
		if( criteriaFilterObj.getTitle() != null && !criteriaFilterObj.getTitle().trim().isEmpty()  ) {
			
			
			titleP =  cb.like(root.get(Job_.title ) , '%' + criteriaFilterObj.getTitle() + "%");
			predicateReturn = cb.and(predicateReturn, titleP);
		}
		
		if( criteriaFilterObj.getJobType() != null ) { 
			if (criteriaFilterObj.getJobType().getId() != null && criteriaFilterObj.getJobType().getId() > 0 ) {			
		
				jobTypeP = cb.equal(root.get(Job_.jobType) , criteriaFilterObj.getJobType());

				predicateReturn = cb.and(predicateReturn, jobTypeP);
			}
		}
		
		if(criteriaFilterObj.getStatus() != null) {
			String status;
			if( criteriaFilterObj.getStatus().equals("Open") ) {
				status = "O";
			}else if(criteriaFilterObj.getStatus().equals("Closed")) {
				status = "C";
			}else {
				status = "A";
			}
			
			if( !status.equals("A")) {
				
//				System.out.println("status " + root.get(Job_.status) );
				statusP = cb.equal(root.get(Job_.status), status);
				predicateReturn = cb.and(predicateReturn, statusP);
			}
			
		}
		
		if( criteriaFilterObj instanceof JobSearchFilter ) {
			
			if( ((JobSearchFilter) criteriaFilterObj).getJobPostedTimeIndex() > 0 ) {
				
				Date jobPostedFilter = getDateFilter( ((JobSearchFilter) criteriaFilterObj).getJobPostedTimeIndex() );
				Predicate jobPostedDateP;
				
				switch ( ((JobSearchFilter) criteriaFilterObj).getJobPostedTimeIndex()  ) {
					case 1:
					case 2:
					
						jobPostedDateP = cb.equal(root.get(Job_.datePosted), jobPostedFilter );

						break;
						
					
					default: 
						jobPostedDateP = cb.between(root.get(Job_.datePosted), jobPostedFilter , new Date() );

							break;
					
				}
				
				
				predicateReturn = cb.and(predicateReturn, jobPostedDateP);
				
			}
			
			if( ((JobSearchFilter) criteriaFilterObj).getSalaryRangeSearchIndex() > 0 ) {
				

				HashMap<Integer, SalaryRangeOption> salaryRangesHash =  jobSearchFiltersService.getSalaryFilterOptions();
				
				SalaryRangeOption salaryRangeSelected = salaryRangesHash.get(((JobSearchFilter) criteriaFilterObj).getSalaryRangeSearchIndex());

				Predicate salaryP = cb.between( root.get(Job_.salary), salaryRangeSelected.getSalaryEqualsMore(),
						salaryRangeSelected.getSalaryLessEquals() );
				
				predicateReturn = cb.and( predicateReturn , salaryP);
						
				
			}
			
		}
		
				
		return predicateReturn;
	}
	
	public static Date getDateFilter(int jobPostedFilterIndex) {
		LocalDate jobPostedDateFilter ;
		//0 All
		
		//1 Today
		LocalDate today = LocalDate.now();
		
		switch (jobPostedFilterIndex) {
			case 1:
				jobPostedDateFilter = today;
				break;
			case 2:
				//2 Yesterday
				jobPostedDateFilter = today.minusDays(1);
				break;
			case 3:
				//3 One week ago
				jobPostedDateFilter = today.minusDays(7);

				break;
			case 4:
				//4 15 days
				jobPostedDateFilter = today.minusDays(15);
				break;
			case 5:
				//5 One month
				jobPostedDateFilter = today.minusDays(30);
				break;
			default:
				jobPostedDateFilter = today;
				break;
		}
		
		
		
		
		return  java.sql.Date.valueOf( jobPostedDateFilter );
	}

}
