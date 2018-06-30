package org.vbazurtob.HRRecruitApp.model;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class JobSpecification implements Specification<Job> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Job criteriaFilterObj;
	
	public JobSpecification(Job c) {
		criteriaFilterObj = c;
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
			
	//			System.out.println("Root " + root.get(Job_.title )  );;
			
			titleP =  cb.like(root.get(Job_.title ) , '%' + criteriaFilterObj.getTitle() + "%");
			predicateReturn = cb.and(predicateReturn, titleP);
		}
		
		if( criteriaFilterObj.getJobType() != null ) { 
			if (criteriaFilterObj.getJobType().getId() != null && criteriaFilterObj.getJobType().getId() > 0 ) {			
		
				jobTypeP = cb.equal(root.get(Job_.jobType) , criteriaFilterObj.getJobType());

//				System.out.println("JobType " + root.get(Job_.jobType));;
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
				
		return predicateReturn;
	}

}
